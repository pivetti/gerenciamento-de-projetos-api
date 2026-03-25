package com.example.demo.controller;

import com.example.demo.dto.checklist.ChecklistQualidadeRequestDto;
import com.example.demo.dto.checklist.ChecklistQualidadeResponseDto;
import com.example.demo.service.ChecklistQualidadeService;
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
@RequestMapping("/checklists-qualidade")
@RequiredArgsConstructor
public class ChecklistQualidadeController {

    private final ChecklistQualidadeService checklistQualidadeService;

    @GetMapping
    public ResponseEntity<List<ChecklistQualidadeResponseDto>> listarTodos() {
        return ResponseEntity.ok(checklistQualidadeService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChecklistQualidadeResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(checklistQualidadeService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ChecklistQualidadeResponseDto> criar(
            @Valid @RequestBody ChecklistQualidadeRequestDto request) {
        ChecklistQualidadeResponseDto response = checklistQualidadeService.criar(request);
        return ResponseEntity.created(URI.create("/checklists-qualidade/" + response.getId())).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChecklistQualidadeResponseDto> atualizar(@PathVariable Long id,
            @Valid @RequestBody ChecklistQualidadeRequestDto request) {
        return ResponseEntity.ok(checklistQualidadeService.atualizar(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ChecklistQualidadeResponseDto> atualizarParcialmente(@PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(checklistQualidadeService.atualizarParcialmente(id, updates));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        checklistQualidadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
