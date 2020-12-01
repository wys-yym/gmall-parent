package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.result.Result;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @ProjectName: gmall-parent
 * @Package: com.atguigu.gmall.product.controller
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/12/1 10:54
 * @Version: 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("admin/product")
public class FileUploadApiController {

    @RequestMapping("fileUpload")
    public Result fileUpload(MultipartFile file) {
        String url = "http://192.168.200.129:80";
        String path = FileUploadApiController.class.getClassLoader().getResource("tracker.conf").getPath();
        try {
            ClientGlobal.init(path);
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageClient storageClient = new StorageClient(trackerServer, null);
            //获取文件扩展名
            String filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String[] uploadInfo = storageClient.upload_file(file.getBytes(), filenameExtension, null);
            for (String info : uploadInfo) {
                url += "/" + info;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok(url);
    }
}
