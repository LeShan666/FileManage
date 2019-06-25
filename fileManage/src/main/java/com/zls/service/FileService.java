package com.zls.service;

import com.zls.dao.FileDao;
import com.zls.domain.FileMan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FileService {

    @Autowired
    FileDao fileDao;

    public FileMan saveFile(FileMan fileMan){
        FileMan fileMan1 = fileDao.save(fileMan);

        return fileMan1;
    }

    public List<FileMan> getAllFile() {

        return fileDao.findAll();
    }

    public List<FileMan> searchFile(FileMan fileMan) {

        return fileDao.findByFilenameLike("%"+fileMan.getFilename()+"%");

    }

    public void delFile(int i) {

        fileDao.deleteByFileid(i);

    }

    public List<FileMan> getBlueFile() {

        return fileDao.findByFilestyle("blueFile");
    }

    public List<FileMan> getCPCFile() {
        return fileDao.findByFilestyle("CPCFile");
    }
}
