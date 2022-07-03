package com.AuD.common.function.web;


/**
 * Description: 通用服务端异常
 * 主要使用于全局异常处理器 {org.springframework.web.bind.annotation.RestControllerAdvice} & { org.springframework.web.bind.annotation.ExceptionHandler }
 * 标准化规范内容.
 * 异常信息包括:errorCode(错误码) & errorMsg(错误信息)
 * --> 可以与{@link ControllerResultInfo}结合使用
 *
 * 用户可以通过继承 this class来完成各个场景下状况信息.
 *
 * @author AuD/胡钊
 * @ClassName AbstractServerException
 * @date 2021/4/16 18:48
 * @Version 1.0
 */
public abstract class AbstractServerException extends RuntimeException {

    /** 错误码 */
    protected int errorCode;

    /** 错误信息 */
    protected String errorMsg;

    /**
     * 通过code & msg构建服务端异常信息. <br>
     * 其中code & msg不可修改,并且 this 仅提供getter方法.
     *
     * @param errorCode
     * @param errorMsg
     */
    protected AbstractServerException(final int errorCode, final String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * 构建"AbstractServerException"对象.   <br>
     * @param errorCode
     * @param errorMsg
     * @return
     */
    public static AbstractServerException of(final int errorCode, final String errorMsg){
        return new AbstractServerException(errorCode,errorMsg) {};
    }

    /* ============================ FUNCTION COMMON =============================================
     * 1.code只有getter
     * -- 一个场景对应一个code,即code具有唯一性.
     * 2.errorMsg只有getter
     * -- msg不是不可以修改(实际情况,msg是可以做相应的调整),但是为什么没有setter?
     * 如果一旦设置setter,意味着某个异常对象是可以修改的,这个是不希望出现的(因为不安全).
     * 所以对于需要调整msg的场景下,eg.相同的code下,msg不一样(虽然code相同,提醒信息是有区别的),
     * 推荐使用new 对象方式构建(eg.在子类实现相关需求,切记:不要有修改对象的隐患).
     * ==========================================================================================*/

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}
