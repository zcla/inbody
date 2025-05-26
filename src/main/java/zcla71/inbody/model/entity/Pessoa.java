package zcla71.inbody.model.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Pessoa {
	private String id;
	private String nome;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate nascimento;
	private Integer altura;
	private String sexo;
	private List<Medicao> medicoes;

	public Pessoa() {
		this.medicoes = new ArrayList<>();
	}
}
