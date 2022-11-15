package com.yunmo.dcs.domain.repository;

import com.yunmo.dcs.domain.Project;
import com.yunmo.domain.common.EntityRepository;

import java.util.List;

/** @author lh */
public interface ProjectRepository extends EntityRepository<Project, Long> {
    List<Project> findByCreatedBy(Long id);
}
