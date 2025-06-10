package zcla71.inbody.model.entity;

import lombok.Data;

@Data
public class FaixaFloat {
	private Float valor;
	private Float minimo;
	private Float maximo;

	public Float somaValor(Float value) {
		if (valor == null || value == null) {
			return null;
		}
		return valor + value;
	}
}
