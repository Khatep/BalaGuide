package kz.balaguide.payment_module.services.receipt;

import kz.balaguide.common_module.core.entities.*;
import kz.balaguide.common_module.core.enums.PaymentMethod;
import kz.balaguide.payment_module.repository.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {
    private final ReceiptRepository receiptRepository;

    @Override
    public Receipt createReceipt(Payment payment) {

        Receipt receipt = Receipt.builder()
                .receiptNumber(UUID.randomUUID())
                .description("Payment receipt") //TODO HARDCODE
                .issuer("SYSTEM")
                .payment(payment)
                .build();

        receipt.setFileUrl("https://balaguide.kz/receipts/"+ receipt.getReceiptNumber() + ".pdf");
        return receiptRepository.save(receipt);
    }
}
