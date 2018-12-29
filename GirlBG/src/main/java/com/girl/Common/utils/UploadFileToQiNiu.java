package com.girl.Common.utils;

import com.girl.Common.constants.Constant;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class UploadFileToQiNiu {


    private final static Configuration cfg = new Configuration(Zone.zone0());

    public static void uploadByFile(File temFile, String bucketname, String path) throws QiniuException {
        Auth auth = Auth.create(Constant.QINIU_ACCESSKEY, Constant.QINIU_SECRETKEY);

        UploadManager uploadManager = new UploadManager(cfg);
        String token = getUpToken(bucketname, auth);
        uploadManager.put(temFile, path, token);
    }

    public static void uploadByByte(byte[] bytes, String bucketname, String path) throws QiniuException {
        Auth auth = Auth.create(Constant.QINIU_ACCESSKEY, Constant.QINIU_SECRETKEY);

        UploadManager uploadManager = new UploadManager(cfg);
        String token = getUpToken(bucketname, auth);
        uploadManager.put(bytes, path, token);
    }

    /**
     * 七牛流式上传
     *
     * @param inputStream
     * @param path
     * @throws QiniuException
     */
    public static void uploadByInputStream(InputStream inputStream, String bucketname, String path) throws QiniuException {
        Auth auth = Auth.create(Constant.QINIU_ACCESSKEY, Constant.QINIU_SECRETKEY);

        UploadManager uploadManager = new UploadManager(cfg);
        String token = getUpToken(bucketname, auth);

        uploadManager.put(inputStream, path, token, null, null);
    }

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    private static String getUpToken(String bucketname, Auth auth) {

        return auth.uploadToken(bucketname);
    }

    /**
     * 创建云存储文件名格式yyyyMMdd/HHmmssSSS.type
     *
     * @param type
     * @return
     */
    public static String getUploadFileName(String type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Random random = new Random();
        String now = sdf.format(new Date()) + getFixLenthString(4);

        return now.substring(0, 8) + "/" + now.substring(8, now.length() - 1) + "." + type;
    }
    /*
     * 返回长度为【strLength】的随机数，在前面补0
     */
    private static String getFixLenthString(int strLength) {

        Random rm = new Random();

        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);

        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);

        // 返回固定的长度的随机数
        return fixLenthString.substring(1, strLength + 1);
    }
    /**
     * 将base64位图片转换为图片格式并上传七牛云，同时返回图片访问路径
     *
     * @param base64Img
     * @return
     */
    public static String uploadQiNiu(String base64Img) {
        byte[] bytes = Base64ImageUtil.GenerateImage(base64Img);

        String type = "jpg";

        String qiniuFileName = getUploadFileName(type);
        try {
            uploadByByte(bytes, "images", qiniuFileName);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return Constant.QINIU_DOMAINNAME + qiniuFileName;
    }
}
