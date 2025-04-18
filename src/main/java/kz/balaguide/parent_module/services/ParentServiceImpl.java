package kz.balaguide.parent_module.services;

import kz.balaguide.child_module.mappers.ChildMapper;
import kz.balaguide.course_module.dto.EnrollmentActionDto;
import kz.balaguide.course_module.repository.GroupRepository;
import kz.balaguide.course_module.services.GroupService;
import kz.balaguide.parent_module.dtos.CreateChildRequest;
import kz.balaguide.parent_module.dtos.CreateParentRequest;
import kz.balaguide.common_module.core.entities.*;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.financialoperation.heirs.InsufficientFundsException;
import kz.balaguide.auth_module.services.AuthUserService;
import kz.balaguide.course_module.services.CourseService;
import kz.balaguide.parent_module.dtos.UpdateParentRequest;
import kz.balaguide.payment_module.services.payment.PaymentService;
import kz.balaguide.parent_module.mappers.ParentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import kz.balaguide.common_module.core.annotations.ForLog;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.alreadyexists.UserAlreadyExistsException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.generic.ChildNotBelongToParentException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ChildNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.CourseNotFoundException;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.notfound.ParentNotFoundException;
import kz.balaguide.child_module.repository.ChildRepository;
import kz.balaguide.course_module.repository.CourseRepository;
import kz.balaguide.parent_module.repository.ParentRepository;
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
    private final GroupRepository groupRepository;

    private final CourseService courseService;
    private final GroupService groupService;
    private final PaymentService paymentService;

    //TODO: Добавил authenticationService так как родитель регистрирует своего ребенка, но это hardcode
    private final AuthUserService authUserService;

    private final ParentMapper parentMapper;
    private final ChildMapper childMapper;


    /**
     * Save a new parent in the system.
     *
     * @param createParentRequest the {@link Parent} entity to be saved
     * @return the saved {@link Parent} entity
     */
    @Override
    public Parent createParentAndSave(CreateParentRequest createParentRequest) {
        if (parentRepository.existsByEmail(createParentRequest.email())) {
            log.warn("Parent with email: {} already exists", createParentRequest.email());
            throw new UserAlreadyExistsException("Parent with email: " + createParentRequest.email() + " already exists");
        }

        if (parentRepository.existsByPhoneNumber(createParentRequest.phoneNumber())) {
            log.warn("Parent with phoneNumber: {} already exists", createParentRequest.phoneNumber());
            throw new UserAlreadyExistsException("Parent with phoneNumber: " + createParentRequest.phoneNumber() + " already exists");
        }

        Parent parent = parentMapper.mapCreateParentRequestToParent(createParentRequest);

        //Связываем Parent с зарегистрированным authUser с помощью phone number
        //TODO: Возможно есть риск отсутствие клиента в authUser
        AuthUser authUser = (AuthUser) authUserService
                .userDetailsService()
                .loadUserByUsername(createParentRequest.phoneNumber());
        parent.setAuthUser(authUser);

        //Save parent
        return parentRepository.save(parent);
    }

    /**
     * Adds a child to the parent's account.
     *
     * @param parentId the id of {@link Parent} entity
     * @param createChildRequest the {@link Child} entity to be added
     * @return the saved {@link Child} entity
     * @throws IllegalArgumentException if the parent is not found or the password is incorrect
     */
    @Override
    @Transactional
    @ForLog
    public Child addChild(Long parentId, CreateChildRequest createChildRequest)  {
        Parent parent = findById(parentId);

        //TODO HARDCODE
        AuthUser authUser = (AuthUser) authUserService
                .userDetailsService()
                .loadUserByUsername(createChildRequest.phoneNumber());

        Child child = childMapper.mapCreateChildRequestToChild(createChildRequest);
        child.setAuthUser(authUser);
        child.setParent(parent);

        return childRepository.save(child);
    }

    @Override
    public Parent findByPhoneNumber(String phoneNumber) {
        return parentRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ParentNotFoundException("Parent with phone number: " + phoneNumber + " not found"));
    }

    @Override
    public Parent findById(Long id) {
        return parentRepository.findById(id)
                .orElseThrow(() -> new ParentNotFoundException("Parent with id: " + id + " not found"));
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
        findById(parentId);
        return childRepository.findAllByParentId(parentId);
    }

    @Override
    @ForLog
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean enrollChildToCourse(EnrollmentActionDto enrollmentActionDto) {
        Parent parent = findById(enrollmentActionDto.parentId());

        Child child = childRepository.findById(enrollmentActionDto.childId())
                .orElseThrow(() -> new ChildNotFoundException("Child with id: " + enrollmentActionDto.childId() + " not found"));

        Course course = courseRepository.findById(enrollmentActionDto.courseId())
                .orElseThrow(() -> new CourseNotFoundException("Course with id: " + enrollmentActionDto.courseId() + "not found"));

         boolean isParentsChild = child.getParent().equals(parent);

        if (!isParentsChild) {
            throw new ChildNotBelongToParentException("Child does not belong to the specified parent");
        }

        boolean isPaid = paymentService.payForCourse(parent, child, course);

        if (isPaid)
            return courseService.enrollChild(enrollmentActionDto);
        else
            throw new InsufficientFundsException("Failed to pay for course");
    }

    @Override
    @ForLog
    public boolean unenrollChildFromCourse(EnrollmentActionDto enrollmentActionDto) {
        findById(enrollmentActionDto.parentId());

        Child child = childRepository.findById(enrollmentActionDto.childId())
                .orElseThrow(() -> new ChildNotFoundException("Child with id: " + enrollmentActionDto.childId() + " not found"));

        if (!child.getParent().getId().equals(enrollmentActionDto.parentId())) {
            log.error("Child ID {} does not belong to Parent ID {}", enrollmentActionDto.childId(), enrollmentActionDto.parentId());
            throw new ChildNotBelongToParentException("Child does not belong to the specified parent");
        }

        boolean isEnrolledInCourse = groupRepository.isChildEnrolledInCourseGroup(
                enrollmentActionDto.groupId(),
                enrollmentActionDto.parentId()
        );

        if (isEnrolledInCourse) {
            return groupService.unenrollChild(enrollmentActionDto);
        } else {
            log.warn("Child ID {} is not enrolled in course ID {}",
                    enrollmentActionDto.childId(),
                    enrollmentActionDto.courseId()
            );
            return false;
        }
    }

    /**
     * Adds balance to the parent's account.
     *
     * @param parentId the ID of the {@link Parent} entity
     * @param amountOfMoney the amount to add to the balance
     * @param bankCard the bank card where the balance is replenished
     * @return a success message indicating the updated balance
     * @throws ParentNotFoundException if the parent is not found
     */
    @Override
    @ForLog
    @Transactional(isolation = Isolation.READ_COMMITTED)
        public String addBalance(Long parentId, Integer amountOfMoney, BankCard bankCard) {
        Parent parent = findById(parentId);

        BigDecimal newBalance = parent.getBalance().add(BigDecimal.valueOf(amountOfMoney));
        parent.setBalance(newBalance);
        parentRepository.save(parent);
        return "Balance updated successfully. New balance: " + newBalance;
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
    public Parent updateParent(Long parentId, UpdateParentRequest updatedParent) {
        Parent existingParent = parentRepository.findById(parentId)
                .orElseThrow(() -> new ParentNotFoundException("Parent with id: " + parentId + " not found"));

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
