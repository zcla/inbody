package zcla71.inbody.model.entity;

import lombok.Data;

@Data
public class Faixa<T> {
	private T valor;
	private T minimo;
	private T maximo;
}
