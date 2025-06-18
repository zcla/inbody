package zcla71.inbody.model.entity;

import java.time.LocalDateTime;
import java.util.Comparator;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import zcla71.inbody.controller.InBodyController;
import zcla71.inbody.model.service.Validation;
import zcla71.inbody.model.service.ValidationException;

@Data
public class Medicao {
	private String id;
	private String local;

	// Cabeçalho
	@DateTimeFormat(pattern = InBodyController.ENTITY_PATTERN_DATE_TIME)
	private LocalDateTime dataHora;

	// Análise da Composição Corporal
	private FaixaFloat aguaCorporalTotal;
	private FaixaFloat proteina;
	private FaixaFloat minerais;
	private FaixaFloat massaDeGordura;
	private FaixaFloat peso;

	// Análise Músculo-Gordura
	// peso (-> peso.valor)
	private FaixaFloat percentualPeso;
	// massaMuscularEsqueletica (-> massaMuscularEsqueletica.valor)
	private FaixaFloat percentualMassaMuscularEsqueletica;
	// massaDeGordura (-> massaDeGordura.valor)
	private FaixaFloat percentualMassaDeGordura;

	// Análise de Obesidade
	private Float imc;
	private Float pgc;

	// Análise da Massa Magra Segmentar
	private Corpo massaMagraSegmentar;

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
	private FaixaFloat massaMuscularEsqueletica;
	private FaixaFloat massaLivreDeGordura;
	private FaixaInteger taxaMetabolicaBasal;
	private Float circunferenciaDeCintura;
	private FaixaInteger grauDeObesidade;
	private Float smi;
	private Integer ingestaoCaloricaRecomendada;

	// Perdas de calorias do exercício (decidi ignorar)

	// Impedância (decidi ignorar)

	public Medicao() {
		this.aguaCorporalTotal = new FaixaFloat();
		this.proteina = new FaixaFloat();
		this.minerais = new FaixaFloat();
		this.massaDeGordura = new FaixaFloat();
		this.peso = new FaixaFloat();

		this.percentualPeso = new FaixaFloat();
		this.percentualMassaMuscularEsqueletica = new FaixaFloat();
		this.percentualMassaDeGordura = new FaixaFloat();

		this.massaMagraSegmentar = new Corpo();

		this.gorduraSegmentar = new Corpo();

		this.massaMuscularEsqueletica = new FaixaFloat();
		this.massaLivreDeGordura = new FaixaFloat();
		this.taxaMetabolicaBasal = new FaixaInteger();
		this.grauDeObesidade = new FaixaInteger();
	}

	@JsonIgnore
	public Float getIngestaoCaloricaRecomendadaAsFloat() {
		return this.ingestaoCaloricaRecomendada == null ? null : Float.valueOf(this.ingestaoCaloricaRecomendada);
	}

	@JsonIgnore
	public Float getMassaDeGorduraIdeal() {
		return this.massaDeGordura.somaValor(this.controleDeGordura);
	}

	@JsonIgnore
	public Float getMassaDeGorduraMaxima() {
		if (massaDeGordura.getMaximo() != null) {
			return massaDeGordura.getMaximo();
		}

		if ((getMassaDeGorduraIdeal() != null) && (percentualMassaDeGordura.getValor() != null) && (percentualMassaDeGordura.getMaximo() != null)) {
			return getMassaDeGorduraIdeal() / percentualMassaDeGordura.getValor() * percentualMassaDeGordura.getMaximo();
		}

		return null;
	}

	@JsonIgnore
	public Float getMassaDeGorduraMinima() {
		if (massaDeGordura.getMinimo() != null) {
			return massaDeGordura.getMinimo();
		}

		if ((getMassaDeGorduraIdeal() != null) && (percentualMassaDeGordura.getValor() != null) && (percentualMassaDeGordura.getMinimo() != null)) {
			return getMassaDeGorduraIdeal() / percentualMassaDeGordura.getValor() * percentualMassaDeGordura.getMinimo();
		}

		return null;
	}

	@JsonIgnore
	public Float getMassaMuscularEsqueleticaIdeal() {
		return this.massaMuscularEsqueletica.somaValor(this.controleMuscular);
	}

	@JsonIgnore
	public Float getMassaMuscularEsqueleticaMaxima() {
		if (massaMuscularEsqueletica.getMaximo() != null) {
			return massaMuscularEsqueletica.getMaximo();
		}

		if ((getMassaMuscularEsqueleticaIdeal() != null) && (percentualMassaMuscularEsqueletica.getValor() != null) && (percentualMassaMuscularEsqueletica.getMaximo() != null)) {
			return getMassaMuscularEsqueleticaIdeal() / percentualMassaMuscularEsqueletica.getValor() * percentualMassaMuscularEsqueletica.getMaximo();
		}

		return null;
	}

	@JsonIgnore
	public Float getMassaMuscularEsqueleticaMinima() {
		if (massaMuscularEsqueletica.getMinimo() != null) {
			return massaMuscularEsqueletica.getMinimo();
		}

		if ((getMassaMuscularEsqueleticaIdeal() != null) && (percentualMassaMuscularEsqueletica.getValor() != null) && (percentualMassaMuscularEsqueletica.getMinimo() != null)) {
			return getMassaMuscularEsqueleticaIdeal() / percentualMassaMuscularEsqueletica.getValor() * percentualMassaMuscularEsqueletica.getMinimo();
		}

		return null;
	}

	@JsonIgnore
	public Float getNivelDeGorduraVisceralAsFloat() {
		return this.nivelDeGorduraVisceral == null ? null : Float.valueOf(this.nivelDeGorduraVisceral);
	}

	public Float getPesoIdeal() {
		if (pesoIdeal == null) {
			return this.peso.somaValor(this.controleDePeso);
		}
		return pesoIdeal;
	}

	@JsonIgnore
	public Float getPesoMaximo() {
		if (peso.getMaximo() != null) {
			return peso.getMaximo();
		}

		if ((getPesoIdeal() != null) && (percentualPeso.getValor() != null) && (percentualPeso.getMaximo() != null)) {
			return getPesoIdeal() / percentualPeso.getValor() * percentualPeso.getMaximo();
		}

		return null;
	}

	@JsonIgnore
	public Float getPesoMinimo() {
		if (peso.getMinimo() != null) {
			return peso.getMinimo();
		}

		if ((getPesoIdeal() != null) && (percentualPeso.getValor() != null) && (percentualPeso.getMinimo() != null)) {
			return getPesoIdeal() / percentualPeso.getValor() * percentualPeso.getMinimo();
		}

		return null;
	}

	@JsonIgnore
	public Float getPontuacaoInBodyAsFloat() {
		return this.pontuacaoInBody == null ? null : Float.valueOf(this.pontuacaoInBody);
	}

	public static class MedicaoComparator implements Comparator<Medicao> {
		@Override
		public int compare(Medicao o1, Medicao o2) {
			return o1.getDataHora().compareTo(o2.getDataHora());
		}
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

	public void copyDataFrom(Medicao medicao) {
		this.local = medicao.getLocal();

		this.dataHora = medicao.getDataHora();

		this.aguaCorporalTotal = medicao.getAguaCorporalTotal();
		this.proteina = medicao.getProteina();
		this.minerais = medicao.getMinerais();
		this.massaDeGordura = medicao.getMassaDeGordura();
		this.peso = medicao.getPeso();

		this.percentualPeso = medicao.getPercentualPeso();
		this.percentualMassaMuscularEsqueletica = medicao.getPercentualMassaMuscularEsqueletica();
		this.percentualMassaDeGordura = medicao.getPercentualMassaDeGordura();

		this.massaMuscularEsqueletica = medicao.getMassaMuscularEsqueletica();

		this.imc = medicao.getImc();
		this.pgc = medicao.getPgc();

		this.massaMagraSegmentar = medicao.getMassaMagraSegmentar();
		this.gorduraSegmentar = medicao.getGorduraSegmentar();

		this.pontuacaoInBody = medicao.getPontuacaoInBody();

		this.pesoIdeal = medicao.getPesoIdeal();
		this.controleDePeso = medicao.getControleDePeso();
		this.controleDeGordura = medicao.getControleDeGordura();
		this.controleMuscular = medicao.getControleMuscular();

		this.avaliacaoImc = medicao.getAvaliacaoImc();
		this.avaliacaoPgc = medicao.getAvaliacaoPgc();

		this.relacaoCinturaQuadril = medicao.getRelacaoCinturaQuadril();

		this.nivelDeGorduraVisceral = medicao.getNivelDeGorduraVisceral();
		this.massaLivreDeGordura = medicao.getMassaLivreDeGordura();
		this.taxaMetabolicaBasal = medicao.getTaxaMetabolicaBasal();
		this.circunferenciaDeCintura = medicao.getCircunferenciaDeCintura();
		this.grauDeObesidade = medicao.getGrauDeObesidade();
		this.smi = medicao.getSmi();
		this.ingestaoCaloricaRecomendada = medicao.getIngestaoCaloricaRecomendada();
	}
}
