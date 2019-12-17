package com.sujan.esoftwaricaapi.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sujan.esoftwaricaapi.EmployeeActivity;
import com.sujan.esoftwaricaapi.R;
import com.sujan.esoftwaricaapi.adapter.AdapterRecycleView;
import com.sujan.esoftwaricaapi.api.EmployeeApi;
import com.sujan.esoftwaricaapi.model.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);



        recyclerView = root.findViewById(R.id.ReV);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(EmployeeActivity.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EmployeeApi employeeApi = retrofit.create(EmployeeApi.class);
        final Call<List<Employee>> listCall = employeeApi.getAllEmployees();
        listCall.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getActivity(), "error"+response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("error","error" +response.code() );
                    return;
                }
                List<Employee>listEmployee=response.body();
                AdapterRecycleView adapterRecycleView = new AdapterRecycleView(getActivity(),listEmployee);
                recyclerView.setAdapter(adapterRecycleView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Toast.makeText(getActivity(), "error"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error","error   "+t.getLocalizedMessage() );

            }
        });


        return root;
    }
}