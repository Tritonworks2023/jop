package com.triton.johnsonapp.adapter.Worksheet_empactivity_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnsonapp.R;
import com.triton.johnsonapp.WorksheetActivity;
import com.triton.johnsonapp.Worksheet_table_AddActivity;
import com.triton.johnsonapp.responsepojo.JobNoManagementResponse;
import com.triton.johnsonapp.responsepojo.WorksheetEmpactivityResponse;

import java.util.List;


public class Empactivity_WorksheetAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "JobDetailListAdapter";
    private List<WorksheetEmpactivityResponse.DataBean> dataBeanList;
    private Context context;

    WorksheetEmpactivityResponse.DataBean currentItem;

    private int size;
    String status;
    private String UKEY;
    String data="";
    int hrs_count;
    String fromactivity,job_id,date,prepby,emp_no,br_code,remark,km,workhrs,btn_edit,edit_date,emp_name,recycler_count,job_dummy,dummy_emp_act;
    public Empactivity_WorksheetAdapter(Context context, List<WorksheetEmpactivityResponse.DataBean> dataBeanList, String status , String fromactivity) {
        this.context = context;
        this.dataBeanList = dataBeanList;
        this.fromactivity = fromactivity ;
        this.status = status ;


    }

    public void filterList(List<WorksheetEmpactivityResponse.DataBean> filterllist)
    {
        dataBeanList = filterllist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_empacitivity, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);


    }

    @SuppressLint({"SetTextI18n", "LogNotTimber"})
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = dataBeanList.get(position);

        if(dataBeanList.get(position).getAct_name() != null && !dataBeanList.get(position).getAct_name().equals("")){
            holder.txt_serv_title.setText(dataBeanList.get(position).getAct_name());
            holder.txt_serv_updatedat.setText(dataBeanList.get(position).getDesc());
        }else{
            holder.txt_serv_title.setText("");

        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        job_id = sharedPreferences.getString("job_id", "");
        date = sharedPreferences.getString("date", "");
        br_code = sharedPreferences.getString("br_code", "");
        prepby = sharedPreferences.getString("prepby", "");
        emp_no = sharedPreferences.getString("emp_no", "");
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
            intent.putExtra("emp_activity",dataBeanList.get(position).getAct_name());
            intent.putExtra("job_id",job_id);
            intent.putExtra("date",date);
            intent.putExtra("btn_edit", "not_edit");
            intent.putExtra("br_code", br_code);
            intent.putExtra("prepby", prepby);
            intent.putExtra("emp_no", emp_no);
            intent.putExtra("workhrs", workhrs);
            intent.putExtra("km", km);
            intent.putExtra("remark", remark);
            intent.putExtra("btn_edit",btn_edit);
            intent.putExtra("edit_date",edit_date);
            intent.putExtra("hrs_count",hrs_count);
            intent.putExtra("status",status);
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




}
