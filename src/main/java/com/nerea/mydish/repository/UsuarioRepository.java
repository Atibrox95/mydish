package com.nerea.mydish.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nerea.mydish.repository.entity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
	Optional<UsuarioEntity> findByCorreo(String correo);
	boolean existsByCorreo(String correo);
}
