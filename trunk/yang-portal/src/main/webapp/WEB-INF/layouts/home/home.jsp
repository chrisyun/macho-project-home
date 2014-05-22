<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<tiles:insertAttribute name="html_headers"/>
<title><tiles:insertAttribute name="title"/></title>
</head>
<body class="skin_default">
  <section id="ew_main" class="pb_imgbg">
    <header class="m_header">
      <h2>
        <a style="background-image: url(./static/img/logo.png)" href="./?fr=enwang" class="logo"></a>
      </h2>
      <form class="search">
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
        <input type="hidden" name="from" value="1008376a" /> <input type="hidden" name="op" value="bd" /> <input type="hidden" name="fr" value="enwang" />
      </form>
    </header>

    <!-- 名站导航 -->
    <my:showArea pageId="1" areaId="34" template="hotsites" />

    <div id="con_wz">
      <div class="common_block" style="background: none; display: table; padding: 0; border: 0; border-radius: 0; margin: 0; width: 100%;" align="center">
        <a id="applink2" href="http://www.enwang.com/?op=gameshow&id=523&fr=enwang"><img width="320" src="./static/img/magic.jpg" /></a>
      </div>
    </div>

    <!-- 手机游戏 -->
    <my:showArea pageId="1" areaId="200" template="mobilegames" />

    <div id="con_wz">
      <div class="common_block" style="background: none; display: table; padding: 0; border: 0; border-radius: 0; margin: 0; width: 100%;" align="center">
        <a id="applink2" href="http://www.enwang.com/?op=gameshow&id=911&fr=enwang"><img width="320" src="./static/img/data.jpg" /></a>
      </div>
    </div>

    <!-- 手机应用 -->
    <my:showArea pageId="1" areaId="300" template="mobileapps" />

    <div id="con_wz">
      <div class="common_block" style="background: none; display: table; padding: 0; border: 0; border-radius: 0; margin: 0; width: 100%;" align="center">
        <script type='text/javascript' src='http://js.adm.cnzz.net/js/abase.js'></script>
        <script>
									CNZZ_SLOT_RENDER("263697");
								</script>
      </div>
    </div>
    <!-- 视频 -->
    <my:showArea pageId="1" areaId="400" template="videos" />

    <div class="common_block xinwen">
      <header class="common_header">
        <h3>
          <a href="#">新闻资讯</a>
        </h3>
      </header>
      <my:showNavigations template="navigations4" categoryId="59" />
      <my:showNavigations template="navigations4" categoryId="59" />
      <my:showNavigations template="navigations4" categoryId="59" />
      <my:showNavigations template="navigations4" categoryId="59" />
      <my:showNavigations template="navigations4" categoryId="59" />
      <my:showNavigations template="navigations5" categoryId="59" />
    </div>

    <div class="common_block yule">
      <header class="common_header">
        <h3>
          <a href="#">娱乐休闲</a>
        </h3>
      </header>
      <my:showNavigations template="navigations4" categoryId="59" />
      <my:showNavigations template="navigations4" categoryId="59" />
      <my:showNavigations template="navigations4" categoryId="59" />
      <my:showNavigations template="navigations4" categoryId="59" />
      <my:showNavigations template="navigations4" categoryId="59" />
      <my:showNavigations template="navigations5" categoryId="59" />
    </div>
    <div class="common_block gouwu">
      <header class="common_header">
        <h3>
          <a href="#">购物生活</a>
        </h3>
      </header>
      <my:showNavigations template="navigations4" categoryId="59" />
      <my:showNavigations template="navigations4" categoryId="59" />
      <my:showNavigations template="navigations4" categoryId="59" />
      <my:showNavigations template="navigations4" categoryId="59" />
      <my:showNavigations template="navigations4" categoryId="59" />
      <my:showNavigations template="navigations5" categoryId="59" />
    </div>
    <tiles:insertAttribute name="footer" />
  </section>
</body>
</html>