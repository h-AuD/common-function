package com.AuD.common.function.lambada;

import com.AuD.common.function.utils.AssertUtils;

/**
 * Description: 使用'lambada'表达式简化 if..else..  <br>
 *
 * @author AuD/胡钊
 * @ClassName FunctionUtils
 * @date 2021/12/7 16:21
 * @Version 1.0
 */
public class BranchHandleUtils {

    /**
     * 私有化构造器
     */
    private BranchHandleUtils(){}


    /**
     *
     * @param flag
     * @return
     */
    public static BranchHandle branchHandle(boolean flag){
        return (trueHandle,falseHandle)->{
            AssertUtils.isNotNull(trueHandle,falseHandle);
            if(flag){
                trueHandle.run();
            }else {
                falseHandle.run();
            }
        };
    }


    /**
     * Description: 分支处理接口
     *
     * @author AuD/胡钊
     * @ClassName BranchHandle
     * @date 2021/12/7 16:19
     * @Version 1.0
     */
    @FunctionalInterface
    public interface BranchHandle {

        /**
         * 分支操作
         *
         * @param trueHandle 为true时要进行的操作
         * @param falseHandle 为false时要进行的操作
         * @return void
         **/
        void trueOrFalseHandle(Runnable trueHandle, Runnable falseHandle);

    }

}
