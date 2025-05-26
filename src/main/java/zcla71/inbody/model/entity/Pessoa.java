package zcla71.inbody.model.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import zcla71.inbody.controller.Validation;
import zcla71.inbody.controller.ValidationException;

@Data
public class Pessoa {
	private String id;
	private String nome;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate nascimento;
	private Integer altura;
	private String sexo; // TODO Criar uma classe para mandar pros html
	private List<Medicao> medicoes;

	public Pessoa() {
		this.medicoes = new ArrayList<>();
	}

	public ValidationException validate() {
		ValidationException result = new ValidationException();
		if (this.nome.trim().length() < 1) {
			result.getValidations().add(new Validation("nome", "Informe o nome."));
		}
		if (this.nascimento == null) {
			result.getValidations().add(new Validation("nascimento", "Informe a data de nascimento."));
		}
		if (this.altura == null || this.altura == 0) {
			result.getValidations().add(new Validation("altura", "Informe a altura."));
		}
		if (this.sexo == null || this.sexo.trim().length() == 0) {
			result.getValidations().add(new Validation("sexo", "Informe o sexo."));
		}
		return result;
	}
}
