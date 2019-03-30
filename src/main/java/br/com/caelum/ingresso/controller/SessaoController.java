package br.com.caelum.ingresso.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.form.SessaoForm;

@Controller
public class SessaoController {
	
	@Autowired
	private FilmeDao filmeDao;
	
	@Autowired
	private SalaDao salaDao;
	
	@Autowired
	private SessaoDao sessaoDao;
	
	@GetMapping("/admin/sessao")
	public ModelAndView form(@RequestParam("salaId") Integer salaId, SessaoForm form) {
		form.setSalaId(salaId);
		
		ModelAndView mdv = new ModelAndView("sessao/sessao");
		
		mdv.addObject("filmes", filmeDao.findAll());
		mdv.addObject("sala", salaDao.findOne(salaId));
		mdv.addObject("form", form);
		
		return mdv;
	}
	
	@Transactional
	@PostMapping("/admin/sessao")
	public ModelAndView insere(@Valid SessaoForm form, BindingResult result) {
		if (result.hasErrors()) {
			return form(form.getSalaId(), form);
		}
		
		Sessao sessao = form.toSessao(salaDao, filmeDao);
		
		sessaoDao.save(sessao);
		
		return new ModelAndView("redirect:/admin/sala/ " + form.getSalaId() + "/sessoes");
	}
	

}
