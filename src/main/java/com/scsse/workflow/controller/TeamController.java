package com.scsse.workflow.controller;

import com.scsse.workflow.entity.model.Team;
import com.scsse.workflow.service.TeamService;
import com.scsse.workflow.service.UserService;
import com.scsse.workflow.util.dao.UserUtil;
import com.scsse.workflow.util.result.Result;
import com.scsse.workflow.util.result.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Alfred Fu
 * Created on 2019/10/6 3:11 下午
 */
@RestController
public class TeamController {

    private final UserUtil userUtil;

    private final TeamService teamService;

    private final UserService userService;

    @Autowired
    public TeamController(UserUtil userUtil, TeamService teamService, UserService userService) {
        this.userUtil = userUtil;
        this.teamService = teamService;
        this.userService = userService;
    }

    /**
     * 获取我加入的团队
     *
     * @return List{TeamDto}
     */
    @GetMapping("/team/joinedTeam")
    @RequiresRoles("admin")
    public Result getJoinTeam() {
        return ResultUtil.success(
                userService.findJoinedTeam(userUtil.getLoginUser()));
    }

    /**
     * 获取团队成员
     *
     * @param teamId teamId
     * @return List{User}
     */
    @GetMapping("/team/{teamId}/members")
    public Result getColleague(@PathVariable Integer teamId) {
        return ResultUtil.success(
                teamService.getTeamMembers(teamId)
        );
    }

    /**
     * 获取所有团队
     *
     *
     * @return List{TeamDto}
     */
    @GetMapping("/team/all")
    public Result getAllTeam(){
        return ResultUtil.success(
                teamService.findAllTeams()
        );
    }

    @GetMapping("/team/{teamId}")
    public Result getTeam(@PathVariable Integer teamId) {
        return ResultUtil.success(
                teamService.getTeam(teamId)
        );
    }

    @PutMapping("/team/{teamId}")
    public Result updateTeam(@PathVariable Integer teamId, @RequestBody Team team) throws Exception {
        team.setId(teamId);
        return ResultUtil.success(
                teamService.updateTeam(team)
        );
    }

    @PostMapping("/team")
    public Result createTeam(@RequestBody Team team) {
        return ResultUtil.success(
                teamService.createTeam(team)
        );
    }

    @DeleteMapping("/team/{teamId}")
    public Result deleteTeam(@PathVariable Integer teamId) {
        teamService.deleteTeam(teamId);
        return ResultUtil.success();
    }


}
