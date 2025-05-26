package zcla71.inbody.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zcla71.inbody.model.entity.Pessoa;
import zcla71.inbody.model.service.InBodyService;
import zcla71.inbody.view.dto.PessoaAlterar;
import zcla71.inbody.view.dto.PessoaAlterarOk;
import zcla71.inbody.view.dto.PessoaExcluir;
import zcla71.inbody.view.dto.PessoaExcluirOk;
import zcla71.inbody.view.dto.PessoaIncluir;
import zcla71.inbody.view.dto.PessoaIncluirOk;
import zcla71.inbody.view.dto.PessoaListar;

@Component
public class InBodyController {
	@Autowired
	private InBodyService inBodyService;

	public PessoaAlterar pessoaAlterar(String id) {
		Pessoa pessoa = inBodyService.buscarPessoa(id);

		return pessoaAlterar(pessoa);
	}

	public PessoaAlterar pessoaAlterar(Pessoa pessoa) {
		PessoaAlterar result = new PessoaAlterar();

		result.setPessoa(pessoa);

		return result;
	}

	public void pessoaAlterarOk(PessoaAlterarOk pessoaAlterar) {
		Pessoa pessoa = pessoaAlterar.getPessoa();

		// TODO Validar se existe
		ValidationException validation = pessoa.validate();
		if (!validation.getValidations().isEmpty()) {
			throw validation;
		}

		inBodyService.alterarPessoa(pessoa);
	}

	public PessoaExcluir pessoaExcluir(String id) {
		Pessoa pessoa = inBodyService.buscarPessoa(id);

		return pessoaExcluir(pessoa);
	}

	public PessoaExcluir pessoaExcluir(Pessoa pessoa) {
		PessoaExcluir result = new PessoaExcluir();

		result.setPessoa(pessoa);

		return result;
	}

	public void pessoaExcluirOk(PessoaExcluirOk pessoaExcluir) {
		Pessoa pessoa = pessoaExcluir.getPessoa();

		// TODO Validar se existe
		// ValidationException validation = pessoa.validate();
		// if (!validation.getValidations().isEmpty()) {
		// 	throw validation;
		// }

		inBodyService.excluirPessoa(pessoa);
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
