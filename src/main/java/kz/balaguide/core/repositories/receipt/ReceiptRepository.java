package kz.balaguide.core.repositories.receipt;

import kz.balaguide.core.entities.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
}
