package kz.balaguide.parent_module.services;

import kz.balaguide.common_module.core.entities.BankCard;
import kz.balaguide.course_module.dto.EnrollmentActionDto;
import kz.balaguide.parent_module.dtos.CreateChildRequest;
import kz.balaguide.parent_module.dtos.CreateParentRequest;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.ChildNotBelongToParentException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ChildNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.CourseNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ParentNotFoundException;
import kz.balaguide.common_module.core.entities.Child;
import kz.balaguide.common_module.core.entities.Parent;
import kz.balaguide.parent_module.dtos.UpdateParentRequest;

import java.util.List;

public interface ParentService {

    Parent save(CreateParentRequest createParentRequest);

    Child addChild(Long parentId, CreateChildRequest createChildRequest);

    Parent findByPhoneNumber(String phoneNumber);
    Parent findById(Long id);
    boolean removeChild(Long parentId, Long childId);

    List<Child> getMyChildren(Long parentId);

    boolean enrollChildToCourse(EnrollmentActionDto enrollmentActionDto)
            throws ParentNotFoundException, ChildNotFoundException, CourseNotFoundException, ChildNotBelongToParentException;

    boolean unenrollChildFromCourse(EnrollmentActionDto enrollmentActionDto)
            throws ParentNotFoundException, ChildNotFoundException;

    String addBalance(Long parentId, Integer amountOfMoney, BankCard bankCard) throws ParentNotFoundException;

    Parent updateParent(Long parentId, UpdateParentRequest updatedParent) throws ParentNotFoundException;

}
