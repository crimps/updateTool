package com.crimps.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by crimps on 2017/9/11.
 */
public class FileUtil {
    /**
     * 获取文件的MD5码
     *
     * @param filePath
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String getMD5(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fileInputStream));
            IOUtils.closeQuietly(fileInputStream);
            return md5;
        } else {
            return null;
        }

    }

    /**
     * 比较两个文件的MD5码
     *
     * @param filePath1 文件1路径
     * @param filePath2 文件2路径
     * @return MD5码相等返回true, 不等返回false
     */
    public static boolean compareFileMD5(String filePath1, String filePath2) throws FileNotFoundException, IOException{
        String md51 = getMD5(filePath1);
        String md52 = getMD5(filePath2);
        if (md51.equals(md52)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 比较两个文件夹的差异文件
     * @param dir1 目录1
     * @param dir2 目录2
     * @return 目录1中与目录2有差异的文件
     */
    public static List<String> compareDirMD5(String dir1, String dir2) throws IOException{
        List<String> resultList = new ArrayList<>();
        List<String> dir1FileList = new ArrayList<>();
        getAllFilesByDir(dir1, dir1FileList);
        for (String filePath : dir1FileList) {
            if (!compareFileMD5(filePath, replaceDir(dir1, dir2, filePath))) {
                resultList.add(filePath);
            }
        }
        return resultList;
    }

    /**
     * 变更文件路径的目录路径
     * @param dir1 目录路径1
     * @param dir2 目录路径2
     * @param filePath 要变更的文件路径
     * @return 变更之后的文件路径
     */
    private static String replaceDir(String dir1, String dir2, String filePath) {
        return filePath.replace(dir1, dir2);
    }

    /**
     * 获取目录下的所有文件路径
     * @param dirPath 目录路径
     * @param fileList 文件路径
     */
    public static void getAllFilesByDir(String dirPath, List<String> fileList) {
        File dirFile = new File(dirPath);
        if (dirFile.isDirectory()) {
            File[] files = dirFile.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    getAllFilesByDir(file.getAbsolutePath(), fileList);
                } else {
                    fileList.add(file.getAbsolutePath());
                }
            }
        }
    }

    /**
     * 复制文件
     * @param source 源文件
     * @param dest 目标文件
     * @throws IOException
     */
    public static void copyFile(File source, File dest) throws IOException{
        //目标文件不存在，就创建文件
        if (!dest.exists()) {
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
        }
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try{
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }
}
