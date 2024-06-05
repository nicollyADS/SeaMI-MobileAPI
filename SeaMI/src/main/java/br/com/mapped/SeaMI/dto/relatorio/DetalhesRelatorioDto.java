package br.com.mapped.SeaMI.dto.relatorio;

import br.com.mapped.SeaMI.model.Relatorio;

import java.time.LocalDateTime;

public record DetalhesRelatorioDto(Long id, String nome, String descricao, String dataCriacao) {

    public DetalhesRelatorioDto(Relatorio relatorio) {
        this(relatorio.getId(), relatorio.getNome(), relatorio.getDescricao(), relatorio.getDataCriacao() );
    }

}