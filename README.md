# AndroidStudio2.0.汉化计划

####AndroidStudio简介：
###### AndroidStudio是Google官方推出的基于 IntelliJ IDEA的Android开发IDE. 
######Android Studio 2.0是为Android平台打造高品质，高性能的应用程序的最快方式，包括手机、平板电脑，Android Auto，Android Wear和Android TV。作为谷歌官方的IDE，Android Studio包括你需要建立的任何应用程序，其中包括代码编辑器，代码分析工具，模拟器等等。这个新的、稳定的Android Studio版本具有快速构建速度和支持最新Android版的快速的仿真器以及谷歌播放服务。

####AndroidStudioV2.0版本主要更新：

* Instant Run
* Android Emulator 
* Cloud Test Lab Integration
* App Indexing Code Generation & Test
* GPU Debugger Preview 
* IntelliJ 15 Update 
* 更新详情：参见发行说明：
   http://android-developers.blogspot.com/2016/04/android-studio-2-0.html

---
# AndroidStudio V2.0.x.版汉化工作
 resource_en.jar------> resource_cn.jar

此汉化基于AndroidStudio V2.0.0.20（20160408）最新V2.0稳定版本

---


##目录介绍
```
AndroidStudio2.0.x.CN/
├─AS_V2.0.0.20_zh-cn/                                    //V2.0.0.20版翻译项目目录
│  ├─resources_cn/
│  └─README.md
├─AS_V2.0.0.20_en/                                        //V2.0.0.20版英语语言解压包
│   ├─resources_en
│   └─README.md
├─Android_Studio_CN/				          //smzy 制作的Android_Studio_CN汉化版本
│   ├─zh_CN/
│   └─README.md
├─Android_Studio_resources_cn_v1.0/                       //Android Studio 中文组 对V1.0汉化版本
│   ├─resources_en/
│   └─README.md
├─Backup/                                                  // 备份目录
│     ├─AndriodStudio_1.x_resources_cn/
│     │     └─Android_Studio_CN_smzy.zip                    //smzy 制作的Android_Studio_CN汉化版本备份压缩包
│     ├─AndriodStudio_2.0.0.20_resources_en/
│     │     └─resources_cn_v1.0_zhangwei_20141211.zip       //Android Studio 中文组 对V1.0汉化版本备份压缩包
│     └─AS_2.0_Preview_Build_143.2443734_20151125_For_Mac/
│           └─resources_en.jar			             //Android-Studio_2.0_Preview_build_143.2443734)_部分汉化包
├─Tools/
│     ├─jre7/
│     ├─AS汉化工具.exe 
│     ├─AS汉化工具使用说明.doc
│     └─README.md
├─.gitignore     
├─LICENSE
└─README.md
```



###安装使用方法
英文语言包l位于安装目录下的 --lib/resource_en.jar 
使用方法：
* 1.备份lib/resource_en.jar 文件；
* 2.用压缩软件打开（非解压）resource_en.jar文件，将本项目下的AS_V2.0.0.20_en/resources_cn文件夹内的所有文件添加到resource_en.jar文件内替换原英文语言包文件；
* 3.点击保存；
* 4.将已经替换的语言包文件，添加回lib/目录，替换之前的resource_en.jar文件；
* 5.汉化完成，正常打开AndroidStudio IDE；


