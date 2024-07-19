package com.example.androidsession.http;

import com.example.androidsession.database.table_entity.ProfileEntity;

import java.util.List;

public interface ResponseCall<T> {
    void onSuccess(T data);
    void onError(String error);
}
