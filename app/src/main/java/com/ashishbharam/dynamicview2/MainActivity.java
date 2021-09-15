package com.ashishbharam.dynamicview2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ashishbharam.dynamicview2.adapter.EmailRvAdapter;
import com.ashishbharam.dynamicview2.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EmailRvAdapter.OnMyItemClickListener {

    private static final String TAG = "tag";
    private ActivityMainBinding binding;
    private EmailRvAdapter adapter;
    private ArrayList<DepartmentModel> departmentModelList;
    private ArrayList<DepartmentModel> tempDeptList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // TODO: 1
        // android editetxt
        //add into spinner
        //emp edittext add
        //add recyclerview
        //sharedpref use method clear

        //createDummyList();
        //SharedPrefManager.getInstance(this).saveEmpInfo(departmentModelList);
        departmentModelList = SharedPrefManager.getInstance(this).getList();

        intiRecyclerview();
        loadSpinnerItem(departmentModelList);

        binding.btnAddDepartment.setOnClickListener(view -> {
            addDepartmentToSpinner();
        });

        binding.btnAddEmp.setOnClickListener(view -> {
            addEmployeeInfo();
        });

        binding.selectDepartmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();

                if (!selectedItem.equals("Select")) {
                    Toast.makeText(MainActivity.this, selectedItem, Toast.LENGTH_SHORT).show();
                    showDepartment(selectedItem);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }

    private void loadSpinnerItem(ArrayList<DepartmentModel> departmentModelList) {
        ArrayList<String> list = new ArrayList<>();
        if (!departmentModelList.isEmpty()) {
            for (int i = 0; i < departmentModelList.size(); i++) {
                if (!list.contains(departmentModelList.get(i).getDepartmentName())) {
                    list.add(departmentModelList.get(i).getDepartmentName());
                }
            }
            if (!list.contains("Select")) {
                list.add(0, "Select");
            }
            ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, list);
            adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.selectDepartmentSpinner.setAdapter(adapterSpinner);
        }
    }

    private void showDepartment(String selectedItem) {
        binding.etDepartment.setText(selectedItem);

        // TODO: 2 Add background to empty recyclerview that add employee
        /*Log.d(TAG, "showDepartment: "+adapter.getItemCount());
        Log.d(TAG, "showDepartment: "+departmentModelList.size());*/
        //departmentModelList.removeAll(tempDeptList); //got null

        //departmentModelList = new ArrayList<>();

        if (!departmentModelList.isEmpty()) {
            //departmentModelList.clear();
            tempDeptList.clear();
            //adapter.notifyItemRangeRemoved(0, departmentModelList.size());
            for (int i = 0; i < departmentModelList.size(); i++) {
                /*if (!departmentModelList.get(i).getDepartmentName().equals(selectedItem)) {
                    departmentModelList.remove(departmentModelList.get(i));
                    adapter.notifyItemRemoved(i);
                    --i;
                }*/
                if (departmentModelList.get(i).getDepartmentName().equals(selectedItem)) {
                    tempDeptList.add(departmentModelList.get(i));
                }else{
                    adapter.updateList(new ArrayList<DepartmentModel>());
                }
            }
            if (tempDeptList.size() > 0) {
                //adapter = new EmailRvAdapter(null, tempDeptList);
                adapter.updateList(tempDeptList);
            }
        } else {
            /*departmentModelList.add(new DepartmentModel("temp", "temp", "Inactive"));
            adapter.notifyItemInserted(departmentModelList.size() - 1);
            departmentModelList.remove(0);
            adapter.notifyItemRemoved(0);*/
            addEmployeeInfo();
            Log.d(TAG, "adapter Count: " + adapter.getItemCount());
            Log.d(TAG, "departmentModelListSize: " + departmentModelList.size());
            //adapter.notifyItemRangeRemoved(0,departmentModelList.size());
        }
    }

    private void intiRecyclerview() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EmailRvAdapter(this, departmentModelList);
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void createDummyList() {
        departmentModelList.add(new DepartmentModel("Android", "test1@gmail.com", "Active"));
        departmentModelList.add(new DepartmentModel("Android", "test2@gmail.com", "Active"));
        departmentModelList.add(new DepartmentModel("Android", "test3@gmail.com", "Active"));
        departmentModelList.add(new DepartmentModel("Web", "test4@gmail.com", "Inactive"));
        departmentModelList.add(new DepartmentModel("Web", "test5@gmail.com", "Inactive"));
        departmentModelList.add(new DepartmentModel("IOS", "test6@gmail.com", "Inactive"));
    }

    private void addEmployeeInfo() {
        String tEmail = binding.etEmpEmail.getText().toString();
        String tDepartmentName = binding.etDepartment.getText().toString();

        if (tDepartmentName.isEmpty()) {
            binding.etDepartment.setError("Enter Department Name");
            binding.etDepartment.requestFocus();
            return;
        }

        if (tEmail.isEmpty()) {
            binding.etEmpEmail.setError("Enter Email");
            binding.etEmpEmail.requestFocus();
            return;
        }

        List<String> list = new ArrayList<>();
        int count = binding.selectDepartmentSpinner.getCount();
        for (int i = 0; i < count; i++) {
            list.add(binding.selectDepartmentSpinner.getAdapter().getItem(i).toString());
        }
        //checking department added in spinner or not
        if (!list.contains(tDepartmentName)) {
            binding.etDepartment.setError("Add Department");
            binding.etDepartment.requestFocus();
            return;
        } else {
            binding.etDepartment.clearFocus();
            // TODO: 6 remove the requestFocus icon
        }

        if (checkEmailExists(tEmail)) {
            Log.d(TAG, "departmentModelList: " + departmentModelList.size());
            if (binding.selectDepartmentSpinner.getSelectedItem().equals("Select")) {
                departmentModelList.add(new DepartmentModel(tDepartmentName, tEmail, "Inactive"));
//            adapter.notifyItemInserted(departmentModelList.size() - 1);
                adapter.updateList(departmentModelList);
            }else{
                tempDeptList.add(new DepartmentModel(tDepartmentName, tEmail, "Inactive"));
                departmentModelList.add(new DepartmentModel(tDepartmentName, tEmail, "Inactive"));
                adapter.updateList(tempDeptList);
                //SharedPrefManager.getInstance(this).updateList(departmentModelList);
            }
                SharedPrefManager.getInstance(this).updateList(departmentModelList);

        } else {
            // TODO: 3 Email exist error
            //adapter.updateList(tempDeptList);
            //adapter.updateList(departmentModelList);
            binding.etEmpEmail.setError("Email Exists");
            binding.etEmpEmail.requestFocus();
        }
    }

    private boolean checkEmailExists(String empEmail) {
        boolean uniqueEmailFlag = true;
        if (!departmentModelList.isEmpty()) {
            for (int i = 0; i < departmentModelList.size(); i++) {
                if (departmentModelList.get(i).getEmployeeEmail().equals(empEmail)) {
                    Toast.makeText(this, "Email Exists :" + empEmail, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "insertEmployeeData: Email Exists :" + empEmail);
                    uniqueEmailFlag = false;
                    break;
                }
            }
        }
        return uniqueEmailFlag;
    }

    private void addDepartmentToSpinner() {
        String tDepartmentName = binding.etDepartment.getText().toString();
        List<String> list = new ArrayList<>();
        int count = binding.selectDepartmentSpinner.getCount();

        if (tDepartmentName.isEmpty()) {
            binding.etDepartment.setError("Enter Department Name");
            binding.etDepartment.requestFocus();
            return;
        }

        for (int i = 0; i < count; i++) {
            list.add(binding.selectDepartmentSpinner.getAdapter().getItem(i).toString());
        }
        // TODO: 4 Compare ignore case Android == android

        if (!list.contains(tDepartmentName)) {
            list.add(tDepartmentName);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.selectDepartmentSpinner.setAdapter(adapter);
            //adapter.notifyDataSetChanged();
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();

        } else {
            binding.etDepartment.setError("Already Exists");
            binding.etDepartment.requestFocus();
        }
    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: " + position);

        adapter.notifyItemChanged(position);
    }

    @Override
    public void onRemoveItemClick(int position) {
        Log.d(TAG, "onRemoveItemClick: " + position + ":" + departmentModelList.get(position).getEmployeeEmail());

        if (binding.selectDepartmentSpinner.getSelectedItem().equals("Select")) {
            departmentModelList.remove(position);
            //adapter.notifyItemRemoved(position);
            adapter.updateList(departmentModelList);
            SharedPrefManager.getInstance(this).updateList(departmentModelList);
        } else {
            int diff = departmentModelList.size() - tempDeptList.size();
            departmentModelList.remove(position + diff);
            tempDeptList.remove(position);
            //adapter.notifyItemRemoved(position);
            adapter.updateList(tempDeptList);
            SharedPrefManager.getInstance(this).updateList(tempDeptList);
            SharedPrefManager.getInstance(this).updateList(departmentModelList);
        }
    }
}
