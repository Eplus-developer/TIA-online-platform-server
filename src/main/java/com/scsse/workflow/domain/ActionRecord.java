package com.scsse.workflow.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "action_record")
public class ActionRecord  implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String content;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "team_id")
	private Integer teamId;

	@Column(name = "user_id")
	private Integer userId;

}
