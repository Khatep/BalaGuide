package kz.balaguide.payment_module.services.payment;

import kz.balaguide.common_module.core.entities.Child;
import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.common_module.core.entities.Parent;

public interface PaymentService {
    boolean payForCourse(Parent parent, Child child, Course course);
}
