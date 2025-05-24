package zcla71.inbody.controller.view.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import zcla71.inbody.controller.model.dto.Pessoa;

@Data
public class PessoaIncluirPessoa {
	private String nome = null;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate nascimento = null;
	private Integer altura = null;
	private String sexo = null;

	public PessoaIncluirPessoa() {
		super();
	}

	public Pessoa toPessoa() {
		Pessoa result = new Pessoa();
		result.setNome(this.nome);
		result.setNascimento(this.nascimento);
		result.setAltura(this.altura);
		result.setSexo(this.sexo);
		return result;
	}
}
