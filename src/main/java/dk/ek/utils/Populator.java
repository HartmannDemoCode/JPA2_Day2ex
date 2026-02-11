package dk.ek.utils;

import dk.ek.persistence.config.HibernateConfig;
import dk.ek.persistence.daos.CourseDAO;
import dk.ek.persistence.daos.StudentDAO;
import dk.ek.persistence.daos.TeatherDAO;
import dk.ek.persistence.interfaces.IDAO;
import dk.ek.persistence.interfaces.IEntity;
import dk.ek.persistence.model.*;

import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.Map;

public class Populator {

    private final EntityManagerFactory emf;
    private final IDAO<Student> studentDAO;
    private final IDAO<Teacher> teacherDAO;
    private final IDAO<Course> courseDAO;

    public Populator(EntityManagerFactory emf) {
        this.emf = emf;
        this.studentDAO = new StudentDAO(emf);
        this.teacherDAO = new TeatherDAO(emf);
        this.courseDAO = new CourseDAO(emf);
    }

    public Map<String, IEntity> populate() {

        // ---------- Teachers ----------
        Teacher teacher1 = new Teacher("Abraham", "abe@college.com", "zoom.com/abe");
        Teacher teacher2 = new Teacher("Benedict", "bene@college.com", "zoom.com/bene");

        teacherDAO.create(teacher1);
        teacherDAO.create(teacher2);

        // ---------- Courses ----------
        LocalDate start = LocalDate.of(2026, 2, 1);
        LocalDate end = start.plusMonths(6);

        Course course1 = new Course(CourseName.MATH, "Mathematics course", start, end, teacher1);
        Course course2 = new Course(CourseName.ART, "Art course", start, end, teacher2);

        courseDAO.create(course1);
        courseDAO.create(course2);

        // ---------- Students ----------
        Student student1 = new Student("Charlie", "char@college.com");
        Student student2 = new Student("Diana", "dian@college.com");
        Student student3 = new Student("Edward", "edw@college.com");
        Student student4 = new Student("Fiona", "fion@college.com");
        Student student5 = new Student("George", "geo@college.com");
        Student student6 = new Student("Hansi", "han@college.com");

        studentDAO.create(student1);
        studentDAO.create(student2);
        studentDAO.create(student3);
        studentDAO.create(student4);
        studentDAO.create(student5);
        studentDAO.create(student6);

        // ---------- Enrollments ----------
        // course1: 2 students
        enroll(student1, course1, EnrollmentStatus.ACTIVE);
        enroll(student2, course1, EnrollmentStatus.ACTIVE);

        // course2: 4 students
        enroll(student3, course2, EnrollmentStatus.ACTIVE);
        enroll(student4, course2, EnrollmentStatus.ACTIVE);
        enroll(student5, course2, EnrollmentStatus.DROPPED); // good for LEFT JOIN exercise
        enroll(student6, course2, EnrollmentStatus.ACTIVE);
        courseDAO.update(course1);
        courseDAO.update(course2);

        return Map.of(
                "teacher1", teacher1,
                "teacher2", teacher2,
                "course1", course1,
                "course2", course2,
                "student1", student1,
                "student2", student2,
                "student3", student3,
                "student4", student4,
                "student5", student5,
                "student6", student6
        );
    }

    private void enroll(Student student, Course course, EnrollmentStatus status) {

        Enrollment enrollment = new Enrollment(student, course, status);

        // Important: maintain both sides
        student.addEnrollment(enrollment);
        course.addEnrollment(enrollment);

        // Because of CascadeType.ALL on Course/Student → Enrollment
        // enrollment will be persisted automatically when flushed
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        Populator populator = new Populator(emf);
        populator.populate().forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
