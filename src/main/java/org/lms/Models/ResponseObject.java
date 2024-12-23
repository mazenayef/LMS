package org.lms.Models;

public class ResponseObject {
    public String message;
    public Object data;

    public ResponseObject(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
