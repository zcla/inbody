package zcla71.inbody.view.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import zcla71.inbody.model.entity.Sexo;

@Data
@AllArgsConstructor
public class SelectOption {
	private String value;
	private String text;

	public static List<SelectOption> sexos() {
		List<SelectOption> result = new ArrayList<>();
		result.add(new SelectOption("", ""));
		for (Sexo sexo : Sexo.values()) {
			result.add(new SelectOption(sexo.name(), sexo.nome));
		}
		return result;
	}
}
