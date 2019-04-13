package br.com.caelum.ingresso.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Compra {

	@Id
	@GeneratedValue
	private Long id;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	private List<Ingresso> ingressos = new ArrayList<>();

	/**
	 * @deprecated frameworks only
	 */
	@Deprecated
	public Compra() {
	}
	
	public Compra(List<Ingresso> ingressos) {
		this.ingressos = ingressos;
	}
	
}
