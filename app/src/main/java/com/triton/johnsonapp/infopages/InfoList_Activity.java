package com.triton.johnsonapp.infopages;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.VolleyLog;
import com.google.gson.Gson;
import com.triton.johnsonapp.Forms.InputValueFormListActivity_JOP;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.requestpojo.Basemant_updateRequest;
import com.triton.johnsonapp.requestpojo.JobFetchAddressRequest;
import com.triton.johnsonapp.responsepojo.Basemant_updateResponse;
import com.triton.johnsonapp.responsepojo.LiftwellInfo_Response;
import com.triton.johnsonapp.session.SessionManager;
import com.triton.johnsonapp.utils.ConnectionDetector;
import com.triton.johnsonapp.utils.RestUtils;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

    public class InfoList_Activity  extends AppCompatActivity {

    private String TAG ="ViewInfoDetailsActivity";

    String userid,username,UKEY,user_id;
    String networkStatus = "",message,job_id,status,service_id,group_id,activity_id,job_detail_no,subgroup_id,fromactivity,activity_ukey,UKEY_DESC,work_status;
    int form_type,new_count,pause_count;
    Button btn_GoBack, btn_continue;
    private Dialog alertDialog;
    ImageView img_Back;
    EditText floor_value,base_value;

    private Dialog dialog;


    LiftwellInfo_Response.DataBean dataBean;

    TextView txt_BrCode, txt_Jobno, txt_Cusname, txt_Ecno, txt_CustAd, txt_InsAd, txt_Nounits, txt_Dmodel, txt_Loadspeed,
                txt_Speed, txt_Floors, txt_Nocarent, txt_Liftwell, txt_Landent, txt_ClrOpen, txt_Framesize,txt_Jobid,txt_cartype,txt_carentrance,txt_flrdispchar;

    String str_BrCode, str_Jobno, str_Cusname, str_Ecno, str_CustAd, str_InsAd, str_Nounits, str_Dmodel,str_Loadspeed,
            str_Speed, str_Floors, str_Nocarent, str_Liftwell, str_Landent, str_ClrOpen, str_Framesize,str_Jobid,str_cartype,str_carentrance,str_flrdispchar;

    Context context;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infolist);
        context =  this;

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(SessionManager.KEY_ID);
        String useremailid = user.get(SessionManager.KEY_EMAILID);

        username = user.get(SessionManager.KEY_USERNAME);
        user_id = user.get(SessionManager.KEY_USERID);

       Log.w("user_id",user_id);

        txt_BrCode = findViewById(R.id.txt_brcode);
        txt_Jobno = findViewById(R.id.txt_jobno);
        txt_Cusname = findViewById(R.id.txt_cusno);
        txt_Ecno = findViewById(R.id.txt_ecno);
        txt_CustAd = findViewById(R.id.txt_custaddress);
        txt_InsAd = findViewById(R.id.txt_insaddress);
        txt_Nounits = findViewById(R.id.txt_nounits);
        txt_Dmodel = findViewById(R.id.txt_dmodel);
        txt_Loadspeed = findViewById(R.id.txt_loadspeed);
        txt_Speed = findViewById(R.id.txt_speed);
        txt_Floors = findViewById(R.id.txt_floors);
        txt_Nocarent = findViewById(R.id.txt_nocarenet);
        txt_Liftwell = findViewById(R.id.txt_liftwell);
        txt_Landent = findViewById(R.id.txt_landentrance);
        txt_ClrOpen = findViewById(R.id.txt_clropen);
        txt_Framesize = findViewById(R.id.txt_framesize);
        btn_GoBack = findViewById(R.id.btn_goback);
        btn_continue = findViewById(R.id.btn_continue);
        txt_Jobid = findViewById(R.id.txt_job_id);
        txt_cartype = findViewById(R.id.txt_cartype);
        txt_carentrance = findViewById(R.id.txt_carentrance);
        txt_flrdispchar = findViewById(R.id.txt_flrdispchar);
        img_Back = findViewById(R.id.img_back);
        base_value = findViewById(R.id.base_value);
        floor_value = findViewById(R.id.floor_value);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            job_id = extras.getString("job_id");
            Log.w(TAG,"job_id : "+job_id);
            txt_Jobid.setText("Job ID :"+job_id);
            service_id = extras.getString("service_id");
            group_id = extras.getString("group_id");
            activity_id = extras.getString("activity_id");
            job_detail_no = extras.getString("job_detail_no");
            status = extras.getString("status");
            subgroup_id = extras.getString("subgroup_id");
            fromactivity = extras.getString("fromactivity");
            activity_ukey = extras.getString("UKEY");
            form_type = extras.getInt("form_type");
            UKEY_DESC = extras.getString("UKEY_DESC");
            work_status = extras.getString("work_status");
            new_count = extras.getInt("new_count");
            pause_count = extras.getInt("pause_count");
            UKEY = extras.getString("UKEY");
        }

        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Toasty.warning(getApplicationContext(),"No Internet",Toasty.LENGTH_LONG).show();

        }
        else {
            ViewInfoRequestCall();
        }

        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });


        btn_GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), InputValueFormListActivity_JOP.class);
                intent.putExtra("activity_id", activity_id);
                intent.putExtra("job_id", job_id);
                intent.putExtra("group_id", group_id);
                Log.w(TAG, "gROUP ID---" + group_id);
                intent.putExtra("group_detail_name", "");
                intent.putExtra("subgroup_id", "");
                intent.putExtra("status", status);
                intent.putExtra("fromactivity", TAG);
                intent.putExtra("UKEY", UKEY);
                intent.putExtra("UKEY_DESC", UKEY_DESC);
                intent.putExtra("job_detail_no", job_detail_no);
                intent.putExtra("new_count", new_count);
                intent.putExtra("pause_count", pause_count);
                intent.putExtra("form_type", form_type);
                startActivity(intent);


//             if (base_value.getText().toString().trim().equals("")) {
//                    showErrorLoading("Please Enter Basement Floor");
//                }
//                else if (floor_value.getText().toString().trim().equals("")) {
//                    showErrorLoading("Please Enter Excluding Basement Floor");
//                }
//                else {
//                 basemant_update();
//             }
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void basemant_update() {

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<Basemant_updateResponse> call = apiInterface.basemant_updateResponseCall(RestUtils.getContentType(), basemant_updateRequest());
        Log.w(VolleyLog.TAG,"SignupResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<Basemant_updateResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<Basemant_updateResponse> call, @NonNull retrofit2.Response<Basemant_updateResponse> response) {

                Log.w(VolleyLog.TAG, "SignupResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();

                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {

                            Log.d("msg", message);

                            Toasty.success(getApplicationContext(), message, Toasty.LENGTH_LONG).show();

                            Intent intent = new Intent(getApplicationContext(), InputValueFormListActivity_JOP.class);
                            intent.putExtra("activity_id", activity_id);
                            intent.putExtra("job_id", job_id);
                            intent.putExtra("group_id", group_id);
                            Log.w(TAG, "gROUP ID---" + group_id);
                            intent.putExtra("group_detail_name", "");
                            intent.putExtra("subgroup_id", "");
                            intent.putExtra("status", status);
                            intent.putExtra("fromactivity", TAG);
                            intent.putExtra("UKEY", UKEY);
                            intent.putExtra("UKEY_DESC", UKEY_DESC);
                            intent.putExtra("job_detail_no", job_detail_no);
                            intent.putExtra("new_count", new_count);
                            intent.putExtra("pause_count", pause_count);
                            intent.putExtra("form_type", form_type);
                            startActivity(intent);

                        }
                        else {
                            Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();

                        }
                    }


                }
            }

            @Override
            public void onFailure(@NonNull Call<Basemant_updateResponse> call, @NonNull Throwable t) {
                Log.e("OTP", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private Basemant_updateRequest basemant_updateRequest() {

        String str_base = base_value.getText().toString();
        String str_floor = floor_value.getText().toString();

        Basemant_updateRequest custom = new Basemant_updateRequest();
        custom.setJob_no(job_id);
        custom.setUser_mobile_no(user_id);
        custom.setUkey(UKEY);
        custom.setBase_value(str_base);
        custom.setFloor_value(str_floor);
        Log.w(VolleyLog.TAG,"loginRequest "+ new Gson().toJson(custom));
        return custom;
    }


    private void ViewInfoRequestCall() {

        dialog = new Dialog(InfoList_Activity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<LiftwellInfo_Response> call = apiInterface.LiftWellInfoCall(RestUtils.getContentType(), jobFetchAddressRequest());
        Log.w(TAG,"Liftwell Info url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<LiftwellInfo_Response>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<LiftwellInfo_Response> call, Response<LiftwellInfo_Response> response) {

                Log.w(TAG,"Liftwell Info Response" + new Gson().toJson(response.body()));

                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {

                        dataBean = response.body().getData();
                        dialog.dismiss();

                        if (dataBean.getBRCODE()!= null){

                            txt_BrCode.setText( dataBean.getBRCODE());
                            txt_BrCode.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }

                        if (dataBean.getJOBNO()!= null){

                            txt_Jobno.setText( dataBean.getJOBNO());
                            txt_Jobno.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }

                        if (dataBean.getCUSNAME()!= null){

                            txt_Cusname.setText( dataBean.getCUSNAME());
                            txt_Cusname.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }

                        if (dataBean.getECNO()!= null){

                            txt_Ecno.setText( dataBean.getECNO());
                            txt_Ecno.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }

                        if (dataBean.getBRCODE()!= null){

                            txt_BrCode.setText( dataBean.getBRCODE());
                            txt_BrCode.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }

                        if (dataBean.getCUST_ADDRESS()!= null){


                            Log.e("Nish",""+dataBean.getCUST_ADDRESS());
                            txt_CustAd.setText( dataBean.getCUST_ADDRESS());
                            txt_CustAd.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }

                        if (dataBean.getINS_ADDRESS()!= null){

                            txt_InsAd.setText(dataBean.getINS_ADDRESS());
                            txt_InsAd.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }

                        if (dataBean.getNOUNITS()!= null){

                            txt_Nounits.setText(dataBean.getNOUNITS());
                            txt_Nounits.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }

                        if (dataBean.getDMODEL()!= null){

                            txt_Dmodel.setText(dataBean.getDMODEL());
                            txt_Dmodel.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }

                        if (dataBean.getLOAD_SPEED()!= null){

                            txt_Loadspeed.setText(dataBean.getLOAD_SPEED());
                            txt_Loadspeed.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }

                        if (dataBean.getSPEED()!= null){

                            txt_Speed.setText(dataBean.getSPEED());
                            txt_Speed.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }

                        if (dataBean.getBRCODE()!= null){

                            txt_BrCode.setText(dataBean.getBRCODE());
                            txt_BrCode.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }

                        if (dataBean.getFLOORS()!= null){

                            txt_Floors.setText(dataBean.getFLOORS());
                            txt_Floors.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }

                        if (dataBean.getNOCARENT()!= null){

                            txt_Nocarent.setText(dataBean.getNOCARENT());
                            txt_Nocarent.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }

                        if (dataBean.getLIFTWELL()!= null){

                            txt_Liftwell.setText( dataBean.getLIFTWELL());
                            txt_Liftwell.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }

                        if (dataBean.getLAND_ENTRANCE()!= null){

                            txt_Landent.setText(dataBean.getLAND_ENTRANCE());
                            txt_Landent.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }

                        if (dataBean.getCLR_OPEN()!= null){

                            txt_ClrOpen.setText(dataBean.getCLR_OPEN());
                            txt_ClrOpen.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }

                        if (dataBean.getFRAME_SIZE()!= null){

                            txt_Framesize.setText(dataBean.getFRAME_SIZE());
                            txt_Framesize.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }

                        if (dataBean.getCARTYPE()!= null){

                            txt_cartype.setText(dataBean.getCARTYPE());
                            txt_cartype.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }

                        if (dataBean.getCARENTRANCE()!= null){

                            txt_carentrance.setText(dataBean.getCARENTRANCE());
                            txt_carentrance.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }

                        if (dataBean.getFLRDISPCHAR()!= null){

                            txt_flrdispchar.setText(dataBean.getFLRDISPCHAR());
                            txt_flrdispchar.setTextColor(ContextCompat.getColor(context, R.color.black));
                        }


                    } else {
                        dialog.dismiss();

                    }
                }
            }

            @Override
            public void onFailure(Call<LiftwellInfo_Response> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private JobFetchAddressRequest jobFetchAddressRequest() {
        JobFetchAddressRequest jobFetchAddressRequest = new JobFetchAddressRequest();
        jobFetchAddressRequest.setJob_id(job_id);

        Log.w(TAG,"Liftwell info Request "+ new Gson().toJson(jobFetchAddressRequest));
        return jobFetchAddressRequest;
    }

    public void showErrorLoading(String errormesage){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(errormesage);
        alertDialogBuilder.setPositiveButton("ok",
                (arg0, arg1) -> hideLoading());


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void hideLoading(){
        try {
            alertDialog.dismiss();
        }catch (Exception ignored){

        }
    }

}
