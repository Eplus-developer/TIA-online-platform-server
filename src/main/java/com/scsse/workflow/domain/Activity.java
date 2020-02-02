package com.scsse.workflow.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "activity")
public class Activity  implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@Column(name = "act_time")
	private Date actTime;

	@Column(name = "publish_time")
	private Date publishTime;

	@Column(name = "end_time")
	private Date endTime;

	private String description;

	@Column(name = "promoter_id")
	private Integer promoterId;

	@Column(name = "activity_type")
	private Integer activityType;

	private String location;

	private String phone;

	@Column(name = "quantity_type")
	private Integer quantityType;

}
