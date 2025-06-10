package zcla71.inbody.view.dto;

import java.util.List;

import lombok.Data;
import zcla71.chartjs.Configuration;
import zcla71.inbody.model.entity.Pessoa;

@Data
public class PessoaEditar {
	private List<SelectOption> sexos;
	private Pessoa pessoa;
	// Análise da Composição Corporal
	private Configuration graficoAguaCorporalTotal;
	private Configuration graficoProteina;
	private Configuration graficoMinerais;
	private Configuration graficoMassaDeGordura;
	private Configuration graficoPeso;
	// Análise Músculo-Gordura
	// (repetido) private Configuration graficoPeso;
	private Configuration graficoMassaMuscularEsqueletica;
	// (repetido) private Configuration graficoMassaDeGordura;
	// Análise de Obesidade
	private Configuration graficoImc;
	private Configuration graficoPgc;
	// Análise da Massa Magra Segmentar
	private Configuration graficoAnaliseDaMassaMagraSegmentarBracoEsquerdoMassa;
	private Configuration graficoAnaliseDaMassaMagraSegmentarBracoEsquerdoPercentagem;
	private Configuration graficoAnaliseDaMassaMagraSegmentarBracoDireitoMassa;
	private Configuration graficoAnaliseDaMassaMagraSegmentarBracoDireitoPercentagem;
	private Configuration graficoAnaliseDaMassaMagraSegmentarTroncoMassa;
	private Configuration graficoAnaliseDaMassaMagraSegmentarTroncoPercentagem;
	private Configuration graficoAnaliseDaMassaMagraSegmentarPernaEsquerdaMassa;
	private Configuration graficoAnaliseDaMassaMagraSegmentarPernaEsquerdaPercentagem;
	private Configuration graficoAnaliseDaMassaMagraSegmentarPernaDireitaMassa;
	private Configuration graficoAnaliseDaMassaMagraSegmentarPernaDireitaPercentagem;
	// Análise da Gordura Segmentar
	// Outras informações
	private Configuration graficoPontuacaoInBody;
	private Configuration graficoRelacaoCinturaQuadril;
	private Configuration graficoNivelGorduraVisceral;
	// Dados adicionais
	private Configuration graficoMassaLivreDeGordura;
	private Configuration graficoTaxaMetabolicaBasal;
	private Configuration graficoGrauDeObesidade;
	private Configuration graficoSmi;
	private Configuration graficoIngestaoCaloricaRecomendada;

	public PessoaEditar() {
		this.sexos = SelectOption.sexos();
	}

	public PessoaEditar(Pessoa pessoa) {
		this();
		this.pessoa = pessoa;
	}
}
