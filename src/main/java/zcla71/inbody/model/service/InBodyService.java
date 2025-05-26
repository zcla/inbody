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

	public Pessoa buscarPessoa(String id) {
		return repository.getData().getPessoas().stream().filter(p -> p.getId().equals(id)).findAny().orElse(null);
	}

	public void alterarPessoa(Pessoa pessoa) {
		Pessoa existente = buscarPessoa(pessoa.getId());
		if (existente == null) {
			// TODO Ver o que fazer
			throw new RuntimeException("Pessoa não encontrada.");
		}
		existente.setNome(pessoa.getNome());
		existente.setNascimento(pessoa.getNascimento());
		existente.setAltura(pessoa.getAltura());
		existente.setSexo(pessoa.getSexo());
		try {
			repository.saveData();
		} catch (IOException e) {
			// TODO Ver o que fazer
			throw new RuntimeException(e);
		}
	}

	public void excluirPessoa(Pessoa pessoa) {
		Pessoa existente = buscarPessoa(pessoa.getId());
		if (existente == null) {
			// TODO Ver o que fazer
			throw new RuntimeException("Pessoa não encontrada.");
		}
		repository.getData().getPessoas().remove(existente);
		try {
			repository.saveData();
		} catch (IOException e) {
			// TODO Ver o que fazer
			throw new RuntimeException(e);
		}
	}

	public void incluirPessoa(Pessoa pessoa) {
		pessoa.setId(UUID.randomUUID().toString());
		Pessoa existente = buscarPessoa(pessoa.getId());
		if (existente != null) {
			// TODO Ver o que fazer
			throw new RuntimeException("Joga na loteria, rapá!");
		}
		repository.getData().getPessoas().add(pessoa);
		try {
			repository.saveData();
		} catch (IOException e) {
			// TODO Ver o que fazer
			throw new RuntimeException(e);
		}
	}

	public List<Pessoa> listarPessoas() {
		return repository.getData().getPessoas();
	}
}
