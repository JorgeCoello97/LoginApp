package com.jorch.data.datastore.local;

import android.content.Context;
import android.util.Log;

import com.jorch.data.datastore.ISessionDataStore;
import com.jorch.data.mapper.UserToUserSession;
import com.jorch.data.realm.DTO.UserSession;
import com.jorch.domain.model.User;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.Sort;

public class LocalSessionDataStore implements ISessionDataStore {
    private static final String TAG = "LocalSessionDataStore";
    Context context;

    private Realm myUserRealm;

    public LocalSessionDataStore(Context context) {
        this.context = context;
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("UserSession.realm")
                .schemaVersion(3)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(configuration);

        myUserRealm = Realm.getInstance(configuration);
    }

    @Override
    public boolean saveSession(User user) {
        UserSession userSession = UserToUserSession.Create(user);

        try {
            myUserRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    userSession.setLastAccess(new Date());
                    realm.copyToRealmOrUpdate(userSession);
                }
            });
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
            return false;
        } finally {
            myUserRealm.close();
        }

        return true;
    }

    @Override
    public User getSession() {
        UserSession userSession = null;
        try {
              userSession = myUserRealm
                      .where(UserSession.class)
                      .findAllSorted("LastAccess", Sort.DESCENDING)
                      .first(null);

        }   catch (Exception e){
            return null;
        } finally {
            myUserRealm.close();
        }
        return UserToUserSession.Create(userSession);
    }
}
