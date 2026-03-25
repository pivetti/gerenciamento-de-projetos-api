package com.example.demo.service;

import com.example.demo.dto.custo.CustoRequestDto;
import com.example.demo.dto.custo.CustoResponseDto;
import com.example.demo.entity.Custo;
import com.example.demo.repository.CustoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustoService {

    private final CustoRepository custoRepository;
    private final EntityLookupService lookupService;

    public List<CustoResponseDto> listarTodos() {
        return custoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public CustoResponseDto buscarPorId(Long id) {
        return toResponse(lookupService.getCusto(id));
    }

    public CustoResponseDto criar(CustoRequestDto request) {
        Custo custo = Custo.builder().build();
        preencherCampos(custo, request);
        return toResponse(custoRepository.save(custo));
    }

    public CustoResponseDto atualizar(Long id, CustoRequestDto request) {
        Custo custo = lookupService.getCusto(id);
        preencherCampos(custo, request);
        return toResponse(custoRepository.save(custo));
    }

    public void deletar(Long id) {
        custoRepository.delete(lookupService.getCusto(id));
    }

    private void preencherCampos(Custo custo, CustoRequestDto request) {
        custo.setDescricao(request.getDescricao());
        custo.setTipo(request.getTipo());
        custo.setValorPrevisto(request.getValorPrevisto());
        custo.setValorReal(request.getValorReal());
        custo.setDataLancamento(request.getDataLancamento());
        custo.setProjeto(lookupService.getProjeto(request.getProjetoId()));
        custo.setAtividade(request.getAtividadeId() != null ? lookupService.getAtividade(request.getAtividadeId()) : null);
        custo.setRecurso(request.getRecursoId() != null ? lookupService.getRecurso(request.getRecursoId()) : null);
    }

    private CustoResponseDto toResponse(Custo custo) {
        return CustoResponseDto.builder()
                .id(custo.getId())
                .descricao(custo.getDescricao())
                .tipo(custo.getTipo())
                .valorPrevisto(custo.getValorPrevisto())
                .valorReal(custo.getValorReal())
                .dataLancamento(custo.getDataLancamento())
                .projetoId(custo.getProjeto().getId())
                .projetoNome(custo.getProjeto().getNome())
                .atividadeId(custo.getAtividade() != null ? custo.getAtividade().getId() : null)
                .atividadeTitulo(custo.getAtividade() != null ? custo.getAtividade().getTitulo() : null)
                .recursoId(custo.getRecurso() != null ? custo.getRecurso().getId() : null)
                .recursoNome(custo.getRecurso() != null ? custo.getRecurso().getNome() : null)
                .build();
    }
}
