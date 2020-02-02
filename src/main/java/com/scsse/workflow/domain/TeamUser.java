package com.scsse.workflow.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "team_user")
public class TeamUser  implements Serializable {

	@Column(name = "team_id")
	private Integer teamId;

	@Column(name = "user_id")
	private Integer userId;

}
