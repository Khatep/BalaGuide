# Bala Guide

## Overview
The online platform for registering children for courses and sections is designed to simplify and optimize the search and enrollment process. Parents will be able to easily find and choose suitable courses for their children
## Table of Contents

- [Project Structure](#project-structure)
- [Updates](#Updates)
=======
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
│   ├── java/kz/balaguide/
│   │   │   │   ├── aop/
│   │   │   │   │   └── annotations/
│   │   │   │   │   │   └── ForLog
│   │   │   │   │   └── LoggingAspect
│   │   │   │   ├── controllers/
│   │   │   │   │   ├── ChildController
│   │   │   │   │   ├── CourseContoller
│   │   │   │   │   └── ParentController
│   │   │   │   ├── exceptions/
│   │   │   │   │   ├── BalanceUpdateException
│   │   │   │   │   ├── ChildNotBelongToParentException
│   │   │   │   │   ├── ChildNotEnrolledException
│   │   │   │   │   ├── ChildNotFoundException
│   │   │   │   │   ├── CourseFullException
│   │   │   │   │   ├── CourseNotFoundException
│   │   │   │   │   ├── EducationCenterNotFoundException
│   │   │   │   │   ├── IneligibleChildException
│   │   │   │   │   ├── InsufficientFundsException
│   │   │   │   │   ├── ParentNotFoundException
│   │   │   │   │   └── UserAlreadyExistsException
│   │   │   │   ├── kafka/
│   │   │   │   │   ├── config/
│   │   │   │   │   │   └── KafkaConfiguration
│   │   │   │   │   └── consumer/
│   │   │   │   │   │   └── EmailConsumerService
│   │   │   │   │   └── producer/
│   │   │   │   │   │   └── EmailProducerService
│   │   │   │   ├── mappers/
│   │   │   │   │   ├── CourseMapper
│   │   │   │   ├── models/
│   │   │   │   │   └── dto/
│   │   │   │   │   │   └── CourseDto
│   │   │   │   │   └── entities/
│   │   │   │   │   │   ├── Child
│   │   │   │   │   │   ├── Course
│   │   │   │   │   │   ├── EducationCenter
│   │   │   │   │   │   ├── Parent
│   │   │   │   │   │   ├── Receipt
│   │   │   │   │   │   └── Teacher
│   │   │   │   │   └── enums/
│   │   │   │   │   │   ├── Category
│   │   │   │   │   │   ├── Gender
│   │   │   │   │   │   ├── PaymentMethod
│   │   │   │   │   │   └── Role
│   │   │   │   │   └── requests/
│   │   │   │   │   │   ├── AddBalanceRequest
│   │   │   │   │   │   └── CourseRequest
│   │   │   │   ├── repositories/
│   │   │   │   │   ├── ChildRepository
│   │   │   │   │   ├── CourseRepository
│   │   │   │   │   ├── EducationCenterRepository
│   │   │   │   │   ├── ParentRepository
│   │   │   │   │   └── ReceiptRepository
│   │   │   │   ├── security/
│   │   │   │   │   ├── config/
│   │   │   │   │   │   ├── JwtAuthenticationFilter
│   │   │   │   │   │   └── SecurityConfiguration
│   │   │   │   │   ├── controller/
│   │   │   │   │   │   └── AuthenticationController
│   │   │   │   │   ├── dto/
│   │   │   │   │   │   ├── JwtAuthenticationResponse
│   │   │   │   │   │   ├── SignInRequest
│   │   │   │   │   │   ├── SignUpEduCenterRequest
│   │   │   │   │   │   └── SignUpParentRequest
│   │   │   │   │   ├── service/
│   │   │   │   │   │   ├── AuthenticationService
│   │   │   │   │   │   └── JwtService
│   │   │   │   ├── services/
│   │   │   │   │   └── impl/
│   │   │   │   │   │   ├── ChildServiceImpl
│   │   │   │   │   │   ├── CourseServiceImpl
│   │   │   │   │   │   ├── EducationServiceImpl
│   │   │   │   │   │   ├── ParentServiceImpl
│   │   │   │   │   │   └── ReceiptServiceImpl
│   │   │   │   │   ├── ChildService
│   │   │   │   │   ├── CourseService
│   │   │   │   │   ├── EducationCenterService
│   │   │   │   │   ├── ParentService
│   │   │   │   │   └── ReceiptService
│   │   │   │   └── BalaGuideApplication.java
│   ├── resources/
│   │   │   │  └── application.yaml
├── .gitignore
├──  pom.xml
└── README.md
```

## Updates

### `Spring Security`
I added security to my project, added authentication and authorization using roles and json web token. Defined a custom `JwtAuthenticationFilter`  and a controller that accepts authentication requests <br>
Endpoints:
- POST: http://localhost:8081/auth/sign-up   (only Parent right now, but it can easily be customized for other types of users.)
  ```json
  {
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "+12345678901",
    "birthDate": "2003-11-27",
    "email": "nurgali.khatep@gmail.com",
    "password": "password123"
  }

- POST: http://localhost:8081/auth/sign-up-edu-center (For Education Center)
  ```json
  {
    "name": "IT easy",
    "dateOfCreated": "2023-11-02",
    "phoneNumber": "87711134898",
    "email": "aserty663qer@gmail.com",
    "password": "password1234",
    "address": "Manasa 34/1",
    "instagramLink": "it_easy_school"
  }

The response will be a generated JWT 
<br>
- POST: http://localhost:8081/auth/sign-in
  ```json
  {
    "email": "nurgali.khatep@gmail.com",
    "password": "password123"
  }

### `ParentController`

firstly you should sign up, and save jwt for next requests
<br>

Choose Bearer token in Postman for Authorization config then input token
<br>
Endpoints:


- POST: http://localhost:8081/api/v1/parents/1/add-child
  ```json
  {
    "firstName": "Ali",
    "lastName": "Utepov",
    "phoneNumber": "+12345678901",
    "birthDate": "2014-11-27",
    "email": "ali.utepov@gmail.com",
    "password": "password123",
    "gender": "MALE"
  }
  
- DELETE: http://localhost:8081/api/v1/parents/1/remove-child/1
  ```json
    none

- GET: http://localhost:8081/api/v1/parents/1/my-children
  ```json
    none

- POST: http://localhost:8081/api/v1/parents/1/add-balance
  ```json
  {
    "amountOfMoney": 1000000,
    "numberOfBankCard": "4405444044404400"
  }

Before next requests you must do `sign-up-edu-center` and execute `http://localhost:8081/api/v1/courses/add-course` (With the token for edu center) and `http://localhost:8081/api/v1/parents/1/add-balance` (parent`s token) 
- POST: http://localhost:8081/api/v1/courses/1/enroll/1
  ```json
  none

### `CourseController`

Before next requests you must do `sign-up-edu-center`
- POST: http://localhost:8081/api/v1/courses/add-course
  ```json
  {
  "educationCenterId": 1,
  "name": "Programming for Kids",
  "description": "An introductory course for kids to learn programming basics.",
  "category": "PROGRAMMING",
  "ageRange": "6-10",
  "price": 150.00,
  "durability": 8,
  "maxParticipants": 20,
  "currentParticipants": 0
  }

- GET: http://localhost:8081/api/v1/courses/search-courses?query=Program
  ```json
  none

- POST: http://localhost:8081/api/v1/courses/1/enroll/1
  ```json
  none
- POST: http://localhost:8081/api/v1/courses/1/unenroll/1
  ```json
  none

<br></br>
### AOP for controllers
I added some methods to the LoggingAspect class, where previously there were already methods that did logging via my @ForLog annotation. The new methods do Log every request and response and their (ip address, url, username) duties  
<br>
### Create a lot of custom exceptions
### I use some functional interfaces, Stream API, records
=======
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