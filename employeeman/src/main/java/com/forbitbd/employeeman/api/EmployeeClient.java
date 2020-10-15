package com.forbitbd.employeeman.api;

import com.forbitbd.androidutils.models.User;
import com.forbitbd.employeeman.models.Attendance;
import com.forbitbd.employeeman.models.AttendanceBody;
import com.forbitbd.employeeman.models.AttendanceResponse;
import com.forbitbd.employeeman.models.Employee;
import com.forbitbd.employeeman.models.EmployeeAttendanceResponse;
import com.forbitbd.employeeman.models.Worker;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface EmployeeClient {

    @GET("/civil/api/users/query/{query}")
    Call<List<User>> getQueryUsers(@Path("query") String query);

    @GET("/civil/api/projects/{project_id}/employees")
    Call<List<Employee>> getProjectEmployees(@Path("project_id") String projectId);

    @POST("/civil/api/projects/{project_id}/employees")
    Call<Employee> addEmployee(@Path("project_id") String projectId,@Body Employee employee);

    @PUT("/civil/api/projects/{project_id}/employees/{employee_id}")
    Call<Employee> updateEmployee(@Path("project_id") String project_id,@Path("employee_id") String employee_id,@Body Employee employee);

    @DELETE("/civil/api/projects/{project_id}/employees/{employee_id}")
    Call<String> deleteEmployee(@Path("project_id") String project_id,@Path("employee_id") String employee_id);

    @GET("/civil/api/projects/{project_id}/employee_attendances/{year}/{month}")
    Call<List<EmployeeAttendanceResponse>> getEmployeeMonthlyAttendance(
            @Path("project_id") String projectId,
            @Path("year") int year,
            @Path("month") int month
    );

    @GET("/civil/api/projects/{project_id}/employee_attendances/{year}/{month}/{day}")
    Call<List<EmployeeAttendanceResponse>> getDailyEmployeeAttendance(
            @Path("project_id") String projectId,
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day
    );

    @POST("/civil/api/projects/{project_id}/employee_attendances")
    Call<EmployeeAttendanceResponse> postEmployeeAttendance(@Body AttendanceBody body, @Path("project_id") String projectid);


    @GET("/civil/api/projects/{project_id}/employee_download/{year}/{month}")
    Call<ResponseBody> getEmployeeMonthlyAttendanceFile(
            @Path("project_id") String projectId,
            @Path("year") int year,
            @Path("month") int month
    );


    @GET("/civil/api/projects/{project_id}/employees/attendances/{employee_id}/{year}/{month}")
    Call<List<Attendance>> getSingleEmployeeMonthlyAttendance(
            @Path("project_id") String projectId,
            @Path("employee_id") String employee_id,
            @Path("year") int year,
            @Path("month") int month
    );








    @GET("/civil/api/projects/{project_id}/workers")
    Call<List<Worker>> getProjectWorkers(@Path("project_id") String projectId);

    @POST("/civil/api/projects/{project_id}/workers")
    @Multipart
    Call<Worker> addWorker(@Path("project_id") String projectId,
                           @Part MultipartBody.Part file,
                           @PartMap() Map<String, RequestBody> partMap);

    @PUT("/civil/api/projects/{project_id}/workers/{worker_id}")
    @Multipart
    Call<Worker> updateWorker(@Path("project_id") String projectId,@Path("worker_id") String workerId,
                              @Part MultipartBody.Part file,
                              @PartMap() Map<String, RequestBody> partMap);

    @DELETE("/civil/api/projects/{project_id}/workers/{worker_id}")
    Call<Worker> deleteWorker(@Path("project_id") String project_id,@Path("worker_id") String worker_id);

    @GET("/civil/api/projects/{project_id}/workers/attendances/{worker_id}/{year}/{month}")
    Call<List<Attendance>> getSingleWorkerMonthlyAttendance(
            @Path("project_id") String projectId,
            @Path("worker_id") String worker_id,
            @Path("year") int year,
            @Path("month") int month
    );

    @GET("/civil/api/projects/{project_id}/attendances")
    Call<List<AttendanceResponse>> getAllAttendance(@Path("project_id") String projectId);

    @GET("/civil/api/attendance/{worker_id}")
    Call<List<Attendance>> getAttendances(@Path("worker_id") String workerId);

    @GET("/civil/api/projects/{project_id}/attendances/{year}/{month}")
    Call<List<AttendanceResponse>> getMonthlyAttendance(
            @Path("project_id") String projectId,
            @Path("year") int year,
            @Path("month") int month
    );

    @GET("/civil/api/projects/{project_id}/worker_download/{year}/{month}")
    Call<ResponseBody> getWorkerMonthlyAttendanceFile(
            @Path("project_id") String projectId,
            @Path("year") int year,
            @Path("month") int month
    );

    @POST("/civil/api/projects/{project_id}/attendances")
    Call<AttendanceResponse> postAttendance(@Body AttendanceBody body, @Path("project_id") String projectid);

    @GET("/civil/api/projects/{project_id}/workers")
    Call<List<Worker>> getAllWorkers(@Path("project_id") String projectId);

    @GET("/civil/api/projects/{project_id}/attendances/{year}/{month}/{day}")
    Call<List<AttendanceResponse>> getDailyAttendance(
            @Path("project_id") String projectId,
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day
    );










}
