package zcla71.inbody.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zcla71.inbody.controller.model.dto.Pessoa;
import zcla71.inbody.controller.model.service.InBodyService;
import zcla71.inbody.controller.view.dto.PessoaIncluir;
import zcla71.inbody.controller.view.dto.PessoaIncluirOk;
import zcla71.inbody.controller.view.dto.PessoaIncluirPessoa;
import zcla71.inbody.controller.view.dto.PessoaListar;
import zcla71.inbody.controller.view.dto.PessoaListarPessoa;

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
				result.getPessoas().add(new PessoaListarPessoa(pessoa));
			}
		}

		return result;
	}

	public PessoaIncluir pessoaIncluir() {
		PessoaIncluirPessoa pessoaIncluirPessoa = new PessoaIncluirPessoa();
		pessoaIncluirPessoa.setNascimento(LocalDate.now().minus(50, ChronoUnit.YEARS));
		pessoaIncluirPessoa.setAltura(165);

		return pessoaIncluir(pessoaIncluirPessoa);
	}

	public PessoaIncluir pessoaIncluir(PessoaIncluirPessoa pessoaIncluirPessoa) {
		PessoaIncluir result = new PessoaIncluir();

		result.setPessoa(pessoaIncluirPessoa);

		return result;
	}

	public void pessoaIncluirOk(PessoaIncluirOk pessoaIncluir) {
		ValidationException validation = new ValidationException();
		PessoaIncluirPessoa pip = pessoaIncluir.getPessoa();
		if (pip.getNome().trim().length() < 1) {
			validation.getValidations().add(new Validation("nome", "Informe o nome."));
		}
		if (pip.getNascimento() == null) {
			validation.getValidations().add(new Validation("nascimento", "Informe a data de nascimento."));
		}
		if (pip.getAltura() == null || pip.getAltura() == 0) {
			validation.getValidations().add(new Validation("altura", "Informe a altura."));
		}
		if (pip.getSexo() == null || pip.getSexo().trim().length() == 0) {
			validation.getValidations().add(new Validation("sexo", "Informe o sexo."));
		}
		if (!validation.getValidations().isEmpty()) {
			throw validation;
		}

		inBodyService.incluirPessoa(pip.toPessoa());
	}
}
