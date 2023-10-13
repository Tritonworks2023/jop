package com.triton.johnsonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.triton.johnsonapp.adapter.Worksheet_dateAdapter;
import com.triton.johnsonapp.api.APIInterface;
import com.triton.johnsonapp.api.RetrofitClient;
import com.triton.johnsonapp.requestpojo.WorksheetSubmitRequest;
import com.triton.johnsonapp.requestpojo.Worksheet_SubmitRequest;
import com.triton.johnsonapp.responsepojo.WorksheetSubmitResponse;
import com.triton.johnsonapp.responsepojo.Worksheet_SubmitResponse;
import com.triton.johnsonapp.utils.RestUtils;
import com.triton.johnsonapp.worksheet_empactivity.Worksheet_empactivity_Activity;
import com.triton.johnsonapp.worksheet_joblist.Worksheet_jobno_detailsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorksheetActivity extends AppCompatActivity {

    EditText date;
    EditText job_no, job_no1, job_no2, job_no3, job_no4, job_no5, job_no6, job_no7, job_no8, job_no9, job_no10, job_no11, job_no12, job_no13, job_no14, job_no15, job_no16, job_no17, job_no18, job_no19;
    EditText emp_activity, emp_activity1, emp_activity2, emp_activity3, emp_activity4, emp_activity5, emp_activity6, emp_activity7, emp_activity8, emp_activity9, emp_activity10, emp_activity11, emp_activity12, emp_activity13, emp_activity14, emp_activity15, emp_activity16, emp_activity17, emp_activity18, emp_activity19;
    EditText work_hrs, work_hrs1, work_hrs2, work_hrs3, work_hrs4, work_hrs5, work_hrs6, work_hrs7, work_hrs8, work_hrs9, work_hrs10, work_hrs11, work_hrs12, work_hrs13, work_hrs14, work_hrs15, work_hrs16, work_hrs17, work_hrs18, work_hrs19;
    EditText km, km1, km2, km3, km4, km5, km6, km7, km8, km9, km10, km11, km12, km13, km14, km15, km16, km17, km18, km19;
    EditText remark, remark1, remark2, remark3, remark4, remark5, remark6, remark7, remark8, remark9, remark10, remark11, remark12, remark13, remark14, remark15, remark16, remark17, remark18, remark19;
    Button submit, btn_submit, btn_submit1, btn_submit2, btn_submit3, btn_submit4, btn_submit5, btn_submit6, btn_submit7, btn_submit8, btn_submit9, btn_submit10, btn_submit11, btn_submit12, btn_submit13, btn_submit14, btn_submit15, btn_submit16, btn_submit17, btn_submit18, btn_submit19;
    Button btn_update, btn_update1, btn_update2, btn_update3, btn_update4, btn_update5, btn_update6, btn_update7, btn_update8, btn_update9, btn_update10, btn_update11, btn_update12, btn_update13, btn_update14, btn_update15, btn_update16, btn_update17, btn_update18, btn_update19;
    ImageView img_back;
    String str_date,str_work_date;
    String str_job_no, str_job_no1, str_job_no2, str_job_no3, str_job_no4, str_job_no5, str_job_no6, str_job_no7, str_job_no8, str_job_no9, str_job_no10, str_job_no11, str_job_no12, str_job_no13, str_job_no14, str_job_no15, str_job_no16, str_job_no17, str_job_no18, str_job_no19;
    String str_emp_activity, str_emp_activity1, str_emp_activity2, str_emp_activity3, str_emp_activity4, str_emp_activity5, str_emp_activity6, str_emp_activity7, str_emp_activity8, str_emp_activity9, str_emp_activity10, str_emp_activity11, str_emp_activity12, str_emp_activity13, str_emp_activity14, str_emp_activity15, str_emp_activity16, str_emp_activity17, str_emp_activity18, str_emp_activity19;
    String str_work_hrs, str_work_hrs1, str_work_hrs2, str_work_hrs3, str_work_hrs4, str_work_hrs5, str_work_hrs6, str_work_hrs7, str_work_hrs8, str_work_hrs9, str_work_hrs10, str_work_hrs11, str_work_hrs12, str_work_hrs13, str_work_hrs14, str_work_hrs15, str_work_hrs16, str_work_hrs17, str_work_hrs18, str_work_hrs19;
    String str_km, str_km1, str_km2, str_km3, str_km4, str_km5, str_km6, str_km7, str_km8, str_km9, str_km10, str_km11, str_km12, str_km13, str_km14, str_km15, str_km16, str_km17, str_km18, str_km19;
    String str_remark, str_remark1, str_remark2, str_remark3, str_remark4, str_remark5, str_remark6, str_remark7, str_remark8, str_remark9, str_remark10, str_remark11, str_remark12, str_remark13, str_remark14, str_remark15, str_remark16, str_remark17, str_remark18, str_remark19;
    String job_id = "", job_id1 = "", job_id2 = "", job_id3 = "", job_id4 = "", job_id5 = "", job_id6 = "", job_id7 = "", job_id8 = "", job_id9 = "", job_id10 = "", job_id11 = "", job_id12 = "", job_id13 = "", job_id14 = "", job_id15 = "", job_id16 = "", job_id17 = "", job_id18 = "", job_id19 = "";
    int mYear, mMonth, mDay;
    private String TAG = WorksheetActivity.class.getSimpleName();
    Dialog dialog;
    private Dialog alertDialog;
    String message, str_emp_no, str_prepby, str_brcode, dateStr, currentdate;
    private Date oneWayTripDate;
    List<Worksheet_SubmitResponse.Datum> dataBeanList;
    Worksheet_dateAdapter worksheeet_dateAdapter;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_worksheet_date)
    RecyclerView rv_worksheet_date;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_no_records)
    TextView txt_no_records;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_worksheet);

        ButterKnife.bind(this);

        date = (EditText) findViewById(R.id.date);
        job_no = (EditText) findViewById(R.id.job_no);
//        job_no1 = (EditText) findViewById(R.id.job_no1);
//        job_no2 = (EditText) findViewById(R.id.job_no2);
//        job_no3 = (EditText) findViewById(R.id.job_no3);
//        job_no4 = (EditText) findViewById(R.id.job_no4);
//        job_no5 = (EditText) findViewById(R.id.job_no5);
//        job_no6 = (EditText) findViewById(R.id.job_no6);
//        job_no7 = (EditText) findViewById(R.id.job_no7);
//        job_no8 = (EditText) findViewById(R.id.job_no8);
//        job_no9 = (EditText) findViewById(R.id.job_no9);
//        job_no10 = (EditText) findViewById(R.id.job_no10);
//        job_no11 = (EditText) findViewById(R.id.job_no11);
//        job_no12 = (EditText) findViewById(R.id.job_no12);
//        job_no13 = (EditText) findViewById(R.id.job_no13);
//        job_no14 = (EditText) findViewById(R.id.job_no14);
//        job_no15 = (EditText) findViewById(R.id.job_no15);
//        job_no16 = (EditText) findViewById(R.id.job_no16);
//        job_no17 = (EditText) findViewById(R.id.job_no17);
//        job_no18 = (EditText) findViewById(R.id.job_no18);
//        job_no19 = (EditText) findViewById(R.id.job_no19);
        emp_activity = (EditText) findViewById(R.id.emp_activity);
//        emp_activity1 = (EditText) findViewById(R.id.emp_activity1);
//        emp_activity2 = (EditText) findViewById(R.id.emp_activity2);
//        emp_activity3 = (EditText) findViewById(R.id.emp_activity3);
//        emp_activity4 = (EditText) findViewById(R.id.emp_activity4);
//        emp_activity5 = (EditText) findViewById(R.id.emp_activity5);
//        emp_activity6 = (EditText) findViewById(R.id.emp_activity6);
//        emp_activity7 = (EditText) findViewById(R.id.emp_activity7);
//        emp_activity8 = (EditText) findViewById(R.id.emp_activity8);
//        emp_activity9 = (EditText) findViewById(R.id.emp_activity9);
//        emp_activity10 = (EditText) findViewById(R.id.emp_activity10);
//        emp_activity11 = (EditText) findViewById(R.id.emp_activity11);
//        emp_activity12 = (EditText) findViewById(R.id.emp_activity12);
//        emp_activity13 = (EditText) findViewById(R.id.emp_activity13);
//        emp_activity14 = (EditText) findViewById(R.id.emp_activity14);
//        emp_activity15 = (EditText) findViewById(R.id.emp_activity15);
//        emp_activity16 = (EditText) findViewById(R.id.emp_activity16);
//        emp_activity17 = (EditText) findViewById(R.id.emp_activity17);
//        emp_activity18 = (EditText) findViewById(R.id.emp_activity18);
//        emp_activity19 = (EditText) findViewById(R.id.emp_activity19);
        work_hrs = (EditText) findViewById(R.id.work_hrs);
//        work_hrs1 = (EditText) findViewById(R.id.work_hrs1);
//        work_hrs2 = (EditText) findViewById(R.id.work_hrs2);
//        work_hrs3 = (EditText) findViewById(R.id.work_hrs3);
//        work_hrs4 = (EditText) findViewById(R.id.work_hrs4);
//        work_hrs5 = (EditText) findViewById(R.id.work_hrs5);
//        work_hrs6 = (EditText) findViewById(R.id.work_hrs6);
//        work_hrs7 = (EditText) findViewById(R.id.work_hrs7);
//        work_hrs8 = (EditText) findViewById(R.id.work_hrs8);
//        work_hrs9 = (EditText) findViewById(R.id.work_hrs9);
//        work_hrs10 = (EditText) findViewById(R.id.work_hrs10);
//        work_hrs11 = (EditText) findViewById(R.id.work_hrs11);
//        work_hrs12 = (EditText) findViewById(R.id.work_hrs12);
//        work_hrs13 = (EditText) findViewById(R.id.work_hrs13);
//        work_hrs14 = (EditText) findViewById(R.id.work_hrs14);
//        work_hrs15 = (EditText) findViewById(R.id.work_hrs15);
//        work_hrs16 = (EditText) findViewById(R.id.work_hrs16);
//        work_hrs17 = (EditText) findViewById(R.id.work_hrs17);
//        work_hrs18 = (EditText) findViewById(R.id.work_hrs18);
//        work_hrs19 = (EditText) findViewById(R.id.work_hrs19);
        km = (EditText) findViewById(R.id.km);
//        km1 = (EditText) findViewById(R.id.km1);
//        km2 = (EditText) findViewById(R.id.km2);
//        km3 = (EditText) findViewById(R.id.km3);
//        km4 = (EditText) findViewById(R.id.km4);
//        km5 = (EditText) findViewById(R.id.km5);
//        km6 = (EditText) findViewById(R.id.km6);
//        km7 = (EditText) findViewById(R.id.km7);
//        km8 = (EditText) findViewById(R.id.km8);
//        km9 = (EditText) findViewById(R.id.km9);
//        km10 = (EditText) findViewById(R.id.km10);
//        km11 = (EditText) findViewById(R.id.km11);
//        km12 = (EditText) findViewById(R.id.km12);
//        km13 = (EditText) findViewById(R.id.km13);
//        km14 = (EditText) findViewById(R.id.km14);
//        km15 = (EditText) findViewById(R.id.km15);
//        km16 = (EditText) findViewById(R.id.km16);
//        km17 = (EditText) findViewById(R.id.km17);
//        km18 = (EditText) findViewById(R.id.km18);
//        km19 = (EditText) findViewById(R.id.km19);
        remark = (EditText) findViewById(R.id.remark);
//        remark1 = (EditText) findViewById(R.id.remark1);
//        remark2 = (EditText) findViewById(R.id.remark2);
//        remark3 = (EditText) findViewById(R.id.remark3);
//        remark4 = (EditText) findViewById(R.id.remark4);
//        remark5 = (EditText) findViewById(R.id.remark5);
//        remark6 = (EditText) findViewById(R.id.remark6);
//        remark7 = (EditText) findViewById(R.id.remark7);
//        remark8 = (EditText) findViewById(R.id.remark8);
//        remark9 = (EditText) findViewById(R.id.remark9);
//        remark10 = (EditText) findViewById(R.id.remark10);
//        remark11 = (EditText) findViewById(R.id.remark11);
//        remark12 = (EditText) findViewById(R.id.remark12);
//        remark13 = (EditText) findViewById(R.id.remark13);
//        remark14 = (EditText) findViewById(R.id.remark14);
//        remark15 = (EditText) findViewById(R.id.remark15);
//        remark16 = (EditText) findViewById(R.id.remark16);
//        remark17 = (EditText) findViewById(R.id.remark17);
//        remark18 = (EditText) findViewById(R.id.remark18);
//        remark19 = (EditText) findViewById(R.id.remark19);
        btn_submit = (Button) findViewById(R.id.btn_submit);
//        btn_submit1 = (Button) findViewById(R.id.btn_submit1);
//        btn_submit2 = (Button) findViewById(R.id.btn_submit2);
//        btn_submit3 = (Button) findViewById(R.id.btn_submit3);
//        btn_submit4 = (Button) findViewById(R.id.btn_submit4);
//        btn_submit5 = (Button) findViewById(R.id.btn_submit5);
//        btn_submit6 = (Button) findViewById(R.id.btn_submit6);
//        btn_submit7 = (Button) findViewById(R.id.btn_submit7);
//        btn_submit8 = (Button) findViewById(R.id.btn_submit8);
//        btn_submit9 = (Button) findViewById(R.id.btn_submit9);
//        btn_submit10 = (Button) findViewById(R.id.btn_submit10);
//        btn_submit11 = (Button) findViewById(R.id.btn_submit11);
//        btn_submit12 = (Button) findViewById(R.id.btn_submit12);
//        btn_submit13 = (Button) findViewById(R.id.btn_submit13);
//        btn_submit14 = (Button) findViewById(R.id.btn_submit14);
//        btn_submit15 = (Button) findViewById(R.id.btn_submit15);
//        btn_submit16 = (Button) findViewById(R.id.btn_submit16);
//        btn_submit17 = (Button) findViewById(R.id.btn_submit17);
//        btn_submit18 = (Button) findViewById(R.id.btn_submit18);
//        btn_submit19 = (Button) findViewById(R.id.btn_submit19);
       btn_update = (Button) findViewById(R.id.btn_update);
//        btn_update1 = (Button) findViewById(R.id.btn_update1);
//        btn_update2 = (Button) findViewById(R.id.btn_update2);
//        btn_update3 = (Button) findViewById(R.id.btn_update3);
//        btn_update4 = (Button) findViewById(R.id.btn_update4);
//        btn_update5 = (Button) findViewById(R.id.btn_update5);
//        btn_update6 = (Button) findViewById(R.id.btn_update6);
//        btn_update7 = (Button) findViewById(R.id.btn_update7);
//        btn_update8 = (Button) findViewById(R.id.btn_update8);
//        btn_update9 = (Button) findViewById(R.id.btn_update9);
//        btn_update10 = (Button) findViewById(R.id.btn_update10);
//        btn_update11 = (Button) findViewById(R.id.btn_update11);
//        btn_update12 = (Button) findViewById(R.id.btn_update12);
//        btn_update13 = (Button) findViewById(R.id.btn_update13);
//        btn_update14 = (Button) findViewById(R.id.btn_update14);
//        btn_update15 = (Button) findViewById(R.id.btn_update15);
//        btn_update16 = (Button) findViewById(R.id.btn_update16);
//        btn_update17 = (Button) findViewById(R.id.btn_update17);
//        btn_update18 = (Button) findViewById(R.id.btn_update18);
//        btn_update19 = (Button) findViewById(R.id.btn_update19);

        img_back = (ImageView) findViewById(R.id.iv_back);
        submit = (Button) findViewById(R.id.submit);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("custom-message"));

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
//            date.setText(str_date);
        }
//        if (extras != null) {
//            job_id = extras.getString("job_id");
//            job_id1 = extras.getString("job_id1");
//            job_id2 = extras.getString("job_id2");
//            job_id3 = extras.getString("job_id3");
//            job_id4 = extras.getString("job_id4");
//            job_id5 = extras.getString("job_id5");
//            job_id6 = extras.getString("job_id6");
//            job_id7 = extras.getString("job_id7");
//            job_id8 = extras.getString("job_id8");
//            job_id9 = extras.getString("job_id9");
//            job_id10 = extras.getString("job_id10");
//            job_id11 = extras.getString("job_id11");
//            job_id12 = extras.getString("job_id12");
//            job_id13 = extras.getString("job_id13");
//            job_id14 = extras.getString("job_id14");
//            job_id15 = extras.getString("job_id15");
//            job_id16 = extras.getString("job_id16");
//            job_id17 = extras.getString("job_id17");
//            job_id18 = extras.getString("job_id18");
//            job_id19 = extras.getString("job_id19");
//
//            job_no.setText(job_id);
//            job_no1.setText(job_id1);
//            job_no2.setText(job_id2);
//            job_no3.setText(job_id3);
//            job_no4.setText(job_id4);
//            job_no5.setText(job_id5);
//            job_no6.setText(job_id6);
//            job_no7.setText(job_id7);
//            job_no8.setText(job_id8);
//            job_no9.setText(job_id9);
//            job_no10.setText(job_id10);
//            job_no11.setText(job_id11);
//            job_no12.setText(job_id12);
//            job_no13.setText(job_id13);
//            job_no14.setText(job_id14);
//            job_no15.setText(job_id15);
//            job_no16.setText(job_id16);
//            job_no17.setText(job_id17);
//            job_no18.setText(job_id18);
//            job_no19.setText(job_id19);
//        }
//        if (extras != null) {
//            str_emp_activity = extras.getString("emp_activity");
//            str_emp_activity1 = extras.getString("emp_activity1");
//            str_emp_activity2 = extras.getString("emp_activity2");
//            str_emp_activity3 = extras.getString("emp_activity3");
//            str_emp_activity4 = extras.getString("emp_activity4");
//            str_emp_activity5 = extras.getString("emp_activity5");
//            str_emp_activity6 = extras.getString("emp_activity6");
//            str_emp_activity7 = extras.getString("emp_activity7");
//            str_emp_activity8 = extras.getString("emp_activity8");
//            str_emp_activity9 = extras.getString("emp_activity9");
//            str_emp_activity10 = extras.getString("emp_activity10");
//            str_emp_activity11 = extras.getString("emp_activity11");
//            str_emp_activity12 = extras.getString("emp_activity12");
//            str_emp_activity13 = extras.getString("emp_activity13");
//            str_emp_activity14 = extras.getString("emp_activity14");
//            str_emp_activity15 = extras.getString("emp_activity15");
//            str_emp_activity16 = extras.getString("emp_activity16");
//            str_emp_activity17 = extras.getString("emp_activity17");
//            str_emp_activity18 = extras.getString("emp_activity18");
//            str_emp_activity19 = extras.getString("emp_activity19");
//
//            emp_activity.setText(str_emp_activity);
//            emp_activity1.setText(str_emp_activity1);
//            emp_activity2.setText(str_emp_activity2);
//            emp_activity3.setText(str_emp_activity3);
//            emp_activity4.setText(str_emp_activity4);
//            emp_activity5.setText(str_emp_activity5);
//            emp_activity6.setText(str_emp_activity6);
//            emp_activity7.setText(str_emp_activity7);
//            emp_activity8.setText(str_emp_activity8);
//            emp_activity9.setText(str_emp_activity9);
//            emp_activity10.setText(str_emp_activity10);
//            emp_activity11.setText(str_emp_activity11);
//            emp_activity12.setText(str_emp_activity12);
//            emp_activity13.setText(str_emp_activity13);
//            emp_activity14.setText(str_emp_activity14);
//            emp_activity15.setText(str_emp_activity15);
//            emp_activity16.setText(str_emp_activity16);
//            emp_activity17.setText(str_emp_activity17);
//            emp_activity18.setText(str_emp_activity18);
//            emp_activity19.setText(str_emp_activity19);
//        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        dateStr = sdf.format(new Date());
        currentdate = dateStr.toUpperCase();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

//        date.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                final Calendar c = Calendar.getInstance();
//                mYear = c.get(Calendar.YEAR);
//                mMonth = c.get(Calendar.MONTH);
//                mDay = c.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(WorksheetActivity.this,
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//
//                                date.setText(dayOfMonth + "-" +"0"+(monthOfYear + 1) + "-" + year);
                                callAttendanceHelperList();
//
//                            }
//                        }, mYear, mMonth, mDay);
//                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000);
//                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//                datePickerDialog.show();
//            }
//        });

        job_no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_jobno_detailsActivity.class);
                    intent.putExtra("date", str_date);
                    startActivity(intent);
            }
        });

        emp_activity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                str_job_no = job_no.getText().toString();

                 if (str_job_no.equals("")) {

                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_empactivity_Activity.class);
                    intent.putExtra("job_id", str_job_no);
                    intent.putExtra("date", str_date);
                    startActivity(intent);
                }

            }
        });

        work_hrs.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {

                str_job_no = job_no.getText().toString();
                str_emp_activity = emp_activity.getText().toString();

                 if (str_job_no.equals("")) {

                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
                } else if (str_emp_activity.equals("")) {

                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        });

        km.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {

                str_job_no = job_no.getText().toString();
                str_emp_activity = emp_activity.getText().toString();
                str_work_hrs = work_hrs.getText().toString();

                 if (str_job_no.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
                }
                else if (str_emp_activity.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
                }
                else if (str_work_hrs.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        });

        remark.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {

                str_job_no = job_no.getText().toString();
                str_emp_activity = emp_activity.getText().toString();
                str_work_hrs = work_hrs.getText().toString();
                str_km = km.getText().toString();

                 if (str_job_no.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
                } else if (str_emp_activity.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
                } else if (str_work_hrs.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
                } else if (str_km.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter KM", Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        });

//        job_no1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_jobno_details1Activity.class);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//
//            }
//        });
//
//        emp_activity1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no1 = job_no1.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no1.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_empactivity1_Activity.class);
//                    intent.putExtra("job_id1", str_job_no1);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        work_hrs1.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no1 = job_no1.getText().toString();
//                str_emp_activity1 = emp_activity1.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no1.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity1.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp activity", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        km1.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no1 = job_no1.getText().toString();
//                str_emp_activity1 = emp_activity1.getText().toString();
//                str_work_hrs1 = work_hrs1.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no1.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity1.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs1.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//
//            }
//        });
//
//        remark1.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no1 = job_no1.getText().toString();
//                str_emp_activity1 = emp_activity1.getText().toString();
//                str_work_hrs1 = work_hrs1.getText().toString();
//                str_km1 = km1.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no1.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity1.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs1.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else if (str_km1.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter KM", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//
//            }
//        });
//
//        job_no2.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_jobno_details2Activity.class);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//
//                }
//            }
//        });
//
//        emp_activity2.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no2 = job_no2.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no2.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_empactivity2_Activity.class);
//                    intent.putExtra("job_id2", str_job_no2);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//
//            }
//        });
//
//        work_hrs2.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no2 = job_no2.getText().toString();
//                str_emp_activity2 = emp_activity2.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no2.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity2.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        km2.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no2 = job_no2.getText().toString();
//                str_emp_activity2 = emp_activity2.getText().toString();
//                str_work_hrs2 = work_hrs2.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no2.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity2.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs2.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        remark2.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no2 = job_no2.getText().toString();
//                str_emp_activity2 = emp_activity2.getText().toString();
//                str_work_hrs2 = work_hrs2.getText().toString();
//                str_km2 = km2.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no2.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity2.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs2.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else if (str_km2.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter KM", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//
//        job_no3.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_jobno_details3Activity.class);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        emp_activity3.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no3 = job_no3.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date ", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no3.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No ", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_empactivity3_Activity.class);
//                    intent.putExtra("job_id3", str_job_no3);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//
//            }
//        });
//
//        work_hrs3.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//
//                str_date = date.getText().toString();
//                str_job_no3 = job_no3.getText().toString();
//                str_emp_activity3 = emp_activity3.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date ", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no3.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No ", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity3.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity ", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        km3.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no3 = job_no3.getText().toString();
//                str_emp_activity3 = emp_activity3.getText().toString();
//                str_work_hrs3 = work_hrs3.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date ", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no3.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No ", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity3.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity ", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs3.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        remark3.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no3 = job_no3.getText().toString();
//                str_emp_activity3 = emp_activity3.getText().toString();
//                str_work_hrs3 = work_hrs3.getText().toString();
//                str_km3 = km3.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no3.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity3.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs3.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else if (str_km3.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter KM", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//
//            }
//        });
//
//        job_no4.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_jobno_details4Activity.class);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        emp_activity4.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no4 = job_no4.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no4.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_empactivity4_Activity.class);
//                    intent.putExtra("job_id4", str_job_no4);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        work_hrs4.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no4 = job_no4.getText().toString();
//                str_emp_activity4 = emp_activity4.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no4.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity4.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        km4.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no4 = job_no4.getText().toString();
//                str_emp_activity4 = emp_activity4.getText().toString();
//                str_work_hrs4 = work_hrs4.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no4.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity4.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs4.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        remark4.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no4 = job_no4.getText().toString();
//                str_emp_activity4 = emp_activity4.getText().toString();
//                str_work_hrs4 = work_hrs4.getText().toString();
//                str_km4 = km4.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no4.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity4.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs4.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else if (str_km4.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter KM", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        job_no5.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_jobno_details5Activity.class);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//
//                }
//
//            }
//        });
//
//        emp_activity5.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no5 = job_no5.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no5.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_empactivity5_Activity.class);
//                    intent.putExtra("job_id5", str_job_no5);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//
//            }
//        });
//
//        work_hrs5.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no5 = job_no5.getText().toString();
//                str_emp_activity5 = emp_activity5.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no5.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity5.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        km5.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no5 = job_no5.getText().toString();
//                str_emp_activity5 = emp_activity5.getText().toString();
//                str_work_hrs5 = work_hrs5.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no5.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity5.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs5.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        remark5.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no5 = job_no5.getText().toString();
//                str_emp_activity5 = emp_activity5.getText().toString();
//                str_work_hrs5 = work_hrs5.getText().toString();
//                str_km5 = km5.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no5.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity5.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs5.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else if (str_km5.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter KM", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        job_no6.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_jobno_details6ctivity.class);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        emp_activity6.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no6 = job_no6.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no6.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_empactivity6_Activity.class);
//                    intent.putExtra("job_id6", str_job_no6);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        work_hrs6.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no6 = job_no6.getText().toString();
//                str_emp_activity6 = emp_activity6.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no6.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity6.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        km6.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no6 = job_no6.getText().toString();
//                str_emp_activity6 = emp_activity6.getText().toString();
//                str_work_hrs6 = work_hrs6.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no6.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity6.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs6.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//
//            }
//        });
//
//        remark6.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no6 = job_no6.getText().toString();
//                str_emp_activity6 = emp_activity6.getText().toString();
//                str_work_hrs6 = work_hrs6.getText().toString();
//                str_km6 = km6.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no6.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity6.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs6.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else if (str_km6.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter KM", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        job_no7.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_jobno_details7Activity.class);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//
//            }
//        });
//
//        emp_activity7.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no7 = job_no7.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no7.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_empactivity7_Activity.class);
//                    intent.putExtra("job_id7", str_job_no7);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        work_hrs7.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no7 = job_no7.getText().toString();
//                str_emp_activity7 = emp_activity7.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no7.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity7.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//
//            }
//        });
//
//        km7.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no7 = job_no7.getText().toString();
//                str_emp_activity7 = emp_activity7.getText().toString();
//                str_work_hrs7 = work_hrs7.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no7.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity7.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs7.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        remark7.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no7 = job_no7.getText().toString();
//                str_emp_activity7 = emp_activity7.getText().toString();
//                str_work_hrs7 = work_hrs7.getText().toString();
//                str_km7 = km7.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no7.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity7.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs7.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else if (str_km7.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter KM", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        job_no8.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_jobno_details8Activity.class);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        emp_activity8.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no8 = job_no8.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no8.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_empactivity8_Activity.class);
//                    intent.putExtra("job_id8", str_job_no8);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        work_hrs8.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no8 = job_no8.getText().toString();
//                str_emp_activity8 = emp_activity8.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no8.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity8.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        km8.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no8 = job_no8.getText().toString();
//                str_emp_activity8 = emp_activity8.getText().toString();
//                str_work_hrs8 = work_hrs8.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no8.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity8.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs8.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//
//            }
//        });
//
//        remark8.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no8 = job_no8.getText().toString();
//                str_emp_activity8 = emp_activity8.getText().toString();
//                str_work_hrs8 = work_hrs8.getText().toString();
//                str_km8 = km8.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no8.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity8.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs8.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else if (str_km8.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter KM", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//
//            }
//        });
//
//        job_no9.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_jobno_details9Activity.class);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//
//            }
//        });
//
//        emp_activity9.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no9 = job_no9.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no9.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_empactivity9_Activity.class);
//                    intent.putExtra("job_id9", str_job_no9);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        work_hrs9.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no9 = job_no9.getText().toString();
//                str_emp_activity9 = emp_activity9.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no9.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity9.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        km9.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no9 = job_no9.getText().toString();
//                str_emp_activity9 = emp_activity9.getText().toString();
//                str_work_hrs9 = work_hrs9.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no9.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity9.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs9.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        remark9.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no9 = job_no9.getText().toString();
//                str_emp_activity9 = emp_activity9.getText().toString();
//                str_work_hrs9 = work_hrs9.getText().toString();
//                str_km9 = km9.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no9.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity9.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs9.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else if (str_km9.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter KM", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        job_no10.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_jobno_details10Activity.class);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//
//                }
//
//            }
//        });
//
//        emp_activity10.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no10 = job_no10.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no10.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_empactivity10_Activity.class);
//                    intent.putExtra("job_id10", str_job_no10);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        work_hrs10.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no10 = job_no10.getText().toString();
//                str_emp_activity10 = emp_activity10.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no10.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity10.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        km10.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no10 = job_no10.getText().toString();
//                str_emp_activity10 = emp_activity10.getText().toString();
//                str_work_hrs10 = work_hrs10.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no10.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity10.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs10.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        remark10.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no10 = job_no10.getText().toString();
//                str_emp_activity10 = emp_activity10.getText().toString();
//                str_work_hrs10 = work_hrs10.getText().toString();
//                str_km10 = km10.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no10.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity10.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs10.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else if (str_km10.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter KM", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        job_no11.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_jobno_details11Activity.class);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        emp_activity11.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no11 = job_no11.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no11.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_empactivity11_Activity.class);
//                    intent.putExtra("job_id11", str_job_no11);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        work_hrs11.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no11 = job_no11.getText().toString();
//                str_emp_activity11 = emp_activity11.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no11.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity11.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        km11.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no11 = job_no11.getText().toString();
//                str_emp_activity11 = emp_activity11.getText().toString();
//                str_work_hrs11 = work_hrs11.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no11.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity11.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs11.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        remark11.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no11 = job_no11.getText().toString();
//                str_emp_activity11 = emp_activity11.getText().toString();
//                str_work_hrs11 = work_hrs11.getText().toString();
//                str_km11 = km11.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no11.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity11.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs11.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else if (str_km11.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter KM", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        job_no12.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_jobno_details12Activity.class);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//
//            }
//        });
//
//        emp_activity12.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no12 = job_no12.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no12.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_empactivity12_Activity.class);
//                    intent.putExtra("job_id12", str_job_no12);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        work_hrs12.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no12 = job_no12.getText().toString();
//                str_emp_activity12 = emp_activity12.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no12.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity12.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        km12.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no12 = job_no12.getText().toString();
//                str_emp_activity12 = emp_activity12.getText().toString();
//                str_work_hrs12 = work_hrs12.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no12.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity12.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs12.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        remark12.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no12 = job_no12.getText().toString();
//                str_emp_activity12 = emp_activity12.getText().toString();
//                str_work_hrs12 = work_hrs12.getText().toString();
//                str_km12 = km12.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no12.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity12.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs12.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else if (str_km12.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter KM", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        job_no13.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_jobno_details13Activity.class);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        emp_activity13.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no13 = job_no13.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no13.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_empactivity13_Activity.class);
//                    intent.putExtra("job_id13", str_job_no13);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//
//            }
//        });
//
//        work_hrs13.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no13 = job_no13.getText().toString();
//                str_emp_activity13 = emp_activity13.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no13.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity13.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        km13.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no13 = job_no13.getText().toString();
//                str_emp_activity13 = emp_activity13.getText().toString();
//                str_work_hrs13 = work_hrs13.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no13.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity13.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs13.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        remark13.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no13 = job_no13.getText().toString();
//                str_emp_activity13 = emp_activity13.getText().toString();
//                str_work_hrs13 = work_hrs13.getText().toString();
//                str_km13 = km13.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no13.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity13.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs13.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else if (str_km13.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter KM", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        job_no14.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_jobno_details14Activity.class);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//
//                }
//
//            }
//        });
//
//        emp_activity14.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no14 = job_no14.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no14.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_empactivity14_Activity.class);
//                    intent.putExtra("job_id14", str_job_no14);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        work_hrs14.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no14 = job_no14.getText().toString();
//                str_emp_activity14 = emp_activity14.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no14.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity14.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        km14.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no14 = job_no14.getText().toString();
//                str_emp_activity14 = emp_activity14.getText().toString();
//                str_work_hrs14 = work_hrs14.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no14.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity14.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs14.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        remark14.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no14 = job_no14.getText().toString();
//                str_emp_activity14 = emp_activity14.getText().toString();
//                str_work_hrs14 = work_hrs14.getText().toString();
//                str_km14 = km14.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no14.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity14.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs14.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else if (str_km14.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter KM", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        job_no15.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_jobno_details15Activity.class);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        emp_activity15.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no15 = job_no15.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no15.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_empactivity15_Activity.class);
//                    intent.putExtra("job_id15", str_job_no15);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//
//            }
//        });
//
//        work_hrs15.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no15 = job_no15.getText().toString();
//                str_emp_activity15 = emp_activity15.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no15.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity15.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        km15.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no15 = job_no15.getText().toString();
//                str_emp_activity15 = emp_activity15.getText().toString();
//                str_work_hrs15 = work_hrs15.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no15.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity15.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs15.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        remark15.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no15 = job_no15.getText().toString();
//                str_emp_activity15 = emp_activity15.getText().toString();
//                str_work_hrs15 = work_hrs15.getText().toString();
//                str_km15 = km15.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no15.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity15.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs15.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else if (str_km15.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter KM", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        job_no16.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_jobno_details16Activity.class);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        emp_activity16.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no16 = job_no16.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no16.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_empactivity16_Activity.class);
//                    intent.putExtra("job_id16", str_job_no16);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        work_hrs16.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no16 = job_no16.getText().toString();
//                str_emp_activity16 = emp_activity16.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no16.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity16.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        km16.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no16 = job_no16.getText().toString();
//                str_emp_activity16 = emp_activity16.getText().toString();
//                str_work_hrs16 = work_hrs16.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no16.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity16.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs16.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        remark16.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no16 = job_no16.getText().toString();
//                str_emp_activity16 = emp_activity16.getText().toString();
//                str_work_hrs16 = work_hrs16.getText().toString();
//                str_km16 = km16.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no16.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity16.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs16.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else if (str_km16.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter KM", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        job_no17.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_jobno_details17Activity.class);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        emp_activity17.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no17 = job_no17.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no17.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_empactivity17_Activity.class);
//                    intent.putExtra("job_id17", str_job_no17);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        work_hrs17.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no17 = job_no17.getText().toString();
//                str_emp_activity17 = emp_activity17.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no17.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity17.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        km17.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no17 = job_no17.getText().toString();
//                str_emp_activity17 = emp_activity17.getText().toString();
//                str_work_hrs17 = work_hrs17.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no17.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity17.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs17.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        remark17.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no17 = job_no17.getText().toString();
//                str_emp_activity17 = emp_activity17.getText().toString();
//                str_work_hrs17 = work_hrs17.getText().toString();
//                str_km17 = km17.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no17.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity17.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs17.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else if (str_km17.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter KM", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        job_no18.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_jobno_details18Activity.class);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        emp_activity18.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no18 = job_no18.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no18.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_empactivity18_Activity.class);
//                    intent.putExtra("job_id18", str_job_no18);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        work_hrs18.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no18 = job_no18.getText().toString();
//                str_emp_activity18 = emp_activity18.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no18.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity18.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        km18.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no18 = job_no18.getText().toString();
//                str_emp_activity18 = emp_activity18.getText().toString();
//                str_work_hrs18 = work_hrs18.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no18.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity18.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs18.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        remark18.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no18 = job_no18.getText().toString();
//                str_emp_activity18 = emp_activity18.getText().toString();
//                str_work_hrs18 = work_hrs18.getText().toString();
//                str_km18 = km18.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no18.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity18.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs18.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else if (str_km18.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter KM", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        job_no19.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_jobno_details19Activity.class);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//
//            }
//        });
//
//        emp_activity19.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no19 = job_no19.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no19.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent intent = new Intent(WorksheetActivity.this, Worksheet_empactivity19_Activity.class);
//                    intent.putExtra("job_id19", str_job_no19);
//                    intent.putExtra("date", str_date);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        work_hrs19.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no19 = job_no19.getText().toString();
//                str_emp_activity19 = emp_activity19.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no19.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity19.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        km19.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no19 = job_no19.getText().toString();
//                str_emp_activity19 = emp_activity19.getText().toString();
//                str_work_hrs19 = work_hrs19.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no19.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity19.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs19.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
//        remark19.addTextChangedListener(new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//                str_date = date.getText().toString();
//                str_job_no19 = job_no19.getText().toString();
//                str_emp_activity19 = emp_activity19.getText().toString();
//                str_work_hrs19 = work_hrs19.getText().toString();
//                str_km19 = km19.getText().toString();
//
//                if (str_date.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Selected Date", Toast.LENGTH_SHORT).show();
//                } else if (str_job_no19.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Job No", Toast.LENGTH_SHORT).show();
//                } else if (str_emp_activity19.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Emp Activity", Toast.LENGTH_SHORT).show();
//                } else if (str_work_hrs19.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter Work Hours", Toast.LENGTH_SHORT).show();
//                } else if (str_km19.equals("")) {
//
//                    Toast.makeText(getApplicationContext(), "Please Enter KM", Toast.LENGTH_SHORT).show();
//                } else {
//
//                }
//            }
//        });
//
        btn_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                str_date = date.getText().toString();
                str_job_no = job_no.getText().toString();
                str_emp_activity = emp_activity.getText().toString();
                str_work_hrs = work_hrs.getText().toString();
                str_km = km.getText().toString();
                str_remark = remark.getText().toString();


                if (str_date.equals("")) {
                    showErrorLoading("Please select date");
                } else if (str_job_no.equals("")) {
                    showErrorLoading("Please Enter Job No");
                } else if (str_emp_activity.equals("")) {
                    showErrorLoading("Please Enter Emp Activity");
                } else if (str_work_hrs.equals("")) {
                    showErrorLoading("Please Enter Work Hours");
                } else if (str_km.equals("")) {
                    showErrorLoading("Please Enter KM");
                } else if (str_remark.equals("")) {
                    showErrorLoading("Please Enter Remark");
                } else {

                    SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
                    try {
                        oneWayTripDate = input.parse(str_date);  // parse input
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    str_work_date = output.format(oneWayTripDate);

                    callWorksheetsubmit();
                }

            }
        });
//        btn_submit1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no1 = job_no1.getText().toString();
//                str_emp_activity1 = emp_activity1.getText().toString();
//                str_work_hrs1 = work_hrs1.getText().toString();
//                str_km1 = km1.getText().toString();
//                str_remark1 = remark1.getText().toString();
//
//                if (str_date.equals("")) {
//                    showErrorLoading("Please select date");
//                } else if (str_job_no1.equals("")) {
//                    showErrorLoading("Please Enter Job No");
//                } else if (str_emp_activity1.equals("")) {
//                    showErrorLoading("Please Enter Emp Activity");
//                } else if (str_work_hrs1.equals("")) {
//                    showErrorLoading("Please Enter Work Hours");
//                } else if (str_km1.equals("")) {
//                    showErrorLoading("Please Enter KM");
//                } else if (str_remark1.equals("")) {
//                    showErrorLoading("Please Enter Remark");
//                } else {
//
//
//                    callWorksheetsubmit1();
//                }
//            }
//        });
//        btn_submit2.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no2 = job_no2.getText().toString();
//                str_emp_activity2 = emp_activity2.getText().toString();
//                str_work_hrs2 = work_hrs2.getText().toString();
//                str_km2 = km2.getText().toString();
//                str_remark2 = remark2.getText().toString();
//
//                if (str_date.equals("")) {
//                    showErrorLoading("Please select date");
//                } else if (str_job_no2.equals("")) {
//                    showErrorLoading("Please Enter Job No");
//                } else if (str_emp_activity2.equals("")) {
//                    showErrorLoading("Please Enter Emp Activity");
//                } else if (str_work_hrs2.equals("")) {
//                    showErrorLoading("Please Enter Work Hours");
//                } else if (str_km2.equals("")) {
//                    showErrorLoading("Please Enter KM");
//                } else if (str_remark2.equals("")) {
//                    showErrorLoading("Please Enter Remark");
//                } else {
//
//                    SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
//                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
//                    try {
//                        oneWayTripDate = input.parse(str_date);  // parse input
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    str_work_date = output.format(oneWayTripDate);
//
//                    callWorksheetsubmit2();
//                }
//            }
//        });
//        btn_submit3.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no3 = job_no.getText().toString();
//                str_emp_activity3 = emp_activity3.getText().toString();
//                str_work_hrs3 = work_hrs3.getText().toString();
//                str_km3 = km3.getText().toString();
//                str_remark3 = remark3.getText().toString();
//
//                if (str_date.equals("")) {
//                    showErrorLoading("Please select date");
//                } else if (str_job_no3.equals("")) {
//                    showErrorLoading("Please Enter Job No");
//                } else if (str_emp_activity3.equals("")) {
//                    showErrorLoading("Please Enter Emp Activity");
//                } else if (str_work_hrs3.equals("")) {
//                    showErrorLoading("Please Enter Work Hours");
//                } else if (str_km3.equals("")) {
//                    showErrorLoading("Please Enter KM");
//                } else if (str_remark3.equals("")) {
//                    showErrorLoading("Please Enter Remark");
//                } else {
//
//                    SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
//                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
//                    try {
//                        oneWayTripDate = input.parse(str_date);  // parse input
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    str_work_date = output.format(oneWayTripDate);
//
//                    callWorksheetsubmit3();
//                }
//            }
//        });
//        btn_submit4.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no4 = job_no4.getText().toString();
//                str_emp_activity4 = emp_activity4.getText().toString();
//                str_work_hrs4 = work_hrs4.getText().toString();
//                str_km4 = km4.getText().toString();
//                str_remark4 = remark4.getText().toString();
//
//                if (str_date.equals("")) {
//                    showErrorLoading("Please select date");
//                } else if (str_job_no4.equals("")) {
//                    showErrorLoading("Please Enter Job No");
//                } else if (str_emp_activity4.equals("")) {
//                    showErrorLoading("Please Enter Emp Activity");
//                } else if (str_work_hrs4.equals("")) {
//                    showErrorLoading("Please Enter Work Hours");
//                } else if (str_km4.equals("")) {
//                    showErrorLoading("Please Enter KM");
//                } else if (str_remark4.equals("")) {
//                    showErrorLoading("Please Enter Remark");
//                } else {
//
//                    SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
//                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
//                    try {
//                        oneWayTripDate = input.parse(str_date);  // parse input
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    str_work_date = output.format(oneWayTripDate);
//
//                    callWorksheetsubmit4();
//                }
//            }
//        });
//        btn_submit5.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no5 = job_no5.getText().toString();
//                str_emp_activity5 = emp_activity5.getText().toString();
//                str_work_hrs5 = work_hrs5.getText().toString();
//                str_km5 = km5.getText().toString();
//                str_remark4 = remark4.getText().toString();
//
//                if (str_date.equals("")) {
//                    showErrorLoading("Please select date");
//                } else if (str_job_no5.equals("")) {
//                    showErrorLoading("Please Enter Job No");
//                } else if (str_emp_activity5.equals("")) {
//                    showErrorLoading("Please Enter Emp Activity");
//                } else if (str_work_hrs5.equals("")) {
//                    showErrorLoading("Please Enter Work Hours");
//                } else if (str_km5.equals("")) {
//                    showErrorLoading("Please Enter KM");
//                } else if (str_remark5.equals("")) {
//                    showErrorLoading("Please Enter Remark");
//                } else {
//
//                    SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
//                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
//                    try {
//                        oneWayTripDate = input.parse(str_date);  // parse input
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    str_work_date = output.format(oneWayTripDate);
//                    callWorksheetsubmit5();
//                }
//            }
//        });
//        btn_submit6.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no6 = job_no6.getText().toString();
//                str_emp_activity6 = emp_activity6.getText().toString();
//                str_work_hrs6 = work_hrs6.getText().toString();
//                str_km6 = km6.getText().toString();
//                str_remark6 = remark6.getText().toString();
//
//                if (str_date.equals("")) {
//                    showErrorLoading("Please select date");
//                } else if (str_job_no6.equals("")) {
//                    showErrorLoading("Please Enter Job No");
//                } else if (str_emp_activity6.equals("")) {
//                    showErrorLoading("Please Enter Emp Activity");
//                } else if (str_work_hrs6.equals("")) {
//                    showErrorLoading("Please Enter Work Hours");
//                } else if (str_km6.equals("")) {
//                    showErrorLoading("Please Enter KM");
//                } else if (str_remark6.equals("")) {
//                    showErrorLoading("Please Enter Remark");
//                } else {
//
//                    SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
//                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
//                    try {
//                        oneWayTripDate = input.parse(str_date);  // parse input
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    str_work_date = output.format(oneWayTripDate);
//                    callWorksheetsubmit6();
//                }
//            }
//        });
//        btn_submit7.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no7 = job_no7.getText().toString();
//                str_emp_activity7 = emp_activity7.getText().toString();
//                str_work_hrs7 = work_hrs7.getText().toString();
//                str_km7 = km7.getText().toString();
//                str_remark7 = remark7.getText().toString();
//
//                if (str_date.equals("")) {
//                    showErrorLoading("Please select date");
//                } else if (str_job_no7.equals("")) {
//                    showErrorLoading("Please Enter Job No");
//                } else if (str_emp_activity7.equals("")) {
//                    showErrorLoading("Please Enter Emp Activity");
//                } else if (str_work_hrs7.equals("")) {
//                    showErrorLoading("Please Enter Work Hours");
//                } else if (str_km7.equals("")) {
//                    showErrorLoading("Please Enter KM");
//                } else if (str_remark7.equals("")) {
//                    showErrorLoading("Please Enter Remark");
//                } else {
//
//                    SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
//                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
//                    try {
//                        oneWayTripDate = input.parse(str_date);  // parse input
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    str_work_date = output.format(oneWayTripDate);
//
//                    callWorksheetsubmit7();
//                }
//            }
//        });
//        btn_submit8.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no8 = job_no8.getText().toString();
//                str_emp_activity8 = emp_activity8.getText().toString();
//                str_work_hrs8 = work_hrs8.getText().toString();
//                str_km8 = km8.getText().toString();
//                str_remark8 = remark8.getText().toString();
//
//                if (str_date.equals("")) {
//                    showErrorLoading("Please select date");
//                } else if (str_job_no8.equals("")) {
//                    showErrorLoading("Please Enter Job No");
//                } else if (str_emp_activity8.equals("")) {
//                    showErrorLoading("Please Enter Emp Activity");
//                } else if (str_work_hrs8.equals("")) {
//                    showErrorLoading("Please Enter Work Hours");
//                } else if (str_km8.equals("")) {
//                    showErrorLoading("Please Enter KM");
//                } else if (str_remark8.equals("")) {
//                    showErrorLoading("Please Enter Remark");
//                } else {
//
//                    SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
//                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
//                    try {
//                        oneWayTripDate = input.parse(str_date);  // parse input
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    str_work_date = output.format(oneWayTripDate);
//                    callWorksheetsubmit8();
//                }
//            }
//        });
//        btn_submit9.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no9 = job_no9.getText().toString();
//                str_emp_activity9 = emp_activity9.getText().toString();
//                str_work_hrs9 = work_hrs9.getText().toString();
//                str_km9 = km9.getText().toString();
//                str_remark9 = remark9.getText().toString();
//
//                if (str_date.equals("")) {
//                    showErrorLoading("Please select date");
//                } else if (str_job_no9.equals("")) {
//                    showErrorLoading("Please Enter Job No");
//                } else if (str_emp_activity9.equals("")) {
//                    showErrorLoading("Please Enter Emp Activity");
//                } else if (str_work_hrs9.equals("")) {
//                    showErrorLoading("Please Enter Work Hours");
//                } else if (str_km9.equals("")) {
//                    showErrorLoading("Please Enter KM");
//                } else if (str_remark9.equals("")) {
//                    showErrorLoading("Please Enter Remark");
//                } else {
//
//                    SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
//                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
//                    try {
//                        oneWayTripDate = input.parse(str_date);  // parse input
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    str_work_date = output.format(oneWayTripDate);
//                    callWorksheetsubmit9();
//                }
//            }
//        });
//        btn_submit10.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no10 = job_no10.getText().toString();
//                str_emp_activity10 = emp_activity10.getText().toString();
//                str_work_hrs10 = work_hrs10.getText().toString();
//                str_km10 = km10.getText().toString();
//                str_remark10 = remark10.getText().toString();
//
//                if (str_date.equals("")) {
//                    showErrorLoading("Please select date");
//                } else if (str_job_no10.equals("")) {
//                    showErrorLoading("Please Enter Job No");
//                } else if (str_emp_activity10.equals("")) {
//                    showErrorLoading("Please Enter Emp Activity");
//                } else if (str_work_hrs10.equals("")) {
//                    showErrorLoading("Please Enter Work Hours");
//                } else if (str_km10.equals("")) {
//                    showErrorLoading("Please Enter KM");
//                } else if (str_remark10.equals("")) {
//                    showErrorLoading("Please Enter Remark");
//                } else {
//
//                    SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
//                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
//                    try {
//                        oneWayTripDate = input.parse(str_date);  // parse input
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    str_work_date = output.format(oneWayTripDate);
//                    callWorksheetsubmit10();
//                }
//            }
//        });
//        btn_submit11.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no11 = job_no11.getText().toString();
//                str_emp_activity11 = emp_activity11.getText().toString();
//                str_work_hrs11 = work_hrs11.getText().toString();
//                str_km11 = km11.getText().toString();
//                str_remark11 = remark11.getText().toString();
//
//                if (str_date.equals("")) {
//                    showErrorLoading("Please select date");
//                } else if (str_job_no11.equals("")) {
//                    showErrorLoading("Please Enter Job No");
//                } else if (str_emp_activity11.equals("")) {
//                    showErrorLoading("Please Enter Emp Activity");
//                } else if (str_work_hrs11.equals("")) {
//                    showErrorLoading("Please Enter Work Hours");
//                } else if (str_km11.equals("")) {
//                    showErrorLoading("Please Enter KM");
//                } else if (str_remark11.equals("")) {
//                    showErrorLoading("Please Enter Remark");
//                } else {
//
//                    SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
//                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
//                    try {
//                        oneWayTripDate = input.parse(str_date);  // parse input
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    str_work_date = output.format(oneWayTripDate);
//
//                    callWorksheetsubmit11();
//                }
//            }
//        });
//        btn_submit12.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no12 = job_no12.getText().toString();
//                str_emp_activity12 = emp_activity12.getText().toString();
//                str_work_hrs12 = work_hrs12.getText().toString();
//                str_km12 = km12.getText().toString();
//                str_remark12 = remark12.getText().toString();
//
//                if (str_date.equals("")) {
//                    showErrorLoading("Please select date");
//                } else if (str_job_no12.equals("")) {
//                    showErrorLoading("Please Enter Job No");
//                } else if (str_emp_activity12.equals("")) {
//                    showErrorLoading("Please Enter Emp Activity");
//                } else if (str_work_hrs12.equals("")) {
//                    showErrorLoading("Please Enter Work Hours");
//                } else if (str_km12.equals("")) {
//                    showErrorLoading("Please Enter KM");
//                } else if (str_remark12.equals("")) {
//                    showErrorLoading("Please Enter Remark");
//                } else {
//
//                    SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
//                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
//                    try {
//                        oneWayTripDate = input.parse(str_date);  // parse input
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    str_work_date = output.format(oneWayTripDate);
//
//                    callWorksheetsubmit12();
//                }
//            }
//        });
//        btn_submit13.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no13 = job_no13.getText().toString();
//                str_emp_activity13 = emp_activity13.getText().toString();
//                str_work_hrs13 = work_hrs13.getText().toString();
//                str_km13 = km13.getText().toString();
//                str_remark13 = remark13.getText().toString();
//
//                if (str_date.equals("")) {
//                    showErrorLoading("Please select date");
//                } else if (str_job_no13.equals("")) {
//                    showErrorLoading("Please Enter Job No");
//                } else if (str_emp_activity13.equals("")) {
//                    showErrorLoading("Please Enter Emp Activity");
//                } else if (str_work_hrs13.equals("")) {
//                    showErrorLoading("Please Enter Work Hours");
//                } else if (str_km13.equals("")) {
//                    showErrorLoading("Please Enter KM");
//                } else if (str_remark13.equals("")) {
//                    showErrorLoading("Please Enter Remark");
//                } else {
//
//                    SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
//                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
//                    try {
//                        oneWayTripDate = input.parse(str_date);  // parse input
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    str_work_date = output.format(oneWayTripDate);
//
//                    callWorksheetsubmit13();
//                }
//            }
//        });
//        btn_submit14.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no14 = job_no14.getText().toString();
//                str_emp_activity14 = emp_activity14.getText().toString();
//                str_work_hrs14 = work_hrs14.getText().toString();
//                str_km14 = km14.getText().toString();
//                str_remark14 = remark14.getText().toString();
//
//                if (str_date.equals("")) {
//                    showErrorLoading("Please select date");
//                } else if (str_job_no14.equals("")) {
//                    showErrorLoading("Please Enter Job No");
//                } else if (str_emp_activity14.equals("")) {
//                    showErrorLoading("Please Enter Emp Activity");
//                } else if (str_work_hrs14.equals("")) {
//                    showErrorLoading("Please Enter Work Hours");
//                } else if (str_km14.equals("")) {
//                    showErrorLoading("Please Enter KM");
//                } else if (str_remark14.equals("")) {
//                    showErrorLoading("Please Enter Remark");
//                } else {
//
//                    SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
//                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
//                    try {
//                        oneWayTripDate = input.parse(str_date);  // parse input
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    str_work_date = output.format(oneWayTripDate);
//
//                    callWorksheetsubmit14();
//                }
//            }
//        });
//        btn_submit15.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no15 = job_no15.getText().toString();
//                str_emp_activity15 = emp_activity15.getText().toString();
//                str_work_hrs15 = work_hrs15.getText().toString();
//                str_km15 = km15.getText().toString();
//                str_remark15 = remark15.getText().toString();
//
//                if (str_date.equals("")) {
//                    showErrorLoading("Please select date");
//                } else if (str_job_no15.equals("")) {
//                    showErrorLoading("Please Enter Job No");
//                } else if (str_emp_activity15.equals("")) {
//                    showErrorLoading("Please Enter Emp Activity");
//                } else if (str_work_hrs15.equals("")) {
//                    showErrorLoading("Please Enter Work Hours");
//                } else if (str_km15.equals("")) {
//                    showErrorLoading("Please Enter KM");
//                } else if (str_remark15.equals("")) {
//                    showErrorLoading("Please Enter Remark");
//                } else {
//
//                    SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
//                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
//                    try {
//                        oneWayTripDate = input.parse(str_date);  // parse input
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    str_work_date = output.format(oneWayTripDate);
//                    callWorksheetsubmit15();
//                }
//
//            }
//        });
//        btn_submit16.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no16 = job_no16.getText().toString();
//                str_emp_activity16 = emp_activity16.getText().toString();
//                str_work_hrs16 = work_hrs16.getText().toString();
//                str_km16 = km16.getText().toString();
//                str_remark16 = remark16.getText().toString();
//
//                if (str_date.equals("")) {
//                    showErrorLoading("Please select date");
//                } else if (str_job_no16.equals("")) {
//                    showErrorLoading("Please Enter Job No");
//                } else if (str_emp_activity16.equals("")) {
//                    showErrorLoading("Please Enter Emp Activity");
//                } else if (str_work_hrs16.equals("")) {
//                    showErrorLoading("Please Enter Work Hours");
//                } else if (str_km16.equals("")) {
//                    showErrorLoading("Please Enter KM");
//                } else if (str_remark16.equals("")) {
//                    showErrorLoading("Please Enter Remark");
//                } else {
//
//                    SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
//                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
//                    try {
//                        oneWayTripDate = input.parse(str_date);  // parse input
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    str_work_date = output.format(oneWayTripDate);
//
//                    callWorksheetsubmit16();
//                }
//            }
//        });
//
//        btn_submit17.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no17 = job_no17.getText().toString();
//                str_emp_activity17 = emp_activity17.getText().toString();
//                str_work_hrs17 = work_hrs17.getText().toString();
//                str_km17 = km17.getText().toString();
//                str_remark17 = remark17.getText().toString();
//
//                if (str_date.equals("")) {
//                    showErrorLoading("Please select date");
//                } else if (str_job_no17.equals("")) {
//                    showErrorLoading("Please Enter Job No");
//                } else if (str_emp_activity17.equals("")) {
//                    showErrorLoading("Please Enter Emp Activity");
//                } else if (str_work_hrs17.equals("")) {
//                    showErrorLoading("Please Enter Work Hours");
//                } else if (str_km17.equals("")) {
//                    showErrorLoading("Please Enter KM");
//                } else if (str_remark.equals("")) {
//                    showErrorLoading("Please Enter Remark");
//                } else {
//
//                    SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
//                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
//                    try {
//                        oneWayTripDate = input.parse(str_date);  // parse input
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    str_work_date = output.format(oneWayTripDate);
//
//                    callWorksheetsubmit17();
//                }
//            }
//        });
//        btn_submit18.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no18 = job_no18.getText().toString();
//                str_emp_activity18 = emp_activity18.getText().toString();
//                str_work_hrs18 = work_hrs18.getText().toString();
//                str_km18 = km18.getText().toString();
//                str_remark18 = remark18.getText().toString();
//
//                if (str_date.equals("")) {
//                    showErrorLoading("Please select date");
//                } else if (str_job_no18.equals("")) {
//                    showErrorLoading("Please Enter Job No");
//                } else if (str_emp_activity18.equals("")) {
//                    showErrorLoading("Please Enter Emp Activity");
//                } else if (str_work_hrs18.equals("")) {
//                    showErrorLoading("Please Enter Work Hours");
//                } else if (str_km18.equals("")) {
//                    showErrorLoading("Please Enter KM");
//                } else if (str_remark18.equals("")) {
//                    showErrorLoading("Please Enter Remark");
//                } else {
//
//                    SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
//                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
//                    try {
//                        oneWayTripDate = input.parse(str_date);  // parse input
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    str_work_date = output.format(oneWayTripDate);
//
//                    callWorksheetsubmit18();
//                }
//            }
//        });
//
//        btn_submit19.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                str_date = date.getText().toString();
//                str_job_no19 = job_no19.getText().toString();
//                str_emp_activity19 = emp_activity19.getText().toString();
//                str_work_hrs19 = work_hrs19.getText().toString();
//                str_km19 = km19.getText().toString();
//                str_remark19 = remark19.getText().toString();
//
//                if (str_date.equals("")) {
//                    showErrorLoading("Please select date");
//                } else if (str_job_no19.equals("")) {
//                    showErrorLoading("Please Enter Job No");
//                } else if (str_emp_activity19.equals("")) {
//                    showErrorLoading("Please Enter Emp Activity");
//                } else if (str_work_hrs19.equals("")) {
//                    showErrorLoading("Please Enter Work Hours");
//                } else if (str_km19.equals("")) {
//                    showErrorLoading("Please Enter KM");
//                } else if (str_remark19.equals("")) {
//                    showErrorLoading("Please Enter Remark");
//                } else {
//
//                    SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
//                    SimpleDateFormat output = new SimpleDateFormat("dd-MMM-yyyy");
//                    try {
//                        oneWayTripDate = input.parse(str_date);  // parse input
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    str_work_date = output.format(oneWayTripDate);
//                    callWorksheetsubmit19();
//                }
//            }
//        });
    }

    private void callAttendanceHelperList() {

        dialog = new Dialog(WorksheetActivity.this, R.style.NewProgressDialog);
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
                    if (200 == response.body().getCode()) {
                        if (response.body().getData() != null) {

                            dataBeanList = response.body().getData();

                            if (dataBeanList != null && dataBeanList.size() > 0) {
                                rv_worksheet_date.setVisibility(View.VISIBLE);
                                setView(dataBeanList);

                                txt_no_records.setVisibility(View.GONE);
                            } else {
//                                rv_jobdetaillist.setVisibility(View.GONE);
//                                txt_no_records.setVisibility(View.VISIBLE);

                                //txt_no_records.setText("No Job Detail Available");
                            }

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

        Worksheet_SubmitRequest worksheet_submitRequest = new Worksheet_SubmitRequest();
   //     worksheet_submitRequest.setJLS_EWD_WKDATE(currentdate);
//        worksheet_submitRequest.setJLS_EWD_BRCODE(str_brcode);
//        worksheet_submitRequest.setJLS_EWD_PREPBY(str_prepby);
//        worksheet_submitRequest.setJLS_EWD_JOBNO("");
//        worksheet_submitRequest.setJLS_EWD_EMPNO(str_emp_no);

        worksheet_submitRequest.setJLS_EWD_WKDATE("06-Jan-2023");
        worksheet_submitRequest.setJLS_EWD_BRCODE("TN01");
        worksheet_submitRequest.setJLS_EWD_PREPBY("E123");
        worksheet_submitRequest.setJLS_EWD_JOBNO("L-R1234");
        worksheet_submitRequest.setJLS_EWD_EMPNO("E12345");
        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheet_submitRequest));
        return worksheet_submitRequest;
    }

    private void setView(List<Worksheet_SubmitResponse.Datum> dataBeanList) {
        rv_worksheet_date.setLayoutManager(new LinearLayoutManager(this));
        rv_worksheet_date.setItemAnimator(new DefaultItemAnimator());
        worksheeet_dateAdapter = new Worksheet_dateAdapter(this, dataBeanList, TAG);
        rv_worksheet_date.setAdapter(worksheeet_dateAdapter);

    }

    private void callWorksheetsubmit() {

        dialog = new Dialog(WorksheetActivity.this, R.style.NewProgressDialog);
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
        worksheetsubmitRequest.setJLS_EWD_BRCODE(str_brcode);
        worksheetsubmitRequest.setJLS_EWD_PREPBY(str_prepby);
        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no);
        worksheetsubmitRequest.setJLS_EWD_EMPNO(str_emp_no);
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

//    private void callWorksheetsubmit1() {
//
//        dialog = new Dialog(WorksheetActivity.this, R.style.NewProgressDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.progroess_popup);
//        dialog.show();
//
//        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
//        Call<WorksheetSubmitResponse> call = apiInterface.WorksheetSubmitResponseCall(RestUtils.getContentType(), worksheetsubmitRequest1());
//        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());
//
//        call.enqueue(new Callback<WorksheetSubmitResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Response<WorksheetSubmitResponse> response) {
//
//                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
//                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));
//
//                //   dialog.dismiss();
//
//                if (response.body() != null) {
//                    message = response.body().getMessage();
//                    if (200 == response.body().getCode()) {
//                        if (response.body().getData() != null) {
//
////                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                            startActivity(intent);
//                            dialog.dismiss();
//                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                        }
//
//                    } else {
//                        dialog.dismiss();
//                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                    }
//                } else {
//
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());
//
//            }
//        });
//    }
//
//    private WorksheetSubmitRequest worksheetsubmitRequest1() {
//
//        WorksheetSubmitRequest worksheetsubmitRequest = new WorksheetSubmitRequest();
//        worksheetsubmitRequest.setJLS_EWD_WKDATE(str_work_date);
//        worksheetsubmitRequest.setJLS_EWD_BRCODE(str_brcode);
//        worksheetsubmitRequest.setJLS_EWD_PREPBY(str_prepby);
//        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no1);
//        worksheetsubmitRequest.setJLS_EWD_EMPNO(str_emp_no);
//        worksheetsubmitRequest.setJLS_EWD_ACTIVITY(str_emp_activity1);
//        worksheetsubmitRequest.setJLS_EWD_WRKHOUR(str_work_hrs1);
//        worksheetsubmitRequest.setJLS_EWD_PREPDT(currentdate);
//        worksheetsubmitRequest.setJLS_EWD_STATUS("A");
//        worksheetsubmitRequest.setJLS_EWD_MODBY("");
//        worksheetsubmitRequest.setJLS_EWD_MODDT("");
//        worksheetsubmitRequest.setJLS_EWD_DISTANCE(str_km1);
//        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheetsubmitRequest));
//        return worksheetsubmitRequest;
//    }
//
//    private void callWorksheetsubmit2() {
//
//        dialog = new Dialog(WorksheetActivity.this, R.style.NewProgressDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.progroess_popup);
//        dialog.show();
//
//        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
//        Call<WorksheetSubmitResponse> call = apiInterface.WorksheetSubmitResponseCall(RestUtils.getContentType(), worksheetsubmitRequest2());
//        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());
//
//        call.enqueue(new Callback<WorksheetSubmitResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Response<WorksheetSubmitResponse> response) {
//
//                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
//                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));
//
//                //   dialog.dismiss();
//
//                if (response.body() != null) {
//                    message = response.body().getMessage();
//                    if (200 == response.body().getCode()) {
//                        if (response.body().getData() != null) {
//
////                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                            startActivity(intent);
//                            dialog.dismiss();
//                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                        }
//
//                    } else {
//                        dialog.dismiss();
//                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                    }
//                } else {
//
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());
//
//            }
//        });
//    }
//
//    private WorksheetSubmitRequest worksheetsubmitRequest2() {
//
//        WorksheetSubmitRequest worksheetsubmitRequest = new WorksheetSubmitRequest();
//        worksheetsubmitRequest.setJLS_EWD_WKDATE(str_work_date);
//        worksheetsubmitRequest.setJLS_EWD_BRCODE(str_brcode);
//        worksheetsubmitRequest.setJLS_EWD_PREPBY(str_prepby);
//        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no2);
//        worksheetsubmitRequest.setJLS_EWD_EMPNO(str_emp_no);
//        worksheetsubmitRequest.setJLS_EWD_ACTIVITY(str_emp_activity2);
//        worksheetsubmitRequest.setJLS_EWD_WRKHOUR(str_work_hrs2);
//        worksheetsubmitRequest.setJLS_EWD_PREPDT(currentdate);
//        worksheetsubmitRequest.setJLS_EWD_STATUS("A");
//        worksheetsubmitRequest.setJLS_EWD_MODBY("");
//        worksheetsubmitRequest.setJLS_EWD_MODDT("");
//        worksheetsubmitRequest.setJLS_EWD_DISTANCE(str_km2);
//        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheetsubmitRequest));
//        return worksheetsubmitRequest;
//    }
//
//    private void callWorksheetsubmit3() {
//
//        dialog = new Dialog(WorksheetActivity.this, R.style.NewProgressDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.progroess_popup);
//        dialog.show();
//
//        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
//        Call<WorksheetSubmitResponse> call = apiInterface.WorksheetSubmitResponseCall(RestUtils.getContentType(), worksheetsubmitRequest3());
//        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());
//
//        call.enqueue(new Callback<WorksheetSubmitResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Response<WorksheetSubmitResponse> response) {
//
//                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
//                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));
//
//                //   dialog.dismiss();
//
//                if (response.body() != null) {
//                    message = response.body().getMessage();
//                    if (200 == response.body().getCode()) {
//                        if (response.body().getData() != null) {
//
////                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                            startActivity(intent);
//                            dialog.dismiss();
//                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                        }
//
//                    } else {
//                        dialog.dismiss();
//                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                    }
//                } else {
//
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());
//
//            }
//        });
//    }
//
//    private WorksheetSubmitRequest worksheetsubmitRequest3() {
//
//        WorksheetSubmitRequest worksheetsubmitRequest = new WorksheetSubmitRequest();
//        worksheetsubmitRequest.setJLS_EWD_WKDATE(str_work_date);
//        worksheetsubmitRequest.setJLS_EWD_BRCODE(str_brcode);
//        worksheetsubmitRequest.setJLS_EWD_PREPBY(str_prepby);
//        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no3);
//        worksheetsubmitRequest.setJLS_EWD_EMPNO(str_emp_no);
//        worksheetsubmitRequest.setJLS_EWD_ACTIVITY(str_emp_activity3);
//        worksheetsubmitRequest.setJLS_EWD_WRKHOUR(str_work_hrs3);
//        worksheetsubmitRequest.setJLS_EWD_PREPDT(currentdate);
//        worksheetsubmitRequest.setJLS_EWD_STATUS("A");
//        worksheetsubmitRequest.setJLS_EWD_MODBY("");
//        worksheetsubmitRequest.setJLS_EWD_MODDT("");
//        worksheetsubmitRequest.setJLS_EWD_DISTANCE(str_km3);
//        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheetsubmitRequest));
//        return worksheetsubmitRequest;
//    }
//
//    private void callWorksheetsubmit4() {
//
//        dialog = new Dialog(WorksheetActivity.this, R.style.NewProgressDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.progroess_popup);
//        dialog.show();
//
//        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
//        Call<WorksheetSubmitResponse> call = apiInterface.WorksheetSubmitResponseCall(RestUtils.getContentType(), worksheetsubmitRequest4());
//        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());
//
//        call.enqueue(new Callback<WorksheetSubmitResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Response<WorksheetSubmitResponse> response) {
//
//                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
//                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));
//
//                //   dialog.dismiss();
//
//                if (response.body() != null) {
//                    message = response.body().getMessage();
//                    if (200 == response.body().getCode()) {
//                        if (response.body().getData() != null) {
//
////                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                            startActivity(intent);
//                            dialog.dismiss();
//                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                        }
//
//                    } else {
//                        dialog.dismiss();
//                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                    }
//                } else {
//
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());
//
//            }
//        });
//    }
//
//
//    private WorksheetSubmitRequest worksheetsubmitRequest4() {
//
//        WorksheetSubmitRequest worksheetsubmitRequest = new WorksheetSubmitRequest();
//        worksheetsubmitRequest.setJLS_EWD_WKDATE(str_work_date);
//        worksheetsubmitRequest.setJLS_EWD_BRCODE(str_brcode);
//        worksheetsubmitRequest.setJLS_EWD_PREPBY(str_prepby);
//        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no4);
//        worksheetsubmitRequest.setJLS_EWD_EMPNO(str_emp_no);
//        worksheetsubmitRequest.setJLS_EWD_ACTIVITY(str_emp_activity4);
//        worksheetsubmitRequest.setJLS_EWD_WRKHOUR(str_work_hrs4);
//        worksheetsubmitRequest.setJLS_EWD_PREPDT(currentdate);
//        worksheetsubmitRequest.setJLS_EWD_STATUS("A");
//        worksheetsubmitRequest.setJLS_EWD_MODBY("");
//        worksheetsubmitRequest.setJLS_EWD_MODDT("");
//        worksheetsubmitRequest.setJLS_EWD_DISTANCE(str_km4);
//        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheetsubmitRequest));
//        return worksheetsubmitRequest;
//    }
//
//    private void callWorksheetsubmit5() {
//
//        dialog = new Dialog(WorksheetActivity.this, R.style.NewProgressDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.progroess_popup);
//        dialog.show();
//
//        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
//        Call<WorksheetSubmitResponse> call = apiInterface.WorksheetSubmitResponseCall(RestUtils.getContentType(), worksheetsubmitRequest5());
//        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());
//
//        call.enqueue(new Callback<WorksheetSubmitResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Response<WorksheetSubmitResponse> response) {
//
//                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
//                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));
//
//                //   dialog.dismiss();
//
//                if (response.body() != null) {
//                    message = response.body().getMessage();
//                    if (200 == response.body().getCode()) {
//                        if (response.body().getData() != null) {
//
////                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                            startActivity(intent);
//                            dialog.dismiss();
//                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                        }
//
//                    } else {
//                        dialog.dismiss();
//                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                    }
//                } else {
//
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());
//
//            }
//        });
//    }
//
//    private WorksheetSubmitRequest worksheetsubmitRequest5() {
//
//        WorksheetSubmitRequest worksheetsubmitRequest = new WorksheetSubmitRequest();
//        worksheetsubmitRequest.setJLS_EWD_WKDATE(str_work_date);
//        worksheetsubmitRequest.setJLS_EWD_BRCODE(str_brcode);
//        worksheetsubmitRequest.setJLS_EWD_PREPBY(str_prepby);
//        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no5);
//        worksheetsubmitRequest.setJLS_EWD_EMPNO(str_emp_no);
//        worksheetsubmitRequest.setJLS_EWD_ACTIVITY(str_emp_activity5);
//        worksheetsubmitRequest.setJLS_EWD_WRKHOUR(str_work_hrs5);
//        worksheetsubmitRequest.setJLS_EWD_PREPDT(currentdate);
//        worksheetsubmitRequest.setJLS_EWD_STATUS("A");
//        worksheetsubmitRequest.setJLS_EWD_MODBY("");
//        worksheetsubmitRequest.setJLS_EWD_MODDT("");
//        worksheetsubmitRequest.setJLS_EWD_DISTANCE(str_km5);
//        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheetsubmitRequest));
//        return worksheetsubmitRequest;
//    }
//
//    private void callWorksheetsubmit6() {
//
//        dialog = new Dialog(WorksheetActivity.this, R.style.NewProgressDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.progroess_popup);
//        dialog.show();
//
//        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
//        Call<WorksheetSubmitResponse> call = apiInterface.WorksheetSubmitResponseCall(RestUtils.getContentType(), worksheetsubmitRequest6());
//        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());
//
//        call.enqueue(new Callback<WorksheetSubmitResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Response<WorksheetSubmitResponse> response) {
//
//                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
//                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));
//
//                //   dialog.dismiss();
//
//                if (response.body() != null) {
//                    message = response.body().getMessage();
//                    if (200 == response.body().getCode()) {
//                        if (response.body().getData() != null) {
//
////                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                            startActivity(intent);
//                            dialog.dismiss();
//                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                        }
//
//                    } else {
//                        dialog.dismiss();
//                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                    }
//                } else {
//
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());
//
//            }
//        });
//    }
//
//    private WorksheetSubmitRequest worksheetsubmitRequest6() {
//
//        WorksheetSubmitRequest worksheetsubmitRequest = new WorksheetSubmitRequest();
//        worksheetsubmitRequest.setJLS_EWD_WKDATE(str_work_date);
//        worksheetsubmitRequest.setJLS_EWD_BRCODE(str_brcode);
//        worksheetsubmitRequest.setJLS_EWD_PREPBY(str_prepby);
//        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no6);
//        worksheetsubmitRequest.setJLS_EWD_EMPNO(str_emp_no);
//        worksheetsubmitRequest.setJLS_EWD_ACTIVITY(str_emp_activity6);
//        worksheetsubmitRequest.setJLS_EWD_WRKHOUR(str_work_hrs6);
//        worksheetsubmitRequest.setJLS_EWD_PREPDT(currentdate);
//        worksheetsubmitRequest.setJLS_EWD_STATUS("A");
//        worksheetsubmitRequest.setJLS_EWD_MODBY("");
//        worksheetsubmitRequest.setJLS_EWD_MODDT("");
//        worksheetsubmitRequest.setJLS_EWD_DISTANCE(str_km6);
//        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheetsubmitRequest));
//        return worksheetsubmitRequest;
//    }
//
//    private void callWorksheetsubmit7() {
//
//        dialog = new Dialog(WorksheetActivity.this, R.style.NewProgressDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.progroess_popup);
//        dialog.show();
//
//        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
//        Call<WorksheetSubmitResponse> call = apiInterface.WorksheetSubmitResponseCall(RestUtils.getContentType(), worksheetsubmitRequest7());
//        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());
//
//        call.enqueue(new Callback<WorksheetSubmitResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Response<WorksheetSubmitResponse> response) {
//
//                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
//                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));
//
//                //   dialog.dismiss();
//
//                if (response.body() != null) {
//                    message = response.body().getMessage();
//                    if (200 == response.body().getCode()) {
//                        if (response.body().getData() != null) {
//
////                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                            startActivity(intent);
//                            dialog.dismiss();
//                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                        }
//
//                    } else {
//                        dialog.dismiss();
//                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                    }
//                } else {
//
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());
//
//            }
//        });
//    }
//
//    private WorksheetSubmitRequest worksheetsubmitRequest7() {
//
//        WorksheetSubmitRequest worksheetsubmitRequest = new WorksheetSubmitRequest();
//        worksheetsubmitRequest.setJLS_EWD_WKDATE(str_work_date);
//        worksheetsubmitRequest.setJLS_EWD_BRCODE(str_brcode);
//        worksheetsubmitRequest.setJLS_EWD_PREPBY(str_prepby);
//        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no7);
//        worksheetsubmitRequest.setJLS_EWD_EMPNO(str_emp_no);
//        worksheetsubmitRequest.setJLS_EWD_ACTIVITY(str_emp_activity7);
//        worksheetsubmitRequest.setJLS_EWD_WRKHOUR(str_work_hrs7);
//        worksheetsubmitRequest.setJLS_EWD_PREPDT(currentdate);
//        worksheetsubmitRequest.setJLS_EWD_STATUS("A");
//        worksheetsubmitRequest.setJLS_EWD_MODBY("");
//        worksheetsubmitRequest.setJLS_EWD_MODDT("");
//        worksheetsubmitRequest.setJLS_EWD_DISTANCE(str_km7);
//        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheetsubmitRequest));
//        return worksheetsubmitRequest;
//    }
//
//    private void callWorksheetsubmit8() {
//
//        dialog = new Dialog(WorksheetActivity.this, R.style.NewProgressDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.progroess_popup);
//        dialog.show();
//
//        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
//        Call<WorksheetSubmitResponse> call = apiInterface.WorksheetSubmitResponseCall(RestUtils.getContentType(), worksheetsubmitRequest8());
//        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());
//
//        call.enqueue(new Callback<WorksheetSubmitResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Response<WorksheetSubmitResponse> response) {
//
//                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
//                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));
//
//                //   dialog.dismiss();
//
//                if (response.body() != null) {
//                    message = response.body().getMessage();
//                    if (200 == response.body().getCode()) {
//                        if (response.body().getData() != null) {
//
////                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                            startActivity(intent);
//                            dialog.dismiss();
//                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                        }
//
//                    } else {
//                        dialog.dismiss();
//                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                    }
//                } else {
//
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());
//
//            }
//        });
//    }
//
//    private WorksheetSubmitRequest worksheetsubmitRequest8() {
//
//        WorksheetSubmitRequest worksheetsubmitRequest = new WorksheetSubmitRequest();
//        worksheetsubmitRequest.setJLS_EWD_WKDATE(str_work_date);
//        worksheetsubmitRequest.setJLS_EWD_BRCODE(str_brcode);
//        worksheetsubmitRequest.setJLS_EWD_PREPBY(str_prepby);
//        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no8);
//        worksheetsubmitRequest.setJLS_EWD_EMPNO(str_emp_no);
//        worksheetsubmitRequest.setJLS_EWD_ACTIVITY(str_emp_activity8);
//        worksheetsubmitRequest.setJLS_EWD_WRKHOUR(str_work_hrs8);
//        worksheetsubmitRequest.setJLS_EWD_PREPDT(currentdate);
//        worksheetsubmitRequest.setJLS_EWD_STATUS("A");
//        worksheetsubmitRequest.setJLS_EWD_MODBY("");
//        worksheetsubmitRequest.setJLS_EWD_MODDT("");
//        worksheetsubmitRequest.setJLS_EWD_DISTANCE(str_km8);
//        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheetsubmitRequest));
//        return worksheetsubmitRequest;
//    }
//
//    private void callWorksheetsubmit9() {
//
//        dialog = new Dialog(WorksheetActivity.this, R.style.NewProgressDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.progroess_popup);
//        dialog.show();
//
//        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
//        Call<WorksheetSubmitResponse> call = apiInterface.WorksheetSubmitResponseCall(RestUtils.getContentType(), worksheetsubmitRequest9());
//        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());
//
//        call.enqueue(new Callback<WorksheetSubmitResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Response<WorksheetSubmitResponse> response) {
//
//                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
//                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));
//
//                //   dialog.dismiss();
//
//                if (response.body() != null) {
//                    message = response.body().getMessage();
//                    if (200 == response.body().getCode()) {
//                        if (response.body().getData() != null) {
//
////                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                            startActivity(intent);
//                            dialog.dismiss();
//                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                        }
//
//                    } else {
//                        dialog.dismiss();
//                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                    }
//                } else {
//
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());
//
//            }
//        });
//    }
//
//    private WorksheetSubmitRequest worksheetsubmitRequest9() {
//
//        WorksheetSubmitRequest worksheetsubmitRequest = new WorksheetSubmitRequest();
//        worksheetsubmitRequest.setJLS_EWD_WKDATE(str_work_date);
//        worksheetsubmitRequest.setJLS_EWD_BRCODE(str_brcode);
//        worksheetsubmitRequest.setJLS_EWD_PREPBY(str_prepby);
//        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no9);
//        worksheetsubmitRequest.setJLS_EWD_EMPNO(str_emp_no);
//        worksheetsubmitRequest.setJLS_EWD_ACTIVITY(str_emp_activity9);
//        worksheetsubmitRequest.setJLS_EWD_WRKHOUR(str_work_hrs9);
//        worksheetsubmitRequest.setJLS_EWD_PREPDT(currentdate);
//        worksheetsubmitRequest.setJLS_EWD_STATUS("A");
//        worksheetsubmitRequest.setJLS_EWD_MODBY("");
//        worksheetsubmitRequest.setJLS_EWD_MODDT("");
//        worksheetsubmitRequest.setJLS_EWD_DISTANCE(str_km9);
//        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheetsubmitRequest));
//        return worksheetsubmitRequest;
//    }
//
//    private void callWorksheetsubmit10() {
//
//        dialog = new Dialog(WorksheetActivity.this, R.style.NewProgressDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.progroess_popup);
//        dialog.show();
//
//        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
//        Call<WorksheetSubmitResponse> call = apiInterface.WorksheetSubmitResponseCall(RestUtils.getContentType(), worksheetsubmitRequest10());
//        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());
//
//        call.enqueue(new Callback<WorksheetSubmitResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Response<WorksheetSubmitResponse> response) {
//
//                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
//                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));
//
//                //   dialog.dismiss();
//
//                if (response.body() != null) {
//                    message = response.body().getMessage();
//                    if (200 == response.body().getCode()) {
//                        if (response.body().getData() != null) {
//
////                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                            startActivity(intent);
//                            dialog.dismiss();
//                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                        }
//
//                    } else {
//                        dialog.dismiss();
//                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                    }
//                } else {
//
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());
//
//            }
//        });
//    }
//
//    private WorksheetSubmitRequest worksheetsubmitRequest10() {
//
//        WorksheetSubmitRequest worksheetsubmitRequest = new WorksheetSubmitRequest();
//        worksheetsubmitRequest.setJLS_EWD_WKDATE(str_work_date);
//        worksheetsubmitRequest.setJLS_EWD_BRCODE(str_brcode);
//        worksheetsubmitRequest.setJLS_EWD_PREPBY(str_prepby);
//        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no10);
//        worksheetsubmitRequest.setJLS_EWD_EMPNO(str_emp_no);
//        worksheetsubmitRequest.setJLS_EWD_ACTIVITY(str_emp_activity10);
//        worksheetsubmitRequest.setJLS_EWD_WRKHOUR(str_work_hrs10);
//        worksheetsubmitRequest.setJLS_EWD_PREPDT(currentdate);
//        worksheetsubmitRequest.setJLS_EWD_STATUS("A");
//        worksheetsubmitRequest.setJLS_EWD_MODBY("");
//        worksheetsubmitRequest.setJLS_EWD_MODDT("");
//        worksheetsubmitRequest.setJLS_EWD_DISTANCE(str_km10);
//        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheetsubmitRequest));
//        return worksheetsubmitRequest;
//    }
//
//    private void callWorksheetsubmit11() {
//
//        dialog = new Dialog(WorksheetActivity.this, R.style.NewProgressDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.progroess_popup);
//        dialog.show();
//
//        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
//        Call<WorksheetSubmitResponse> call = apiInterface.WorksheetSubmitResponseCall(RestUtils.getContentType(), worksheetsubmitRequest11());
//        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());
//
//        call.enqueue(new Callback<WorksheetSubmitResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Response<WorksheetSubmitResponse> response) {
//
//                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
//                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));
//
//                //   dialog.dismiss();
//
//                if (response.body() != null) {
//                    message = response.body().getMessage();
//                    if (200 == response.body().getCode()) {
//                        if (response.body().getData() != null) {
//
////                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                            startActivity(intent);
//                            dialog.dismiss();
//                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                        }
//
//                    } else {
//                        dialog.dismiss();
//                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                    }
//                } else {
//
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());
//
//            }
//        });
//    }
//
//    private WorksheetSubmitRequest worksheetsubmitRequest11() {
//
//        WorksheetSubmitRequest worksheetsubmitRequest = new WorksheetSubmitRequest();
//        worksheetsubmitRequest.setJLS_EWD_WKDATE(str_work_date);
//        worksheetsubmitRequest.setJLS_EWD_BRCODE(str_brcode);
//        worksheetsubmitRequest.setJLS_EWD_PREPBY(str_prepby);
//        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no11);
//        worksheetsubmitRequest.setJLS_EWD_EMPNO(str_emp_no);
//        worksheetsubmitRequest.setJLS_EWD_ACTIVITY(str_emp_activity11);
//        worksheetsubmitRequest.setJLS_EWD_WRKHOUR(str_work_hrs11);
//        worksheetsubmitRequest.setJLS_EWD_PREPDT(currentdate);
//        worksheetsubmitRequest.setJLS_EWD_STATUS("A");
//        worksheetsubmitRequest.setJLS_EWD_MODBY("");
//        worksheetsubmitRequest.setJLS_EWD_MODDT("");
//        worksheetsubmitRequest.setJLS_EWD_DISTANCE(str_km11);
//        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheetsubmitRequest));
//        return worksheetsubmitRequest;
//    }
//
//    private void callWorksheetsubmit12() {
//
//        dialog = new Dialog(WorksheetActivity.this, R.style.NewProgressDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.progroess_popup);
//        dialog.show();
//
//        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
//        Call<WorksheetSubmitResponse> call = apiInterface.WorksheetSubmitResponseCall(RestUtils.getContentType(), worksheetsubmitRequest12());
//        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());
//
//        call.enqueue(new Callback<WorksheetSubmitResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Response<WorksheetSubmitResponse> response) {
//
//                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
//                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));
//
//                //   dialog.dismiss();
//
//                if (response.body() != null) {
//                    message = response.body().getMessage();
//                    if (200 == response.body().getCode()) {
//                        if (response.body().getData() != null) {
//
////                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                            startActivity(intent);
//                            dialog.dismiss();
//                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                        }
//
//                    } else {
//                        dialog.dismiss();
//                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                    }
//                } else {
//
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());
//
//            }
//        });
//    }
//
//    private WorksheetSubmitRequest worksheetsubmitRequest12() {
//
//        WorksheetSubmitRequest worksheetsubmitRequest = new WorksheetSubmitRequest();
//        worksheetsubmitRequest.setJLS_EWD_WKDATE(str_work_date);
//        worksheetsubmitRequest.setJLS_EWD_BRCODE(str_brcode);
//        worksheetsubmitRequest.setJLS_EWD_PREPBY(str_prepby);
//        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no12);
//        worksheetsubmitRequest.setJLS_EWD_EMPNO(str_emp_no);
//        worksheetsubmitRequest.setJLS_EWD_ACTIVITY(str_emp_activity12);
//        worksheetsubmitRequest.setJLS_EWD_WRKHOUR(str_work_hrs12);
//        worksheetsubmitRequest.setJLS_EWD_PREPDT(currentdate);
//        worksheetsubmitRequest.setJLS_EWD_STATUS("A");
//        worksheetsubmitRequest.setJLS_EWD_MODBY("");
//        worksheetsubmitRequest.setJLS_EWD_MODDT("");
//        worksheetsubmitRequest.setJLS_EWD_DISTANCE(str_km12);
//        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheetsubmitRequest));
//        return worksheetsubmitRequest;
//    }
//
//    private void callWorksheetsubmit13() {
//
//        dialog = new Dialog(WorksheetActivity.this, R.style.NewProgressDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.progroess_popup);
//        dialog.show();
//
//        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
//        Call<WorksheetSubmitResponse> call = apiInterface.WorksheetSubmitResponseCall(RestUtils.getContentType(), worksheetsubmitRequest13());
//        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());
//
//        call.enqueue(new Callback<WorksheetSubmitResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Response<WorksheetSubmitResponse> response) {
//
//                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
//                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));
//
//                //   dialog.dismiss();
//
//                if (response.body() != null) {
//                    message = response.body().getMessage();
//                    if (200 == response.body().getCode()) {
//                        if (response.body().getData() != null) {
//
////                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                            startActivity(intent);
//                            dialog.dismiss();
//                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                        }
//
//                    } else {
//                        dialog.dismiss();
//                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                    }
//                } else {
//
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());
//
//            }
//        });
//    }
//
//    private WorksheetSubmitRequest worksheetsubmitRequest13() {
//
//        WorksheetSubmitRequest worksheetsubmitRequest = new WorksheetSubmitRequest();
//        worksheetsubmitRequest.setJLS_EWD_WKDATE(str_work_date);
//        worksheetsubmitRequest.setJLS_EWD_BRCODE(str_brcode);
//        worksheetsubmitRequest.setJLS_EWD_PREPBY(str_prepby);
//        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no13);
//        worksheetsubmitRequest.setJLS_EWD_EMPNO(str_emp_no);
//        worksheetsubmitRequest.setJLS_EWD_ACTIVITY(str_emp_activity13);
//        worksheetsubmitRequest.setJLS_EWD_WRKHOUR(str_work_hrs13);
//        worksheetsubmitRequest.setJLS_EWD_PREPDT(currentdate);
//        worksheetsubmitRequest.setJLS_EWD_STATUS("A");
//        worksheetsubmitRequest.setJLS_EWD_MODBY("");
//        worksheetsubmitRequest.setJLS_EWD_MODDT("");
//        worksheetsubmitRequest.setJLS_EWD_DISTANCE(str_km13);
//        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheetsubmitRequest));
//        return worksheetsubmitRequest;
//    }
//
//    private void callWorksheetsubmit14() {
//
//        dialog = new Dialog(WorksheetActivity.this, R.style.NewProgressDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.progroess_popup);
//        dialog.show();
//
//        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
//        Call<WorksheetSubmitResponse> call = apiInterface.WorksheetSubmitResponseCall(RestUtils.getContentType(), worksheetsubmitRequest14());
//        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());
//
//        call.enqueue(new Callback<WorksheetSubmitResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Response<WorksheetSubmitResponse> response) {
//
//                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
//                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));
//
//                //   dialog.dismiss();
//
//                if (response.body() != null) {
//                    message = response.body().getMessage();
//                    if (200 == response.body().getCode()) {
//                        if (response.body().getData() != null) {
//
////                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                            startActivity(intent);
//                            dialog.dismiss();
//                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                        }
//
//                    } else {
//                        dialog.dismiss();
//                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                    }
//                } else {
//
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());
//
//            }
//        });
//    }
//
//    private WorksheetSubmitRequest worksheetsubmitRequest14() {
//
//        WorksheetSubmitRequest worksheetsubmitRequest = new WorksheetSubmitRequest();
//        worksheetsubmitRequest.setJLS_EWD_WKDATE(str_work_date);
//        worksheetsubmitRequest.setJLS_EWD_BRCODE(str_brcode);
//        worksheetsubmitRequest.setJLS_EWD_PREPBY(str_prepby);
//        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no14);
//        worksheetsubmitRequest.setJLS_EWD_EMPNO(str_emp_no);
//        worksheetsubmitRequest.setJLS_EWD_ACTIVITY(str_emp_activity14);
//        worksheetsubmitRequest.setJLS_EWD_WRKHOUR(str_work_hrs14);
//        worksheetsubmitRequest.setJLS_EWD_PREPDT(currentdate);
//        worksheetsubmitRequest.setJLS_EWD_STATUS("A");
//        worksheetsubmitRequest.setJLS_EWD_MODBY("");
//        worksheetsubmitRequest.setJLS_EWD_MODDT("");
//        worksheetsubmitRequest.setJLS_EWD_DISTANCE(str_km14);
//        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheetsubmitRequest));
//        return worksheetsubmitRequest;
//    }
//
//    private void callWorksheetsubmit15() {
//
//        dialog = new Dialog(WorksheetActivity.this, R.style.NewProgressDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.progroess_popup);
//        dialog.show();
//
//        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
//        Call<WorksheetSubmitResponse> call = apiInterface.WorksheetSubmitResponseCall(RestUtils.getContentType(), worksheetsubmitRequest15());
//        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());
//
//        call.enqueue(new Callback<WorksheetSubmitResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Response<WorksheetSubmitResponse> response) {
//
//                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
//                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));
//
//                //   dialog.dismiss();
//
//                if (response.body() != null) {
//                    message = response.body().getMessage();
//                    if (200 == response.body().getCode()) {
//                        if (response.body().getData() != null) {
//
////                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                            startActivity(intent);
//                            dialog.dismiss();
//                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                        }
//
//                    } else {
//                        dialog.dismiss();
//                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                    }
//                } else {
//
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());
//
//            }
//        });
//    }
//
//    private WorksheetSubmitRequest worksheetsubmitRequest15() {
//
//        WorksheetSubmitRequest worksheetsubmitRequest = new WorksheetSubmitRequest();
//        worksheetsubmitRequest.setJLS_EWD_WKDATE(str_work_date);
//        worksheetsubmitRequest.setJLS_EWD_BRCODE(str_brcode);
//        worksheetsubmitRequest.setJLS_EWD_PREPBY(str_prepby);
//        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no15);
//        worksheetsubmitRequest.setJLS_EWD_EMPNO(str_emp_no);
//        worksheetsubmitRequest.setJLS_EWD_ACTIVITY(str_emp_activity15);
//        worksheetsubmitRequest.setJLS_EWD_WRKHOUR(str_work_hrs15);
//        worksheetsubmitRequest.setJLS_EWD_PREPDT(currentdate);
//        worksheetsubmitRequest.setJLS_EWD_STATUS("A");
//        worksheetsubmitRequest.setJLS_EWD_MODBY("");
//        worksheetsubmitRequest.setJLS_EWD_MODDT("");
//        worksheetsubmitRequest.setJLS_EWD_DISTANCE(str_km15);
//        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheetsubmitRequest));
//        return worksheetsubmitRequest;
//    }
//
//    private void callWorksheetsubmit16() {
//
//        dialog = new Dialog(WorksheetActivity.this, R.style.NewProgressDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.progroess_popup);
//        dialog.show();
//
//        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
//        Call<WorksheetSubmitResponse> call = apiInterface.WorksheetSubmitResponseCall(RestUtils.getContentType(), worksheetsubmitRequest16());
//        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());
//
//        call.enqueue(new Callback<WorksheetSubmitResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Response<WorksheetSubmitResponse> response) {
//
//                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
//                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));
//
//                //   dialog.dismiss();
//
//                if (response.body() != null) {
//                    message = response.body().getMessage();
//                    if (200 == response.body().getCode()) {
//                        if (response.body().getData() != null) {
//
////                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                            startActivity(intent);
//                            dialog.dismiss();
//                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                        }
//
//                    } else {
//                        dialog.dismiss();
//                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                    }
//                } else {
//
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());
//
//            }
//        });
//    }
//
//    private WorksheetSubmitRequest worksheetsubmitRequest16() {
//
//        WorksheetSubmitRequest worksheetsubmitRequest = new WorksheetSubmitRequest();
//        worksheetsubmitRequest.setJLS_EWD_WKDATE(str_work_date);
//        worksheetsubmitRequest.setJLS_EWD_BRCODE(str_brcode);
//        worksheetsubmitRequest.setJLS_EWD_PREPBY(str_prepby);
//        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no16);
//        worksheetsubmitRequest.setJLS_EWD_EMPNO(str_emp_no);
//        worksheetsubmitRequest.setJLS_EWD_ACTIVITY(str_emp_activity16);
//        worksheetsubmitRequest.setJLS_EWD_WRKHOUR(str_work_hrs16);
//        worksheetsubmitRequest.setJLS_EWD_PREPDT(currentdate);
//        worksheetsubmitRequest.setJLS_EWD_STATUS("A");
//        worksheetsubmitRequest.setJLS_EWD_MODBY("");
//        worksheetsubmitRequest.setJLS_EWD_MODDT("");
//        worksheetsubmitRequest.setJLS_EWD_DISTANCE(str_km16);
//        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheetsubmitRequest));
//        return worksheetsubmitRequest;
//    }
//
//    private void callWorksheetsubmit17() {
//
//        dialog = new Dialog(WorksheetActivity.this, R.style.NewProgressDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.progroess_popup);
//        dialog.show();
//
//        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
//        Call<WorksheetSubmitResponse> call = apiInterface.WorksheetSubmitResponseCall(RestUtils.getContentType(), worksheetsubmitRequest17());
//        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());
//
//        call.enqueue(new Callback<WorksheetSubmitResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Response<WorksheetSubmitResponse> response) {
//
//                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
//                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));
//
//                //   dialog.dismiss();
//
//                if (response.body() != null) {
//                    message = response.body().getMessage();
//                    if (200 == response.body().getCode()) {
//                        if (response.body().getData() != null) {
//
////                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                            startActivity(intent);
//                            dialog.dismiss();
//                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                        }
//
//                    } else {
//                        dialog.dismiss();
//                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                    }
//                } else {
//
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());
//
//            }
//        });
//    }
//
//    private WorksheetSubmitRequest worksheetsubmitRequest17() {
//
//        WorksheetSubmitRequest worksheetsubmitRequest = new WorksheetSubmitRequest();
//        worksheetsubmitRequest.setJLS_EWD_WKDATE(str_work_date);
//        worksheetsubmitRequest.setJLS_EWD_BRCODE(str_brcode);
//        worksheetsubmitRequest.setJLS_EWD_PREPBY(str_prepby);
//        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no17);
//        worksheetsubmitRequest.setJLS_EWD_EMPNO(str_emp_no);
//        worksheetsubmitRequest.setJLS_EWD_ACTIVITY(str_emp_activity17);
//        worksheetsubmitRequest.setJLS_EWD_WRKHOUR(str_work_hrs17);
//        worksheetsubmitRequest.setJLS_EWD_PREPDT(currentdate);
//        worksheetsubmitRequest.setJLS_EWD_STATUS("A");
//        worksheetsubmitRequest.setJLS_EWD_MODBY("");
//        worksheetsubmitRequest.setJLS_EWD_MODDT("");
//        worksheetsubmitRequest.setJLS_EWD_DISTANCE(str_km17);
//        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheetsubmitRequest));
//        return worksheetsubmitRequest;
//    }
//
//    private void callWorksheetsubmit18() {
//
//        dialog = new Dialog(WorksheetActivity.this, R.style.NewProgressDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.progroess_popup);
//        dialog.show();
//
//        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
//        Call<WorksheetSubmitResponse> call = apiInterface.WorksheetSubmitResponseCall(RestUtils.getContentType(), worksheetsubmitRequest18());
//        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());
//
//        call.enqueue(new Callback<WorksheetSubmitResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Response<WorksheetSubmitResponse> response) {
//
//                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
//                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));
//
//                //   dialog.dismiss();
//
//                if (response.body() != null) {
//                    message = response.body().getMessage();
//                    if (200 == response.body().getCode()) {
//                        if (response.body().getData() != null) {
//
////                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                            startActivity(intent);
//                            dialog.dismiss();
//                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                        }
//
//                    } else {
//                        dialog.dismiss();
//                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                    }
//                } else {
//
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());
//
//            }
//        });
//    }
//
//    private WorksheetSubmitRequest worksheetsubmitRequest18() {
//
//        WorksheetSubmitRequest worksheetsubmitRequest = new WorksheetSubmitRequest();
//        worksheetsubmitRequest.setJLS_EWD_WKDATE(str_work_date);
//        worksheetsubmitRequest.setJLS_EWD_BRCODE(str_brcode);
//        worksheetsubmitRequest.setJLS_EWD_PREPBY(str_prepby);
//        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no18);
//        worksheetsubmitRequest.setJLS_EWD_EMPNO(str_emp_no);
//        worksheetsubmitRequest.setJLS_EWD_ACTIVITY(str_emp_activity18);
//        worksheetsubmitRequest.setJLS_EWD_WRKHOUR(str_work_hrs18);
//        worksheetsubmitRequest.setJLS_EWD_PREPDT(currentdate);
//        worksheetsubmitRequest.setJLS_EWD_STATUS("A");
//        worksheetsubmitRequest.setJLS_EWD_MODBY("");
//        worksheetsubmitRequest.setJLS_EWD_MODDT("");
//        worksheetsubmitRequest.setJLS_EWD_DISTANCE(str_km18);
//        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheetsubmitRequest));
//        return worksheetsubmitRequest;
//    }
//
//    private void callWorksheetsubmit19() {
//
//        dialog = new Dialog(WorksheetActivity.this, R.style.NewProgressDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.progroess_popup);
//        dialog.show();
//
//        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
//        Call<WorksheetSubmitResponse> call = apiInterface.WorksheetSubmitResponseCall(RestUtils.getContentType(), worksheetsubmitRequest19());
//        Log.i(TAG, "callAttendanceHelperList url  :%s" + " " + call.request().url());
//
//        call.enqueue(new Callback<WorksheetSubmitResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Response<WorksheetSubmitResponse> response) {
//
//                Log.i(TAG, "callAttendanceHelperList: url  : -> " + call.request().url());
//                Log.i(TAG, " callAttendanceHelperList: Response -> " + new Gson().toJson(response.body()));
//
//                //   dialog.dismiss();
//
//                if (response.body() != null) {
//                    message = response.body().getMessage();
//                    if (200 == response.body().getCode()) {
//                        if (response.body().getData() != null) {
//
////                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                            startActivity(intent);
//                            dialog.dismiss();
//                            Toasty.success(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                        }
//
//                    } else {
//                        dialog.dismiss();
//                        Toasty.warning(getApplicationContext(), "" + message, Toasty.LENGTH_LONG).show();
//                    }
//                } else {
//
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<WorksheetSubmitResponse> call, @NonNull Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                Log.e(TAG, "callAttendanceHelperList: onFailure: error -> " + t.getMessage());
//
//            }
//        });
//    }
//
//    private WorksheetSubmitRequest worksheetsubmitRequest19() {
//
//        WorksheetSubmitRequest worksheetsubmitRequest = new WorksheetSubmitRequest();
//        worksheetsubmitRequest.setJLS_EWD_WKDATE(str_work_date);
//        worksheetsubmitRequest.setJLS_EWD_BRCODE(str_brcode);
//        worksheetsubmitRequest.setJLS_EWD_PREPBY(str_prepby);
//        worksheetsubmitRequest.setJLS_EWD_JOBNO(str_job_no19);
//        worksheetsubmitRequest.setJLS_EWD_EMPNO(str_emp_no);
//        worksheetsubmitRequest.setJLS_EWD_ACTIVITY(str_emp_activity19);
//        worksheetsubmitRequest.setJLS_EWD_WRKHOUR(str_work_hrs19);
//        worksheetsubmitRequest.setJLS_EWD_PREPDT(currentdate);
//        worksheetsubmitRequest.setJLS_EWD_STATUS("A");
//        worksheetsubmitRequest.setJLS_EWD_MODBY("");
//        worksheetsubmitRequest.setJLS_EWD_MODDT("");
//        worksheetsubmitRequest.setJLS_EWD_DISTANCE(str_km19);
//        Log.w(TAG, "loginRequest " + new Gson().toJson(worksheetsubmitRequest));
//        return worksheetsubmitRequest;
//    }


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

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {

            str_job_no = intent.getStringExtra("ad_jobno");
            str_emp_activity = intent.getStringExtra("ad_empactivity");
            str_work_hrs = intent.getStringExtra("ad_workhrs");
            str_km = intent.getStringExtra("ad_km");

            job_no.setText(str_job_no);
            emp_activity.setText(str_emp_activity);
            work_hrs.setText(str_work_hrs);
            km.setText(str_km);
        }
    };




    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(WorksheetActivity.this, DailyAttendanceActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.new_right, R.anim.new_left);
        finish();
    }
}