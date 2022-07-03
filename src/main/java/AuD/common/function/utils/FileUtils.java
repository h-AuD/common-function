package AuD.common.function.utils;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

/**
 * Description: 文件操作工具类,其中大部分function的parameter-type是{@link File}. <br>
 * -- 这样设计的理由:为什么不设计入参为String-type(i.e.file absolute path)? <br>
 * -- 1.this class 本来用于文件处理,参数设计File,贴切场景语义,由用户提供即可. <br>
 * -- 2.使用绝对路径作为参数,首先需要构建File对象,返回值也不方便设计,用户到底需要String,还是File,并且File是可以获取到String. <br>
 * -- 3.使用File作为参数存在的缺陷:可能导致增加垃圾对象的生命周期. <br>
 * --- eg.入参file1,返回file2,但是file1可能不再需要了,可是file1依旧存在当前栈帧中,并且增加新对象file2. <br>
 *
 * @author AuD/胡钊
 * @ClassName FileUtils
 * @date 2021/9/14 15:04
 * @Version 1.0
 */
public class FileUtils {

    /**
     * 获取文件名称,没有后缀的,eg."D:\test\test.csv" ==> "test"
     * @param file
     * @return
     */
    public static String getNameWithoutExtension(File file){
        String fileName = file.getName();
        int lastIndexOf = fileName.lastIndexOf(".");
        return lastIndexOf!=-1?fileName.substring(0,lastIndexOf):fileName;
    }

    /**
     * 修改文件后缀. <br>
     * 备注:file名称修改完成后,file所代表的文件描述符并没有发送变化.eg: <br>
     * file("D:\test\test.txt") --> newFile("D:\test\test.csv"),"D:\test\test.txt"文件不存在,但是file所代表的对象路径信息依然是"D:\test\test.txt". <br>
     * -- 顺便提一下:{@link File}对象仅仅只是文件的描述,不是代表文件,因为这个文件描述可以是不存在. <br>
     * -- eg: File file = new File("A:\\不存在目录\\不存在文件"),文件是可以不存在的,创建的File对象是实打实存在的. ===> 因为File仅仅只是文件的描述而已. <br>
     * -- 这个就是this function需要有return newFile,原本设计为return void. <br>
     * @param file
     * @param suffix
     * @return 返回修改后新文件对象.
     */
    public static File modifyNameExtension(File file,String suffix){
        String newFilePath = file.getParent()+File.separator+getNameWithoutExtension(file) + "." + suffix;
        File newFile = new File(newFilePath);
        file.renameTo(newFile);
        return newFile;
    }

    /**
     * 删除过期文件. <br>
     * @param fileObj   目标文件对象(可以是文件夹或者文件),不能为null
     * @param unit  时间单位,不能为null
     * @param interval  过期值,至少大于0
     */
    public static void clearFile(File fileObj, ChronoUnit unit, long interval){
        clearFile(fileObj,unit,interval,ZoneId.systemDefault());
    }


    /**
     * 删除过期文件. <br>
     * i.e.根据文件最近一次修改时间与当前时间的差值是否满足指定条件(大于interval). <br>
     * @param fileObj   目标文件对象(可以是文件夹或者文件),不能为null
     * @param unit  时间单位,不能为null
     * @param interval  过期值,至少大于0
     * @param zoneId 时区,如果为null,则默认系统时区
     */
    public static void clearFile(File fileObj, ChronoUnit unit, long interval,ZoneId zoneId){
        // todo dir & unit 不能为null & interval>0
        AssertUtils.conditionMatch(fileObj!=null && unit!=null && interval>0,
                "target dir and unit must not null,and interval at least 0");
        if(zoneId==null){
            zoneId = ZoneId.systemDefault();
        }
        // === 为了应用lambda表达式规范(Variable used in lambda expression should be final or effectively final)
        ZoneId finalZoneId = zoneId;
        // === 判断fileObj是文件夹还是文件,做不同处理(dir:循环处理,file:直接处理)
        if(fileObj.isDirectory()){
            Arrays.asList(fileObj.listFiles()).forEach(file -> clearFile0(file,unit,interval,finalZoneId));
        }else if(fileObj.isFile()){
            clearFile0(fileObj,unit,interval,zoneId);
        }else {
            throw new UnsupportedOperationException("target fileObj("+fileObj.toString()+") "+ "neither dir nor file");
        }
    }

    private static void clearFile0(File file, ChronoUnit unit, long interval,ZoneId zoneId){
        long until = LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), zoneId).until(LocalDateTime.now(), unit);
        if(until>interval){
            file.delete();
        }
    }


}
