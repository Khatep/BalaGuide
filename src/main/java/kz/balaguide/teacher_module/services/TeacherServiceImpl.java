package kz.balaguide.teacher_module.services;

import kz.balaguide.auth_module.services.AuthUserService;
import kz.balaguide.common_module.core.entities.AuthUser;
import kz.balaguide.common_module.core.entities.EducationCenter;
import kz.balaguide.common_module.core.entities.Teacher;
import kz.balaguide.common_module.core.exceptions.buisnesslogic.alreadyexists.UserAlreadyExistsException;
import kz.balaguide.education_center_module.repository.EducationCenterRepository;
import kz.balaguide.teacher_module.dto.CreateTeacherRequest;
import kz.balaguide.teacher_module.mappers.TeacherMapper;
import kz.balaguide.teacher_module.repositories.TeacherRepository;
import kz.balaguide.teacher_module.utils.QrCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final EducationCenterRepository educationCenterRepository;
    private final TeacherMapper teacherMapper;
    private final QrCodeUtil qrCodeUtil;

    private final AuthUserService authUserService;
    @Override
    public Teacher createTeacher(CreateTeacherRequest createTeacherRequest) {
        if (teacherRepository.existsByEmail(createTeacherRequest.email())) {
            log.warn("Teacher with email: {} already exists", createTeacherRequest.email());
            throw new UserAlreadyExistsException("Teacher with email: " + createTeacherRequest.email() + " already exists");
        }

        if (teacherRepository.existsByPhoneNumber(createTeacherRequest.phoneNumber())) {
            log.warn("Teacher with phoneNumber: {} already exists", createTeacherRequest.phoneNumber());
            throw new UserAlreadyExistsException("Teacher with phoneNumber: " + createTeacherRequest.phoneNumber() + " already exists");
        }

        Teacher teacher = teacherMapper.mapCreateTeacherRequestToTeacher(createTeacherRequest);
        AuthUser authUser = (AuthUser) authUserService
                .userDetailsService()
                .loadUserByUsername(createTeacherRequest.phoneNumber());
        teacher.setAuthUser(authUser);

        EducationCenter educationCenter = educationCenterRepository.findById(createTeacherRequest.educationCenterId())
                .orElseThrow(() -> new IllegalArgumentException("Education Center not found"));

        teacher.setEducationCenter(educationCenter);

        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher findTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow();
    }

    @Override
    public List<Teacher> findAllTeachersByEducationCenterId(Long educationCenterId) {
        return teacherRepository.findAllByEducationCenterId(educationCenterId);
    }

    @Override
    public String generateQrCodeForLesson(Long lessonId) {
        String attendanceUrl = "https://balaguide.kz/attendance/" + lessonId; // или ваш домен
        return qrCodeUtil.generateBase64QrCode(attendanceUrl);
    }

    @Override
    public String getExistingQrCode(Long lessonId) {
        // если QR код уже был сгенерен и сохранён — возвращай его, иначе генерируй
        return generateQrCodeForLesson(lessonId);
    }

}
