package kz.balaguide.services.child;

import kz.balaguide.core.enums.ResponseCode;
import kz.balaguide.core.exceptions.buisnesslogic.notfound.ChildrenNotFoundException;
import lombok.RequiredArgsConstructor;
import kz.balaguide.core.annotations.ForLog;
import kz.balaguide.core.exceptions.buisnesslogic.generic.ChildNotEnrolledToCourseException;
import kz.balaguide.core.exceptions.buisnesslogic.notfound.ChildNotFoundException;
import kz.balaguide.core.entities.Child;
import kz.balaguide.core.entities.Course;
import kz.balaguide.core.repositories.child.ChildRepository;
import kz.balaguide.core.repositories.course.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    /**
     * Retrieves all {@link Child} heirs.
     *
     * @return a {@link List} of all {@link Child} heirs.
     */
    @Override
    public Page<Child> findAll(int page, int size) {

        throw new RuntimeException();
//        Page<Child> children = childRepository.findAll(PageRequest.of(page, size));
//        if (children.getTotalElements() == 0) {
//            throw new ChildrenNotFoundException(ResponseCode._0002.getMessage());
//        };
//
//        return children;
    }

    /**
     * Retrieves a {@link Child} entity by its ID.
     *
     * @param id the ID of the {@link Child} entity to be retrieved.
     * @return an {@link Optional} containing the {@link Child} if found, or empty if not found.
     */
    @Override
    public Optional<Child> findById(Long id) {
        return childRepository.findById(id);
    }

    /**
     * Saves a new {@link Child} entity.
     *
     * @param child the {@link Child} entity to be saved.
     * @return the saved {@link Child} entity.
     */
    @Override
    public Child save(Child child) {
        return childRepository.save(child);
    }

    /**
     * Updates an existing {@link Child} entity by its ID.
     *
     * @param id    the ID of the {@link Child} entity to be updated.
     * @param updatedChild the {@link Child} entity containing updated information.
     * @return an {@link Optional} containing the updated {@link Child} if found and updated, or empty if not found.
     */
    @Override
    public Optional<Child> update(Long id, Child updatedChild) {
        return childRepository.findById(id).map(existingChild -> {
            copyNonNullProperties(updatedChild, existingChild);
            return childRepository.save(existingChild);
        });
    }

    /**
     * Copy non-null properties from source Child to target Child.
     */
    private void copyNonNullProperties(Child source, Child target) {
        if (source.getFirstName() != null) target.setFirstName(source.getFirstName());
        if (source.getLastName() != null) target.setLastName(source.getLastName());
        if (source.getBirthDate() != null) target.setBirthDate(source.getBirthDate());
        if (source.getPhoneNumber() != null) target.setPhoneNumber(source.getPhoneNumber());
        if (source.getPassword() != null) target.setPassword(source.getPassword());
        if (source.getGender() != null) target.setGender(source.getGender());
        if (source.getParent() != null) target.setParent(source.getParent());
        if (source.getCoursesEnrolled() != null) target.setCoursesEnrolled(source.getCoursesEnrolled());
    }

    /**
     * Remove a {@link Child} entity by its ID.
     *
     * @param id the ID of the {@link Child} entity to be deleted.
     * @return true if the deletion was successful, false if no entity was found with the specified ID.
     */
    @Override
    public boolean removeChild(Long id) {
        if (childRepository.existsById(id)) {
            childRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Retrieves a list of courses that the specified child is enrolled in.
     *
     * @param child the {@link Child} entity for which to retrieve enrolled courses
     * @return a {@link List} of {@link Course} heirs that the child is enrolled in
     * @throws RuntimeException if the child is not found or if the child is not enrolled in any courses
     */
    @Override
    @ForLog
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<Course> getMyCourses(Child child) {
        Optional<Child> childFromDatabase = childRepository.findById(child.getId());

        if (childFromDatabase.isEmpty())
            throw new ChildNotFoundException("Child not found with ID: " + child.getId());

        Optional<List<Course>> enrolledCoursesFromDatabase = childFromDatabase.map(childEntity ->
                courseRepository.findAllByChildId(childFromDatabase.get().getId())
        );

        if (enrolledCoursesFromDatabase.isEmpty())
            throw new ChildNotEnrolledToCourseException("Child with id: " + child + " is not enrolled in any courses");

        return enrolledCoursesFromDatabase.get();
    }
}
