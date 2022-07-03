package AuD.common.function.content.excel;

import AuD.common.function.utils.AssertUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * <p>
 * Excel文件处理器. === 封装了{@link EasyExcel}相关方法 <br>
 * - 只能处理excel格式的文件,即文件后缀为"xls"或者"xlsx" <br>
 * - EasyExcel官方文档:https://www.yuque.com/easyexcel/doc/ <br>
 * </p>
 *
 * <p>
 * Attention: <br>
 * - 1.仅仅只能处理以".xls" or ".xlsx"结尾的文件.
 * - 2.
 * </p>
 *
 * @author AuD/胡钊
 * @ClassName ExcelHandleByRead
 * @date 2021/4/1 17:05
 * @Version 1.0
 */
public final class EasyExcelReadProcessor {

    private final static String SUFFIX_XLS = ".xls";
    private final static String SUFFIX_XLSX= ".xlsx";


    /**================================ Attention ==========================================
     * eg.如果excel只有一列数据(String文本),当如下代码读取excel时,会发生异常,类型转换异常:
     * String不能转换成[C ===> 即String不能转换为字符数组。为什么出现这样情况?
     * - EasyExcel.read(excelPath,String.class,new ExcelDataListener()).sheet().doRead()
     * - 其中ExcelDataListener的泛型类型是String
     *
     * 结论:基于debug得出来的。
     * EasyExcel将一行数据转换为JavaObject的过程,是一个单元格数据对应JavaObject一个的属性,
     * 而String其中的一个属性则是char[]。
     * 综上:如果只有一列数据的excel,推荐使用不构建对象方式处理,即EasyExcel默认采取LinkHashMap
     *======================================================================================*/


    /**
     *
     * @param absolutePath excel文件绝对路径,不能为null
     * @param invoke    读取每行数据逻辑,不能为null
     * @param doAfterAll    读取完毕后的逻辑,如果为null,则do nothing。
     * @param invokeHeadMap 读取第一行数据逻辑,如果为null,则跳过第一行(表头).
     * @param onException   读取过程发送异常处理逻辑,如果为null,则默认抛出异常,并终止读取.
     */
    public static void handle(String absolutePath,
                              EasyExcelInvoke<Map<Integer,String>> invoke,
                              EasyExcelAfterAll doAfterAll,
                              EasyExcelInvokeHeadMap invokeHeadMap,
                              EasyExcelOnException onException){
        AssertUtils.notNull(invoke,"invoke is not null");
        if(doAfterAll==null){
            doAfterAll = context ->{} ;
        }
        handle(absolutePath,DefaultAnalysisEventListener.of(invoke,doAfterAll,invokeHeadMap,onException),null);
    }

    /**
     *
     * @param absolutePath excel文件绝对路径,不能为null
     * @param clazz
     * @param <T> 每行数据被解析并封装为类型T对象
     * @param invoke 读取每行数据逻辑,不能为null
     * @param doAfterAll 读取完毕后的逻辑,如果为null,则do nothing。
     * @param invokeHeadMap 读取第一行数据逻辑,如果为null,则跳过第一行(表头).
     * @param onException 读取过程发送异常处理逻辑,如果为null,则默认抛出异常,并终止读取.
     */
    public static <T> void handle(String absolutePath,
                                  Class<T> clazz,
                                  EasyExcelInvoke<? super T> invoke,
                                  EasyExcelAfterAll doAfterAll,
                                  EasyExcelInvokeHeadMap invokeHeadMap,
                                  EasyExcelOnException onException){
        AssertUtils.notNull(invoke,"invoke is not null");
        if(doAfterAll==null){
            doAfterAll = context ->{} ;
        }
        handle(absolutePath,DefaultAnalysisEventListener.of(invoke,doAfterAll,invokeHeadMap,onException),clazz);
    }




    /**
     * 该方法仅仅读取第一个sheet. <br>
     * 注意:listener每次读取都需要new,即调用该方法类似如下形式: <br>
     * handleFirstSheet(filePath,new AnalysisEventListener(),clazz) <br>
     *
     * @param absolutePath  excel文件路径(推荐使用绝对路径)
     * @param listener
     * @param clazz 如果不为null,则clazz需要与listener中的泛型保证一致
     */
    public static <T> void handle(String absolutePath,AnalysisEventListener listener,Class<T> clazz){
        AssertUtils.notNull(listener,"parameter listener is null");
        AssertUtils.notNull(absolutePath,"parameter filePath is null");
        AssertUtils.isTrue(verifyFile(absolutePath),"file is not excel format");
        EasyExcel.read(absolutePath,clazz,listener).sheet().doRead();
        /*================================Attention===============================
         * 以下判断其实可以不需要的,因为如果clazz==null,则采取默认不创建对象方式读取
         * if(clazz==null){
         *      EasyExcel.read(filePath,listener).sheet().doRead();
         * }else {
         *      EasyExcel.read(filePath,clazz,listener).sheet().doRead();
         * }
         *=========================================================================*/
    }



    /**
     * 该方法读取第多个sheet,通过不创建对象(数据模型)的情况下. <br>
     *
     * @param absolutePath excel文件绝对路径,不能为null
     * @param sheets    sheet编号,同理于数组 从0开始
     * @param invoke    读取每行数据逻辑,不能为null
     * @param doAfterAll    读取完毕后的逻辑,如果为null,则do nothing。
     * @param invokeHeadMap 读取第一行数据逻辑,如果为null,则跳过第一行(表头).
     * @param onException   读取过程发送异常处理逻辑,如果为null,则默认抛出异常,并终止读取.
     */
    public static void handleMultiSheet(String absolutePath,
                              List<Integer> sheets,
                              EasyExcelInvoke<Map<Integer,String>> invoke,
                              EasyExcelAfterAll doAfterAll,
                              EasyExcelInvokeHeadMap invokeHeadMap,
                              EasyExcelOnException onException){
        AssertUtils.notNull(invoke,"invoke is not null");
        if(doAfterAll==null){
            doAfterAll = context ->{} ;
        }
        handleMultiSheet(absolutePath,DefaultAnalysisEventListener.of(invoke,doAfterAll,invokeHeadMap,onException),null,sheets);
    }

    /**
     *
     * @param absolutePath excel文件绝对路径,不能为null
     * @param clazz
     * @param <T> 每行数据被解析并封装为类型T对象
     * @param invoke 读取每行数据逻辑,不能为null
     * @param doAfterAll 读取完毕后的逻辑,如果为null,则do nothing。
     * @param invokeHeadMap 读取第一行数据逻辑,如果为null,则跳过第一行(表头).
     * @param onException 读取过程发送异常处理逻辑,如果为null,则默认抛出异常,并终止读取.
     */
    public static <T> void handleMultiSheet(String absolutePath,
                                  Class<T> clazz,
                                  List<Integer> sheets,
                                  EasyExcelInvoke<? super T> invoke,
                                  EasyExcelAfterAll doAfterAll,
                                  EasyExcelInvokeHeadMap invokeHeadMap,
                                  EasyExcelOnException onException){
        AssertUtils.notNull(invoke,"invoke is not null");
        if(doAfterAll==null){
            doAfterAll = context ->{} ;
        }
        handleMultiSheet(absolutePath,DefaultAnalysisEventListener.of(invoke,doAfterAll,invokeHeadMap,onException),clazz,sheets);
    }


    /**
     * 该方法读取第多个sheet
     * 注意:
     * 1.listener每次读取都需要new
     *
     * @param absolutePath
     * @param listener
     * @param clazz
     * @param sheets    sheet编号,从0开始
     */
    public static <T> void handleMultiSheet(String absolutePath,AnalysisEventListener listener,Class<T> clazz,List<Integer> sheets){
        AssertUtils.notNull(listener,"parameter listener is null");
        AssertUtils.notNull(absolutePath,"parameter filePath is null");
        AssertUtils.isTrue(verifyFile(absolutePath),"file is not excel format");
        ExcelReader excelReader = null;
        List<ReadSheet> sheetList= new ArrayList<>();
        try {
            excelReader = EasyExcel.read(absolutePath).build();
            sheets.forEach((index)->{
                // 这里为了简单 所以注册了 同样的head和Listener,即可以根据实际需求场景使用不同的head和Listener
                sheetList.add(EasyExcel.readSheet(index).head(clazz).registerReadListener(listener).build());
            });
            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(sheetList);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件,到时磁盘会崩的
                excelReader.finish();
            }
        }
    }

    /** 验证文件是否符合要求,必须是excel格式(i.e.文件后缀为"xls"或者"xlsx") */
    private static boolean verifyFile(String path){
        if(path.endsWith(SUFFIX_XLS) || path.endsWith(SUFFIX_XLSX)){
            return true;
        }
        return false;
    }

}
