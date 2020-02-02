package com.scsse.workflow.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "reply")
public class Reply  implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "topic_id")
	private Integer topicId;

	private String content;

	@Column(name = "author_id")
	private Integer authorId;

	@Column(name = "create_time")
	private java.util.Date createTime;

	@Column(name = "replyee_id")
	private String replyeeId;

}
