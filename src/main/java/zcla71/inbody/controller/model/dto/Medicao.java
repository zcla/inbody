package zcla71.inbody.controller.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Medicao {
	private String titulo;

	// Cabeçalho
	private LocalDateTime dataHora;

	// Análise da Composição Corporal
	private Faixa<Float> aguaCorporalTotal;
	private Faixa<Float> proteina;
	private Faixa<Float> minerais;
	private Faixa<Float> massaDeGordura;
	private Faixa<Float> peso;

	// Análise Músculo-Gordura
	// peso (repetido)
	// massaMuscularEsqueletica (repetido)
	// massaDeGordura (repetido)

	// Análise de Obesidade
	private Float imc;
	private Float pgc;

	// Análise da Massa Magra Segmentar
	private Corpo massaSegmentar;

	// Análise da Massa Magra Segmentar
	private Corpo gorduraSegmentar;

	// Pontuação InBody
	private Integer pontuacaoInBody;

	// Controle de Peso
	private Float pesoIdeal;
	private Float controleDePeso;
	private Float controleDeGordura;
	private Float controleMuscular;

	// Avaliação de Obesidade
	private AvaliacaoImc avaliacaoImc;
	private AvaliacaoPgc avaliacaoPgc;

	// Relação Cintura-Quadril
	private Float relacaoCinturaQuadril;

	// Nível de Gordura Visceral
	private Integer nivelDeGorduraVisceral;

	// Dados adicionais
	private Faixa<Float> massaMuscularEsqueletica;
	private Faixa<Float> massaLivreDeGordura;
	private Faixa<Integer> taxaMetabolicaBasal;
	private Float circunferenciaDeCintura;
	private Faixa<Integer> grauDeObesidade;
	private Float smi;
	private Integer ingestaoCaloricaRecomendada;

	// Perdas de calorias do exercício (decidi ignorar)

	// Impedância (decidi ignorar)
}
