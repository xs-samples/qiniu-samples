package io.xs.qiniu.samples.tests;

import java.io.IOException;

import org.junit.Test;

import cn.hutool.core.io.FileUtil;

/**
 * 上传测试类
 *
 * @author xs <tonycody@qq.com>
 * @version Id: UploadTests, v 0.1 2022/6/3 08:29 xs <tonycody@qq.com> Exp $
 */
public class UploadTests {

    /**
     * upload1
     *
     * @throws IOException ioexception
     */
    @Test
    public void upload1() throws IOException {
        // 图片1
        String filePath = FileUtil.getAbsolutePath("1.jpeg");
        UploadHelper.upload(filePath);
    }

    /**
     * upload2
     *
     * @throws IOException ioexception
     */
    @Test
    public void upload2() throws IOException {
        // 图片2
        String filePath = FileUtil.getAbsolutePath("2.jpeg");
        UploadHelper.upload(filePath);
    }
}