package com.triton.johnson_tap_app.activity;



import static com.triton.johnson_tap_app.utils.CommonFunction.nullPointerValidator;
import static com.triton.johnsonapp.db.CommonUtil.context;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.triton.johnson_tap_app.Dashbaord_MainActivity;
import com.triton.johnson_tap_app.Location.GpsTracker;
import com.triton.johnson_tap_app.api.APIInterface;
import com.triton.johnson_tap_app.api.RetrofitClient;
import com.triton.johnson_tap_app.materialeditext.MaterialEditText;
import com.triton.johnson_tap_app.materialspinner.MaterialSpinner;
import com.triton.johnson_tap_app.requestpojo.AttendanceCreateRequest;
import com.triton.johnson_tap_app.requestpojo.LoginRequest;
import com.triton.johnson_tap_app.responsepojo.LoginResponse;
import com.triton.johnson_tap_app.responsepojo.SuccessResponse;
import com.triton.johnson_tap_app.session.SessionManager;
import com.triton.johnson_tap_app.utils.ConnectionDetector;
import com.google.android.material.snackbar.Snackbar;
import com.triton.johnson_tap_app.utils.NumericKeyBoardTransformationMethod;
import com.triton.johnson_tap_app.utils.RestUtils;
import com.triton.johnsonapp.R;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private String TAG ="LoginActivity";

    MaterialEditText employeeMaterialEditText, userNameMaterialEditText, passwordMaterialEditText;

    MaterialSpinner mainMaterialSpinner;

    LinearLayout forgotLinearLayout, loginMainLinearLayout;

    RequestQueue requestQueue;

    String location;
    String imeicode;
    SharedPreferences sharedPreferences;

    TextView mainReasonCustomFontTextView;

    Button loginButton;

    String networkStatus = "", stationId = "";
    String status , message = "", user_level = "", station_code = "", station_name = "", empid = "", name = "", username = "",latitude,longitude, mobile;

    Dialog  dialog;

    SessionManager sessionManager;
    private String role = "";
    private GpsTracker gpsTracker;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    Geocoder geocoder;
    String address = "";
    List<Address> myAddress = new ArrayList<>();

    private String userid;
    private String token;
    private static final int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.w(TAG,"onCreate-->");

        sessionManager = new SessionManager(getApplicationContext());


        userNameMaterialEditText =  findViewById(R.id.user_name);
        passwordMaterialEditText =  findViewById(R.id.password);
        loginMainLinearLayout =  findViewById(R.id.loginMainLinearLayout);

        userNameMaterialEditText.setTransformationMethod(new NumericKeyBoardTransformationMethod());


        loginButton = findViewById(R.id.loginnnn_button);

        userNameMaterialEditText.setOnTouchListener((view, motionEvent) -> {

            userNameMaterialEditText.setFocusableInTouchMode(true);
            passwordMaterialEditText.setFocusableInTouchMode(true);
            return false;
        });

        passwordMaterialEditText.setOnTouchListener((view, motionEvent) -> {

            userNameMaterialEditText.setFocusableInTouchMode(true);
            passwordMaterialEditText.setFocusableInTouchMode(true);
            return false;
        });

        hitLocation();

        // check whether internet is on or not
        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Snackbar snackbar = Snackbar
                    .make(loginMainLinearLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", view -> {

                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    });

            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = sbView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }

        loginButton.setOnClickListener(view -> {
           /* Intent intent = new Intent(CMRLLogin.this, CmrlLoginDashboardActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.new_right, R.anim.new_left);
*/
            networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
            if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {
                Snackbar snackbar = Snackbar
                        .make(loginMainLinearLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", view1 -> {

                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                            startActivity(intent);
                        });

                snackbar.setActionTextColor(Color.RED);

                // Changing action button text color
                View sbView = snackbar.getView();
                TextView textView = sbView.findViewById(R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();
            }
            else {
                if (Objects.requireNonNull(userNameMaterialEditText.getText()).toString().trim().equalsIgnoreCase("")) {
                    Toasty.warning(getApplicationContext(),"Enter Phone Number",Toasty.LENGTH_LONG).show();

                } else if (Objects.requireNonNull(passwordMaterialEditText.getText()).toString().trim().equalsIgnoreCase("")) {
                    Toasty.warning(getApplicationContext(),"Enter Password",Toasty.LENGTH_LONG).show();
                } else {

                    LoginResponseCall();
                }
            }


        });

        //StationListUrl();
    }

    // default back button action
    public void onBackPressed() {

      /*  Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.new_right, R.anim.new_left);*/

        //super.onBackPressed();
    }
    private void hitLocation() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getMYLocation();
        }
    }
    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getMYLocation() {

        gpsTracker = new GpsTracker(context);
        if (gpsTracker.canGetLocation()) {
            latitude = "" + gpsTracker.getLatitude();
            longitude = "" + gpsTracker.getLongitude();
            Log.i(TAG, "getMYLocation: latitude -> " + latitude + " longitude -> " + longitude);
            if (nullPointerValidator(latitude) && nullPointerValidator(longitude)
                    && !latitude.equalsIgnoreCase("0.0") && !longitude.equalsIgnoreCase("0.0")) {
                geocoder = new Geocoder(context, Locale.getDefault());
                try {
                    myAddress = geocoder.getFromLocation(gpsTracker.getLatitude(), gpsTracker.getLongitude(), 1);
                    address = myAddress.get(0).getAddressLine(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "getMYLocation: address -> " + address);
            } else {
                ErrorMyLocationAlert("Kindly enable the\nGPS Location and Try again");
//                Toast.makeText(context, "Kindly enable the GPS Location and Try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            gpsTracker.showSettingsAlert();
        }
    }
    private void ErrorMyLocationAlert(String strMsg) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View mView = getLayoutInflater().inflate(R.layout.popup_tryagain, null);

        TextView txt_Message = mView.findViewById(R.id.txt_message);
        Button btn_Ok = mView.findViewById(R.id.btn_ok);

        txt_Message.setText(strMsg);

        mBuilder.setView(mView);
        AlertDialog mDialog = mBuilder.create();
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }
    protected void onResume() {

        super.onResume();
        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Snackbar snackbar = Snackbar
                    .make(loginMainLinearLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", view -> {

                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    });

            snackbar.setActionTextColor(Color.RED);

// Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = sbView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();


        } else {

            //StationListUrl();
        }
    }

    @SuppressLint("LogNotTimber")
    private void LoginResponseCall() {
        dialog = new Dialog(LoginActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<LoginResponse> call = apiInterface.LoginResponseCall(RestUtils.getContentType(), loginRequest());
        Log.w(TAG, "SignupResponse url  :%s" + " " + call.request().url().toString());


        call.enqueue(new Callback<LoginResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {

                Log.w(TAG, "SignupResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {


                            imeicode = response.body().getData().getImie_code();
                            location=response.body().getData().getLocation();

                            /* if (imeicode.equals(ID) ) {*/
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);


                            startActivity(intent);

                            Log.e("UserId", response.body().getData().getUser_id());

                            Log.e("_id", response.body().getData().get_id());

                            userid = response.body().getData().get_id();
                            String location = response.body().getData().getLocation().replaceAll("\\s+", "");
                            sessionManager.createSessionLogin(
                                    response.body().getData().get_id(),
                                    String.valueOf(response.body().getData().getUser_id()),
                                    response.body().getData().getUser_name(),
                                    response.body().getData().getUser_email_id(),
                                    response.body().getData().getUser_designation(),
                                    response.body().getData().getUser_token(),
                                    response.body().getData().getUser_status(),
                                    String.valueOf(response.body().getData().getUser_type()),
                                    response.body().getData().getUser_role(),
                                    response.body().getData().getImie_code(),
                                    response.body().getData().getAgent_code(),
                                    response.body().getData().getLocation()


                            );


                            String usermobile = userNameMaterialEditText.getText().toString();
                            String username = response.body().getData().getUser_name();
                            Log.e("Mobile Number",""+usermobile);
                            Log.e("Location",""+location);
                            Log.e("Name",""+username);
                            String formattedcode =  response.body().getData().getAgent_code().replaceAll("\\s+", "");

                            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("mobile", usermobile);
                            editor.putString("location", location);
                            editor.putString("username", username);
                            editor.putString("agent_code", formattedcode);
                            Log.e("Mobile Number 1",""+ usermobile);
                            Log.e("Location 1",""+location);
                            editor.apply();

                            attendanceCreateRequestCall(response.body().getData().get_id(), response.body().getData().getUser_name());
                            Log.e("UserId", response.body().getData().get_id());


                            /*  }*/ /*else {
                                ShowPopup();
                                dialog.dismiss();
                            }*/



                        }




                    } else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();

                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e("OTP", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private LoginRequest loginRequest() {

        /**
         * user_id : 12345
         * user_password : 12345
         * last_login_time : 20-10-2021 11:00 AM
         */

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        LoginRequest loginRequest = new LoginRequest();
        if (!nullPointerValidator(latitude) | !nullPointerValidator(longitude)
                | latitude.equalsIgnoreCase("0.0") | longitude.equalsIgnoreCase("0.0")) {

            Log.i(TAG, "createRequest: latitude -> " + latitude + " longitude -> " + longitude);
            ErrorMyLocationAlert("Kindly enable the\nGPS Location and Try again");
            return null;
        }
        loginRequest.setUser_id(userNameMaterialEditText.getText().toString().trim());
        loginRequest.setUser_password(passwordMaterialEditText.getText().toString());
        loginRequest.setLast_login_time(currentDateandTime);
        loginRequest.setLogin_lat(latitude);
        loginRequest.setLogin_long(longitude);
        loginRequest.setLogin_address(address);
        Log.i(TAG,"loginRequest "+ new Gson().toJson(loginRequest));
        return loginRequest;
    }


    @SuppressLint("LogNotTimber")
    private void attendanceCreateRequestCall(String user_id, String user_name) {
        dialog = new Dialog(LoginActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<SuccessResponse> call = apiInterface.attendanceCreateRequestCall(RestUtils.getContentType(), attendanceCreateRequest(user_id,user_name));
        Log.w(TAG,"attendanceCreateRequest url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {

                Log.w(TAG,"attendanceCreateRequest" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {

                        startActivity(new Intent(LoginActivity.this, Dashbaord_MainActivity.class));
                    }


//                        if(status.equals("0")){
//
//                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
//                            alertDialogBuilder.setMessage("Allow");
//                            alertDialogBuilder.setPositiveButton("yes",
//                                    new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface arg0, int arg1) {
//                                            startActivity(new Intent(LoginActivity.this, Dashbaord_MainActivity.class));
//                                        }
//                                    });
//
////                                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
////                                    Override
////                                    public void onClick(DialogInterface dialog, int which) {
////                                        finish();
////                                    }
////                                });
//
//                            AlertDialog alertDialog = alertDialogBuilder.create();
//                            alertDialog.show();
//                        }
//
//                        else {
//                            Toast.makeText(LoginActivity.this,"Status 1",Toast.LENGTH_LONG).show();
//                        }
//
//                    } else {
//                        dialog.dismiss();
//                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();
//
//                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call,@NonNull Throwable t) {
                dialog.dismiss();
                Log.e("SuccessResponse flr", "--->" + t.getMessage());
            }
        });

    }
    private AttendanceCreateRequest attendanceCreateRequest(String user_id, String user_name) {

         /*{
            "user_id": "6209f7cb967cef205b87110c",
            "attendance_name": "Mohammed imthiyas",
            "attendance_start_date": "23-10-2022",
            "attendance_start_date_time": "23-10-2022 11:00 AM",
            "attendance_start_lat": 18.009090,
            "attendance_start_long": 79.990090,
            "attendance_created_at": "23-10-2022 11:00 AM"
}*/

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = simpleDateFormat.format(new Date());

        AttendanceCreateRequest attendanceCreateRequest = new AttendanceCreateRequest();
        attendanceCreateRequest.setUser_id(user_id);
        attendanceCreateRequest.setAttendance_name(user_name);
        attendanceCreateRequest.setAttendance_start_date(currentDate);
        attendanceCreateRequest.setAttendance_start_date_time(currentDateandTime);
        attendanceCreateRequest.setAttendance_created_at(currentDateandTime);
        Log.w(TAG,"attendanceCreateRequest "+ new Gson().toJson(attendanceCreateRequest));
        return attendanceCreateRequest;
    }

    @SuppressLint("MissingSuperCall")
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull String @org.jetbrains.annotations.NotNull [] permissions, @org.jetbrains.annotations.NotNull int @org.jetbrains.annotations.NotNull [] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                }
            } else {
                Toast.makeText(getApplicationContext(), "permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

}