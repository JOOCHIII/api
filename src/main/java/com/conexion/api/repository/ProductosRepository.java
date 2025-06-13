package com.conexion.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.conexion.api.model.Productos;

public interface ProductosRepository extends JpaRepository<Productos, Long> {
	long countByStock(int stock);
	@Query(value = "SELECT DISTINCT categoria FROM productos", nativeQuery = true)
	List<String> findDistinctCategorias();



}
