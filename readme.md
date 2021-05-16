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
│  │      │  index.html
│  │      │  index.jsp
│  │      │  
│  │      ├─css
│  │      │  │  animate.css
│  │      │  │  bootstrap.min.css
│  │      │  │  color.css
│  │      │  │  magnific-popup.css
│  │      │  │  normalize.css
│  │      │  │  owl.carousel.css
│  │      │  │  owl.theme.css
│  │      │  │  owl.transitions.css
│  │      │  │  slider-pro.css
│  │      │  │  style.css
│  │      │  │  
│  │      │  ├─font-awesome
│  │      │  │  ├─css
│  │      │  │  │      font-awesome.css
│  │      │  │  │      font-awesome.min.css
│  │      │  │  │      
│  │      │  │  └─fonts
│  │      │  │          fontawesome-webfont.eot
│  │      │  │          fontawesome-webfont.svg
│  │      │  │          fontawesome-webfont.ttf
│  │      │  │          fontawesome-webfont.woff
│  │      │  │          fontawesome-webfont.woff2
│  │      │  │          FontAwesome.otf
│  │      │  │          
│  │      │  └─images
│  │      ├─elegant_font
│  │      │  │  gpl_license.txt
│  │      │  │  index.html
│  │      │  │  lte-ie7.js
│  │      │  │  mit_license.txt
│  │      │  │  style.css
│  │      │  │  
│  │      │  └─fonts
│  │      │          ElegantIcons.eot
│  │      │          ElegantIcons.svg
│  │      │          ElegantIcons.ttf
│  │      │          ElegantIcons.woff
│  │      │          
│  │      ├─fonts
│  │      │      BOD_I.TTF
│  │      │      fontawesome-webfont.eot
│  │      │      fontawesome-webfont.svg
│  │      │      fontawesome-webfont.ttf
│  │      │      fontawesome-webfont.woff
│  │      │      fontawesome-webfont.woff2
│  │      │      FontAwesome.otf
│  │      │      
│  │      ├─images
│  │      │  │  features.png
│  │      │  │  features2.png
│  │      │  │  footer-map.png
│  │      │  │  logo.png
│  │      │  │  preloader.gif
│  │      │  │  
│  │      │  ├─img-portfolio
│  │      │  │      portfolio1.jpg
│  │      │  │      portfolio2.jpg
│  │      │  │      portfolio3.jpg
│  │      │  │      portfolio4.jpg
│  │      │  │      portfolio5.jpg
│  │      │  │      portfolio6.jpg
│  │      │  │      portfolio7.jpg
│  │      │  │      
│  │      │  ├─img-teams
│  │      │  │      team1.jpg
│  │      │  │      team2.jpg
│  │      │  │      team3.jpg
│  │      │  │      team4.jpg
│  │      │  │      
│  │      │  └─slider
│  │      │          slider-img-1.jpg
│  │      │          slider-img-2.jpg
│  │      │          slider-img-3.jpg
│  │      │          
│  │      ├─js
│  │      │      bootstrap.min.js
│  │      │      custom.js
│  │      │      html5shiv.min.js
│  │      │      imagesloaded.pkgd.min.js
│  │      │      isotope.pkgd.min.js
│  │      │      jquery-1.11.3.min.js
│  │      │      jquery.easing.1.3.js
│  │      │      jquery.easypiechart.js
│  │      │      jquery.fitvids.js
│  │      │      jquery.magnific-popup.min.js
│  │      │      jquery.nav.js
│  │      │      jquery.scrollUp.min.js
│  │      │      jquery.sliderPro.min.js
│  │      │      jquery.stellar.min.js
│  │      │      jquery.waypoints.min.js
│  │      │      modernizr.min.js
│  │      │      owl.carousel.min.js
│  │      │      respond.min.js
│  │      │      selectivizr.js
│  │      │      smooth-scroll.min.js
│  │      │      wow.min.js
│  │      │      
│  │      └─WEB-INF
│  │              web.xml
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