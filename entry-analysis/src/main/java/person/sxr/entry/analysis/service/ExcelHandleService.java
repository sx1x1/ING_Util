package person.sxr.entry.analysis.service;

import com.alibaba.excel.EasyExcel;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import person.sxr.entry.analysis.domain.ExcelReadPo;
import person.sxr.entry.analysis.domain.ExcelWritePo;
import person.sxr.entry.analysis.listener.DataListener;
import person.sxr.entry.analysis.util.PathConst;

import java.io.File;
import java.util.List;

/**
 * @author sxr
 * @date 2021/9/19 1:50 下午
 */
@Service
public class ExcelHandleService {

    /**
     * 读表格
     */
    public void simpleRead() {
        String fileReadName = PathConst.filePathRead;
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileReadName, ExcelReadPo.class, new DataListener()).sheet().doRead();
    }

    /**
     * 将数据写到表格
     *
     * @param data 数据
     */
    @SneakyThrows
    public void simpleWrite(List<ExcelWritePo> data) {
        String fileWriteName = PathConst.filePathWrite;
//        File file = new File(PathConst.fileName);
//        if (!file.exists()) {
//            file.createNewFile();
//        }
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileWriteName, ExcelWritePo.class).sheet("sheet1").doWrite(data);
    }

}
