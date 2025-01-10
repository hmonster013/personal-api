package com.de013.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.de013.dto.FilterVO;
import com.de013.dto.LinksRequest;
import com.de013.dto.LinksVO;
import com.de013.exception.RestException;
import com.de013.model.Links;
import com.de013.model.Paging;
import com.de013.service.LinksService;
import com.de013.utils.URI;

@RestController
@RequestMapping(URI.V1 + URI.LINKS)
public class LinksController extends BaseController{
    static Logger log = LoggerFactory.getLogger(LinksController.class.getName());

    @Autowired
    private LinksService linksService;

    @PostMapping(value = URI.LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listLinks(@RequestBody FilterVO request) throws Exception {
        log.info("Search links");
        Pageable paging = new Paging().getPageRequest(request);
        Page<Links> result = linksService.search(request, paging);
        log.info("Search links total elements [" + result.getTotalElements() + "]");
        List<LinksVO> reponseList = result.stream().map(Links::getVO).toList();
        
        return responseList(reponseList, result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getById(@PathVariable("id") long id) throws Exception{
        log.info("Get detail links");
        
        Links links = linksService.findById(id);
		log.info("Get link by getId [" + id + "]");
		if (links != null) {
			return response(links.getVO());
		} else {
			throw new RestException("Link Id [" + id + "] invalid ");
		}
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createLink(@ModelAttribute LinksRequest linkRequest) throws Exception {
        log.info("Create link");
        
        Links link = linksService.create(linkRequest);
        return response(link.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateLink(@ModelAttribute LinksRequest linkRequest) throws Exception {
        log.info("Start update link");

        Long id = linkRequest.getId();
        Links existed = linksService.findById(id);

        if (existed == null) {
			throw new RestException("Link Id [" + id + "] invalid ");
		}

        Links result = linksService.update(linkRequest, existed);
        return response(result.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = URI.ID,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@PathVariable("id") Long id) throws Exception {
        log.info("Delete link:[" + id + "]");

        Links existed = linksService.findById(id);
        if (existed == null) {
            throw new RestException("Link id [" + id + "] invailid");
        }

        linksService.deleteById(id);
        return responseMessage("Deleted link [" + id + "] sucessfully");
    }
}
