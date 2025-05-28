package zcla71.inbody.model.service;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ValidationException extends Exception {
	private List<Validation> validations;

	public ValidationException() {
		super();
		this.validations = new ArrayList<>();
	}
}
