package com.triton.johnsonapp.activity;


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
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.multidex.BuildConfig;

import com.android.volley.RequestQueue;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.triton.johnson_tap_app.Location.GpsTracker;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.db.CommonUtil;
import com.triton.johnsonapp.db.DbHelper;
import com.triton.johnsonapp.db.DbUtil;
import com.triton.johnsonapp.materialeditext.MaterialEditText;
import com.triton.johnsonapp.materialspinner.MaterialSpinner;
import com.triton.johnsonapp.requestpojo.AttendanceCreateRequest;
import com.triton.johnsonapp.requestpojo.ImageSafetyRequest;
import com.triton.johnsonapp.requestpojo.LoginRequest;
import com.triton.johnsonapp.responsepojo.LoginResponse;
import com.triton.johnsonapp.responsepojo.SafetyImageResponse;
import com.triton.johnsonapp.responsepojo.SuccessResponse;
import com.triton.johnsonapp.service.GPSTracker;
import com.triton.johnsonapp.session.SessionManager;
import com.triton.johnsonapp.utils.ConnectionDetector;
import com.google.android.material.snackbar.Snackbar;
import com.triton.johnsonapp.utils.NumericKeyBoardTransformationMethod;
import com.triton.johnsonapp.utils.RestUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.android.gms.location.LocationListener;

import org.jetbrains.annotations.NotNull;


public class LoginActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private String TAG = "LoginActivity";

    MaterialEditText employeeMaterialEditText, userNameMaterialEditText, passwordMaterialEditText;

    MaterialSpinner mainMaterialSpinner;

    LinearLayout forgotLinearLayout, loginMainLinearLayout;

    RequestQueue requestQueue;

    TextView mainReasonCustomFontTextView;

    Button loginButton;
    String getdate="";
    String imeicode;
    String imageurl;
    String networkStatus = "", stationId = "";
    String status = "", message = "", user_level = "", station_code = "", station_name = "", empid = "", name = "", username = "", mobile,latitude1,longitude1;

    Dialog dialog;
    Dialog alertdialog;
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
    private GoogleApiClient googleApiClient;
    Location mLastLocation;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private double latitude;
    private double longitude;
    String ID;
    Calendar calendar;
    private String VersionUpdate, VersionUpdate1;
    Context context;
    ImageView safetyimage;
    SharedPreferences sharedPreferences;
    String location;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        CommonUtil.dbUtil = new DbUtil(context);
        CommonUtil.dbUtil.open();
        CommonUtil.dbHelper = new DbHelper(context);

        Log.i(TAG, "onCreate-->");
        TextView device_id = (TextView) findViewById(R.id.device_id);
        TextView txt_version = (TextView) findViewById(R.id.txt_version);
        TextView txt_version1 = (TextView) findViewById(R.id.txt_version1);
        safetyimage = findViewById(R.id.safetyimaageview);

        String Version1 = txt_version1.getText().toString();
        Log.i(TAG, "VERSION1--" + Version1);
        //txt_version.setText("Version "+thisDate+".1");
        String Version = txt_version.getText().toString();
        int versionCode = BuildConfig.VERSION_CODE;
        VersionUpdate = Version + "." + versionCode;
      //  VersionUpdate1 = Version1 + "." + versionCode;
        txt_version.setText("Version " + VersionUpdate);
        Log.i(TAG, "Version Code----" + VersionUpdate);
        ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        device_id.setText(ID);
        Log.i("deviceid", ID);
        calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        getdate =simpleDateFormat.format(calendar.getTime());
        Log.i(TAG,"get current date :"+getdate);


        sessionManager = new SessionManager(getApplicationContext());


      //  googleApiConnected();
       // hitLocation();

       imagesresponsecall();

        userNameMaterialEditText = findViewById(R.id.user_name);
        passwordMaterialEditText = findViewById(R.id.password);
        loginMainLinearLayout = findViewById(R.id.loginMainLinearLayout);

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
            } else {
                if (Objects.requireNonNull(userNameMaterialEditText.getText()).toString().trim().equalsIgnoreCase("")) {
                    Toasty.warning(getApplicationContext(), "Enter Phone Number", Toasty.LENGTH_LONG).show();

                } else if (Objects.requireNonNull(passwordMaterialEditText.getText()).toString().trim().equalsIgnoreCase("")) {
                    Toasty.warning(getApplicationContext(), "Enter Password", Toasty.LENGTH_LONG).show();
                } else {


                    Log.i(TAG, "loginButton latitude : " + latitude + " longitude : " + longitude);
//
//                    if (latitude >=0 && longitude >= 0) {
//// change form not equal to Zero to equal to Zero
//                        LoginResponseCall();
//                        //hitLocation();
//                    } else {
//                        googleApiConnected();
//                    }
                    LoginResponseCall();
                    //
                    //LoginUrl(ApiCall.API_URL + "login_access.php?empid=" + employeeMaterialEditText.getText().toString().replace(" ", "%20") + "&username=" + userNameMaterialEditText.getText().toString().replace(" ", "%20") + "&password=" + passwordMaterialEditText.getText().toString().replace(" ", "%20") + "&user_level=5");

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
private void ShowPopup()
{
    alertdialog = new Dialog(LoginActivity.this);
    alertdialog.setContentView(R.layout.loginpopup);
    alertdialog.setCancelable(false);
    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


    ImageView img_close = alertdialog.findViewById(R.id.img_close);




    img_close.setOnClickListener(v -> alertdialog.dismiss());
    Objects.requireNonNull(alertdialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    alertdialog.show();
}
    @SuppressLint("LogNotTimber")
    private void LoginResponseCall() {
        dialog = new Dialog(LoginActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<LoginResponse> call = apiInterface.LoginResponseCall(RestUtils.getContentType(), loginRequest());
        Log.i(TAG, "SignupResponse url  :%s" + " " + call.request().url().toString());


        call.enqueue(new Callback<LoginResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {

                Log.i(TAG, "SignupResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {


                                imeicode = response.body().getData().getImie_code();
                                location=response.body().getData().getLocation().replaceAll("\\s+","");

                           /* if (imeicode.equals(ID) ) {*/
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);


                                startActivity(intent);

                            Log.i("UserId", response.body().getData().getUser_id());

                            Log.i("_id", response.body().getData().get_id());

                                 userid = response.body().getData().get_id();
                                 String location = response.body().getData().getLocation().replaceAll("\\s+","");
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
                            Log.i("Mobile Number",""+usermobile);
                            Log.i("Location",""+location);
                            Log.i("Name",""+username);

                            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("mobile", usermobile);
                            editor.putString("location", location);
                            editor.putString("username", username);
                            editor.putString("agent_code", response.body().getData().getAgent_code().replaceAll("\\s+",""));
                            Log.i("Mobile Number 1",""+ usermobile);
                            Log.i("Location 1",""+location);
                            editor.apply();

                            attendanceCreateRequestCall(response.body().getData().get_id(), response.body().getData().getUser_name());
                            Log.i("UserId", response.body().getData().get_id());


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
         * user_password : 123                                                                                                                                                      45
         * last_login_time : 20-10-2021 11:00 AM
         */

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUser_id(userNameMaterialEditText.getText().toString().trim());
        loginRequest.setUser_password(passwordMaterialEditText.getText().toString());
        loginRequest.setLast_login_time(currentDateandTime);
        loginRequest.setDevice_id(ID);
        loginRequest.setLogin_lat(latitude1);
        loginRequest.setLogin_long(longitude1);
        loginRequest.setLogin_address(address);
//        if (latitude1!=null||latitude1.equals("0.0")||latitude1.equals("0")){
//            loginRequest.setLogin_lat(latitude1);
//        }
//        if (longitude1!=null||longitude1.equals("0.0")||longitude1.equals("0")){
//            loginRequest.setLogin_long(longitude1);
//        }else {
//            Toasty.warning(getApplicationContext(),"Please Check Location").show();
//        }
//        if (address!=null||address.equals("0.0")||address.equals("0")){
//            loginRequest.setLogin_address(address);
//        }else {
//            Toasty.warning(getApplicationContext(),"Please Check Location").show();
//        }



        Log.i(TAG, "loginRequest " + new Gson().toJson(loginRequest));
        return loginRequest;
    }

    private void imagesresponsecall(){
        APIInterface apiInterface  = RetrofitClient.getClient().create(APIInterface.class);
        Call<SafetyImageResponse> call = apiInterface.safeyimageresponsecall(RestUtils.getContentType(),imageSafetyRequest());
        Log.i(TAG,"safteyimage response url :"+call.request().url().toString());
        call.enqueue(new Callback<SafetyImageResponse>() {
            @Override
            public void onResponse(Call<SafetyImageResponse> call, Response<SafetyImageResponse> response) {
                Log.i("TAG","images safety response call  :"+new Gson().toJson(response.body()));

                if (response.body()!=null){
                    List<SafetyImageResponse.ImageData>  imageDataList  = response.body().getData();
                    if(response.body().getCode() == 200){
                        for ( SafetyImageResponse.ImageData imageData : imageDataList){
                            imageurl=imageData.getImageUrl();
                        }
                        Log.i(TAG,"Images are get this response :"+imageurl);
                        Picasso.get()
                                .load(imageurl)
                                .fit()
                                .into(safetyimage);
                    }
                }
            }

            @Override
            public void onFailure(Call<SafetyImageResponse> call, Throwable t) {
                Log.i(TAG,"API call failed: " + t.getMessage());
            }
        });
    }

    private ImageSafetyRequest imageSafetyRequest(){
        ImageSafetyRequest imageSafetyRequest=  new ImageSafetyRequest();
        imageSafetyRequest.setDate(getdate);
        Log.i(TAG,"imagesafety request data :"+new Gson().toJson(imageSafetyRequest));
        return imageSafetyRequest;
    }


    @SuppressLint("LogNotTimber")
    private void attendanceCreateRequestCall(String user_id, String user_name) {
        dialog = new Dialog(LoginActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<SuccessResponse> call = apiInterface.attendanceCreateRequestCall(RestUtils.getContentType(), attendanceCreateRequest(user_id, user_name));
        Log.i(TAG, "attendanceCreateRequest url  :%s" + " " + call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {

                Log.i(TAG, "attendance Response" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                        String currentDateandTime = sdf.format(new Date());
                        Log.i(TAG , "" + currentDateandTime);

                        CommonUtil.dbUtil.addDate(currentDateandTime);

                        Cursor cursor = CommonUtil.dbUtil.getDate();

                        Log.i(TAG," CoutnResponess---->" +  cursor.getCount());

                        if (cursor.moveToLast()){

                            @SuppressLint("Range")
                            String mydate = cursor.getString(cursor.getColumnIndex(DbHelper.CURRENT_DATE));
                            Log.i(TAG,"Respones----->"+mydate);
                        }

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("Location",location);
                        Log.i(TAG, "onResponse: Location------>"+location);

                        startActivity(intent);

                    } else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();

                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call, @NonNull Throwable t) {
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

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        String currentDate = simpleDateFormat.format(new Date());

        AttendanceCreateRequest attendanceCreateRequest = new AttendanceCreateRequest();
        attendanceCreateRequest.setUser_id(user_id);
        attendanceCreateRequest.setAttendance_name(user_name);
        attendanceCreateRequest.setAttendance_start_date(currentDate);
        attendanceCreateRequest.setAttendance_start_date_time(currentDateandTime);
        attendanceCreateRequest.setAttendance_start_lat(latitude);
        attendanceCreateRequest.setAttendance_start_long(longitude);
        attendanceCreateRequest.setAttendance_created_at(currentDateandTime);
        Log.i(TAG, "attendanceCreateRequest " + new Gson().toJson(attendanceCreateRequest));
        return attendanceCreateRequest;
    }


    @SuppressLint("LogNotTimber")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS_GPS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                case Activity.RESULT_CANCELED:
                    getMyLocation();
                    break;
            }
        }


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_schedule);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull String @org.jetbrains.annotations.NotNull [] permissions, @org.jetbrains.annotations.NotNull int @org.jetbrains.annotations.NotNull [] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    getMyLocation();

                }
            } else {
                Toast.makeText(getApplicationContext(), "permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getLatandLong() {
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {
                GPSTracker gps = new GPSTracker(getApplicationContext());
                // Check if GPS enabled
                if (gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    if (latitude != 0 && longitude != 0) {
                        LatLng latLng = new LatLng(latitude, longitude);
                        Log.i(TAG, "getLatandLong latitude : " + latitude + " longitude : " + longitude);


                    }


                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void googleApiConnected() {
        googleApiClient = new GoogleApiClient.Builder(Objects.requireNonNull(getApplicationContext())).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).build();
        googleApiClient.connect();

    }

    private void checkLocation() {
        try {
            LocationManager lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;

            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ignored) {
            }

            try {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ignored) {
            }

            if (!gps_enabled && !network_enabled) {

                if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    getMyLocation();
                }

            } else {
                getLatandLong();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        latitude = mLastLocation.getLatitude();
        longitude = mLastLocation.getLongitude();


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        permissionChecking();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @SuppressLint("LongLogTag")
    @Override
    public void onMapReady(GoogleMap googleMap) {


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
            latitude1 = "" + gpsTracker.getLatitude();
            longitude1= "" + gpsTracker.getLongitude();
            Log.i(TAG, "getMYLocation: latitude -> " + latitude1 + " longitude -> " + longitude1);
            if (nullPointerValidator(latitude1) && nullPointerValidator(longitude1)
                    && !latitude1.equalsIgnoreCase("0.0") && !longitude1.equalsIgnoreCase("0.0")) {
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
    private void permissionChecking() {
        if (getApplicationContext() != null) {
            if (Build.VERSION.SDK_INT >= 23 && (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 5);

            } else {

                checkLocation();
            }
        }
    }

    public void getMyLocation() {
        if (googleApiClient != null) {

            if (googleApiClient.isConnected()) {
                if (getApplicationContext() != null) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                }

                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                LocationRequest locationRequest = new LocationRequest();
                locationRequest.setInterval(2000);
                locationRequest.setFastestInterval(2000);
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
                builder.setAlwaysShow(true);
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
                PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
                result.setResultCallback(result1 -> {
                    Status status = result1.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied.
                            // You can initialize location requests here.
                            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                            Handler handler = new Handler();
                            int delay = 1000; //milliseconds

                            handler.postDelayed(new Runnable() {
                                @SuppressLint({"LongLogTag", "LogNotTimber"})
                                public void run() {
                                    //do something
                                    if (getApplicationContext() != null) {
                                        if (latitude != 0 && longitude != 0) {
                                            LatLng latLng = new LatLng(latitude, longitude);

                                            Log.i(TAG, "getMyLocation latitude : " + latitude + " longitude : " + longitude);

                                        }
                                    }
                                }
                            }, delay);


                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                status.startResolutionForResult(LoginActivity.this, REQUEST_CHECK_SETTINGS_GPS);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                });
            }


        }
    }


}