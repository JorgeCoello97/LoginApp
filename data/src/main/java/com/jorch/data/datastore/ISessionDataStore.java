package com.jorch.data.datastore;

import com.jorch.domain.model.User;

public interface ISessionDataStore extends IDataStore{
    boolean saveSession(User user);
    User getSession();
}
