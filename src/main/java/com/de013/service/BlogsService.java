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

import com.de013.dto.BlogsRequest;
import com.de013.dto.FilterVO;
import com.de013.dto.SkillsVO;
import com.de013.model.Blogs;
import com.de013.model.Skills;
import com.de013.repository.BlogsRepository;
import com.de013.utils.URI;
import com.de013.utils.Utils;

@Service
public class BlogsService {
    private static final Logger log = LoggerFactory.getLogger(BlogsService.class.getSimpleName());

    @Autowired
    private BlogsRepository blogsRepository;

    @Autowired
    private SkillsService skillsService;

    @Autowired 
    private ImageStorageService imageStorageService;

    public List<Blogs> findAll() {
        return blogsRepository.findAll();
    }

    public Blogs findById(Long id) {
        return blogsRepository.findById(id).orElse(null);
    }

    public Blogs create(BlogsRequest request, MultipartFile file) throws Exception{
        Blogs blogs = new Blogs(request);
        for (SkillsVO skillsVO : request.getSkillsVOs()) {
            Skills temp = skillsService.findById(skillsVO.getId());
            blogs.getSkills().add(temp);
        }

        if (file != null) {
            String fileName = URI.BASE + URI.V1 + URI.IMAGE 
                            + URI.VIEW + URI.SLASH
                            + imageStorageService.saveFile(file);
            blogs.setImgUrl(fileName);
        }

        this.save(blogs);
        return blogs;
    }

    public Blogs update(BlogsRequest request, Blogs existed, MultipartFile file) throws Exception{
        log.debug("update " + request);
        Utils.copyNonNullProperties(request, existed);

        // Processing skills
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
            if (!existed.getImgUrl().equals("")) {
                String imageName = existed.getImgUrl().split(URI.BASE + URI.V1 + URI.IMAGE + URI.VIEW+  URI.SLASH)[1];
                imageStorageService.deleteFile(imageName);
            }
            String imageUrl = URI.BASE + URI.V1 + URI.IMAGE 
                            + URI.VIEW +  URI.SLASH 
                            +  imageStorageService.saveFile(file);
            existed.setImgUrl(imageUrl);
        }

        existed = save(existed);
        return existed;
    }

    public Blogs save(Blogs blogs) {
        blogs = blogsRepository.save(blogs);
        log.debug("save " + blogs);
        return blogs;
    }

    public void deleteById(Long id) throws Exception{
        Blogs blog = blogsRepository.findById(id).orElse(null);
        if (!blog.getImgUrl().equals("")) {
            String imageName = blog.getImgUrl().split(URI.BASE + URI.V1 + URI.IMAGE + URI.VIEW+  URI.SLASH)[1];
            imageStorageService.deleteFile(imageName);
        }

        blogsRepository.deleteById(id);
    }

    public Page<Blogs> search(FilterVO request, Pageable paging) {
        return blogsRepository.search(request, paging);
    }
}
