package com.scsse.workflow.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "reply_liker")
public class ReplyLiker  implements Serializable {

	@Column(name = "reply_id")
	private Integer replyId;

	@Column(name = "liker_id")
	private Integer likerId;

}
