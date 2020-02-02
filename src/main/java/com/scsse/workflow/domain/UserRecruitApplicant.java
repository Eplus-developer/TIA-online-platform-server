package com.scsse.workflow.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user_recruit_applicant")
public class UserRecruitApplicant  implements Serializable {

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "recruit_id")
	private Integer recruitId;

	@Column(name = "recruit_result")
	private Integer recruitResult;

}
