package com.scsse.workflow.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user")
public class User  implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String password;

	private String salt;

	@Column(name = "face_image")
	private String faceImage;

	private String email;

	@Column(name = "add_time")
	private java.util.Date addTime;

	@Column(name = "last_login_date")
	private java.util.Date lastLoginDate;

	private String college;

	private Integer gender;

	@Column(name = "open_id")
	private String openId;

	private String grade;

	@Column(name = "stu_number")
	private String stuNumber;

	private String phone;

	private String resume;

	private String specialty;

	@Column(name = "wx_id")
	private String wxId;

}
