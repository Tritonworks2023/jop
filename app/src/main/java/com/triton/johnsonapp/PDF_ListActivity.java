package com.triton.johnsonapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.johnsonapp.activity.ActivityStatusActivity;
import com.triton.johnsonapp.activity.JobDetailActivity;
import com.triton.johnsonapp.activitybased.ABCustomerDetailsActivity;
import com.triton.johnsonapp.adapter.JobDetailListAdapter;
import com.triton.johnsonapp.adapter.PDFListAdapter;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.requestpojo.JobNoManagementRequest;
import com.triton.johnsonapp.requestpojo.PDFListRequest;
import com.triton.johnsonapp.responsepojo.JobNoManagementResponse;
import com.triton.johnsonapp.responsepojo.PDFListResponse;
import com.triton.johnsonapp.utils.RestUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PDF_ListActivity extends AppCompatActivity {

    Dialog dialog;
    String message;
    ProgressDialog mProgressDialog;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_pdflist)
    RecyclerView rv_pdflist;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;
    String job_id,UKEY;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_pdf_list);

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            job_id = extras.getString("job_id");
        }
        if (extras != null) {
            UKEY = extras.getString("UKEY");
        }

       SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(PDF_ListActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("job_id", job_id);
        editor.putString("UKEY", UKEY);
        editor.apply();

        pdflistResponseCall();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }

    private void pdflistResponseCall() {
//        dialog = new Dialog(PDF_ListActivity.this, R.style.NewProgressDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.progroess_popup);
//        dialog.show();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please Wait PDF Loading...");
        mProgressDialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<PDFListResponse> call = apiInterface.pdflistResponseCall(RestUtils.getContentType(), pdflistRequest());
        Log.w(TAG,"JobNoManagementResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<PDFListResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<PDFListResponse> call, @NonNull Response<PDFListResponse> response) {

                Log.w(TAG,"JobNoManagementResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){

//                            dialog.dismiss();
                            mProgressDialog.dismiss();
                            List<PDFListResponse.DataBean> dataBeanList = response.body().getData();


                            if(dataBeanList != null && dataBeanList.size()>0){
                                rv_pdflist.setVisibility(View.VISIBLE);
                                setView(dataBeanList);

                                txt_no_records.setVisibility(View.GONE);
                            }

                            else {
                                rv_pdflist.setVisibility(View.GONE);
                                txt_no_records.setVisibility(View.VISIBLE);

                                txt_no_records.setText("No PDF Detail Available");
                            }

                        }


                    } else {
                       // dialog.dismiss();
                        mProgressDialog.dismiss();
                        Toasty.warning(getApplicationContext(),""+message,Toasty.LENGTH_LONG).show();

                        //showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<PDFListResponse> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Log.e("JobNoManagementResponse", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private PDFListRequest pdflistRequest() {

        PDFListRequest pdflistRequest = new PDFListRequest();
        pdflistRequest.setJob_no(job_id);

        Log.w(TAG,"JobNoManagementRequest "+ new Gson().toJson(pdflistRequest));
        return pdflistRequest;
    }

    @SuppressLint("LogNotTimber")
    private void setView(List<PDFListResponse.DataBean> dataBeanList) {
        rv_pdflist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rv_pdflist.setItemAnimator(new DefaultItemAnimator());
        PDFListAdapter jobDetailListAdapter = new PDFListAdapter(this, dataBeanList);
        rv_pdflist.setAdapter(jobDetailListAdapter);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PDF_ListActivity.this, ABCustomerDetailsActivity.class);
        intent.putExtra("UKEY",UKEY);
        intent.putExtra("job_id",job_id);
        intent.putExtra("job_detail_no",job_id);
        startActivity(intent);
        overridePendingTransition(R.anim.new_right, R.anim.new_left);
    }
}