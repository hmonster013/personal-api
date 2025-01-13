package com.de013.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.de013.dto.ExperiencesRequest;
import com.de013.dto.FilterVO;
import com.de013.model.Experiences;
import com.de013.repository.ExperiencesRepository;
import com.de013.utils.URI;
import com.de013.utils.Utils;

@Service
public class ExperiencesService {
    private static final Logger log = LoggerFactory.getLogger(ExperiencesService.class.getSimpleName());

    @Autowired
    private ExperiencesRepository experiencesRepository;

    @Autowired
    private ImageStorageService imageStorageService;

    public List<Experiences> findAll() {
        return experiencesRepository.findAll();
    }

    public Experiences findById(Long id) {
        return experiencesRepository.findById(id).orElse(null);
    }

    public Experiences create(ExperiencesRequest request, MultipartFile file) throws Exception{
        Experiences experiences = new Experiences(request);
        String imageUrl = "";
        if (file != null) {
            imageUrl = imageStorageService.saveFile(file);
            experiences.setCompanyImg(URI.BASE + URI.V1 + URI.IMAGE + URI.VIEW+  URI.SLASH + imageUrl);
        }
        this.save(experiences);
        return experiences;
    }

    public Experiences update(ExperiencesRequest request, Experiences existed, MultipartFile file) throws Exception {
        log.debug("update " + request);
        Utils.copyNonNullProperties(request, existed);
        String imageUrl = "";
        if (file != null) {
            if (!existed.getCompanyImg().equals("")) {
                String imageName = existed.getCompanyImg().split(URI.BASE + URI.V1 + URI.IMAGE + URI.VIEW+  URI.SLASH)[1];
                imageStorageService.deleteFile(imageName);
            }
            imageUrl = imageStorageService.saveFile(file);
            existed.setCompanyImg(URI.BASE + URI.V1 + URI.IMAGE + URI.VIEW+  URI.SLASH + imageUrl);
        }
        existed = save(existed);
        return existed;
    }

    public Experiences save(Experiences experiences) {
        experiences = experiencesRepository.save(experiences);
        log.debug("save " + experiences);
        return experiences;
    }

    public void deleteById(Long id) throws Exception{
        Experiences experiences = experiencesRepository.findById(id).orElse(null);
        if (!experiences.getCompanyImg().equals("")) {
            String imageName = experiences.getCompanyImg().split(URI.BASE + URI.V1 + URI.IMAGE + URI.VIEW+  URI.SLASH)[1];
            imageStorageService.deleteFile(imageName);
        }

        experiencesRepository.deleteById(id);
    }

    public Page<Experiences> search(FilterVO request, Pageable paging) {
        return experiencesRepository.search(request, paging);
    }
}
