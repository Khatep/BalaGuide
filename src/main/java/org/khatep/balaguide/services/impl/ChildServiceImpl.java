package org.khatep.balaguide.services.impl;

import lombok.RequiredArgsConstructor;
import org.khatep.balaguide.aop.annotations.ForLog;
import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;
import org.khatep.balaguide.repositories.ChildRepository;
import org.khatep.balaguide.repositories.CourseRepository;
import org.khatep.balaguide.services.ChildService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChildServiceImpl implements ChildService {

    private final ChildRepository childRepository;
    private final CourseRepository courseRepository;

    @Override
    @ForLog
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<Course> getMyCourses(Child child) {
        return childRepository.findById(child.getId())
                .map(childEntity -> courseRepository.findAllByChildId(child.getId()))
                .orElseThrow(() -> new RuntimeException("Child not found with ID: " + child.getId()));
    }

    @Override
    @ForLog
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean login(Child child) {
        Optional<Child> childFromDatabase = childRepository.findByPhoneNumber(child.getPhoneNumber());

        if (childFromDatabase.isEmpty())
            throw new RuntimeException("Child not found with phone number: " + child.getPhoneNumber());

        if(!childFromDatabase.get().getPassword().contentEquals(child.getPassword()))
            throw new RuntimeException("Wrong child password with ID: " + child.getId());

        return true;
    }
}
