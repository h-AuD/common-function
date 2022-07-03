package com.AuD.common.function.content.excel;

import com.alibaba.excel.context.AnalysisContext;

/**
 * Description: TODO
 *
 * @author AuD/胡钊
 * @ClassName EasyExcelOnException
 * @date 2021/9/8 16:19
 * @Version 1.0
 */
@FunctionalInterface
public interface EasyExcelOnException {

    public void onException(Exception exception, AnalysisContext context) throws Exception;

}
