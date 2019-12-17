package com.sujan.esoftwaricaapi.ui.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sujan.esoftwaricaapi.EmployeeActivity;
import com.sujan.esoftwaricaapi.R;
import com.sujan.esoftwaricaapi.api.EmployeeApi;
import com.sujan.esoftwaricaapi.model.CreateEmployee;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddFragment extends Fragment {
    Button register;
    EditText name, age, salary;
    int Age;
    String Name, Salary;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
          View root = inflater.inflate(R.layout.fragment_add, container, false);
        name = root.findViewById(R.id.rName);
        age = root.findViewById(R.id.rAge);
        salary = root.findViewById(R.id.rSalary);
        register = root.findViewById(R.id.save);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Age = Integer.parseInt(age.getText().toString());
                Name = name.getText().toString();
                Salary = salary.getText().toString();
                register();

            }
        });


        return root;
    }
    private void register() {
        int ID = 0;

        CreateEmployee createEmployee = new CreateEmployee(ID, Age, Name, Salary);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(EmployeeActivity.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EmployeeApi employeeApi = retrofit.create(EmployeeApi.class);

        Call<Void> voidCall = employeeApi.registerEmployee(createEmployee);
        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getActivity(), "You have sucessfully registered", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Error"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
