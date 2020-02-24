package com.scsse.workflow.entity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name = "user")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    /**
     * 用户名
     */
    @Column
    private String name;
    @Column
    private String password;
    @Column
    private String salt;

    /**
     * 头像
     */
    @Column(name = "face_image")
    private String faceImage;
  
    @Column
    private String email;

    /**
     * 添加时间
     */
	@JsonFormat( pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @Column(name = "add_time")
    private Date addTime;

    /**
     * 最后登录时间
     */
	@JsonFormat( pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @Column(name = "last_login_date")
    private Date lastLoginDate;

    /**
     * 所属学院
     */
    @Column
    private String college;

    /**
     * 性别
     */
    @Column
    private Boolean gender;

    @Column(name = "open_id")
    private String openId;

    /**
     * 年级
     */
    @Column
    private String grade;

    /**
     * 学号
     */
    @Column(name = "stu_number")
    private String stuNumber;

    /**
     * 电话号码
     */
    @Column
    private String phone;

    /**
     * 用户简历（图片）
     */
    @Column
    private String resume;

    /**
     * 特长
     */
    @Column
    private String specialty;

    @Column(name = "wx_id")
    private String wxId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonBackReference(value = "user.userTags")
    @JoinTable(name = "user_tag",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> userTags = new HashSet<>();

    @ManyToMany(fetch=FetchType.EAGER)
	@JsonBackReference(value = "user.roles")
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
    
    @ManyToMany
    @JsonBackReference(value = "user.topics")
    @JoinTable(name = "topic_liker",joinColumns = @JoinColumn(name = "liker_id"),inverseJoinColumns = @JoinColumn(name = "topic_id"))
    private Set<Topic> topics = new HashSet<>();
    
    @ManyToMany
    @JsonBackReference(value = "user.replys")
    @JoinTable(name = "reply_liker",joinColumns = @JoinColumn(name = "liker_id"),inverseJoinColumns = @JoinColumn(name = "reply_id"))
    private Set<Reply> replys = new HashSet<>();


    @ManyToMany(mappedBy = "members",fetch=FetchType.EAGER)
    @JsonBackReference(value = "user.joinedTeam")
    private Set<Team> joinedTeam = new HashSet<>();
    
    @ManyToMany
    @JsonBackReference(value = "user.joinActivities")
    @JoinTable(name = "user_activity",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id"))
    private Set<Activity> joinActivities = new HashSet<>();

    @ManyToMany(mappedBy = "students")
    @JsonBackReference(value = "user.joinCourses")
    private Set<Course> joinCourses = new HashSet<>();

    @ManyToMany(fetch=FetchType.EAGER)
    @JsonBackReference(value = "user.followUsers")
    @JoinTable(name = "user_follower",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private Set<User> followUsers = new HashSet<>();
    
    @ManyToMany(fetch=FetchType.EAGER)
    @JsonBackReference(value = "user.followActivities")
    @JoinTable(name = "user_activity_follower",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id"))
    private Set<Activity> followActivities = new HashSet<>();

    @ManyToMany
    @JsonBackReference(value = "user.followCourses")
    @JoinTable(name = "user_course_follower",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> followCourses = new HashSet<>();

    @ManyToMany(fetch=FetchType.EAGER)
    @JsonBackReference(value = "user.followRecruits")
    @JoinTable(name = "user_recruit_follower",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recruit_id"))
    private Set<Recruit> followRecruits = new HashSet<>();

    @ManyToMany(fetch=FetchType.EAGER)
    @JsonBackReference(value = "user.applyRecruits")
    @JoinTable(name = "user_recruit_applicant",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recruit_id"))
    private Set<Recruit> applyRecruits = new HashSet<>();

//    @ManyToMany
//    @JsonBackReference(value = "user.successRecruits")
//    @JoinTable(name = "recruit_member",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "recruit_id"))
//    private Set<Recruit> successRecruits = new HashSet<>();

    @ManyToMany(mappedBy = "participants",fetch=FetchType.EAGER)
    @JsonBackReference(value = "user.successRecruits")
    private Set<Recruit> successRecruits = new HashSet<>();

    public User(String username, String openid) {
        this.name = username;
        this.openId = openid;
    }
    
    public User(String openid) {
        this.openId = openid;
        this.name = openid;

        this.password =  "123456";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getStuNumber(), user.getStuNumber()) &&
                Objects.equals(getGrade(), user.getGrade()) &&
                Objects.equals(getPhone(), user.getPhone()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getSpecialty(), user.getSpecialty()) &&
                Objects.equals(getResume(), user.getResume()) &&
                Objects.equals(getOpenId(), user.getOpenId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getStuNumber(), getStuNumber(), getPhone(), getEmail(),
        		getSpecialty(), getResume(), getOpenId());
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", openId='" + openId + '\'' +
                '}';
    }
}