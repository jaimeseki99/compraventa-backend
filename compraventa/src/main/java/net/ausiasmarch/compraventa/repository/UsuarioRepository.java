package net.ausiasmarch.compraventa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.compraventa.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{

    Optional<UsuarioEntity> findByUsername(String username);

    Optional<UsuarioEntity> findByUsernameAndContrasenya(String username, String contrasenya);

    @Modifying
    @Query(value = "ALTER TABLE usuario AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
} 
