package com.triton.johnsonapp.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnsonapp.DailyAttendanceActivity;
import com.triton.johnsonapp.Worksheet1Activity;
import com.triton.johnsonapp.R;
import com.triton.johnsonapp.interfaces.OnItemClickDataChangeListener;
import com.triton.johnsonapp.responsepojo.AttendanceHelperListResponse;
import com.triton.johnsonapp.responsepojo.LocalDummyResponse;

import java.util.List;

public class DailyAttendanceTableListAdapter extends RecyclerView.Adapter<DailyAttendanceTableListAdapter.ViewHolderOne> {

    AttendanceHelperListResponse.Data currentItem;
    private String TAG = DailyAttendanceTableListAdapter.class.getSimpleName();
    private AttendanceHelperListResponse attendanceHelperListResponse;
    private OnItemClickDataChangeListener onItemClickDataChangeListener;
    String date;
    private Context context;
    Dialog dialog;
    private Dialog alertDialog;
    String job_id,br_code,prepby,emp_no,emp_name,status,back="";
    int hrs_count;

    public DailyAttendanceTableListAdapter(AttendanceHelperListResponse attendanceHelperListResponse, OnItemClickDataChangeListener onItemClickDataChangeListener) {
        this.attendanceHelperListResponse = attendanceHelperListResponse;
        this.onItemClickDataChangeListener = onItemClickDataChangeListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderOne onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_daily_attendance_table_list, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderOne holder, int position) {
        initLayoutOne((ViewHolderOne) holder, position);
    }

    @SuppressLint({"LogNotTimber", "SetTextI18n"})
    private void initLayoutOne(ViewHolderOne holder, final int position) {

        currentItem = attendanceHelperListResponse.getData().get(position);


        int pos = position + 1;
        if (currentItem.getJlsHaEnggname() != null) {
            holder.s_no.setText(pos + "");
            holder.employee_id.setText(currentItem.getJlsHaHelpercode());
            holder.route_code.setText(currentItem.getJlsHaRoutecd());
            holder.employee_name.setText(currentItem.getJlsHaHelpername());

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(holder.attendance_menu.getContext());
            String  job_id = sharedPreferences.getString("job_id", "");
            date = sharedPreferences.getString("date", "");
            Log.w("job_id",job_id);

            Log.w("valueee",currentItem.getJlsHaStatus());

            if (currentItem.getJlsHaStatus().isEmpty()) {
                holder.attendance_menu.setText("Select");
            }
            else {
//                holder.attendance_menu.setText(currentItem.getJlsHaStatus());
//                holder.attendance_menu.setText(currentItem.getStatusDisplay());

                switch (currentItem.getJlsHaStatus()) {
                    case "FNL": {
                        holder.attendance_menu.setText("FN - Leave");
                    }
                    break;
                    case "ANL": {
                        holder.attendance_menu.setText("AN - Leave");
                    }
                    break;
                    case "FDL": {
                        holder.attendance_menu.setText("Full Leave");
                    }
                    break;
                    case "PRE": {
                        holder.attendance_menu.setText("Present");
                    }
                    break;

                    case "ABS": {
                        holder.attendance_menu.setText("Absent");
                    }
                    break;

                    case "COM":{
                        holder.attendance_menu.setText("Com-Off");
                    }
                    break;
                    case "HOLI":{
                        holder.attendance_menu.setText("Holiday");
                    }
                    break;
                    case "Station":{
                        holder.attendance_menu.setText("Out-Station");
                    }
                    break;

//                    case "PRM": {
//                        String strStartTime = "", strEndTime = "";
//
//                        if (!currentItem.getJlsHaFromtime().isEmpty() && !currentItem.getJlsHaTotime().isEmpty()) {
//                            String[] fromtime = currentItem.getJlsHaFromtime().split(" ");
//                            strStartTime = fromtime[1].trim();
//                            String[] toTime = currentItem.getJlsHaTotime().split(" ");
//                            strEndTime = toTime[1].trim();
//                            holder.attendance_menu.setText("Permission\n" + strStartTime + " - " + strEndTime);
//                        } else {
//                            holder.attendance_menu.setText("Permission");
//                        }
//
//                    }
//                    break;
                }
            }
        }

        if (currentItem.getJlsHaRemarks() == null || currentItem.getJlsHaRemarks().isEmpty()) {
            holder.btn_add_remark.setVisibility(View.VISIBLE);
            holder.btn_edit_remark.setVisibility(View.GONE);
            holder.btn_delete_remark.setVisibility(View.GONE);
        } else {
            holder.btn_add_remark.setVisibility(View.GONE);
            holder.btn_edit_remark.setVisibility(View.VISIBLE);
            holder.btn_delete_remark.setVisibility(View.VISIBLE);
        }

        holder.attendance_menu.setOnClickListener(view -> {
            if (currentItem.getJlsHaValue().equalsIgnoreCase("Not Marked")) {
                holder.ivDown.performClick();
            } else {
                Toast.makeText(holder.attendance_menu.getContext(), "Attendance already submitted for this Helper", Toast.LENGTH_SHORT).show();
            }
        });

        holder.ivDown.setOnClickListener(view -> {
            if (currentItem.getJlsHaValue().equalsIgnoreCase("Not Marked")) {
                if (DailyAttendanceActivity.formattedDate.isEmpty()) {
                    Toast.makeText(holder.ivDown.getContext(), "Please select the Date", Toast.LENGTH_SHORT).show();
                } else {
                    callAttendancePopUp(position, holder);
                }
            } else {
                Toast.makeText(holder.attendance_menu.getContext(), "Attendance already submitted for this Helper", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btn_add_remark.setOnClickListener(view -> {

            String ss = holder.attendance_menu.getText().toString();

            if(ss.equals("Select")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(holder.attendance_menu.getContext());
                alertDialogBuilder.setMessage("you can't select this option");
                alertDialogBuilder.setPositiveButton("ok",
                        (arg0, arg1) -> hideLoading());
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
            else if(ss.equals("Full Leave")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(holder.attendance_menu.getContext());
                alertDialogBuilder.setMessage("you can't use this option");
                alertDialogBuilder.setPositiveButton("ok",
                        (arg0, arg1) -> hideLoading());
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
            else if(ss.equals("Absent")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(holder.attendance_menu.getContext());
                alertDialogBuilder.setMessage("you can't use this option");
                alertDialogBuilder.setPositiveButton("ok",
                        (arg0, arg1) -> hideLoading());
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
//              else if(ss.equals("Training")){
//                  System.out.println("work----->" + ss);
//              }
            else {
                Intent intent = new Intent(holder.attendance_menu.getContext(), Worksheet1Activity.class);
                intent.putExtra("br_code",attendanceHelperListResponse.getData().get(position).getJlsHaRoutecd());
                intent.putExtra("prepby",attendanceHelperListResponse.getData().get(position).getJlsHaEnggcode());
                intent.putExtra("emp_no",attendanceHelperListResponse.getData().get(position).getJlsHaHelpercode());
                intent.putExtra("emp_name",attendanceHelperListResponse.getData().get(position).getJlsHaHelpername());
                intent.putExtra("date",date);

                intent.putExtra("status",ss);
                holder.attendance_menu.getContext().startActivity(intent);
            }

            System.out.println("work----->" + attendanceHelperListResponse.getData().get(position).getJlsHaRoutecd());

            System.out.println("date----->" + date);

            System.out.println("emp_name----->" + attendanceHelperListResponse.getData().get(position).getJlsHaEnggname());
        });

        holder.btn_edit_remark.setOnClickListener(view -> {
            if (currentItem.getJlsHaValue().equalsIgnoreCase("Not Marked")) {
                onItemClickDataChangeListener.itemClickDataChangeListener(position, "remark", "edit");
            } else {
                Toast.makeText(holder.attendance_menu.getContext(), "Attendance already submitted for this Helper", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btn_delete_remark.setOnClickListener(view -> {
            if (currentItem.getJlsHaValue().equalsIgnoreCase("Not Marked")) {
                onItemClickDataChangeListener.itemClickDataChangeListener(position, "remark", "delete");
            } else {
                Toast.makeText(holder.attendance_menu.getContext(), "Attendance already submitted for this Helper", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void hideLoading() {
        try {
            alertDialog.dismiss();
        } catch (Exception ignored) {

        }
    }

    private void callAttendancePopUp(int position, ViewHolderOne holder) {
        PopupMenu popup = new PopupMenu(holder.ivDown.getContext(), holder.ivDown);
        //inflating menu from xml resource
        popup.inflate(R.menu.menu_attendance);
        //adding click listener
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_present:
                    //handle menu1 click
//                    holder.attendance_menu.setText("Present");
                    onItemClickDataChangeListener.itemClickDataChangeListener(position, "status", "Present");
                    Log.i(TAG,"present" + position);
                    Log.i(TAG,"present" + "status");
                    break;
                case R.id.menu_leave:
                    //handle menu2 click
//                    holder.attendance_menu.setText("Leave");
                    callLeaveTypePopUp(position, "status", holder);
                    Log.i(TAG,"leave" + position);
                    Log.i(TAG,"leave" + position);
                    break;
//                case R.id.menu_permission:
//                    //handle menu1 click
////                    holder.attendance_menu.setText("Permission");
//                    onItemClickDataChangeListener.itemClickDataChangeListener(position, strParam, "Permission");
////                    callTimePicker(holder);
//                    break;
                case R.id.menu_absent:
                    //handle menu3 click
//                    holder.attendance_menu.setText("Absent");
                    onItemClickDataChangeListener.itemClickDataChangeListener(position, "status", "Absent");
                    Log.i(TAG,"absent" + position);
                    Log.i(TAG,"absent" + "status");
                    break;
                case R.id.menu_comoff:
                    //handle menu4 click
//                    holder.attendance_menu.setText("Com-Off");
                    onItemClickDataChangeListener.itemClickDataChangeListener(position, "status", "Com-Off");
                    Log.i(TAG,"comoff" + position);
                    Log.i(TAG,"comoff" + "status");
                    break;
                case R.id.menu_holiday:
                    //handle menu4 click
//                    holder.attendance_menu.setText("Com-Off");
                    onItemClickDataChangeListener.itemClickDataChangeListener(position, "status", "Holiday");
                    Log.i(TAG,"holiday" + position);
                    Log.i(TAG,"holiday" + "status");
                    break;

                case R.id.menu_station:
                    //handle menu4 click

                    onItemClickDataChangeListener.itemClickDataChangeListener(position, "status", "Out-Station");
                    Log.i(TAG,"Out Station" + position);
                    Log.i(TAG,"Out Station" + "status");
                    break;
            }
            return false;
        });
        //displaying the popup
        popup.show();
    }

    private void callLeaveTypePopUp(int position, String strParam, ViewHolderOne holder) {
        PopupMenu popup = new PopupMenu(holder.ivDown.getContext(), holder.ivDown);
        //inflating menu from xml resource
        popup.inflate(R.menu.menu_leave_type);
        //adding click listener
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_forenoon:
//                    holder.attendance_menu.setText("FN - Leave");
                    onItemClickDataChangeListener.itemClickDataChangeListener(position, strParam, "FN - Leave");
                    break;
                case R.id.menu_afternoon:
//                    holder.attendance_menu.setText("AN - Leave");
                    onItemClickDataChangeListener.itemClickDataChangeListener(position, strParam, "AN - Leave");
                    break;
                case R.id.menu_full:
//                    holder.attendance_menu.setText("Full Leave");
                    onItemClickDataChangeListener.itemClickDataChangeListener(position, strParam, "Full Leave");
                    break;
            }
            return false;
        });
        //displaying the popup
        popup.show();
    }

    @Override
    public int getItemCount() {
        return attendanceHelperListResponse.getData().size();
    }

    public void filterList(List<LocalDummyResponse.DataBean> localDummyList) {
        localDummyList = localDummyList;
        Log.w(TAG, "localDummyList : " + new Gson().toJson(localDummyList));

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView s_no, employee_id, route_code, employee_name, attendance_menu;
        public EditText remark;
        public Spinner spinner_status;
        public ImageView ivDown;
        public Button btn_add_remark, btn_edit_remark, btn_delete_remark;

        public ViewHolderOne(View itemView) {
            super(itemView);

            s_no = itemView.findViewById(R.id.s_no);
            employee_id = itemView.findViewById(R.id.employee_id);
            route_code = itemView.findViewById(R.id.route_code);
            employee_name = itemView.findViewById(R.id.employee_name);
            attendance_menu = itemView.findViewById(R.id.attendance_menu);
            remark = itemView.findViewById(R.id.remark);
            spinner_status = itemView.findViewById(R.id.spinner_status);
            ivDown = itemView.findViewById(R.id.ivDown);
            btn_add_remark = itemView.findViewById(R.id.btn_add_remark);
            btn_edit_remark = itemView.findViewById(R.id.btn_edit_remark);
            btn_delete_remark = itemView.findViewById(R.id.btn_delete_remark);

        }
    }
}
