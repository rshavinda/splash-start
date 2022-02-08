package com.acentura.splashstart.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NetResource <T>{
    @NonNull
    public final Status status;
    @Nullable
    public final T data;
    @Nullable
    public final int code;
    @Nullable
    public final String message;

    public NetResource(@NonNull Status status, @Nullable T data, @Nullable int code, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public static <T> NetResource<T> success(@NonNull String msg, @Nullable T data) {
        return new NetResource<>(Status.SUCCESS, data, 0, msg);
    }

    public static <T> NetResource<T> error(@NonNull String msg) {
        return new NetResource<>(Status.ERROR, null, -1, msg);
    }

    public static <T> NetResource<T> error(@NonNull Throwable error) {
        ResponseHandler.ResponseData data = null;
        try {
            data = ResponseHandler.getError(error);

            if(data.getCode() == ResponseHandler.Code.CE999_CUSTOM_ERROR_NOT_CONNECTED){
                return new NetResource<>(Status.NOT_CONNECTED, null, data.getCode(), data.getStatusMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
            data = new ResponseHandler.ResponseData(
                    ResponseHandler.StatusCode.REQUEST_DENIED, error.getMessage());
        }
        return new NetResource<>(Status.ERROR, null, data.getCode(), data.getStatusMessage());
    }

    public static <T> NetResource<T> loading() {
        return new NetResource<>(Status.LOADING, null, -1, null);
    }


    public enum Status {SUCCESS, ERROR, LOADING, NOT_CONNECTED}
}
