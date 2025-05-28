package zcla71.inbody.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zcla71.inbody.model.entity.Pessoa;
import zcla71.inbody.model.service.InBodyService;
import zcla71.inbody.model.service.ServiceException;
import zcla71.inbody.model.service.ValidationException;
import zcla71.inbody.view.dto.PessoaEditar;
import zcla71.inbody.view.dto.PessoaListar;

@Component
public class InBodyController {
	@Autowired
	private InBodyService inBodyService;

	public PessoaEditar pessoaAlterar(String id) {
		Pessoa pessoa = inBodyService.buscarPessoa(id);

		if (pessoa == null) {
			throw new ControllerException("Pessoa não encontrada.");
		}

		return pessoaAlterar(pessoa);
	}

	public PessoaEditar pessoaAlterar(Pessoa pessoa) {
		return new PessoaEditar(pessoa);
	}

	public void pessoaAlterarOk(PessoaEditar pessoaAlterar) throws ValidationException {
		try {
			inBodyService.alterarPessoa(pessoaAlterar.getPessoa());
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
	}

	public PessoaEditar pessoaExcluir(String id) {
		Pessoa pessoa = inBodyService.buscarPessoa(id);

		if (pessoa == null) {
			throw new ControllerException("Pessoa não encontrada.");
		}

		return pessoaExcluir(pessoa);
	}

	public PessoaEditar pessoaExcluir(Pessoa pessoa) {
		return new PessoaEditar(pessoa);
	}

	public void pessoaExcluirOk(PessoaEditar pessoaExcluir) {
		try {
			inBodyService.excluirPessoa(pessoaExcluir.getPessoa());
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
	}

	public PessoaEditar pessoaIncluir() {
		Pessoa pessoa = new Pessoa();
		pessoa.setNascimento(LocalDate.now().minus(50, ChronoUnit.YEARS));
		pessoa.setAltura(165);

		return pessoaIncluir(pessoa);
	}

	public PessoaEditar pessoaIncluir(Pessoa pessoa) {
		return new PessoaEditar(pessoa);
	}

	public void pessoaIncluirOk(PessoaEditar pessoaIncluir) throws ValidationException {
		try {
			inBodyService.incluirPessoa(pessoaIncluir.getPessoa());
		} catch (ValidationException | ServiceException e) {
			throw new ControllerException(e);
		}
	}

	public PessoaListar pessoaListar() {
		return new PessoaListar(inBodyService.listarPessoas());
	}
}
