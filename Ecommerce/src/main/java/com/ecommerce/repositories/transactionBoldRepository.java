package com.ecommerce.repositories;

import com.ecommerce.models.transactionBoldModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface transactionBoldRepository extends JpaRepository<transactionBoldModel, String> {

    Optional<transactionBoldModel> findByIdInternal(String idInternal);

}
