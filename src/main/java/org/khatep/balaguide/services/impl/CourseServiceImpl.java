package org.khatep.balaguide.services.impl;

import lombok.RequiredArgsConstructor;
import org.khatep.balaguide.aop.annotations.ForLog;
import org.khatep.balaguide.dao.ChildDao;
import org.khatep.balaguide.dao.CourseDao;
import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;
import org.khatep.balaguide.services.CourseService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseDao courseDao;
    private final ChildDao childDao;

    @Override
    @ForLog
    public int addCourse(Course course) {
        return courseDao.save(course);
    }

    @Override
    @ForLog
    public int updateInformation(Long courseId, Course updatedCourse) {
        Optional<Course> existingCourse = courseDao.findById(courseId);
        if (existingCourse.isPresent()) {
            Course course = existingCourse.get();
            course.setName(updatedCourse.getName());
            course.setDescription(updatedCourse.getDescription());
            course.setEducationCenterId(updatedCourse.getEducationCenterId());
            course.setCategory(updatedCourse.getCategory());
            course.setAgeRange(updatedCourse.getAgeRange());
            course.setPrice(updatedCourse.getPrice());
            course.setDurability(updatedCourse.getDurability());
            course.setAddress(updatedCourse.getAddress());
            course.setMaxParticipants(updatedCourse.getMaxParticipants());
            course.setCurrentParticipants(updatedCourse.getCurrentParticipants());
            return courseDao.save(course);
        }
        return 0;
    }

    @Override
    @ForLog
    public List<Course> getCourses() {
            return courseDao.findAll();
        }

    @Override
    @ForLog
    public boolean addParticipant(Long courseId, Long childId) {
        Optional<Course> courseOpt = courseDao.findById(courseId);
        Optional<Child> childOpt = childDao.findById(childId);

        if (courseOpt.isPresent() && childOpt.isPresent()) {
            Course course = courseOpt.get();
            Child child = childOpt.get();

            if (isChildEligible(course, child) && !isCourseFull(course)) {
                courseDao.addParticipant(childId, courseId);

                course.setCurrentParticipants(course.getCurrentParticipants() + 1);
                courseDao.update(course);
                return true;
            }
        }
        return false;
    }

    @Override
    @ForLog
    public boolean removeParticipant(Long courseId, Long childId) {
        Optional<Course> courseOpt = courseDao.findById(courseId);
        Optional<Child> childOpt = childDao.findById(childId);

        if (courseOpt.isPresent() && childOpt.isPresent()) {
            Course course = courseOpt.get();
            Child child = childOpt.get();

            if (child.getCoursesEnrolled().contains(course)) {
                course.setCurrentParticipants(course.getCurrentParticipants() - 1);
                child.getCoursesEnrolled().remove(course);
                return true;
            }
        }
        return false;
    }

    @Override
    @ForLog
    public boolean isCourseFull(Course course)  {
        return course.getCurrentParticipants() >= course.getMaxParticipants();
    }

    @Override
    @ForLog
    public int getCurrentParticipants(Long courseId) {
        Optional<Course> courseOpt = courseDao.findById(courseId);
        return courseOpt.map(Course::getCurrentParticipants).orElse(-1);
    }

    @Override
    @ForLog
    public boolean isChildEligible(Course course, Child child) {
        String[] ageRangeParts = course.getAgeRange().split("-");
        int minAge = Integer.parseInt(ageRangeParts[0].trim());
        int maxAge = Integer.parseInt(ageRangeParts[1].trim());

        int childAge = LocalDate.now().getYear() - child.getBirthDate().getYear();
        return childAge >= minAge && childAge <= maxAge;
    }
}
