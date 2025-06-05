package zcla71.inbody.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zcla71.chartjs.Configuration;
import zcla71.chartjs.Data;
import zcla71.chartjs.Dataset;
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
		Float tension = 0.1f;

		result.setGraficoPeso(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Peso (kg)",
				pessoa.getMedicoes().stream().map(m -> m.getPeso().getValor()).collect(Collectors.toList()),
				tension
			))
		)));

		result.setGraficoMassaMuscular(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Massa Muscular (kg)",
				pessoa.getMedicoes().stream().map(m -> m.getMassaMuscularEsqueletica().getValor()).collect(Collectors.toList()),
				tension
			))
		)));

		result.setGraficoGorduraCorporal(new Configuration("line", new Data(
			labels,
			Arrays.asList(new Dataset(
				"Gordura Corporal (%)",
				pessoa.getMedicoes().stream().map(m -> m.getPgc()).collect(Collectors.toList()),
				tension
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
