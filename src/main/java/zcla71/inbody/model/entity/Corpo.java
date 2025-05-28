package zcla71.inbody.model.entity;

import lombok.Data;

@Data
public class Corpo {
	private Segmentar bracoEsquerdo;
	private Segmentar bracoDireito;
	private Segmentar tronco;
	private Segmentar pernaEsquerda;
	private Segmentar pernaDireita;

	public Corpo() {
		this.bracoEsquerdo = new Segmentar();
		this.bracoDireito = new Segmentar();
		this.tronco = new Segmentar();
		this.pernaEsquerda = new Segmentar();
		this.pernaDireita = new Segmentar();
	}
}
