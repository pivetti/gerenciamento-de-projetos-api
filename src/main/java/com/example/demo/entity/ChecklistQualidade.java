package com.example.demo.entity;

import com.example.demo.enums.StatusChecklist;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "checklists_qualidade")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChecklistQualidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private String criterio;

    @Enumerated(EnumType.STRING)
    private StatusChecklist status;

    private LocalDate dataVerificacao;

    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;

    @ManyToOne
    @JoinColumn(name = "atividade_id")
    private Atividade atividade;
}
