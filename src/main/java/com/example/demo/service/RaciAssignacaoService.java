package com.example.demo.service;

import com.example.demo.dto.raci.RaciAssignacaoRequestDto;
import com.example.demo.dto.raci.RaciAssignacaoResponseDto;
import com.example.demo.entity.RaciAssignacao;
import com.example.demo.repository.RaciAssignacaoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RaciAssignacaoService {

    private final RaciAssignacaoRepository raciAssignacaoRepository;
    private final EntityLookupService lookupService;

    public List<RaciAssignacaoResponseDto> listarTodos() {
        return raciAssignacaoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public RaciAssignacaoResponseDto buscarPorId(Long id) {
        return toResponse(lookupService.getRaciAssignacao(id));
    }

    public RaciAssignacaoResponseDto criar(RaciAssignacaoRequestDto request) {
        RaciAssignacao raciAssignacao = RaciAssignacao.builder().build();
        preencherCampos(raciAssignacao, request);
        return toResponse(raciAssignacaoRepository.save(raciAssignacao));
    }

    public RaciAssignacaoResponseDto atualizar(Long id, RaciAssignacaoRequestDto request) {
        RaciAssignacao raciAssignacao = lookupService.getRaciAssignacao(id);
        preencherCampos(raciAssignacao, request);
        return toResponse(raciAssignacaoRepository.save(raciAssignacao));
    }

    public void deletar(Long id) {
        raciAssignacaoRepository.delete(lookupService.getRaciAssignacao(id));
    }

    private void preencherCampos(RaciAssignacao raciAssignacao, RaciAssignacaoRequestDto request) {
        raciAssignacao.setAtividade(lookupService.getAtividade(request.getAtividadeId()));
        raciAssignacao.setParticipante(lookupService.getParticipante(request.getParticipanteId()));
        raciAssignacao.setPapelRaci(request.getPapelRaci());
    }

    private RaciAssignacaoResponseDto toResponse(RaciAssignacao raciAssignacao) {
        return RaciAssignacaoResponseDto.builder()
                .id(raciAssignacao.getId())
                .atividadeId(raciAssignacao.getAtividade().getId())
                .atividadeTitulo(raciAssignacao.getAtividade().getTitulo())
                .participanteId(raciAssignacao.getParticipante().getId())
                .participanteNome(raciAssignacao.getParticipante().getUsuario().getNome())
                .papelRaci(raciAssignacao.getPapelRaci())
                .build();
    }
}
