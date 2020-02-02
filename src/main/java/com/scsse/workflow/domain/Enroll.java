package com.scsse.workflow.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "enroll")
public class Enroll  implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "enroll_id")
	private Integer enrollId;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "activity_id")
	private Integer activityId;

	@Column(name = "sign_up_time")
	private java.util.Date signUpTime;

	@Column(name = "quantity_type")
	private Integer quantityType;

	private Integer status;

}
