package com.hp.schemas;

import java.io.*;
import java.util.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * excel读取 工具类
 *
 *      该类使用到了以下jar：
 *      1、poi-ooxml-3.9.jar
 *      2、poi-3.9.jar
 */
public class ImportExcel {

    /**
     * main测试
     */
//    public static void main(String[] args)  {
//        ImportExcel poi = new ImportExcel();
//        Map<String,Integer> map = new HashMap<>();
//        List<List<String>> list = poi.read(poi.toExcel().getProperty("filePath"));
//        String[] value = list.get(0).toArray(new String[0]);
//        if (value != null){
//            for (int i = 0; i < value.length ; i++) {
//                //从配置中找到当前列的匹配，然后取该列上传
//                String str = poi.toExcel().getProperty(value[i]);
//                map.put(str,i);
//            }
//        }
//        System.out.println(map);
//        System.out.println(list.size());
//        if (list != null) {
//            for (int i = 0; i < list.size(); i++) {
//                List<String> cellList = list.get(i);
//                for (int j = 0; j < cellList.size(); j++) {
//                    System.out.print("    " + cellList.get(j));
//                }
//                System.out.println();
//            }
//        }
//    }
    //测试配置文件读取
//    public static void main(String[] args) {
//        ImportExcel poi = new ImportExcel();
//        System.out.println(poi.toExcel());
//        List<List<String>> list = poi.read(poi.toExcel().getProperty("filePath"));
//    }

    //总行数
    private int totalRows = 0;

    //总列数
    private int totalCells = 0;

    //错误信息
    private String errorInfo;

    //构造方法
    ImportExcel() {
    }

    /**
     * 得到总行数
     */
    public int getTotalRows() {
        return totalRows;
    }

    /**
     * 得到总列数
     */
    public int getTotalCells() {
        return totalCells;
    }

    /**
     * 得到错误信息
     */
    public String getErrorInfo() {
        return errorInfo;
    }

    /**
     * 验证excel文件
     */
    private boolean validateExcel(String filePath) {
        // 检查文件名是否为空或者是否是Excel格式的文件
        if (filePath == null || !(CheckExcelUtil.isExcel2003(filePath) || CheckExcelUtil.isExcel2007(filePath))) {
            errorInfo = "文件名不是excel格式";
            return false;
        }
        // 检查文件是否存在
        File file = new File(filePath);
        if (!file.exists()) {
            errorInfo = "文件不存在";
            return false;
        }
        return true;
    }

    /**
     * 根据文件路径读取excel文件
     */
    List<List<String>> read(String filePath) {
        List<List<String>> dataLst = new ArrayList<>();
        InputStream is = null;
        try {
            //验证文件是否合法
            if (!validateExcel(filePath)) {
                System.out.println(errorInfo);
                return null;
            }
            //判断文件的类型，是2003还是2007
            boolean isExcel2003 = true;
            if (CheckExcelUtil.isExcel2007(filePath)) {
                isExcel2003 = false;
            }
            //调用本类提供的根据流读取的方法
            File file = new File(filePath);
            is = new FileInputStream(file);
            dataLst = read(is, isExcel2003);
            is.close();
            is = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataLst;
    }

    /**
     * 根据流读取Excel文件
     *
     * @param inputStream 文件输入流
     * @param isExcel2003 标识是否2003的excel。
     *                        true：是2003的excel，false：是2007的excel
     * @return
     *
     *          如果使用springmvc的MultipartFile接收前端上传的excel文件的话，可以使用MultipartFile的对象，获取上传的文件名称，
     *          然后，可以通过 CheckExcelUtil 类的方法，接收文件名称参数，来判断excel所属的版本。最后再调用此方法来读取excel数据。
     *
     */
    public List<List<String>> read(InputStream inputStream, boolean isExcel2003) {
        List<List<String>> dataLst = null;
        try {
            //根据版本选择创建Workbook的方式
            Workbook wb ;
            if (isExcel2003) {
                wb = new HSSFWorkbook(inputStream);
            } else {
                wb = new XSSFWorkbook(inputStream);
            }
            dataLst = read(wb);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataLst;
    }

    /**
     * 读取数据
     */
    private List<List<String>> read(Workbook wb) {
        List<List<String>> dataLst = new ArrayList<>();
        //得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        //得到Excel的行数
        this.totalRows = sheet.getPhysicalNumberOfRows();
        //得到Excel的列数
        if (this.totalRows >= 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        //循环Excel的行
        for (int r = 0; r < this.totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            List<String> rowLst = new ArrayList<>();
            //循环Excel的列
            for (int c = 0; c < this.getTotalCells(); c++) {
                Cell cell = row.getCell(c);
                String cellValue = "";
                if (null != cell) {
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING); //把所有的Excel内容当做字符串处理
                    // 以下是判断数据的类型
                    switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                            cellValue = cell.getNumericCellValue() + "";
                            break;

                        case HSSFCell.CELL_TYPE_STRING: // 字符串
                            cellValue = cell.getStringCellValue();
                            break;

                        case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                            cellValue = cell.getBooleanCellValue() + "";
                            break;

                        case HSSFCell.CELL_TYPE_FORMULA: // 公式
                            cellValue = cell.getCellFormula() + "";
                            break;

                        case HSSFCell.CELL_TYPE_BLANK: // 空值
                            cellValue = "";
                            break;

                        case HSSFCell.CELL_TYPE_ERROR: // 故障
                            cellValue = "非法字符";
                            break;

                        default:
                            cellValue = "未知类型";
                            break;
                    }
                }
                rowLst.add(cellValue);
            }
            //保存第r行的第c列
            dataLst.add(rowLst);
        }
        return dataLst;
    }

    /**
     *  读取配置文件
     */
    public Properties toExcel(){
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream("D:excelUcmdb.properties"));
            properties.load(new InputStreamReader(inputStream,"gbk"));
            for(String key : properties.stringPropertyNames()){
                System.out.println(key + " : " + properties.getProperty(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}

class CheckExcelUtil {
    /**
     * 检查是否是2003的excel，若是，则返回true
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 检查是否是2007的excel，若是，则返回true
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
}