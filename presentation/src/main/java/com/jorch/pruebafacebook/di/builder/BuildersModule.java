package com.jorch.pruebafacebook.di.builder;

import com.jorch.pruebafacebook.di.module.LoginModule;
import com.jorch.pruebafacebook.di.scope.PerActivity;
import com.jorch.pruebafacebook.ui.LoginActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {
    @PerActivity
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity contributeLoginActivity();

}
