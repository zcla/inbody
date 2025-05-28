package zcla71.inbody.model.entity;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import zcla71.inbody.model.service.Validation;
import zcla71.inbody.model.service.ValidationException;

@Data
public class Medicao {
	private String local;

	// Cabeçalho
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime dataHora;

	// // Análise da Composição Corporal
	// private Faixa<Float> aguaCorporalTotal;
	// private Faixa<Float> proteina;
	// private Faixa<Float> minerais;
	// private Faixa<Float> massaDeGordura;
	// private Faixa<Float> peso;

	// // Análise Músculo-Gordura
	// // peso (repetido)
	// // massaMuscularEsqueletica (repetido)
	// // massaDeGordura (repetido)

	// // Análise de Obesidade
	// private Float imc;
	// private Float pgc;

	// // Análise da Massa Magra Segmentar
	// private Corpo massaSegmentar;

	// // Análise da Massa Magra Segmentar
	// private Corpo gorduraSegmentar;

	// // Pontuação InBody
	// private Integer pontuacaoInBody;

	// // Controle de Peso
	// private Float pesoIdeal;
	// private Float controleDePeso;
	// private Float controleDeGordura;
	// private Float controleMuscular;

	// // Avaliação de Obesidade
	// private AvaliacaoImc avaliacaoImc;
	// private AvaliacaoPgc avaliacaoPgc;

	// // Relação Cintura-Quadril
	// private Float relacaoCinturaQuadril;

	// // Nível de Gordura Visceral
	// private Integer nivelDeGorduraVisceral;

	// // Dados adicionais
	// private Faixa<Float> massaMuscularEsqueletica;
	// private Faixa<Float> massaLivreDeGordura;
	// private Faixa<Integer> taxaMetabolicaBasal;
	// private Float circunferenciaDeCintura;
	// private Faixa<Integer> grauDeObesidade;
	// private Float smi;
	// private Integer ingestaoCaloricaRecomendada;

	// Perdas de calorias do exercício (decidi ignorar)

	// Impedância (decidi ignorar)

	public Medicao() {
		// this.aguaCorporalTotal = new Faixa<>();
		// this.proteina = new Faixa<>();
		// this.minerais = new Faixa<>();
		// this.massaDeGordura = new Faixa<>();
		// this.peso = new Faixa<>();
		// this.massaSegmentar = new Corpo();
		// this.gorduraSegmentar = new Corpo();
		// this.massaMuscularEsqueletica = new Faixa<>();
		// this.massaLivreDeGordura = new Faixa<>();
		// this.taxaMetabolicaBasal = new Faixa<>();
		// this.grauDeObesidade = new Faixa<>();
	}

	public ValidationException validate() {
		ValidationException result = new ValidationException();
		if (this.local.trim().length() < 1) {
			result.getValidations().add(new Validation("medicao.local", "Informe o local."));
		}
		if (this.dataHora == null) {
			result.getValidations().add(new Validation("medicao.dataHora", "Informe a data/hora."));
		}
		// TODO Ver as demais validações
		return result;
	}
}
