package com.iwanvi.nvwa.web.util;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.util.Lists;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class ExcelUtil {

    public static final String SUFFIX_XLS = "xls";
    public static final String SUFFIX_XLSX = "xlsx";

    /**
     * 导出Excel
     *
     * @param sheetName sheet名称
     * @param title     标题
     * @param values    内容
     * @param wb        HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }

    public static List<List<Object>> getValuesFromExcel(String filePath) {
        List<List<Object>> valueList = Lists.newArrayList();
        if (StringUtils.isNotBlank(filePath)) {
            File excelFile = new File(filePath);
            if (excelFile.exists()) {
                if (excelFile.isFile()) {
                    try {
                        Workbook workbook = null;
                        String suffix = filePath.substring(filePath.lastIndexOf(Constants.SIGN_POINT) + 1);
                        FileInputStream inputStream = new FileInputStream(excelFile);
                        if (SUFFIX_XLS.equals(suffix)) {
                            workbook = new HSSFWorkbook(inputStream);
                        } else if (SUFFIX_XLSX.equals(suffix)) {
                            workbook = new XSSFWorkbook(inputStream);
                        } else {
                            throw new ServiceException("it's not a excel file, can not analysis this file.");
                        }
                        Sheet sheet = workbook.getSheetAt(Constants.INTEGER_0);
                        Row row;
                        Cell cell;
                        List<Object> cellList;
                        DecimalFormat df = new DecimalFormat("0.00");
                        if (sheet != null) {
                            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                                row = sheet.getRow(i);
                                cellList = Lists.newArrayList();
                                if (row != null) {
                                    for (int j = 0; j < row.getLastCellNum(); j++) {
                                        cell = row.getCell(j);
                                        if (cell != null) {
                                            if (CellType.NUMERIC.equals(cell.getCellType())) {
                                                String value = df.format(cell.getNumericCellValue());
                                                if (value.lastIndexOf(".00") > 0) {
                                                    value = value.substring(0, value.lastIndexOf(".00"));
                                                }
                                                cellList.add(j, NumberFormat.getInstance().parse(value));
                                            } else if (CellType.FORMULA.equals(cell.getCellType())) {
                                                String value;
                                                try {
                                                    value = String.valueOf(cell.getNumericCellValue());
                                                    if (value.lastIndexOf(".00") > 0) {
                                                        value = value.substring(0, value.lastIndexOf(".00"));
                                                    }
                                                    cellList.add(j, NumberFormat.getInstance().parse(value));
                                                } catch (IllegalStateException e) {
                                                    cellList.add(j, cell.getRichStringCellValue());
                                                }
                                            } else if (CellType.STRING.equals(cell.getCellType())) {
                                                cellList.add(j, cell.getStringCellValue());
                                            } else {
                                                cellList.add(j, Constants.SIGN_BLANK);
                                            }
                                        }
                                    }
                                }
                                valueList.add(i - 1, cellList);
                            }
                        }
                    } catch (Exception e) {
                        throw new ServiceException(e.getMessage(),e);
                    }
                } else {
                    throw new ServiceException(filePath + " file is dic");
                }
            } else {
                throw new ServiceException(filePath + " file is not existed");
            }
        }
        return valueList;
    }
}
