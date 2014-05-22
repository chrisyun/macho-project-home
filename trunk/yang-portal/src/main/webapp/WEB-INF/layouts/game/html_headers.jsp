<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=0" name="viewport">
<meta charset="utf-8">

<base href='<c:url value="/"/>' />
<link href="/icon.png" rel="apple-touch-icon-precomposed">
<link href="/favicon.png" type="image/png" rel="icon">
<link rel="Stylesheet" type="text/css" href="resources/style/himarketwap/cssAnimation.css?ver=1.3.0">
<link rel="Stylesheet" type="text/css" href="resources/style/himarketwap/list.css?ver=1.3.0">
<link rel="Stylesheet" type="text/css" href="resources/style/himarketwap/load.css?ver=1.3.0">

<!-- 基本类库 -->
<script src="resources/jslib/zepto-min.js" type="text/javascript"></script>
<script src="resources/jslib/underscore-min.js" type="text/javascript"></script>
<script src="resources/jslib/backbone-min.js" type="text/javascript"></script>
<script src="resources/jslib/require-min.js?v=1.0" type="text/javascript"></script>
<script src="resources/jslib/iscroll.js" type="text/javascript"></script>
<!-- 默认中文语言包 -->
<script src="resources/lang/zh-cn.js?ver=1.3.0" type="text/javascript"></script>

<!-- 动态加载JS配置 -->
<script src="resources/util/CommonUtil.js?ver=1.3.0" type="text/javascript"></script>
<script src="resources/util/ClassUtil.js" type="text/javascript"></script>
<script src="resources/util/RequireConfig.js?ver=1.3.1" type="text/javascript"></script>

<script type="text/javascript">
  //加载首页的数据   
  $(function() {
    // 设置主域名TODO
    document.domain = "hiapk.com";
    $.upVersionTip();
    requirejs.config({
      baseUrl : ""
    });
    defineJS();
  });
</script>
