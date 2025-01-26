package kz.balaguide.services.receipt;

import kz.balaguide.core.exceptions.buisnesslogic.notfound.CourseNotFoundException;
import kz.balaguide.core.exceptions.buisnesslogic.notfound.ParentNotFoundException;
import kz.balaguide.core.entities.Receipt;

public interface ReceiptService {

    Receipt createReceipt(Long parentId, Long courseId)
            throws ParentNotFoundException, CourseNotFoundException;

}
