package com.scsse.workflow.controller;

import com.scsse.workflow.entity.model.Activity;
import com.scsse.workflow.handler.WrongUsageException;
import com.scsse.workflow.service.ActivityService;
import com.scsse.workflow.util.dao.UserUtil;
import com.scsse.workflow.util.result.Result;
import com.scsse.workflow.util.result.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author Alfred Fu
 * Created on 2019-03-07 20:48
 */

@RestController
public class ActivityController {

    private final ActivityService activityService;

    private final UserUtil userUtil;

    @Autowired
    public ActivityController(ActivityService activityService, UserUtil userUtil) {
        this.activityService = activityService;
        this.userUtil = userUtil;
    }

    /**
     * 获取所有活动
     *
     * @param type {fresh,expire,finish}
     *             fresh：未到报名截止时间
     *             expire：超过报名截止时间，但活动尚未开始
     *             finish：活动已经结束
     * @return List{ActivityDto}
     */
    @GetMapping("/activity/all")
    public Result findAllActivity(
            @RequestParam(required = false) String activityName,
            @RequestParam(required = false, defaultValue = "0") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "type", required = false, defaultValue = "normal") String type) {
//        switch (type) {
//            case "fresh":
//                return ResultUtil.success(activityService.findAllFreshActivity("activity"));
//        break;
//            case "expire":
//                return ResultUtil.success(activityService.findAllExpiredActivity("activity"));
//        break;
//            case "finish":
//                return ResultUtil.success(activityService.findAllFinishedActivity("activity"));
//        break;
//            default:
//                return ResultUtil.success(activityService.findAllActivity("activity"));
//        }
        return ResultUtil.success(activityService.findPaginationActivityWithCriteria(pageNum, pageSize, activityName, type,false));


    }

    /**
     * 获取所有比赛
     */
    @GetMapping("/activity/all/competition")
    public Result findAllCompetition(
            @RequestParam(required = false) String activityName,
            @RequestParam(required = false, defaultValue = "0") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "type", required = false, defaultValue = "normal") String type) {
        return ResultUtil.success(activityService.findPaginationActivityWithCriteria(pageNum, pageSize, activityName, type,true));

    }


    /**
     * 获取关于该活动的所有招聘
     *
     * @param activityId
     * @return List{RecruitDto}
     */
    @GetMapping("/activity/{activityId}/recruit")
    public Result findRecruitOfActivity(@PathVariable Integer activityId) {
        return ResultUtil.success(activityService.findAllRecruitOfActivity(activityId));
    }

    /**
     * 获取某个活动的具体信息
     *
     * @param activityId 活动Id
     * @return ActivityDto
     * <p>
     * e.g.
     * GET /activity/1
     */
    @GetMapping("/activity/{activityId}")
    public Result getActivityDetail(@PathVariable Integer activityId) {
        return ResultUtil.success(activityService.findActivityById(activityId));
    }

    /**
     * 创建一个活动
     *
     * @param activity 活动
     * @return Activity
     * 例:
     * url:
     * /recruit
     * @see Activity
     */
    @RequiresRoles("admin")
    @PostMapping("/activity")
    public Result createOneActivity(@RequestBody Activity activity) {
        activity.setPromoter(userUtil.getLoginUser());
        if (activity.getActivityType().equals("course"))
            activity.setQuantityType(true);
        activity.setPublishTime(new Date());
        return ResultUtil.success(activityService.createActivity(activity));
    }

    @RequiresRoles("admin")
    @PutMapping("/activity/{activityId}")
    public Result updateOneActivity(@RequestBody Activity activity, @PathVariable Integer activityId) {
        activity.setId(activityId);
        return ResultUtil.success(activityService.updateActivity(activity));
    }

    @RequiresRoles("admin")
    @DeleteMapping("/activity/{activityId}")
    public Result deleteOneActivity(@PathVariable Integer activityId) {
        activityService.deleteActivityById(activityId);
        return ResultUtil.success();
    }

    @PutMapping("/activity/{activityId}/enroll")
    public Result enroll(@PathVariable Integer activityId) throws WrongUsageException {
        Integer userId = userUtil.getLoginUserId();
        activityService.enroll(userId, activityId);
        return ResultUtil.success();
    }
}
