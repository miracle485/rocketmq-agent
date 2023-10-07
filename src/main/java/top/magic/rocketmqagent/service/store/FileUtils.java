package top.magic.rocketmqagent.service.store;

import java.io.File;

public class FileUtils {
    private FileUtils() {}

    /**
     * 检查存储文件的前置路径是否都已经创建，如果没有创建，创建这个路径
     */
    public static boolean checkAndCreateFilePath(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        }
        return file.mkdirs();
    }

    /**
     * 返回一个20位的文件名，如果不足20位，就使用前导0来填充
     */
    public static String formatFileName(long offset) {
        return String.format("%020d", offset);
    }
}
