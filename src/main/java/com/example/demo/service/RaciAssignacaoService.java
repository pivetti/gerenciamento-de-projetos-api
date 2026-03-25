package com.example.demo.service;

import com.example.demo.dto.raci.RaciAssignacaoRequestDto;
import com.example.demo.dto.raci.RaciAssignacaoResponseDto;
import com.example.demo.entity.RaciAssignacao;
import com.example.demo.enums.PapelRaci;
import com.example.demo.repository.RaciAssignacaoRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RaciAssignacaoService {

    private final RaciAssignacaoRepository raciAssignacaoRepository;
    private final EntityLookupService lookupService;
    private final PatchFieldService patchFieldService;

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

    public RaciAssignacaoResponseDto atualizarParcialmente(Long id, Map<String, Object> updates) {
        RaciAssignacao raciAssignacao = lookupService.getRaciAssignacao(id);
        aplicarPatch(raciAssignacao, updates);
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

    private void aplicarPatch(RaciAssignacao raciAssignacao, Map<String, Object> updates) {
        if (updates.containsKey("atividadeId")) {
            raciAssignacao.setAtividade(lookupService.getAtividade(patchFieldService.getLong(updates, "atividadeId")));
        }
        if (updates.containsKey("participanteId")) {
            raciAssignacao
                    .setParticipante(lookupService.getParticipante(patchFieldService.getLong(updates, "participanteId")));
        }
        if (updates.containsKey("papelRaci")) {
            raciAssignacao.setPapelRaci(patchFieldService.getEnum(updates, "papelRaci", PapelRaci.class));
        }
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
