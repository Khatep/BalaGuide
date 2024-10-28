package org.khatep.balaguide.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.khatep.balaguide.models.entities.Receipt;
import org.khatep.balaguide.models.enums.PaymentMethod;
import org.khatep.balaguide.repositories.ReceiptRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
