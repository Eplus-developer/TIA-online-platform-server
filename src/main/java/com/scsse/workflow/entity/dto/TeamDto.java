package com.scsse.workflow.entity.dto;

import com.scsse.workflow.entity.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Alfred Fu
 * Created on 2019/10/6 6:55 下午
 */
@Getter
@Setter
@NoArgsConstructor
public class TeamDto {
    private Integer teamId;
    private String teamName;
    private String teamDescription;
    private String leaderName;
    private Set<UserDto> members;
    private Integer memberNum;
    private String activityName;
}
