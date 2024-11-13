package com.bho.catchtrippingbackend.error.code;

public interface ErrorCodeModel {
    int getStatusCode();
    String getSystemErrorCode();
    String getErrorMessage();
}
