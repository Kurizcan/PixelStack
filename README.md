# PixelStack

PixelStack backend

## 如何使用

### 环境及配置

- Maven
- MySQL
- Redis
- Java（我使用的是 jdk1.8）

#### 修改配置文件

1. 修改 src/main/resources/application.properties 中的 MySQL、Redis 的配置信息
2. 修改 src/main/resources/application.properties 26 行中 设置资源映射地址（若权限不足有可能需要事先创建所要存储的文件夹）
3. 修改 src/main/java/com/pixelstack/ims/service/ImageService.java 中 33 行的存储图片文件夹路径


### 启动

	git clone https://github.com/Lcanboom/PixelStack.git

	mvn clean package

	sh ims.sh ims_pixelStack start

### 测试

访问：

- http://xxxxx:8080/image/getImageList
-  http://xxxxx:8080/hello

## 其他信息

前端请参考：[vue-vuetify-pixelstack: frontend of pixelstack use vue.js](https://github.com/XXXMrG/vue-vuetify-pixelstack)


