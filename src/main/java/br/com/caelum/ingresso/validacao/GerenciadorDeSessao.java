package br.com.caelum.ingresso.validacao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessao {
	
	private List<Sessao> sessoesDaSala;

	public GerenciadorDeSessao(List<Sessao> sessoesDaSala) {
		this.sessoesDaSala = sessoesDaSala;
	}
	
	private boolean horarioIsConflitante(Sessao sessaoExistente, Sessao novaSessao) {
		LocalDate hoje = LocalDate.now();
		
		LocalDateTime horarioSessaoExistente = sessaoExistente.getHorario().atDate(hoje);
		LocalDateTime terminoSessaoExistente = sessaoExistente.getHorarioTermino().atDate(hoje);
		
		LocalDateTime horarioNovaSessao = novaSessao.getHorario().atDate(hoje);
		LocalDateTime terminoNovaSessao = novaSessao.getHorarioTermino().atDate(hoje);
		
		boolean terminaAntes = terminoNovaSessao.isBefore(horarioSessaoExistente);
		boolean comecaDepois = terminoSessaoExistente.isBefore(horarioNovaSessao);
		
		return terminaAntes || comecaDepois;
	}
	
	public boolean cabe(Sessao novaSessao) {
		return sessoesDaSala
					.stream()
					.noneMatch(sessaoExistente -> horarioIsConflitante(sessaoExistente, novaSessao));
		
	}

}
