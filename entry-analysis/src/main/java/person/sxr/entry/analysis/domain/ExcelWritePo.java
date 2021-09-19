package person.sxr.entry.analysis.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author sxr
 * @date 2021/9/19 4:00 下午
 */
@Data
@ExcelIgnoreUnannotated
public class ExcelWritePo {

    /**
     * 编号
     */
    private Long serialNumber;

    /**
     * 粗粒度
     */
    @ExcelProperty(value = "粗粒度",index = 2)
    private String coarseGrained;

    /**
     * 细粒度
     */
    @ExcelProperty(value = "细粒度",index = 3)
    private String fineGrained;

}
