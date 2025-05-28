package zcla71.inbody.model.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zcla71.inbody.model.entity.Pessoa;
import zcla71.inbody.model.repository.InBodyRepository;

@Component
public class InBodyService {
	@Autowired
	private InBodyRepository repository;

	public void alterarPessoa(Pessoa pessoa) throws ServiceException, ValidationException {
		ValidationException validation = pessoa.validate();
		if (!validation.getValidations().isEmpty()) {
			throw validation;
		}

		Pessoa existente = buscarPessoa(pessoa.getId());
		if (existente == null) {
			throw new ServiceException("Pessoa não encontrada.");
		}
		existente.setNome(pessoa.getNome());
		existente.setNascimento(pessoa.getNascimento());
		existente.setAltura(pessoa.getAltura());
		existente.setSexo(pessoa.getSexo());
		try {
			repository.saveData();
		} catch (IOException e) {
			throw new ServiceException(e);
		}
	}

	public Pessoa buscarPessoa(String id) {
		return repository.getData().getPessoas().stream().filter(p -> p.getId().equals(id)).findAny().orElse(null);
	}

	public void excluirPessoa(Pessoa pessoa) throws ServiceException {
		Pessoa existente = buscarPessoa(pessoa.getId());
		if (existente == null) {
			throw new ServiceException("Pessoa não encontrada.");
		}
		repository.getData().getPessoas().remove(existente);
		try {
			repository.saveData();
		} catch (IOException e) {
			throw new ServiceException(e);
		}
	}

	public void incluirPessoa(Pessoa pessoa) throws ValidationException, ServiceException {
		ValidationException validation = pessoa.validate();
		if (!validation.getValidations().isEmpty()) {
			throw validation;
		}

		pessoa.setId(UUID.randomUUID().toString());
		Pessoa existente = buscarPessoa(pessoa.getId());
		if (existente != null) {
			throw new ServiceException("UUID gerado já existe!");
		}

		repository.getData().getPessoas().add(pessoa);
		try {
			repository.saveData();
		} catch (IOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Pessoa> listarPessoas() {
		return repository.getData().getPessoas();
	}
}
