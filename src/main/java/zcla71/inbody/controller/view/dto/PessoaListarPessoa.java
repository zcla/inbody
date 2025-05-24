package zcla71.inbody.controller.view.dto;

import java.time.LocalDate;

import lombok.Data;
import zcla71.inbody.controller.model.dto.Pessoa;

@Data
public class PessoaListarPessoa {
	private String nome;
	private LocalDate nascimento;
	private Integer altura;
	private String sexo;
	private Integer qtdMedicoes;

	public PessoaListarPessoa(Pessoa pessoa) {
		this.nome = pessoa.getNome();
		this.nascimento = pessoa.getNascimento();
		this.altura = pessoa.getAltura();
		this.sexo = pessoa.getSexo();
		this.qtdMedicoes = pessoa.getMedicoes().size();
	}
}
