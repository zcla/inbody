package zcla71.inbody.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import zcla71.inbody.controller.InBodyController;
import zcla71.inbody.controller.ValidationException;
import zcla71.inbody.view.dto.PessoaIncluirOk;

@Controller
public class InBodyViewController {
	@Autowired
	private InBodyController inBodyController;

	@GetMapping(value = {"/", "/pessoa"})
	public String index(Model model) {
        model.addAttribute("data", inBodyController.pessoaListar());
        return "/index";
	}

	@GetMapping("/pessoa/incluir")
	public String pessoaIncluir(Model model) {
		if (model.getAttribute("data") == null) { // Vem preenchido quando dá erro de validação
			model.addAttribute("data", inBodyController.pessoaIncluir());
		}
		return "/pessoa/incluir";
	}

    @PostMapping("/pessoa/incluir_ok")
    public ModelAndView pessoaIncluirOk(Model model, @ModelAttribute PessoaIncluirOk pessoaIncluir) {
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
}
