package com.zls.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "file")
public class FileMan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileid;
    @Column(name = "file_name")
    private String filename;
    @Column(name = "file_style")
    private String filestyle;
    @Column(name = "file_size")
    private String filesize;
    @Column(name = "file_up")
    private String uptime;
    @Column(name = "file_path")
    private String filepath;

}
