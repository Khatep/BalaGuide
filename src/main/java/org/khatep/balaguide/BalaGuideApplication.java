package org.khatep.balaguide;

import lombok.RequiredArgsConstructor;
import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;
import org.khatep.balaguide.models.entities.Parent;
import org.khatep.balaguide.services.MainService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class BalaGuideApplication implements CommandLineRunner {

    private final MainService mainService;

    public static void main(String[] args) {
       SpringApplication.run(BalaGuideApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        Parent parent = new Parent(1L, "Nurgali", "Khatep", "87711134885", "nurgali@gmail.com", "password123", "Saina 48", BigDecimal.valueOf(100_000), new ArrayList<>());

        Parent savedParent = mainService.signUp(parent);
        System.out.println("Parent registered: " + savedParent);

        // Create and save a child
        Child child = new Child(1L, "Ergali", "Khatep", LocalDate.of(2008, 5, 25), "8772158945", "password123", "male", savedParent, new ArrayList<>());

        Child savedChild = mainService.addChild(savedParent, child);
        System.out.println("Child added: " + savedChild);

        // Create and save a course
        Course course = new Course(1L, "Python for children", "Basic Python Course", "IT school", "programming", "8-16", BigDecimal.valueOf(20_000), "Satbaeva 15", 15, 0);

        Course savedCourse = mainService.addCourse(course);
        System.out.println("Course added: " + savedCourse);

        // Enroll the child to the course
        boolean enrolled = mainService.enrollChildToCourse(savedParent.getId(), savedChild.getId(), savedCourse.getId());
        System.out.println("Child enrolled in course: " + enrolled);

        // Check the balance after payment
        System.out.println("Remaining balance: " + savedParent.getBalance());

        // Get the child's enrolled courses
        List<Course> enrolledCourses = mainService.getMyCourses(savedChild);
        System.out.println("Courses enrolled by child: " + enrolledCourses);

        // Unenroll the child from the course
        boolean unenrolled = mainService.unenrollChildFromCourse(savedCourse.getId(), savedChild.getId());
        System.out.println("Child unenrolled from course: " + unenrolled);

    }
}
