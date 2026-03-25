package com.example.demo.entity;

import com.example.demo.enums.PapelRaci;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "raci_assignacoes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaciAssignacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "atividade_id", nullable = false)
    private Atividade atividade;

    @ManyToOne
    @JoinColumn(name = "participante_id", nullable = false)
    private Participante participante;

    @Enumerated(EnumType.STRING)
    private PapelRaci papelRaci;
}
