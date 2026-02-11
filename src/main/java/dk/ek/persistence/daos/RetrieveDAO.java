package dk.ek.persistence.daos;

import dk.ek.persistence.interfaces.IDAO;
import dk.ek.persistence.interfaces.IRetrieveDAO;
import dk.ek.persistence.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RetrieveDAO implements IRetrieveDAO {
    private EntityManagerFactory emf;

    public RetrieveDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Set<Student> getAllStudentsInCourse(Long courseId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s JOIN s.enrollments e WHERE e.course.id = :courseId", Student.class);
            query.setParameter("courseId", courseId);
            return new HashSet(query.getResultList());
        }
    }

    @Override
    public Set<Course> getAllCoursesForTeacher(Long teacherId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Course> query = em.createQuery("SELECT c FROM Course c WHERE c.teacher.id = :teacherId", Course.class);
            query.setParameter("teacherId", teacherId);
            return new HashSet(query.getResultList());
        }
    }

    @Override
    public Set<Student> getAllStudentsForTeacher(Long teacherId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s Join s.enrollments e WHERE e.course.teacher.id = :teacherId", Student.class);
            query.setParameter("teacherId", teacherId);
            return new HashSet(query.getResultList());
        }
    }

    @Override
    public Set<Teacher> getAllTeachersForCourse(String courseName) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Teacher> query = em.createQuery("SELECT t FROM Course c JOIN c.teacher t WHERE c.courseName = :courseName", Teacher.class);
            CourseName enumValue = CourseName.valueOf(courseName);
            query.setParameter("courseName", enumValue);
            return new HashSet(query.getResultList());
        }
    }

    @Override
    public Set<Course> getAllCoursesForStudent(Long studentId) {
        return Set.of();
    }

    @Override
    public Set<Student> getAllStudentsByEnrollmentStatus(EnrollmentStatus status) {
        return Set.of();
    }

    @Override
    public Set<Course> getCoursesWithStudentEmailDomain(String domain) {
        return Set.of();
    }

    @Override
    public Set<Course> getCoursesWithNoActiveStudents() {
        return Set.of();
    }

    @Override
    public Set<Teacher> getTeachersWithAtLeastNStudents(long minStudents) {
        return Set.of();
    }

    @Override
    public List<Course> getTopCoursesByActiveStudents(int limit) {
        return List.of();
    }

}
