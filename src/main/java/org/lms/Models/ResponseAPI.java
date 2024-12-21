package org.lms.Models;

public class ResponseAPI {
    public int statusCode;
    public String message;
    public Object data;

    public ResponseAPI(int statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}
