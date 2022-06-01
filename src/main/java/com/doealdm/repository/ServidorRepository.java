package com.doealdm.repository;

import com.doealdm.domain.Servidor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Servidor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServidorRepository extends JpaRepository<Servidor, Long> {}
