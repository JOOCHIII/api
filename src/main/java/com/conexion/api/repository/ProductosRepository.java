package com.conexion.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conexion.api.model.Productos;

public interface ProductosRepository extends JpaRepository<Productos, Integer> {}