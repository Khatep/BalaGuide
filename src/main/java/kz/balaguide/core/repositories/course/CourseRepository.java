package kz.balaguide.core.repositories.course;

import jakarta.transaction.Transactional;
import kz.balaguide.core.dtos.CourseDto;
import kz.balaguide.core.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Retrieves a list of {@link Course} entities whose names contain the specified substring,
     * ignoring case.
     *
     * @param courseName the substring to search for in course names
     * @return a {@link List} of {@link Course} entities matching the search criteria
     */
    @Query("SELECT c FROM Course c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :courseName, '%'))")
    List<Course> findByNameContainingIgnoreCase(@Param("courseName") String courseName);

    /**
     * Retrieves all {@link Course} entities associated with a given child ID.
     *
     * @param childId the ID of the child whose enrolled courses are to be retrieved
     * @return a {@link List} of {@link Course} entities associated with the specified child ID
     */
    @Query("SELECT c FROM Course c JOIN c.children ch WHERE ch.id = :childId")
    List<Course> findAllByChildId(@Param("childId") Long childId);

    /**
     * Checks if a child is enrolled in a specific course.
     *
     * @param courseId the ID of the course to check
     * @param childId the ID of the child to check
     * @return true if the child is enrolled in the course, false otherwise
     */
    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END
            FROM child_course
            WHERE child_id = :childId AND course_id = :courseId
            """, nativeQuery = true)
    boolean isChildEnrolledInCourse(@Param("courseId") Long courseId, @Param("childId") Long childId);

    /**
     * Enrolls a child in a specified course.
     *
     * @param childId the ID of the child to enroll
     * @param courseId the ID of the course in which to enroll the child
     */
    @Modifying
    @Query(value = "INSERT INTO child_course (child_id, course_id) VALUES (:childId, :courseId)", nativeQuery = true)
    void enrollChildInCourse(@Param("childId") Long childId, @Param("courseId") Long courseId);

    /**
     * Unenrolls a child from a specified course.
     *
     * @param childId the ID of the child to unenroll
     * @param courseId the ID of the course from which to unenroll the child
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM Child c JOIN c.coursesEnrolled ce WHERE c.id = :childId AND ce.id = :courseId")
    void unenrollChildInCourse(@Param("childId") Long childId, @Param("courseId") Long courseId);

    /**
     * Retrieves a {@link CourseDto} containing the name and price of a course by its ID.
     *
     * @param courseId the ID of the course to retrieve
     * @return a {@link CourseDto} containing the course's name and price
     */
    @Query("SELECT new kz.balaguide.utils.dtos.CourseDto(c.name, c.price) FROM Course c WHERE c.id = :courseId")
    CourseDto findCoursePriceAndNameById(@Param("courseId") Long courseId);
}
