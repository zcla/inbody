package zcla71.inbody.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class FaixaInteger {
	private Integer valor;
	private Integer minimo;
	private Integer maximo;

	@JsonIgnore
	public Float getValorAsFloat() {
		if (valor == null) {
			return null;
		}
		return valor.floatValue();
	}

	@JsonIgnore
	public Float getMinimoAsFloat() {
		if (minimo == null) {
			return null;
		}
		return minimo.floatValue();
	}

	@JsonIgnore
	public Float getMaximoAsFloat() {
		if (maximo == null) {
			return null;
		}
		return maximo.floatValue();
	}
}
