package com.zxh.community;

import org.junit.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/9/2 21:48
 */
public class WkhtmltoxTests {

    @Test
    public void test01() {
        String cmd = "E:\\wkhtmltox\\wkhtmltopdf\\bin\\wkhtmltoimage --quality 75 https://baike.baidu.com/item/CSDN/172150 D:\\dir\\wk-images\\3.png";
        try {
            Runtime.getRuntime().exec(cmd);
            System.out.println("图片已生成！");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
