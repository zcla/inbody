package zcla71.inbody.controller;

public class ControllerException extends RuntimeException {
	public ControllerException(String message) {
		super(message);
	}

	public ControllerException(Throwable cause) {
		super(cause);
	}
}
