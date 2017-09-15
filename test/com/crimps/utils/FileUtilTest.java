package com.crimps.utils;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by crimps on 2017/9/11.
 */
public class FileUtilTest {
    @Test
    public void getMD5() throws Exception {
        String path = "D:\\work\\tools\\socket-tool.jar";
        String md5 = FileUtil.getMD5(path);
        System.out.println(md5);
    }

    @Test
    public void compareMD5() throws Exception{
        String path1 = "D:\\work\\tools\\socket-tool.jar";
        String path2 = "D:\\work\\tools\\socket-tool.jar";
        if (FileUtil.compareFileMD5(path1, path2)) {
            System.out.println("#############: 相等");
        } else {
            System.out.println("#############: 不相等");
        }
    }

    @Test
    public void compareDirMD5() throws Exception{
        String path1 = "D:\\work\\URMP_releaseVersion\\devicePlatform4.2.0.3";
        String path2 = "D:\\work\\URMP_releaseVersion\\devicePlatform4.2.0.1";
        List<String> fileList = FileUtil.compareDirMD5(path1, path2);
        for (String filePath : fileList) {
            System.out.println(filePath);
        }
    }

    @Test
    public void getAllFilesByDir() {
        String dirPath = "D:\\360Downloads3";
        List<String> fileList = new ArrayList<>();
        FileUtil.getAllFilesByDir(dirPath, fileList);
        for (String fileStr : fileList) {
            System.out.println(fileStr);
        }
    }

    @Test
    public void copyFile() throws Exception{
        String source = "D:\\work\\imcc\\imccjs23.rar";
        String dest = "D:\\work\\imcc\\test1\\a\\b\\ccimccjs2345.rar";
        File sourceFile = new File(source);
        File destFile = new File(dest);
        FileUtil.copyFile(sourceFile, destFile);
    }
}