package com.iwanvi.nvwa.common.exception;

/**
 * Author: chengen
 * Date: 2014/11/21
 * Time: 16:04
 */
public class HttpInvokeException extends RuntimeException {
    private static final long serialVersionUID = 9023390431371233851L;

    private int statusCode;

    public HttpInvokeException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public HttpInvokeException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public HttpInvokeException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public HttpInvokeException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    public HttpInvokeException(int statusCode) {
        super();
        this.statusCode=statusCode;
    }

    public HttpInvokeException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode=statusCode;
    }

    public HttpInvokeException(int statusCode, String message) {
        super(message);
        this.statusCode=statusCode;
    }

    public HttpInvokeException(int statusCode, Throwable cause) {
        super(cause);
        this.statusCode=statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }


}
