package io.xs.qiniu.samples.tests;

import java.io.IOException;

import com.qiniu.cdn.CdnManager;
import com.qiniu.cdn.CdnResult;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import lombok.experimental.UtilityClass;

/**
 *  上传帮助类
 *
 * @author xs <tonycody@qq.com>
 * @version Id: UploadHelper, v 0.1 2022/6/3 22:54 xs <tonycody@qq.com> Exp $
 */
@UtilityClass
public class UploadHelper {
    /** 访问密钥 */
    private static final String ACCESS_KEY  = "aP4ZNEq77A-xC7SVVYkBPtX53VbVEHvacmeTncSR";
    /** 秘密密钥 */
    private static final String SECRET_KEY  = "kiJzLOBBRC8S-WUCcGYE1ppC5VUAyByMnBLZ_EBn";
    /** bucket名称 */
    private static final String BUCKET_NAME = "xs-tests";
    //上传到七牛后保存的文件名
    private static final String KEY         = "test.jpeg";

    /** url */
    private static final String URL         = "http://tests.0512.host/" + KEY;

    /** 身份验证 */
    private Auth                auth;
    /** c */
    private Configuration       configuration;
    /** 上传管理 */
    private UploadManager       uploadManager;
    /** cdn管理 */
    private CdnManager          cdnManager;

    static {
        //密钥配置
        auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        // 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
        Region r = Region.autoRegion();
        configuration = new Configuration(r);
        //创建上传对象
        uploadManager = new UploadManager(configuration);
        cdnManager = new CdnManager(auth);
    }

    // 覆盖上传
    private String getUpToken() {
        //<bucket>:<key>，表示只允许用户上传指定key的文件。在这种格式下文件默认允许“修改”，已存在同名资源则会被本次覆盖。
        //如果希望只能上传指定key的文件，并且不允许修改，那么可以将下面的 insertOnly 属性值设为 1。
        //第三个参数是token的过期时间
        return auth.uploadToken(BUCKET_NAME, KEY, 3600, null);
    }

    /**
     * 上传
     *
     * @param filePath 文件路径
     * @throws IOException ioexception
     */
    public void upload(String filePath) throws IOException {
        try {
            //调用put方法上传，这里指定的key和上传策略中的key要一致
            Response res = uploadManager.put(filePath, KEY, getUpToken());
            //打印返回的信息
            System.out.println(res.bodyString());
            fileRefresh();
            System.out.println("link url => " + URL);
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
    }

    /**
     * 文件更新
     *
     */
    private void fileRefresh() {
        //待刷新的链接列表
        String[] urls = new String[] { URL };
        try {
            //单次方法调用刷新的链接不可以超过100个
            CdnResult.RefreshResult result = cdnManager.refreshUrls(urls);
            System.out.println(result.code);
            //获取其他的回复内容
        } catch (QiniuException e) {
            System.err.println(e.response.toString());
        }
    }
}