package dk.ek.persistence;

import java.util.Set;

public interface IRetrieveDAO {
    Set<Student> getAllStudentsInCourse(Long courseId);
    Set<Course> getAllCoursesForTeacher(Long teacherId);
    Set<Student> getAllStudentsForTeacher(Long teacherId);
    Set<Teacher> getAllTeachersForCourse(String courseName);

}
