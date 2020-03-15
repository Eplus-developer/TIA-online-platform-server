package com.scsse.workflow.repository;

import com.scsse.workflow.entity.model.Activity;
import com.scsse.workflow.entity.model.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Set;

/**
 * @author Alfred Fu
 * Created on 2019-02-19 20:08
 */
public interface RecruitRepository extends JpaRepository<Recruit, Integer>, JpaSpecificationExecutor<Recruit> {
//    Recruit findOne(Integer recruitId);

//    void deleteById(Integer recruitId);

    Set<Recruit> findAllByActivity_Id(Integer activityId);

    Set<Recruit> findAllByCreator_Id(Integer userId);
}
