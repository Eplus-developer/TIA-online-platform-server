package com.scsse.workflow.Controller;

import com.scsse.workflow.service.ActivityService;
import com.scsse.workflow.util.Result.Result;
import com.scsse.workflow.util.Result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alfred Fu
 * Created on 2019-03-07 20:48
 */

@RestController
public class ActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    /**
     * 获取所有活动
     * @param type {fresh,expire,finish}
     *             fresh：未到报名截止时间
     *             expire：超过报名截止时间，但活动尚未开始
     *             finish：活动已经结束
     * @return List{Activity}
     */
    @GetMapping("/activity/all")
    public Result findAllActivity(@RequestParam(name = "type",required = false,defaultValue = "normal") String type){
        switch (type){
            case "fresh":
                return ResultUtil.success(activityService.findAllFreshActivity());
            case "expire":
                return ResultUtil.success(activityService.findAllExpiredActivity());
            case "finish":
                return ResultUtil.success(activityService.findAllFinishedActivity());
            default:
                return ResultUtil.success(activityService.findAllActivity());
        }

    }

    /**
     * 获取某个活动的具体信息
     * @param id
     * @return
     */
    @GetMapping("/activity/{id}")
    public Result getActivityDetail(@PathVariable Integer id){
        return ResultUtil.success();
    }

    /**
     * 获取调用者关注的所有比赛
     * @param openid 调用者的openid
     * @return
     */
    @GetMapping("/activity/followed")
    public Result getFollowedActivity(@RequestAttribute() String openid){
        return ResultUtil.success();
    }

    /**
     * 关注一个活动
     * 因为是新建一条关注，所以用post
     * @param id 该活动的id
     * @param openid 调用者的openid
     * @return
     * 例:
     *  url:
     *      /activity/1/follow
     */
    @PostMapping("/activity/{id}/follow")
    public Result followOneActivity(@PathVariable() Integer id, @RequestAttribute() String openid){
        return ResultUtil.success();
    }

    /**
     * 取消关注一个活动
     * @param id 该活动的id
     * @param openid 调用者的openid
     * @return
     * 例:
     *  url:
     *      /activity/1/unfollow
     */
    @DeleteMapping("/activity/{id}/unfollow")
    public Result unfollowOneActivity(@PathVariable() Integer id, @RequestAttribute() String openid){
        return ResultUtil.success();
    }


}
