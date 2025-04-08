package kz.balaguide.child_module.services;

import kz.balaguide.common_module.core.enums.ResponseCode;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ChildrenNotFoundException;
import lombok.RequiredArgsConstructor;
import kz.balaguide.common_module.core.annotations.ForLog;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.ChildNotEnrolledToCourseException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ChildNotFoundException;
import kz.balaguide.common_module.core.entities.Child;
import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.child_module.repository.ChildRepository;
import kz.balaguide.course_module.repository.CourseRepository;
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

        Page<Child> children = childRepository.findAll(PageRequest.of(page, size));
        if (children.getTotalElements() == 0) {
            throw new ChildrenNotFoundException(ResponseCode._0101.getMessage());
        }

        return children;
    }

    /**
     * Retrieves a {@link Child} entity by its ID.
     *
     * @param id the ID of the {@link Child} entity to be retrieved.
     * @return an {@link Optional} containing the {@link Child} if found, or empty if not found.
     */
    @Override
    public Child findById(Long id) {

        Optional<Child> child = childRepository.findById(id);
        if (child.isEmpty()) {
            throw new ChildNotFoundException(ResponseCode._0100.getMessage());
        }

        return child.get();
    }

    @Override
    public Child findByPhoneNumber(String phoneNumber) {
        return childRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ChildNotFoundException(ResponseCode._0100.getMessage()));
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
    public Child update(Long id, Child updatedChild) {

        Optional<Child> existingChildOpt = childRepository.findById(id);

        if (existingChildOpt.isEmpty()) {
            throw new ChildNotFoundException(ResponseCode._0100.getMessage());
        }

        copyNonNullProperties(updatedChild, existingChildOpt.get());

        return childRepository.save(existingChildOpt.get());
    }

    /**
     * Remove a {@link Child} entity by its ID.
     *
     * @param id the ID of the {@link Child} entity to be deleted.
     * @return true if the deletion was successful, false if no entity was found with the specified ID.
     */
    @Override
    public void removeChild(Long id) {
        if (childRepository.existsById(id)) {
            childRepository.deleteById(id);
        } else {
            throw new ChildNotFoundException(ResponseCode._0100.getMessage());
        }
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
        Optional<Child> childOpt = childRepository.findById(child.getId());

        if (childOpt.isEmpty()) {
            throw new ChildNotFoundException(ResponseCode._0100.getMessage());
        }

        Optional<List<Course>> enrolledCoursesOpt = childOpt.map(childEntity ->
                    courseRepository.findAllByChildId(childOpt.get().getId())
        );

        if (enrolledCoursesOpt.isEmpty()) {
            throw new ChildNotEnrolledToCourseException(ResponseCode._0401.getMessage());
        }

        return enrolledCoursesOpt.get();
    }

    /**
     * Copy non-null properties from source Child to target Child.
     */
    private void copyNonNullProperties(Child source, Child target) {
        if (source.getFirstName() != null) target.setFirstName(source.getFirstName());
        if (source.getLastName() != null) target.setLastName(source.getLastName());
        if (source.getBirthDate() != null) target.setBirthDate(source.getBirthDate());
        if (source.getPhoneNumber() != null) target.setPhoneNumber(source.getPhoneNumber());
        //if (source.getPassword() != null) target.setPassword(source.getPassword());
        if (source.getGender() != null) target.setGender(source.getGender());
        if (source.getParent() != null) target.setParent(source.getParent());
        if (source.getCoursesEnrolled() != null) target.setCoursesEnrolled(source.getCoursesEnrolled());
    }
}
