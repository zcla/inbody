package zcla71.inbody.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Segmentar {
	private Float massa;
	private Float percentagem;
	private AvaliacaoSegmentar avaliacao;

	@JsonIgnore
	public Float getIdeal() {
		if ((massa != null) && (percentagem != null)) {
			return 100F * massa / percentagem;
		}

		return null;
	}
}
