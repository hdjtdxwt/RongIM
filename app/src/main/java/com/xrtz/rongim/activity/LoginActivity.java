package com.xrtz.rongim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.xrtz.rongim.R;
import com.xrtz.rongim.application.ImApplication;
import com.xrtz.rongim.bean.User;
import com.xrtz.rongim.bean.UserResult;
import com.xrtz.rongim.common.CommonValues;
import com.xrtz.rongim.iservice.UserService;
import com.xrtz.rongim.response.UserResponse;
import com.xrtz.rongim.util.NetStatueUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 登陆Activity
 */
public class LoginActivity extends AppCompatActivity  {
    @BindView(R.id.login_user)
    EditText mLoginUserEt;
    @BindView(R.id.login_passwsd)
    EditText mLoginPwdEt;

    String userName;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //SharedPreferences sp = getSharedPreferences("userinfo",MODE_PRIVATE);

    }
    @OnClick({R.id.login_button, R.id.login_reg})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.login_button:
                //startActivity(new Intent(this,MainActivity.class));
                userName = mLoginUserEt.getText().toString().trim();
                password = mLoginPwdEt.getText().toString().trim();
                if(checkLoginInfo(userName,password)){
                    loginSubmit(userName,password);
                }
                break;
            case R.id.login_reg:
               // startActivity(new Intent(this,RegActivity.class));
                break;
        }
    }
    /**
     * 检查用户名密码的合法性
     * @param userName
     * @param password
     * @return
     */
    public boolean checkLoginInfo(String userName,String password){
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this,"请输入登陆账号",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"请输入登陆密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 登陆提交
     * @param userName
     * @param password
     */
    public void loginSubmit(final String userName,final String password){
        if(NetStatueUtil.isConnected(getApplicationContext()) && !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)){//网络连接ok

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CommonValues.BaseUrl) //这个就是常量类里的基础路径 ;
                    .addConverterFactory(GsonConverterFactory.create())  //有这个Convert会自动将json转换成实体bean
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //有这个配置，会自动将结果转换成一个支持Rxjava的Observable对象
                    .build();

            UserService apiService = retrofit.create(UserService.class);
            apiService.login(userName,password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map(new Func1<UserResponse, Observable<UserResult>>() {
                        @Override
                        public Observable<UserResult> call(final UserResponse userResponse) {
                            if (userResponse != null) {
                                if (userResponse.getStatus() == 1) {//成功
                                    return Observable.create(new Observable.OnSubscribe<UserResult>() {
                                        //这里就相当于创建了一个被观察者，被观察者做某一件事情，做这件事的返回值是Bitmap
                                        @Override
                                        public void call(Subscriber<? super UserResult> subscriber) {
                                            //call方法就是被观察者具体是怎么做的
                                            ImApplication.newInstance().setUserResult(userResponse.getData());
                                            subscriber.onNext(userResponse.getData());
                                        }
                                    });
                                }

                            }
                            return null;
                        }
                    })
                    .subscribe(new Action1<Observable<UserResult>>() {
                        //对上面转化之后的对象进行处理，接下来是登陆实时聊天服务器
                        @Override
                        public void call(Observable<UserResult> userResultObservable) {

                            userResultObservable.doOnNext(new Action1<UserResult>() {
                                @Override
                                public void call(UserResult userResult) {
                                    RongIM.connect(userResult.getToken(), new RongIMClient.ConnectCallback() {
                                        /**
                                         * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                                         */
                                        @Override
                                        public void onTokenIncorrect() {
                                            Log.d("LoginActivity", "--onTokenIncorrect");
                                        }

                                        /**
                                         * 连接融云成功
                                         * @param userid 当前 token
                                         */
                                        @Override
                                        public void onSuccess(String userid) {
                                            Log.d("LoginActivity", "--onSuccess" + userid+"   "+Thread.currentThread().getName());
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                            finish();
                                        }

                                        /**
                                         * 连接融云失败
                                         * @param errorCode 错误码，可到官网 查看错误码对应的注释
                                         */
                                        @Override
                                        public void onError(RongIMClient.ErrorCode errorCode) {

                                            Log.d("LoginActivity", "--onError" + errorCode);
                                        }
                                    });
                                }
                            });

                        }
                    });
                    /*.map(new Func1<Observable<UserResponse>, Observable<User>>() {
                        //处理返回的Observable<UserResponse>类型的对象,转化的结果生成了另一个Observable<User>的对象

                        @Override
                        public Observable<User> call(Observable<UserResponse> userResponseObservable) {
                            if(userResponseObservable!=null){
                                userResponseObservable.subscribe(new Action1<UserResponse>() {
                                    @Override
                                    public void call(UserResponse userResponse) {
                                        Log.e("call", "login_after_reg-userResponse:" + userResponse);
                                        if (userResponse!=null) {
                                            if(userResponse.getStatus()==1){//成功
                                                final User user = userResponse.getData().getUser();
                                                ImApplication.newInstance().setUserResult(userResponse.getData());
                                                Observable.create(new Observable.OnSubscribe<User>() {
                                                    @Override
                                                    public void call(Subscriber<? super User> subscriber) {
                                                        subscriber.onNext(user);
                                                        subscriber.onCompleted();
                                                    }
                                                });
                                            }else{

                                            }
                                        }
                                    }
                                });
                            }else{
                                return null;
                                //Toast.makeText(RegActivity.this," 返回数据失败！ ",Toast.LENGTH_SHORT).show();
                            }

                    })
                    .subscribe(new Subscriber<UserResponse>() {
                        @Override
                        public void onCompleted() {
                            Log.e("onCompleted","onCompleted----");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("onError",e.getMessage());
                        }

                        @Override
                        public void onNext(UserResponse userResponse) {
                            if (userResponse!=null) {
                                Log.e("onNext", "userResponse:" + userResponse);
                                if(userResponse.getStatus()==1){//成功
                                    ImApplication.newInstance().setUserResult(userResponse.getData());
                                    *//*startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                    LoginActivity.this.finish();*//*

                                }else{
                                    Toast.makeText(LoginActivity.this,userResponse.getMsg(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });*/

        }else{
            Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 建立与融云服务器的连接
     * @param token
     */
    private void connect(String token) {

        if (getApplicationInfo().packageName.equals("com.xrtz.rongim")) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {
                    Log.d("LoginActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {

                    Log.d("LoginActivity", "--onSuccess" + userid);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                    Log.d("LoginActivity", "--onError" + errorCode);
                }
            });
        }
    }
}

