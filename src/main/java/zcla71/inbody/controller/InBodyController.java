package zcla71.inbody.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zcla71.inbody.model.entity.Pessoa;
import zcla71.inbody.model.service.InBodyService;
import zcla71.inbody.view.dto.PessoaEditar;
import zcla71.inbody.view.dto.PessoaListar;

@Component
public class InBodyController {
	@Autowired
	private InBodyService inBodyService;

	public PessoaEditar pessoaAlterar(String id) {
		Pessoa pessoa = inBodyService.buscarPessoa(id);

		return pessoaAlterar(pessoa);
	}

	public PessoaEditar pessoaAlterar(Pessoa pessoa) {
		PessoaEditar result = new PessoaEditar();

		result.setPessoa(pessoa);

		return result;
	}

	public void pessoaAlterarOk(PessoaEditar pessoaAlterar) {
		Pessoa pessoa = pessoaAlterar.getPessoa();

		// TODO Validar se existe
		ValidationException validation = pessoa.validate();
		if (!validation.getValidations().isEmpty()) {
			throw validation;
		}

		inBodyService.alterarPessoa(pessoa);
	}

	public PessoaEditar pessoaExcluir(String id) {
		Pessoa pessoa = inBodyService.buscarPessoa(id);

		return pessoaExcluir(pessoa);
	}

	public PessoaEditar pessoaExcluir(Pessoa pessoa) {
		PessoaEditar result = new PessoaEditar();

		result.setPessoa(pessoa);

		return result;
	}

	public void pessoaExcluirOk(PessoaEditar pessoaExcluir) {
		Pessoa pessoa = pessoaExcluir.getPessoa();

		// TODO Validar se existe
		// ValidationException validation = pessoa.validate();
		// if (!validation.getValidations().isEmpty()) {
		// 	throw validation;
		// }

		inBodyService.excluirPessoa(pessoa);
	}

	public PessoaEditar pessoaIncluir() {
		Pessoa pessoa = new Pessoa();
		pessoa.setNascimento(LocalDate.now().minus(50, ChronoUnit.YEARS));
		pessoa.setAltura(165);

		return pessoaIncluir(pessoa);
	}

	public PessoaEditar pessoaIncluir(Pessoa pessoa) {
		PessoaEditar result = new PessoaEditar();

		result.setPessoa(pessoa);

		return result;
	}

	public void pessoaIncluirOk(PessoaEditar pessoaIncluir) {
		Pessoa pessoa = pessoaIncluir.getPessoa();

		// TODO Validar se existe
		ValidationException validation = pessoa.validate();
		if (!validation.getValidations().isEmpty()) {
			throw validation;
		}

		inBodyService.incluirPessoa(pessoa);
	}

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
}
