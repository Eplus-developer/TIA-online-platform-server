package com.scsse.workflow.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "recruit")
public class Recruit  implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "create_time")
	private java.util.Date createTime;

	@Column(name = "recruit_description")
	private String recruitDescription;

	@Column(name = "recruit_name")
	private String recruitName;

	@Column(name = "recruit_position")
	private String recruitPosition;

	@Column(name = "recruit_registered_number")
	private Integer recruitRegisteredNumber;

	@Column(name = "recruit_state")
	private String recruitState;

	@Column(name = "recruit_willing_number")
	private Integer recruitWillingNumber;

	@Column(name = "activity_id")
	private Integer activityId;

	@Column(name = "creator_id")
	private Integer creatorId;

	@Column(name = "team_id")
	private Integer teamId;

}
