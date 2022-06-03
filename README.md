# 七牛云对象存储（Kodo）示例

## 上传图片及刷新 CDN 文件缓存（同名同路径）

```shell
# 上传图片1
io.xs.qiniu.samples.tests.UploadTests.upload1
# 上传图片2
io.xs.qiniu.samples.tests.UploadTests.upload2
```

```shell
# 流程
覆盖上传 > CDN 刷新上传文件
```

> 图片地址：http://tests.0512.host/test.jpeg