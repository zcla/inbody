package zcla71.inbody.view.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import zcla71.inbody.controller.InBodyController;
import zcla71.inbody.model.service.ValidationException;
import zcla71.inbody.view.dto.MedicaoEditar;
import zcla71.inbody.view.dto.PessoaEditar;

@Controller
public class InBodyViewController {
	@Autowired
	private InBodyController inBodyController;

	// index

	@GetMapping(value = {"/", "/pessoa"})
	public String index(Model model) {
		model.addAttribute("data", inBodyController.pessoaListar());
		return "/index";
	}

	// Pessoa

	@GetMapping("/pessoa/alterar")
	public String pessoaAlterar(@RequestParam(name="id", required = true) String id, Model model) {
		model.addAttribute("data", inBodyController.pessoaAlterar(id));
		return "/pessoa/alterar";
	}

	@PostMapping("/pessoa/alterar_ok")
	public ModelAndView pessoaAlterarOk(Model model, @ModelAttribute PessoaEditar pessoaAlterar) {
		try {
			inBodyController.pessoaAlterarOk(pessoaAlterar);
		} catch (ValidationException e) {
			ModelAndView mav = new ModelAndView("/pessoa/alterar");
			mav.addObject("data", inBodyController.pessoaAlterar(pessoaAlterar.getPessoa()));
			mav.addObject("validation", e.getValidations());
			return mav;
		}
		return new ModelAndView("redirect:/pessoa");
	}

	@GetMapping("/pessoa/excluir")
	public String pessoaExcluir(@RequestParam(name="id", required = true) String id, Model model) {
		model.addAttribute("data", inBodyController.pessoaExcluir(id));
		return "/pessoa/excluir";
	}

	@PostMapping("/pessoa/excluir_ok")
	public ModelAndView pessoaExcluirOk(Model model, @ModelAttribute PessoaEditar pessoaExcluir) {
		inBodyController.pessoaExcluirOk(pessoaExcluir);
		return new ModelAndView("redirect:/pessoa");
	}

	@GetMapping("/pessoa/incluir")
	public String pessoaIncluir(Model model) {
		if (model.getAttribute("data") == null) { // Vem preenchido quando dá erro de validação
			model.addAttribute("data", inBodyController.pessoaIncluir());
		}
		return "/pessoa/incluir";
	}

	@PostMapping("/pessoa/incluir_ok")
	public ModelAndView pessoaIncluirOk(Model model, @ModelAttribute PessoaEditar pessoaIncluir) {
		try {
			inBodyController.pessoaIncluirOk(pessoaIncluir);
		} catch (ValidationException e) {
			ModelAndView mav = new ModelAndView("/pessoa/incluir");
			mav.addObject("data", inBodyController.pessoaIncluir(pessoaIncluir.getPessoa()));
			mav.addObject("validation", e.getValidations());
			return mav;
		}
		return new ModelAndView("redirect:/pessoa");
	}

	@GetMapping("/pessoa/mostrar")
	public String pessoaMostrar(@RequestParam(name="id", required = true) String id, Model model) {
		model.addAttribute("data", inBodyController.pessoaMostrar(id));
		return "/pessoa/mostrar";
	}

	// Medição

	@GetMapping("/medicao/alterar")
	public String medicaoAlterar(@RequestParam(name="idPessoa", required = true) String idPessoa, @RequestParam(name="dataHora", required = true) LocalDateTime dataHora, Model model) {
		model.addAttribute("data", inBodyController.medicaoAlterar(idPessoa, dataHora));
		return "/medicao/alterar";
	}

	@PostMapping("/medicao/alterar_ok")
	public ModelAndView medicaoAlterarOk(Model model, @ModelAttribute MedicaoEditar medicaoAlterar) {
		try {
			inBodyController.medicaoAlterarOk(medicaoAlterar);
		} catch (ValidationException e) {
			ModelAndView mav = new ModelAndView("/medicao/alterar");
			mav.addObject("idPessoa", medicaoAlterar.getPessoa().getId());
			mav.addObject("data", inBodyController.medicaoAlterar(medicaoAlterar.getPessoa().getId(), medicaoAlterar.getMedicao()));
			mav.addObject("validation", e.getValidations());
			return mav;
		}
		return new ModelAndView("redirect:/pessoa/mostrar?id=" + medicaoAlterar.getPessoa().getId());
	}

	@GetMapping("/medicao/incluir")
	public String medicaoIncluir(@RequestParam(name="idPessoa", required = true) String idPessoa, Model model) {
		if (model.getAttribute("data") == null) { // Vem preenchido quando dá erro de validação
			model.addAttribute("data", inBodyController.medicaoIncluir(idPessoa));
		}
		return "/medicao/incluir";
	}

	@PostMapping("/medicao/incluir_ok")
	public ModelAndView medicaoIncluirOk(Model model, @ModelAttribute MedicaoEditar medicaoIncluir) {
		try {
			inBodyController.medicaoIncluirOk(medicaoIncluir);
		} catch (ValidationException e) {
			ModelAndView mav = new ModelAndView("/medicao/incluir");
			mav.addObject("idPessoa", medicaoIncluir.getPessoa().getId());
			mav.addObject("data", inBodyController.medicaoIncluir(medicaoIncluir.getPessoa().getId(), medicaoIncluir.getMedicao()));
			mav.addObject("validation", e.getValidations());
			return mav;
		}
		return new ModelAndView("redirect:/pessoa/mostrar?id=" + medicaoIncluir.getPessoa().getId());
	}
}
