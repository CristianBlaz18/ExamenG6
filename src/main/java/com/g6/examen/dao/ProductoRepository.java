package com.g6.examen.dao;

import com.g6.examen.entity.ClienteEntity;
import com.g6.examen.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<ProductoEntity,Long> {
}
