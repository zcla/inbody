package zcla71.inbody.controller;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
// TODO Ver se extends Exception fica melhor pra tratar
public class ValidationException extends RuntimeException {
	private List<Validation> validations;

	public ValidationException() {
		super();
		this.validations = new ArrayList<>();
	}
}
