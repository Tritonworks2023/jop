package com.triton.johnsonapp.infopages;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.vision.text.Line;
import com.google.gson.Gson;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.db.CommonUtil;
import com.triton.johnsonapp.db.DbHelper;
import com.triton.johnsonapp.db.DbUtil;
import com.triton.johnsonapp.requestpojo.QRCodeListRequest;
import com.triton.johnsonapp.requestpojo.SubmitQRRequest;
import com.triton.johnsonapp.requestpojo.SubmitScanQRRequest;
import com.triton.johnsonapp.responsepojo.QRCodeListResponse;
import com.triton.johnsonapp.responsepojo.SuccessResponse;
import com.triton.johnsonapp.session.SessionManager;
import com.triton.johnsonapp.utils.ConnectionDetector;
import com.triton.johnsonapp.utils.RestUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QRCode_Activity extends AppCompatActivity {

    String TAG = "QRCODELIST";
    Button btn_Scan,btn_Submit;
    Context context;
    TextView txt_Totalcount, txt_Scancount,txt_Jobid,txt_Message;
    String userid,username,networkStatus = "",str_MaterialID, str_TotalCount,str_ScanCount,job_id,message,str_Message;
    private Dialog dialog;
    List<QRCodeListResponse.Datum> databeanList;
    RecyclerView recyclerView;
    QRAdapter qrAdapter;
    String qrlist, pre_Scan,myFuction;
    String myQRList;
    ArrayList<String> Ar_QR = new ArrayList<>();
    ArrayList<String> myAr_QR = new ArrayList<String>();
    SharedPreferences sharedPreferences;
    String[] str_QR;
    boolean flag = false;
    String str_Username,str_Mobile;
    String ValidationStatus;
    ImageView img_Back;
    AlertDialog alertDialog;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrscan_activity);
        context = this;

        CommonUtil.dbUtil = new DbUtil(context);
        CommonUtil.dbUtil.open();
        CommonUtil.dbHelper = new DbHelper(context);

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(SessionManager.KEY_ID);
        String useremailid = user.get(SessionManager.KEY_EMAILID);
        username = user.get(SessionManager.KEY_USERNAME);

        btn_Scan = findViewById(R.id.btn_scan);
        btn_Submit = findViewById(R.id.btn_submit);
        txt_Totalcount = findViewById(R.id.txt_totalcount);
        txt_Scancount = findViewById(R.id.txt_scancount);
        txt_Jobid = findViewById(R.id.txt_job_id);
        txt_Message = findViewById(R.id.txt_message);
        recyclerView = findViewById(R.id.recyclerView);
        img_Back = findViewById(R.id.img_back);

        btn_Submit.setText("BACK");

        Bundle extras = getIntent().getExtras();
        job_id = extras.getString("job_id");
        str_MaterialID = extras.getString("material");
        str_TotalCount = extras.getString("count");
        str_ScanCount = extras.getString("scount");
        Log.e("JobID " ,"" + job_id + " Material ID :" + str_MaterialID + " Total Count :" + str_TotalCount + " Scan Count :" + str_ScanCount);
        txt_Jobid.setText("Job ID : "+job_id);
        txt_Totalcount.setText("Total Counts : "+str_TotalCount);
        txt_Scancount.setText("Scanned Counts : "+str_ScanCount);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        str_Username = sharedPreferences.getString("username","name");
        str_Mobile = sharedPreferences.getString("mobile","1234567890");
        Log.e("User Name", "" + str_Username);
        Log.e("Mobile", "" + str_Mobile);


        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Toasty.warning(getApplicationContext(),"No Internet",Toasty.LENGTH_LONG).show();

        }
        else {
            fetchQRList();
        }

        CommonUtil.dbUtil.deleteQR();
        Cursor cursor = CommonUtil.dbUtil.getQRall();
        Log.e("QR Count", "" + cursor.getCount());

        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        btn_Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e(" Total Count ","" + "\n"+ str_TotalCount +"\n" +" Scan Count :" + str_ScanCount);

                if (str_ScanCount.equals(str_TotalCount)){

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(QRCode_Activity.this);
                    View mView = getLayoutInflater().inflate(R.layout.qrpopup, null);

                    ImageView img_Success,img_Failure,img_Alert;
                    TextView txt_Message,txt_Popup;
                    Button btn_Ok,btn_Back;
                    LinearLayout ll_Btn;

                    img_Failure = mView.findViewById(R.id.img_fail);
                    img_Success = mView.findViewById(R.id.img_success);
                    img_Alert = mView.findViewById(R.id.img_alert);
                    txt_Message = mView.findViewById(R.id.txt_message);
                    btn_Ok = mView.findViewById(R.id.btn_ok);
                    btn_Back = mView.findViewById(R.id.btn_back);
                    ll_Btn = mView.findViewById(R.id.ll_btn);

                    img_Alert.setVisibility(View.VISIBLE);
                    btn_Back.setVisibility(View.GONE);
                    txt_Message.setText("Count Limit Over");
                    ll_Btn.setVisibility(View.VISIBLE);
                    btn_Ok.setVisibility(View.VISIBLE);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);

                    btn_Ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.dismiss();
                        }
                    });

                }
                else{

                    Ar_QR = new ArrayList<>();

                    getQR();

                    ScanOptions options = new ScanOptions();
                    options.setPrompt("Volume up to flash on");
                    options.setBeepEnabled(true);
                    options.setOrientationLocked(true);
                    options.setCaptureActivity(CaptureAct.class);
                    barLaucher.launch(options);
                }
            }
        });

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View view) {

//                Cursor cursor = CommonUtil.dbUtil.getQR(job_id, str_MaterialID);
//                Log.e("QR Final Count", "" + cursor.getCount());
//                String count = String.valueOf(cursor.getCount());

//                submitAlert();

                onBackPressed();
            }
        });

    }

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    private void submitAlert() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(QRCode_Activity.this);
        View mView = getLayoutInflater().inflate(R.layout.submit_popup, null);

        TextView txt_Message;
        Button btn_Yes,btn_No;

        txt_Message = mView.findViewById(R.id.txt_message);
        btn_Yes = mView.findViewById(R.id.btn_yes);
        btn_No = mView.findViewById(R.id.btn_no);

        Cursor cursor = CommonUtil.dbUtil.getQR(job_id, str_MaterialID);
        Log.e("QR Final Count", "" + cursor.getCount());

        if (str_TotalCount.equals(String.valueOf(cursor.getCount()))){

            txt_Message.setText("Are You Sure Want to Submit?");
        }
        else {

            if (str_ScanCount.equals("0")){

                txt_Message.setText("Please Scan QR Code");
                btn_Yes.setVisibility(View.GONE);
                btn_No.setText("OK");
            }
            else{
                txt_Message.setText("Few of the QR Code not Scanned \n Are You Sure Want to Submit?");
            }

        }

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

        btn_Yes.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View view) {

                Ar_QR = new ArrayList<>();
                if (cursor.getCount()>0 && cursor.moveToFirst()){

                    do {
                        myQRList = cursor.getString(cursor.getColumnIndex(DbHelper.QR_CODE));
                        Log.e("My QR Codes",""+myQRList);
                        Ar_QR.add(myQRList);

                    }while (cursor.moveToNext());
                }
                else{

                    Log.e("Datasss",""+cursor);
                }

//                myAr_QR = new ArrayList<String>();
//                for (String item: Ar_QR) {
//                    myAr_QR.add("\""+item+"\"");
//                    myAr_QR.add(""+item+"");
//                }

                Log.e("QR CODES 1", String.valueOf(Ar_QR));
                pre_Scan = String.valueOf(Ar_QR);
                pre_Scan = pre_Scan.replaceAll("\\[", "").replaceAll("\\]","");
                Log.e("QR CODES", pre_Scan);


                networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
                if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

                    Toasty.warning(getApplicationContext(),"No Internet",Toasty.LENGTH_LONG).show();

                }
                else {

                  //  submitQRCall();
                }

                dialog.dismiss();
            }
        });

        btn_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });


    }

    private void getQR() {

        for (int i = 0; i < databeanList.size(); i++) {

            qrlist = qrlist + "," + databeanList.get(i).getQRCODE().toString();

        }

        qrlist = qrlist.replace("null", "");

        Ar_QR.add(qrlist);

        str_QR = qrlist.split(",");

        Ar_QR = new ArrayList<String>(
                Arrays.asList(str_QR));
        Ar_QR.remove(0);

        Log.e("List", "" + Ar_QR);
        Log.e("Size Value", "" + Ar_QR.size());

    }

    private void fetchQRList() {

        dialog = new Dialog(QRCode_Activity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        com.triton.johnsonapp.api.APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<QRCodeListResponse> call = apiInterface.GetQRListCall(RestUtils.getContentType(), qrcodelist());
        Log.w(TAG,"QR Code List url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<QRCodeListResponse>() {
            @Override
            public void onResponse(Call<QRCodeListResponse> call, Response<QRCodeListResponse> response) {

                Log.w(TAG,"QR Code List Response" + new Gson().toJson(response.body()));

                if (response.body() != null) {

                    dialog.dismiss();

                    message = response.body().getMessage();
                    Log.d("message", message);

                    if (200 == response.body().getCode()) {

                        dialog.dismiss();

                        if (response.body().getData() != null) {
                            databeanList = response.body().getData();

                            setView(databeanList);

                        }

                    } else if (400 == response.body().getCode()) {
                        dialog.dismiss();
                        if (response.body().getMessage() != null && response.body().getMessage().equalsIgnoreCase("There is already a user registered with this email id. Please add new email id")) {


                        }
                    } else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<QRCodeListResponse> call, Throwable t) {

                Log.e("On Failure ", "--->" + t.getMessage());
            }
        });
    }

    private void setView(List<QRCodeListResponse.Datum> databeanList) {

        Log.e("Nish",""+databeanList.size());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        qrAdapter = new QRAdapter(getApplicationContext(), databeanList,job_id);
        recyclerView.setAdapter(qrAdapter);
    }

    private QRCodeListRequest qrcodelist() {

        QRCodeListRequest qrCodeListRequest = new QRCodeListRequest();
        qrCodeListRequest.setJOBNO(job_id);
        qrCodeListRequest.setMATL_ID(Integer.valueOf(str_MaterialID));

        Log.w(TAG,"Get MaterialList Request "+ new Gson().toJson(qrCodeListRequest));
        return qrCodeListRequest;

    }

    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result-> {
        if(result.getContents() !=null)
        {
            if (result.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
            else {

                str_Message = result.getContents();

                alert();

            }
//                AlertDialog.Builder builder = new AlertDialog.Builder(QRCode_Activity.this);
//                builder.setTitle("Result");
//                builder.setMessage(result.getContents());
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i)
//                    {
//                        txt_Message.setText(result.getContents());
//                        str_Message =  txt_Message.getText().toString();
//                        Log.e("Message",""+ str_Message);
//
//                        Log.e("Size Value", "" + Ar_QR.size());
//                        Log.e("List",""+Ar_QR);
//
//                        if (Ar_QR.contains(str_Message)){
//
//                            Log.e("Hi" ,"Success");
//                            Ar_QR.clear();
//                            Log.e("Size Value", "" + Ar_QR.size());
//                        }else{
//
//                            Log.e("Hi" ,"Failed");
//                            Ar_QR.clear();
//                            Log.e("Size Value", "" + Ar_QR.size());
//                        }
//                        dialogInterface.dismiss();
//                    }
//                }).show();
//
        }
    });

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    private void alert() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(QRCode_Activity.this);
        View mView = getLayoutInflater().inflate(R.layout.qrpopup, null);

        ImageView img_Success,img_Failure,img_Alert;
        TextView txt_Message,txt_Popup;
        Button btn_Ok,btn_Back,btn_Yes,btn_No;
        LinearLayout ll_Btn,ll_Submit;

        img_Failure = mView.findViewById(R.id.img_fail);
        img_Success = mView.findViewById(R.id.img_success);
        img_Alert = mView.findViewById(R.id.img_alert);
        txt_Message = mView.findViewById(R.id.txt_message);
        btn_Ok = mView.findViewById(R.id.btn_ok);
        btn_Back = mView.findViewById(R.id.btn_back);
        txt_Popup = mView.findViewById(R.id.txt_popup);
        ll_Btn = mView.findViewById(R.id.ll_btn);
        ll_Submit = mView.findViewById(R.id.ll_submit);
        btn_Yes = mView.findViewById(R.id.btn_yes);
        btn_No = mView.findViewById(R.id.btn_no);

        Log.e("Size Value", "" + Ar_QR.size());
        Log.e("List",""+Ar_QR);

        if (Ar_QR.contains(str_Message)){

            Log.e("Hi" ,"Success");
            Ar_QR.clear();
            Log.e("Size Value", "" + Ar_QR.size());

            Log.e("Job ID",""+job_id);
            Log.e("Message",""+str_Message);

            if (CommonUtil.dbUtil.hasQR(str_Message,job_id,str_MaterialID)){
                Log.e("Hi Nish","Had Data");
                Cursor cursor = CommonUtil.dbUtil.getQR(job_id, str_MaterialID);
                Log.e("QR Count", "" + cursor.getCount());

                txt_Message.setText("Duplicate QR Code");
                img_Alert.setVisibility(View.VISIBLE);
                ll_Btn.setVisibility(View.VISIBLE);
                btn_Ok.setVisibility(View.VISIBLE);

            }
            else{
                Log.e("Hi Nish","No Data");
                CommonUtil.dbUtil.addQR(str_Message,job_id,str_MaterialID);
                Cursor cursor = CommonUtil.dbUtil.getQR(job_id,str_MaterialID);
                Log.e("QR Count", "" + cursor.getCount());

                txt_Message.setText("Valid QR Code");
                ValidationStatus = txt_Message.getText().toString();
                Log.e("A", "" + ValidationStatus);
                img_Success.setVisibility(View.VISIBLE);
                ll_Btn.setVisibility(View.VISIBLE);
                btn_Ok.setVisibility(View.VISIBLE);

                str_ScanCount = String.valueOf(cursor.getCount());
                txt_Scancount.setText("Scanned Counts : "+str_ScanCount);

                submitScanQRCall();

            }
        }

        else{

                Log.e("Hi" ,"Failed");
                Ar_QR.clear();
                Log.e("Size Value", "" + Ar_QR.size());
                txt_Message.setText("Invalid QR Code.! \n Are You Sure Want to Submit?");
                ValidationStatus = txt_Message.getText().toString();
                Log.e("B", "" + ValidationStatus);
                img_Failure.setVisibility(View.VISIBLE);
                btn_Back.setVisibility(View.GONE);
                ll_Submit.setVisibility(View.VISIBLE);

//                submitScanQRCall();

        }



        mBuilder.setView(mView);
        alertDialog= mBuilder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);

        btn_Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CommonUtil.dbUtil.addQR(str_Message,job_id,str_MaterialID);
                Cursor cursor = CommonUtil.dbUtil.getQR(job_id,str_MaterialID);
                Log.e("QR Count", "" + cursor.getCount());

                str_ScanCount = String.valueOf(cursor.getCount());
                txt_Scancount.setText("Scanned Counts : "+str_ScanCount);

                submitScanQRCall();

            }
        });

        btn_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
            }
        });

        btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();

            }
        });

        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    private void submitScanQRCall() {

        dialog = new Dialog(QRCode_Activity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();


        com.triton.johnsonapp.api.APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<SuccessResponse> call = apiInterface.SubmitScanQRCall(RestUtils.getContentType(), submitscanqr());
        Log.w(TAG,"Submit QR Code url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {

                Log.w(TAG,"Submit QR Response" + new Gson().toJson(response.body()));

                dialog.dismiss();

                if (response.body() != null){

                    message = response.body().getMessage();

                    if (response.body().getCode() == 200){

                        if (!Objects.equals(ValidationStatus, "Valid QR Code")){

                            alertDialog.dismiss();

                        }

                    }
                    else{

                        dialog.dismiss();
                    }
                }
                else{

                    dialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {

                dialog.dismiss();
                Log.e("Submit QR On Failure ", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private SubmitScanQRRequest submitscanqr() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());

        Log.e("Validation Status",""+ ValidationStatus);

        SubmitScanQRRequest submitscanqr = new SubmitScanQRRequest();
        submitscanqr.setJob_id(job_id);
        submitscanqr.setMat_id(str_MaterialID);
        submitscanqr.setUser_mobile_no(str_Mobile);
        submitscanqr.setUser_name(str_Username);
        submitscanqr.setQrcode(str_Message);
        submitscanqr.setCurrent_date(currentDateandTime);

        if (ValidationStatus.equals("Valid QR Code")){

            submitscanqr.setValidation_status("Y");
        }
        else{
            submitscanqr.setValidation_status("N");
        }

        Log.w(TAG,"Submit QR Request "+ new Gson().toJson(submitscanqr));
        return submitscanqr;
    }

    private void submitQRCall() {

        dialog = new Dialog(QRCode_Activity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        com.triton.johnsonapp.api.APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<SuccessResponse> call = apiInterface.SubmitQRListCall(RestUtils.getContentType(), submitqrlist());
        Log.w(TAG,"Submit QR Code url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {

                Log.w(TAG,"Submit QR Response" + new Gson().toJson(response.body()));

                if (response.body() != null){

                    message = response.body().getMessage();

                    if (response.body().getCode() ==200){

                        dialog.dismiss();

                        if (response.body().getData() !=  null){

                            Log.e("Message",message);

//                            Intent send = new Intent(context, MaterialList_JointInspection_Activity.class);
//                            startActivity(send);
                            onBackPressed();
                        }
                    }
                    else{
                        dialog.dismiss();

                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                dialog.dismiss();

                Log.e("Submit QR On Failure ", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private SubmitQRRequest submitqrlist() {

        SubmitQRRequest submitqr = new SubmitQRRequest();
        submitqr.setJob_id(job_id);
        submitqr.setMat_id(str_MaterialID);
        submitqr.setQrcode(Collections.singletonList(pre_Scan));
        Log.w(TAG,"Submit QR Request "+ new Gson().toJson(submitqr));
        return submitqr;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
