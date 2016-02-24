package com.mauscoelho.controllers.interfaces;

public interface IAction<T> {
    void OnCompleted(T response);
    void OnError(T response);
}