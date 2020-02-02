package com.scsse.workflow.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "topic")
public class Topic  implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String title;

	private String content;

	@Column(name = "like_count")
	private Integer likeCount;

	@Column(name = "reply_count")
	private Integer replyCount;

	@Column(name = "author_id")
	private Integer authorId;

	@Column(name = "create_time")
	private java.util.Date createTime;

}
