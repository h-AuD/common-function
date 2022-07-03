package com.AuD.common.function.excel_csv;

import com.AuD.common.function.content.csv.CSVParse;
import com.AuD.common.function.content.excel.EasyExcelReadProcessor;
import com.AuD.common.function.excel_csv.model.ExcelModel1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Description: TODO
 *
 * @author AuD/胡钊
 * @ClassName EasyExcel_CSV_test
 * @date 2021/8/30 16:32
 * @Version 1.0
 */
public class EasyExcel_CSV_test {

    // =================== easyExcel test demo =====================

    @Test
    public void testExcelHandleByRead(){
        List<ExcelModel1> list = new ArrayList<>();
        EasyExcelReadProcessor.handle("",ExcelModel1.class,((data, context) -> {
            list.add(data);
        }),(context)->{},null,null);
        System.out.println(list);
    }


    // ==================== CSV test demo ====================================

    @Test
    public void testCSVParse(){
        AtomicLong num = new AtomicLong();
        CSVParse.parseWithFirstRecordAsHeader("",(csvRecord -> {
            num.set(csvRecord.getRecordNumber());
        }));
        System.out.println("=============="+num);
    }

}
