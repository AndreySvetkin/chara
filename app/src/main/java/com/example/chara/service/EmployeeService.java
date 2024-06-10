package com.example.chara.service;

import com.example.chara.helper.UserServiceHelper;
import com.example.chara.model.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EmployeeService {

    final String VIEW = "employee-view";

    default Call<List<Employee>> allEmployees() {
        return allEmployees(" Bearer " + UserServiceHelper.ACCESS_TOKEN, VIEW);
    }
    @GET("entities/chara_Employee")
    Call<List<Employee>> allEmployees(@Header("Authorization") String auth, @Query("view") String view);

    default Call<Employee> fetchEmployee(Employee employee) {
        return fetchEmployee(" Bearer " + UserServiceHelper.ACCESS_TOKEN, employee.getId(), VIEW);
    }
    @GET("entities/chara_Employee/{employeeId}")
    Call<Employee> fetchEmployee(@Header("Authorization") String auth, @Path("employeeId") String employeeId, @Query("view") String view);

    default Call<Employee> addEmployee(Employee employee) {
        return addEmployee(" Bearer " + UserServiceHelper.ACCESS_TOKEN, employee, VIEW);
    }
    @POST("entities/chara_Employee/")
    Call<Employee> addEmployee(@Header("Authorization") String auth, @Body Employee employee, @Query("responseView") String responseView);

    default Call<Employee> saveEmployee(Employee employee) {
        return saveEmployee(" Bearer " + UserServiceHelper.ACCESS_TOKEN, employee.getId(), employee, VIEW);
    }
    @PUT("entities/chara_Employee/{employeeId}")
    Call<Employee> saveEmployee(@Header("Authorization") String auth, @Path("employeeId") String employeeId, @Body Employee employee, @Query("responseView") String responseView);

    default Call<Employee> deleteEmployee(Employee employee) {
        return saveEmployee(" Bearer " + UserServiceHelper.ACCESS_TOKEN, employee.getId());
    }
    @DELETE("entities/chara_Employee/{employeeId}")
    Call<Employee> saveEmployee(@Header("Authorization") String auth, @Path("employeeId") String employeeId);
}
