package com.jorch.domain.interactor;

import io.reactivex.observers.DisposableObserver;

public interface IObservableUseCase {
    void suscribe(DisposableObserver disposableObserver);
    void cancelSuscriptions();
}
