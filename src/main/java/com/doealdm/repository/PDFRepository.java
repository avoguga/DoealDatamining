package com.doealdm.repository;

import com.doealdm.domain.PDF;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PDF entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PDFRepository extends JpaRepository<PDF, Long> {}
