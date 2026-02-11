package dk.ek.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "teacher")
public class Teacher implements IEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String zoom;
    public Teacher(String name, String email, String zoom) {
        this.name = name;
        this.email = email;
        this.zoom = zoom;
    }
}