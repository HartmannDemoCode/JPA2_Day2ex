package dk.ek.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.HashSet;
import java.util.Set;

public class RetrieveDAO implements IRetrieveDAO {
    private IDAO<Course> courseDAO;
    private IDAO<Student> studentDAO;
    private IDAO<Teacher> teacherDAO;
    private EntityManagerFactory emf;

    public RetrieveDAO(EntityManagerFactory emf) {
        this.courseDAO = new CourseDAO(emf);
        this.studentDAO = new StudentDAO(emf);
        this.teacherDAO = new TeatherDAO(emf);
        this.emf = emf;
    }

    @Override
    public Set<Student> getAllStudentsInCourse(Long courseId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.course.id = :courseId", Student.class);
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
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.course.teacher.id = :teacherId", Student.class);
            query.setParameter("teacherId", teacherId);
            return new HashSet(query.getResultList());
        }
    }

    @Override
    public Set<Teacher> getAllTeachersForCourse(String courseName) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Teacher> query = em.createQuery("SELECT t FROM Course c JOIN c.teacher t  WHERE c.courseName = :courseName", Teacher.class);
            query.setParameter("courseName", courseName);
            return new HashSet(query.getResultList());
        }
    }
}
