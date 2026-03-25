package com.example.demo.service;

import com.example.demo.dto.participante.ParticipanteRequestDto;
import com.example.demo.dto.participante.ParticipanteResponseDto;
import com.example.demo.entity.Participante;
import com.example.demo.repository.ParticipanteRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipanteService {

    private final ParticipanteRepository participanteRepository;
    private final EntityLookupService lookupService;

    public List<ParticipanteResponseDto> listarTodos() {
        return participanteRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ParticipanteResponseDto buscarPorId(Long id) {
        return toResponse(lookupService.getParticipante(id));
    }

    public ParticipanteResponseDto criar(ParticipanteRequestDto request) {
        Participante participante = Participante.builder().build();
        preencherCampos(participante, request);
        return toResponse(participanteRepository.save(participante));
    }

    public ParticipanteResponseDto atualizar(Long id, ParticipanteRequestDto request) {
        Participante participante = lookupService.getParticipante(id);
        preencherCampos(participante, request);
        return toResponse(participanteRepository.save(participante));
    }

    public void deletar(Long id) {
        participanteRepository.delete(lookupService.getParticipante(id));
    }

    private void preencherCampos(Participante participante, ParticipanteRequestDto request) {
        participante.setUsuario(lookupService.getUsuario(request.getUsuarioId()));
        participante.setProjeto(lookupService.getProjeto(request.getProjetoId()));
        participante.setFuncaoNoProjeto(request.getFuncaoNoProjeto());
        participante.setPapelAcesso(request.getPapelAcesso());
        participante.setAtivo(request.getAtivo());
    }

    private ParticipanteResponseDto toResponse(Participante participante) {
        return ParticipanteResponseDto.builder()
                .id(participante.getId())
                .usuarioId(participante.getUsuario().getId())
                .usuarioNome(participante.getUsuario().getNome())
                .projetoId(participante.getProjeto().getId())
                .projetoNome(participante.getProjeto().getNome())
                .funcaoNoProjeto(participante.getFuncaoNoProjeto())
                .papelAcesso(participante.getPapelAcesso())
                .ativo(participante.getAtivo())
                .build();
    }
}
