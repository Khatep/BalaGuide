package kz.balaguide.education_center_module.repository;

import kz.balaguide.common_module.core.entities.Course;
import kz.balaguide.common_module.core.entities.EducationCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface EducationCenterRepository extends JpaRepository<EducationCenter, Long> {

    Optional<EducationCenter> findByEmail(String email);

    Optional<EducationCenter> findByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    // 1. Общая сумма оплат (выручка)
    @Query(value = """
                SELECT COALESCE(SUM(p.amount), 0)
                FROM payments p
                JOIN courses c ON p.course_id = c.id
                WHERE c.education_center_id = :centerId AND p.payment_status = 'PAID'
            """, nativeQuery = true)
    Double calculateTotalRevenue(@Param("centerId") Long centerId);

    // 2. Топ-5 курсов по выручке
    @Query(value = """
                SELECT c.course_name AS courseName, SUM(p.amount) AS revenue
                FROM payments p
                JOIN courses c ON p.course_id = c.id
                WHERE c.education_center_id = :centerId AND p.payment_status = 'PAID'
                GROUP BY c.course_name
                ORDER BY revenue DESC
                LIMIT :limit
            """, nativeQuery = true)
    List<Map<String, Object>> getTopCoursesByRevenue(@Param("centerId") Long centerId, @Param("limit") int limit);

    // 3. Распределение детей по курсам
    @Query(value = """
                SELECT c.course_name AS courseName, COUNT(DISTINCT cg.child_id) AS childrenCount
                FROM child_group cg
                JOIN groups g ON cg.group_id = g.id
                JOIN courses c ON g.course_id = c.id
                WHERE c.education_center_id = :centerId
                GROUP BY c.course_name
            """, nativeQuery = true)
    List<Map<String, Object>> getChildrenDistributionByCourse(@Param("centerId") Long centerId);

    // 4. Оплаты по месяцам
    @Query(value = """
                SELECT TO_CHAR(p.payment_time, 'YYYY-MM') AS month, SUM(p.amount) AS revenue
                FROM payments p
                JOIN courses c ON p.course_id = c.id
                WHERE c.education_center_id = :centerId AND p.payment_status = 'PAID'
                GROUP BY month
                ORDER BY month
            """, nativeQuery = true)
    List<Map<String, Object>> getMonthlyRevenue(@Param("centerId") Long centerId);

    // 5. Рост количества детей по месяцам
    @Query(value = """
                SELECT TO_CHAR(cg.created_date, 'YYYY-MM') AS month, COUNT(DISTINCT ch.id) AS childrenCount
                FROM children ch
                JOIN child_group cg ON ch.id = cg.child_id
                JOIN groups g ON cg.group_id = g.id
                JOIN courses c ON g.course_id = c.id
                WHERE c.education_center_id = :centerId
                GROUP BY month
                ORDER BY month
            """, nativeQuery = true)
    List<Map<String, Object>> getMonthlyChildrenGrowth(@Param("centerId") Long centerId);

    // 6. Средняя длительность курса
    @Query(value = """
                SELECT AVG(c.durability_by_weeks) 
                FROM courses c
                WHERE c.education_center_id = :centerId
            """, nativeQuery = true)
    Double calculateAverageCourseDuration(@Param("centerId") Long centerId);

    // 7. Заполняемость групп (средний %)
    @Query(value = """
                SELECT AVG((g.current_participants * 100.0) / g.max_participants)
                FROM groups g
                JOIN courses c ON g.course_id = c.id
                WHERE c.education_center_id = :centerId
            """, nativeQuery = true)
    Double calculateAverageGroupFillPercent(@Param("centerId") Long centerId);

    // 8. Кол-во родителей с повторными оплатами
    @Query(value = """
                SELECT COUNT(*) FROM (
                    SELECT p.parent_id
                    FROM payments p
                    JOIN courses c ON p.course_id = c.id
                    WHERE c.education_center_id = :centerId AND p.payment_status = 'PAID'
                    GROUP BY p.parent_id
                    HAVING COUNT(p.id) > 1
                ) AS subquery
            """, nativeQuery = true)
    Integer countReturningParents(@Param("centerId") Long centerId);

    @Query(value = """
            SELECT *
            FROM courses
            WHERE education_center_id = :educationalCenterId
            ORDER BY created_date DESC
            """,
            nativeQuery = true)
    List<Course> getAllCoursesByEducationalCenterId(Long educationalCenterId);
}
