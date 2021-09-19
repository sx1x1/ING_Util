package person.sxr.entry.analysis.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import person.sxr.entry.analysis.domain.ExcelReadPo;
import person.sxr.entry.analysis.domain.ExcelWritePo;
import person.sxr.entry.analysis.service.EntryAnalysisService;
import person.sxr.entry.analysis.service.ExcelHandleService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sxr
 * @date 2021/9/19 2:21 下午
 */
public class DataListener extends AnalysisEventListener<ExcelReadPo> {

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    public static List<ExcelReadPo> readDataList = new ArrayList<>();
    private static int count = 1;

    @Override
    public void invoke(ExcelReadPo data, AnalysisContext context) {
        System.out.println("解析到一条数据:{ " + data.toString() + " }");
        readDataList.add(data);
        count++;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 解析数据
        EntryAnalysisService entryAnalysisService = new EntryAnalysisService();
        List<ExcelWritePo> analysis = entryAnalysisService.analysis(readDataList);

        // 写入表格
        ExcelHandleService excelHandleService = new ExcelHandleService();
        excelHandleService.simpleWrite(analysis);
        System.out.println("所有数据解析完成！");
        System.out.println(" count ：" + count);
    }

}
