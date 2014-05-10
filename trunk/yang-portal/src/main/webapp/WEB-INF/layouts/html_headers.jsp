<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<base href="http://www.tutucha.com/" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,user-scalable=0">
<meta content="telephone=no" name="format-detection">
<link rel="apple-touch-icon-precomposed" href="/static/img/apple-touch-icon-114x114.png">
<link rel="stylesheet" type="text/css" href="/static/css/index_css_a8da6ae5.css?v=3" />
<link href="./static/css/mainv3_320.css?v=20130311" rel="stylesheet" type="text/css" />
<link href="./static/css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="./static/js/zepto.js"></script>
<script type="text/javascript" src="./static/js/common2.js"></script>
<script src="http://www.tutucha.com/static/js/goloo.js?v=2"></script>
<script type="text/javascript">
	function quickDownload(localUrl, failUrl) {
		$.ajax({
			url : localUrl,
			error : function(xhr, type) {
				document.location = failUrl;
			}
		});
	}
	var cookies = new COOKIES();
	var dontshowme = cookies.get('dontshowme');
	if (dontshowme != null)
		location.href = "http://m.baidu.com/s?from=1003349a";
</script>
<style>
body {
	margin: auto;
	width: 320px;
}
</style>
<script type='text/javascript' src='http://js.adm.cnzz.net/js/mm.js'></script>
