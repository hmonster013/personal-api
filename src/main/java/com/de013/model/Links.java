package com.de013.model;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

import com.de013.dto.LinksRequest;
import com.de013.dto.LinksVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "links")
@Getter
@Setter
public class Links implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String title;
    private String url;
    private String icon;

    public Links() {

    }

    public Links(LinksRequest request) {
        BeanUtils.copyProperties(request, this);
    }

    @JsonIgnore
    public LinksVO getVO() {
        LinksVO linksVO = new LinksVO();
        BeanUtils.copyProperties(this, linksVO);
        return linksVO;
    }
}
