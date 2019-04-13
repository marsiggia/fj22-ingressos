package br.com.caelum.ingresso.model.descontos;

import java.math.BigDecimal;

public interface Desconto {
	
	String getDescricao();
	
	BigDecimal aplicarDescontoSobre(BigDecimal precoOriginal);

}
