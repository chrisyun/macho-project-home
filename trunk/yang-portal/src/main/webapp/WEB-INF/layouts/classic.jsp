<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<html>
<head>
<title><tiles:getAsString name="title" /></title>
<tiles:insertAttribute name="html_headers" />
<style type="text/css">
body {
  margin: auto;
  width: 320px;
}
</style>
</head>
<body class="skin_default">
	<section id="main" class="pb_imgbg">
	<h2 style="color: red">尊敬的用户,您访问的域名有误,请使用如下服务</h2>
	<header class="m_header">
	<h2 style="display: none">
		<a style="background-image: url(/img/logo.png)" href="http://junshi.tutucha.com" class="logo"></a>
	</h2>
	<form class="search" id='search' name="search" action="http://m.baidu.com/s" method="get">
		<div class='search_wp'>
			<div class="secrch_input_wp">
				<input type="text" value="" autocomplete="off" autocorrect="off" maxlength="64" id="kw" name="word" class="search_input">
			</div>
			<div class='search_bt_wp'>
				<div class="search_cross"></div>
				<button type="submit" class="search_bt">
					<span>百度一下</span>
				</button>
			</div>
		</div>
		<input type="hidden" name="from" value="1003349a" />
	</form>
	<div id="con_wz">
		<div class="common_block hykz">
			<ul>
				<li style="border: none"><script type="text/javascript" src="http://e.ran10.com.cn/s?z=ran10&c=168"></script></li>
			</ul>
		</div>
	</div>
	</header>

	<div id="con_wz">
		<div class="common_block hykz">
			<ul>
				<li><script type="text/javascript" src="http://e.ran10.com.cn/s?z=ran10&c=155"></script></li>
				<li><script type="text/javascript" src="http://e.ran10.com.cn/s?z=ran10&c=156"></script></li>
				<li style="border: none"><script type="text/javascript" src="http://e.ran10.com.cn/s?z=ran10&c=157"></script></li>
			</ul>
		</div>
	</div>
  <!-- 名站导航 -->
  <my:showArea pageId="1" areaId="34" template="hotsites"/>
  
	<div id="con_wz">
		<div class="common_block hykz">
			<ul>
				<li><script type="text/javascript" src="http://e.ran10.com.cn/s?z=ran10&c=158"></script></li>
				<li style="border: none"><script type="text/javascript" src="http://e.ran10.com.cn/s?z=ran10&c=159"></script></li>
			</ul>
		</div>
	</div>
	<div id="con_wz">
		<div class="common_block" style="background: none; display: table; padding: 0; border: 0; border-radius: 0; margin: 0; width: 100%;" align="center">
			<script type="text/javascript" src="http://e.ran10.com.cn/s?z=ran10&c=222"></script>
		</div>
	</div>
	<nav id="nav" class="nav">
	<h2 id="gameajax" class="nav_cur" id="nav_1" data-loc="wz">行业酷站</h2>
	</nav>
	<style>
.hykz {
	padding: 5px
}

.hykz li {
	padding: 5px 5px;
	font-size: 14px;
	border-bottom: 1px dotted #ccc;
	list-style: circle;
	list-style-position: inside
}

#PAGE_AD_2 img {
	width: 320px
}
</style>
	<div id="con_wz">
		<div class="common_block hykz">
			<ul>
				<li><script type="text/javascript" src="http://e.ran10.com.cn/s?z=ran10&c=160"></script></li>
				<li><script type="text/javascript" src="http://e.ran10.com.cn/s?z=ran10&c=161"></script></li>
				<li><script type="text/javascript" src="http://e.ran10.com.cn/s?z=ran10&c=162"></script></li>

				<li style="border: none"><script type="text/javascript" src="http://e.ran10.com.cn/s?z=ran10&c=163"></script></li>
			</ul>
		</div>
	</div>
	<div id="con_wz">
		<div class="banner" style="background: #FFF; height: 60px; overflow: hidden;">
			<!-- 广告位：兔兔查图片1-->
			<script>
				CNZZ_M_SLOT_RENDER('271142');
			</script>
		</div>
		<div class="banner" style="background: #FFF; height: 60px; overflow: hidden;">
			<!-- 广告位：兔兔查图片2-->
			<script>
				CNZZ_M_SLOT_RENDER('271143');
			</script>
		</div>
	</div>

  <!-- 网站应用 -->
  <my:showArea pageId="1" areaId="100" template="webapps"/>

	<div class="common_block xinwen">
		<header class="common_header">
		<h3>
			<a href="#">新闻资讯</a>
		</h3>
		</header>
    <my:showNavigations template="navigations4" categoryId="59"/>
    <my:showNavigations template="navigations4" categoryId="59"/>
    <my:showNavigations template="navigations4" categoryId="59"/>
    <my:showNavigations template="navigations4" categoryId="59"/>
    <my:showNavigations template="navigations4" categoryId="59"/>
    <my:showNavigations template="navigations5" categoryId="59"/>
	</div>

	<div class="common_block yule">
		<header class="common_header">
		<h3>
			<a href="#">娱乐休闲</a>
		</h3>
		</header>
    <my:showNavigations template="navigations4" categoryId="59"/>
    <my:showNavigations template="navigations4" categoryId="59"/>
    <my:showNavigations template="navigations4" categoryId="59"/>
    <my:showNavigations template="navigations4" categoryId="59"/>
    <my:showNavigations template="navigations4" categoryId="59"/>
    <my:showNavigations template="navigations5" categoryId="59"/>
	</div>
	<div class="common_block gouwu">
		<header class="common_header">
		<h3>
			<a href="#">购物生活</a>
		</h3>
		</header>
    <my:showNavigations template="navigations4" categoryId="59"/>
    <my:showNavigations template="navigations4" categoryId="59"/>
    <my:showNavigations template="navigations4" categoryId="59"/>
    <my:showNavigations template="navigations4" categoryId="59"/>
    <my:showNavigations template="navigations4" categoryId="59"/>
    <my:showNavigations template="navigations5" categoryId="59"/>
	</div>
  <tiles:insertAttribute name="footer" />
</body>
</html>