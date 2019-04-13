package br.com.caelum.ingresso.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Component
public class Carrinho {

	private List<Ingresso> ingressos = new ArrayList<>();
	
	public void add(Ingresso ingresso) {
		ingressos.add(ingresso);
	}
	
	public List<Ingresso> getIngressos() {
		return ingressos;
	}
	
	public boolean isSelecionado(Lugar lugar) {
		return ingressos.stream().map(Ingresso::getLugar).anyMatch(l -> l.equals(lugar));
	}
	
}
