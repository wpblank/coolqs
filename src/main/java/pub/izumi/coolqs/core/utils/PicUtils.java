package pub.izumi.coolqs.core.utils;

import java.io.*;
import java.net.URL;

public class PicUtils {

    /**
     * 下载图片，并按照指定的路径存储
     */
    public static void downImage(String url, String fileName, String filePath) {
        // 网络请求所需变量
        try {
            //获取输入流
            BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
            //创建文件流
            File file = new File(filePath + fileName);
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            //缓冲字节数组
            byte[] data = new byte[2048];
            int length = in.read(data);
            while (length != -1) {
                out.write(data, 0, data.length);
                length = in.read(data);
            }
            System.out.println("正在执行下载任务：当前正在下载图片" + fileName);
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
