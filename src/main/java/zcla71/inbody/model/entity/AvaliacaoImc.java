package zcla71.inbody.model.entity;

public enum AvaliacaoImc {
	NORMAL("Normal"), ABAIXO("Abaixo"), LEVEMENTE_ACIMA("Levemente acima"), ACIMA("Acima");

	public final String nome;

	private AvaliacaoImc(String nome) {
		this.nome = nome;
	}
}
