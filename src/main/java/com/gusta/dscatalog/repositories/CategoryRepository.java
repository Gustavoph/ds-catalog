package com.gusta.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gusta.dscatalog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
