package person.sxr.entry.analysis.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author sxr
 * @date 2021/9/19 1:53 下午
 */
@Data
public class ExcelReadPo {
    /**
     * 行号
     */
    @ExcelIgnore
    private Integer id;

    /**
     * 编号
     */
    @ExcelProperty("编号")
    private Long serialNumber;

    /**
     * 文本内容，其中包含要拆分的词
     */
    @ExcelProperty("文本")
    private String content;

    /**
     * 粗粒度
     */
    @ExcelProperty("粗粒度")
    private String coarseGrained;

    /**
     * 细粒度
     */
    @ExcelProperty("细粒度")
    private String fineGrained;

    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String remarks;

    /**
     * 理由
     */
    @ExcelProperty("理由")
    private String reason;

    /**
     * 有疑问
     */
    @ExcelProperty("有疑问")
    private String anyQuestions;

    /**
     * 总行数
     */
    @ExcelIgnore
    private Integer totalCount;

}
