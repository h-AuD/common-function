package AuD.common.function.content.excel;

import com.alibaba.excel.context.AnalysisContext;

import java.util.Map;

/**
 * Description: TODO
 *
 * @author AuD/胡钊
 * @ClassName EasyExcelInvokeHeadMap
 * @date 2021/9/8 16:18
 * @Version 1.0
 */
@FunctionalInterface
public interface EasyExcelInvokeHeadMap {

    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context);

}
