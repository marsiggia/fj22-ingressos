package br.com.caelum.ingresso.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Carrinho;
import br.com.caelum.ingresso.model.ImagemCapa;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.TipoDeIngresso;
import br.com.caelum.ingresso.model.form.SessaoForm;
import br.com.caelum.ingresso.rest.OmdbClient;
import br.com.caelum.ingresso.validacao.GerenciadorDeSessao;

@Controller
public class SessaoController {
	
	@Autowired
	private FilmeDao filmeDao;
	
	@Autowired
	private SalaDao salaDao;
	
	@Autowired
	private SessaoDao sessaoDao;
	
	@Autowired
	private OmdbClient client;
	
	@Autowired
	private Carrinho carrinho;
	
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
		
		List<Sessao> sessoesDaSala = sessaoDao.buscaSessaoDaSala(sessao.getSala());
		
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoesDaSala);
		
		if (gerenciador.cabe(sessao)) {
			sessaoDao.save(sessao);
			return new ModelAndView("redirect:/admin/sala/" + form.getSalaId() + "/sessoes");
		}
		
		return form(form.getSalaId(), form);
	}
	
	@GetMapping("/sessao/{id}/lugares")
	public ModelAndView lugaresNaSessao(@PathVariable("id") Integer idSessao) {
		ModelAndView mdv = new ModelAndView("sessao/lugares");
		
		Sessao sessao = sessaoDao.findOne(idSessao);
		mdv.addObject("sessao", sessao);
		
		ImagemCapa imagemCapa = client.request(sessao.getFilme(), ImagemCapa.class).orElse(new ImagemCapa());
		mdv.addObject("imagemCapa", imagemCapa);
		
		mdv.addObject("tiposDeIngressos", TipoDeIngresso.values());
		mdv.addObject("carrinho", carrinho);
		
		return mdv;
	}
	
}
