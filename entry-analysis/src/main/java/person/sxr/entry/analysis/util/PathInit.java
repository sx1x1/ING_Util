package person.sxr.entry.analysis.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author sxr
 * @date 2021/9/19 2:55 下午
 */
@Configuration
public class PathInit {
    /**
     * 文件后缀常量
     */
    public static final String XLSX = "xlsx";

    @Value("${file.path.read}")
    private String filePathRead;

    @Value("${file.path.write}")
    private String filePathWrite;

    public List<String> getFileNames() {
        File file = new File(filePathRead);
        String[] list = file.list((dir, name) -> name.contains(XLSX));
        assert list != null;
        return Arrays.asList(list);
    }

    @PostConstruct
    public void init() {
        PathConst.filePathRead = filePathRead + File.separator + getFileNames().get(0);
        PathConst.filePathWrite = "result" + filePathWrite + File.separator + getFileNames().get(0);
        PathConst.fileName = getFileNames().get(0);
    }
}
