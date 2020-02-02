package com.scsse.workflow.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "role_access")
public class RoleAccess  implements Serializable {

	@Column(name = "role_id")
	private Integer roleId;

	@Column(name = "access_id")
	private Integer accessId;

}
