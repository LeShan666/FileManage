package com.zls.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.zls.domain.FileMan;
import com.zls.service.FileService;
import com.zls.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {


    @Autowired
    private FileService fileService;

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    /**
     * /file/getBlueFile  得到蓝桥杯文件
     */
    @RequestMapping("/getBlueFile")
    public List<FileMan> getBlueFile(){

        List<FileMan> list = fileService.getBlueFile();
        return  list;
    }

    /**
     * /file/getBlueFile  得到蓝桥杯文件
     */
    @RequestMapping("/getCPCFile")
    public List<FileMan> getCPCFile(){

        List<FileMan> list = fileService.getCPCFile();
        return  list;
    }

    @RequestMapping("/delFile")
    public void delFile(HttpServletRequest request){

        String id = request.getParameter("id");
        int i = Integer.parseInt(id);
        System.out.println(i);
        fileService.delFile(i);

    }

    /**
     * 查找文件
     */
    @RequestMapping("/searchFile")
    public List<FileMan> searchFile(@Valid FileMan fileMan){

        System.out.println(fileMan);
        List<FileMan> list = fileService.searchFile(fileMan);
        return list;
    }

    /**
     * 获取所有文件
     * @return
     */
    @RequestMapping("/getAllFile")
    public List<FileMan> getAllFile(){

        List<FileMan> list = fileService.getAllFile();
        System.out.println("============================================");
        return list;
    }


    /**
     * 单文件上传
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file){

        if (file.isEmpty()) {
            return "文件为空";
        }

        System.out.println("111111"+file);
        // 获取文件名
        String fileName = file.getOriginalFilename();
        logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = "E://test//";
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    /**
     * 多文件上传
     * @param request
     * @return
     */
    @RequestMapping("/uploadMany")
    public String uploadMany(HttpServletRequest request){
        logger.info("多文件上传开始");
        String filetype = request.getParameter("filestype");
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");  // file  问 input file 的name值
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        logger.info("-------------------------"+filetype);
        for (int i = 0; i < files.size(); ++i) {
            FileMan fileMan = new FileMan();
            file = files.get(i);
            fileMan.setFilename(file.getOriginalFilename());
            fileMan.setFilesize(Util.setSize(file.getSize()));
            fileMan.setUptime(Util.getCurrentTime());
            fileMan.setFilestyle(filetype);
            fileMan.setFilepath("E://FileManage//"+filetype+"//"+file.getOriginalFilename());
            fileService.saveFile(fileMan);
            System.out.println("............................."+fileMan);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    File file1 = new File("E://FileManage//"+filetype+"//" + file.getOriginalFilename());
                    if (!file1.getParentFile().exists()) {
                        file1.getParentFile().mkdirs();
                    }
                    stream = new BufferedOutputStream(new FileOutputStream(file1));
                    stream.write(bytes);
                    stream.close();

                } catch (Exception e) {
                    stream = null;
                    logger.info("-----多文件上传结束");
                    return "You failed to upload " + i + " => "
                            + e.getMessage();
                }
            } else {
                logger.info("-----多文件上传结束");
                return "You failed to upload " + i
                        + " because the file was empty.";
            }
        }
        logger.info("-----多文件上传结束");
        return "upload successful";
    }

    /**
     * 文件下载
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/download")
    public String downLoad(HttpServletResponse response,HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        String filePath = request.getParameter("filepath");
        String filename = request.getParameter("filename");
        System.out.println(filePath+"/"+filename);
        File file = new File(filePath);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            // response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" +   java.net.URLEncoder.encode(filename,"UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("----------file download---" + filename);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
}
