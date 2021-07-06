package com.jorch.pruebafacebook.di.component;

import com.jorch.pruebafacebook.App;
import com.jorch.pruebafacebook.di.builder.BuildersModule;
import com.jorch.pruebafacebook.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component (modules = {AndroidSupportInjectionModule.class, AppModule.class, BuildersModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(App application);
        AppComponent build();
    }

    void inject(App app);
}
