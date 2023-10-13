package com.triton.johnsonapp;

import static com.triton.johnsonapp.db.CommonUtil.context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.requestpojo.WorksheetSubmitRequest;
import com.triton.johnsonapp.responsepojo.WorksheetSubmitResponse;
import com.triton.johnsonapp.utils.RestUtils;
import com.triton.johnsonapp.worksheet_empactivity.Worksheet_empactivity_Activity;
import com.triton.johnsonapp.worksheet_joblist.Worksheet_jobno_detailsActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Worksheet_table_AddActivity extends AppCompatActivity {

    RelativeLayout rl_job_no, rl_emp_activity;
    EditText edt_workhrs,edt_km;
    Button btn_submit,btn_update;
    TextView txt_job_no,txt_emp_activity,lable_job_no,lable_emp_activity,lable_work_hrs;
    String str_date,str_job_no,str_emp_activity,str_work_hrs,str_km,str_remark,emp_no,br_code,prepby,str_work_date,dateStr, currentdate,message,str_edit="",str_edit_date="",str_emp_name,str_status;
    String formattedDate,job_dummy,recycler_count,dummy_emp_act;
    private Date oneWayTripDate,oneWayTripDate1;
    private String TAG = WorksheetActivity.class.getSimpleName();
    Dialog dialog;
    private Dialog alertDialog;
    ImageView img_back;
    int hrs_count,sum,sum1;
    String test_job="",test_job1="",test_job2="",test_job3="",test_job4="",test_job5="",test_job6="",test_job7="";
    String test_emp_activity ="",test_emp_activity1 ="",test_emp_activity2 ="",test_emp_activity3 ="",test_emp_activity4 ="",test_emp_activity5 ="",test_emp_activity6 ="",test_emp_activity7 ="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_worksheet_table_add);

        rl_job_no = (RelativeLayout) findViewById(R.id.rl_job_no);
        rl_emp_activity = (RelativeLayout) findViewById(R.id.rl_emp_activity);
        edt_workhrs = (EditText) findViewById(R.id.edt_workhrs);
        edt_km = (EditText) findViewById(R.id.edt_km);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_update = (Button) findViewById(R.id.btn_update);
        txt_job_no = (TextView) findViewById(R.id.txt_job_no);
        txt_emp_activity = (TextView) findViewById(R.id.txt_emp_activity);
        lable_job_no = (TextView) findViewById(R.id.lable_job_no);
        lable_emp_activity = (TextView) findViewById(R.id.lable_emp_activity);
        lable_work_hrs = (TextView) findViewById(R.id.lable_workhrs);
        img_back = (ImageView) findViewById(R.id.iv_back);

        edt_km.setFilters( new InputFilter[]{ new MinMaxFilter( "0" , "100" )}) ;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            str_date = extras.getString("date");
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
            str_edit = extras.getString("btn_edit");

            if(str_edit.equals("not_edit")) {
                btn_update.setVisibility(View.GONE);
                btn_submit.setVisibility(View.VISIBLE);
            }
            else {
                btn_update.setVisibility(View.VISIBLE);
                btn_submit.setVisibility(View.GONE);
                rl_job_no.setClickable(false);
            }
        }
        if (extras != null) {
            str_job_no = extras.getString("job_id");
            txt_job_no.setText(str_job_no);
        }
        if (extras != null) {
            str_emp_activity = extras.getString("emp_activity");
            txt_emp_activity.setText(str_emp_activity);
        }
        if (extras != null) {
            str_work_hrs = extras.getString("workhrs");
            edt_workhrs.setText(str_work_hrs);
        }
        if (extras != null) {
            str_km = extras.getString("km");
            if(str_km == null){
                edt_km.setText("0");
            }
            else {
                edt_km.setText(str_km);
            }
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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        job_dummy = sharedPreferences.getString("job_dummy", "");
        dummy_emp_act = sharedPreferences.getString("dummy_emp_act", "");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        dateStr = sdf.format(new Date());
        currentdate = dateStr.toUpperCase();

        Spannable str_lable_job_no = new SpannableString("Job no : ");
        str_lable_job_no.setSpan(new ForegroundColorSpan(Color.BLACK), 0, str_lable_job_no.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lable_job_no.setText(str_lable_job_no);
        Spannable str_lable_job_no1 = new SpannableString("*");
        str_lable_job_no1.setSpan(new ForegroundColorSpan(Color.RED), 0, str_lable_job_no1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lable_job_no.append(str_lable_job_no1);

        Spannable str_lable_emp_activity = new SpannableString("Emp activity : ");
        str_lable_emp_activity.setSpan(new ForegroundColorSpan(Color.BLACK), 0, str_lable_emp_activity.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lable_emp_activity.setText(str_lable_emp_activity);
        Spannable str_lable_emp_activity1 = new SpannableString("*");
        str_lable_emp_activity1.setSpan(new ForegroundColorSpan(Color.RED), 0, str_lable_emp_activity1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lable_emp_activity.append(str_lable_emp_activity1);

        Spannable str_lable_work_hrs = new SpannableString("Work hrs : ");
        str_lable_work_hrs.setSpan(new ForegroundColorSpan(Color.BLACK), 0, str_lable_work_hrs.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lable_work_hrs.setText(str_lable_work_hrs);
        Spannable str_lable_work_hrs1 = new SpannableString("*");
        str_lable_work_hrs1.setSpan(new ForegroundColorSpan(Color.RED), 0, str_lable_work_hrs1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lable_work_hrs.append(str_lable_work_hrs1);

        if(recycler_count.equals("1")) {
        StringTokenizer st = new StringTokenizer(job_dummy, ",");
        StringTokenizer emp = new StringTokenizer(dummy_emp_act, ",");
        test_job = st.nextToken();
        test_emp_activity = emp.nextToken();
        }
        else if(recycler_count.equals("2")){
            StringTokenizer st = new StringTokenizer(job_dummy, ",");
            StringTokenizer emp = new StringTokenizer(dummy_emp_act, ",");
            test_job = st.nextToken();
            test_job1 = st.nextToken();
            test_emp_activity = emp.nextToken();
            test_emp_activity1 = emp.nextToken();
        }
        else if(recycler_count.equals("3")){
            StringTokenizer st = new StringTokenizer(job_dummy, ",");
            StringTokenizer emp = new StringTokenizer(dummy_emp_act, ",");
            test_job = st.nextToken();
            test_job1 = st.nextToken();
            test_job2 = st.nextToken();
            test_emp_activity = emp.nextToken();
            test_emp_activity1 = emp.nextToken();
            test_emp_activity2 = emp.nextToken();
        }
        else if(recycler_count.equals("4")){
            StringTokenizer st = new StringTokenizer(job_dummy, ",");
            StringTokenizer emp = new StringTokenizer(dummy_emp_act, ",");
            test_job = st.nextToken();
            test_job1 = st.nextToken();
            test_job2 = st.nextToken();
            test_job3 = st.nextToken();
            test_emp_activity = emp.nextToken();
            test_emp_activity1 = emp.nextToken();
            test_emp_activity2 = emp.nextToken();
            test_emp_activity3 = emp.nextToken();
        }
        else if(recycler_count.equals("5")){
            StringTokenizer st = new StringTokenizer(job_dummy, ",");
            StringTokenizer emp = new StringTokenizer(dummy_emp_act, ",");
            test_job = st.nextToken();
            test_job1 = st.nextToken();
            test_job2 = st.nextToken();
            test_job3 = st.nextToken();
            test_job4 = st.nextToken();
            test_emp_activity = emp.nextToken();
            test_emp_activity1 = emp.nextToken();
            test_emp_activity2 = emp.nextToken();
            test_emp_activity3 = emp.nextToken();
            test_emp_activity4 = emp.nextToken();

        }
        else if(recycler_count.equals("6")){
            StringTokenizer st = new StringTokenizer(job_dummy, ",");
            StringTokenizer emp = new StringTokenizer(dummy_emp_act, ",");
            test_job = st.nextToken();
            test_job1 = st.nextToken();
            test_job2 = st.nextToken();
            test_job3 = st.nextToken();
            test_job4 = st.nextToken();
            test_job5 = st.nextToken();
            test_emp_activity = emp.nextToken();
            test_emp_activity1 = emp.nextToken();
            test_emp_activity2 = emp.nextToken();
            test_emp_activity3 = emp.nextToken();
            test_emp_activity4 = emp.nextToken();
            test_emp_activity5 = emp.nextToken();

        }
        else if(recycler_count.equals("7")){
            StringTokenizer st = new StringTokenizer(job_dummy, ",");
            StringTokenizer emp = new StringTokenizer(dummy_emp_act, ",");
            test_job = st.nextToken();
            test_job1 = st.nextToken();
            test_job2 = st.nextToken();
            test_job3 = st.nextToken();
            test_job4 = st.nextToken();
            test_job5 = st.nextToken();
            test_job6 = st.nextToken();
            test_emp_activity = emp.nextToken();
            test_emp_activity1 = emp.nextToken();
            test_emp_activity2 = emp.nextToken();
            test_emp_activity3 = emp.nextToken();
            test_emp_activity4 = emp.nextToken();
            test_emp_activity5 = emp.nextToken();
            test_emp_activity6 = emp.nextToken();
        }
        else if(recycler_count.equals("8")){
            StringTokenizer st = new StringTokenizer(job_dummy, ",");
            StringTokenizer emp = new StringTokenizer(dummy_emp_act, ",");
            test_job = st.nextToken();
            test_job1 = st.nextToken();
            test_job2 = st.nextToken();
            test_job3 = st.nextToken();
            test_job4 = st.nextToken();
            test_job5 = st.nextToken();
            test_job6 = st.nextToken();
            test_job7 = st.nextToken();
            test_emp_activity = emp.nextToken();
            test_emp_activity1 = emp.nextToken();
            test_emp_activity2 = emp.nextToken();
            test_emp_activity3 = emp.nextToken();
            test_emp_activity4 = emp.nextToken();
            test_emp_activity5 = emp.nextToken();
            test_emp_activity6 = emp.nextToken();
            test_emp_activity7 = emp.nextToken();
        }
        else {

        }

        rl_job_no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                str_emp_activity = txt_emp_activity.getText().toString();
                str_work_hrs = edt_workhrs.getText().toString();
                str_km = edt_km.getText().toString();

                if(str_edit.equals("not_edit")) {

                    Intent intent = new Intent(Worksheet_table_AddActivity.this, Worksheet_jobno_detailsActivity.class);
                    intent.putExtra("date", str_date);
                    intent.putExtra("edit_date", str_edit_date);
                    intent.putExtra("btn_edit", str_edit);
                    intent.putExtra("br_code", br_code);
                    intent.putExtra("prepby", prepby);
                    intent.putExtra("emp_no", emp_no);
                    intent.putExtra("emp_activity", str_emp_activity);
                    intent.putExtra("workhrs", str_work_hrs);
                    intent.putExtra("km", str_km);
                    intent.putExtra("emp_name",str_emp_name);
                    intent.putExtra("status",str_status);
                    intent.putExtra("hrs_count",hrs_count);
                    intent.putExtra("recycler_count", recycler_count);
                    intent.putExtra("job_dummy", job_dummy);
                    intent.putExtra("dummy_emp_act", dummy_emp_act);
                    startActivity(intent);

                    System.out.println("name---->" + str_emp_name);
                }
                else {

                }
            }
        });

        rl_emp_activity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                str_job_no = txt_job_no.getText().toString();
                str_work_hrs = edt_workhrs.getText().toString();
                str_km = edt_km.getText().toString();

                Intent intent = new Intent(Worksheet_table_AddActivity.this, Worksheet_empactivity_Activity.class);
                intent.putExtra("job_id", str_job_no);
                intent.putExtra("date", str_date);
                intent.putExtra("edit_date", str_edit_date);
                intent.putExtra("br_code",br_code);
                intent.putExtra("prepby",prepby);
                intent.putExtra("emp_no",emp_no);
                intent.putExtra("workhrs",str_work_hrs);
                intent.putExtra("km",str_km);
                intent.putExtra("btn_edit", str_edit);
                intent.putExtra("emp_name",str_emp_name);
                intent.putExtra("status",str_status);
                intent.putExtra("hrs_count",hrs_count);
                intent.putExtra("recycler_count", recycler_count);
                intent.putExtra("job_dummy", job_dummy);
                intent.putExtra("dummy_emp_act", dummy_emp_act);
                startActivity(intent);

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                str_job_no = txt_job_no.getText().toString();
                str_emp_activity = txt_emp_activity.getText().toString();
                str_work_hrs = edt_workhrs.getText().toString();
                str_km = edt_km.getText().toString();
                int work = Integer.parseInt(str_work_hrs);

                if (str_job_no.equals("")) {
                    showErrorLoading("Please Enter Job No");
                } else if (str_emp_activity.equals("")) {
                    showErrorLoading("Please Enter Emp Activity");
                } else if (str_work_hrs.equals("")) {
                    showErrorLoading("Please Enter Work Hours");
                }
                else {

                    if (str_job_no.equals(test_job) && str_emp_activity.equals(test_emp_activity) || str_job_no.equals(test_job1) && str_emp_activity.equals(test_emp_activity1) || str_job_no.equals(test_job2) && str_emp_activity.equals(test_emp_activity2) || str_job_no.equals(test_job3) && str_emp_activity.equals(test_emp_activity3) || str_job_no.equals(test_job4) && str_emp_activity.equals(test_emp_activity4) || str_job_no.equals(test_job5) && str_emp_activity.equals(test_emp_activity5) || str_job_no.equals(test_job6) && str_emp_activity.equals(test_emp_activity6) || str_job_no.equals(test_job7) && str_emp_activity.equals(test_emp_activity7)){

                        showErrorLoading("Already This Job no & Emp Activity Select");
                    }
                    else {

                    //    Toast.makeText(getApplicationContext(), "sucess", Toast.LENGTH_SHORT).show();

                        SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
                        SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
                        try {
                            oneWayTripDate = input.parse(str_date);  // parse input
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        str_work_date = output.format(oneWayTripDate);

                        sum = hrs_count + work;

                        Log.w("sum", String.valueOf(sum));

                        if (str_status.equals("Present")) {

                            if (sum <= 8) {
                                callWorksheetsubmit();
                            }
                            else {
                                showErrorLoading("Per day working hours should be 8 hrs");
                            }
                        }
                        else if(str_status.equals("FN - Leave") || str_status.equals("AN - Leave")) {
                            if (sum <= 4) {
                                callWorksheetsubmit();
                            } else {
                                showErrorLoading("Per day working hours should be be 4 hrs");
                            }
                        }
                        else {

                        }

                    }

                }

            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                str_job_no = txt_job_no.getText().toString();
                str_emp_activity = txt_emp_activity.getText().toString();
                str_work_hrs = edt_workhrs.getText().toString();
                str_km = edt_km.getText().toString();
                int work = Integer.parseInt(str_work_hrs);

                if (str_job_no.equals("")) {
                    showErrorLoading("Please Enter Job No");
                } else if (str_emp_activity.equals("")) {
                    showErrorLoading("Please Enter Emp Activity");
                } else if (str_work_hrs.equals("")) {
                    showErrorLoading("Please Enter Work Hours");
                }
                else {

                        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            oneWayTripDate1 = inputFormat.parse(str_edit_date);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                          formattedDate = outputFormat.format(oneWayTripDate1);

                        sum = hrs_count + work;

                        Log.w("sum", String.valueOf(sum) + " ---- "+ String.valueOf(sum1));

                        if (str_status.equals("Present")) {

                            if (sum <= 8) {
                                callWorksheetupdate();
                               // System.out.println("sucess------>");
                            }
                            else {
                                showErrorLoading("Per day working hours should be 8 hrs");
                            }

                           }
                        else if (str_status.equals("FN - Leave") || str_status.equals("AN - Leave")) {
                            if (sum <= 4) {
                                callWorksheetupdate();
                              //  System.out.println("sucess");
                            } else {
                                showErrorLoading("Per day working hours should be 4 hrs");
                            }
                        }
                        else {

                        }
                    }
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

    }

    private void callWorksheetsubmit() {

        dialog = new Dialog(Worksheet_table_AddActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<WorksheetSubmitResponse> call = apiInterface.WorksheetSubmitResponseCall(RestUtils.getContentType(), worksheetsubmitRequest());
        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());

        call.enqueue(new Callback<WorksheetSubmitResponse>() {
            @Override
            public void onResponse(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Response<WorksheetSubmitResponse> response) {

                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));

                //   dialog.dismiss();

                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {

                            Intent intent = new Intent(Worksheet_table_AddActivity.this, Worksheet1Activity.class);
                            intent.putExtra("br_code",br_code);
                            intent.putExtra("prepby",prepby);
                            intent.putExtra("emp_no",emp_no);
                            intent.putExtra("date",str_date);
                            intent.putExtra("emp_name",str_emp_name);
                            intent.putExtra("status",str_status);
                            startActivity(intent);

                            dialog.dismiss();
                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
                        }

                    } else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
                    }
                } else {

                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());

            }
        });
    }

    private WorksheetSubmitRequest worksheetsubmitRequest() {

        WorksheetSubmitRequest worksheetsubmitRequest = new WorksheetSubmitRequest();
        worksheetsubmitRequest.setJLS_EWD_WKDATE(str_work_date);
        worksheetsubmitRequest.setJLS_EWD_BRCODE(br_code);
        worksheetsubmitRequest.setJLS_EWD_PREPBY(prepby);
        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no);
        worksheetsubmitRequest.setJLS_EWD_EMPNO(emp_no);
        worksheetsubmitRequest.setJLS_EWD_ACTIVITY(str_emp_activity);
        worksheetsubmitRequest.setJLS_EWD_WRKHOUR(str_work_hrs);
        worksheetsubmitRequest.setJLS_EWD_PREPDT(currentdate);
        worksheetsubmitRequest.setJLS_EWD_STATUS("A");
        worksheetsubmitRequest.setJLS_EWD_MODBY("");
        worksheetsubmitRequest.setJLS_EWD_MODDT("");
        worksheetsubmitRequest.setJLS_EWD_DISTANCE(str_km);
        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheetsubmitRequest));
        return worksheetsubmitRequest;
    }

    private void callWorksheetupdate() {

        dialog = new Dialog(Worksheet_table_AddActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<WorksheetSubmitResponse> call = apiInterface.WorksheetUpdateResponseCall(RestUtils.getContentType(), worksheetupdateRequest());
        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());

        call.enqueue(new Callback<WorksheetSubmitResponse>() {
            @Override
            public void onResponse(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Response<WorksheetSubmitResponse> response) {

                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));

                //   dialog.dismiss();

                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {

                            Intent intent = new Intent(Worksheet_table_AddActivity.this, Worksheet1Activity.class);
                            intent.putExtra("br_code",br_code);
                            intent.putExtra("prepby",prepby);
                            intent.putExtra("emp_no",emp_no);
                            intent.putExtra("date",str_date);
                            intent.putExtra("emp_name",str_emp_name);
                            intent.putExtra("status",str_status);
                            startActivity(intent);

                            dialog.dismiss();
                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
                        }

                    } else {
                        dialog.dismiss();
                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
                    }
                } else {

                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());

            }
        });
    }

    private WorksheetSubmitRequest worksheetupdateRequest() {

        WorksheetSubmitRequest worksheetsubmitRequest = new WorksheetSubmitRequest();
        worksheetsubmitRequest.setJLS_EWD_WKDATE(formattedDate);
        worksheetsubmitRequest.setJLS_EWD_BRCODE(br_code);
        worksheetsubmitRequest.setJLS_EWD_PREPBY(prepby);
        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no);
        worksheetsubmitRequest.setJLS_EWD_EMPNO(emp_no);
        worksheetsubmitRequest.setJLS_EWD_ACTIVITY(str_emp_activity);
        worksheetsubmitRequest.setJLS_EWD_WRKHOUR(str_work_hrs);
        worksheetsubmitRequest.setJLS_EWD_PREPDT(currentdate);
        worksheetsubmitRequest.setJLS_EWD_STATUS("A");
        worksheetsubmitRequest.setJLS_EWD_MODBY("");
        worksheetsubmitRequest.setJLS_EWD_MODDT("");
        worksheetsubmitRequest.setJLS_EWD_DISTANCE(str_km);
        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheetsubmitRequest));
        Log.w(TAG, "update_date " + formattedDate);
        return worksheetsubmitRequest;
    }

    public void showErrorLoading(String errormesage) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(errormesage);
            alertDialogBuilder.setPositiveButton("ok",
                    (arg0, arg1) -> hideLoading());


            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        public void hideLoading() {
            try {
                alertDialog.dismiss();
            } catch (Exception ignored) {

            }
        }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Worksheet_table_AddActivity.this, Worksheet1Activity.class);
        intent.putExtra("br_code",br_code);
        intent.putExtra("prepby",prepby);
        intent.putExtra("emp_no",emp_no);
        intent.putExtra("date",str_date);
        intent.putExtra("emp_name",str_emp_name);
        intent.putExtra("status",str_status);
        startActivity(intent);
        System.out.println("wwwwwww-----" + str_emp_name);
        overridePendingTransition(R.anim.new_right, R.anim.new_left);
        finish();
    }
}