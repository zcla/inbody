package zcla71.inbody.controller.model.dto;

import lombok.Data;

@Data
public class Faixa<T> {
	private T valor;
	private T minimo;
	private T maximo;
}
