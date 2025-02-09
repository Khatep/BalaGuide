package kz.balaguide.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import kz.balaguide.core.entities.Receipt;
import kz.balaguide.core.enums.PaymentMethod;
import kz.balaguide.core.repositories.receipt.ReceiptRepository;
import kz.balaguide.services.receipt.ReceiptServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceiptServiceImplTest {

    @Mock
    private ReceiptRepository receiptRepository;

    @InjectMocks
    private ReceiptServiceImpl receiptService;

    @Test
    public void testCreateReceipt() {
        Long parentId = 100L;
        Long courseId = 100L;
        Receipt receipt = Receipt.builder()
                .parentId(parentId)
                .courseId(courseId)
                .percentOfVat(12)
                .dateOfCreated(LocalDate.now())
                .paymentMethod(PaymentMethod.BANK_CARD)
                .build();

        when(receiptRepository.save(receipt)).thenReturn(receipt);

        Receipt createdReceipt = receiptService.createReceipt(parentId, courseId);

        assertNotNull(createdReceipt);
        assertEquals(parentId, createdReceipt.getParentId());
        assertEquals(courseId, createdReceipt.getCourseId());
        assertEquals(12, createdReceipt.getPercentOfVat());
        assertEquals(LocalDate.now(), createdReceipt.getDateOfCreated());
        assertEquals(PaymentMethod.BANK_CARD, createdReceipt.getPaymentMethod());
    }
}
