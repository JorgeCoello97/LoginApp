package com.jorch.data.mapper;

import com.jorch.data.realm.DTO.UserSession;
import com.jorch.domain.model.User;

public class UserToUserSession {
    public static User Create(UserSession userSession){
        if (userSession == null) return null;

        User user = new User();
        user.setAuthToken(userSession.getAuthToken());
        user.setEmail(userSession.getEmail());
        user.setName(userSession.getName());
        user.setPhotoUrl(userSession.getPhotoUrl());
        user.setProvider(userSession.getProvider());
        user.setUserId(userSession.getUserId());
        return user;
    }
    public static UserSession Create(User user) {
        if (user == null) return null;

        UserSession userSession = new UserSession();
        userSession.setAuthToken(user.getAuthToken());
        userSession.setEmail(user.getEmail());
        userSession.setName(user.getName());
        userSession.setPhotoUrl(user.getPhotoUrl());
        userSession.setProvider(user.getProvider());
        userSession.setUserId(user.getUserId());
        return userSession;
    }
}
