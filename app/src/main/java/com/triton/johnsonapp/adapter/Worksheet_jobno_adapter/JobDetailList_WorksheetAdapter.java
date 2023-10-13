package com.triton.johnsonapp.adapter.Worksheet_jobno_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnsonapp.R;
import com.triton.johnsonapp.WorksheetActivity;
import com.triton.johnsonapp.Worksheet_table_AddActivity;
import com.triton.johnsonapp.activity.CustomerDetailsActivity;
import com.triton.johnsonapp.responsepojo.JobNoManagementResponse;
import com.triton.johnsonapp.responsepojo.Worksheet_jobno_listResponse;

import java.util.List;


public class JobDetailList_WorksheetAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "JobDetailListAdapter";
    private List<Worksheet_jobno_listResponse.DataBean> dataBeanList;
    private Context context;

    Worksheet_jobno_listResponse.DataBean currentItem;

    private int size;
    String status,recycler_count;
    private String UKEY;
    String fromactivity,date,prepby,emp_no,br_code,emp_activity,workhrs,km,remark,btn_edit,edit_date,emp_name,dummy_emp_act,job_dummy;
    int hrs_count;
    public JobDetailList_WorksheetAdapter(Context context, List<Worksheet_jobno_listResponse.DataBean> dataBeanList, String status , String fromactivity) {
        this.context = context;
        this.dataBeanList = dataBeanList;
        this.fromactivity = fromactivity ;
        this.status = status ;


    }

    public void filterList(List<Worksheet_jobno_listResponse.DataBean> filterllist)
    {
        dataBeanList = filterllist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_staticdatalist, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = dataBeanList.get(position);
        Log.i(TAG,"check size of data "+ dataBeanList.size());

        if(dataBeanList.get(position).getJob_detail_no() != null && !dataBeanList.get(position).getJob_detail_no().equals("")){
            holder.txt_serv_title.setText(dataBeanList.get(position).getJob_detail_no());
        }else{
            holder.txt_serv_title.setText("");

        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        date = sharedPreferences.getString("date", "");
        br_code = sharedPreferences.getString("br_code", "");
        prepby = sharedPreferences.getString("prepby", "");
        emp_no = sharedPreferences.getString("emp_no", "");
        emp_activity = sharedPreferences.getString("emp_activity", "");
        workhrs = sharedPreferences.getString("workhrs", "");
        km = sharedPreferences.getString("km", "");
        remark = sharedPreferences.getString("remark", "");
        btn_edit = sharedPreferences.getString("btn_edit", "");
        edit_date = sharedPreferences.getString("edit_date", "");
        emp_name = sharedPreferences.getString("emp_name", "");
        status = sharedPreferences.getString("status", "");
        hrs_count = sharedPreferences.getInt("hrs_count", 0);
        recycler_count = sharedPreferences.getString("recycler_count","");
        job_dummy = sharedPreferences.getString("job_dummy", "");
        dummy_emp_act = sharedPreferences.getString("dummy_emp_act", "");

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("job_dummy",job_dummy);
        editor.putString("dummy_emp_act",dummy_emp_act);
        editor.apply();

        holder.cv_root.setOnClickListener(v -> {

            Intent intent = new Intent(context, Worksheet_table_AddActivity.class);
            intent.putExtra("job_id",dataBeanList.get(position).get_id());
            intent.putExtra("job_detail_no",dataBeanList.get(position).getJob_detail_no());
            intent.putExtra("date",date);
            intent.putExtra("btn_edit", "not_edit");
            intent.putExtra("br_code", br_code);
            intent.putExtra("prepby", prepby);
            intent.putExtra("emp_no", emp_no);
            intent.putExtra("emp_activity", emp_activity);
            intent.putExtra("workhrs", workhrs);
            intent.putExtra("km", km);
            intent.putExtra("remark", remark);
            intent.putExtra("btn_edit",btn_edit);
            intent.putExtra("edit_date",edit_date);
            intent.putExtra("status",status);
            intent.putExtra("hrs_count",hrs_count);
            intent.putExtra("emp_name",emp_name);
            intent.putExtra("recycler_count",recycler_count);
            context.startActivity(intent);

            System.out.println("hhhhhh---->" + hrs_count);
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
        public TextView txt_serv_title,txt_serv_updatedat,txt_serv_createdat;
        ImageView img_next;
        CardView cv_root;

        public ViewHolderOne(View itemView) {
            super(itemView);
            txt_serv_title = itemView.findViewById(R.id.txt_serv_title);
            txt_serv_updatedat = itemView.findViewById(R.id.txt_serv_updatedat);
            txt_serv_createdat = itemView.findViewById(R.id.txt_serv_createdat);
            img_next = itemView.findViewById(R.id.img_next);
            cv_root = itemView.findViewById(R.id.cv_root);


        }




    }

    public void loadMoreData(List<Worksheet_jobno_listResponse.DataBean> newData) {
        int startPosition = dataBeanList.size();
        dataBeanList.addAll(newData);
        notifyDataSetChanged();
    }




}
