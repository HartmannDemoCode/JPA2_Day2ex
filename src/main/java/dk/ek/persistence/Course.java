package dk.ek.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "course")
public class Course implements IEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private CourseName courseName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne
    private Teacher teacher;
    public Course(CourseName courseName, String description, LocalDate startDate, LocalDate endDate, Teacher teacher) {
        this.courseName = courseName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teacher = teacher;
    }
}