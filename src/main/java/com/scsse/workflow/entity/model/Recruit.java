package com.scsse.workflow.entity.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@NoArgsConstructor
@Table(name = "recruit")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
public class Recruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "create_time")
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Column(name = "recruit_description")
    private String recruitDescription;

    @Column(name = "recruit_name")
    private String recruitName;

    /**
     * 招聘职位
     */
    @Column(name = "recruit_position")
    private String recruitPosition;

    /**
     * 已招人数
     */
    @Column(name = "recruit_registered_number")
    private Integer recruitRegisteredNumber;

    @Column(name = "recruit_state")
    private String recruitState;

    /**
     * 待招人数
     */
    @Column(name = "recruit_willing_number")
    private Integer recruitWillingNumber;

    /**
     * 所属活动的id
     */
    @ManyToOne
    @JoinColumn(name = "activity_id")
//    @JsonBackReference(value = "recruit.activity")
    private Activity activity;

    /**
     * 招聘创建者的id
     */
    @ManyToOne
    @JoinColumn(name = "creator_id")
//    @JsonBackReference(value = "recruit.creator")
    private User creator;

    /**
     * 进行招聘的团队的id
     */
    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonBackReference(value = "recruit.team")
    private Team team;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonBackReference(value = "recruit.recruitTags")
    @JoinTable(name = "recruit_tag",
            joinColumns = @JoinColumn(name = "recruit_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> recruitTags = new HashSet<>();

    @ManyToMany(mappedBy = "followRecruits")
    @JsonBackReference(value = "recruit.followers")
    private Set<User> followers = new HashSet<>();
    
    @ManyToMany(mappedBy = "applyRecruits" , cascade = CascadeType.REMOVE)
    @JsonBackReference(value = "recruit.applicants")
    private Set<User> applicants = new HashSet<>();

    @ManyToMany
    @JsonBackReference(value = "recruit.participants")
    @JoinTable(name = "recruit_member",
            joinColumns = @JoinColumn(name = "recruit_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> participants = new HashSet<>();

//    @ManyToMany(mappedBy = "successRecruits")
//    @JsonBackReference(value = "recruit.participants")
//    private Set<User> participants = new HashSet<>();

    public Recruit(String recruitName, Activity activity) {
        this.recruitName = recruitName;
        this.activity = activity;
    }

    public Recruit(String recruitName, String recruitPosition, String recruitDescription, String recruitState, int recruitWillingNumber, int recruitRegisteredNumber, User manager, Activity activity) {
        this.recruitName = recruitName;
        this.recruitPosition = recruitPosition;
        this.recruitDescription = recruitDescription;
        this.recruitState = recruitState;
        this.recruitWillingNumber = recruitWillingNumber;
        this.recruitRegisteredNumber = recruitRegisteredNumber;
        this.creator = manager;
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "Recruit{" +
                "id=" + id +
                ", recruitDescription='" + recruitDescription + '\'' +
                ", recruitName='" + recruitName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recruit recruit = (Recruit) o;
        return Objects.equals(id, recruit.id) &&
                Objects.equals(createTime, recruit.createTime) &&
                Objects.equals(recruitDescription, recruit.recruitDescription) &&
                Objects.equals(recruitName, recruit.recruitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createTime, recruitDescription, recruitName);
    }
}