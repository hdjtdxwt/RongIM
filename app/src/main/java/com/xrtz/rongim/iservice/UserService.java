package com.xrtz.rongim.iservice;

import com.xrtz.rongim.response.UserListResponse;
import com.xrtz.rongim.response.UserResponse;

import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/11/4.
 */

public interface UserService {
    @POST("LoginUserServlet")
    Observable<UserResponse> login(@Query("userName") String userName, @Query("userPassword") String userPassword);
    //2.0就一种模式，Call<返回值类型>或者Call<Void>  1.x    分同步和异步，按是否有返回值区分同步异步，异步没返回值，同步有
    //而创建service 的方法也变得和OkHttp的模式一模一样。如果要调用同步请求，只需调用execute；而发起一个异步请求则是调用enqueue。


    //注册用户
    @POST("AddUserServlet")
    Observable<UserResponse> reg(@Query("userName") String userName, @Query("userPassword") String userPassword, @Query("userEmail") String userEmail, @Query("userNicheng") String userNicheng);

    @POST("ShowUserServlet")
    Observable<UserListResponse> queryAllUser();

}
