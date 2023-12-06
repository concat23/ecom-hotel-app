package dev.concat.vab.ecomhotelappbackend.utils;

public class GenerateResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public GenerateResponse(boolean success, String message, T data){
        this.success = success;
        this.message = message;
        this.data = data;
    }


    public boolean isSuccess() {
        return this.success;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
