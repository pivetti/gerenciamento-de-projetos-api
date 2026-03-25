package com.example.demo.dto.checklist;

import com.example.demo.enums.StatusChecklist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
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
public class ChecklistQualidadeRequestDto {

    @NotBlank
    @Size(max = 255)
    private String descricao;

    @NotBlank
    @Size(max = 255)
    private String criterio;

    @NotNull
    private StatusChecklist status;

    private LocalDate dataVerificacao;

    @Size(max = 1000)
    private String observacoes;

    @NotNull
    private Long projetoId;

    private Long atividadeId;
}
