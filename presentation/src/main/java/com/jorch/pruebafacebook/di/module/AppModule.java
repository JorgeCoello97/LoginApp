package com.jorch.pruebafacebook.di.module;

import android.content.Context;

import com.facebook.CallbackManager;
import com.google.firebase.auth.FirebaseAuth;
import com.jorch.data.SignInRepositoryImplementation;
import com.jorch.domain.repositories.ISignInRepository;
import com.jorch.pruebafacebook.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

@Module
public class AppModule {

    @Provides
    @Singleton
    static Context provideContext(App application){
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    static Scheduler provideScheduler(){
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    static ISignInRepository provideSignInRepository(SignInRepositoryImplementation signInRepositoryImplementation){
        return signInRepositoryImplementation;
    }

    @Provides
    @Singleton
    static CallbackManager provideCallbackManager(){
        return CallbackManager.Factory.create();
    }

    @Provides
    @Singleton
    static FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }
}
