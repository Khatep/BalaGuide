package org.khatep.balaguide;

import lombok.RequiredArgsConstructor;
import org.khatep.balaguide.dbinit.DatabaseInitializer;
import org.khatep.balaguide.menu.MenuPrinter;
import org.khatep.balaguide.models.entities.Child;
import org.khatep.balaguide.models.entities.Course;
import org.khatep.balaguide.models.entities.Parent;
import org.khatep.balaguide.models.enums.Gender;
import org.khatep.balaguide.repositories.ParentRepository;
import org.khatep.balaguide.services.ChildService;
import org.khatep.balaguide.services.ParentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

import static org.khatep.balaguide.models.enums.Colors.*;

@SpringBootApplication
@RequiredArgsConstructor
public class BalaGuideApplication implements CommandLineRunner {

    private final ParentRepository parentRepository;
    private final DatabaseInitializer databaseInitializer;

    private static final Scanner console = new Scanner(System.in);
    private static int command = -1;
    private final MenuPrinter printer = new MenuPrinter();

    boolean isParentLogin = false;
    boolean isChildLogin = false;

    private final ParentService parentService;
    private final ChildService childService;

    public static void main(String[] args) {
        SpringApplication.run(BalaGuideApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        runApp();
    }

    private void runApp() {
        while (command != 0) {
            System.out.println("************************");
            printer.printMainMenu();
            try {
                System.out.print(GREEN.getCode() + "Enter command number: " + RESET.getCode());
                command = console.nextInt();
            } catch (Exception e) {
                System.out.println(RED.getCode() + "Error: Enter a number! (1-6)" + RESET.getCode());
                console.next();
                continue;
            }

            if (command == 1) {
                printer.printParentMenu();
                try {
                    System.out.print(GREEN.getCode()    + "Enter command number: " + RESET.getCode());
                    command = console.nextInt();
                } catch (Exception e) {
                    System.out.println(RED.getCode() + "Error: Enter a number! (1-15)" + RESET.getCode());
                    console.next();
                    continue;
                }
                switch (command) {
                    case 1 -> signUpParent();
                    case 2 -> loginParent();
                    case 3 -> addChild();
                    case 4 -> removeChild();
                    case 5 -> getMyChildren();
                    case 6 -> searchCourse();
                    case 7 -> enrollChildToCourse();
                    case 8 -> unenrollChildFromCourse();
                    case 9 -> addBalance();
                    case 10 -> {
                    }
                    default ->
                            System.out.println(RED.getCode() + "Error: Enter right command number! (From 1 to 15)" + RESET.getCode());
                }
            } else if (command == 2) {
                printer.printChildMenu();
                try {
                    System.out.print(GREEN.getCode() + "Enter command number: " + RESET.getCode());
                    command = console.nextInt();
                } catch (Exception e) {
                    System.out.println(RED.getCode() + "Error: Enter a number! (1-4)" + RESET.getCode());
                    console.next();
                    continue;
                }
                switch (command) {
                    case 1 -> loginChild();
                    case 2 -> searchCourse();
                    case 3 -> getEnrolledCourses();
                    case 4 -> {
                    }
                    default ->
                            System.out.println(RED.getCode() + "Error: Enter right command number! (From 1 to 4)" + RESET.getCode());
                }
            } else if (command == 3) {
                System.exit(0);
            } else {
                System.out.println(RED.getCode() + "Error: Enter right command number! (From 1 to 4)" + RESET.getCode());
            }
        }
    }

    public void signUpParent() {
        System.out.print("Enter first name: ");
        String firstName = console.next();

        System.out.print("Enter last name : ");
        String surname = console.next();

        System.out.print("Enter phone number: ");
        String phoneNumber = console.next();

        System.out.print("Enter email: ");
        String email = console.next();

        System.out.print("Enter password: ");
        String password = console.next();

        System.out.print("Enter address: ");
        String address = console.next();

        Parent parent = Parent.builder()
                .firstName(firstName)
                .lastName(surname)
                .phoneNumber(phoneNumber)
                .email(email)
                .password(password)
                .address(address)
                .balance(BigDecimal.valueOf(0))
                .build();

        Parent p = parentService.signUp(parent);
        if (p != null)
            System.out.println(GREEN.getCode() + "Successfully added parent!" + RESET.getCode());
        else
            System.out.println(RED.getCode() + "Something went wrong!" + RESET.getCode());

    }

    public void loginParent() {
        System.out.print("Enter phone number: ");
        String phoneNumber = console.next();

        System.out.print("Enter password: ");
        String password = console.next();

        boolean res = parentService.login(Parent.builder().phoneNumber(phoneNumber).password(password).build());
        isParentLogin = res;
        if (res)
            System.out.println(GREEN.getCode() + "Successfully logged in!" + RESET.getCode());
        else
            System.out.println(RED.getCode() + "Failed to log in!" + RESET.getCode());
    }

    public void addChild() {
        if (!isParentLogin) {
            System.out.println(CYAN.getCode() + "Please login first!" + RESET.getCode());
            loginParent();
        } else {
            System.out.print("Enter child first name: ");
            String firstName = console.next();

            System.out.print("Enter child last name: ");
            String lastName = console.next();

            System.out.print("Enter child birth date (YYYY-MM-DD): ");
            String birthDateString = console.next();
            LocalDate birthDate = LocalDate.parse(birthDateString);

            System.out.print("Enter child phone number: ");
            String phoneNumber = console.next();

            System.out.print("Enter child password: ");
            String password = console.next();

            System.out.print("Enter child gender (e.g., MALE, FEMALE): ");
            String genderInput = console.next().toUpperCase();
            Gender gender = Gender.valueOf(genderInput);

            System.out.print("Enter parent id: ");
            Long parentId = console.nextLong();

            Parent parent = parentRepository.findById(parentId).orElse(null);
            System.out.print("Enter parent password: ");
            String parentPassword = console.next();

            Child child = Child.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .birthDate(birthDate)
                    .phoneNumber(phoneNumber)
                    .password(password)
                    .gender(gender)
                    .parent(parent)
                    .build();

            Child c = parentService.addChild(child, parentPassword);
            if (c != null)
                System.out.println(GREEN.getCode() + "Successfully added child!" + RESET.getCode());
            else
                System.out.println(RED.getCode() + "Something was wrong!" + RESET.getCode());
        }
    }

    public void removeChild() {
        if (!isParentLogin) {
            System.out.println(CYAN.getCode() + "Please login first!" + RESET.getCode());
            loginParent();
        } else {
            System.out.print("Enter child id: ");
            Long childId = console.nextLong();

            System.out.print("Enter parent password: ");
            String parentPassword = console.next();

            boolean res = parentService.removeChild(childId, parentPassword);
            if (res)
                System.out.println(GREEN.getCode() + "Successfully removed child!" + RESET.getCode());
            else
                System.out.println(RED.getCode() + "Something was wrong!" + RESET.getCode());
        }
    }

    public void getMyChildren() {
        if (!isParentLogin) {
            System.out.println(CYAN.getCode() + "Please login first!" + RESET.getCode());
            loginParent();
        } else {
            System.out.print("Enter parent id: ");
            Long parentId = console.nextLong();

            System.out.print("Enter parent password: ");
            String parentPassword = console.next();

            List<Child> childList = parentService.getMyChildren(parentId, parentPassword);
            if (!childList.isEmpty()) {
                System.out.println(GREEN.getCode() + "Successfully: " + RESET.getCode());
                System.out.println(childList);
            }
            else
                System.out.println(RED.getCode() + "Something was wrong!" + RESET.getCode());
        }
    }

    public void searchCourse() {
        System.out.print("Enter course name: ");
        String courseName = console.next().trim();

        System.out.print("Do you want to add filters? (Yes/No): ");
        String isWanted = console.next().trim();

        Predicate<Course> predicate = course -> true;

        if (isWanted.equalsIgnoreCase("Yes")) {
            System.out.print("Enter category (Programming, Sport, Languages, Art, Math) or press Enter to skip: ");
            String categoryInput = console.next().trim();
            if (!categoryInput.isEmpty()) {
                predicate = predicate.and(course -> course.getCategory().toString().equalsIgnoreCase(categoryInput));
            }

            System.out.print("Enter maximum price or press Enter to skip: ");
            String maxPriceInput = console.next().trim();
            if (!maxPriceInput.isEmpty()) {
                BigDecimal maxPrice = new BigDecimal(maxPriceInput);
                predicate = predicate.and(course -> course.getPrice().compareTo(maxPrice) <= 0);
            }

            System.out.print("Enter age range (e.g., '6-10 years') or press Enter to skip: ");
            String ageRangeInput = console.next().trim();
            if (!ageRangeInput.isEmpty()) {
                predicate = predicate.and(course -> course.getAgeRange().equalsIgnoreCase(ageRangeInput));
            }
        }

        List<Course> filteredCourses = parentService.searchCoursesWithFilter(courseName, predicate);

        if (filteredCourses.isEmpty()) {
            System.out.println(RED.getCode() + "No courses found." + RESET.getCode());
        } else {
            System.out.println(GREEN.getCode() + "Found courses:" + RESET.getCode());
            for (Course course : filteredCourses) {
                System.out.println(course);
            }
        }
    }

    public void enrollChildToCourse() {
        if (!isParentLogin) {
            System.out.println(CYAN.getCode() + "Please login first!" + RESET.getCode());
            loginParent();
        } else {
            System.out.print("Enter parent id: ");
            Long parentId = console.nextLong();

            System.out.print("Enter child id: ");
            Long childId = console.nextLong();

            System.out.print("Enter course id: ");
            Long courseId = console.nextLong();

            boolean isEnrolled = false;
            try {
                isEnrolled = parentService.enrollChildToCourse(parentId, childId, courseId);
            } catch (RuntimeException e) {
                System.out.println(RED.getCode() + e.getMessage() + RESET.getCode());
            }

            if (isEnrolled)
                System.out.println(GREEN.getCode() + "Successfully enrolled child!" + RESET.getCode());
        }
    }

    public void unenrollChildFromCourse() {
        if (!isParentLogin) {
            System.out.println(CYAN.getCode() + "Please login first!" + RESET.getCode());
            loginParent();
        } else {
            System.out.print("Enter parent id: ");
            Long parentId = console.nextLong();

            System.out.print("Enter child id: ");
            Long childId = console.nextLong();

            System.out.print("Enter course id: ");
            Long courseId = console.nextLong();

            boolean isUnenrolled = parentService.unenrollChildFromCourse(parentId, childId, courseId);
            if (isUnenrolled)
                System.out.println(GREEN.getCode() + "Successfully unenrolled child!" + RESET.getCode());
            else
                System.out.println(RED.getCode() + "Something went wrong!" + RESET.getCode());
        }
    }

    /**
     * It is fake method
     */
    private void addBalance() {
        if (!isParentLogin) {
            System.out.println(CYAN.getCode() + "Please login first!" + RESET.getCode());
            loginParent();
        } else {
            System.out.print("Enter parent id: ");
            Long parentId = console.nextLong();

            System.out.print("Enter the amount of money you want to deposit: ");
            Integer amountOfMoney = console.nextInt();

            System.out.print("Enter bank account number (example: 1101): ");
            Integer accountNumber = console.nextInt();

            System.out.println(CYAN.getCode() + "The request is being sent to your bank......" + RESET.getCode());

            String res = parentService.addBalance(parentId, amountOfMoney);
            System.out.println(res);
        }
    }

    private void loginChild() {
        System.out.print("Enter phone number: ");
        String phoneNumber = console.next();

        System.out.print("Enter password: ");
        String password = console.next();

        boolean res = childService.login(Child.builder().phoneNumber(phoneNumber).password(password).build());
        isChildLogin = res;
        if (res)
            System.out.println(GREEN.getCode() + "Successfully logged in!" + RESET.getCode());
        else
            System.out.println(RED.getCode() + "Failed to log in!" + RESET.getCode());
    }

    private void getEnrolledCourses() {
        if (!isChildLogin) {
            System.out.println(CYAN.getCode() + "Please login first!" + RESET.getCode());
            loginChild();
        } else {
            System.out.print("Enter child id: ");
            Long childId = console.nextLong();

            List<Course> enrolledCourses;
            try {
                enrolledCourses = childService.getMyCourses(Child.builder().id(childId).build());
            } catch (RuntimeException e) {
                System.out.println(RED.getCode() + e.getMessage() + RESET.getCode());
                return;
            }

            System.out.println(GREEN.getCode() + "Enrolled Courses:" + RESET.getCode());
            for (Course course : enrolledCourses) {
                System.out.println("- " + course.getName() + " (ID: " + course.getId() + ")");
            }
        }
    }
}