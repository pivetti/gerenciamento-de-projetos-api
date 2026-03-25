package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "eap_itens")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EapItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private String codigo;

    private Integer nivel;

    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;

    @ManyToOne
    @JoinColumn(name = "item_pai_id")
    private EapItem itemPai;

    @OneToMany(mappedBy = "itemPai")
    @Builder.Default
    private List<EapItem> subitens = new ArrayList<>();

    @OneToMany(mappedBy = "eapItem")
    @Builder.Default
    private List<Atividade> atividades = new ArrayList<>();
}
