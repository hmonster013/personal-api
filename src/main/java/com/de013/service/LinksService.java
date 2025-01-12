package com.de013.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.de013.dto.FilterVO;
import com.de013.dto.LinksRequest;
import com.de013.model.Links;
import com.de013.repository.LinksRepository;
import com.de013.utils.Utils;

@Service
public class LinksService {
    private static final Logger log = LoggerFactory.getLogger(LinksService.class.getSimpleName());

    @Autowired
    private LinksRepository linksRepository;

    public List<Links> findAll() {
        return linksRepository.findAll();
    }

    public Links findById(Long id) {
        return linksRepository.findById(id).orElse(null);
    }

    public List<Links> findByName(String name) {
        return linksRepository.findByName(name);
    }

    public List<Links> findByListName(List<String> listName) {
        return linksRepository.findByListName(listName);
    }

    public Links create(LinksRequest request) {
        Links links = new Links(request);
        this.save(links);
        return links;
    }

    public Links update(LinksRequest links, Links existed) {
        log.debug("update " + links);
        Utils.copyNonNullProperties(links, existed);
        existed = save(existed);
        return existed;
    }

    public Links save(Links links) {
        links = linksRepository.save(links);
        log.debug("save " + links);
        return links;
    }

    public void deleteById(Long id) {
        linksRepository.deleteById(id);
    }

    public Page<Links> search(FilterVO request, Pageable paging) {
        return linksRepository.search(request, paging);
    }
}
