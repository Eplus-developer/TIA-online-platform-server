package com.scsse.workflow.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "team")
public class Team  implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@Column(name = "activity_id")
	private Integer activityId;

	@Column(name = "leader_id")
	private Integer leaderId;

	@Column(name = "enroll_id")
	private Integer enrollId;

}
