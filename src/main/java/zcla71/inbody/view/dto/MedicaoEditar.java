package zcla71.inbody.view.dto;

import java.util.List;

import lombok.Data;
import zcla71.inbody.model.entity.Medicao;
import zcla71.inbody.model.entity.Pessoa;

@Data
public class MedicaoEditar {
	private List<SelectOption> sexos;
	private Pessoa pessoa;
	private Medicao medicao;

	public MedicaoEditar() {
		this.sexos = SelectOption.sexos();
	}

	public MedicaoEditar(Pessoa pessoa, Medicao medicao) {
		this();
		this.pessoa = pessoa;
		this.medicao = medicao;
	}
}
