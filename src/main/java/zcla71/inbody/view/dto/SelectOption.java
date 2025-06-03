package zcla71.inbody.view.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import zcla71.inbody.model.entity.AvaliacaoImc;
import zcla71.inbody.model.entity.AvaliacaoPgc;
import zcla71.inbody.model.entity.AvaliacaoSegmentar;
import zcla71.inbody.model.entity.Sexo;

@Data
@AllArgsConstructor
public class SelectOption {
	private String value;
	private String text;

	public static List<SelectOption> avaliacaoImc() {
		List<SelectOption> result = new ArrayList<>();
		result.add(new SelectOption("", ""));
		for (AvaliacaoImc avaliacaoImc : AvaliacaoImc.values()) {
			result.add(new SelectOption(avaliacaoImc.name(), avaliacaoImc.nome));
		}
		return result;
	}

	public static List<SelectOption> avaliacaoPgc() {
		List<SelectOption> result = new ArrayList<>();
		result.add(new SelectOption("", ""));
		for (AvaliacaoPgc avaliacaoPgc : AvaliacaoPgc.values()) {
			result.add(new SelectOption(avaliacaoPgc.name(), avaliacaoPgc.nome));
		}
		return result;
	}

	public static List<SelectOption> avaliacaoSegmentar() {
		List<SelectOption> result = new ArrayList<>();
		result.add(new SelectOption("", ""));
		for (AvaliacaoSegmentar avaliacaoSegmentar : AvaliacaoSegmentar.values()) {
			result.add(new SelectOption(avaliacaoSegmentar.name(), avaliacaoSegmentar.nome));
		}
		return result;
	}

	public static List<SelectOption> sexos() {
		List<SelectOption> result = new ArrayList<>();
		result.add(new SelectOption("", ""));
		for (Sexo sexo : Sexo.values()) {
			result.add(new SelectOption(sexo.name(), sexo.nome));
		}
		return result;
	}
}
