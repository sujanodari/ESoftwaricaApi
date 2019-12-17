package com.sujan.esoftwaricaapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sujan.esoftwaricaapi.api.EmployeeApi;
import com.sujan.esoftwaricaapi.model.CreateEmployee;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateActivity extends AppCompatActivity {
    EditText Name, Age, Salary;
    Button Update;
    String id,age,name,salary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        age = intent.getStringExtra("age");
        name = intent.getStringExtra("name");
        salary = intent.getStringExtra("salary");

        Name = findViewById(R.id.uName);
        Age = findViewById(R.id.uAge);
        Salary = findViewById(R.id.uSalarly);
        Update = findViewById(R.id.update);

        Name.setText(name);
        Age.setText(age);
        Salary.setText(salary);


        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = Name.getText().toString();
                age = Age.getText().toString();
                salary = Salary.getText().toString();


                int empId = Integer.parseInt(id);

                CreateEmployee createEmployee = new CreateEmployee(empId, Integer.parseInt(age), name, salary);

                Retrofit retrofit = new Retrofit.Builder().baseUrl(EmployeeActivity.base_url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                EmployeeApi employeeApi = retrofit.create(EmployeeApi.class);

                Call<Void> voidCall = employeeApi.updateEmployee(empId,createEmployee);
                voidCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(UpdateActivity.this, "You have sucessfully updated", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(UpdateActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }


        });

    }
}
