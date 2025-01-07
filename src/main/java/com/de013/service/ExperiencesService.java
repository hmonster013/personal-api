package com.de013.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.de013.dto.ExperiencesRequest;
import com.de013.dto.FilterVO;
import com.de013.dto.LinksRequest;
import com.de013.model.Experiences;
import com.de013.model.Links;
import com.de013.repository.ExperiencesRepository;
import com.de013.repository.LinksRepository;
import com.de013.utils.Utils;

@Service
public class ExperiencesService {
    private static final Logger log = LoggerFactory.getLogger(ExperiencesService.class.getSimpleName());

    @Autowired
    private ExperiencesRepository experiencesRepository;

    public List<Experiences> findAll() {
        return experiencesRepository.findAll();
    }

    public Experiences findById(Long id) {
        return experiencesRepository.findById(id).orElse(null);
    }

    public Experiences create(ExperiencesRequest request) {
        Experiences experiences = new Experiences(request);
        this.save(experiences);
        return experiences;
    }

    public Experiences update(ExperiencesRequest request, Experiences existed) {
        log.debug("update " + request);
        Utils.copyNonNullProperties(request, existed);
        existed = save(existed);
        return existed;
    }

    public Experiences save(Experiences experiences) {
        experiences = experiencesRepository.save(experiences);
        log.debug("save " + experiences);
        return experiences;
    }

    public void deleteById(Long id) {
        experiencesRepository.deleteById(id);
    }

    public Page<Experiences> search(FilterVO request, Pageable paging) {
        return experiencesRepository.search(request, paging);
    }
}
