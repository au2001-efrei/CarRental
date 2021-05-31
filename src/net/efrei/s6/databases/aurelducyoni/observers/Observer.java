package net.efrei.s6.databases.aurelducyoni.observers;

public interface Observer<T extends Observable<T>> {
    void update(T subject);
}
