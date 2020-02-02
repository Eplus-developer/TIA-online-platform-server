package com.scsse.workflow.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user_follower")
public class UserFollower  implements Serializable {

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "follower_id")
	private Integer followerId;

}
