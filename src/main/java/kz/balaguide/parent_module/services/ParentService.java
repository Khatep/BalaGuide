package kz.balaguide.parent_module.services;

import kz.balaguide.auth_module.dtos.CreateParentRequest;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.alreadyexists.UserAlreadyExistsException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.ChildNotBelongToParentException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.financialoperation.heirs.BalanceUpdateException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.financialoperation.heirs.InsufficientFundsException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ChildNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.CourseNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ParentNotFoundException;
import kz.balaguide.common_module.core.entities.Child;
import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.common_module.core.entities.Parent;

import java.util.List;

public interface ParentService {

    Child addChild(Long parentId, Child child) throws ParentNotFoundException;

    boolean removeChild(Long parentId, Long childId)
            throws ChildNotFoundException, ParentNotFoundException, ChildNotBelongToParentException;

    List<Child> getMyChildren(Long parentId) throws ParentNotFoundException;

    boolean enrollChildToCourse(Long parentId, Long childId, Long courseId)
            throws ParentNotFoundException, ChildNotFoundException, CourseNotFoundException, ChildNotBelongToParentException;

    boolean unenrollChildFromCourse(Long parentId, Long courseId, Long childId)
            throws ParentNotFoundException, ChildNotFoundException;

    boolean payForCourse(Long parentId, Course course)
            throws ParentNotFoundException, InsufficientFundsException, BalanceUpdateException;

    String addBalance(Long parentId, Integer amountOfMoney, String bankCardNumber) throws ParentNotFoundException;

    Parent save(CreateParentRequest createParentRequest) throws UserAlreadyExistsException;

    boolean removeParent(Long parentId) throws ParentNotFoundException;

    Parent updateParent(Long parentId, Parent updatedParent) throws ParentNotFoundException;

}
