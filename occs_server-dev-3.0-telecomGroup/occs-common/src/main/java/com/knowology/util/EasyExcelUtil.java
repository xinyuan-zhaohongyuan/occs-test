package com.knowology.util;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 为easyExcel封装工具类(读写)
 * @author xullent
 */
public class EasyExcelUtil {
    /**
     * 文件上传读取
     * @param file
     * @param className
     * @return
     */
    @SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	public static List<Object> readExcel(MultipartFile file , Class className){
        List<Object> list  = new ArrayList<>();
        ExcelReader excelReader = null;
        try {
            InputStream inputStream = file.getInputStream();
            ExcelListener listener = new ExcelListener();
            if((file.getOriginalFilename().endsWith("xlsx"))){
                excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);
            }else if(file.getOriginalFilename().endsWith("xls")){
                excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);
            }

            excelReader.read(new Sheet(1, 1, className));
            list = listener.getDatas();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 文件读取，支持自定义读取sheet的号码，每一个sheet对应一个实体类
     * @param file
     * @param className
     * @param sheetNum sheet编号
     * @return
     */
    @SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	public static List<Object> readExcel(File file , Class className, Integer sheetNum){
        List<Object> list  = new ArrayList<>();
        ExcelReader excelReader = null;
        try {
            InputStream inputStream = new FileInputStream(file);
            ExcelListener listener = new ExcelListener();
            if((file.getName().endsWith("xlsx"))){
                excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);
            }else if(file.getName().endsWith("xls")){
                excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);
            }
            excelReader.read(new Sheet(sheetNum, 1, className));
            list = listener.getDatas();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
