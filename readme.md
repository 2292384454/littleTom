littleTom
===================================================

- 一个基于java的简易的web服务器，默认使用80端口
- 支持访问日志记录

## 环境依赖

- jdk8及以上
- maven(或者手动配置log4j2依赖)

## 部署步骤

暂略

## 目录结构描述
```
│  .gitignore
│  littleTom.iml
│  pom.xml
│  readme.md
│    
├─src
│  ├─main
│  │  ├─java
│  │  │      HTTPServer.java
│  │  │      RequestProcessor.java
│  │  │      
│  │  ├─resources
│  │  │      log4j2.xml
│  │  │      
│  │  └─webapp
│  │              
│  └─test
│      ├─java
│      └─resources
│          ├─logs
│          │      kittyHTTPServerLog.log
│          │      RequestProcessorLog.log
│          │      
│          └─staticHTML
└─target
    ├─classes
    │      HTTPServer.class
    │      log4j2.xml
    │      RequestProcessor.class
    │      
    ├─generated-sources
    │  └─annotations
    └─test-classes
        └─logs
                kittyHTTPServerLog.log
                RequestProcessorLog.log
                

```
## V0.0.0 版本内容更新

支持对静态网页的HTTP服务，支持GET和HEAD方法。

## TODO

1.异步缓存