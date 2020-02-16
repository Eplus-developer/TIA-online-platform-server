package com.scsse.workflow.service;

import com.scsse.workflow.entity.dto.TeamDto;
import com.scsse.workflow.entity.dto.UserDto;
import com.scsse.workflow.entity.model.Team;

import java.util.List;

/**
 * @author Alfred Fu
 * Created on 2019/10/6 6:52 下午
 */
public interface TeamService {

    TeamDto getTeam(Integer teamId);

    Team findTeam(Integer teamId);

    List<Team> findAllTeams();

    TeamDto createTeam(Team team);

    TeamDto updateTeam(Team team) throws Exception;

    void deleteTeam(Integer teamId);

    List<UserDto> getTeamMembers(Integer teamId);
}
