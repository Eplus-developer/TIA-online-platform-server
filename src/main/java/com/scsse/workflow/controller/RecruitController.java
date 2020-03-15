package com.scsse.workflow.controller;

import com.scsse.workflow.constant.PredicateType;
import com.scsse.workflow.entity.dto.RecruitDto;
import com.scsse.workflow.entity.model.Activity;
import com.scsse.workflow.entity.model.Recruit;
import com.scsse.workflow.entity.model.User;
import com.scsse.workflow.handler.WrongUsageException;
import com.scsse.workflow.service.ActivityService;
import com.scsse.workflow.service.RecruitService;
import com.scsse.workflow.service.TeamService;
import com.scsse.workflow.service.UserService;
import com.scsse.workflow.util.container.Pair;
import com.scsse.workflow.util.dao.UserUtil;
import com.scsse.workflow.util.mvc.QueryParameterBuilder;
import com.scsse.workflow.util.result.Result;
import com.scsse.workflow.util.result.ResultCode;
import com.scsse.workflow.util.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Andrew Dong
 * @date 2019-09-14 15:49
 */
@RestController
public class RecruitController {

    private final UserUtil userUtil;

    private final RecruitService recruitService;
    private final ActivityService activityService;
    private final UserService userService;
    private final TeamService teamService;

    @Autowired
    public RecruitController(UserUtil userUtil, RecruitService recruitService, ActivityService activityService,
                             UserService userService, TeamService teamService) {
        this.userUtil = userUtil;
        this.recruitService = recruitService;
        this.activityService = activityService;
        this.userService = userService;
        this.teamService = teamService;
    }


    /**
     * 分页返回指定时间前(防止新数据写入到数据库时出现bug)的应聘数据
     *
     * @param recruitName     应聘名称(可选, 模糊查询)
     * @param recruitPosition 职位(可选, 严格匹配)
     * @param pageNum         页码，从0开始(可选,默认0)
     * @param pageSize        每页条数(可选,默认10)
     * @param currentTime     指定时间点 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     * @return List{RecruitListDto}
     * 例:
     * url:
     * /recruit/all?recruitPosition=java_backend&currentTime=xxx&pageNum=1  xxx时间点前第2页的职位为后端的应聘数据
     * /recruit/all?recruitName=java?recruitPosition=java_backend&currentTime=xxx&pageNum=0 xxx时间点前第1页的职位为后端的recruitName带java的应聘数据
     * @see RecruitDto 返回详细属性见此类
     */
    @GetMapping("/recruit/all")
    public Result getRecruitList(@RequestParam(required = false) String activityType,
                                 @RequestParam(required = false) String recruitName,
                                 @RequestParam(required = false) String recruitPosition,
                                 @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                 @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                 @RequestParam(required = false) String currentTime) {
        if (currentTime == null || currentTime == "") {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime time = LocalDateTime.now();
            currentTime = df.format(time);
        }
//        HashMap<Integer, Pair<String, String>> requestParam = new QueryParameterBuilder()
//                .addParameter(PredicateType.LIKE, "recruitName", recruitName)
//                .addParameter(PredicateType.EQUAL, "recruitPosition", recruitPosition)
//                .addParameter(PredicateType.TIME_COMPARE, "createTime", currentTime)
//                .build();
//
//        return ResultUtil.success(recruitService.findPaginationRecruitWithCriteria(pageNum, pageSize, requestParam));


        return ResultUtil.success(recruitService.findPaginationRecruitWithCriteria(pageNum, pageSize,
                activityType,null,
                recruitName, recruitPosition, currentTime));

    }

    /**
     * 某条招聘的具体信息
     *
     * @param recruitId recruitId
     * @return RecruitDto
     * 例:
     * url:
     * /recruit/1
     * @see RecruitDto 返回详细属性见此类
     */
    @GetMapping("/recruit/{recruitId}")
    public Result getRecruitDetail(@PathVariable() Integer recruitId) {
        return ResultUtil.success(recruitService.findRecruitById(recruitId));
    }

    /**
     * 获取我发布的所有招聘
     *
     * @return RecruitDto
     */
    @GetMapping("/recruit/all/publish")
    public Result getMyPublishedRecruits(@RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                         @RequestParam(required = false, defaultValue = "10") Integer pageSize
                                     ) throws WrongUsageException {
        Integer userId = userUtil.getLoginUserId();
        return ResultUtil.success(
                recruitService.findPaginationRecruitWithCriteria(pageNum,pageSize,null,userId,null,null,null));
    }


    /**
     * 获取申请loginUser管理招聘的所有用户
     *
     * @return UserAppliedRecruit
     * @throws WrongUsageException USER_NOT_FOUND loginUser not exists
     * @see com.scsse.workflow.entity.dto.UserAppliedRecruit  返回详细属性见此类
     */
    @GetMapping("/recruit/myAppliedUsers")
    public Result getUsersAppliedMyRecruits() throws WrongUsageException {
        return ResultUtil.success(recruitService.findUsersAppliedMyRecruits());
    }

    /**
     * 获取关注该应聘的所有用户
     *
     * @return List{UserDto}
     * 例:
     * url:
     * GET /user/1/followedRecruit
     */
    @GetMapping("/recruit/{recruitId}/followUser")
    public Result getFollowUser(@PathVariable Integer recruitId) {
        return ResultUtil.success(
                recruitService.findAllFollowerOfRecruit(recruitId)
        );
    }

    /**
     * 获取申请该应聘的所有用户
     *
     * @return List{UserDto}
     * 例:
     * url:
     * GET /user/1/followedRecruit
     */
    @GetMapping("/recruit/{recruitId}/applyUser")
    public Result getApplyUser(@PathVariable Integer recruitId) {
        return ResultUtil.success(
                recruitService.findAllApplicantOfRecruit(recruitId)
        );
    }

    /**
     * 获取成功申请该应聘的所有用户
     *
     * @return List{UserDto}
     * 例:
     * url:
     * GET /user/1/followedRecruit
     */
    @GetMapping("/recruit/{recruitId}/participant")
    public Result getSuccessApplyUser(@PathVariable Integer recruitId) {
        return ResultUtil.success(
                recruitService.findAllMemberOfRecruit(recruitId)
        );
    }

    /**
     * 获取<b>调用者</b>申请应聘的所有应聘
     *
     * @return RecruitDto
     * @see RecruitDto
     */
    @GetMapping("/recruit/appliedRecruit")
    public Result getAppliedRecruit() throws WrongUsageException {
        Integer uid = userUtil.getLoginUserId();
        List list = userService.findAllRegisteredRecruit(uid);
        return ResultUtil.success(list);
//        return ResultUtil.success(
//                userService.findAllRegisteredRecruit(
//                        userUtil.getLoginUserId()
//                )
//        );
    }

    /**
     * 获取<b>调用者</b>成功加入应聘的所有应聘
     *
     * @return RecruitDto
     * @see RecruitDto
     */
    @GetMapping("/recruit/assignedRecruit")
    public Result getAssignedRecruit() throws WrongUsageException {
        return ResultUtil.success(
                userService.findAllAssignedRecruit(
                        userUtil.getLoginUserId()
                )
        );
    }

    /**
     * 创建一条招聘
     *
     * @param recruit 招聘
     * @return RecruitListDto
     * 例:
     * url:
     * /recruit/1?teamId=2
     * @see RecruitDto
     */
    @PostMapping("/recruit/{activityId}")
    public Result createOneRecruit(@RequestBody Recruit recruit, @PathVariable Integer activityId,
                                   @RequestParam(value = "teamId", required = true) Integer teamId) {
        Activity activity = activityService.findActivityById(activityId);
        if (!activity.getQuantityType()) {//单人
            return ResultUtil.error(ResultCode.CODE_500);
        }
        recruit.setCreator(userUtil.getLoginUser());
        recruit.setRecruitState(recruitService.going);
        recruit.setActivity(activity);
        recruit.setCreateTime(new Date());
        recruit.setTeam(teamService.findTeam(teamId));
        recruit.setRecruitRegisteredNumber(0);
        return ResultUtil.success(recruitService.createRecruit(recruit));
    }

    @PutMapping("/recruit/{recruitId}")
    public Result updateOneRecruit(@RequestBody Recruit recruit, @PathVariable Integer recruitId) {
        recruit.setId(recruitId);
        recruit.setCreateTime(null);
        return ResultUtil.success(recruitService.updateRecruit(recruit));
    }

    @DeleteMapping("/recruit/{recruitId}")
    public Result deleteOneRecruit(@PathVariable Integer recruitId) {
        recruitService.deleteRecruitById(recruitId);
        return ResultUtil.success();
    }

    /**
     * 通过招聘
     *
     * @param userId    用户Id
     * @param recruitId 招聘Id
     * @return 200OK
     * @throws WrongUsageException USER_NOT_FOUND
     */
    @PutMapping("/recruit/{recruitId}/user/{userId}")
    public Result applyUser(@PathVariable Integer userId, @PathVariable Integer recruitId) throws WrongUsageException {
        recruitService.addMember(userId, recruitId);
        Recruit recruit = recruitService.findRecruit(recruitId);
        if (recruit.getRecruitRegisteredNumber() == recruit.getRecruitWillingNumber()) {
            recruitService.finishRecruit(recruitId);
        }
        return ResultUtil.success();
    }

    /**
     * 移除通过操作
     *
     * @param userId    用户Id
     * @param recruitId 招聘Id
     * @return 200OK
     * @throws WrongUsageException USER_NOT_FOUND
     */
    @DeleteMapping("/recruit/{recruitId}/user/{userId}")
    public Result removeUser(@PathVariable Integer userId, @PathVariable Integer recruitId) throws WrongUsageException {
        recruitService.removeMember(userId, recruitId);
        return ResultUtil.success();
    }

    /**
     * 用户申请招聘
     *
     * @param userId    用户Id
     * @param recruitId 招聘Id
     * @return 200 OK
     * @throws WrongUsageException USER_NOT_FOUND
     */
    @PutMapping("/recruit/{recruitId}/appliedUser/{userId}")
    public Result applyRecruit(@PathVariable Integer userId, @PathVariable Integer recruitId)
            throws WrongUsageException {
        recruitService.applyOneRecruit(userId, recruitId);
        return ResultUtil.success();
    }

    /**
     * 用户取消申请招聘
     *
     * @param userId    用户Id
     * @param recruitId 招聘Id
     * @return 200 OK
     * @throws WrongUsageException USER_NOT_FOUND
     */
    @DeleteMapping("/recruit/{recruitId}/appliedUser/{userId}")
    public Result cancelAppliedRecruit(@PathVariable Integer userId, @PathVariable Integer recruitId)
            throws WrongUsageException {
        recruitService.cancelAppliedRecruit(userId, recruitId);
        return ResultUtil.success();
    }


}
