package com.scsse.workflow.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user_recruit_follower")
public class UserRecruitFollower  implements Serializable {

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "recruit_id")
	private Integer recruitId;

}
