package org.khatep.balaguide.repositories;

import org.khatep.balaguide.models.entities.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
}
