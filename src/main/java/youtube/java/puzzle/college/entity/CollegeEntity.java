package youtube.java.puzzle.college.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "college")
public class CollegeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collegeId")
    int id;
    @Column(name = "collegeName")
    String name;
    @Column(name = "collegeLocation")
    String location;
}
