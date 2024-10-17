package org.khatep.balaguide.services;

import lombok.RequiredArgsConstructor;
import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;
import org.khatep.balaguide.models.entities.Parent;
import org.khatep.balaguide.repositories.ChildRepository;
import org.khatep.balaguide.repositories.CourseRepository;
import org.khatep.balaguide.repositories.ParentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class MainService {
    private final ChildRepository childRepository;
    private final CourseRepository courseRepository;
    private final ParentRepository parentRepository;

    /** Parent methods */

    /**
     * Registers a new parent and save to Database.
     *
     * @param parent The parent to be registered.
     * @return The saved parent entity with a generated ID.
     */
    public Parent signUp(Parent parent) {
        return parentRepository.save(parent);
    }

    /**
     * Logs in a parent by checking their credentials.
     *
     * @param parent The parent attempting to log in, with their ID, phone number, and password.
     * @return True if the login credentials are valid, otherwise false.
     */
    public boolean login(Parent parent) {
        Optional<Parent> parentFromDatabase = parentRepository.findById(parent.getId());

        return parentFromDatabase
                .filter(value -> parent.getPassword().toString().contentEquals(value.getPassword())
                    && parent.getPhoneNumber().equals(value.getPhoneNumber()))
                .isPresent();
    }

    /**
     * Adds a new child to a parent's list of children and saves the child to the repository.
     *
     * @param parent The parent to whom the child will be added.
     * @param child  The child to be added.
     * @return The saved child entity with a generated ID.
     */
    public Child addChild(Parent parent, Child child) {
        parent.getMyChildren().add(child);
        parentRepository.save(parent);

        return childRepository.save(child);
    }

    /**
     * Removes a child from a parent's list of children and deletes the child from the repository.
     *
     * @param parent The parent from whom the child will be removed.
     * @param child  The child to be removed.
     */
    public void removeChild(Parent parent, Child child) {
        parent.getMyChildren().remove(child);
        parentRepository.save(parent);

        childRepository.delete(child);
    }

    /**
     * Retrieves a list of all children associated with a parent.
     *
     * @param parent The parent whose children will be retrieved.
     * @return A list of children associated with the given parent.
     */
    public List<Child> getChildren(Parent parent) {
        return parent.getMyChildren();
    }

    /**
     * Retrieves a list of children associated with a parent, filtered by a given condition.
     *
     * @param parent    The parent whose children will be filtered.
     * @param predicate A predicate to filter the children.
     * @return A list of children that match the given condition.
     */
    public List<Child> getChildren(Parent parent, Predicate<Child> predicate) {
        return parent.getMyChildren()
                .stream()
                .filter(predicate)
                .toList();
    }

    /**
     * Searches for courses by name or a portion of the name.
     *
     * @param query The search term to match against course names.
     * @return A list of courses whose names contain the search term, case-insensitive.
     */
    public List<Course> searchCourses(String query) {
        return courseRepository.findByNameContainingIgnoreCase(query);
    }

    /**
     * Searches for courses by name or a portion of the name, then filters and sorts the results.
     *
     * @param query     The search term to match against course names.
     * @param predicate A predicate to filter the courses.
     * @return A list of filtered and sorted courses whose names contain the search term.
     */
    public  List<Course> searchCoursesWithFilter(String query, Predicate<Course> predicate) {
        return courseRepository.findByNameContainingIgnoreCase(query)
                .stream()
                .filter(predicate)
                .sorted()
                .toList();
    }

    /**
     * Enrolls a child in a course after verifying that the child belongs to the parent.
     *
     * @param parentId The id of parent of the child.
     * @param childId  The id of child to be enrolled.
     * @param courseId The id of course.
     * @return True if the child is successfully enrolled, otherwise false.
     */
    @Transactional
    public boolean enrollChildToCourse(Long parentId, Long childId, Long courseId) {
        Optional<Parent> parentOptional= parentRepository.findById(parentId);
        Optional<Child> childOptional = childRepository.findById(childId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (parentOptional.isEmpty() || childOptional.isEmpty() || courseOptional.isEmpty())
            return false;

        boolean isPaid = payForCourse(parentOptional.get().getId(), courseOptional.get());

        boolean isChildWithParent = parentOptional
                .map(p -> p.getMyChildren().contains(childOptional.get()))
                .orElse(false);

        if (isChildWithParent && isPaid) {
            return addParticipant(courseId, childId);
        }

        return false;
    }

    /**
     * Processes payment for enrolling a child in a course.
     *
     * @param parentId  The id of parent making the payment.
     * @param course  The course being paid for.
     * @return True if the payment is successful and the child's enrollment is confirmed, otherwise false.
     */
    public boolean payForCourse(Long parentId, Course course) {
        BigDecimal coursePrice = course.getPrice();

        return parentRepository.findById(parentId)
                .filter(p -> p.getBalance().compareTo(coursePrice) >= 0)
                .map(parent -> {
                    parent.setBalance(parent.getBalance().subtract(coursePrice));
                    parentRepository.save(parent);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Unenrolls a child from a specific course.
     *
     * @param courseId The ID of the course.
     * @param childId  The ID of the child to be unenrolled.
     * @return True if the child is successfully unenrolled, otherwise false.
     */
    public boolean unenrollChildFromCourse(Long courseId, Long childId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        Optional<Child> childOptional = childRepository.findById(childId);

        if (courseOptional.isPresent() && childOptional.isPresent()) {
            Course course = courseOptional.get();
            Child child = childOptional.get();

            // Remove the child from the list of enrolled children.
            boolean removed = child.getCoursesEnrolled().remove(course);
            if (removed) {
                childRepository.save(child);
            }

            return removed;
        }

        return false;
    }

    /** Child methods */
    @Transactional
    public List<Course> getMyCourses(Child child) {
        Optional<Child> childOptional = childRepository.findById(child.getId());
        return childOptional.map(Child::getCoursesEnrolled).orElse(null);
    }

    /** Course methods */

    /**
     * Add a new course to the repository.
     *
     * @param course The course to be added.
     * @return The saved course.
     */
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    /**
     * Update course information.
     *
     * @param courseId The ID of the course to be updated.
     * @param updatedCourse The course object containing updated information.
     * @return The updated course or null if not found.
     */
    public Course updateInformation(Long courseId, Course updatedCourse) {
        Optional<Course> existingCourse = courseRepository.findById(courseId);
        if (existingCourse.isPresent()) {
            Course course = existingCourse.get();
            course.setName(updatedCourse.getName());
            course.setDescription(updatedCourse.getDescription());
            course.setEducationalCenter(updatedCourse.getEducationalCenter());
            course.setCategory(updatedCourse.getCategory());
            course.setAgeRange(updatedCourse.getAgeRange());
            course.setPrice(updatedCourse.getPrice());
            return courseRepository.save(course);
        }
        return null;
    }

    /**
     * Retrieves all courses or filter by name.
     *
     * @param name Optional name filter.
     * @return A list of courses.
     */
    public List<Course> getCourses(String name) {
        if (name == null || name.isEmpty()) {
            return courseRepository.findAll();
        }
        return courseRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Add a child as a participant in a course.
     *
     * @param courseId The ID of the course.
     * @param childId The ID of the child.
     * @return True if the child is successfully added, otherwise false.
     */
    public boolean addParticipant(Long courseId, Long childId) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        Optional<Child> childOpt = childRepository.findById(childId);

        if (courseOpt.isPresent() && childOpt.isPresent()) {
            Course course = courseOpt.get();
            Child child = childOpt.get();

            if (isChildEligible(course, child) && !isCourseFull(course)) {
                course.setCurrentParticipants(course.getCurrentParticipants());
                child.getCoursesEnrolled().add(course);
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a course is full.
     *
     * @param course The course to check.
     * @return True if the course is full, otherwise false.
     */
    public boolean isCourseFull(Course course) {
        return course.getCurrentParticipants() >= course.getMaxParticipants();
    }

    /**
     * Get the current number of participants for a course.
     *
     * @param courseId The ID of the course.
     * @return The number of participants or -1 if the course is not found.
     */
    public int getCurrentParticipants(Long courseId) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        return courseOpt.map(Course::getCurrentParticipants).orElse(-1);
    }

    /**
     * Check if a child is eligible for the course based on age range.
     *
     * @param course The course to check against.
     * @param child The child to check.
     * @return True if the child is eligible, otherwise false.
     */
    public boolean isChildEligible(Course course, Child child) {
        String[] ageRangeParts = course.getAgeRange().split("-");
        int minAge = Integer.parseInt(ageRangeParts[0].trim());
        int maxAge = Integer.parseInt(ageRangeParts[1].trim());

        int childAge = LocalDate.now().getYear() - child.getBirthDate().getYear();
        return childAge >= minAge && childAge <= maxAge;
    }
}