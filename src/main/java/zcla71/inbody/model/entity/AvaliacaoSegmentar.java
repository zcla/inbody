package zcla71.inbody.model.entity;

public enum AvaliacaoSegmentar {
	ABAIXO("Abaixo"), NORMAL("Normal"), ACIMA("Acima");

	public final String nome;

	private AvaliacaoSegmentar(String nome) {
		this.nome = nome;
	}
}
