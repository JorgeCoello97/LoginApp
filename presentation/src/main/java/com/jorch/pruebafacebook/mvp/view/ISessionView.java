package com.jorch.pruebafacebook.mvp.view;

import com.jorch.domain.model.User;

public interface ISessionView extends IDataView {
    void updateUI(User user);
    void onSignedIn();
    void onSignedOut();
}
