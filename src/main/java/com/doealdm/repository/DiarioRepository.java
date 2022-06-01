package com.doealdm.repository;

import com.doealdm.domain.Diario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Diario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiarioRepository extends JpaRepository<Diario, Long> {}
