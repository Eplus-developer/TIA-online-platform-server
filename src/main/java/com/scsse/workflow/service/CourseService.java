package com.scsse.workflow.service;

import com.scsse.workflow.entity.dto.CourseDto;
import com.scsse.workflow.entity.dto.UserDto;
import com.scsse.workflow.entity.model.Course;
import com.scsse.workflow.handler.WrongUsageException;

import java.util.List;

public interface CourseService {
    CourseDto getCourse(Integer courseId);

    List<CourseDto> getAllCourse();

    CourseDto createCourse(Course course);

    CourseDto updateCourse(Course course) throws Exception;

    void deleteCourse(Integer courseId);

    List<UserDto> getCourseMembers(Integer courseId);

    boolean enroll(Integer userId, Integer courseId) throws WrongUsageException;
}
