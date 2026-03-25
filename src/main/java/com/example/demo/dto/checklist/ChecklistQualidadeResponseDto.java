package com.example.demo.dto.checklist;

import com.example.demo.enums.StatusChecklist;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ChecklistQualidadeResponseDto {
    private Long id;
    private String descricao;
    private String criterio;
    private StatusChecklist status;
    private LocalDate dataVerificacao;
    private String observacoes;
    private Long projetoId;
    private String projetoNome;
    private Long atividadeId;
    private String atividadeTitulo;
}
