package com.example.demo.dto.eap;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
public class EapItemRequestDto {

    @NotBlank
    @Size(max = 150)
    private String nome;

    @Size(max = 1000)
    private String descricao;

    @NotBlank
    @Size(max = 50)
    private String codigo;

    @NotNull
    @Positive
    private Integer nivel;

    @NotNull
    private Long projetoId;

    private Long itemPaiId;
}
