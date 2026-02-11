package dk.ek.dtos;

import dk.ek.persistence.model.CourseName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClassWithTeacherDTO {
    private Long courseId;
    private CourseName courseName;
    private String teacher;
    private String email;
}
