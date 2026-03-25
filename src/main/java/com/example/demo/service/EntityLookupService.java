package com.example.demo.service;

import com.example.demo.entity.Atividade;
import com.example.demo.entity.ChecklistQualidade;
import com.example.demo.entity.Custo;
import com.example.demo.entity.EapItem;
import com.example.demo.entity.Participante;
import com.example.demo.entity.Projeto;
import com.example.demo.entity.RaciAssignacao;
import com.example.demo.entity.Recurso;
import com.example.demo.entity.Risco;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AtividadeRepository;
import com.example.demo.repository.ChecklistQualidadeRepository;
import com.example.demo.repository.CustoRepository;
import com.example.demo.repository.EapItemRepository;
import com.example.demo.repository.ParticipanteRepository;
import com.example.demo.repository.ProjetoRepository;
import com.example.demo.repository.RaciAssignacaoRepository;
import com.example.demo.repository.RecursoRepository;
import com.example.demo.repository.RiscoRepository;
import com.example.demo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntityLookupService {

    private final UsuarioRepository usuarioRepository;
    private final ProjetoRepository projetoRepository;
    private final ParticipanteRepository participanteRepository;
    private final EapItemRepository eapItemRepository;
    private final AtividadeRepository atividadeRepository;
    private final RiscoRepository riscoRepository;
    private final RecursoRepository recursoRepository;
    private final CustoRepository custoRepository;
    private final RaciAssignacaoRepository raciAssignacaoRepository;
    private final ChecklistQualidadeRepository checklistQualidadeRepository;

    public Usuario getUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado com id " + id));
    }

    public Projeto getProjeto(Long id) {
        return projetoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto nao encontrado com id " + id));
    }

    public Participante getParticipante(Long id) {
        return participanteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participante nao encontrado com id " + id));
    }

    public EapItem getEapItem(Long id) {
        return eapItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EAP item nao encontrado com id " + id));
    }

    public Atividade getAtividade(Long id) {
        return atividadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atividade nao encontrada com id " + id));
    }

    public Risco getRisco(Long id) {
        return riscoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Risco nao encontrado com id " + id));
    }

    public Recurso getRecurso(Long id) {
        return recursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso nao encontrado com id " + id));
    }

    public Custo getCusto(Long id) {
        return custoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Custo nao encontrado com id " + id));
    }

    public RaciAssignacao getRaciAssignacao(Long id) {
        return raciAssignacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atribuicao RACI nao encontrada com id " + id));
    }

    public ChecklistQualidade getChecklistQualidade(Long id) {
        return checklistQualidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist de qualidade nao encontrado com id " + id));
    }
}
