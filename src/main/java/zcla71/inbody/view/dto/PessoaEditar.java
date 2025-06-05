package zcla71.inbody.view.dto;

import java.util.List;

import lombok.Data;
import zcla71.chartjs.Configuration;
import zcla71.inbody.model.entity.Pessoa;

@Data
public class PessoaEditar {
	private List<SelectOption> sexos;
	private Pessoa pessoa;
	private Configuration graficoPeso;
	private Configuration graficoMassaMuscular;
	private Configuration graficoGorduraCorporal;

	public PessoaEditar() {
		this.sexos = SelectOption.sexos();
	}

	public PessoaEditar(Pessoa pessoa) {
		this();
		this.pessoa = pessoa;
	}
}
