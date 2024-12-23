package com.b2camp.simple_core_banking.repository;


import com.b2camp.simple_core_banking.entity.MCif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MCifRepository extends JpaRepository<MCif, String> {
    @Query("SELECT mu FROM MCif mu WHERE " +
            "(:customerName IS NULL OR :customerName = '' OR LOWER(mu.customerName) LIKE LOWER(CONCAT('%', :customerName, '%'))) " +
            "AND mu.isDeleted = false")
    List<MCif> findAllByCustomerNameAndIsDeletedFalse(@Param("customerName") String customerName);

    @Query(value = "select * from m_cif mc where is_deleted is false and cif_id = :cifId", nativeQuery = true)
    Optional<MCif> findByCifIdAndIsDeletedFalse(@Param("cifId") String cifId);


}
