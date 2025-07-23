package zcla71.inbody.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zcla71.chartjs.Configuration;
import zcla71.chartjs.Data;
import zcla71.chartjs.Dataset;
import zcla71.chartjs.Options;
import zcla71.chartjs.Scale;
import zcla71.inbody.model.entity.Medicao;
import zcla71.inbody.model.entity.Pessoa;
import zcla71.inbody.model.service.InBodyService;
import zcla71.inbody.model.service.ServiceException;
import zcla71.inbody.model.service.ValidationException;
import zcla71.inbody.view.dto.MedicaoEditar;
import zcla71.inbody.view.dto.PessoaEditar;
import zcla71.inbody.view.dto.PessoaListar;

@Component
public class InBodyController {
	// Para usar nas entities: @DateTimeFormat(pattern = InBodyController.ENTITY_PATTERN_DATE)
	public static final String ENTITY_PATTERN_DATE = "yyyy-MM-dd";
	public static final String ENTITY_PATTERN_TIME = "HH:mm";
	public static final String ENTITY_PATTERN_DATE_TIME = ENTITY_PATTERN_DATE + " " + ENTITY_PATTERN_TIME;
	// Para usar na exibição ao usuário
	public static final String VIEW_FORMAT_DATE = "dd/MM/yyyy";
	public static final String VIEW_FORMAT_TIME = "HH:mm";
	public static final String VIEW_FORMAT_DATE_TIME = VIEW_FORMAT_DATE + " " + VIEW_FORMAT_TIME;
	// Cores
	private final String DATASET_COLOR_MEASURED = "rgb(33, 64, 154)"; // #21409a
	private final String DATASET_COLOR_BAD = "rgb(247, 161, 154)"; // #f7a19a
	private final String DATASET_COLOR_GOOD = "rgb(140, 207, 183)"; // #8ccfb7
	@Autowired
	private InBodyService inBodyService;

	// Pessoa

	public PessoaEditar pessoaAlterar(String id) {
		Pessoa pessoa = inBodyService.pessoaBuscar(id);
		if (pessoa == null) {
			throw new ControllerException("Pessoa não encontrada.");
		}

		return pessoaAlterar(pessoa);
	}

	public PessoaEditar pessoaAlterar(Pessoa pessoa) {
		return new PessoaEditar(pessoa);
	}

	public void pessoaAlterarOk(PessoaEditar pessoaAlterar) throws ValidationException {
		try {
			inBodyService.pessoaAlterar(pessoaAlterar.getPessoa());
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
	}

	public PessoaEditar pessoaExcluir(String id) {
		Pessoa pessoa = inBodyService.pessoaBuscar(id);
		if (pessoa == null) {
			throw new ControllerException("Pessoa não encontrada.");
		}

		return pessoaExcluir(pessoa);
	}

	public PessoaEditar pessoaExcluir(Pessoa pessoa) {
		return new PessoaEditar(pessoa);
	}

	public void pessoaExcluirOk(PessoaEditar pessoaExcluir) {
		try {
			inBodyService.pessoaExcluir(pessoaExcluir.getPessoa());
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
	}

	public PessoaEditar pessoaIncluir() {
		Pessoa pessoa = new Pessoa();
		pessoa.setNascimento(LocalDate.now().minus(50, ChronoUnit.YEARS));
		pessoa.setAltura(165);

		return pessoaIncluir(pessoa);
	}

	public PessoaEditar pessoaIncluir(Pessoa pessoa) {
		return new PessoaEditar(pessoa);
	}

	public void pessoaIncluirOk(PessoaEditar pessoaIncluir) throws ValidationException {
		try {
			inBodyService.pessoaIncluir(pessoaIncluir.getPessoa());
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
	}

	public PessoaListar pessoaListar() {
		PessoaListar result = new PessoaListar(inBodyService.pessoaListar());
		result.getPessoas().sort(new Pessoa.PessoaComparator());
		return result;
	}

	public PessoaEditar pessoaMostrar(String id) {
		Pessoa pessoa = inBodyService.pessoaBuscar(id);
		if (pessoa == null) {
			throw new ControllerException("Pessoa não encontrada.");
		}

		return pessoaMostrar(pessoa);
	}

	public PessoaEditar pessoaMostrar(Pessoa pessoa) {
		PessoaEditar result = new PessoaEditar(pessoa);
		result.getPessoa().getMedicoes().sort(new Medicao.MedicaoComparator());

		List<String> labels = pessoa.getMedicoes().stream().map(m -> m.getDataHora().format(DateTimeFormatter.ofPattern(VIEW_FORMAT_DATE))).collect(Collectors.toList());
		Float tension = 0.2f;

		// Análise da Composição Corporal

		result.setGraficoAguaCorporalTotal(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Água Corporal Total (L)",
				pessoa.getMedicoes().stream().map(m -> m.getAguaCorporalTotal().getValor()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			), new Dataset(
				"Mínimo",
				pessoa.getMedicoes().stream().map(m -> m.getAguaCorporalTotal().getMinimo()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			), new Dataset(
				"Máximo",
				pessoa.getMedicoes().stream().map(m -> m.getAguaCorporalTotal().getMaximo()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			))
		)));

		result.setGraficoProteina(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Proteína (kg)",
				pessoa.getMedicoes().stream().map(m -> m.getProteina().getValor()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			), new Dataset(
				"Mínimo",
				pessoa.getMedicoes().stream().map(m -> m.getProteina().getMinimo()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			), new Dataset(
				"Máximo",
				pessoa.getMedicoes().stream().map(m -> m.getProteina().getMaximo()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			))
		)));

		result.setGraficoMinerais(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Minerais (kg)",
				pessoa.getMedicoes().stream().map(m -> m.getMinerais().getValor()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			), new Dataset(
				"Mínimo",
				pessoa.getMedicoes().stream().map(m -> m.getMinerais().getMinimo()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			), new Dataset(
				"Máximo",
				pessoa.getMedicoes().stream().map(m -> m.getMinerais().getMaximo()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			))
		)));

		result.setGraficoMassaDeGordura(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Massa de Gordura (kg)",
				pessoa.getMedicoes().stream().map(m -> m.getMassaDeGordura().getValor()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			), new Dataset(
				"Mínimo",
				pessoa.getMedicoes().stream().map(m -> m.getMassaDeGorduraMinima()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			), new Dataset(
				"Máximo",
				pessoa.getMedicoes().stream().map(m -> m.getMassaDeGorduraMaxima()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			), new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getMassaDeGorduraIdeal()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		result.setGraficoPeso(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Peso (kg)",
				pessoa.getMedicoes().stream().map(m -> m.getPeso().getValor()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			), new Dataset(
				"Mínimo",
				pessoa.getMedicoes().stream().map(m -> m.getPesoMinimo()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			), new Dataset(
				"Máximo",
				pessoa.getMedicoes().stream().map(m -> m.getPesoMaximo()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			), new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getPesoIdeal()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		// Análise Músculo-Gordura

		// (repetido) setGraficoPeso();

		result.setGraficoMassaMuscularEsqueletica(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Massa Muscular Esquelética (kg)",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMuscularEsqueletica().getValor()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			), new Dataset(
				"Mínimo",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMuscularEsqueleticaMinima()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			), new Dataset(
				"Máximo",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMuscularEsqueleticaMaxima()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			), new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMuscularEsqueleticaIdeal()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		// (repetido) setGraficoMassaDeGordura();

		// Análise de Obesidade / Avaliação de Obesidade

		Options optAvaliacaoDeObesidade = new Options();
		Map<String, Scale> scales = new HashMap<>();
		optAvaliacaoDeObesidade.setScales(scales);
		scales.put("y", new Scale("linear", null, "stack", 3));
		scales.put("y2", new Scale("category", new ArrayList<>(), "stack", 2));
		scales.get("y2").getLabels().add("Acima");
		scales.get("y2").getLabels().add("Levemente acima");
		scales.get("y2").getLabels().add("Normal");
		scales.get("y2").getLabels().add("Abaixo");
		scales.get("y2").getLabels().add("");
		result.setGraficoImc(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"IMC (kg/m²)",
				pessoa.getMedicoes().stream().map(m -> m.getImc()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED,
				false,
				"y"
			), new Dataset(
				"Mínimo",
				pessoa.getMedicoes().stream().map(m -> m.getImcFaixa().getMinimo()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD,
				false,
				"y"
			), new Dataset(
				"Máximo",
				pessoa.getMedicoes().stream().map(m -> m.getImcFaixa().getMaximo()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD,
				false,
				"y"
			), new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getImcFaixa().getValor()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD,
				false,
				"y"
			), new Dataset(
				"Avaliação de Obesidade",
				pessoa.getMedicoes().stream().map(m -> m.getAvaliacaoImc() == null ? null : m.getAvaliacaoImc().nome).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED,
				true,
				"y2"
			))
		), optAvaliacaoDeObesidade));

		// TODO Adicionar Avaliação de Obesidade
		result.setGraficoPgc(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"PGC (%)",
				pessoa.getMedicoes().stream().map(m -> m.getPgc()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			), new Dataset(
				"Mínimo",
				pessoa.getMedicoes().stream().map(m -> m.getPgcFaixa().getMinimo()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			), new Dataset(
				"Máximo",
				pessoa.getMedicoes().stream().map(m -> m.getPgcFaixa().getMaximo()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			), new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getPgcFaixa().getValor()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		// Análise da Massa Magra Segmentar
		// TODO Mostrar AvaliacaoSegmentar
		// TODO Tentar descobrir as faixas de avaliação segmentar

		result.setGraficoAnaliseDaMassaMagraSegmentarBracoEsquerdoMassa(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Braço esquerdo (kg)",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMagraSegmentar().getBracoEsquerdo().getMassa()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),
			new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMagraSegmentar().getBracoEsquerdo().getIdeal()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		result.setGraficoAnaliseDaMassaMagraSegmentarBracoEsquerdoPercentagem(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Braço esquerdo (%)",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMagraSegmentar().getBracoEsquerdo().getPercentagem()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),
			new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMagraSegmentar().getBracoEsquerdo().getPercentagem() == null ? null : 100F).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		result.setGraficoAnaliseDaMassaMagraSegmentarBracoDireitoMassa(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Braço direito (kg)",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMagraSegmentar().getBracoDireito().getMassa()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),
			new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMagraSegmentar().getBracoDireito().getIdeal()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		result.setGraficoAnaliseDaMassaMagraSegmentarBracoDireitoPercentagem(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Braço direito (%)",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMagraSegmentar().getBracoDireito().getPercentagem()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),
			new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMagraSegmentar().getBracoDireito().getPercentagem() == null ? null : 100F).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		result.setGraficoAnaliseDaMassaMagraSegmentarTroncoMassa(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Tronco (kg)",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMagraSegmentar().getTronco().getMassa()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),
			new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMagraSegmentar().getTronco().getIdeal()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		result.setGraficoAnaliseDaMassaMagraSegmentarTroncoPercentagem(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Tronco (%)",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMagraSegmentar().getTronco().getPercentagem()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),
			new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMagraSegmentar().getTronco().getPercentagem() == null ? null : 100F).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		result.setGraficoAnaliseDaMassaMagraSegmentarPernaEsquerdaMassa(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Perna esquerda (kg)",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMagraSegmentar().getPernaEsquerda().getMassa()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),
			new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMagraSegmentar().getPernaEsquerda().getIdeal()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		result.setGraficoAnaliseDaMassaMagraSegmentarPernaEsquerdaPercentagem(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Perna esquerda (%)",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMagraSegmentar().getPernaEsquerda().getPercentagem()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),
			new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMagraSegmentar().getPernaEsquerda().getPercentagem() == null ? null : 100F).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		result.setGraficoAnaliseDaMassaMagraSegmentarPernaDireitaMassa(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Perna direita (kg)",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMagraSegmentar().getPernaDireita().getMassa()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),
			new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMagraSegmentar().getPernaDireita().getIdeal()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		result.setGraficoAnaliseDaMassaMagraSegmentarPernaDireitaPercentagem(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Perna direita (%)",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMagraSegmentar().getPernaDireita().getPercentagem()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),
			new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMagraSegmentar().getPernaDireita().getPercentagem() == null ? null : 100F).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		// Análise da Gordura Segmentar
		// TODO Mostrar AvaliacaoSegmentar
		// TODO Tentar descobrir as faixas de avaliação segmentar

		result.setGraficoAnaliseDaGorduraSegmentarBracoEsquerdoMassa(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Braço esquerdo (kg)",
				pessoa.getMedicoes().stream().map(m -> m.getGorduraSegmentar().getBracoEsquerdo().getMassa()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),
			new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getGorduraSegmentar().getBracoEsquerdo().getIdeal()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		result.setGraficoAnaliseDaGorduraSegmentarBracoEsquerdoPercentagem(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Braço esquerdo (%)",
				pessoa.getMedicoes().stream().map(m -> m.getGorduraSegmentar().getBracoEsquerdo().getPercentagem()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),
			new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getGorduraSegmentar().getBracoEsquerdo().getPercentagem() == null ? null : 100F).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		result.setGraficoAnaliseDaGorduraSegmentarBracoDireitoMassa(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Braço direito (kg)",
				pessoa.getMedicoes().stream().map(m -> m.getGorduraSegmentar().getBracoDireito().getMassa()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),
			new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getGorduraSegmentar().getBracoDireito().getIdeal()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		result.setGraficoAnaliseDaGorduraSegmentarBracoDireitoPercentagem(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Braço direito (%)",
				pessoa.getMedicoes().stream().map(m -> m.getGorduraSegmentar().getBracoDireito().getPercentagem()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),
			new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getGorduraSegmentar().getBracoDireito().getPercentagem() == null ? null : 100F).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		result.setGraficoAnaliseDaGorduraSegmentarTroncoMassa(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Tronco (kg)",
				pessoa.getMedicoes().stream().map(m -> m.getGorduraSegmentar().getTronco().getMassa()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),
			new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getGorduraSegmentar().getTronco().getIdeal()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		result.setGraficoAnaliseDaGorduraSegmentarTroncoPercentagem(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Tronco (%)",
				pessoa.getMedicoes().stream().map(m -> m.getGorduraSegmentar().getTronco().getPercentagem()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),
			new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getGorduraSegmentar().getTronco().getPercentagem() == null ? null : 100F).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		result.setGraficoAnaliseDaGorduraSegmentarPernaEsquerdaMassa(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Perna esquerda (kg)",
				pessoa.getMedicoes().stream().map(m -> m.getGorduraSegmentar().getPernaEsquerda().getMassa()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),
			new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getGorduraSegmentar().getPernaEsquerda().getIdeal()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		result.setGraficoAnaliseDaGorduraSegmentarPernaEsquerdaPercentagem(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Perna esquerda (%)",
				pessoa.getMedicoes().stream().map(m -> m.getGorduraSegmentar().getPernaEsquerda().getPercentagem()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),
			new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getGorduraSegmentar().getPernaEsquerda().getPercentagem() == null ? null : 100F).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		result.setGraficoAnaliseDaGorduraSegmentarPernaDireitaMassa(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Perna direita (kg)",
				pessoa.getMedicoes().stream().map(m -> m.getGorduraSegmentar().getPernaDireita().getMassa()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),
			new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getGorduraSegmentar().getPernaDireita().getIdeal()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		result.setGraficoAnaliseDaGorduraSegmentarPernaDireitaPercentagem(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Perna direita (%)",
				pessoa.getMedicoes().stream().map(m -> m.getGorduraSegmentar().getPernaDireita().getPercentagem()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),
			new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getGorduraSegmentar().getPernaDireita().getPercentagem() == null ? null : 100F).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		// Outras informações

		result.setGraficoPontuacaoInBody(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Pontuação InBody (pontos)",
				pessoa.getMedicoes().stream().map(m -> m.getPontuacaoInBodyAsFloat()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getPontuacaoIdealAsFloat()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		result.setGraficoRelacaoCinturaQuadril(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Relação Cintura-Quadril",
				pessoa.getMedicoes().stream().map(m -> m.getRelacaoCinturaQuadril().getValor()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			), new Dataset(
				"Mínimo",
				pessoa.getMedicoes().stream().map(m -> m.getRelacaoCinturaQuadril().getMinimo()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			), new Dataset(
				"Máximo",
				pessoa.getMedicoes().stream().map(m -> m.getRelacaoCinturaQuadril().getMaximo()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			))
		)));

		result.setGraficoNivelGorduraVisceral(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Nível de Gordura Visceral",
				pessoa.getMedicoes().stream().map(m -> m.getNivelDeGorduraVisceralAsFloat()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			),new Dataset(
				"Ideal",
				pessoa.getMedicoes().stream().map(m -> m.getNivelDeGorduraVisceralIdealAsFloat()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_GOOD
			))
		)));

		// Dados adicionais

		result.setGraficoMassaLivreDeGordura(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Massa Livre de Gordura (kg)",
				pessoa.getMedicoes().stream().map(m -> m.getMassaLivreDeGordura().getValor()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			), new Dataset(
				"Mínimo",
				pessoa.getMedicoes().stream().map(m -> m.getMassaLivreDeGordura().getMinimo()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			), new Dataset(
				"Máximo",
				pessoa.getMedicoes().stream().map(m -> m.getMassaLivreDeGordura().getMaximo()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			))
		)));

		result.setGraficoTaxaMetabolicaBasal(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Taxa Metabólica Basal (kcal)",
				pessoa.getMedicoes().stream().map(m -> m.getTaxaMetabolicaBasal().getValorAsFloat()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			), new Dataset(
				"Mínimo",
				pessoa.getMedicoes().stream().map(m -> m.getTaxaMetabolicaBasal().getMinimoAsFloat()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			), new Dataset(
				"Máximo",
				pessoa.getMedicoes().stream().map(m -> m.getTaxaMetabolicaBasal().getMaximoAsFloat()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			))
		)));

		result.setGraficoGrauDeObesidade(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Grau de obesidade (%)",
				pessoa.getMedicoes().stream().map(m -> m.getGrauDeObesidade().getValorAsFloat()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			), new Dataset(
				"Mínimo",
				pessoa.getMedicoes().stream().map(m -> m.getGrauDeObesidade().getMinimoAsFloat()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			), new Dataset(
				"Máximo",
				pessoa.getMedicoes().stream().map(m -> m.getGrauDeObesidade().getMaximoAsFloat()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_BAD
			))
		)));

		// TODO Descobrir e adicionar máximo, mínimo e/ou ideal.
		result.setGraficoSmi(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"SMI (kg/m²)",
				pessoa.getMedicoes().stream().map(m -> m.getSmi()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			))
		)));

		result.setGraficoIngestaoCaloricaRecomendada(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Ingestão calórica recomendada (kcal)",
				pessoa.getMedicoes().stream().map(m -> m.getIngestaoCaloricaRecomendadaAsFloat()).collect(Collectors.toList()),
				tension,
				DATASET_COLOR_MEASURED
			))
		)));

		Collections.reverse(result.getPessoa().getMedicoes()); // Coloca em ordem inversa

		return result;
	}

	// Medição

	public MedicaoEditar medicaoAlterar(String idPessoa, String idMedicao) {
		Pessoa pessoa = inBodyService.pessoaBuscar(idPessoa);
		if (pessoa == null) {
			throw new ControllerException("Pessoa não encontrada.");
		}
		Medicao medicao = pessoa.getMedicoes().stream().filter(m -> m.getId().equals(idMedicao)).findFirst().orElse(null);
		if (medicao == null) {
			throw new ControllerException("Medição não encontrada.");
		}

		return medicaoAlterar(pessoa, medicao);
	}

	public MedicaoEditar medicaoAlterar(Pessoa pessoa, Medicao medicao) {
		return new MedicaoEditar(pessoa, medicao);
	}

	public void medicaoAlterarOk(MedicaoEditar medicaoAlterar) throws ValidationException {
		try {
			inBodyService.medicaoAlterar(medicaoAlterar.getPessoa(), medicaoAlterar.getMedicao());
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
	}

	public MedicaoEditar medicaoExcluir(String idPessoa, String idMedicao) {
		Pessoa pessoa = inBodyService.pessoaBuscar(idPessoa);
		if (pessoa == null) {
			throw new ControllerException("Pessoa não encontrada.");
		}
		Medicao medicao = pessoa.getMedicoes().stream().filter(m -> m.getId().equals(idMedicao)).findFirst().orElse(null);
		if (medicao == null) {
			throw new ControllerException("Medição não encontrada.");
		}

		return medicaoAlterar(pessoa, medicao);
	}

	public void medicaoExcluirOk(MedicaoEditar medicaoExcluir) {
		try {
			inBodyService.medicaoExcluir(medicaoExcluir.getPessoa(), medicaoExcluir.getMedicao());
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
	}

	public MedicaoEditar medicaoIncluir(String idPessoa) {
		Medicao medicao = new Medicao();
		medicao.setDataHora(LocalDateTime.now());
		// TODO Repetir dados da última medição, se houver

		return medicaoIncluir(idPessoa, medicao);
	}

	public MedicaoEditar medicaoIncluir(String idPessoa, Medicao medicao) {
		return new MedicaoEditar(inBodyService.pessoaBuscar(idPessoa), medicao);
	}

	public void medicaoIncluirOk(MedicaoEditar medicaoIncluir) throws ValidationException {
		try {
			inBodyService.medicaoIncluir(medicaoIncluir.getPessoa(), medicaoIncluir.getMedicao());
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
	}
}
