package zcla71.inbody.controller.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Pessoa {
	private String nome;
	private LocalDate nascimento;
	private Integer altura;
	private String sexo;
	private List<Medicao> medicoes;

	public Pessoa() {
		this.medicoes = new ArrayList<>();
	}
}
