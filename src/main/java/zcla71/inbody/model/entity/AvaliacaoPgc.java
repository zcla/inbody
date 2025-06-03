package zcla71.inbody.model.entity;

public enum AvaliacaoPgc {
	NORMAL("Normal"), LEVEMENTE_ACIMA("Levemente acima"), ACIMA("Acima");

	public final String nome;

	private AvaliacaoPgc(String nome) {
		this.nome = nome;
	}
}
