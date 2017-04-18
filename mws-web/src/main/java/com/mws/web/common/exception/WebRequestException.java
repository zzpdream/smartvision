package com.mws.web.common.exception;

public class WebRequestException extends RuntimeException {

	private static final long serialVersionUID = -1680070642633783426L;

	public WebRequestException() {
		super();
	}

	public WebRequestException(String message) {
		super(message);
	}

	public WebRequestException(Throwable cause) {
		super(cause);
	}

	public WebRequestException(String message, Throwable cause) {
		super(message, cause);
	}
}
