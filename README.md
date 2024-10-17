# Bala Guide

The online platform for registering children for courses and sections is designed to simplify and optimize the search and enrollment process. Parents will be able to easily find and select appropriate courses, view class schedules and track their children's attendance. The platform will include a system of notifications informing about upcoming classes and changes in the schedule.
Course organizers will have access to tools for program management, including the ability to edit schedules, control course registrations, as well as the ability to collect feedback and suggestions from parents and children to improve the quality of courses and sections. Integration with payment systems will ensure convenient payments and increase user satisfaction.

## Table of Contents

- [Project Structure](#project-structure)
- [Report for practice 1](#report-for-practice-1)

## Project Structure

```bash
BalaGuide/
├── src/
│   ├── main
│   ├── java/org/khatep/balaguide/
│   │   │   │   ├── models/entities/
│   │   │   │   │   ├── Child.java
│   │   │   │   │   ├── Course.java
│   │   │   │   │   └── Parent.java
│   │   │   │   ├── repositories/
│   │   │   │   │   ├── ChildRepository.java.java
│   │   │   │   │   ├── CourseRepository.java.java
│   │   │   │   │   └── ParentRepository.java  
│   │   │   │   ├── services/
│   │   │   │   │   └── MainService.java
│   │   │   │   └── BalaGuideApplication.java.java
│   ├── resources/
│   │   │   │  └── application.properties
├── .gitignore
├──  pom.xml
└── README.md
```
# Report for practice 1
For the first practice, I described three entities (entity fields are described with JavaDoc):
- **`Parent`**
- **`Child`**
- **`Course`**
  
and made a repository for each one separately.

I wrote one common **`MainService`** class which has methods that implement the business logic layer of the application.

list of methods: 

- **`Parent signUp(Parent parent)`**
  - Registers a new parent and saves to the database.

- **`boolean login(Parent parent)`**
  - Logs in a parent by checking their credentials.
    
- **`Child addChild(Parent parent, Child child)`**
  - Adds a new child to a parent's list of children and saves the child to the repository.

- **`void removeChild(Parent parent, Child child)`**
  - Removes a child from a parent's list of children and deletes the child from the repository.

- **`List<Child> getChildren(Parent parent)`**
  - Retrieves a list of all children associated with a parent.

- **`List<Child> getChildren(Parent parent, Predicate<Child> predicate)`**
  - Retrieves a list of children associated with a parent, filtered by a given condition.

- **`List<Course> searchCourses(String query)`**
  - Searches for courses by name or a portion of the name.

- **`List<Course> searchCoursesWithFilter(String query, Predicate<Course> predicate)`**
  - Searches for courses by name or a portion of the name, then filters and sorts the results.

- **`boolean enrollChildToCourse(Long parentId, Long childId, Long courseId)`**
  - Enrolls a child in a course after verifying that the child belongs to the parent.

- **`boolean payForCourse(Long parentId, Course course)`**
  - Processes payment for enrolling a child in a course.

- **`boolean unenrollChildFromCourse(Long courseId, Long childId)`**
  - Unenrolls a child from a specific course.

- **`List<Course> getMyCourses(Child child)`**
  - Retrieves courses enrolled by a specific child.

- **`Course addCourse(Course course)`**
  - Adds a new course to the repository.

- **`Course updateInformation(Long courseId, Course updatedCourse)`**
  - Updates course information.

- **`List<Course> getCourses(String name)`**
  - Retrieves all courses or filters by name.

- **`boolean addParticipant(Long courseId, Long childId)`**
  - Adds a child as a participant in a course.

- **`boolean isCourseFull(Course course)`**
  - Checks if a course is full.

- **`int getCurrentParticipants(Long courseId)`**
  - Gets the current number of participants for a course.

- **`boolean isChildEligible(Course course, Child child)`**
  - Checks if a child is eligible for the course based on age range.

<br>
At the end, I overridden the run method in <strong><code>BalaGuideApplication.java</code></strong> and wrote code to test the methods. 



