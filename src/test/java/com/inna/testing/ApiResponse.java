package com.inna.testing;

public class ApiResponse {
    private final int status;
    private final String body;

    public int getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }

    public ApiResponse(int status, String body) {
        this.status = status;
        this.body = body;
    }
}
