package org.khatep.balaguide.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.khatep.balaguide.aop.annotations.ForLog;
import org.khatep.balaguide.exceptions.ChildNotBelongToParentException;
import org.khatep.balaguide.exceptions.InsufficientFundsException;
import org.khatep.balaguide.repositories.ChildRepository;
import org.khatep.balaguide.repositories.CourseRepository;
import org.khatep.balaguide.repositories.EducationCenterRepository;
import org.khatep.balaguide.repositories.ParentRepository;
import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;
import org.khatep.balaguide.models.entities.Parent;
import org.khatep.balaguide.services.CourseService;
import org.khatep.balaguide.services.ParentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.khatep.balaguide.models.enums.Colors.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;
    private final CourseRepository courseRepository;
    private final CourseService courseService;
    private final EducationCenterRepository educationCenterRepository;

    @Override
    @ForLog
    public Parent signUp(Parent parent) {
        return parentRepository.save(parent);
    }

    @Override
    @ForLog
    public boolean login(Parent parent) {
        Optional<Parent> parentFromDatabase = parentRepository.findByPhoneNumber(parent.getPhoneNumber());

        if (parentFromDatabase.isEmpty()) {
            log.warn("Parent with phone number {} not found", parent.getPhoneNumber());
            throw new IllegalArgumentException("Parent not found");
        }

        if (!parent.getPassword().contentEquals(parentFromDatabase.get().getPassword())) {
            log.warn("Invalid password for parent with phone number {}", parent.getPhoneNumber());
            throw new IllegalArgumentException("Wrong password for parent");
        }

        return parent.getPassword().contentEquals(parentFromDatabase.get().getPassword());
    }

    @Override
    @ForLog
    public Child addChild(Child child, String parentPassword)  {
        Parent parent = parentRepository.findById(child.getParent().getId())
                .orElseThrow(() -> new IllegalArgumentException("Parent not found"));

        if (!parent.getPassword().contentEquals(parentPassword)) {
            log.warn("Invalid password for parent ID {}", parent.getId());
            throw new IllegalArgumentException("Incorrect parent password");
        }
        return childRepository.save(child);
    }

    @Override
    @ForLog
    public boolean removeChild(Long childId, String parentPassword) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new IllegalArgumentException("Child not found"));

        Parent parent = parentRepository.findById(child.getParent().getId())
                .orElseThrow(() -> new IllegalArgumentException("Parent not found"));

        if (!parent.getPassword().contentEquals(parentPassword)) {
            log.warn("Invalid password for parent ID {}", parent.getId());
            throw new IllegalArgumentException("Incorrect parent password");
        }

        childRepository.deleteById(child.getId());
        return !childRepository.existsById(childId);
    }

    @Override
    @ForLog
    public List<Child> getMyChildren(Long parentId, String parentPassword) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("Parent not found"));

        if (!parent.getPassword().contentEquals(parentPassword)) {
            log.warn("Invalid password for parent ID {}", parent.getId());
            throw new IllegalArgumentException("Incorrect parent password");
        }

        return childRepository.findAllByParentId(parentId);
    }

    @Override
    @ForLog
    public List<Course> searchCourses(String query)  {
        return courseRepository.findByNameContainingIgnoreCase(query);
    }

    @Override
    @ForLog
    public List<Course> searchCoursesWithFilter(String query, Predicate<Course> predicate)  {
        return courseRepository.findByNameContainingIgnoreCase(query)
                .stream()
                .filter(predicate)
                .sorted()
                .toList();
    }

    @Override
    @ForLog
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean enrollChildToCourse(Long parentId, Long childId, Long courseId) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("Parent not found"));

        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new IllegalArgumentException("Child not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

         boolean isParentsChild = child.getParent().equals(parent);

        if (!isParentsChild)
            throw new ChildNotBelongToParentException("Child does not belong to the specified parent");

        boolean isPaid = payForCourse(parentId, course);

        if (isPaid)
            return courseService.enrollChild(courseId, childId);
        else
            throw new InsufficientFundsException("Failed to pay for course");
    }

    @Override
    @ForLog
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean payForCourse(Long parentId, Course course) {
        BigDecimal coursePrice = course.getPrice();

        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("Parent not found"));

        if (parent.getBalance().compareTo(coursePrice) < 0)
            throw new InsufficientFundsException("Insufficient funds for payment");

        parent.setBalance(parent.getBalance().subtract(coursePrice));
        parentRepository.save(parent);

        try {
            course.getEducationCenter().setBalance(course.getEducationCenter().getBalance().add(coursePrice));
            educationCenterRepository.save(course.getEducationCenter());
            return true;
        } catch (Exception e) {
            log.error("Failed to update balance for Education Center after parent ID {} payment", parentId, e);
            parent.setBalance(parent.getBalance().add(coursePrice));
            parentRepository.save(parent);
            throw new RuntimeException("Payment failed, transaction rolled back");
        }
    }

    @Override
    @ForLog
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String addBalance(Long parentId, Integer amountOfMoney) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("Parent not found"));

        BigDecimal newBalance = parent.getBalance().add(BigDecimal.valueOf(amountOfMoney));
        parent.setBalance(newBalance);
        parentRepository.save(parent);
        return GREEN.getCode() + "Balance updated successfully. New balance: " + newBalance + RESET.getCode();
    }

    @Override
    @ForLog
    public boolean unenrollChildFromCourse(Long parentId, Long childId, Long courseId) {
        parentRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("Parent not found"));

        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new IllegalArgumentException("Child not found"));

        if (!child.getParent().getId().equals(parentId)) {
            log.error("Child ID {} does not belong to Parent ID {}", childId, parentId);
            throw new ChildNotBelongToParentException("Child does not belong to the specified parent");
        }

        boolean isEnrolledInCourse = courseRepository.isChildEnrolledInCourse(courseId, childId);

        if (isEnrolledInCourse)
            return courseService.removeParticipant(courseId, childId);
        else {
            log.warn("Child ID {} is not enrolled in course ID {}", childId, courseId);
            return false;
        }
    }
}
