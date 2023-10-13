package com.triton.johnsonapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.common.StringUtils;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.WorksheetActivity;
import com.triton.johnsonapp.Worksheet_table_AddActivity;
import com.triton.johnsonapp.responsepojo.WorksheetEmpactivityResponse;
import com.triton.johnsonapp.responsepojo.Worksheet_SubmitResponse;

import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;


public class Worksheet_dateAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "JobDetailListAdapter";
    private List<Worksheet_SubmitResponse.Datum> dataBeanList;
    private Context context;
    Worksheet_SubmitResponse.Datum currentItem;
    String data="";
    private int size,sum,work;
    String status;
    private String UKEY;
    String total;
    String fromactivity,job_id,date,str_date,str_brcode,str_status,str_prepby,str_emp_no,str_emp_name,dummy_emp_act;
    int hrs_count;
    public Worksheet_dateAdapter(Context context, List<Worksheet_SubmitResponse.Datum> dataBeanList, String fromactivity) {
        this.context = context;
        this.dataBeanList = dataBeanList;
        this.fromactivity = fromactivity ;
        this.status = status ;
    }

    public void filterList(List<Worksheet_SubmitResponse.Datum> filterllist)
    {
        dataBeanList = filterllist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_worksheet, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);
    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = dataBeanList.get(position);

        holder.ad_job_no.setText(dataBeanList.get(position).getJLS_EWD_JOBNO());
        holder.ad_emp_activity.setText(dataBeanList.get(position).getJLS_EWD_ACTIVITY());
        holder.ad_work_hrs.setText(dataBeanList.get(position).getJLS_EWD_WRKHOUR());
        holder.ad_km.setText(dataBeanList.get(position).getJLS_EWD_DISTANCE());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        str_date = sharedPreferences.getString("date", "");
        str_brcode = sharedPreferences.getString("br_code", "");
        str_prepby = sharedPreferences.getString("prepby", "");
        str_emp_no = sharedPreferences.getString("emp_no", "");
        str_emp_name = sharedPreferences.getString("emp_name", "");
        str_status = sharedPreferences.getString("status", "");
        hrs_count = sharedPreferences.getInt("hrs_count", 0);

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder1 = new StringBuilder();

        for (int i = 0; i < dataBeanList.size(); i++) {

            stringBuilder.append(dataBeanList.get(i).getJLS_EWD_JOBNO() + ",");
            stringBuilder1.append(dataBeanList.get(i).getJLS_EWD_ACTIVITY() + ",");

            if (stringBuilder.toString().endsWith(",")) {
                job_id = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
            }
            if (stringBuilder1.toString().endsWith(",")) {
                dummy_emp_act = stringBuilder1.toString().substring(0, stringBuilder1.toString().length() - 1);
            }
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("job_dummy", job_id);
        editor.putString("dummy_emp_act", dummy_emp_act);
        editor.apply();

        String sucessCount  = String.valueOf(getItemCount());

        work = Integer.parseInt(dataBeanList.get(position).getJLS_EWD_WRKHOUR());

        sum = hrs_count - work;

        holder.ad_btn_edit.setOnClickListener(v -> {

            Intent intent = new Intent(context, Worksheet_table_AddActivity.class);
            intent.putExtra("btn_edit", "edit");
            intent.putExtra("job_id", dataBeanList.get(position).getJLS_EWD_JOBNO());
            intent.putExtra("emp_activity", dataBeanList.get(position).getJLS_EWD_ACTIVITY());
            intent.putExtra("workhrs", dataBeanList.get(position).getJLS_EWD_WRKHOUR());
            intent.putExtra("km", dataBeanList.get(position).getJLS_EWD_DISTANCE());
            intent.putExtra("edit_date", dataBeanList.get(position).getJLS_EWD_WKDATE());
            intent.putExtra("emp_name", str_emp_name);
            intent.putExtra("date", str_date);
            intent.putExtra("hrs_count", sum);
            intent.putExtra("status", str_status);
            intent.putExtra("br_code", str_brcode);
            intent.putExtra("prepby", str_prepby);
            intent.putExtra("emp_no", str_emp_no);
            intent.putExtra("recycler_count", sucessCount);
            context.startActivity(intent);

            System.out.println("hhhhhh---->" + sum);

        });
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        TextView ad_job_no,ad_emp_activity,ad_work_hrs,ad_km;
        ImageView img_next;
        Button ad_btn_edit;
        CardView cv_root;

        public ViewHolderOne(View itemView) {
            super(itemView);
            ad_job_no = itemView.findViewById(R.id.ad_job_no);
            ad_emp_activity = itemView.findViewById(R.id.ad_emp_activity);
            ad_work_hrs = itemView.findViewById(R.id.ad_work_hrs);
            ad_km = itemView.findViewById(R.id.ad_km);
            img_next = itemView.findViewById(R.id.img_next);
            ad_btn_edit = itemView.findViewById(R.id.ad_btn_edit);
            cv_root = itemView.findViewById(R.id.cv_root);
        }
    }
}