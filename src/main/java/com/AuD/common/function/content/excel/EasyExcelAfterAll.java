package com.AuD.common.function.content.excel;

import com.alibaba.excel.context.AnalysisContext;

/**
 * Description: TODO
 *
 * @author AuD/胡钊
 * @ClassName EasyExcelAfterAll
 * @date 2021/8/17 20:47
 * @Version 1.0
 */
@FunctionalInterface
public interface EasyExcelAfterAll{

    public void doAfterAllAnalysed(AnalysisContext context);

}