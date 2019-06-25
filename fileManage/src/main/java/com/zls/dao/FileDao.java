package com.zls.dao;

import com.zls.domain.FileMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FileDao extends JpaRepository<FileMan,Integer>, JpaSpecificationExecutor<FileMan> {

    public List<FileMan>  findByFilenameLike(String filename);

    public void deleteByFileid(Integer fileid);

    public List<FileMan> findByFilestyle(String filestyle);
}
