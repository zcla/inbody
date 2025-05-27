package zcla71.inbody.view.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import zcla71.inbody.model.entity.Pessoa;

@Data
public class PessoaEditar {
	@Data
	@AllArgsConstructor
	public class SelectOption {
		private String value;
		private String text;
	}

	private List<SelectOption> sexos;
	private Pessoa pessoa;

	public PessoaEditar() {
		this.sexos = new ArrayList<>();
		this.sexos.add(new SelectOption("", ""));
		this.sexos.add(new SelectOption("F", "Feminino"));
		this.sexos.add(new SelectOption("M", "Masculino"));
	}
}
