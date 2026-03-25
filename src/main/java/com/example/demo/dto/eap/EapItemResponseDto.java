package com.example.demo.dto.eap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EapItemResponseDto {
    private Long id;
    private String nome;
    private String descricao;
    private String codigo;
    private Integer nivel;
    private Long projetoId;
    private String projetoNome;
    private Long itemPaiId;
    private String itemPaiNome;
}
