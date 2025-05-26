package zcla71.inbody.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zcla71.inbody.model.entity.Pessoa;
import zcla71.inbody.model.service.InBodyService;
import zcla71.inbody.view.dto.PessoaIncluir;
import zcla71.inbody.view.dto.PessoaIncluirOk;
import zcla71.inbody.view.dto.PessoaListar;

@Component
public class InBodyController {
	@Autowired
	private InBodyService inBodyService;

	public PessoaListar pessoaListar() {
		PessoaListar result = new PessoaListar();

		result.setPessoas(new ArrayList<>());
		List<Pessoa> pessoas = inBodyService.listarPessoas();
		if (pessoas != null) {
			for (Pessoa pessoa : pessoas) {
				result.getPessoas().add(pessoa);
			}
		}

		return result;
	}

	public PessoaIncluir pessoaIncluir() {
		Pessoa pessoa = new Pessoa();
		pessoa.setNascimento(LocalDate.now().minus(50, ChronoUnit.YEARS));
		pessoa.setAltura(165);

		return pessoaIncluir(pessoa);
	}

	public PessoaIncluir pessoaIncluir(Pessoa pessoa) {
		PessoaIncluir result = new PessoaIncluir();

		result.setPessoa(pessoa);

		return result;
	}

	public void pessoaIncluirOk(PessoaIncluirOk pessoaIncluir) {
		ValidationException validation = new ValidationException();
		Pessoa pessoa = pessoaIncluir.getPessoa();
		if (pessoa.getNome().trim().length() < 1) {
			validation.getValidations().add(new Validation("nome", "Informe o nome."));
		}
		if (pessoa.getNascimento() == null) {
			validation.getValidations().add(new Validation("nascimento", "Informe a data de nascimento."));
		}
		if (pessoa.getAltura() == null || pessoa.getAltura() == 0) {
			validation.getValidations().add(new Validation("altura", "Informe a altura."));
		}
		if (pessoa.getSexo() == null || pessoa.getSexo().trim().length() == 0) {
			validation.getValidations().add(new Validation("sexo", "Informe o sexo."));
		}
		if (!validation.getValidations().isEmpty()) {
			throw validation;
		}

		inBodyService.incluirPessoa(pessoa);
	}
}
