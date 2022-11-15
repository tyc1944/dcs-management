package com.yunmo.dcs.api.resource;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@RequestMapping("/api/projects/{projectId}/entities")
@Tag(name = "项目下资产实体")
public class ProjectAssetEntityResource {}
