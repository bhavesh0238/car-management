package com.bhavesh.project1.repository;

import com.bhavesh.project1.model.product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface productrepo extends JpaRepository<product,Integer>{
    @Query("select p from product p where "+
            "LOWER(p.name) like LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description) like LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.brand) like LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.category) like LOWER(CONCAT('%', :keyword, '%'))")
    List<product> searchproducts(String keyword);
}
