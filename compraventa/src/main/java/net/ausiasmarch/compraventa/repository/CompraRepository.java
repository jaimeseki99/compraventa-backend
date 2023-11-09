package net.ausiasmarch.compraventa.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.compraventa.entity.CompraEntity;

public interface CompraRepository extends JpaRepository<CompraEntity, Long>{

    Page<CompraEntity> findByUsuarioId(Long id_usuario, Pageable pageable);

    Page<CompraEntity> findByUsuarioIdAndProductoId(Long id_usuario, Long id_producto, Pageable pageable);

    Page<CompraEntity> findByProductoId(Long id_producto, Pageable pageable);
    
    @Modifying
    @Query(value = "ALTER TABLE compra AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}

