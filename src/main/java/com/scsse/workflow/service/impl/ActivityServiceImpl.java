package com.scsse.workflow.service.impl;

import com.scsse.workflow.entity.dto.ActivityDto;
import com.scsse.workflow.entity.dto.RecruitDto;
import com.scsse.workflow.entity.model.Activity;
import com.scsse.workflow.entity.model.Recruit;
import com.scsse.workflow.entity.model.Tag;
import com.scsse.workflow.entity.model.User;
import com.scsse.workflow.handler.WrongUsageException;
import com.scsse.workflow.repository.ActivityRepository;
import com.scsse.workflow.repository.RecruitRepository;
import com.scsse.workflow.repository.TagRepository;
import com.scsse.workflow.repository.UserRepository;
import com.scsse.workflow.service.ActivityService;
import com.scsse.workflow.util.dao.DtoTransferHelper;
import com.scsse.workflow.util.dao.UserUtil;
import com.scsse.workflow.util.result.ResultUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Alfred Fu
 * Created on 2019-02-19 20:18
 */
@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {

    private final ModelMapper modelMapper;

    private final UserUtil userUtil;

    private final DtoTransferHelper dtoTransferHelper;

    private final ActivityRepository activityRepository;

    private final RecruitRepository recruitRepository;
    private final UserRepository userRepository;

    private final TagRepository tagRepository;

    @Autowired
    public ActivityServiceImpl(ModelMapper modelMapper, UserUtil userUtil,UserRepository userRepository, DtoTransferHelper dtoTransferHelper, ActivityRepository activityRepository, RecruitRepository recruitRepository, TagRepository tagRepository) {
        this.modelMapper = modelMapper;
        this.userUtil = userUtil;
        this.userRepository = userRepository;
        this.dtoTransferHelper = dtoTransferHelper;
        this.activityRepository = activityRepository;
        this.recruitRepository = recruitRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<ActivityDto> findAllActivity(String type) {
        List<Activity> list = new ArrayList<>();
        if(type.equals("competition")){
            list = activityRepository.findByActivityType("competition");
        }else{
            list = activityRepository.findAll();
        }
        return dtoTransferHelper.transferToListDto(list, eachItem -> dtoTransferHelper.transferToActivityDto((Activity) eachItem,userUtil.getLoginUser()));
    }
    @Override
    public Activity findActivity(Integer activityId) {
        return activityRepository.findOne(activityId);
    }
    @Override
    public List<ActivityDto> findAllExpiredActivity(String type) {
        List<Activity> activities = new ArrayList<>();
        List<Activity> list = new ArrayList<>();
        if(type.equals("competition")){
            list = activityRepository.findByActivityType("competition");
        }else{
            list = activityRepository.findAll();
        }
        list.stream()
                // if the time now is greater than the signUpDeadline
                .filter(activity -> LocalDate.now().compareTo
                        (activity.getEndTime().toInstant().atZone(ZoneId.of("Asia/Shanghai")).
                                toLocalDate()) > 0)
                // but less than the activity time
                .filter(activity -> LocalDate.now().compareTo
                        (activity.getActTime().toInstant().atZone(ZoneId.of("Asia/Shanghai")).
                                toLocalDate()) < 0)
                .forEach(activities::add);
        return dtoTransferHelper.transferToListDto(activities, eachItem -> dtoTransferHelper.transferToActivityDto((Activity) eachItem,userUtil.getLoginUser()));
    }

    @Override
    public List<ActivityDto> findAllFinishedActivity(String type) {
        List<Activity> activities = new ArrayList<>();
        List<Activity> list = new ArrayList<>();
        if(type.equals("competition")){
            list = activityRepository.findByActivityType("competition");
        }else{
            list = activityRepository.findAll();
        }
        list.stream()
                // if the time now is greater than the activity time
                .filter(activity -> LocalDate.now().compareTo
                        (activity.getActTime().toInstant().atZone(ZoneId.of("Asia/Shanghai")).
                                toLocalDate()) > 0)
                .forEach(activities::add);
        return dtoTransferHelper.transferToListDto(activities, eachItem -> dtoTransferHelper.transferToActivityDto((Activity) eachItem,userUtil.getLoginUser()));

    }

    @Override
    public List<ActivityDto> findAllFreshActivity(String type) {
        List<Activity> activities = new ArrayList<>();
        List<Activity> list = new ArrayList<>();
        if(type.equals("competition")){
            list = activityRepository.findByActivityType("competition");
        }else{
            list = activityRepository.findAll();
        }
        list.stream()
                // if the time now is less than the signUpDeadline
                .filter(activity -> LocalDate.now().compareTo
                        (activity.getEndTime().toInstant().atZone(ZoneId.of("Asia/Shanghai")).
                                toLocalDate()) < 0)
                .forEach(activities::add);
        return dtoTransferHelper.transferToListDto(activities, eachItem -> dtoTransferHelper.transferToActivityDto((Activity) eachItem,userUtil.getLoginUser()));

    }

    @Override
    public ActivityDto findActivityById(Integer activityId) {
        return dtoTransferHelper.transferToActivityDto(activityRepository.findOne(activityId),userUtil.getLoginUser());
    }

    @Override
    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public Activity updateActivity(Activity activity) {
        Integer activityId = activity.getId();
        Activity oldActivity = activityRepository.findOne(activityId);
        modelMapper.map(activity, oldActivity);
        return activityRepository.save(oldActivity);
    }

    @Override
    public void deleteActivityById(Integer activityId) {
        activityRepository.delete(activityId);
    }

    @Override
    public List<RecruitDto> findAllRecruitOfActivity(Integer activityId) {
        return dtoTransferHelper.transferToListDto(
                recruitRepository.findAllByActivity_Id(activityId), userUtil.getLoginUser(),
                (firstParam, secondParam) -> dtoTransferHelper.transferToRecruitDto((Recruit) firstParam, (User) secondParam)
        );
    }

    @Override
    public List<ActivityDto> findPaginationActivityWithCriteria(Integer pageNum, Integer pageSize,
                                                         String activityName,String type,boolean isCompetition){
        Pageable pageable = new PageRequest(pageNum, pageSize, Sort.Direction.DESC, "publishTime");
        List<ActivityDto> result = new ArrayList<>();
        Page<Activity> page = activityRepository.findAll(new Specification<Activity>() {

            public Predicate toPredicate(Root<Activity> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<String> activityNamePath = root.get("name");
                /**
                 * 连接查询条件, 不定参数，可以连接0..N个查询条件
                 */
                Predicate condition1 = null;
                if(activityName==null||activityName.trim()==""){
                    condition1=cb.like(activityNamePath, "%%");
                }else{
                    condition1=cb.like(activityNamePath, "%"+activityName+"%");
                }
                Path<Timestamp> endTimePath = root.get("endTime");
                Path<Timestamp> actTimePath = root.get("actTime");
                Path<String> activityTypePath = root.get("activityType");
                Predicate condition2 = null;
                Predicate condition3 = null;
                Predicate condition4 = null;
                LocalDateTime currentTime = LocalDateTime.now();
                switch (type) {
                    case "fresh":
                        condition2=cb.greaterThan(endTimePath, Timestamp.valueOf(currentTime));
                        break;
                    case "expire":
                        condition2=cb.lessThanOrEqualTo(endTimePath, Timestamp.valueOf(currentTime));
                        condition3=cb.greaterThan(actTimePath, Timestamp.valueOf(currentTime));
                        break;
                    case "finish":
                        condition2=cb.lessThanOrEqualTo(actTimePath, Timestamp.valueOf(currentTime));
                        break;
                    default:
                }
                if(isCompetition){
                    condition4=cb.equal(activityTypePath, "competition");
                }else{
                    condition4=cb.like(activityTypePath, "%%");
                }
                if(condition2==null)
                query.where(condition1,condition4);
                else if(condition3==null){
                    query.where(condition1,condition2,condition4);
                }else
                    query.where(condition1,condition2,condition3,condition4);

                return null;
            }

        },pageable);
        List<Activity> list = page.getContent();
        User currentUser = userUtil.getLoginUser();

        for(Activity ans:list){
            result.add(dtoTransferHelper.transferToActivityDto(ans,currentUser));
        }
        return result;
    }
    @Override
    public boolean enroll(Integer userId, Integer activityId) throws WrongUsageException {
        Activity activity = findActivity(activityId);
        User user = userUtil.getUserByUserId(userId);
        if(activity!=null) {
            user.getJoinActivities().add(activity);
            userRepository.save(user);
            return true;
        }
        return false;
    }
    @Override
    public Set<Tag> findAllTagOfActivity(Integer activityId) {
        Activity activity = activityRepository.findOne(activityId);
        return activity.getActivityTags();
    }

    @Override
    public void bindTagToActivity(Integer activityId, Integer tagId) {
        Activity activity = activityRepository.findOne(activityId);
        Tag tag = tagRepository.findByTagId(tagId);
        if (activity != null && tag != null && !activity.getActivityTags().contains(tag)) {
            activity.getActivityTags().add(tag);
            activityRepository.save(activity);
        }

    }

    @Override
    public void unBindTagToActivity(Integer activityId, Integer tagId) {
        Activity activity = activityRepository.findOne(activityId);
        activity.getActivityTags().remove(tagRepository.findByTagId(tagId));
        activityRepository.save(activity);
    }
}
