package org.khatep.balaguide.services;

import org.khatep.balaguide.exceptions.*;
import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;
import org.khatep.balaguide.models.entities.Parent;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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

    UserDetailsService userDetailsService() throws UsernameNotFoundException;

    Parent save(Parent parent) throws UserAlreadyExistsException;

    boolean removeParent(Long parentId) throws ParentNotFoundException;

    Parent updateParent(Long parentId, Parent updatedParent) throws ParentNotFoundException;

}
