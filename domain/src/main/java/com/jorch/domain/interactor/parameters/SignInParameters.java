package com.jorch.domain.interactor.parameters;

public class SignInParameters {
    public static final class Parameters {
        private String sessionProvider;
        private String accountIdToken;

        public String getSessionProvider() {
            return sessionProvider;
        }

        public String getAccountIdToken() {
            return accountIdToken;
        }

        public Parameters(String sessionProvider, String accountIdToken) {
            this.sessionProvider = sessionProvider;
            this.accountIdToken = accountIdToken;
        }

        public static Parameters Create(String sessionProvider, String accountIdToken){
            return new Parameters(sessionProvider,accountIdToken);
        }
    }
}
