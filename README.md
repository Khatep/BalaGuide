# Bala Guide

## Overview
The online platform for registering children for courses and sections is designed to simplify and optimize the search and enrollment process. Parents will be able to easily find and choose suitable courses for their children
## Table of Contents

- [Project Structure](#project-structure)
- [Updates](#Updates)
- [Access the DataBase](#access-the-dataBase)
- [DataBase](#dataBase)


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
│   │   │   │   ├── dbinit/
│   │   │   │   │   └── DatabaseInitializer
│   │   │   │   ├── exceptions
│   │   │   │   │   ├── ChildNotBelongToParentException
│   │   │   │   │   ├── CourseFullException
│   │   │   │   │   ├── IneligibleChildException
│   │   │   │   │   └── InsufficientFundsException
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
│   │   │   │   ├── repositories/
│   │   │   │   │   ├── ChildRepository
│   │   │   │   │   ├── CourseRepository
│   │   │   │   │   ├── EducationCenterRepository
│   │   │   │   │   ├── ParentRepository
│   │   │   │   │   └── TeacherRepository
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
│   │   │   │  └── application.properties
├── .gitignore
├──  pom.xml
└── README.md
```
## Updates

### added `repositories`
- **Purpose**: JPA repositories for managing database operations
  - **Interfaces**:
    - `ChildRepository`
    - `CourseRepository`
    - `EducationCenterRepository`
    - `ParentRepository`
    - `TeacherRepository`
### added `DatabaseInitializer` class to initialize the data :)
### added custom exceptions: `ChildNotBelongToParentException`, `CourseFullException`, `IneligibleChildException`, `InsufficientFundsException`
### added @Transaction for services' methods
### create `ParentServiceImplTest`
- **Purpose**: Testing the functionality of the Parent Service Impl class, ensuring that the methods work correctly
  - **Methods**:
    - `testSignUp`
    - `testLoginSuccess`
    - `testLoginFailureNotFound`
    - `testLoginFailureWrongPassword`
    - `testAddChild`
    - `testAddChildInvalidPassword`
    - `testEnrollChildToCourseSuccess`
    - `testEnrollChildToCourseInsufficientFunds`
    - `testUnenrollChildFromCourseSuccess`
    - `testUnenrollChildFromCourseNotEnrolled`

## Access the DataBase
   - Open your web browser and go to `http://localhost:8081/h2-console` to access the H2 console.
   - Use the following credentials to log in:
     - **JDBC URL**: `jdbc:h2:mem:testdb`
     - **Username**: `sa`
     - **Password**: *(leave blank)*

## DataBase

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






