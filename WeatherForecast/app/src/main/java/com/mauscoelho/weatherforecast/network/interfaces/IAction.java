package com.mauscoelho.weatherforecast.network.interfaces;


public interface IAction<T> {
    void OnCompleted(T response);
    void OnError(T response);
}
