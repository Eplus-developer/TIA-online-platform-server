package com.scsse.workflow.repository;

import com.scsse.workflow.entity.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author Alfred Fu
 * Created on 2019-02-19 20:08
 */
public interface ActivityRepository extends JpaRepository<Activity, Integer>, JpaSpecificationExecutor<Activity> {
    List<Activity> findByActivityType(String activityType);
}
