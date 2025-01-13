package com.de013.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.de013.dto.BlogsRequest;
import com.de013.dto.BlogsVO;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "blogs")
@Getter
@Setter
public class Blogs implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String imgUrl;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String description;
    private String status;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @ManyToMany
    @JoinTable(
        name = "blogs_skills",
        joinColumns = @JoinColumn(name = "blog_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skills> skills;

    public Blogs() {
        
    }

    public Blogs(BlogsRequest request) {
        this.id = request.getId();
        this.title = request.getTitle();
        this.imgUrl = request.getImgUrl();
        this.content = request.getContent();
        this.description = request.getDescription();
        this.status = request.getStatus();
        this.createDate = request.getCreateDate();
        this.updateDate = request.getUpdateDate();
        this.skills = new ArrayList<>();
    }

    public BlogsVO getVO() {
        BlogsVO blogsVO = new BlogsVO();
        BeanUtils.copyProperties(this, blogsVO);
        blogsVO.setSkillsVOs(this.skills.stream().map(Skills::getVO).toList());
        return blogsVO;
    }
}
