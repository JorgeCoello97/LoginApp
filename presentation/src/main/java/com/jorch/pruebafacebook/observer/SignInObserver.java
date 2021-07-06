package com.jorch.pruebafacebook.observer;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.jorch.domain.model.User;
import com.jorch.pruebafacebook.R;
import com.jorch.pruebafacebook.mvp.view.ISessionView;

import io.reactivex.observers.DisposableObserver;

public class SignInObserver extends DisposableObserver<User> {
    private ISessionView iSessionView;
    private Context context;

    public SignInObserver(ISessionView iSessionView, Context context) {
        this.iSessionView = iSessionView;
        this.context = context;
    }


    @Override
    public void onNext(User value) {
        iSessionView.updateUI(value);
        iSessionView.onSignedIn();
    }

    @Override
    public void onError(Throwable e) {
        iSessionView.hideLoading();

        if(e instanceof FirebaseAuthInvalidCredentialsException)
            iSessionView.showMessage(context.getString(R.string.error_invalid_credentials));
        else if(e instanceof FirebaseAuthUserCollisionException)
            iSessionView.showMessage(context.getString(R.string.error_user_already_exists));
        else if(e instanceof FirebaseAuthRecentLoginRequiredException)
            iSessionView.showMessage(context.getString(R.string.error_need_authenticate_again));
        else if(e instanceof FirebaseAuthInvalidUserException)
            iSessionView.showMessage(context.getString(R.string.error_user_not_found));
        else
            iSessionView.showMessage(e.getMessage());
    }

    @Override
    public void onComplete() {
        iSessionView.hideLoading();
    }
}
