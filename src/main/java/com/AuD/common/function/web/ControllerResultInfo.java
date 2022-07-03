package com.AuD.common.function.web;


/**
 * Description: 定义接口(controller/API)响应数据结构。<br>
 *
 * <p> 由三部分/属性组成: code -- 状态码 && msg -- 消息内容 && data -- 数据.
 * <p> 不提供对外的构造函数(i.e.constructor is private),only using static function construct object(reference {@link java.util.Optional})
 * <p>
 *     note:
 *     common status code: 0(success),-1(fail). <br>
 *     1.起初{@link ControllerResultInfo}包含build-this static function,参见{@link ControllerResultInfoBuilder}中static function. <br>
 *     - 其目的希望让其有赋值功能(eg.set data || set msg).  <br>
 *     - 但是同时带来一个隐患问题,就是创建完毕的对象其实可以再次call build_static_function,意味着对象内容是可以发生变化的,这个是不希望有的.  <br>
 *     2.所以{@link ControllerResultInfoBuilder}来了,由它来构建ControllerResultInfo对象,并且删除ControllerResultInfo的赋值方法  <br>
 * </p>
 *
 *
 * @author AuD/胡钊
 * @ClassName ControllerResultInfo
 * @date 2021/2/23 12:03
 * @Version 1.0
 */
public final class ControllerResultInfo<T> {

    /** API响应状态码 */
    private int code;
    /** 响应消息(提示)内容 */
    private String msg;
    /** API响应数据,设置为泛型的目的是希望使用者可以明确指出响应数据类型 */
    private T data;

    /**================== 常量列表 =================================
     * 不推荐这样设置常量,原因在于存在并发数据风险。
     * - 假如2个请求最终都需要返回SUCCESS,并且带有data,
     * - 由于操作同一个对象,有可能会发生其中一个data覆盖另一个data现象
     *=============================================================*/
    //private final static ControllerResultInfo SUCCESS = new ControllerResultInfo(0,"success");
    //private final static ControllerResultInfo INTERNAL_ERROR = new ControllerResultInfo(500,"server internal error");

    ControllerResultInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    ControllerResultInfo(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    /** =============== getter(code & msg) function 必须提供,因为至少需要序列化(eg.jackson序列化依赖getter function) =============== */
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData(){
        return data;
    }

    /** ==================== 设置对象属性data、code、msg ================= */
    // === 讨论:下面function(SET)是否需要?
    // === 从this class的作用(API响应结构体)来看,一旦创建ControllerResultInfo对象,那么code & msg不应该允许被改变,而且每次创建都是新对象.
    // === 提供SET function,是不合需求的
    /*
    public ControllerResultInfo<T> data(T data){
        this.data = data;
        return this;
    }

    public ControllerResultInfo<T> code(int code){
        this.code = code;
        return this;
    }

    public ControllerResultInfo<T> msg(String msg){
        this.msg = msg;
        return this;
    }
     */


}
