package net.croz.qed.bank.gateway.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private boolean success;
    private String error;
    private T data;

    private Response() {
    }

    public static <T> Response<T> success(final T data) {
        final Response<T> response = new Response<>();
        response.setSuccess(true);
        response.setError(null);
        response.setData(data);
        return response;
    }

    public static <T> Response<T> fail(final String error) {
        final Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setError(error);
        response.setData(null);
        return response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
