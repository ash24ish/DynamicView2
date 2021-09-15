package com.ashishbharam.dynamicview2;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "EmployeeSharedPref";
    private ArrayList<DepartmentModel> departmentModelList;
    private static SharedPrefManager prefInstance;
    private Context context;

    private SharedPrefManager(Context ctx) {
        this.context = ctx;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {

        if (prefInstance == null) {
            prefInstance = new SharedPrefManager(context);
        }
        return prefInstance;
    }

    public void saveEmpInfo(ArrayList<DepartmentModel> departmentModelList) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(departmentModelList);
        editor.putString("Department", json);
        editor.apply();
    }

    public void updateList(ArrayList<DepartmentModel> updatedList){
        clear();
        departmentModelList = updatedList;
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(departmentModelList);
        editor.putString("Department", json);
        editor.apply();
    }


    public ArrayList<DepartmentModel> getList() {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("Department", null);
        Type type = new TypeToken<ArrayList<DepartmentModel>>() {
        }.getType();
        departmentModelList = gson.fromJson(json, type);

        if (departmentModelList == null) {
            departmentModelList = new ArrayList<>();
        }
        return departmentModelList;
    }

    public void clear() {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

}
