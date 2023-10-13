package com.triton.johnsonapp.worksheet_joblist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.WorksheetActivity;
import com.triton.johnsonapp.Worksheet_table_AddActivity;
import com.triton.johnsonapp.adapter.Worksheet_jobno_adapter.JobDetailList_WorksheetAdapter;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.requestpojo.ActivityListManagementRequest;
import com.triton.johnsonapp.requestpojo.Worksheet_jobno_listRequest;
import com.triton.johnsonapp.responsepojo.JobNoManagementResponse;
import com.triton.johnsonapp.responsepojo.Worksheet_jobno_listResponse;
import com.triton.johnsonapp.session.SessionManager;
import com.triton.johnsonapp.utils.ConnectionDetector;
import com.triton.johnsonapp.utils.RestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Worksheet_jobno_detailsActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_jobdetaillist)
    RecyclerView rv_jobdetaillist;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    Dialog dialog;

    String networkStatus = "", message, activity_id;

    int number = 0;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_back)
    ImageView img_back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edt_search)
    EditText edt_search;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.img_clearsearch)
    ImageView img_clearsearch;

    private String search_string = "";
    SessionManager session;
    private String status;

    private int currentPage = 1;
    private int ITEM_PER_PAGE = 50; // Number of items to load per page
    private int TOTAL_NUMBER_OF_ITEMS; // Total number of items available from the API
    private int ITEMS_REMAINING; // Number of items remaining to be loaded
    private int totalpages ; // Total number of pages available

    private boolean isLoading = false; // Flag to track if data is being loaded
    private boolean isLastPage = false;

    private static final int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private GoogleApiClient googleApiClient;
    Location mLastLocation;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private double latitude;
    private double longitude;
    private String TAG = "AllJobListActivity";
    private String locationid;
    List<Worksheet_jobno_listResponse.DataBean> dataBeanList;
    JobDetailList_WorksheetAdapter jobDetailListAdapter;
    String date,br_code,emp_no,prepby,emp_activity,workhrs,km,remark,str_edit,str_edit_date,str_emp_name,str_status,recycler_count,job_dummy,dummy_emp_act;
    int hrs_count;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_worksheet_jobno_details);

        ButterKnife.bind(this);


        SessionManager session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();


        locationid=user.get(SessionManager.KEY_LOCATION);
        Log.e("Alljoblocation", "onCreate: "+user );

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            date = extras.getString("date");
        }
        if (extras != null) {
            br_code = extras.getString("br_code");
        }
        if (extras != null) {
            prepby = extras.getString("prepby");
        }
        if (extras != null) {
            emp_no = extras.getString("emp_no");
        }
        if (extras != null) {
            emp_activity = extras.getString("emp_activity");
        }
        if (extras != null) {
            workhrs = extras.getString("workhrs");
        }
        if (extras != null) {
            km = extras.getString("km");
        }
        if (extras != null) {
            remark = extras.getString("remark");
        }
        if (extras != null) {
            str_edit = extras.getString("btn_edit");
        }
        if (extras != null) {
            str_edit_date = extras.getString("edit_date");
        }
        if (extras != null) {
            str_emp_name = extras.getString("emp_name");
        }
        if (extras != null) {
            str_status = extras.getString("status");
        }
        if (extras != null) {
            hrs_count = extras.getInt("hrs_count");
        }
        if (extras != null) {
            recycler_count = extras.getString("recycler_count");
        }
        if (extras != null) {
            job_dummy = extras.getString("job_dummy");
        }
        if (extras != null) {
            dummy_emp_act = extras.getString("dummy_emp_act");
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("date", date);
        editor.putString("br_code", br_code);
        editor.putString("prepby", prepby);
        editor.putString("emp_no", emp_no);
        editor.putString("emp_activity", emp_activity);
        editor.putString("workhrs", workhrs);
        editor.putString("km", km);
        editor.putString("remark", remark);
        editor.putString("btn_edit", str_edit);
        editor.putString("edit_date", str_edit_date);
        editor.putString("emp_name", str_emp_name);
        editor.putString("status", str_status);
        editor.putInt("hrs_count",hrs_count);
        editor.putString("recycler_count",recycler_count);
        editor.putString("job_dummy",job_dummy);
        editor.putString("dummy_emp_act",dummy_emp_act);
        editor.apply();

        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                img_clearsearch.setVisibility(View.VISIBLE);
                String Searchvalue = edt_search.getText().toString();
                Log.w(TAG, "Search Value---" + Searchvalue);
                filter(Searchvalue);
                return false;
            }
        });

//        rv_jobdetaillist.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
//                int totalItemCount = layoutManager.getItemCount();
//
//                if (!isLoading && !isLastPage && lastVisibleItemPosition == totalItemCount - 1) {
//                    loadMoreData();
//                }
//            }
//        });
        img_clearsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_search.setText("");
                rv_jobdetaillist.setVisibility(View.VISIBLE);
                jobnomanagmentgetlistallResponseCall();
                img_clearsearch.setVisibility(View.INVISIBLE);
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Toasty.warning(getApplicationContext(), "No Internet", Toasty.LENGTH_LONG).show();

        } else {

            jobnomanagmentgetlistallResponseCall();
        }

    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Worksheet_jobno_detailsActivity.this, Worksheet_table_AddActivity.class);
        intent.putExtra("date",date);
        intent.putExtra("emp_name",str_emp_name);
        intent.putExtra("btn_edit",str_edit);
        intent.putExtra("recycler_count", recycler_count);
        intent.putExtra("br_code",br_code);
        intent.putExtra("prepby",prepby);
        intent.putExtra("emp_no",emp_no);
        intent.putExtra("status",str_status);
        startActivity(intent);
        overridePendingTransition(R.anim.new_right, R.anim.new_left);


    }




    @SuppressLint("LogNotTimber")
    private void jobnomanagmentgetlistallResponseCall() {
        dialog = new Dialog(Worksheet_jobno_detailsActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<Worksheet_jobno_listResponse> call = apiInterface.Worksheet_jobno_listResponseCall(RestUtils.getContentType(),worksheet_jobno_listRequest());
        Log.w(TAG, "JobNoManagementResponse url  :%s" + " " + call.request().url().toString());

        call.enqueue(new Callback<Worksheet_jobno_listResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<Worksheet_jobno_listResponse> call, @NonNull Response<Worksheet_jobno_listResponse> response) {

                Log.w(TAG, "JobNoManagementResponsechecklist---->" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {

                            dialog.dismiss();
                            //List<JobNoManagementResponse.DataBean> dataBeanList = response.body().getData();
                            dataBeanList = response.body().getData();

                            if (dataBeanList != null && dataBeanList.size() > 0) {
                                rv_jobdetaillist.setVisibility(View.VISIBLE);

                                TOTAL_NUMBER_OF_ITEMS = dataBeanList.size();
                                ITEMS_REMAINING = TOTAL_NUMBER_OF_ITEMS - ITEM_PER_PAGE;
                                totalpages = (int) Math.ceil((double) TOTAL_NUMBER_OF_ITEMS / ITEM_PER_PAGE);

                                Log.i(TAG, "DataChecking total pages: " + totalpages + ", total items: " + TOTAL_NUMBER_OF_ITEMS + ", remaining items: " + ITEMS_REMAINING);

                                List<Worksheet_jobno_listResponse.DataBean> currentPageData = getCurrentPageData();
                                setView(currentPageData);

                                txt_no_records.setVisibility(View.GONE);
                            } else {
                                rv_jobdetaillist.setVisibility(View.GONE);
                                txt_no_records.setVisibility(View.VISIBLE);

                                txt_no_records.setText("No Job Detail Available");
                            }

                        }


                    } else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();

                        //showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<Worksheet_jobno_listResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.e("JobNoManagementResponse", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Worksheet_jobno_listRequest worksheet_jobno_listRequest() {

        Worksheet_jobno_listRequest worksheet_jobno_listRequest = new  Worksheet_jobno_listRequest();
        worksheet_jobno_listRequest.setBRCODE(locationid);
        Log.w(TAG, "ActivityListManagementRequest " + new Gson().toJson(worksheet_jobno_listRequest));
        return worksheet_jobno_listRequest;
    }

    @SuppressLint("LogNotTimber")
//    private void setView(List<Worksheet_jobno_listResponse.DataBean> dataBeanList,int ITEM_PER_PAGE,int TOTAL_NUMBER_OF_ITEMS) {
//        rv_jobdetaillist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        rv_jobdetaillist.setItemAnimator(new DefaultItemAnimator());
//        jobDetailListAdapter = new JobDetailList_WorksheetAdapter(this, dataBeanList, status, TAG);
//        rv_jobdetaillist.setAdapter(jobDetailListAdapter);
//    }

    private void setView(List<Worksheet_jobno_listResponse.DataBean> dataBeanList) {
        rv_jobdetaillist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_jobdetaillist.setItemAnimator(new DefaultItemAnimator());
        jobDetailListAdapter = new JobDetailList_WorksheetAdapter(this, dataBeanList, status, TAG);
        rv_jobdetaillist.setAdapter(jobDetailListAdapter);
    }

    private void filter(String s) {
        List<Worksheet_jobno_listResponse.DataBean> filteredlist = new ArrayList<>();
        for (Worksheet_jobno_listResponse.DataBean item : dataBeanList) {
            if (item.getJob_detail_no().toLowerCase().contains(s.toLowerCase())) {
                Log.w(TAG, "filter----" + item.getJob_detail_no().toLowerCase().contains(s.toLowerCase()));
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Data Found ... ", Toast.LENGTH_SHORT).show();
            rv_jobdetaillist.setVisibility(View.GONE);
            txt_no_records.setVisibility(View.VISIBLE);
            txt_no_records.setText("No Jobs Available");

        } else {
            jobDetailListAdapter.filterList(filteredlist);
        }
    }

    private List<Worksheet_jobno_listResponse.DataBean> getCurrentPageData() {
        int startIndex = (currentPage - 1) * ITEM_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEM_PER_PAGE, TOTAL_NUMBER_OF_ITEMS);

        return dataBeanList.subList(startIndex, endIndex);
    }

    private void loadMoreData() {
        isLoading = true;

        if (currentPage < totalpages) {
            currentPage++;
            List<Worksheet_jobno_listResponse.DataBean> nextPageData = getCurrentPageData();
            jobDetailListAdapter.loadMoreData(nextPageData);
        } else {
            isLastPage = true;
        }

        isLoading = false;
    }
}