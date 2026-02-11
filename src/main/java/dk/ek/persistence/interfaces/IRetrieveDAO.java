package dk.ek.persistence.interfaces;

import dk.ek.persistence.model.Course;
import dk.ek.persistence.model.EnrollmentStatus;
import dk.ek.persistence.model.Student;
import dk.ek.persistence.model.Teacher;

import java.util.List;
import java.util.Set;

public interface IRetrieveDAO {

    /**
     * Return all courses taught by a specific teacher.
     */
    Set<Course> getAllCoursesForTeacher(Long teacherId);

    /**
     * Return all teachers for a given course name.
     */
    Set<Teacher> getAllTeachersForCourse(String courseName);

    /**
     * Return all students enrolled in a specific course.
     */
    Set<Student> getAllStudentsInCourse(Long courseId);

    /**
     * Return all students taught by a given teacher.
     */
    Set<Student> getAllStudentsForTeacher(Long teacherId);


    // ------------------------------------------------------------
    // INTERMEDIATE (Collection joins required)
    // ------------------------------------------------------------

    /**
     * Return all courses in which a student is enrolled.
     */
    Set<Course> getAllCoursesForStudent(Long studentId);

    /**
     * Return all students with a specific enrollment status.
     */
    Set<Student> getAllStudentsByEnrollmentStatus(EnrollmentStatus status);

    /**
     * Return all courses with at least one student having a specific email domain.
     */
    Set<Course> getCoursesWithStudentEmailDomain(String domain);


    // ------------------------------------------------------------
    // ADVANCED (JOIN + GROUP BY + HAVING + LEFT JOIN)
    // ------------------------------------------------------------

    /**
     * Return all courses with no active students.
     */
    Set<Course> getCoursesWithNoActiveStudents();

    /**
     * Return teachers with at least N active students.
     */
    Set<Teacher> getTeachersWithAtLeastNStudents(long minStudents);

    /**
     * Return the top N courses ordered by number of active students.
     */
    List<Course> getTopCoursesByActiveStudents(int limit);
}
