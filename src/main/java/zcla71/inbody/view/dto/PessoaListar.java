package zcla71.inbody.view.dto;

import java.util.List;

import lombok.Data;
import zcla71.inbody.model.entity.Pessoa;

@Data
public class PessoaListar {
	private List<Pessoa> pessoas;

	public PessoaListar(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
}
