package com.mws.service.exception;


public class ServiceException extends RuntimeException {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 8025434620321706646L;
	private String exceptionDetails;
	private ExceptionCode exceptionCode;

	public ServiceException() {
	}

	/**
	 * @param message
	 */
	public ServiceException(int message) {
		super(String.valueOf(message));
	}

	/**
	 * @param message
	 */
	public ServiceException(String message) {
		super(message);
	}

	/**
	 */
	public ServiceException(ExceptionCode exceptionCode) {
		this.setExceptionCode(exceptionCode);
	}

	public ServiceException(ExceptionCode exceptionCode, String details) {
		this.setExceptionCode(exceptionCode);
		this.exceptionDetails = details;
	}

	/**
	 * @param cause
	 */
	public ServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getExceptionDetails() {
		return exceptionDetails;
	}

	public void setExceptionDetails(String exceptionDetails) {
		this.exceptionDetails = exceptionDetails;
	}

	public ExceptionCode getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(ExceptionCode exceptionCode) {
		this.exceptionCode = exceptionCode;
	}


}
