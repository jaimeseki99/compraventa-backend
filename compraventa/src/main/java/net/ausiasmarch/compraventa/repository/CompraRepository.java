package net.ausiasmarch.compraventa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.compraventa.entity.CompraEntity;

public interface CompraRepository extends JpaRepository<CompraEntity, Long>{


    @Modifying
    @Query(value = "ALTER TABLE compra AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}

