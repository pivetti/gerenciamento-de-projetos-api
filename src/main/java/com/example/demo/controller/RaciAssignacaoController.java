package com.example.demo.controller;

import com.example.demo.dto.raci.RaciAssignacaoRequestDto;
import com.example.demo.dto.raci.RaciAssignacaoResponseDto;
import com.example.demo.service.RaciAssignacaoService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/raci")
@RequiredArgsConstructor
public class RaciAssignacaoController {

    private final RaciAssignacaoService raciAssignacaoService;

    @GetMapping
    public ResponseEntity<List<RaciAssignacaoResponseDto>> listarTodos() {
        return ResponseEntity.ok(raciAssignacaoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RaciAssignacaoResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(raciAssignacaoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<RaciAssignacaoResponseDto> criar(@Valid @RequestBody RaciAssignacaoRequestDto request) {
        RaciAssignacaoResponseDto response = raciAssignacaoService.criar(request);
        return ResponseEntity.created(URI.create("/raci/" + response.getId())).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RaciAssignacaoResponseDto> atualizar(@PathVariable Long id,
            @Valid @RequestBody RaciAssignacaoRequestDto request) {
        return ResponseEntity.ok(raciAssignacaoService.atualizar(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RaciAssignacaoResponseDto> atualizarParcialmente(@PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(raciAssignacaoService.atualizarParcialmente(id, updates));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        raciAssignacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
