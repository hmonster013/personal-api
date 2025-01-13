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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.de013.dto.BlogsRequest;
import com.de013.dto.BlogsVO;
import com.de013.dto.FilterVO;
import com.de013.dto.ProjectsRequest;
import com.de013.dto.ProjectsVO;
import com.de013.exception.RestException;
import com.de013.model.Blogs;
import com.de013.model.Paging;
import com.de013.model.Projects;
import com.de013.service.BlogsService;
import com.de013.service.ProjectsService;
import com.de013.utils.URI;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(URI.V1 + URI.BLOGS)
public class BlogsController extends BaseController{
    static Logger log = LoggerFactory.getLogger(BlogsController.class.getName());

    @Autowired
    private BlogsService blogsService;

    @PostMapping(value = URI.LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listBlogs(@RequestBody FilterVO request) throws Exception {
        log.info("Search blogs");
        Pageable paging = new Paging().getPageRequest(request);
        Page<Blogs> result = blogsService.search(request, paging);
        log.info("Search blog total elements [" + result.getTotalElements() + "]");
        List<BlogsVO> reponseList = result.stream().map(Blogs::getVO).toList();
        
        return responseList(reponseList, result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = URI.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getById(@PathVariable("id") long id) throws Exception{
        log.info("Get detail blog by [" + id + "]");
        
        Blogs blog = blogsService.findById(id);
		log.info("Get blog by getId [" + id + "]");
		if (blog != null) {
			return response(blog.getVO());
		} else {
			throw new RestException("Blog Id [" + id + "] invalid ");
		}
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createBlog(
        @RequestParam("requestJsonData") String jsonData,
        @RequestPart(value = "file", required = false) MultipartFile file
    ) throws Exception {
        log.info("Create blog");
        
        ObjectMapper objectMapper = new ObjectMapper();
        BlogsRequest request = objectMapper.readValue(jsonData, BlogsRequest.class);

        Blogs blog = blogsService.create(request, file);
        return response(blog.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateBlog(
        @RequestParam("requestJsonData") String jsonData,
        @RequestPart(value = "file", required = false) MultipartFile file
    ) throws Exception {
        log.info("Start update blog");

        ObjectMapper objectMapper = new ObjectMapper();
        BlogsRequest request = objectMapper.readValue(jsonData, BlogsRequest.class);

        Long id = request.getId();
        Blogs existed = blogsService.findById(id);

        if (existed == null) {
			throw new RestException("Blog Id [" + id + "] invalid ");
		}

        Blogs result = blogsService.update(request, existed, file);
        return response(result.getVO());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = URI.ID,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@PathVariable("id") Long id) throws Exception {
        log.info("Delete blog:[" + id + "]");

        Blogs existed = blogsService.findById(id);
        if (existed == null) {
            throw new RestException("Blog id [" + id + "] invailid");
        }

        blogsService.deleteById(id);
        return responseMessage("Deleted blog [" + id + "] sucessfully");
    }
}
