package person.sxr.entry.analysis.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import person.sxr.entry.analysis.domain.ExcelReadPo;
import person.sxr.entry.analysis.domain.ExcelWritePo;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.example.BaiduBaikePageProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author sxr
 * @date 2021/9/19 1:48 下午
 */
@Service
public class EntryAnalysisService {
    /**
     * 拆分词条
     *
     * @param readDataList 读入的数据
     * @return 要写入的数据
     */
    public List<ExcelWritePo> analysis(List<ExcelReadPo> readDataList) {
        Map<Long, List<String>> contentMap = readDataList.stream()
                .collect(Collectors.toMap(ExcelReadPo::getSerialNumber, t -> contentSubstring(t.getContent())));

        Map<Long, List<String>> map = search(contentMap);
        readDataList.forEach(data -> data.setCoarseGrained(map.get(data.getSerialNumber()).stream().filter(a -> a.length() > 1).collect(Collectors.joining("/"))));

        return readDataList.stream().map(data -> {
            ExcelWritePo writePo = new ExcelWritePo();
            writePo.setSerialNumber(data.getSerialNumber());
            writePo.setFineGrained(data.getFineGrained());
            writePo.setCoarseGrained(data.getCoarseGrained());
            return writePo;
        }).collect(Collectors.toList());
    }

    /**
     * 查询百度百科
     *
     * @param contentMap 按照编号，拆分后的词条去查
     * @return 能拆分的词条
     */
    public Map<Long, List<String>> search(Map<Long, List<String>> contentMap) {
        Spider spider = Spider.create(new BaiduBaikePageProcessor()).thread(1000);
        String urlTemplate = "http://baike.baidu.com/search/word?word=%s&pic=1&sug=1&enc=utf8";

        Map<Long, List<String>> map = new HashMap<>(contentMap.size());
        for (Map.Entry<Long, List<String>> entry : contentMap.entrySet()) {
            List<String> strings = new ArrayList<>();
            List<String> value = entry.getValue();
            List<String> entryList = new ArrayList<>();

            for (String str : value) {
                if (str.length() > 1) {
                    entryList.add(String.format(urlTemplate, str));
                } else {
                    // 单个字符跳过查询
                    strings.add(str);
                }
            }
            List<ResultItems> result = spider.getAll(entryList);
            for (ResultItems item : result) {
                String name = (String) item.getAll().get("name");
                if (name != null) {
                    strings.add(name);
                }
            }
            map.put(entry.getKey(), strings);
        }
        spider.close();
        return map;
    }

    /**
     * 拆分词组
     *
     * @param content 文本内容
     * @return 拆分后的词组
     */
    public List<String> contentSubstring(String content) {
        String keyWord = StringUtils.substring(content, content.indexOf('【') + 1, content.indexOf('】'));
        // 核心算法
        // 拆分成词组
        List<String> entryList = new ArrayList<>();
        int length = keyWord.length();
        for (int i = 0; i < length - 1; i++) {
            for (int j = i + 1; j < length; j++) {
                entryList.add(keyWord.substring(i, j + 1));
            }
        }
        // 拆分成单个字符
        char[] chars = keyWord.toCharArray();
        for (char c : chars) {
            entryList.add(String.valueOf(c));
        }
        return entryList;
    }
}
