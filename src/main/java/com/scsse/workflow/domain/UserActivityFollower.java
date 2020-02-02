package com.scsse.workflow.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user_activity_follower")
public class UserActivityFollower  implements Serializable {

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "activity_id")
	private Integer activityId;

}
