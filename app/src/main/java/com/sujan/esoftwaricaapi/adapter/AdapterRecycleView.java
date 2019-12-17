package com.sujan.esoftwaricaapi.adapter;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sujan.esoftwaricaapi.EmployeeActivity;
import com.sujan.esoftwaricaapi.R;

import com.sujan.esoftwaricaapi.UpdateActivity;
import com.sujan.esoftwaricaapi.api.EmployeeApi;
import com.sujan.esoftwaricaapi.model.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AdapterRecycleView extends RecyclerView.Adapter<AdapterRecycleView.EmployeeViewHolder > {
    Context context;
    List<Employee> show_employees;

    public AdapterRecycleView(Context context, List<Employee> show_employees) {
        this.context = context;
        this.show_employees = show_employees;
    }

    @NonNull
    @Override
    public AdapterRecycleView.EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecycleView.EmployeeViewHolder holder, final int position) {

        final Employee se = show_employees.get(position);
        holder.txtname.setText(se.getEmployee_name());
        holder.txtsalary.setText(String.valueOf(se.getEmployee_salary()));
        holder.txtage.setText(String.valueOf(se.getEmployee_age()));

        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int empId= Integer.parseInt(String.valueOf(se.getId()));
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(EmployeeActivity.base_url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                EmployeeApi employeeAPI = retrofit.create(EmployeeApi.class);
                Call<Void>voidCall = employeeAPI.deleteEmployee(empId);
                voidCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(context, "Employee deleted successfully", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("errors", "onFailure: "+t.getLocalizedMessage());

                    }
                });

                show_employees.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(se.getId()));
                intent.putExtra("name",String.valueOf(se.getEmployee_name()));
                intent.putExtra("age", String.valueOf(se.getEmployee_age()));
                intent.putExtra("salary", String.valueOf(se.getEmployee_salary()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return show_employees.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView txtname,txtage,txtsalary;
        Button btndelete,btnupdate;
        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtname=itemView.findViewById(R.id.name);
            txtsalary=itemView.findViewById(R.id.salarly);
            txtage=itemView.findViewById(R.id.age);
            btndelete=itemView.findViewById(R.id.del);
            btnupdate=itemView.findViewById(R.id.update);
        }
    }

}