package kz.balaguide.payment_module.services.receipt;

import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.CourseNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ParentNotFoundException;
import kz.balaguide.common_module.core.entities.Receipt;

public interface ReceiptService {

    Receipt createReceipt(Long parentId, Long childId, Long courseId)
            throws ParentNotFoundException, CourseNotFoundException;

}
