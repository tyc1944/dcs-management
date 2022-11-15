package com.yunmo.dcs.infrastruction.repository.jpa;

import com.yunmo.dcs.domain.Project;
import com.yunmo.dcs.domain.repository.ProjectRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/** @author lh */
public interface ProjectJpaRepository extends JpaRepository<Project, Long>, ProjectRepository {}
