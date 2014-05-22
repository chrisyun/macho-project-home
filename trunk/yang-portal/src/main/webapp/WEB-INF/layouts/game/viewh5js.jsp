<!-- 这里没用H5的写法 -->
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>

<html manifest="util/application.appcache">
<head>
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=0" />
<meta charset="utf-8" />
<base href="http://m.apk.hiapk.com/" />
<link rel=apple-touch-icon-precomposed href=/icon.png>
<link rel=icon type=image/png href=/favicon.png>
<link href="resources/style/himarketwap/cssAnimation.css?ver=1.3.0" type="text/css" rel="Stylesheet">
<link href="resources/style/himarketwap/list.css?ver=1.3.0" type="text/css" rel="Stylesheet">
<link href="resources/style/himarketwap/load.css?ver=1.3.0" type="text/css" rel="Stylesheet">
<title>安卓市场Wap</title>

<!-- 基本类库 -->


<script type="text/javascript" src="jslib/zepto-min.js"></script>
<script type="text/javascript" src="jslib/underscore-min.js"></script>
<script type="text/javascript" src="jslib/backbone-min.js"></script>
<script type="text/javascript" src="jslib/require-min.js?v=1.0"></script>
<script type="text/javascript" src="jslib/iscroll.js"></script>
<!-- 默认中文语言包 -->
<script type="text/javascript" src="lang/zh-cn.js?ver=1.3.0"></script>

<!-- 动态加载JS配置 -->
<script type="text/javascript" src="util/CommonUtil.js?ver=1.3.0"></script>
<script type="text/javascript" src="util/ClassUtil.js"></script>
<script type="text/javascript" src="util/RequireConfig.js?ver=1.3.1"></script>

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
</head>

<body>


</body>
</html>

