package com.example.demo.dto.raci;

import com.example.demo.enums.PapelRaci;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaciAssignacaoRequestDto {

    @NotNull
    private Long atividadeId;

    @NotNull
    private Long participanteId;

    @NotNull
    private PapelRaci papelRaci;
}
