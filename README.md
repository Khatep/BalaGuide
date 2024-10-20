# Bala Guide

## Overview
The online platform for registering children for courses and sections is designed to simplify and optimize the search and enrollment process. Parents will be able to easily find and choose suitable courses for their children
## Table of Contents

For pratice 2, I use AOP, I made a custom annotation (@ForLog) and managed the logs and use JdbcTemplate

- [Project Structure](#project-structure)
- [Familiarization with the project](#familiarization-with-the-project)
- [Access the DataBase](#access-the-dataBase)
- [DataBase](#dataBase)

when using methods, you need to be logged in, for this you can use mocks from data.sql. Information about them is below

## Project Structure

```bash
BalaGuide/
├── src/
│   ├── main
│   ├── java/org/khatep/balaguide/
│   │   │   │   ├── aop/
│   │   │   │   │   └── annotations/
│   │   │   │   │   │   └── ForLog
│   │   │   │   │   └── LoggingAspect
│   │   │   │   ├── dao/
│   │   │   │   │   └── impl/
│   │   │   │   │   │   ├── ChildDaoImpl
│   │   │   │   │   │   ├── CourseDaoImpl
│   │   │   │   │   │   ├── EducationCenterDaoImpl
│   │   │   │   │   │   ├── ParentDaoImpl
│   │   │   │   │   │   └── TeacherDaoImpl
│   │   │   │   │   ├── ChildDao
│   │   │   │   │   ├── CourseDao
│   │   │   │   │   ├── EducationCenterDao
│   │   │   │   │   ├── ParentDao
│   │   │   │   │   └── TeacherDao
│   │   │   │   ├── menu/
│   │   │   │   │   └── MenuPrinter
│   │   │   │   ├── models/
│   │   │   │   │   └── entities/
│   │   │   │   │   │   ├── Child
│   │   │   │   │   │   ├── Course
│   │   │   │   │   │   ├── EducationCenter
│   │   │   │   │   │   ├── Parent
│   │   │   │   │   │   └── Teacher
│   │   │   │   │   └── enums/
│   │   │   │   │   │   ├── Category
│   │   │   │   │   │   ├── Colors
│   │   │   │   │   │   └── Gender
│   │   │   │   ├── services/
│   │   │   │   │   └── impl/
│   │   │   │   │   │   ├── ChildServiceImpl
│   │   │   │   │   │   ├── CourseServiceImpl
│   │   │   │   │   │   └── ParentServiceImpl
│   │   │   │   │   ├── ChildService
│   │   │   │   │   ├── CourseService
│   │   │   │   │   └── ParentService
│   │   │   │   └── BalaGuideApplication.java
│   ├── resources/
│   │   │   │  ├── application.properties
│   │   │   │  ├── data.sql
│   │   │   │  └── schema.sql
├── .gitignore
├──  pom.xml
└── README.md
```
## Familiarization with the project
### 1. `aop`
- **Purpose**: Contains aspects for logging and other cross-cutting concerns.
  -- **annotations**:
    - `ForLog` - annotation for marking methods for logging
  - **Classes**:
    - `LoggingAspect`
      - **Description**: This class uses Aspect-Oriented Programming (AOP) to log method execution details.
      - **Methods**:
        - `logBefore(JoinPoint joinPoint, Object param)`:
          - **Description**: Logs method entry details before the execution of methods annotated with `@ForLog`.
          - **Parameters**:
            - `joinPoint`: Provides access to the method being called.
            - `param`: The parameter passed to the method; can be null.
        - `logAround(ProceedingJoinPoint proceedingJoinPoint)`:
          - **Description**: Logs the execution time of methods annotated with `@ForLog`.
          - **Parameters**:
            - `proceedingJoinPoint`: Provides access to the method being called.
          - **Returns**: The result of the method execution.
          - **Throws**: `Throwable` if the method execution fails.
        - `logAfterThrowing(JoinPoint joinPoint, Throwable exception)`:
          - **Description**: Logs an error message when a method annotated with `@ForLog` throws an exception.
          - **Parameters**:
            - `joinPoint`: Provides access to the method being called.
            - `exception`: The exception thrown by the method.



### 2. `dao`
- **Purpose**: Data Access Objects for managing database operations, the methods are implemented using JdbcTemplate
  - **impl** - this is a package with classes that implement the following interfaces
  - **Interfaces**:
    - `ChildDao`
      - **Methods**:
        - `save(Child child)`
        - `update(Child child)`
        - `deleteById(Long childId)`
        - `findAll()`
        - `findAllByParentId(Long parentId)`
        - `findById(Long childId)`
        - `findCoursesByChildId(Long childId)`
        - `findByPhoneNumber(String phoneNumber)`
      
    - `CourseDao`
      - **Methods**:
        - `save(Course course)`
        - `update(Course course)`
        - `deleteById(Long courseId)`
        - `findAll()`
        - `findById(Long courseId)`
        - `findByNameContainingIgnoreCase(String courseName)`
        - `addParticipant(Long childId, Long courseId)`
    
    - `EducationCenterDao`
      - **Methods**:
        - `save(EducationCenter educationCenter)`
        - `update(EducationCenter educationCenter)`
        - `deleteById(Long educationCenterId)`
        - `findAll()`
        - `findById(Long educationCenterId)`
    
    - `ParentDao`
      - **Methods**:
        - `save(Parent parent)`
        - `update(Parent parent)`
        - `deleteById(Long parentId)`
        - `findAll()`
        - `findById(Long parentId)`
        - `findByPhoneNumber(String phoneNumber)`

    - `TeacherDao`
      - **Methods**:
        - `save(Teacher teacher)`
        - `update(Teacher teacher)`
        - `deleteById(Long teacherId)`
        - `findAll()`
        - `findById(Long teacherId)`

### 3. `menu`
- **Purpose**: Manages the console menus for user interaction.

  - **Classes**:
    - `MenuPrinter`
      - **Methods**:
        - `printMainMenu()`
        - `printParentMenu()`
        - `printChildMenu()`

### 4. `models`
- **Purpose**: Contains entity classes representing the database schema.

  - **Sub-packages**:
    - `entities`
      - **Classes**:
        - `Child`
        - `Course`
        - `EducationCenter`
        - `Parent`
        - `Teacher`
    - `enums`
      - **Enums**:
        - `Category`
        - `Colors`
        - `Gender`

### 5. `services`
- **Purpose**: Contains service classes that implement business logic.
  -**impl** - this is a package with classes that implement the following interfaces
  - **Interfaces**:
    - `ChildService`
      - **Methods**:
        - `List<Course> getMyCourses(Child child)`
        - `boolean login(Child build)`

    - `CourseService`
      - **Methods**:
        - `int addCourse(Course course)`
        - `int updateInformation(Long courseId, Course updatedCourse)`
        - `List<Course> getCourses()`
        - `boolean addParticipant(Long courseId, Long childId)`
        - `boolean removeParticipant(Long courseId, Long childId)`
        - `boolean isCourseFull(Course course)`
        - `int getCurrentParticipants(Long courseId)`
        - `boolean isChildEligible(Course course, Child child)`

    - `ParentService`
      - **Methods**:
        - `int signUp(Parent parent)`
        - `boolean login(Parent parent)`
        - `int addChild(Child child, String parentPassword)`
        - `int removeChild(Long childId, String parentPassword)`
        - `List<Child> getMyChildren(Long parentId, String parentPassword)`
        - `List<Child> getChildren(Parent parent, Predicate<Child> predicate)`
        - `List<Course> searchCourses(String query);`
        - `List<Course> searchCoursesWithFilter(String query, Predicate<Course> predicate)`
        - `boolean enrollChildToCourse(Long parentId, Long childId, Long courseId)`
        - `boolean unenrollChildFromCourse(Long parentId, Long courseId, Long childId)`
        - `boolean payForCourse(Long parentId, Course course)`
        - `String addBalance(Long parentId, Integer amountOfMoney)`

### 6. `BalaGuideApplication`
- **Purpose**: The main application class that serves as the entry point for the Spring Boot application.

  - **Class**:
    - `BalaGuideApplication`
      - **Methods**:
        - `public static void main(String[] args)`


## Access the DataBase
   - Open your web browser and go to `http://localhost:8081/h2-console` to access the H2 console.
   - Use the following credentials to log in:
     - **JDBC URL**: `jdbc:h2:mem:testdb`
     - **Username**: `sa`
     - **Password**: *(leave blank)*

## DataBase
The `schema.sql` file defines the structure of the database for the BalaGuide application. This file is essential for setting up the initial database schema, including tables, data types, and relationships necessary to support the application's functionality. 

### Enums
- **Gender Enum**: This enum defines two possible values for gender—'MALE' and 'FEMALE'. It is used in the `teacher` and `child` tables to categorize individuals accordingly.
- **Category Enum**: This enum encompasses various educational categories, including 'PROGRAMMING', 'SPORT', 'LANGUAGES', 'ART', and 'MATH'. It is applied in the `course` table to classify courses.

## Tables

### 1. Education Center
- **Table Name**: `education_center`
- **Purpose**: Stores information about educational institutions offering courses. Key attributes include the center's name, creation date, contact information, and social media links.

### 2. Course
- **Table Name**: `course`
- **Purpose**: Contains details about the courses available at education centers. Attributes include course name, description, category (linked to the Category Enum), pricing, and participant details. It also establishes a foreign key relationship with the `education_center` table to associate each course with a specific center.

### 3. Teacher
- **Table Name**: `teacher`
- **Purpose**: Captures information about teachers, including their personal details and gender. This table allows for managing teachers who conduct courses.

### 4. Parent
- **Table Name**: `parent`
- **Purpose**: Manages parent information, including contact details and account credentials. This table supports features related to parent accounts and their interaction with courses.

### 5. Child
- **Table Name**: `child`
- **Purpose**: Holds data regarding children enrolled in courses. Each child can be linked to a parent via a foreign key, allowing for structured access to parental information.

### 6. Teacher-Course Relationship
- **Table Name**: `teacher_course`
- **Purpose**: Establishes a many-to-many relationship between teachers and courses, allowing multiple teachers to be associated with multiple courses. This is essential for course management and instructor assignments.

### 7. Child-Course Relationship
- **Table Name**: `child_course`
- **Purpose**: Facilitates a many-to-many relationship between children and courses, enabling tracking of which children are enrolled in which courses.

### 8. Parent Favorite Course Relationship
- **Table Name**: `parent_favorite_course`
- **Purpose**: Maintains a many-to-many relationship between parents and their favorite courses, allowing parents to easily access courses they are interested in for their children.

### 9. Child Interests
- **Table Name**: `child_interest`
- **Purpose**: Links children to their interests through categories. This table helps in customizing course recommendations based on a child's interests.


The initial data is populated in the H2 database using the `data.sql` script. The following records are pre-inserted:

## Education Centers

### Code School
- **ID**: 101
- **Date of Created**: 2021-03-10
- **Phone Number**: `87731581222`
- **Address**: `789 Secondary St, Townsville`
- **Instagram Link**: [Link](https://instagram.com/codeschool)

---

## Courses

### Python for Beginners
- **ID**: 101
- **Educational Center**: `Code School`
- **Category**: `PROGRAMMING`
- **Age Range**: `8-16`
- **Price**: `$200.00`
- **Max Participants**: `15`
- **Current Participants**: `5`
- **Description**: Start your journey with Python.
- **Durability**: `40 hours`
- **Address**: `789 Secondary St, Townsville`
- **Course Materials**: Course book, Online materials.

### Web Development Basics
- **ID**: 102
- **Educational Center**: `Code School`
- **Category**: `PROGRAMMING`
- **Age Range**: `10-18`
- **Price**: `$350.00`
- **Max Participants**: `20`
- **Current Participants**: `10`
- **Description**: Learn HTML, CSS, and JavaScript.
- **Durability**: `60 hours`
- **Address**: `789 Secondary St, Townsville`
- **Course Materials**: Course book, Online materials, Projects.

---

## Teachers

### Erzhan Khatep
- **ID**: 101
- **Date of Birth**: 1990-05-12
- **Phone Number**: `87761080680`
- **Gender**: `MALE`
- **Password**: `securepassword456`

### ALi Utepov
- **ID**: 102
- **Date of Birth**: 2000-11-30
- **Phone Number**: `87771467852`
- **Gender**: `MALE`
- **Password**: `securepassword789`

---

## Parents

### Nurgali Harris
- **ID**: 101
- **Phone Number**: `123`
- **Email**: `nurgaloi@example.com`
- **Balance**: `$250.00`
- **Password**: `parentpassword123`
- **Address**: `Saina, 73`

---

## Children

### Ergali Harris
- **ID**: 101
- **Date of Birth**: 2012-04-20
- **Phone Number**: `12345`
- **Gender**: `MALE`
- **Password**: `childpassword123`
- **Parent ID**: `101`

### Zhansaya Li
- **ID**: 102
- **Date of Birth**: 2015-02-15
- **Phone Number**: `12345678`
- **Gender**: `FEMALE`
- **Password**: `childpassword456`
- **Parent ID**: `101`

---

## Relationships

### Teacher-Course Assignments

- **Teacher Erzhan Khatep** (ID: 101) teaches **Python for Beginners** (ID: 101).
- **Teacher ALi Utepov** (ID: 102) teaches **Web Development Basics** (ID: 102).

### Child-Course Enrollments

- **Child Ergali Harris** (ID: 101) is enrolled in **Python for Beginners** (ID: 101).
- **Child Zhansaya Li** (ID: 102) is enrolled in **Web Development Basics** (ID: 102).

### Parent Favorite Courses

- **Parent Nurgali Harris** (ID: 101) has marked **Python for Beginners** (ID: 101) as a favorite course.
- **Parent Nurgali Harris** (ID: 101) has marked **Web Development Basics** (ID: 102) as a favorite course.

### Child Interests

- **Child Ergali Harris** (ID: 101) is interested in the **PROGRAMMING** category.
- **Child Zhansaya Li** (ID: 102) is interested in the **PROGRAMMING** category.

---




