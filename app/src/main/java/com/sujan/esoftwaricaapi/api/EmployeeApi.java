package com.sujan.esoftwaricaapi.api;

import com.sujan.esoftwaricaapi.model.CreateEmployee;
import com.sujan.esoftwaricaapi.model.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EmployeeApi {
    //Retriving all employee
    @GET("employees")
    Call<List<Employee>> getAllEmployees();

    //get employee by id
    @GET("employee/{empId}")
    Call<Employee> getEmployeeById(@Path("empId") int empID);

    //Create employee
    @POST("create")
    Call<Void>registerEmployee(@Body CreateEmployee emp);

    //update
    @PUT("update/{empId}")
    Call<Void>updateEmployee(@Path("empId") int empId,@Body CreateEmployee emp);

    //delete
    @DELETE("delete/{empId}")
    Call<Void>deleteEmployee(@Path("empId") int empId);
}
