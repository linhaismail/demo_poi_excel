package com.example.demo;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReadExcelUtil {


    public static void main(String[] args) {
        // 读取Excel的地址
        String excelFilePath = "D:/linhai/Desktop/work/SymbolInf.xlsx";

        // 图片输出地址
        String picFilePath = "D:/linhai/Desktop/work/SymbolPics";

        // 写出Excel的地址
        String outputExcelFilePath = "D:/linhai/Desktop/work/SymbolExcel.xlsx";

        // 读取 Excel
        Map<String, String> nameEgAndUrl = readExcel(excelFilePath);

        // 处理图片名网址，及把 币种名、图片名对应关系写入Excel
        Map<String, String> nameFileAndUrl = nameFileAndUrl(nameEgAndUrl, outputExcelFilePath);

        Download(nameFileAndUrl, picFilePath);

    }


    /**
     * 处理Map 中的name 使其能够在保存图片服务器时保持原样
     * 把币种英文名，图片名 对应关系存入Excel
     */
    private static Map<String,String> nameFileAndUrl(Map<String, String> nameEgAndUrl, String outputExcelFilePath) {
        Map<String, String> nameFileAndUrl = new HashMap<>();

        // 创建一个Excel对象
        XSSFWorkbook wb = new XSSFWorkbook();

        // 创建表单Sheet对象
        XSSFSheet sheet = wb.createSheet();

        int i = 0;

        for (String name : nameEgAndUrl.keySet()){
            String newName = name
                    .trim()
                    .replace(" ", "_")
                    .replace(".", "_")
                    .replace("/", "_")
                    .replace("-", "_");
            String url = nameEgAndUrl.get(name);
            nameFileAndUrl.put(newName, url);

            XSSFRow row = sheet.createRow(i);
            row.createCell(0).setCellValue(name);
            row.createCell(1).setCellValue(newName);
            row.createCell(2).setCellValue(url);

            i++;
        }

        try {
            FileOutputStream fos = new FileOutputStream(outputExcelFilePath);
            wb.write(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nameFileAndUrl;
    }

    /**
     * 读取 Excel 文件，取得币种英文名称以及相应的币种图片网址
     */
    public static Map<String, String> readExcel(String filePath) {
        try {
            InputStream is = new FileInputStream(filePath);
            // 构建 XSSFWorkbook 对象
            XSSFWorkbook xfb = new XSSFWorkbook(is);
            // 读取第一个 sheet
            XSSFSheet sheet = xfb.getSheetAt(0);
            // 循环输出表格中的内容
            Row row;
            // 币种英文名：下载地址
            Map<String, String> nameEgUrlMap = new HashMap<>();
            for (int i = sheet.getFirstRowNum()+1; i<sheet.getPhysicalNumberOfRows(); i++){
                // 获得行
                row = sheet.getRow(i);
                // 第一列
                short firstCellNum = row.getFirstCellNum();

                // 英文名
                String nameEg = row.getCell(firstCellNum + 1).toString();
                // 网址
                String picUrl = row.getCell(firstCellNum + 2).toString();

                // 文件名与网址的对应关系 Map，用来下载及命名图片
                nameEgUrlMap.put(nameEg, picUrl);

            }
            return nameEgUrlMap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 下载图片，指定图片名称
     */
    public static void Download(Map<String, String> nameAndUrl, String picFilePath) {
        try {
            //开始时间
            Date begindate = new Date();
            // 如果文件夹存在，清空
            File file = new File(picFilePath);
            if (file.exists()){
                deleteDir(picFilePath);
            } else {
                // 如果不存在，创建文件夹
                file.mkdirs();
            }
            for (String key:nameAndUrl.keySet()) {
                //开始时间
                Date begindate2 = new Date();
                // 图片名
                String nameEg = key;
                // 图片下载地址
                String url = nameAndUrl.get(key);
                URL uri = new URL(url);
                // 根据图片地址获得输入流
                InputStream in = uri.openStream();
                // 获得图片格式
                // String picType = url.substring(url.lastIndexOf("."));
                // 图片格式全部设置为 .png
                String picType = ".png";
                // 图片输出流
                FileOutputStream fos = new FileOutputStream(new File(picFilePath+"/"+nameEg+picType));
                byte[] buf = new byte[1024];
                int length = 0;
                System.out.println("开始下载:" + url);
                while ((length = in.read(buf, 0, buf.length)) != -1) {
                    fos.write(buf, 0, length);
                }
                //关闭流
                in.close();
                fos.close();
                System.out.println(nameEg + "下载完成");
                //结束时间
                Date overdate2 = new Date();
                double time = overdate2.getTime() - begindate2.getTime();
                System.out.println("耗时：" + time / 1000 + "s");
            }
            Date overdate = new Date();
            double time = overdate.getTime() - begindate.getTime();
            System.out.println("总耗时：" + time / 1000 + "s");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("下载失败");
        }
    }

    /**
     * 删除文件夹下的所有文件
     */
    public static boolean deleteDir(String path){

        File file = new File(path);
        // 判断待删除目录是否存在
        if(!file.exists()){
            System.err.println("The dir are not exists!");
            return false;
        }
        // 取得当前目录下所有文件和文件夹
        String[] content = file.list();
        for(String name : content){
            File temp = new File(path, name);
            // 判断是否是目录
            if(temp.isDirectory()){
                // 递归调用，删除目录里的内容
                deleteDir(temp.getAbsolutePath());
                // 删除空目录
                temp.delete();
            }else{
                // 直接删除文件
                if(!temp.delete()){
                    System.err.println("Failed to delete " + name);
                }
            }
        }
        return true;
    }

}
