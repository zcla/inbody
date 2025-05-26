package zcla71.inbody.model.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

import zcla71.inbody.model.entity.Pessoa;
import zcla71.inbody.model.repository.InBodyRepository;

@Component
public class InBodyService {
	@Autowired
	private InBodyRepository repository;

	public List<Pessoa> listarPessoas() {
		return repository.getData().getPessoas();
	}

	public void incluirPessoa(Pessoa pessoa) {
		pessoa.setId(UUID.randomUUID().toString());
		repository.getData().getPessoas().add(pessoa);
		try {
			repository.saveData();
		} catch (StreamWriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
