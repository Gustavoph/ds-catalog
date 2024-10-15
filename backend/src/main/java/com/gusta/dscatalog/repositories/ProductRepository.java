package com.gusta.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gusta.dscatalog.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
