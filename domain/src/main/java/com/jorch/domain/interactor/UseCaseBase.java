package com.jorch.domain.interactor;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class UseCaseBase<Type,Object> implements IObservableUseCase {
    ArrayList<DisposableObserver<Type>> observers = new ArrayList<>();

    public void createUseCase(Observable observable,
                              DisposableObserver<Type> observer,
                              Scheduler mainThread) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(mainThread)
                .subscribeWith(observer);
        suscribe(observer);
    }

    public abstract Observable<Type> implementUseCase(DisposableObserver observer, Object object);

    @Override
    public void suscribe(DisposableObserver disposableObserver) {
        if (!observers.contains(disposableObserver))
            observers.add(disposableObserver);
    }

    @Override
    public void cancelSuscriptions() {
        ArrayList<DisposableObserver<Type>> observersAux = (ArrayList<DisposableObserver<Type>>) observers.clone();
        for (DisposableObserver observer : observersAux) {
            if ( observer != null && observers.contains(observer)){
                observer.dispose();
                observers.remove(observer);
            }
        }
    }
}
