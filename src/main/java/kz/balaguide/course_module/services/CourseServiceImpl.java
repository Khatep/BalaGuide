package kz.balaguide.course_module.services;

import kz.balaguide.common_module.core.entities.Group;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.ChildNotEnrolledToCourseException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.GroupNotFoundException;
import kz.balaguide.course_module.dto.EnrollmentActionDto;
import kz.balaguide.course_module.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import kz.balaguide.common_module.core.annotations.ForLog;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.CourseFullException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.IneligibleChildException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ChildNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.CourseNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.EducationCenterNotFoundException;
import kz.balaguide.course_module.mappers.CourseMapper;
import kz.balaguide.common_module.core.entities.Child;
import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.common_module.core.entities.EducationCenter;
import kz.balaguide.common_module.core.dtos.requests.CourseRequest;
import kz.balaguide.child_module.repository.ChildRepository;
import kz.balaguide.course_module.repository.CourseRepository;
import kz.balaguide.education_center_module.repository.EducationCenterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ChildRepository childRepository;
    private final EducationCenterRepository educationCenterRepository;
    private final GroupRepository groupRepository;
    private final CourseMapper courseMapper;
    private final GroupService groupService;

    /**
     * Adds a new course to the system.
     *
     * @param courseRequest the {@link CourseRequest} entity to be added
     * @return the saved {@link Course} entity
     */
    @Override
    @ForLog
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Course addCourse(CourseRequest courseRequest) {
        EducationCenter educationCenter = educationCenterRepository.findById(courseRequest.educationCenterId())
                .orElseThrow(() -> new EducationCenterNotFoundException("Education center with id: " + courseRequest.educationCenterId() + " not found") );

        Course course = courseMapper.mapCourseRequestToCourse(courseRequest, educationCenter);

        return courseRepository.save(course);
    }

    /**
     * Updates the information of an existing course.
     *
     * @param courseId the ID of the course to update
     * @param updatedCourse the {@link Course} entity containing updated information
     * @return the updated {@link Course} entity
     * @throws RuntimeException if the course is not found
     */
    @Override
    @ForLog
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Course updateInformation(Long courseId, Course updatedCourse) {
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course with id: " + courseId + " not found"));

        courseMapper.mapUpdatedCourseToExisting(existingCourse, updatedCourse);

        return courseRepository.save(existingCourse);
    }

    /**
     * Retrieves a list of all courses available in the system.
     *
     * @return a {@link List} of {@link Course} heirs
     */
    @Override
    @ForLog
    @Transactional(readOnly = true)
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }


    @Override
    @ForLog
    public boolean removeCourse(Long courseId) {
        childRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course with id " + courseId + " not found"));

        childRepository.deleteById(courseId);

        return !childRepository.existsById(courseId);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    @ForLog
    public boolean enrollChild(EnrollmentActionDto enrollmentActionDto) {
        Course course = courseRepository.findById(enrollmentActionDto.courseId())
                .orElseThrow(() -> new CourseNotFoundException("Course with id: " + enrollmentActionDto.courseId() + " not found"));
        Child child = childRepository.findById(enrollmentActionDto.childId())
                .orElseThrow(() -> new ChildNotFoundException("Child with id: " + enrollmentActionDto.childId() + " not found"));
        Group group = groupRepository.findById(enrollmentActionDto.groupId())
                .orElseThrow(() -> new GroupNotFoundException("Group with id: " + enrollmentActionDto.groupId() + " not found"));

        if (!isChildEligible(course, child)) {
            throw new IneligibleChildException("Child is not eligible for this course.");
        }
        if (group.isGroupFull()) {
            throw new CourseFullException("Course is full and cannot enroll more participants.");
        }

        child.getGroupsEnrolled().add(group);
        childRepository.save(child);

        group.setCurrentParticipants(group.getCurrentParticipants() + 1);
        groupRepository.save(group);
        return true;
    }

    @Override
    @ForLog
    public List<Course> searchCourses(String query)  {
        return courseRepository.findByNameContainingIgnoreCase(query)
                .stream()
                .sorted()
                .toList();
    }

/*    @Override
    @ForLog
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean unenrollChild(EnrollmentActionDto enrollmentActionDto) {
        Course course = courseRepository.findById(enrollmentActionDto.childId())
                .orElseThrow(() -> new CourseNotFoundException("Course with id: " + enrollmentActionDto.courseId() + " not found"));

        Group group = groupRepository.findById(enrollmentActionDto.courseId())
                .orElseThrow(() -> new GroupNotFoundException("Group with id: " + enrollmentActionDto.groupId() + " not found"));

        childRepository.findById(course.getId())
                .orElseThrow(() -> new ChildNotFoundException("Child with id: " + enrollmentActionDto.childId() + " not found"));

        List<Group> enrolledGroup = groupRepository.findAllByChildId(enrollmentActionDto.childId());

        if (!enrolledGroup.isEmpty()) {
            throw new ChildNotEnrolledToCourseException("Child with id: " + enrollmentActionDto.childId() + " is not enrolled in any courses");
        }

        group.setCurrentParticipants(group.getCurrentParticipants() - 1);
        groupRepository.unenrollChildFromCourseGroup(enrollmentActionDto.courseId(), course.getId());
        return true;
    }*/


    /**
     * Checks if a child is eligible for a specified course based on age range.
     *
     * @param course the {@link Course} entity to check against
     * @param child the {@link Child} entity to verify eligibility
     * @return true if the child is eligible for the course
     */
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
