package zcla71.inbody.controller;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Validation {
	public static final String getMessage(List<Validation> validations, String fieldName) {
		return validations == null ? null : validations.stream().filter(v -> v.getFieldName().equals(fieldName)).findFirst().get().getMessage();
	}

	public static final boolean isValid(List<Validation> validations, String fieldName) {
		return validations == null ? true : !validations.stream().anyMatch(v -> v.getFieldName().equals(fieldName));
	}

	public static final String validationClass(List<Validation> validations, String fieldName) {
		if (validations == null) {
			return "";
		}
		if (isValid(validations, fieldName)) {
			return "is-valid";
		}
		return "is-invalid";
	}

	private String fieldName;
	private String message;
}
