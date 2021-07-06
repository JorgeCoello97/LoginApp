package com.jorch.data;

import android.content.Context;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jorch.data.factory.SessionDataStoreFactory;
import com.jorch.data.mapper.FirebaseUserToUser;
import com.jorch.domain.model.SessionProvider;
import com.jorch.domain.model.User;
import com.jorch.domain.repositories.ISignInRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class SignInRepositoryImplementation implements ISignInRepository {
    private Context context;
    private FirebaseAuth firebaseAuth;
    private SessionDataStoreFactory sessionDataStoreFactory;

    @Inject
    public SignInRepositoryImplementation(Context context, FirebaseAuth firebaseAuth, SessionDataStoreFactory sessionDataStoreFactory) {
        this.context = context;
        this.firebaseAuth = firebaseAuth;
        this.sessionDataStoreFactory = sessionDataStoreFactory;
    }

    @Override
    public Observable<User> signIn(String provider, String accountIdToken) {
        return Observable.create(emitter -> {
            try {
                AuthCredential credential = null;
                if (provider.equals(SessionProvider.FACEBOOK))
                    credential = FacebookAuthProvider.getCredential(accountIdToken);

                firebaseAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
                   if (task.isSuccessful())
                       getTokenOnSuccessFullSignIn(emitter);
                   else
                       emitter.onError(task.getException());
                });
            }catch (Exception e){
                emitter.onError(e);
            }
        });
    }

    private void getTokenOnSuccessFullSignIn(ObservableEmitter<User> emitter) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        User user = FirebaseUserToUser.Create(firebaseUser);

        firebaseUser.getIdToken(false).addOnCompleteListener(resultTask -> {
            user.setAuthToken(resultTask.getResult().getToken());
            if (sessionDataStoreFactory.Local().saveSession(user)){
                emitter.onNext(user);
                emitter.onComplete();
            }else
                emitter.onError(new Exception(context.getString(R.string.error_save_user_local)));

        });
    }
}
