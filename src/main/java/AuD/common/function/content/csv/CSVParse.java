package AuD.common.function.content.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.util.function.Consumer;

/**
 * Description: TODO
 *
 * @author AuD/胡钊
 * @ClassName CSVParse
 * @date 2021/8/17 19:44
 * @Version 1.0
 */
public final class CSVParse {

    private CSVParse(){

    }

    /**
     * Standard Comma Separated Value format, as for RFC4180 but allowing empty lines.
     *
     * @param absolutePath 需要处理的文件的绝对路径
     * @param action 处理的逻辑(lambda表达式). <br>
     *               其中参数类型({@link CSVRecord}),有获取数据的API,推荐了解这个class的API. <br>
     */
    public static void parseWithFirstRecordAsHeader(String absolutePath, Consumer<? super CSVRecord> action){
        try(Reader reader = new FileReader(absolutePath)) {
            CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader).forEach(action);
        }catch (Exception e){
            throw new CSVParseException("CSV file("+absolutePath+")"+" parse fail,please check carefully your file ",e.getCause());
        }
    }


}
