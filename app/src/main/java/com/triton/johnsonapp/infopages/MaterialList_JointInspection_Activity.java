package com.triton.johnsonapp.infopages;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.requestpojo.JobFetchAddressRequest;
import com.triton.johnsonapp.responsepojo.MaterialList_JointInspectionResponse;
import com.triton.johnsonapp.session.SessionManager;
import com.triton.johnsonapp.utils.ConnectionDetector;
import com.triton.johnsonapp.utils.RestUtils;

import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaterialList_JointInspection_Activity extends AppCompatActivity {

    String TAG = "JobInfoJointInspection";
    Context context;
    ImageView img_Back;
    TextView txt_JobDetails,txt_no_records;
    RecyclerView recyclerView;
    String job_id,UKEY,userid,username,networkStatus="",message;
    List<MaterialList_JointInspectionResponse.Datum> databeanList;
    MaterialList_Adapter materialApdater;
    private Dialog dialog;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materiallist_jointinspection);
        context = this;

        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userid = user.get(SessionManager.KEY_ID);
        String useremailid = user.get(SessionManager.KEY_EMAILID);
        username = user.get(SessionManager.KEY_USERNAME);

        img_Back = findViewById(R.id.img_back);
        txt_JobDetails = findViewById(R.id.txt_job_id);
        recyclerView = findViewById(R.id.recyclerView);
        txt_no_records = findViewById(R.id.txt_no_records);

        Bundle extras = getIntent().getExtras();
        job_id = extras.getString("job_id");
        Log.e("JobID","" + job_id);
        UKEY = extras.getString("UKEY");
        Log.w(TAG, "UKEYyy -->" + UKEY);
        txt_JobDetails.setText("Job ID : "+job_id);

        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Toasty.warning(getApplicationContext(),"No Internet",Toasty.LENGTH_LONG).show();

        }
        else {
            fetchMaterialList();
        }

        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void fetchMaterialList() {
        dialog = new Dialog(MaterialList_JointInspection_Activity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        com.triton.johnsonapp.api.APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<MaterialList_JointInspectionResponse> call = apiInterface.MaterialListJointInspectionCall(RestUtils.getContentType(), materialListrequest());
        Log.w(TAG,"Get MaterialList url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<MaterialList_JointInspectionResponse>() {
            @Override
            public void onResponse(Call<MaterialList_JointInspectionResponse> call, Response<MaterialList_JointInspectionResponse> response) {

                Log.w(TAG,"Get MaterialList Response" + new Gson().toJson(response.body()));

                dialog.dismiss();

                if (response.body() != null) {

                    message = response.body().getMessage();
                    Log.d("message", message);

                    if (200 == response.body().getCode()) {

                        dialog.dismiss();

                        if (response.body().getData() != null) {
                            databeanList = response.body().getData();

                            if (databeanList.size() == 0){

                                recyclerView.setVisibility(View.GONE);
                                txt_no_records.setVisibility(View.VISIBLE);
                                txt_no_records.setText("No Records Found");

                            }

                            setView(databeanList);
                            Log.d("dataaaaa", String.valueOf(databeanList));

                        }

                    }
                    else if (400 == response.body().getCode()) {
                        dialog.dismiss();
                        if (response.body().getMessage() != null && response.body().getMessage().equalsIgnoreCase("There is already a user registered with this email id. Please add new email id")) {

                            recyclerView.setVisibility(View.GONE);
                            txt_no_records.setVisibility(View.VISIBLE);
                            txt_no_records.setText("Error 404 Found");
                        }
                    } else {
                        dialog.dismiss();
                        recyclerView.setVisibility(View.GONE);
                        txt_no_records.setVisibility(View.VISIBLE);
                        txt_no_records.setText("Error 404 Found");
                        // Toasty.warning(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_LONG).show();
                    }
                }
                else{
                    dialog.dismiss();
                    recyclerView.setVisibility(View.GONE);
                    txt_no_records.setVisibility(View.VISIBLE);
                    txt_no_records.setText("Error 404 Found");
                }
            }

            @Override
            public void onFailure(Call<MaterialList_JointInspectionResponse> call, Throwable t) {

                dialog.dismiss();
                Log.e("JobList On Failure ", "--->" + t.getMessage());
                // Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.GONE);
                txt_no_records.setVisibility(View.VISIBLE);
                txt_no_records.setText("No Records Found");
            }
        });
    }

    private void setView(List<MaterialList_JointInspectionResponse.Datum> databeanList) {

        Log.e("Nish",""+databeanList.size());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        materialApdater = new MaterialList_Adapter(getApplicationContext(), databeanList,job_id);
        recyclerView.setAdapter(materialApdater);
    }

    private JobFetchAddressRequest materialListrequest() {

        JobFetchAddressRequest jobFetchAddressRequest = new JobFetchAddressRequest();
        jobFetchAddressRequest.setJob_id(job_id);

        Log.w(TAG,"Get MaterialList Request "+ new Gson().toJson(jobFetchAddressRequest));
        return jobFetchAddressRequest;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
