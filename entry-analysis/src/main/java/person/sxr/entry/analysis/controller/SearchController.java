package person.sxr.entry.analysis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import person.sxr.entry.analysis.service.ExcelHandleService;

import javax.annotation.Resource;

/**
 * @author sxr
 * @date 2021/9/19 12:52 下午
 */
@RestController
@RequestMapping("/api")
public class SearchController {

    /**
     * 表格处理
     */
    @Resource
    private ExcelHandleService excelHandleService;


    @RequestMapping("/read")
    public void read() {
        excelHandleService.simpleRead();
    }

}
