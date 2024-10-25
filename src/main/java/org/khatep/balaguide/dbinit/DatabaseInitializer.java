    package org.khatep.balaguide.dbinit;

    import jakarta.annotation.PostConstruct;
    import lombok.RequiredArgsConstructor;
    import org.khatep.balaguide.models.entities.*;
    import org.khatep.balaguide.models.enums.Category;
    import org.khatep.balaguide.models.enums.Gender;
    import org.khatep.balaguide.repositories.*;
    import org.springframework.stereotype.Component;
    import org.springframework.transaction.annotation.Transactional;

    import java.math.BigDecimal;
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.List;

    @Component
    @RequiredArgsConstructor
    public class DatabaseInitializer {
        private final ChildRepository childRepository;
        private final CourseRepository courseRepository;
        private final EducationCenterRepository educationCenterRepository;
        private final ParentRepository parentRepository;
        private final TeacherRepository teacherRepository;

        @PostConstruct
        @Transactional
        public void init() {
            // Initialize Education Centers
            EducationCenter center1 = EducationCenter.builder()
                    .name("ABC Learning Center")
                    .dateOfCreated(LocalDate.of(2022, 1, 15))
                    .phoneNumber("555-1234")
                    .address("789 Elm St")
                    .instagramLink("https://instagram.com/abc_learning")
                    .balance(BigDecimal.valueOf(2000.00))
                    .build();

            EducationCenter center2 = EducationCenter.builder()
                    .name("IT master")
                    .dateOfCreated(LocalDate.of(2021, 5, 10))
                    .phoneNumber("555-5678")
                    .address("321 Oak Ave")
                    .instagramLink("https://instagram.com/xyz_academy")
                    .balance(BigDecimal.valueOf(3000.00))
                    .build();

            educationCenterRepository.saveAll(List.of(center1, center2));

            // Initialize Courses
            Course course1 = Course.builder()
                    .name("Math Course")
                    .description("Basic and advanced math lessons")
                    .category(Category.MATH)
                    .ageRange("6-16")
                    .price(BigDecimal.valueOf(50.00))
                    .durability(10)
                    .address("123 Learning Rd")
                    .maxParticipants(30)
                    .currentParticipants(0)
                    .educationCenter(center1)
                    .build();

            Course course2 = Course.builder()
                    .name("Introduction to Python")
                    .description("Python for everyone")
                    .category(Category.PROGRAMMING)
                    .ageRange("6-16")
                    .price(BigDecimal.valueOf(60.00))
                    .durability(12)
                    .address("456 Learning Rd")
                    .maxParticipants(25)
                    .currentParticipants(0)
                    .educationCenter(center2)
                    .build();

            // Save courses first to ensure they are managed
            courseRepository.saveAll(List.of(course1, course2));
            List<Course> managedCourses = courseRepository.findAll();

            // Initialize Parents
            Parent parent1 = Parent.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .phoneNumber("1234567890")
                    .email("johndoe@example.com")
                    .password("password123")
                    .address("123 Main St")
                    .balance(BigDecimal.valueOf(100.00))
                    .build();

            Parent parent2 = Parent.builder()
                    .firstName("Jane")
                    .lastName("Smith")
                    .phoneNumber("0987654321")
                    .email("janesmith@example.com")
                    .password("password456")
                    .address("456 Maple Ave")
                    .balance(BigDecimal.valueOf(150.00))
                    .build();

            Parent parent3 = Parent.builder()
                    .firstName("a")
                    .lastName("a")
                    .phoneNumber("a")
                    .email("a@example.com")
                    .password("a")
                    .address("a")
                    .balance(BigDecimal.valueOf(1500.00))
                    .build();

            parentRepository.saveAll(List.of(parent1, parent2, parent3));

            Child child1 = Child.builder()
                    .firstName("Emily")
                    .lastName("Doe")
                    .birthDate(LocalDate.of(2015, 6, 1))
                    .phoneNumber("1234567891")
                    .password("childpass1")
                    .gender(Gender.FEMALE)
                    .parent(parent1)
                    .coursesEnrolled(new ArrayList<>(List.of(managedCourses.get(0))))
                    .build();

            Child child2 = Child.builder()
                    .firstName("Michael")
                    .lastName("Smith")
                    .birthDate(LocalDate.of(2013, 9, 15))
                    .phoneNumber("0987654322")
                    .password("childpass2")
                    .gender(Gender.MALE)
                    .parent(parent2)
                    .coursesEnrolled(new ArrayList<>(List.of(managedCourses.get(1))))
                    .build();

            Child child3 = Child.builder()
                    .firstName("a")
                    .lastName("a")
                    .birthDate(LocalDate.of(2013, 9, 15))
                    .phoneNumber("a")
                    .password("a")
                    .gender(Gender.MALE)
                    .parent(parent3)
                    .coursesEnrolled(new ArrayList<>())
                    .build();

            childRepository.saveAll(List.of(child1, child2, child3));

            // Initialize Teachers
            Teacher teacher1 = Teacher.builder()
                    .firstName("Alice")
                    .lastName("Johnson")
                    .dateOfBirth(LocalDate.of(1985, 3, 15))
                    .phoneNumber("555-2345")
                    .password("teachpass123")
                    .salary(BigDecimal.valueOf(500.00))
                    .gender(Gender.FEMALE)
                    .myCourses(new ArrayList<>(List.of(managedCourses.get(0))))
                    .build();

            Teacher teacher2 = Teacher.builder()
                    .firstName("Bob")
                    .lastName("Williams")
                    .dateOfBirth(LocalDate.of(1978, 7, 20))
                    .phoneNumber("555-6789")
                    .password("teachpass456")
                    .salary(BigDecimal.valueOf(550.00))
                    .gender(Gender.MALE)
                    .myCourses(new ArrayList<>(List.of(managedCourses.get(1))))
                    .build();

            teacherRepository.saveAll(List.of(teacher1, teacher2));
        }

    }
