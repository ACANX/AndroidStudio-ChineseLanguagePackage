# 如何(参与)翻译汉化包
 授人以鱼不如授人以渔



### 汉化方法：
* 1.备份lib/resource_en.jar 文件；
* 2.用压缩软件解压resource_en.jar文件；
* 3.将resource_en/messages/ 目录下的 *.properties文件打开
* 4.将每行 “=”后的英文替换成中文：
* 5.保存替换后的文件;并使用JDK中的native2ascii.exe处理保存的文件
* 6.使用如下命令 ：
```
native2ascii IdeBundle.properties tmp.properties
```
* 7.将tmp.properties移动到其它目录，并重命名为IdeBundle.properties；
* 8.选中resources_en.jar右键用打开方式 选择用WinRAR，然后打开IdeBundle.properties所在的目录，点击添加，选择转码并重命名为IdeBundle.properties的文件替换原IdeBundle.properties文件，然后点击保存；
* 9.文件替换完毕，可以移动到 AS安装目录/lib/  目录下，替换原jar包，然后重启，可以看到汉化后的效果





### native2ascii.exe介绍
native2ascii.exe 是Java的一个文件转码工具，是将特殊各异的内容转为用指定的编码标准文体形式统一的表现出来，它通常位于JDK_home\bin目录下，安装好Java SE后，可在命令行直接使用 native2ascii命令进行转码。
实现国际化*.properties文件，中文字符与Unicode字符相互转换
国际化resources.properties文件，中文字符转换为Unicode字符：
native2ascii resources.properties tmp.properties 或者 native2ascii -encoding Unicode resources.properties tmp.properties
注意：Unicode首字母必须大写

国际化resources.properties文件，Unicode字符转换为中文字符：

```
native2ascii -reverse -encoding GB2312 resources.properties tmp.properties
native2ascii IdeBundle.properties tmp.properties
```

中文字符转换为Unicode字符,国际化resources.properties文件：
```
native2ascii -reverse -encoding GB2312 tmp.properties resources.properties

```