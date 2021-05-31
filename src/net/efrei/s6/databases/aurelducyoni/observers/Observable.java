package net.efrei.s6.databases.aurelducyoni.observers;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<T extends Observable<T>> {

    private final List<Observer<T>> observers;

    protected Observable() {
        this.observers = new ArrayList<>();
    }

    public boolean addObserver(Observer<T> observer) {
        if (observer == null)
            throw new NullPointerException();
        return !observers.contains(observer) && this.observers.add(observer);
    }

    public boolean deleteObserver(Observer<?> observer) {
        return this.observers.remove(observer);
    }

    @SuppressWarnings("unchecked")
    protected void notifyObservers() {
        for (Observer<T> observer : this.observers)
            observer.update((T) this);
    }

}
