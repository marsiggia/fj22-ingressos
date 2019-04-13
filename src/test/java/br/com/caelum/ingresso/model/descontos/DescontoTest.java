package br.com.caelum.ingresso.model.descontos;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Lugar;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.TipoDeIngresso;

public class DescontoTest {
	
	private Sala sala;
	
	private Filme filme;
	
	private Sessao sessao;
	
	Lugar lugar;
	
	@Before
	public void preparaCenario() {
		lugar = new Lugar("A", 1);
		sala = new Sala("Eldorado - IMax", new BigDecimal("50"));
		filme = new Filme("Rogue One", Duration.ofMinutes(120), "SCI-FI", new BigDecimal("50"));
		sessao = new Sessao(LocalTime.parse("10:00:00"), filme, sala);
	}
	
	@Test
	public void naoDeveConcederDecontoParaIngressoNormal() {
		Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.INTEIRO, lugar);
		BigDecimal precoEsperado = new BigDecimal("100.00");
		
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
	}
	
	@Test
	public void deveConcederDecontoDe30PorcentoParaIngressoDeClientesDeBancos() {
		Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.BANCO, lugar);
		BigDecimal precoEsperado = new BigDecimal("70.00");
		
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
	}
	
	@Test
	public void deveConcederDecontoDe50PorcentoParaIngressoDeEstudante() {
		Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.ESTUDANTE, lugar);
		BigDecimal precoEsperado = new BigDecimal("50.00");
		
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
	}

}
