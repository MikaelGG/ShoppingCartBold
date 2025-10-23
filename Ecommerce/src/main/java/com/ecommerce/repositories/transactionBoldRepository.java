package com.ecommerce.repositories;

import com.ecommerce.dtos.InitButtonRequest;
import com.ecommerce.models.transactionBoldModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface transactionBoldRepository extends JpaRepository<transactionBoldModel, String> {

    Optional<transactionBoldModel> findByIdInternal(String idInternal);

    List<transactionBoldModel> findByIdClientOrderByCreateAtDesc(Long idClient);

    List<transactionBoldModel> findAllByOrderByCreateAtDesc();

    Boolean existsByIdClient(Long id);

    Boolean existsByIdInternal(String idInternal);

    @Query(value = "SELECT products FROM transactionBoldModel WHERE idInternal = ?1")
    String findProductsByIdInternal(String idInternal);

}
