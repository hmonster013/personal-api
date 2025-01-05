package com.de013.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.de013.dto.FilterVO;
import com.de013.model.Skills;
import com.de013.repository.SkillsRepository;
import com.de013.utils.Utils;

@Service
public class SkillsService {
    private static final Logger log = LoggerFactory.getLogger(SkillsService.class.getSimpleName());

    @Autowired
    private SkillsRepository skillsRepository;

    public List<Skills> findAll() {
        return skillsRepository.findAll();
    }

    public Skills findById(Long id) {
        return skillsRepository.findById(id).orElse(null);
    }

    public Skills create(Skills skills) {
        this.save(skills);
        return skills;
    }

    public Skills update(Skills skills, Skills existed) {
        log.debug("update " + skills);
        Utils.copyNonNullProperties(skills, existed);
        existed = save(existed);
        return existed;
    }

    public Skills save(Skills skills) {
        skills = skillsRepository.save(skills);
        log.debug("save " + skills);
        return skills;
    }

    public void deleteById(Long id) {
        skillsRepository.deleteById(id);
    }

    public Page<Skills> search(FilterVO request, Pageable paging) {
        return skillsRepository.search(request, paging);
    }
}
