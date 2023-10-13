package com.triton.johnsonapp.infopages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnson_tap_app.PetBreedTypeSelectListener;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.responsepojo.MaterialList_JointInspectionResponse;

import java.util.List;

public class MaterialList_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    String jobid;
    List<MaterialList_JointInspectionResponse.Datum> breedTypedataBeanList;
    PetBreedTypeSelectListener petBreedTypeSelectListener;
    MaterialList_JointInspectionResponse.Datum currentItem;
    SharedPreferences sharedPreferences;

    public MaterialList_Adapter(Context applicationContext, List<MaterialList_JointInspectionResponse.Datum> databeanList, String job_id) {
        this.context = applicationContext;
        this.breedTypedataBeanList = databeanList;
        this.jobid = job_id;

        Log.e("Job ID",""+jobid);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_materiallist_jointinspection, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        initLayoutOne((ViewHolderOne) holder, position);
    }

    private void initLayoutOne(ViewHolderOne holder, int position) {

        currentItem = breedTypedataBeanList.get(position);

        if(currentItem.getMATL_ID() != null){
            String Material = currentItem.getMATL_ID().toString();
            String Desc = currentItem.getMATL_DESC().toString();
            Log.e("Material",""+Material);
            Log.e("Desc",""+Desc);
            holder.txt_MaterialID.setText(Material);
            holder.txt_Desc.setText(Desc);

        }
        if (currentItem.getCNT() != null || currentItem.getSCANNED_COUNT()!=null){

            String Count = currentItem.getCNT().toString();
            String S_Count = currentItem.getSCANNED_COUNT().toString();
            Log.e("Count",""+S_Count + "/" +Count);
            holder.txt_Count.setText(Count);
        }

        holder.cv_Material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Material = breedTypedataBeanList.get(position).getMATL_ID().toString();
                String Count =  breedTypedataBeanList.get(position).getCNT().toString();
                String S_Count =  breedTypedataBeanList.get(position).getSCANNED_COUNT().toString();

                if (Count.equals("0")){


                }

                Intent n_act = new Intent(context, QRCode_Activity.class);
//                n_act.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                n_act.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                n_act.putExtra("job_id", jobid);
                n_act.putExtra("material", Material);
                n_act.putExtra("count", Count);
                n_act.putExtra("scount", S_Count);
                Log.e("JobID " ,"" + jobid + "\n" + " Material ID :" + Material + "\n" +
                        " Total Count :" + Count + "\n" +  " Scan Count :" + S_Count);
                context.startActivity(n_act);
            }
        });


    }

    @Override
    public int getItemCount() {
        return breedTypedataBeanList.size();
    }

    private class ViewHolderOne extends RecyclerView.ViewHolder {

        TextView txt_MaterialID,txt_Count,txt_Desc;
        CardView cv_Material;
        public ViewHolderOne(View view) {
            super(view);
            txt_MaterialID = view.findViewById(R.id.txt_material);
            txt_Count = view.findViewById(R.id.txt_count);
            cv_Material = view.findViewById(R.id.cv_material);
            txt_Desc = view.findViewById(R.id.txt_desc);
        }
    }
}
