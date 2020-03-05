package com.scsse.workflow.controller;

import com.scsse.workflow.entity.model.Course;
import com.scsse.workflow.handler.WrongUsageException;
import com.scsse.workflow.service.ActivityService;
import com.scsse.workflow.service.CourseService;
import com.scsse.workflow.service.UserService;
import com.scsse.workflow.util.dao.UserUtil;
import com.scsse.workflow.util.result.Result;
import com.scsse.workflow.util.result.ResultUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseController {

    private final UserUtil userUtil;

    private final CourseService courseService;

    private final UserService userService;

    private final ActivityService activityService;

    @Autowired
    public CourseController(UserUtil userUtil, CourseService CourseService, UserService userService,
                            ActivityService activityService) {
        this.userUtil = userUtil;
        this.courseService = CourseService;
        this.userService = userService;
        this.activityService = activityService;
    }

    /**
     * 获取我加入的课程
     *
     * @return List{CourseDto}
     */
    @GetMapping("/course/joinedCourse")
    public Result getJoinCourse() {
        return ResultUtil.success(
                userService.findJoinedCourse(userUtil.getLoginUser()));
    }

    /**
     * 获取课程学生
     *
     * @param courseId courseId
     * @return List{User}
     */
    @GetMapping("/course/{courseId}/members")
    public Result getColleague(@PathVariable Integer courseId) {
        return ResultUtil.success(
                courseService.getCourseMembers(courseId)
        );
    }

    /**
     * 获取所有课程
     *
     *
     * @return List{CourseDto}
     */
    @GetMapping("/course/all")
    public Result getAllCourse(){
        return ResultUtil.success(
                courseService.getAllCourse()
        );
    }

    /**
     * 获取一个课程
     *
     *
     * @return CourseDto
     */
    @GetMapping("/course/{courseId}")
    public Result getCourse(@PathVariable Integer courseId) {
        return ResultUtil.success(
                courseService.getCourse(courseId)
        );
    }

    /**
     * 更新一个课程
     *
     *
     * @return CourseDto
     */
    @RequiresRoles("admin")
    @PutMapping("/course/{courseId}")
    public Result updateCourse(@PathVariable Integer courseId, @RequestBody Course course) throws Exception {
        return ResultUtil.success(
                courseService.updateCourse(course)
        );
    }

    /**
     * 发布一个课程
     *
     *
     * @return CourseDto
     */
    @RequiresRoles("admin")
    @PostMapping("/course/{activityId}")
    public Result createCourse(@RequestBody Course course,@PathVariable Integer activityId) {
        course.setActivity(activityService.findActivity(activityId));
        return ResultUtil.success(
                courseService.createCourse(course)
        );
    }

    /**
     * 删除一个课程
     *
     *
     * @return CourseDto
     */
    @RequiresRoles("admin")
    @DeleteMapping("/course/{courseId}")
    public Result deleteCourse(@PathVariable Integer courseId) {
        courseService.deleteCourse(courseId);
        return ResultUtil.success();
    }

    /**
     * 报名一个课程
     *
     *
     * @return CourseDto
     */
    @PutMapping("/course/{courseId}/enroll")
    public Result enroll(@PathVariable Integer courseId) throws WrongUsageException {
        Integer userId = userUtil.getLoginUserId();
        courseService.enroll(userId, courseId);
        return ResultUtil.success();
    }


}
