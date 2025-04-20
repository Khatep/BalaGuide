package kz.balaguide.payment_module.services.receipt;

import kz.balaguide.common_module.core.entities.*;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.CourseNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ParentNotFoundException;

public interface ReceiptService {

    Receipt createReceipt(Payment payment)
            throws ParentNotFoundException, CourseNotFoundException;

}
