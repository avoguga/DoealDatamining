package com.doealdm.repository;

import com.doealdm.domain.Concessao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Concessao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConcessaoRepository extends JpaRepository<Concessao, Long> {}
