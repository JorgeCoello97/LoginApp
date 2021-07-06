package com.jorch.pruebafacebook.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.jorch.domain.model.User;
import com.jorch.pruebafacebook.R;
import com.jorch.pruebafacebook.mvp.presenter.LoginPresenter;
import com.jorch.pruebafacebook.mvp.view.ILoginView;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    private static final String TAG = "LOGIN";

    @Inject
    CallbackManager callbackManager;

    @Inject
    LoginPresenter loginPresenter;

    @BindView(R.id.login_progress)
    ProgressBar login_progress;

    @BindView(R.id.login_email_btn)
    Button login_email_btn;

    @BindView(R.id.login_register_btn)
    Button login_register_btn;

    @BindView(R.id.login_signInFacebook_btn)
    Button login_signInFacebook_btn;

    @BindView(R.id.login_signOut_btn)
    Button login_signOut_btn;


    @OnClick(R.id.login_email_btn)
    protected void doEmailLogin(){

    }

    @OnClick(R.id.login_register_btn)
    protected void doEmailRegister(){

    }

    @OnClick(R.id.login_signInFacebook_btn)
    protected void doFacebookSignIn(){
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.logInWithReadPermissions(this, Arrays.asList("email","public_profile"));
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginPresenter.handleFacebookResult(loginResult);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @OnClick(R.id.login_signOut_btn)
    protected  void doSignOut(){

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        //PANTALLA EN MODO FULLSCREEN
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        loginPresenter.initialize(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.destroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()){
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void updateUI(User user) {
        Toast.makeText(this, "Bienvenido "+user.getName()+". " +
                "Conectado desde: "+user.getProvider(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignedIn() {
        login_signInFacebook_btn.setVisibility(View.GONE);
        login_email_btn.setVisibility(View.GONE);
        login_register_btn.setVisibility(View.GONE);
        login_signOut_btn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSignedOut() {
        login_signInFacebook_btn.setVisibility(View.VISIBLE);
        login_email_btn.setVisibility(View.VISIBLE);
        login_register_btn.setVisibility(View.VISIBLE);
        login_signOut_btn.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        login_progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        login_progress.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}