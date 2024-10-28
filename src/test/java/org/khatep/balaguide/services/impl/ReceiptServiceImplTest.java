package org.khatep.balaguide.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.khatep.balaguide.models.entities.Receipt;
import org.khatep.balaguide.models.enums.PaymentMethod;

import java.time.LocalDate;

public class ReceiptServiceImplTest {

    private ReceiptServiceImpl receiptService;

    @BeforeEach
    public void setUp() {
        receiptService = new ReceiptServiceImpl();
    }

    @Test
    public void testCreateReceipt() {
        Long parentId = 1L;
        Long courseId = 2L;

        Receipt receipt = receiptService.createReceipt(parentId, courseId);

        assertNotNull(receipt, "Receipt should not be null");
        assertEquals(parentId, receipt.getParentId(), "ParentId should match");
        assertEquals(courseId, receipt.getCourseId(), "CourseId should match");
        assertEquals(12, receipt.getPercentOfVat(), "VAT percent should be 12");
        assertEquals(LocalDate.now(), receipt.getDateOfCreated(), "Creation date should be today");
        assertEquals(PaymentMethod.BANK_CARD, receipt.getPaymentMethod(), "Payment method should be BANK_CARD");
    }
}
