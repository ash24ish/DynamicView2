package com.ashishbharam.dynamicview2.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashishbharam.dynamicview2.DepartmentModel;
import com.ashishbharam.dynamicview2.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class EmailRvAdapter extends RecyclerView.Adapter<EmailRvAdapter.EmpEmailViewHolder> {

    private static final String TAG = "tag";
    private OnMyItemClickListener onMyItemClickListener;
    private List<DepartmentModel> tempList;

    public EmailRvAdapter(OnMyItemClickListener onMyItemClickListener, List<DepartmentModel> tempList) {
        this.onMyItemClickListener = onMyItemClickListener;
        this.tempList = tempList;
    }

    public interface OnMyItemClickListener {
        void onItemClick(int position);
        void onRemoveItemClick(int position);
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public EmpEmailViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_emp_email_layout, parent, false);
        return new EmpEmailViewHolder(view, onMyItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull EmailRvAdapter.EmpEmailViewHolder holder, int position) {
        DepartmentModel model = tempList.get(position);
        holder.tvEmail.setText(model.getEmployeeEmail());
        String status = model.getEmployeeStatus();
        if (status.equals("Active")) {
            holder.mSwitch.setChecked(true);
            holder.mSwitch.setTextOn("Active");
        }else{
            holder.mSwitch.setTextOff("Inactive");
        }
        /*holder.tvRemoveEmail.setOnClickListener(view -> Log.d(TAG, "tvRemoveEmail: "
                + holder.getBindingAdapterPosition()
                + " : " + holder.tvEmail.getText()));*/
    }

    @Override
    public int getItemCount() {
        return tempList.size();
    }

    public void updateList( ArrayList<DepartmentModel> tList){
        this.tempList = tList;
        notifyDataSetChanged();
    }

    public void removeListItem( ArrayList<DepartmentModel> tList){
        this.tempList = tList;
        notifyDataSetChanged();
    }


    public static class EmpEmailViewHolder extends RecyclerView.ViewHolder {
        OnMyItemClickListener onMyItemClickListener;
        MaterialTextView tvEmail, tvRemoveEmail;
        SwitchMaterial mSwitch;

        public EmpEmailViewHolder(@NonNull View itemView, OnMyItemClickListener listener) {
            super(itemView);
            this.onMyItemClickListener = listener;
            tvEmail = itemView.findViewById(R.id.tvEmpEmail);
            tvRemoveEmail = itemView.findViewById(R.id.removeEmpEmail);
            mSwitch = itemView.findViewById(R.id.empSwitch);

            itemView.setOnClickListener(view -> {
                if (onMyItemClickListener != null) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onMyItemClickListener.onItemClick(position);
                    }
                }
            });

            tvRemoveEmail.setOnClickListener(view -> {

               if (onMyItemClickListener != null) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onMyItemClickListener.onRemoveItemClick(position);
                    }
                }

            });

        }
    }
}
