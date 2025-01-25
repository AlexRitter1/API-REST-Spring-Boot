package com.example.springboot.repositories;

import com.example.springboot.models.ProductsModel;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductsModel, UUID> {
    Optional<ProductsModel> findById(java.util.UUID id);
}