package com.scsse.workflow.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "topic_liker")
public class TopicLiker  implements Serializable {

	@Column(name = "topic_id")
	private Integer topicId;

	@Column(name = "liker_id")
	private Integer likerId;

}
