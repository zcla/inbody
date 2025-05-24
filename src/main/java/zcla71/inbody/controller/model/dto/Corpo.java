package zcla71.inbody.controller.model.dto;

import lombok.Data;

@Data
public class Corpo {
	private Segmentar bracoEsquerdo;
	private Segmentar bracoDireito;
	private Segmentar tronco;
	private Segmentar pernaEsquerda;
	private Segmentar pernaDireita;
}
