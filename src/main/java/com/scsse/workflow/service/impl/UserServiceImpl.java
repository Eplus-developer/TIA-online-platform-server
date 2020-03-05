package com.scsse.workflow.service.impl;


import com.scsse.workflow.entity.dto.*;
import com.scsse.workflow.entity.model.*;
import com.scsse.workflow.repository.*;
import com.scsse.workflow.service.UserService;
import com.scsse.workflow.util.dao.DtoTransferHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Alfred Fu
 * Created on 2019-02-19 20:19
 */


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;

    private final DtoTransferHelper dtoTransferHelper;

    private final RecruitRepository recruitRepository;

    private final UserRepository userRepository;

    private final TagRepository tagRepository;

    private final ActivityRepository activityRepository;

    private final TeamRepository teamRepository;

    private final CourseRepository courseRepository;
    private final RoleRepository roleRepository;


    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, DtoTransferHelper dtoTransferHelper,
                           RecruitRepository recruitRepository, UserRepository userRepository,
                           TagRepository tagRepository, ActivityRepository activityRepository,
                           TeamRepository teamRepository, CourseRepository courseRepository,
                           RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.dtoTransferHelper = dtoTransferHelper;
        this.recruitRepository = recruitRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.activityRepository = activityRepository;
        this.teamRepository = teamRepository;
        this.courseRepository = courseRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserDto> findAllUser() {
        return dtoTransferHelper.transferToListDto(userRepository.findAll(), eachItem -> dtoTransferHelper.transferToUserDto((User) eachItem));
    }

    @Override
    public UserDetailPage findUserDetail(Integer userId) {
        return dtoTransferHelper.transferToUserDetailPage(userRepository.findOne(userId));
    }

    @Override
    public UserDto findUser(Integer userId) {
        return dtoTransferHelper.transferToUserDto(userRepository.findOne(userId));
    }

    @Override
    public UserDto findUserByStuNumber(String number){
        return dtoTransferHelper.transferToUserDto(userRepository.findByStuNumber(number));
    }

    @Override
    public UserDto createUser(User user) {
        return dtoTransferHelper.transferToUserDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(User user) {
        Integer userId = user.getId();
        User oldUser = userRepository.findOne(userId);
        modelMapper.map(user, oldUser);
        return dtoTransferHelper.transferToUserDto(userRepository.save(oldUser));

    }

    @Override
    public void deleteUserById(Integer userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void followRecruit(Integer userId, Integer recruitId) {
        User user = userRepository.findOne(userId);
        Recruit recruit = recruitRepository.findOne(recruitId);
        if (user != null && recruit != null) {
            user.getFollowRecruits().add(recruit);
            userRepository.save(user);
        }
    }

    @Override
    public void registerRecruit(Integer userId, Integer recruitId) {
        User user = userRepository.findOne(userId);
        Recruit recruit = recruitRepository.findOne(recruitId);
        if (user != null && recruit != null) {
            user.getApplyRecruits().add(recruit);
            userRepository.save(user);
        }
    }

    @Override
    public void unfollowRecruit(Integer userId, Integer recruitId) {
        User user = userRepository.findOne(userId);
        Recruit recruit = recruitRepository.findOne(recruitId);
        if (user != null && recruit != null) {
            user.getFollowRecruits().remove(recruit);
            userRepository.save(user);
        }
    }

    @Override
    public void unregisterRecruit(Integer userId, Integer recruitId) {
        User user = userRepository.findOne(userId);
        Recruit recruit = recruitRepository.findOne(recruitId);
        if (user != null && recruit != null) {
            user.getApplyRecruits().remove(recruit);
            userRepository.save(user);
        }
    }

    @Override
    public List<ActivityDto> findAllFollowedActivity(Integer userId) {
        User user = userRepository.findOne(userId);
        Set<Activity> activities = user.getFollowActivities();
        return dtoTransferHelper.transferToListDto(
                activities,
                activity -> dtoTransferHelper.transferToActivityDto((Activity) activity, user)
        );
    }

    @Override
    public void followCourse(Integer userId, Integer courseId) {
        User user = userRepository.findOne(userId);
        Course course = courseRepository.findOne(courseId);
        if (user != null && course != null) {
            user.getFollowCourses().add(course);
            userRepository.save(user);
        }
    }


    @Override
    public List<CourseDto> findAllFollowedCourse(Integer userId) {
        User user = userRepository.findOne(userId);
        Set<Course> courses = user.getFollowCourses();
        return dtoTransferHelper.transferToListDto(courses);
    }

    @Override
    public void unfollowCourse(Integer userId, Integer courseId) {
        User user = userRepository.findOne(userId);
        Course course = courseRepository.findOne(courseId);
        if (user != null && course != null) {
            user.getFollowCourses().remove(course);
            userRepository.save(user);
        }
    }

    @Override
    public List<UserDto> findAllFollowedUser(Integer userId) {
        return dtoTransferHelper.transferToListDto(userRepository.findUserFollower(userId));
    }

    @Override
    public List<UserDto> findAllFollowingUser(Integer userId) {
        User user = userRepository.findOne(userId);
        return dtoTransferHelper.transferToListDto(user.getFollowUsers());
    }

    @Override
    public List<RecruitDto> findAllFollowedRecruit(Integer userId) {
        User user = userRepository.findOne(userId);
        if (user != null) {
            return dtoTransferHelper.transferToListDto(
                    user.getFollowRecruits(), user,
                    (firstParam, secondParam) -> dtoTransferHelper.transferToRecruitDto((Recruit) firstParam, (User) secondParam)
            );
        } else {
            return null;
        }
    }

    @Override
    public List<ActivityDto> findAllFollowingCompetition(Integer userId) {
        return null;
    }

    @Override
    public List<ActivityDto> findAllFollowingCourse(Integer userId) {
        return null;
    }

    @Override
    public List<RecruitDto> findAllRegisteredRecruit(Integer userId) {
        User user = userRepository.findOne(userId);
        if (user != null) {
            return dtoTransferHelper.transferToListDto(
                    user.getApplyRecruits(), user,
                    (firstParam, secondParam) -> dtoTransferHelper.transferToRecruitDto((Recruit) firstParam, (User) secondParam)
            );
        } else {
            return null;
        }
    }

    @Override
    public List<RecruitDto> findAllAssignedRecruit(Integer userId) {
        User user = userRepository.findOne(userId);
        if (user != null) {
            return dtoTransferHelper.transferToListDto(
                    user.getSuccessRecruits(), user,
                    (firstParam, secondParam) -> dtoTransferHelper.transferToRecruitDto((Recruit) firstParam, (User) secondParam)
            );
        } else {
            return null;
        }
    }

    @Override
    public Set<Tag> findAllTagOfUser(Integer userId) {
        User user = userRepository.findOne(userId);
        return user.getUserTags();
    }

    @Override
    public void bindTagToUser(Integer userId, Integer tagId) {
        User user = userRepository.findOne(userId);
        Tag tag = tagRepository.findByTagId(tagId);
        if (user != null && tag != null) {
            user.getUserTags().add(tag);
            userRepository.save(user);
        }
    }

    @Override
    public void unBindTagToUser(Integer userId, Integer tagId) {
        User user = userRepository.findOne(userId);
        Tag tag = tagRepository.findByTagId(tagId);
        if (user != null && tag != null) {
            user.getUserTags().remove(tag);
            userRepository.save(user);
        }
    }

    @Override
    public void followActivity(Integer userId, Integer activityId) {
        User user = userRepository.findOne(userId);
        Activity activity = activityRepository.findOne(activityId);
        if (user != null && activity != null) {
            user.getFollowActivities().add(activity);
            userRepository.save(user);
        }
    }

    @Override
    public void unfollowActivity(Integer userId, Integer activityId) {
        User user = userRepository.findOne(userId);
        Activity activity = activityRepository.findOne(activityId);
        if (user != null && activity != null) {
            user.getFollowActivities().remove(activity);
            userRepository.save(user);
        }
    }

    @Override
    public void followUser(Integer originUserId, Integer followUserId) {
        User originUser = userRepository.findOne(originUserId);
        User followUser = userRepository.findOne(followUserId);
        if (originUser != null && followUser != null) {
            originUser.getFollowUsers().add(followUser);
            userRepository.save(originUser);
        }

    }

    @Override
    public void unfollowUser(Integer originUserId, Integer followUserId) {
        User originUser = userRepository.findOne(originUserId);
        User followUser = userRepository.findOne(followUserId);
        if (originUser != null && followUser != null) {
            originUser.getFollowUsers().remove(followUser);
            userRepository.save(originUser);
        }
    }


    @Override
    public List<TeamDto> findJoinedTeam(User user) {
        return dtoTransferHelper.transferToListDto(
                teamRepository.findAllByMembersContains(user),
                eachItem -> dtoTransferHelper.transferToTeamDto((Team) eachItem)
        );
    }

    @Override
    public List<CourseDto> findJoinedCourse(User user) {
        return dtoTransferHelper.transferToListDto(
                courseRepository.findAllByStudentsContains(user),
                eachItem -> dtoTransferHelper.transferToCourseDto((Course) eachItem)
        );
    }

    @Override
    public List<TeamDto> findCreatedTeam(Integer userId) {
        return dtoTransferHelper.transferToListDto(
                teamRepository.findAllByLeader_Id(userId)
        );
    }

    @Override
    public List<CourseDto> findCreatedCourse(Integer userId) {
        return dtoTransferHelper.transferToListDto(
                courseRepository.findAllByLecturer_Id(userId)
        );
    }

    @Override
    public Set<Role> findRole(Integer userId) {
        User user = userRepository.findOne(userId);
        if (user != null)
            return user.getRoles();
        return null;
    }

    @Override
    public Set<Access> findAccess(Integer userId) {
        User user = userRepository.findOne(userId);
        Set<Role> roles = findRole(userId);
        Set<Access> accesses = new HashSet<>();
        for (Role role : roles) {
            accesses.addAll(role.getAccesses());
        }
        return accesses;
    }

    @Override
    public void addRole(Integer userId, Integer roleId) {
        Role role = roleRepository.findOne(roleId);
        User user = userRepository.findOne(userId);
        if (role != null) {
            user.getRoles().add(role);
            userRepository.save(user);
        }
    }

    @Override
    public void removeRole(Integer userId, Integer roleId) {
        Role role = roleRepository.findOne(roleId);
        User user = userRepository.findOne(userId);
        if (role != null&&user.getRoles().contains(role)) {
            user.getRoles().remove(role);
            userRepository.save(user);
        }
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public User getOne(Integer id) {
        System.out.println("userid: " + id);
        return userRepository.findOne(id);
    }



}
