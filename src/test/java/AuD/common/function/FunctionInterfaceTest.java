package AuD.common.function;

import AuD.common.function.lambada.BranchHandleUtils;
import AuD.common.function.lambada.TryCatchHandleUtils;
import org.junit.Test;

/**
 * 测试函数式接口
 */
public class FunctionInterfaceTest {

    /**
     * function测试 -- try..catch..
     */
    @Test
    public void testTryCatch(){
        TryCatchHandleUtils.tcf().handleTryCatch(()->{
            //=== 模拟执行业务
            System.out.println("execute service steps1");
            //=== 模拟出现异常
            System.out.println(1/0);
            System.out.println("execute service steps2");
        },()->{
            System.out.println("error handle");
        },()->{
            System.out.println("finally handle");
        });
    }

    /**
     * function测试 -- if..else..
     */
    @Test
    public void testBranch(){
        // true
        BranchHandleUtils.branchHandle(true).trueOrFalseHandle(()->{
            System.out.println("is true execute service");
        },()->{
            System.out.println("is false execute service");
        });
        // null_test
        BranchHandleUtils.branchHandle(false).trueOrFalseHandle(null,null);

    }

}
