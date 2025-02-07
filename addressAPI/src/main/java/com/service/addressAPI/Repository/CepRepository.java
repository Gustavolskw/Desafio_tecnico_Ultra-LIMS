package com.service.addressAPI.Repository;

import com.service.addressAPI.Domain.Model.Cep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CepRepository extends JpaRepository<Cep, Long> {

    @Query(nativeQuery = true, value = """
     SELECT * FROM API_CEP.cep c 
     WHERE 
     (:cep IS NULL OR c.cep LIKE :cep)
     ORDER BY 
     CASE WHEN :sortField IS NOT NULL AND :sortDirection = 'asc' AND :sortField = 'localidade' THEN c.localidade END ASC,
     CASE WHEN :sortField IS NOT NULL AND :sortDirection = 'desc' AND :sortField = 'localidade' THEN c.localidade END DESC,
     CASE WHEN :sortField IS NOT NULL AND :sortDirection = 'asc' AND :sortField = 'bairro' THEN c.bairro END ASC,
     CASE WHEN :sortField IS NOT NULL AND :sortDirection = 'desc' AND :sortField = 'bairro' THEN c.bairro END DESC,
     CASE WHEN :sortField IS NOT NULL AND :sortDirection = 'asc' AND :sortField = 'estado' THEN c.estado END ASC,
     CASE WHEN :sortField IS NOT NULL AND :sortDirection = 'desc' AND :sortField = 'estado' THEN c.estado END DESC,
     c.id 
""", countQuery = """
     SELECT count(*) FROM API_CEP.cep c
     WHERE 
     (:cep IS NULL OR c.cep LIKE :cep)
""")
    Page<Cep> findByAllByParam(
            @Param("cep") String cep,
            @Param("sortField") String sortField,
            @Param("sortDirection") String sortDirection,
            Pageable pageable
    );


    boolean existsByCep(String cep);
}
