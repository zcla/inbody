package zcla71.inbody.model.entity;

import lombok.Data;

@Data
public class FaixaInteger {
	private Integer valor;
	private Integer minimo;
	private Integer maximo;

	public Float getValorAsFloat() {
		if (valor == null) {
			return null;
		}
		return valor.floatValue();
	}

	public Float getMinimoAsFloat() {
		if (minimo == null) {
			return null;
		}
		return minimo.floatValue();
	}

	public Float getMaximoAsFloat() {
		if (maximo == null) {
			return null;
		}
		return maximo.floatValue();
	}
}
