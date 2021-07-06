package com.jorch.data.factory;

import android.content.Context;

import com.jorch.data.datastore.ISessionDataStore;
import com.jorch.data.datastore.local.LocalSessionDataStore;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionDataStoreFactory implements IDataStoreFactory {
    Context context;

    @Inject
    public SessionDataStoreFactory(Context context) {
        this.context = context;
    }

    @Override
    public ISessionDataStore Local() {
        return new LocalSessionDataStore(context);
    }

    @Override
    public ISessionDataStore Remote() {
        return null;
    }
}
