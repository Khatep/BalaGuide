package org.khatep.balaguide.services;

import org.khatep.balaguide.exceptions.CourseNotFoundException;
import org.khatep.balaguide.exceptions.ParentNotFoundException;
import org.khatep.balaguide.models.entities.Receipt;

public interface ReceiptService {

    Receipt createReceipt(Long parentId, Long courseId)
            throws ParentNotFoundException, CourseNotFoundException;

}
