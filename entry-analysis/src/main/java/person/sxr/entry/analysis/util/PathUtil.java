package person.sxr.entry.analysis.util;

/**
 * @author sxr
 * @date 2021/9/19 2:55 下午
 */
public class PathUtil {

    private PathUtil() {
    }

    /**
     * 桌面文件根路径
     */
    private static final String DESKTOP_PATH = "/Users/sunxiran/Desktop/demo";

    /**
     * 获取文件路径
     *
     * @return 文件路径
     */
    public static String getPath() {
        return DESKTOP_PATH;
    }

}
