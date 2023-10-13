package com.triton.johnsonapp.infopages;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.triton.johnsonapp.responsepojo.QRCodeListResponse;

import java.util.List;

public class QRAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    String jobid;
    List<QRCodeListResponse.Datum> breedTypedataBeanList;
    PetBreedTypeSelectListener petBreedTypeSelectListener;
    QRCodeListResponse.Datum currentItem;
    SharedPreferences sharedPreferences;

    public QRAdapter(Context applicationContext, List<QRCodeListResponse.Datum> databeanList, String job_id) {

        this.context = applicationContext;
        this.breedTypedataBeanList = databeanList;
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

        if (currentItem.getQRCODE() != null){
            String Material = currentItem.getQRCODE().toString();
            holder.txt_MaterialID.setText(Material);
        }
    }

    @Override
    public int getItemCount() {
        return breedTypedataBeanList.size();
    }

    private class ViewHolderOne extends RecyclerView.ViewHolder {
        TextView txt_MaterialID,txt_Count;
        CardView cv_Material;
        public ViewHolderOne(View view) {
            super(view);
            txt_MaterialID = view.findViewById(R.id.txt_material);
            txt_Count = view.findViewById(R.id.txt_count);
            cv_Material = view.findViewById(R.id.cv_material);
        }
    }
}
