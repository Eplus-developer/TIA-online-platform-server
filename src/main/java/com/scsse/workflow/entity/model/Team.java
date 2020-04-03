package com.scsse.workflow.entity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "team")
//@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    /**
     * 团队名称
     */
    @Column
    private String name;

    @Column
    private String description;

    @OneToOne
    @JoinColumn(name = "graph_id")
    private Graph graph;
   
    /**
     * 队长id
     */
    @ManyToOne
    @JoinColumn(name = "leader_id")
//    @JsonBackReference(value="team.leader")
    private User leader;

    @ManyToOne
    @JoinColumn(name = "activity_id")
//    @JsonBackReference(value="team.activity")
    private Activity activity;
    
    @ManyToMany
    @JoinTable(
            name = "team_user",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonBackReference(value = "team.members")
    private Set<User> members = new HashSet<>();
  
//    @OneToMany(mappedBy = "team")
//    @JsonBackReference(value = "team.recruits")
//    private Set<Recruit> recruits = new HashSet<>();
    
    @Column(name = "enroll_id")
    private String enroll_id;

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}