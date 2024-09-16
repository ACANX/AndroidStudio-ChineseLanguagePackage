package com.acanx.aslpt.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {


    public static List<File> getSingleFileList(File dir, FileFilter fileFilter){
        List<File> fileList = new ArrayList<File>();
        for(File file : dir.listFiles()){
            if (file.isFile()){
                if(fileFilter.accept(file)){
                    fileList.add(file);
                }
            } else {
                fileList.addAll(getSingleFileList(file, fileFilter));
            }
        }
        return fileList;
    }
}
