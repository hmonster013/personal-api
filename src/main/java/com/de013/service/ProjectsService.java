package com.de013.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.de013.dto.FilterVO;
import com.de013.dto.ProjectsRequest;
import com.de013.dto.SkillsVO;
import com.de013.model.Experiences;
import com.de013.model.Projects;
import com.de013.model.Skills;
import com.de013.repository.ProjectsRepository;
import com.de013.utils.URI;
import com.de013.utils.Utils;

@Service
public class ProjectsService {
    private static final Logger log = LoggerFactory.getLogger(ProjectsService.class.getSimpleName());

    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private SkillsService skillsService;

    @Autowired 
    private ImageStorageService imageStorageService;

    public List<Projects> findAll() {
        return projectsRepository.findAll();
    }

    public Projects findById(Long id) {
        return projectsRepository.findById(id).orElse(null);
    }

    public Projects create(ProjectsRequest request, MultipartFile file) throws Exception{
        Projects projects = new Projects(request);
        for (SkillsVO skillsVO : request.getSkillsVOs()) {
            Skills temp = skillsService.findById(skillsVO.getId());
            projects.getSkills().add(temp);
        }

        if (file != null) {
            String fileName = URI.BASE + URI.V1 + URI.IMAGE 
                            + URI.VIEW + URI.SLASH
                            + imageStorageService.saveFile(file);
            projects.setImg(fileName);
        }

        this.save(projects);
        return projects;
    }

    public Projects update(ProjectsRequest request, Projects existed, MultipartFile file) throws Exception{
        log.debug("update " + request);
        Utils.copyNonNullProperties(request, existed);

        // Processing skills in project
        Set<Long> existingSkillIds = existed.getSkills().stream()
        .map(Skills::getId)
        .collect(Collectors.toSet());

        Set<Long> requestedSkillIds = request.getSkillsVOs().stream()
        .map(SkillsVO::getId)
        .collect(Collectors.toSet());
        
        Set<Long> skillsToAdd = new HashSet<>(requestedSkillIds);
        skillsToAdd.removeAll(existingSkillIds);

        Set<Long> skillsToRemove = new HashSet<>(existingSkillIds);
        skillsToRemove.removeAll(requestedSkillIds);

        for (Long skillId : skillsToAdd) {
            Skills getSkill = skillsService.findById(skillId);
            existed.getSkills().add(getSkill);
        }

        for (Long skillId : skillsToRemove) {
            Skills getSkill = skillsService.findById(skillId);
            existed.getSkills().remove(getSkill);
        }

        // Processing image 
        if (file != null) {
            if (!existed.getImg().equals("")) {
                String imageName = existed.getImg().split(URI.BASE + URI.V1 + URI.IMAGE + URI.VIEW+  URI.SLASH)[1];
                imageStorageService.deleteFile(imageName);
            }
            String imageUrl = URI.BASE + URI.V1 + URI.IMAGE 
                            + URI.VIEW +  URI.SLASH 
                            +  imageStorageService.saveFile(file);
            existed.setImg(imageUrl);
        }

        existed = save(existed);
        return existed;
    }

    public Projects save(Projects projects) {
        projects = projectsRepository.save(projects);
        log.debug("save " + projects);
        return projects;
    }

    public void deleteById(Long id) throws Exception{
        Projects project = projectsRepository.findById(id).orElse(null);
        if (!project.getImg().equals("")) {
            String imageName = project.getImg().split(URI.BASE + URI.V1 + URI.IMAGE + URI.VIEW+  URI.SLASH)[1];
            imageStorageService.deleteFile(imageName);
        }

        projectsRepository.deleteById(id);
    }

    public Page<Projects> search(FilterVO request, Pageable paging) {
        return projectsRepository.search(request, paging);
    }
}
