package com.triton.johnsonapp.adapter;

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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnsonapp.Open_URL_PDFActivity;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.responsepojo.PDFListResponse;

import java.util.List;


public class PDFListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "JobDetailListAdapter";
    private List<PDFListResponse.DataBean> dataBeanList;
    private Context context;

    PDFListResponse.DataBean currentItem;

    private int size;
    String status;
    private String UKEY;
    String fromactivity,job_id;

    public PDFListAdapter(Context context, List<PDFListResponse.DataBean> dataBeanList) {
        this.context = context;
        this.dataBeanList = dataBeanList;
        this.fromactivity = fromactivity ;
        this.status = status ;


    }

    public void filterList(List<PDFListResponse.DataBean> filterllist)
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

        if(dataBeanList.get(position).getFile_name() != null && !dataBeanList.get(position).getFile_name().equals("")){
            holder.txt_serv_title.setText(dataBeanList.get(position).getFile_name());
        }else{
            holder.txt_serv_title.setText("");

        }

       SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        job_id = sharedPreferences.getString("job_id", "");
        UKEY = sharedPreferences.getString("UKEY", "");

        holder.cv_root.setOnClickListener(v -> {
            Intent intent = new Intent(context, Open_URL_PDFActivity.class);
            intent.putExtra("pdf_path",dataBeanList.get(position).getFile_path());
            intent.putExtra("job_id",job_id);
            intent.putExtra("UKEY",UKEY);
            context.startActivity(intent);
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
