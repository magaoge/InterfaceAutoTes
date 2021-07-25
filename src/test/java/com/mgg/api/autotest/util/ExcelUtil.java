package com.mgg.api.autotest.util;



import com.mgg.api.autotest.pojo.Case;
import com.mgg.api.autotest.pojo.Rase;
import com.mgg.api.autotest.pojo.WriteBackData;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mgg on 2021/7/5
 */

//读取数据的工具类
public class ExcelUtil {

    public static Map<String , Integer> rowIdentifierRownumMapping = new HashMap<String, Integer>();
    public static Map<String , Integer> cellNameCellnumMapping = new HashMap<String, Integer>();
    public static List<WriteBackData> writeBackData = new ArrayList<WriteBackData>();

    static {
        //一、初始化数据，将Excel文件中所需表的行列索引，行列值对应关系封装好
        loadRownumAndCellnumMapping(PropertiesUtil.getExcelPath(),"parameter");
    }

    //二、作用：读取数据，将所需表中的所有数据对象放入集合
    public static <T> List<T> load(String filePath, String sheetName, Class<T> clazz) {

        List<T> list = new ArrayList<T>();

        try {
            //1.获取Workbook对象，即将整个excol文件读取
            Workbook sheets = WorkbookFactory.create(new File(filePath));
            //2.获取sheet对象，即要操作整个excol文件中的哪个表
            Sheet sheet = sheets.getSheet(sheetName);
            //3.获取表行头信息
            Row titleRow = sheet.getRow(0);
            //4.获取表列数
            int lastCellNum = titleRow.getLastCellNum();

            //5.声明数组，承接表行头信息
            String[] fields = new String[lastCellNum];

            //6.将表行头信息依照所需的格式取出，封装进数组中
            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = titleRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                String titleValues = cell.getStringCellValue();

                titleValues = titleValues.substring(0,titleValues.indexOf("（"));
                fields[i] = titleValues;
            }

            //7.获取表中所有行数，但是方法有一个问题就是，如果表有效数据末尾中有删除的信息，存在空行也会被统计在内
            int lastRowNum = sheet.getLastRowNum();
            //8.根据行头信息，访问反射类方法，为变量赋值，封装对象
            for (int i = 1; i < lastRowNum; i++) {
                //9.循环创建Case对象，承接表中每行的数据
                T obj = clazz.newInstance();
                //10.获取表中第i行的所有信息
                Row dataRow = sheet.getRow(i);
                //11.由于刚才所说的统计问题，对行信息是否为空进行判断，新增判断方法，是空行的话，则循环下一行
                if(dataRow == null || isEmptyRow(dataRow)){
                    continue;
                }
                //12.获取每行的列数据，封装入数据对象
                for (int j = 0; j < lastCellNum ; j++) {
                    /*首次循环为取第2行（看i的值）第1列的值
                    第2次循环为取第2行（看i的值）第2列的值
                    ......
                    */
                    Cell cellValue = dataRow.getCell(j,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cellValue.setCellType(CellType.STRING);
                    //12.取到每行中，每一列的数据值
                    String value = cellValue.getStringCellValue();
                    //13.根据行头信息，访问反射类方法，为变量赋值，封装对象
                    String methodName = "set"+fields[j];
                    Method method = clazz.getMethod(methodName, String.class);
                    //14.反射类对象cs通过映射调用反射类对应方法，将取到的对应值赋值变量
                    method.invoke(obj,value);
                }
//                //循环封装对象进入cases集合
//                  但是这里存在耦合，解耦
//                if(obj instanceof Case){
//                    CaseUtil.cases.add((Case) obj);
//                }else if(obj instanceof Rase){
//                    RaseUtil.rases.add((Rase) obj);
//                }
                //15.每完成一行的赋值后，将该行赋值对象放入集合中
                list.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
            return list;
    }

    //一、获取指定文件，指定表的列名与列索引，行号与行索引的对应关系
    public static void loadRownumAndCellnumMapping(String excelPath, String sheetName) {
        //1.创建输入流
        FileInputStream inp = null;
        try {
            //2.将文件读入输入流中
            inp = new FileInputStream(new File(excelPath));
            //3.通过poi，Workbook来将数据封装为表格的操作对象
            Workbook workbook = WorkbookFactory.create(inp);
            //4.获取需要操作的表格中表名
            Sheet sheet = workbook.getSheet(sheetName);
            //5.获取表格第一行的行头数据
            Row titlerow = sheet.getRow(0);
            //6.判断行头信息是否为空==》7.写判断是否为空的方法
            if (titlerow != null && !isEmptyRow(titlerow)){
                //8.根据表格行头获取表格有效列的列数
                int lastCellNum = titlerow.getLastCellNum();
                //9，循环遍历获取行头列值
                for (int i = 0; i < lastCellNum; i++) {
                    //10.设置列值数据格式
                    Cell cell = titlerow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String value = cell.getStringCellValue();
                    //获取列名，切分字符串，仅获取自己所需要的列名（为了后面获取变量值，每个表单中的行头设置为“变量名+（变量名解释）”的格式）
                    value = value.substring(0,value.indexOf("（"));
                    //11.获取列索引
                    int column = cell.getAddress().getColumn();
                    //12.将列名与列索引的对照关系存入Map数据样式（列名=索引值）
                    cellNameCellnumMapping.put(value,column);
                }
            }
            //13.从第二行开始，获取所有的数据行的行数
            int lastRowNum = sheet.getLastRowNum();
            //14.循环拿到每个数据行的第一列数据，封装进map集合
            for (int i = 1; i <=lastRowNum; i++) {
                //15.从第一行开始是表中的数据
                Row dataRow = sheet.getRow(i);
                //16.第i行的第0列，并且设置列值数据格式
                Cell firstCellOfRow = dataRow.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                firstCellOfRow.setCellType(CellType.STRING);
                String caseId = firstCellOfRow.getStringCellValue();
                //17.获取表中所取数据行的行号作为索引
                int rowNum = dataRow.getRowNum();
                //18.将用例id=索引号数据存入map集合
                rowIdentifierRownumMapping.put(caseId,rowNum);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //19.关闭流对象
        finally {
            if (inp != null){
                try {
                    inp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //7.判断行头信息是否为空值,只要有一个值不为空则都会返回false，全为空则返回true
    private static boolean isEmptyRow(Row dataRow) {
        //7.1根据表格行头获取表格有效列的列数
        int lastCellNum = dataRow.getLastCellNum();
        //7.2循环判断每一列的列值是否为空
        for (int i = 0; i <lastCellNum ; i++) {
            //7.3获取第i列，并且设定第i列如果检测到null值，设置为空处理
            Cell cell = dataRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            //7.4设置列值为String数据类型
            cell.setCellType(CellType.STRING);
            //7.5获取列值
            String stringCellValue = cell.getStringCellValue();
            //7.6对列值进行空值判断，如果不为空，返回false
            if (stringCellValue !=null && stringCellValue.trim().length()>0){
                return false;
            }
        }
        return true;
    }

    //数据回写(一条一条的写)
    private void writeBackeData(String excelPath,
                                String sheetName,
                                String caseId,
                                String cellName,
                                String result) {

        InputStream inp = null;
        OutputStream otp = null;
        try {
            inp = new FileInputStream(new File(excelPath));
            Workbook workbook = WorkbookFactory.create(inp);
            Sheet sheet = workbook.getSheet(sheetName);
            int rowNum = rowIdentifierRownumMapping.get(caseId);
            Row row = sheet.getRow(rowNum);

            int cellNum = cellNameCellnumMapping.get(cellName);
            Cell cell = row.getCell(cellNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            //写入获取到的坐标格子中（此时还在流中）
            cell.setCellValue(result);
            otp = new FileOutputStream(new File(excelPath));
            workbook.write(otp);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (otp != null) {
                    otp.close();
                }
                if (inp != null) {
                    inp.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //三、创建接受执行结果，以及存放数据位置的实体类WriteBackData
    //四、批量回写数据，需要其他方法先去调用writeBackData，来存放执行结果数据（如果不进行批量写，会每写一次都耗费读写一次）
    public static void batchWriteBackeData(String excelPath){
        //1.生成输入输出流
        InputStream inp = null;
        OutputStream otp = null;

        try {
            //2.读取需要输出的文件路径
            inp = new FileInputStream(new File(excelPath));
            Workbook workBook = WorkbookFactory.create(inp);

            for (WriteBackData  writeBackData: writeBackData) {
                //4.获取writeBackData对象中excel文件的表名
                String sheetName = writeBackData.getSheetName();
                //5.生成操作表的具体对象
                Sheet sheet = workBook.getSheet(sheetName);
                //6.获取用例编号（行信息标识，在本次中，行信息标识为用例编号）
                String caseId = writeBackData.getRowIdentifier();
                //6.1.根据行信息，获取行号
                Integer rowNum = rowIdentifierRownumMapping.get(caseId);
                //7.获取行索引
                Row row = sheet.getRow(rowNum);
                //8.获取列名
                String cellName = writeBackData.getCellName();
                //9.获取列索引
                Integer cellNum = cellNameCellnumMapping.get(cellName);
                //10.根据行索引、列索引，设置数据格的数据格式
                Cell cell = row.getCell(cellNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);

                //11.获取该writeBackData用例或者动态参数执行结果
                String result = writeBackData.getResult();
                //12，根据行索引、列索引，将数据写入流
                cell.setCellValue(result);
            }
            //13.输出流到指定文件中
            otp = new FileOutputStream(new File(excelPath));
            workBook.write(otp);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (otp != null) {
                    otp.close();
                }
                if (inp != null) {
                    inp.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
