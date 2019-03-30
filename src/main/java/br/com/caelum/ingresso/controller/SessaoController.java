package br.com.caelum.ingresso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;

@Controller
public class SessaoController {
	
	@Autowired
	private FilmeDao filmeDao;
	
	@Autowired
	private SalaDao salaDao;
	
	@GetMapping("admin/sessao/")
	public ModelAndView getSessoes(@RequestParam("salaId") Integer salaId) {
		ModelAndView mdv = new ModelAndView("sessao/sessao");
		
		mdv.addObject("filmes", filmeDao.findAll());
		mdv.addObject("sala", salaDao.findOne(salaId));
		
		return mdv;
	}

}
