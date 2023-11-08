package net.ausiasmarch.compraventa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.compraventa.entity.ProductoEntity;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {

    @Modifying
    @Query(value = "ALTER TABLE producto AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

    
} 