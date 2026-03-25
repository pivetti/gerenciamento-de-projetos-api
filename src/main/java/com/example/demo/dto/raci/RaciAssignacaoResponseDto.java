package com.example.demo.dto.raci;

import com.example.demo.enums.PapelRaci;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RaciAssignacaoResponseDto {
    private Long id;
    private Long atividadeId;
    private String atividadeTitulo;
    private Long participanteId;
    private String participanteNome;
    private PapelRaci papelRaci;
}
