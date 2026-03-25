package com.example.demo.repository;

import com.example.demo.entity.Participante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
}
