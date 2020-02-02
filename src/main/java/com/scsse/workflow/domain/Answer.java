package com.scsse.workflow.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "answer")
public class Answer  implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "question_id")
	private Integer questionId;

	private String content;

	@Column(name = "isRight")
	private Integer isright;

}
