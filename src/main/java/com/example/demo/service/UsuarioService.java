package com.example.demo.service;

import com.example.demo.dto.usuario.UsuarioRequestDto;
import com.example.demo.dto.usuario.UsuarioResponseDto;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.UsuarioRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EntityLookupService lookupService;

    public List<UsuarioResponseDto> listarTodos() {
        return usuarioRepository.findAll().stream().map(this::toResponse).toList();
    }

    public UsuarioResponseDto buscarPorId(Long id) {
        return toResponse(lookupService.getUsuario(id));
    }

    public UsuarioResponseDto criar(UsuarioRequestDto request) {
        Usuario usuario = Usuario.builder().build();
        preencherCampos(usuario, request);
        return toResponse(usuarioRepository.save(usuario));
    }

    public UsuarioResponseDto atualizar(Long id, UsuarioRequestDto request) {
        Usuario usuario = lookupService.getUsuario(id);
        preencherCampos(usuario, request);
        return toResponse(usuarioRepository.save(usuario));
    }

    public void deletar(Long id) {
        usuarioRepository.delete(lookupService.getUsuario(id));
    }

    private void preencherCampos(Usuario usuario, UsuarioRequestDto request) {
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(request.getSenha());
        usuario.setTelefone(request.getTelefone());
        usuario.setPerfil(request.getPerfil());
    }

    private UsuarioResponseDto toResponse(Usuario usuario) {
        return UsuarioResponseDto.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .telefone(usuario.getTelefone())
                .perfil(usuario.getPerfil())
                .build();
    }
}
