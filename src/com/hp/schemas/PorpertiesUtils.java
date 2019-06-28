package com.hp.schemas;

import com.alibaba.fastjson.JSONObject;
import java.io.*;

public class PorpertiesUtils {

    /**
     *
     *  为方便格式管理 将配置文件改为Json格式
     *  本类为读取Json格式的工具类
     */

    private PorpertiesUtils(){
        //私有化构造函数
        throw new IllegalAccessError();
    }

    /**
     *    Map<String , Map<String , String>>
     *    CINDA_DB -->  { NAME-->ds_NAME , ..... }
     */
    private static JSONObject getJsonResources(String fileName) {
        File file = new File(fileName);
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fileInputStream,"gbk");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String s = bufferedReader.readLine();
            while (s != null){
                System.out.println(s);
                s = bufferedReader.readLine();
            }
            reader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Json格式转换出错！");
        }
        return null;
    }

//    public static void main(String[] args) {
//        //文件名需使用绝对路径
//        getJsonResources("D://excelUcmdb.json");
//    }


    /**
     * 每一个数据被修改后都需要
     * 相同数据 不做记录
     * 不同数据 做记录
     */


}
