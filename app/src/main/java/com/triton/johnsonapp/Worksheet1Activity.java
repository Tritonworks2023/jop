package com.triton.johnsonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.triton.johnsonapp.adapter.Worksheet_dateAdapter;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.requestpojo.Worksheet_SubmitRequest;
import com.triton.johnsonapp.responsepojo.Worksheet_SubmitResponse;
import com.triton.johnsonapp.utils.RestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Worksheet1Activity extends AppCompatActivity {

    EditText date;
    int mYear, mMonth, mDay;

    private Date oneWayTripDate;
    List<Worksheet_SubmitResponse.Datum> dataBeanList;
    Worksheet_dateAdapter worksheeet_dateAdapter;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_worksheet_date)
    RecyclerView rv_worksheet_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_exp_name)
    TextView txt_exp_name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.fab_add_budgetary)
    FloatingActionButton fab_add_budgetary;

    private String TAG = WorksheetActivity.class.getSimpleName();
    Dialog dialog;
    private Dialog alertDialog;
    String message, str_emp_no, str_prepby, str_brcode, dateStr, currentdate ,str_date="",str_work_date,str_emp_name,str_status,sucessCount ="" ;
    ImageView img_back;
    int hrs_count;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_worksheet1);

        ButterKnife.bind(this);

        date = (EditText) findViewById(R.id.date);
        img_back = (ImageView) findViewById(R.id.iv_back);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            str_brcode = extras.getString("br_code");
        }
        if (extras != null) {
            str_prepby = extras.getString("prepby");
        }
        if (extras != null) {
            str_emp_no = extras.getString("emp_no");
        }
        if (extras != null) {
            str_date = extras.getString("date");
            date.setText(str_date);
        }
        if (extras != null) {
            str_emp_name = extras.getString("emp_name");
            txt_exp_name.setText("Employee Name : " + str_emp_name);
        }
        if (extras != null) {
            str_status = extras.getString("status");
        }

        System.out.println("emp_name----->" + str_emp_name);
        System.out.println("status----->" + str_status);
        System.out.println("date" + str_date);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        dateStr = sdf.format(new Date());
        currentdate = dateStr.toUpperCase();

        if(str_date == null){

            System.out.println("vvv");
        }
        else {
            callAttendanceHelperList();
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("date", str_date);
        editor.putString("br_code", str_brcode);
        editor.putString("prepby", str_prepby);
        editor.putString("emp_no", str_emp_no);
        editor.putString("emp_name", str_emp_name);
        editor.putString("status", str_status);
        editor.putInt("hrs_count", hrs_count);
        editor.apply();

//        date.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                final Calendar c = Calendar.getInstance();
//                mYear = c.get(Calendar.YEAR);
//                mMonth = c.get(Calendar.MONTH);
//                mDay = c.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(Worksheet1Activity.this,
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//
//                                date.setText(dayOfMonth + "-" +"0"+(monthOfYear + 1) + "-" + year);
//
//                                callAttendanceHelperList();
//
//                            }
//                        }, mYear, mMonth, mDay);
//                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000);
//                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//                datePickerDialog.show();
//            }
//        });

        fab_add_budgetary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_date = date.getText().toString();

                if (str_date.equals("")) {

                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), Worksheet_table_AddActivity.class);
                    intent.putExtra("br_code", str_brcode);
                    intent.putExtra("prepby", str_prepby);
                    intent.putExtra("emp_no", str_emp_no);
                    intent.putExtra("date", str_date);
                    intent.putExtra("btn_edit", "not_edit");
                    intent.putExtra("emp_name", str_emp_name );
                    intent.putExtra("status", str_status );
                    intent.putExtra("hrs_count", hrs_count );
                    intent.putExtra("recycler_count", sucessCount);
                    startActivity(intent);
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

    private void callAttendanceHelperList() {

        dialog = new Dialog(Worksheet1Activity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<Worksheet_SubmitResponse> call = apiInterface.Worksheet_SubmitResponseCall(RestUtils.getContentType(), worksheet_submitRequest());
        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());

        call.enqueue(new Callback<Worksheet_SubmitResponse>() {
            @Override
            public void onResponse(@NonNull Call<Worksheet_SubmitResponse> call, @NonNull Response<Worksheet_SubmitResponse> response) {

                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));

                //   dialog.dismiss();

                if (response.body() != null) {
                    message = response.body().getMessage();
                    hrs_count = response.body().getHrs_count();
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("hrs_count", hrs_count);
                    editor.apply();

                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {

                            dataBeanList = response.body().getData();

                            if (dataBeanList != null && dataBeanList.size() > 0) {
                                rv_worksheet_date.setVisibility(View.VISIBLE);
                                setView(dataBeanList);
                                txt_no_records.setVisibility(View.GONE);

                                sucessCount = String.valueOf(worksheeet_dateAdapter.getItemCount());
                                System.out.println("count" + sucessCount);
                            }
                            else {
                                rv_worksheet_date.setVisibility(View.GONE);
                                txt_no_records.setVisibility(View.VISIBLE);
                                sucessCount = "0";
                                System.out.println("count" + sucessCount);

                                //txt_no_records.setText("No Job Detail Available");
                            }

                         //   Toasty.success(getApplicationContext(), "" + hrs_count, Toasty.LENGTH_LONG).show();

//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intent);
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
            public void onFailure(@NonNull Call<Worksheet_SubmitResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());

            }
        });
    }

    private Worksheet_SubmitRequest worksheet_submitRequest() {

        str_date = date.getText().toString();

        SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
        try {
            oneWayTripDate = input.parse(str_date);  // parse input
        } catch (ParseException e) {
            e.printStackTrace();
        }

        str_work_date = output.format(oneWayTripDate);


        Worksheet_SubmitRequest worksheet_submitRequest = new Worksheet_SubmitRequest();
        worksheet_submitRequest.setJLS_EWD_WKDATE(str_work_date);
        worksheet_submitRequest.setJLS_EWD_BRCODE(str_brcode);
        worksheet_submitRequest.setJLS_EWD_PREPBY(str_prepby);
        worksheet_submitRequest.setJLS_EWD_JOBNO("");
        worksheet_submitRequest.setJLS_EWD_EMPNO(str_emp_no);

//        worksheet_submitRequest.setJLS_EWD_WKDATE("06-Jan-2023");
//        worksheet_submitRequest.setJLS_EWD_BRCODE("TN01");
//        worksheet_submitRequest.setJLS_EWD_PREPBY("E123");
//        worksheet_submitRequest.setJLS_EWD_JOBNO("L-R1234");
//        worksheet_submitRequest.setJLS_EWD_EMPNO("E12345");
        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheet_submitRequest));
        return worksheet_submitRequest;
    }

    private void setView(List<Worksheet_SubmitResponse.Datum> dataBeanList) {
        rv_worksheet_date.setLayoutManager(new LinearLayoutManager(this));
        rv_worksheet_date.setItemAnimator(new DefaultItemAnimator());
        worksheeet_dateAdapter = new Worksheet_dateAdapter(this, dataBeanList, TAG);
        rv_worksheet_date.setAdapter(worksheeet_dateAdapter);

    }

    public void onBackPressed() {
        super.onBackPressed();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("br_code", str_brcode);
        editor.putString("prepby", str_prepby);
        editor.putString("emp_no", str_emp_no);
        editor.putString("emp_name", str_emp_name);
        editor.putString("status", str_status);
        editor.putInt("hrs_count", hrs_count);
        editor.putString("back", "back");
        editor.apply();
        Intent intent = new Intent(Worksheet1Activity.this, DailyAttendanceActivity.class);
        intent.putExtra("date",str_date);
        startActivity(intent);
        overridePendingTransition(R.anim.new_right, R.anim.new_left);
        finish();
    }
}