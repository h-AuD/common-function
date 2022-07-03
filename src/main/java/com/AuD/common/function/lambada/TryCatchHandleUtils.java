package com.AuD.common.function.lambada;


import com.AuD.common.function.utils.AssertUtils;

/**
 * Description: 使用'lambada'表达式简化 try..catch..finally..  <br>
 * ==== 对于不喜欢代码中出现'try..catch..finally..'的用户来说,是个不错的工具。<br>
 *
 * @author AuD/胡钊
 * @ClassName FunctionUtils
 * @date 2021/12/7 16:21
 * @Version 1.0
 */
public class TryCatchHandleUtils {

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(TryCatchHandleUtils.class);

    /**
     * 私有化构造器
     */
    private TryCatchHandleUtils(){}

    /**
     * try...catch...finally...匹配特定异常类型{@link T}
     *
     * @param tClass 指定异常类
     * @param isExecuteCatch 异常类型匹配失败,是否执行'catchHandle'
     * @param <T>
     * @return
     */
    public static <T extends Exception> TCFHandle tcf(Class<T> tClass,boolean isExecuteCatch){
        return (tryHandle,catchHandle,finallyHandle)->{
            AssertUtils.isNotNull(tryHandle,catchHandle,finallyHandle);
            try {
                tryHandle.run();
            }catch (Exception e){
                LOG.error("execute "+" occur exception:{}",e.getMessage());
                boolean match = e.getClass().isAssignableFrom(tClass);
                if(!match){
                    LOG.warn("exception type not match,eTarget:"+tClass.getName()+" & eSource:"+e.getClass().getName());
                }
                if(match || isExecuteCatch){
                    catchHandle.run();
                }
            }finally {
                finallyHandle.run();
            }
        };
    }

    /**
     * try...catch...finally...匹配一般异常类型{@link Exception}
     * @return
     */
    public static TCFHandle tcf(){
        return (tryHandle,catchHandle,finallyHandle)->{
            AssertUtils.isNotNull(tryHandle,catchHandle,finallyHandle);
            try {
                tryHandle.run();
            }catch (Exception e){
                LOG.error("execute "+" occur exception:{}",e.getMessage());
                catchHandle.run();
            }finally {
                finallyHandle.run();
            }
        };
    }

    /**
     * try...catch..(指定异常{@link T})
     * @param tClass
     * @param isExecuteCatch
     * @param <T>
     * @return
     */
    public static <T extends Exception> TCHandle tc(Class<T> tClass,boolean isExecuteCatch){
        return (tryHandle,catchHandle)->{
            AssertUtils.isNotNull(tryHandle,catchHandle);
            try {
                tryHandle.run();
            }catch (Exception e){
                LOG.error("execute "+" occur exception:{}",e.getMessage());
                boolean match = e.getClass().isAssignableFrom(tClass);
                if(!match){
                    LOG.warn("exception type not match,eTarget:"+tClass.getName()+" & eSource:"+e.getClass().getName());
                }
                if(match || isExecuteCatch){
                    catchHandle.run();
                }
            }
        };
    }

    /**
     * try...catch..(通用异常{@link Exception})
     * @return
     */
    public static TCHandle tc(){
        return (tryHandle,catchHandle)->{
            try {
                AssertUtils.isNotNull(tryHandle,catchHandle);
                tryHandle.run();
            }catch (Exception e){
                LOG.error("execute "+" occur exception:{}",e.getMessage());
                catchHandle.run();
            }
        };
    }

    /**
     * try...finally
     * @return
     */
    public static TFHandle tf(){
        return (tryHandle,finallyHandle)->{
            AssertUtils.isNotNull(tryHandle,finallyHandle);
            try {
                tryHandle.run();
            }finally {
                finallyHandle.run();
            }
        };
    }


    /*============================= 以下是函数式接口 ======================*/
    /**
     * Description: try...catch...finally...处理接口
     *
     * @author AuD/胡钊
     * @ClassName BranchHandle
     * @date 2021/12/7 16:19
     * @Version 1.0
     */
    @FunctionalInterface
    public interface TCFHandle{
        /**
         * try...catch...操作
         *
         * @param tryHandle try逻辑块
         * @param catchHandle catch逻辑块
         * @param finallyHandle finally逻辑块
         * @return void
         **/
        void handleTryCatch(Runnable tryHandle, Runnable catchHandle, Runnable finallyHandle);
    }

    /**
     * Description: try...catch...处理接口
     *
     * @author AuD/胡钊
     * @ClassName BranchHandle
     * @date 2021/12/7 16:19
     * @Version 1.0
     */
    @FunctionalInterface
    public interface TCHandle{
        /**
         * try...catch...操作
         *
         * @param tryHandle try逻辑块
         * @param catchHandle catch逻辑块
         * @return void
         **/
        void handleTryCatch(Runnable tryHandle, Runnable catchHandle);
    }

    /**
     * Description: try...catch...处理接口
     *
     * @author AuD/胡钊
     * @ClassName BranchHandle
     * @date 2021/12/7 16:19
     * @Version 1.0
     */
    @FunctionalInterface
    public interface TFHandle{
        /**
         * try...catch...操作
         *
         * @param tryHandle try逻辑块
         * @param finallyHandle finally逻辑块
         * @return void
         **/
        void handleTryCatch(Runnable tryHandle, Runnable finallyHandle);
    }


}
