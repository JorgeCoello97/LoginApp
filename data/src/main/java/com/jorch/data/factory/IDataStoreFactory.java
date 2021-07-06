package com.jorch.data.factory;

import com.jorch.data.datastore.IDataStore;

public interface IDataStoreFactory {
    IDataStore Local();
    IDataStore Remote();
}
