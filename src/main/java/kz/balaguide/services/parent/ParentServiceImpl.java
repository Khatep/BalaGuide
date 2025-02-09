package kz.balaguide.services.parent;

import kz.balaguide.core.dtos.auth.CreateParentRequest;
import kz.balaguide.core.entities.*;
import kz.balaguide.core.exceptions.buisnesslogic.financialoperation.heirs.BalanceUpdateException;
import kz.balaguide.core.exceptions.buisnesslogic.financialoperation.heirs.InsufficientFundsException;
import kz.balaguide.services.auth.AuthUserService;
import kz.balaguide.services.course.CourseService;
import kz.balaguide.services.receipt.ReceiptService;
import kz.balaguide.utils.mappers.ParentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import kz.balaguide.core.annotations.ForLog;
import kz.balaguide.core.exceptions.buisnesslogic.alreadyexists.UserAlreadyExistsException;
import kz.balaguide.core.exceptions.buisnesslogic.generic.ChildNotBelongToParentException;
import kz.balaguide.core.exceptions.buisnesslogic.notfound.ChildNotFoundException;
import kz.balaguide.core.exceptions.buisnesslogic.notfound.CourseNotFoundException;
import kz.balaguide.core.exceptions.buisnesslogic.notfound.ParentNotFoundException;
import kz.balaguide.core.repositories.child.ChildRepository;
import kz.balaguide.core.repositories.course.CourseRepository;
import kz.balaguide.core.repositories.educationcenter.EducationCenterRepository;
import kz.balaguide.core.repositories.parent.ParentRepository;
import kz.balaguide.services.kafka.email.EmailProducerService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;
    private final CourseRepository courseRepository;
    private final CourseService courseService;
    private final ReceiptService receiptService;
    private final EducationCenterRepository educationCenterRepository;
    private final EmailProducerService emailProducerService;
    private final ParentMapper parentMapper;
    private final AuthUserService authUserService;

    /**
     * Adds a child to the parent's account.
     *
     * @param parentId the id of {@link Parent} entity
     * @param child the {@link Child} entity to be added
     * @return the saved {@link Child} entity
     * @throws IllegalArgumentException if the parent is not found or the password is incorrect
     */
    @Override
    @ForLog
    public Child addChild(Long parentId, Child child)  {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new ParentNotFoundException("Parent with id" + parentId + " not found"));

        child.setParent(parent);
        return childRepository.save(child);
    }

    /**
     * Removes a child from the parent's account.
     *
     * @param parentId the ID of the {@link Parent} entity which child will be removed
     * @param childId the ID of the {@link Child} entity to be removed
     * @return true if the child was successfully removed
     * @throws IllegalArgumentException if the child or parent is not found or the password is incorrect
     */
    @Override
    @ForLog
    public boolean removeChild(Long parentId, Long childId) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new ChildNotFoundException("Child with id: " + childId + " not found"));

        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new ParentNotFoundException("Parent with id: " + parentId + " not found"));

        boolean isParentsChild = child.getParent().equals(parent);
        if (!isParentsChild)
            throw new ChildNotBelongToParentException("Child with id: " + childId +
                    " does not belong to the specified parent with id: " + parentId);

        childRepository.deleteById(child.getId());
        return !childRepository.existsById(childId);
    }

    /**
     * Retrieves a list of children associated with the parent.
     *
     * @param parentId the ID of the {@link Parent} entity
     * @return a {@link List} of {@link Child} entities associated with the parent
     * @throws IllegalArgumentException if the parent is not found or the password is incorrect
     */
    @Override
    @ForLog
    public List<Child> getMyChildren(Long parentId) {
        parentRepository.findById(parentId)
                .orElseThrow(() -> new ParentNotFoundException("Parent with id" + parentId + " not found"));

        return childRepository.findAllByParentId(parentId);
    }

    /**
     * Enrolls a child in a specified course and processes payment.
     *
     * @param parentId the ID of the {@link Parent} entity
     * @param childId the ID of the {@link Child} entity to enroll
     * @param courseId the ID of the {@link Course} entity
     * @return true if enrollment is successful
     * @throws ParentNotFoundException if the parent not found
     * @throws ChildNotFoundException if the child not found
     * @throws CourseNotFoundException if the course not found
     * @throws ChildNotBelongToParentException if the child does not belong to the specified parent
     * @throws InsufficientFundsException if the payment fails due to insufficient funds
     */
    @Override
    @ForLog
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean enrollChildToCourse(Long parentId, Long childId, Long courseId) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new ParentNotFoundException("Parent with id: " + parentId + "not found"));

        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new ChildNotFoundException("Child with id: " + childId + " not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course with id: " + courseId + "not found"));

         boolean isParentsChild = child.getParent().equals(parent);

        if (!isParentsChild)
            throw new ChildNotBelongToParentException("Child does not belong to the specified parent");

        boolean isPaid = payForCourse(parentId, course);

        if (isPaid)
            return courseService.enrollChild(courseId, childId);
        else
            throw new InsufficientFundsException("Failed to pay for course");
    }

    /**
     * Unenrolls a child from a specific course.
     *
     * @param parentId The id of the parent
     * @param courseId The ID of the course.
     * @param childId  The ID of the child to be unenrolled.
     * @throws ParentNotFoundException if the parent not found
     * @throws ChildNotFoundException if the child not found
     * @throws CourseNotFoundException if the course not found
     * @throws ChildNotBelongToParentException if the child does not belong to the specified parent
     * @return True if the child is successfully unenrolled, otherwise false.
     */
    @Override
    @ForLog
    public boolean unenrollChildFromCourse(Long parentId, Long childId, Long courseId) {
        parentRepository.findById(parentId)
                .orElseThrow(() -> new ParentNotFoundException("Parent with id: " + parentId + " not found"));

        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new ChildNotFoundException("Child with id: " + childId + " not found"));

        if (!child.getParent().getId().equals(parentId)) {
            log.error("Child ID {} does not belong to Parent ID {}", childId, parentId);
            throw new ChildNotBelongToParentException("Child does not belong to the specified parent");
        }

        boolean isEnrolledInCourse = courseRepository.isChildEnrolledInCourse(courseId, childId);

        if (isEnrolledInCourse)
            return courseService.unenrollChild(courseId, childId);
        else {
            log.warn("Child ID {} is not enrolled in course ID {}", childId, courseId);
            return false;
        }
    }

    /**
     * Processes payment for a course.
     *
     * @param parentId the ID of the {@link Parent} entity making the payment
     * @param course the {@link Course} entity for which payment is being made
     * @return true if the payment is successful
     * @throws InsufficientFundsException if the parent has insufficient funds
     */
    @Override
    @ForLog
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean payForCourse(Long parentId, Course course) {
        BigDecimal coursePrice = course.getPrice();

        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new ParentNotFoundException("Parent with id: " + parentId + " not found"));

        if (parent.getBalance().compareTo(coursePrice) < 0)
            throw new InsufficientFundsException("Insufficient funds for payment");

        parent.setBalance(parent.getBalance().subtract(coursePrice));
        parentRepository.save(parent);

        try {
            course.getEducationCenter().setBalance(course.getEducationCenter().getBalance().add(coursePrice));
            educationCenterRepository.save(course.getEducationCenter());
        } catch (Exception e) {
            parent.setBalance(parent.getBalance().add(coursePrice));
            parentRepository.save(parent);
            throw new BalanceUpdateException("Failed to update balance for Education Center after parent ID: " + parentId +
                    " payment, transaction rolled back");
        }

        Receipt receipt = receiptService.createReceipt(parentId, course.getId());
        emailProducerService.sendReceiptToTopic(receipt);
        return true;
    }

    /**
     * Adds balance to the parent's account.
     *
     * @param parentId the ID of the {@link Parent} entity
     * @param amountOfMoney the amount to add to the balance
     * @param bankCardNumber the bank card number from where the balance is replenished
     * @return a success message indicating the updated balance
     * @throws ParentNotFoundException if the parent is not found
     */
    @Override
    @ForLog
    @Transactional(isolation = Isolation.READ_COMMITTED)
        public String addBalance(Long parentId, Integer amountOfMoney, String bankCardNumber) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new ParentNotFoundException("Parent with id: " + parentId + " not found"));

        BigDecimal newBalance = parent.getBalance().add(BigDecimal.valueOf(amountOfMoney));
        parent.setBalance(newBalance);
        parentRepository.save(parent);
        return "Balance updated successfully. New balance: " + newBalance;
    }

    //TODO add UsernameNotFoundException to GlobalExceptionHandler
    private Parent getByPhoneNumber(String phoneNumber) throws UsernameNotFoundException {
        return parentRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("Parent not found with phone number: " + phoneNumber));
    }

    /**
     * Save a new parent in the system.
     *
     * @param createParentRequest the {@link Parent} entity to be saved
     * @return the saved {@link Parent} entity
     */
    @Override
    public Parent save(CreateParentRequest createParentRequest) {
        if (parentRepository.existsByEmail(createParentRequest.email())) {
            log.warn("Parent with email: {} already exists", createParentRequest.email());
            throw new UserAlreadyExistsException("Parent with email: " + createParentRequest.email() + " already exists");
        }

        Parent parent = parentMapper.mapCreateParentRequestToParent(createParentRequest);

        //Связываем Parent с зарегистрированным authUser с помощью phone number
        //TODO: Возможно есть риск отсутсвтие клиента в authUser
        AuthUser authUser = (AuthUser) authUserService
                .userDetailsService()
                .loadUserByUsername(createParentRequest.phoneNumber());
        parent.setAuthUser(authUser);

        //Save parent
        parentRepository.save(parent);

        return parent;
    }

    /**
     * Removes a parent from the system.
     *
     * @param parentId the ID of the {@link Parent} entity to be removed
     * @return true if the parent was successfully removed
     * @throws ParentNotFoundException if the parent is not found
     */
    @Override
    @ForLog
    public boolean removeParent(Long parentId) {
        parentRepository.findById(parentId)
                .orElseThrow(() -> new ParentNotFoundException("Parent with id " + parentId + " not found"));

        parentRepository.deleteById(parentId);

        return !parentRepository.existsById(parentId);
    }

    /**
     * Updates the details of an existing parent.
     *
     * @param parentId the ID of the {@link Parent} entity to be updated
     * @param updatedParent the updated {@link Parent} entity with new details
     * @return the updated {@link Parent} entity
     * @throws ParentNotFoundException if the parent is not found
     */
    @Override
    @ForLog
    public Parent updateParent(Long parentId, Parent updatedParent) {
        Parent existingParent = parentRepository.findById(parentId)
                .orElseThrow(() -> new ParentNotFoundException("Parent with id " + parentId + " not found"));

        existingParent.setFirstName(updatedParent.getFirstName());
        existingParent.setLastName(updatedParent.getLastName());
        existingParent.setAddress(updatedParent.getAddress());
        existingParent.setBalance(updatedParent.getBalance());
        existingParent.setEmail(updatedParent.getEmail());
        existingParent.setPhoneNumber(updatedParent.getPhoneNumber());

        // Save the updated parent information
        return parentRepository.save(existingParent);
    }

}
