package com.jorch.domain.repositories;

import com.jorch.domain.model.User;

import io.reactivex.Observable;

public interface ISignInRepository {
    Observable<User> signIn(String provider, String accountIdToken);
}
