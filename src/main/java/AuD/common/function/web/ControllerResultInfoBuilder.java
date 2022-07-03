package AuD.common.function.web;


/**
 * Description: API响应数据结构对象构造器。<br>
 * <p>
 *     note: ===> 0 --> success,-1 -->fail && 大部分function返回this object,i.e.支持级联赋值,eg,obj.f1().f2(). <br>
 *     1.{@link ControllerResultInfoBuilder#success()} & {@link ControllerResultInfoBuilder#fail(String)} 构建通用的响应体结构.  <br>
 *     2.对于error场景下,提供通过{@link AbstractServerException}来构建对象,推荐使用这样做法,可以通过异常来映射响应状态.  <br>
 * </p>
 *
 *
 * @author AuD/胡钊
 * @ClassName ControllerResultInfo
 * @date 2021/2/23 12:03
 * @Version 1.0
 */
public final class ControllerResultInfoBuilder {

    /** 默认的success响应状态码 */
    private final static int SUCCESS_CODE = 0;

    /** 默认的success响应message */
    private final static String SUCCESS_DEFAULT_MSG = "success";

    /** 默认的fail响应状态码 */
    private final static int FAIL_CODE = -1;


    private ControllerResultInfoBuilder() {

    }


    /* =============================================================================
     * 构建ControllerResultInfo对象
     * Note:
     * 1.禁止使用HTTP内部错误(eg.404,500)
     * - 因为这种code太抽象,不能明确指定具体原因(到底是HTTP错误?还是API error)
     * 2.
     * =============================================================================*/

    /**
     * 构建状态码
     * @param code
     * @param msg
     * @return
     */
    public static ControllerResultInfo of(int code, String msg){
        return new ControllerResultInfo(code,msg);
    }

    /**
     * 构建状态码
     * @param code
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ControllerResultInfo<T> of(int code, String msg, T data){
        return new ControllerResultInfo(code,msg,data);
    }

    /**
     * 创建success状态码,使用默认的 code & msg
     * @return
     */
    public static ControllerResultInfo success(){
         return of(SUCCESS_CODE,SUCCESS_DEFAULT_MSG);
    }


    /**
     * 创建success状态码,使用默认的 code & msg,并且设置响应数据
     *
     * @param data
     * @return
     */
    public static <T> ControllerResultInfo success(T data){
        return of(SUCCESS_CODE,SUCCESS_DEFAULT_MSG,data);
    }


    /**
     * 创建fail状态码
     * @param failMsg
     * @return
     */
    public static ControllerResultInfo fail(String failMsg){
        return of(FAIL_CODE,failMsg);
    }


    /**
     * 使用异常{@link AbstractServerException}构建状态码
     * @param exception
     * @return
     */
    public static ControllerResultInfo error(AbstractServerException exception){
        return of(exception.errorCode,exception.errorMsg);
    }




}
