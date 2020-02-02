package com.scsse.workflow.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user_activity")
public class UserActivity  implements Serializable {

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "activity_id")
	private Integer activityId;

	@Column(name = "activity_type")
	private Integer activityType;

}
