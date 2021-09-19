package person.sxr.entry.analysis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import person.sxr.entry.analysis.service.ExcelHandleService;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.example.BaiduBaikePageProcessor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("/search")
    public Map<String,Boolean> searchEntry(){

        Spider spider = Spider.create(new BaiduBaikePageProcessor()).thread(20);
        String urlTemplate = "http://baike.baidu.com/search/word?word=%s&pic=1&sug=1&enc=utf8";

        List<String> list = new ArrayList<String>();

        list.add(String.format(urlTemplate,"的微城市名称"));
        list.add(String.format(urlTemplate,"太阳能"));
        list.add(String.format(urlTemplate,"互联网"));
        list.add(String.format(urlTemplate,"地热发电"));
        List<ResultItems> result = spider.getAll(list);
        System.out.println(result);
        for (ResultItems item : result) {
            System.out.println(item.getAll());
        }
        spider.close();
        return new HashMap<>(4);

    }

    @RequestMapping("/read")
    public void read(){
        excelHandleService.simpleRead();
    }



}
