package zcla71.inbody.controller.model.service;

import java.util.List;

import zcla71.inbody.controller.model.dto.Pessoa;

public interface InBodyService {
	List<Pessoa> listarPessoas();

	void incluirPessoa(Pessoa pessoa);
}
