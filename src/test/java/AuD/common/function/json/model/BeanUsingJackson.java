package AuD.common.function.json.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Description: TODO
 *
 * @author AuD/胡钊
 * @ClassName BeanUsingJackson
 * @date 2021/9/23 9:33
 * @Version 1.0
 */
@Data
public class BeanUsingJackson {

    private String title;

    private int isShowCoverPic;

    private boolean flag;

    private LocalDateTime ctime;

}
