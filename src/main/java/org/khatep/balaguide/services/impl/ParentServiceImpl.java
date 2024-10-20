package org.khatep.balaguide.services.impl;

import lombok.RequiredArgsConstructor;
import org.khatep.balaguide.aop.annotations.ForLog;
import org.khatep.balaguide.dao.ChildDao;
import org.khatep.balaguide.dao.CourseDao;
import org.khatep.balaguide.dao.ParentDao;
import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;
import org.khatep.balaguide.models.entities.Parent;
import org.khatep.balaguide.services.CourseService;
import org.khatep.balaguide.services.ParentService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.khatep.balaguide.models.enums.Colors.*;

@Service
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {

    private final ParentDao parentDao;
    private final ChildDao childDao;
    private final CourseDao courseDao;
    private final CourseService courseService;

    @Override
    @ForLog
    public int signUp(Parent parent) {
        return parentDao.save(parent);
    }

    @Override
    @ForLog
    public boolean login(Parent parent) {
        Optional<Parent> parentFromDatabase = parentDao.findByPhoneNumber(parent.getPhoneNumber());

        return parentFromDatabase
                .filter(value -> parent.getPassword().contentEquals(value.getPassword()))
                .isPresent();
    }

    @Override
    @ForLog
    public int addChild(Child child, String parentPassword)  {
        Optional<Parent> parentFromDatabase = parentDao.findById(child.getParentId());
        if (parentFromDatabase.isPresent() && parentFromDatabase.get().getPassword().contentEquals(parentPassword))
            return childDao.save(child);
        else
            return 0;

    }

    @Override
    @ForLog
    public int removeChild(Long childId, String parentPassword) {
        Optional<Child> childFromDatabase = childDao.findById(childId);

        if (childFromDatabase.isPresent()) {
            Long parentId = childFromDatabase.get().getParentId();
            Optional<Parent> parentFromDatabase = parentDao.findById(parentId);

            if (parentFromDatabase.isPresent() && parentFromDatabase.get().getPassword().contentEquals(parentPassword))
                return childDao.deleteById(childId);
        }
        return 0;
    }

    @Override
    @ForLog
    public List<Child> getMyChildren(Long parentId, String parentPassword)  {
        Optional<Parent> parentFromDatabase = parentDao.findById(parentId);
        if (parentFromDatabase.isPresent() && parentFromDatabase.get().getPassword().contentEquals(parentPassword))
            return childDao.findAllByParentId(parentId);

        return Collections.EMPTY_LIST;
    }

    @Override
    @ForLog
    public List<Child> getChildren(Parent parent, Predicate<Child> predicate) {
        return parent.getMyChildren()
                .stream()
                .filter(predicate)
                .toList();
    }

    @Override
    @ForLog
    public List<Course> searchCourses(String query)  {
        return courseDao.findByNameContainingIgnoreCase(query);
    }

    @Override
    @ForLog
    public List<Course> searchCoursesWithFilter(String query, Predicate<Course> predicate)  {
        return courseDao.findByNameContainingIgnoreCase(query)
                .stream()
                .filter(predicate)
                .sorted()
                .toList();
    }

    @Override
    @ForLog
    public boolean enrollChildToCourse(Long parentId, Long childId, Long courseId) {
        Optional<Parent> parentOptional= parentDao.findById(parentId);
        Optional<Child> childOptional = childDao.findById(childId);
        Optional<Course> courseOptional = courseDao.findById(courseId);

        boolean result = false;

        if (parentOptional.isEmpty() || childOptional.isEmpty() || courseOptional.isEmpty())
            result = false;

        boolean isPaid = false;
        boolean isParentsChild = false;

        if (parentOptional.isPresent() && courseOptional.isPresent() && childOptional.isPresent() ) {
            isPaid = payForCourse(parentOptional.get().getId(), courseOptional.get());

            isParentsChild = getMyChildren(parentOptional.get().getId(), parentOptional.get().getPassword())
                    .stream()
                    .anyMatch(child -> child.getId().equals(childOptional.get().getId()));
        }

        if (isParentsChild && isPaid)
            result = courseService.addParticipant(courseId, childId);

        return result;
    }

    @Override
    @ForLog
    public boolean payForCourse(Long parentId, Course course) {
        BigDecimal coursePrice = course.getPrice();

        return parentDao.findById(parentId)
                .filter(p -> p.getBalance().compareTo(coursePrice) >= 0)
                .map(parent -> {
                    parent.setBalance(parent.getBalance().subtract(coursePrice));
                    parentDao.update(parent);
                    return true;
                })
                .orElse(false);
    }

    @Override
    @ForLog
    public String addBalance(Long parentId, Integer amountOfMoney) {
        Optional<Parent> parentOptional= parentDao.findById(parentId);

        if (parentOptional.isPresent()) {
            Parent parent = parentOptional.get();

            BigDecimal newBalance = parent.getBalance().add(BigDecimal.valueOf(amountOfMoney));
            parent.setBalance(newBalance);
            parentDao.update(parent);
            return GREEN.getCode() + "Balance updated successfully. New balance: " + newBalance + RESET.getCode();
        } else {
            return RED.getCode() + "Parent not found!" + RESET.getCode();
        }
    }

    @Override
    @ForLog
    public boolean unenrollChildFromCourse(Long parentId, Long childId, Long courseId) {
        Optional<Parent> parentOptional = parentDao.findById(parentId);
        Optional<Child> childOptional = childDao.findById(childId);
        Optional<Course> courseOptional = courseDao.findById(courseId);

        boolean result = false;

        if (parentOptional.isEmpty() || childOptional.isEmpty() || courseOptional.isEmpty()) {
            return false;
        }

        Child child = childOptional.get();

        boolean isParentsChild = parentId.equals(child.getParentId());

        boolean isEnrolledInCourse = childDao.findCoursesByChildId(childId)
                .stream()
                .filter(c -> c.getId().equals(courseId))
                .toList()
                .isEmpty();

        if (isParentsChild && isEnrolledInCourse)
            result = courseService.removeParticipant(courseId, childId);

        return result;
    }
}
