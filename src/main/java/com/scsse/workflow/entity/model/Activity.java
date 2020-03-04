package com.scsse.workflow.entity.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name = "activity")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
    private Integer id;
	
    @Column
    private String name;

    /**
     * 活动时间
     */
    @JsonFormat( pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @Column(name = "act_time")
    private Date actTime;

    /**
     * 活动发布时间
     */
    @JsonFormat( pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @Column(name = "publish_time")
    private Date publishTime;

    /**
     * 报名截止时间
     */
    @JsonFormat( pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @Column(name = "end_time")
    private Date endTime;
	
    @Column
    private String description;

    /**
     * 活动发起人id
     */
    @ManyToOne
    @JoinColumn(name = "promoter_id")
    private User promoter;

    /**
     * 活动类型（0->比赛 1->学生课程）
     */
    @Column(name = "activity_type")
    private String activityType;

    /**
     * 活动地点
     */
	@Column
    private String location;

    /**
     * 联系电话
     */
	@Column
    private String phone;

    /**
     * 比赛类型：个人赛（false）、团队赛（true）
     */
    @Column(name = "quantity_type")
    private Boolean quantityType;

    /**
     * 图片url
     */
    @Column
    private String image;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JsonBackReference(value = "activity.activityTags")
    @JoinTable(name = "activity_tag",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> activityTags = new HashSet<>();
    
    @OneToMany(mappedBy = "activity",fetch=FetchType.EAGER)
	@JsonBackReference(value = "activity.teams")
    private Set<Team> teams = new HashSet<>();
    
    @OneToMany(mappedBy = "activity",fetch=FetchType.EAGER)
    @JsonBackReference(value = "activity.recruits")
    private Set<Recruit> recruits = new HashSet<>();
    
    @ManyToMany(mappedBy = "joinActivities",fetch=FetchType.EAGER)
    @JsonBackReference(value = "joinActivities.participants")
    Set<User> participants = new HashSet<>();
    
    @ManyToMany(mappedBy = "followActivities",fetch=FetchType.EAGER)
    @JsonBackReference(value = "followActivities.followers")
    Set<User> followers = new HashSet<>();

    public Activity(String activityName, Date activityTime, String activityPlace, String activityDescription, Date activitySignUpDeadline) {
        this.name = activityName;
        this.actTime = activityTime;
        this.location = activityPlace;
        this.description = activityDescription;
        this.endTime = activitySignUpDeadline;
    }

    public Activity(String activityName) {
        this.name = activityName;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Activity)) return false;
        Activity activity = (Activity) o;
        return getId() == activity.getId() &&
                Objects.equals(getName(), activity.getName()) &&
                Objects.equals(getActTime(), activity.getActTime()) &&
                Objects.equals(getLocation(), activity.getLocation()) &&
                Objects.equals(getDescription(), activity.getDescription()) &&
                Objects.equals(getEndTime(), activity.getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getActTime(), getLocation(), getDescription(), getEndTime());
    }
}