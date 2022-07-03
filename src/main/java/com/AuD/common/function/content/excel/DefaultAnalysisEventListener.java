package com.AuD.common.function.content.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;


/**
 * Description: TODO
 *
 * @author AuD/胡钊
 * @ClassName DefaultAnalysisEventListener
 * @date 2021/8/13 15:17
 * @Version 1.0
 */
class DefaultAnalysisEventListener<T> extends AnalysisEventListener<T> {

    /* invoke & afterAll 必传参数 */
    private EasyExcelInvoke invoke;

    private EasyExcelAfterAll afterAll;

    private EasyExcelInvokeHeadMap invokeHeadMap;

    private EasyExcelOnException onException;

    private DefaultAnalysisEventListener(EasyExcelInvoke invoke,EasyExcelAfterAll afterAll,EasyExcelInvokeHeadMap invokeHeadMap,EasyExcelOnException onException){
        this.invoke = invoke;
        this.afterAll = afterAll;
        this.invokeHeadMap = invokeHeadMap;
        this.onException = onException;
    }



    @Override
    public void invoke(T data, AnalysisContext context) {
        invoke.invoke(data,context);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        afterAll.doAfterAllAnalysed(context);
    }


    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        if(invokeHeadMap!=null){
            invokeHeadMap.invokeHeadMap(headMap,context);
        }else {
            super.invokeHeadMap(headMap, context);
        }

    }

    /**
     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        if(onException!=null){
            onException.onException(exception,context);
        }else {
            super.onException(exception, context);
        }

    }

    static DefaultAnalysisEventListener of(EasyExcelInvoke invoke, EasyExcelAfterAll afterAll,EasyExcelInvokeHeadMap invokeHeadMap,EasyExcelOnException onException){
        return new DefaultAnalysisEventListener(invoke,afterAll,invokeHeadMap,onException);
    }

}
