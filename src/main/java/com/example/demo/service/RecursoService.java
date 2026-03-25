package com.example.demo.service;

import com.example.demo.dto.recurso.RecursoRequestDto;
import com.example.demo.dto.recurso.RecursoResponseDto;
import com.example.demo.entity.Recurso;
import com.example.demo.repository.RecursoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecursoService {

    private final RecursoRepository recursoRepository;
    private final EntityLookupService lookupService;

    public List<RecursoResponseDto> listarTodos() {
        return recursoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public RecursoResponseDto buscarPorId(Long id) {
        return toResponse(lookupService.getRecurso(id));
    }

    public RecursoResponseDto criar(RecursoRequestDto request) {
        Recurso recurso = Recurso.builder().build();
        preencherCampos(recurso, request);
        return toResponse(recursoRepository.save(recurso));
    }

    public RecursoResponseDto atualizar(Long id, RecursoRequestDto request) {
        Recurso recurso = lookupService.getRecurso(id);
        preencherCampos(recurso, request);
        return toResponse(recursoRepository.save(recurso));
    }

    public void deletar(Long id) {
        recursoRepository.delete(lookupService.getRecurso(id));
    }

    private void preencherCampos(Recurso recurso, RecursoRequestDto request) {
        recurso.setNome(request.getNome());
        recurso.setTipo(request.getTipo());
        recurso.setDescricao(request.getDescricao());
        recurso.setQuantidade(request.getQuantidade());
        recurso.setCustoUnitario(request.getCustoUnitario());
        recurso.setProjeto(lookupService.getProjeto(request.getProjetoId()));
    }

    private RecursoResponseDto toResponse(Recurso recurso) {
        return RecursoResponseDto.builder()
                .id(recurso.getId())
                .nome(recurso.getNome())
                .tipo(recurso.getTipo())
                .descricao(recurso.getDescricao())
                .quantidade(recurso.getQuantidade())
                .custoUnitario(recurso.getCustoUnitario())
                .projetoId(recurso.getProjeto().getId())
                .projetoNome(recurso.getProjeto().getNome())
                .build();
    }
}
