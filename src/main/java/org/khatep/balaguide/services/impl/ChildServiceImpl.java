package org.khatep.balaguide.services.impl;

import lombok.RequiredArgsConstructor;
import org.khatep.balaguide.aop.annotations.ForLog;
import org.khatep.balaguide.dao.ChildDao;
import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;
import org.khatep.balaguide.services.ChildService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChildServiceImpl implements ChildService {

    private final ChildDao childDao;

    @Override
    @ForLog
    public List<Course> getMyCourses(Child child) {
        Optional<Child> childOptional = childDao.findById(child.getId());
        if (childOptional.isPresent())
            return childDao.findCoursesByChildId(child.getId());
        else
            return Collections.EMPTY_LIST;
    }

    @Override
    @ForLog
    public boolean login(Child child) {
        Optional<Child> childFromDatabase = childDao.findByPhoneNumber(child.getPhoneNumber());

        return childFromDatabase
                .filter(value -> child.getPassword().contentEquals(value.getPassword()))
                .isPresent();
    }
}
