package com.jorch.pruebafacebook.mvp.presenter;

import android.content.Context;

import com.facebook.login.LoginResult;
import com.jorch.domain.interactor.SignInUseCase;
import com.jorch.domain.interactor.parameters.SignInParameters;
import com.jorch.domain.model.SessionProvider;
import com.jorch.pruebafacebook.mvp.view.ILoginView;
import com.jorch.pruebafacebook.observer.SignInObserver;

import javax.inject.Inject;

public class LoginPresenter implements IPresenter {
    private static final String TAG = "LOGIN";
    ILoginView iLoginView;
    private Context context;
    private SignInUseCase signInUseCase;

    @Inject
    public LoginPresenter(ILoginView iLoginView, SignInUseCase signInUseCase) {
        this.iLoginView = iLoginView;
        this.signInUseCase = signInUseCase;
    }
    public void initialize(Context context){
        this.context = context;

    }

    @Override
    public void destroy() {
        if (signInUseCase != null)
            signInUseCase.cancelSuscriptions();
    }

    public void handleFacebookResult(LoginResult loginResult){
        iLoginView.showLoading();
        String accessToken = loginResult.getAccessToken().getToken();
        signInUseCase.implementUseCase(
                new SignInObserver(iLoginView,context),
                SignInParameters.Parameters.Create(SessionProvider.FACEBOOK, accessToken)
        );

    }
}
