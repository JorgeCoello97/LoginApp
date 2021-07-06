package com.jorch.data.mapper;

import com.google.firebase.auth.FirebaseUser;
import com.jorch.domain.model.SessionProvider;
import com.jorch.domain.model.User;

public class FirebaseUserToUser {
    public static User Create (FirebaseUser firebaseUser){
        if (firebaseUser == null) return null;

        User user = new User();

        if (!firebaseUser.getProviderId().isEmpty())
            user.setProvider(
                    (firebaseUser.getProviderId().equals(SessionProvider.FACEBOOK)) ? SessionProvider.FACEBOOK
                            :
                            (firebaseUser.getProviderId().equals(SessionProvider.GOOGLE)) ? SessionProvider.GOOGLE
                                    :
                                    (firebaseUser.getProviderId().equals(SessionProvider.EMAIL)) ? SessionProvider.EMAIL
                                            :   SessionProvider.NONE

            );
        user.setUserId(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail());
        user.setName(firebaseUser.getDisplayName());
        user.setPhotoUrl(
                (firebaseUser.getPhotoUrl() != null) ? firebaseUser.getPhotoUrl().toString() : null
        );

        return user;
    }
}
