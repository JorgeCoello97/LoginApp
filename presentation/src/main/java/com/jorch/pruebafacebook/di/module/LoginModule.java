package com.jorch.pruebafacebook.di.module;

import com.jorch.domain.interactor.SignInUseCase;
import com.jorch.pruebafacebook.mvp.presenter.LoginPresenter;
import com.jorch.pruebafacebook.mvp.view.ILoginView;
import com.jorch.pruebafacebook.ui.LoginActivity;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class LoginModule {
    //VIEW AND PRESENTER
    @Provides
    static LoginPresenter provideLoginPresenter(ILoginView iLoginView,SignInUseCase signInUseCase){
        return new LoginPresenter(iLoginView,signInUseCase);
    }

    @Binds
    abstract ILoginView provideLoginView(LoginActivity loginActivity);
}
