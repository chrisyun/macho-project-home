<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<html manifest="util/application.appcache">
<head>
<meta content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=0" name="viewport">
<meta charset="utf-8">

<link href="/icon.png" rel="apple-touch-icon-precomposed">
<link href="/favicon.png" type="image/png" rel="icon">
<link rel="Stylesheet" type="text/css" href="resources/style/himarketwap/cssAnimation.css?ver=1.3.0">
<link rel="Stylesheet" type="text/css" href="resources/style/himarketwap/list.css?ver=1.3.0">
<link rel="Stylesheet" type="text/css" href="resources/style/himarketwap/load.css?ver=1.3.0">
<title>安卓市场Wap</title>

<!-- 基本类库 -->


<script src="jslib/zepto-min.js" type="text/javascript"></script>
<script src="jslib/underscore-min.js" type="text/javascript"></script>
<script src="jslib/backbone-min.js" type="text/javascript"></script>
<script src="jslib/require-min.js?v=1.0" type="text/javascript"></script>
<script src="jslib/iscroll.js" type="text/javascript"></script>
<!-- 默认中文语言包 -->
<script src="lang/zh-cn.js?ver=1.3.0" type="text/javascript"></script>

<!-- 动态加载JS配置 -->
<script src="util/CommonUtil.js?ver=1.3.0" type="text/javascript"></script>
<script src="util/ClassUtil.js" type="text/javascript"></script>
<script src="util/RequireConfig.js?ver=1.3.1" type="text/javascript"></script>

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










  <!-- 头部模板 -->



  <!-- 菜单栏模板 -->


  <!-- 应用框架模板 -->























  <!-- 头部模板 -->



  <!-- 菜单栏模板 -->


  <!-- 应用框架模板 -->














  <!--头部信息模板 -->


  <!--安全厂商配置 -->


  <!--详细信息模板 -->


  <!-- app评论模板 -->


  <!-- app评论列表模板-->


  <!-- 图片滚动列表模板 -->


  <!-- 图片弹窗模板 -->


  <!--其他作品模板 -->


  <!-- 猜你喜欢模板 -->

  <meta charset="UTF-8">
  <link rel="stylesheet" type="text/css" href="template/himarketwap/ui/funheader.css?1400599352000">
  <header id="header">

    <div id="top_banner">
      <div class="top_banner_content">
        <div id="top_center">
          <div class="center_box">
            <div url="#index" class="top_logo"></div>
            <div class="top_searchdiv">
              <input type="text" class="top_shtext">
            </div>
          </div>
        </div>

        <div class="top_right">
          <div url="#index" id="top_home"></div>
          <div class="return_ui" id="HideSearch"></div>
          <div class="space_line"></div>
          <div class="fun_search">
            <div class="search_txt_box">
              <input type="text" maxlength="25" value="" class="search_txt" id="search_txt">
              <div class="clear_txt_box"></div>
            </div>
          </div>
          <div class="top_search" id="btn_search"></div>
        </div>
      </div>
      <div class="top_banner_bottom"></div>
    </div>
    <section class="menu_box">
      <nav class="menu_nav">
        <div class="menu_list">
          <div url="#ranking" class="menu_item title_font24 on" id="menu_ranking">
            排行
            <div class="menu_on"></div>
          </div>
          <div class="space_line"></div>
          <div url="#category" class="menu_item title_font24" id="menu_category">
            分类
            <div class="menu_on"></div>
          </div>
          <div class="space_line"></div>
          <div url="#topic" class="menu_item title_font24" id="menu_topic">
            专题
            <div class="menu_on"></div>
          </div>
          <div class="space_line"></div>
          <div url="#weekly" class="menu_item title_font24" id="menu_weekly">
            周刊
            <div class="menu_on"></div>
          </div>
        </div>
      </nav>
      <div class="nav_bottom"></div>
    </section>
  </header>

  <div id="funScrollBox">
    <div id="funPageContent">
      <ul id="funPageUlFrame" style="left: 0px;">
        <li id="rankingLiFrame"><div id="rankingFrame" style="overflow: hidden;">

            <div id="rankingHtml" style="transition-property: transform; transform-origin: 0px 0px 0px; transform: translate(0px, 0px) translateZ(0px);">
              <meta charset="UTF-8">
              <section class="itemlistbox">
                <div id="topFastTab" class="item_title fasttop on">
                  <span class="title_font24">上升最快</span>
                  <div class="slidedown hide_slidedown"></div>
                  <div class="z"></div>
                </div>
                <div id="topFastContainer" class="listcontainer on">
                  <ul class="itemlist" id="rise">
                    <li class="app_item_li list_item">
                      <article>
                        <a href="#ranking/detail:2635437"><img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload//2014/05_09/10/201405091040364247.png"
                          src="http://cdn.image.market.hiapk.com/data/upload//2014/05_09/10/201405091040364247.png"> </a>
                      </article>
                      <article url="#ranking/detail:2635437" class="list_con list_url">
                        <div class="t_font">
                          <a class="list_title_font">大众点评</a>
                        </div>
                        <div class="list_star_down">
                          <div class="star_bg">
                            <div style="width: 90%;" class="star_level"></div>
                          </div>
                          <div class="list_down_tip">1858万热度</div>
                        </div>
                      </article>
                      <aside>
                        <a h_app_lang="1" h_vender="2635437" class="down_btn_a">
                          <div class="down_btn"></div>
                        </a>
                      </aside>
                      <div class="z"></div>
                    </li>
                    <li class="app_item_li list_item">
                      <article>
                        <a href="#ranking/detail:2662308"><img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/05_19/14/201405191444045011.png"
                          src="http://cdn.image.market.hiapk.com/data/upload/2014/05_19/14/201405191444045011.png"> </a>
                      </article>
                      <article url="#ranking/detail:2662308" class="list_con list_url">
                        <div class="t_font">
                          <a class="list_title_font">搜狗手机输入法</a>
                        </div>
                        <div class="list_star_down">
                          <div class="star_bg">
                            <div style="width: 80%;" class="star_level"></div>
                          </div>
                          <div class="list_down_tip">1亿热度</div>
                        </div>
                      </article>
                      <aside>
                        <a h_app_lang="1" h_vender="2662308" class="down_btn_a">
                          <div class="down_btn"></div>
                        </a>
                      </aside>
                      <div class="z"></div>
                    </li>
                    <li class="app_item_li list_item">
                      <article>
                        <a href="#ranking/detail:2661636"><img class="app_icon"
                          data_src="http://cdn.image.market.hiapk.com/data/upload/2014/05_19/10/20140519105239113_1828.png"
                          src="http://cdn.image.market.hiapk.com/data/upload/2014/05_19/10/20140519105239113_1828.png"> </a>
                      </article>
                      <article url="#ranking/detail:2661636" class="list_con list_url">
                        <div class="t_font">
                          <a class="list_title_font">Google Play 服务</a>
                        </div>
                        <div class="list_star_down">
                          <div class="star_bg">
                            <div style="width: 60%;" class="star_level"></div>
                          </div>
                          <div class="list_down_tip">1377万热度</div>
                        </div>
                      </article>
                      <aside>
                        <a h_app_lang="2" h_vender="2661636" class="down_btn_a">
                          <div class="down_btn"></div>
                        </a>
                      </aside>
                      <div class="z"></div>
                    </li>
                    <li class="app_item_li list_item">
                      <article>
                        <a href="#ranking/detail:2663304"><img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload//2014/05_19/18/201405191801133772.png"
                          src="http://cdn.image.market.hiapk.com/data/upload//2014/05_19/18/201405191801133772.png"> </a>
                      </article>
                      <article url="#ranking/detail:2663304" class="list_con list_url">
                        <div class="t_font">
                          <a class="list_title_font">猎豹清理大师</a>
                        </div>
                        <div class="list_star_down">
                          <div class="star_bg">
                            <div style="width: 100%;" class="star_level"></div>
                          </div>
                          <div class="list_down_tip">1659万热度</div>
                        </div>
                      </article>
                      <aside>
                        <a h_app_lang="1" h_vender="2663304" class="down_btn_a">
                          <div class="down_btn"></div>
                        </a>
                      </aside>
                      <div class="z"></div>
                    </li>
                    <li class="app_item_li list_item">
                      <article>
                        <a href="#ranking/detail:2663040"><img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload//2014/05_19/17/201405191718250365.png"
                          src="http://cdn.image.market.hiapk.com/data/upload//2014/05_19/17/201405191718250365.png"> </a>
                      </article>
                      <article url="#ranking/detail:2663040" class="list_con list_url">
                        <div class="t_font">
                          <a class="list_title_font">百度云</a>
                        </div>
                        <div class="list_star_down">
                          <div class="star_bg">
                            <div style="width: 90%;" class="star_level"></div>
                          </div>
                          <div class="list_down_tip">791万热度</div>
                        </div>
                      </article>
                      <aside>
                        <a h_app_lang="1" h_vender="2663040" class="down_btn_a">
                          <div class="down_btn"></div>
                        </a>
                      </aside>
                      <div class="z"></div>
                    </li>
                    <li class="app_item_li list_item">
                      <article>
                        <a href="#ranking/detail:2586307"><img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/04_23/10/201404231000115272.png"
                          src="http://cdn.image.market.hiapk.com/data/upload/2014/04_23/10/201404231000115272.png"> </a>
                      </article>
                      <article url="#ranking/detail:2586307" class="list_con list_url">
                        <div class="t_font">
                          <a class="list_title_font">有信免费社交电话</a>
                        </div>
                        <div class="list_star_down">
                          <div class="star_bg">
                            <div style="width: 100%;" class="star_level"></div>
                          </div>
                          <div class="list_down_tip">2105万热度</div>
                        </div>
                      </article>
                      <aside>
                        <a h_app_lang="1" h_vender="2586307" class="down_btn_a">
                          <div class="down_btn"></div>
                        </a>
                      </aside>
                      <div class="z"></div>
                    </li>
                    <li class="app_item_li list_item">
                      <article>
                        <a href="#ranking/detail:2576531"><img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/04_18/18/201404181802141365.png"
                          src="http://cdn.image.market.hiapk.com/data/upload/2014/04_18/18/201404181802141365.png"> </a>
                      </article>
                      <article url="#ranking/detail:2576531" class="list_con list_url">
                        <div class="t_font">
                          <a class="list_title_font">炼爱</a>
                        </div>
                        <div class="list_star_down">
                          <div class="star_bg">
                            <div style="width: 100%;" class="star_level"></div>
                          </div>
                          <div class="list_down_tip">200万热度</div>
                        </div>
                      </article>
                      <aside>
                        <a h_app_lang="1" h_vender="2576531" class="down_btn_a">
                          <div class="down_btn"></div>
                        </a>
                      </aside>
                      <div class="z"></div>
                    </li>
                    <li class="app_item_li list_item">
                      <article>
                        <a href="#ranking/detail:2551399"><img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/04_11/11/201404111132303094.png"
                          src="http://cdn.image.market.hiapk.com/data/upload/2014/04_11/11/201404111132303094.png"> </a>
                      </article>
                      <article url="#ranking/detail:2551399" class="list_con list_url">
                        <div class="t_font">
                          <a class="list_title_font">热卷</a>
                        </div>
                        <div class="list_star_down">
                          <div class="star_bg">
                            <div style="width: 0%;" class="star_level"></div>
                          </div>
                          <div class="list_down_tip">24万热度</div>
                        </div>
                      </article>
                      <aside>
                        <a h_app_lang="1" h_vender="2551399" class="down_btn_a">
                          <div class="down_btn"></div>
                        </a>
                      </aside>
                      <div class="z"></div>
                    </li>
                    <li class="app_item_li list_item">
                      <article>
                        <a href="#ranking/detail:2663185"><img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload//2014/05_19/17/201405191737312764.png"
                          src="http://cdn.image.market.hiapk.com/data/upload//2014/05_19/17/201405191737312764.png"> </a>
                      </article>
                      <article url="#ranking/detail:2663185" class="list_con list_url">
                        <div class="t_font">
                          <a class="list_title_font">微话</a>
                        </div>
                        <div class="list_star_down">
                          <div class="star_bg">
                            <div style="width: 100%;" class="star_level"></div>
                          </div>
                          <div class="list_down_tip">69万热度</div>
                        </div>
                      </article>
                      <aside>
                        <a h_app_lang="1" h_vender="2663185" class="down_btn_a">
                          <div class="down_btn"></div>
                        </a>
                      </aside>
                      <div class="z"></div>
                    </li>
                    <li class="app_item_li list_item">
                      <article>
                        <a href="#ranking/detail:2657705"><img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/05_16/18/201405161808088796.png"
                          src="http://cdn.image.market.hiapk.com/data/upload/2014/05_16/18/201405161808088796.png"> </a>
                      </article>
                      <article url="#ranking/detail:2657705" class="list_con list_url">
                        <div class="t_font">
                          <a class="list_title_font">万得股票（证券炒股软件）</a>
                        </div>
                        <div class="list_star_down">
                          <div class="star_bg">
                            <div style="width: 100%;" class="star_level"></div>
                          </div>
                          <div class="list_down_tip">306万热度</div>
                        </div>
                      </article>
                      <aside>
                        <a h_app_lang="1" h_vender="2657705" class="down_btn_a">
                          <div class="down_btn"></div>
                        </a>
                      </aside>
                      <div class="z"></div>
                    </li>
                    <li class="app_item_li list_item">
                      <article>
                        <a href="#ranking/detail:2640817"><img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/05_12/10/201405121041121648.png"
                          src="http://cdn.image.market.hiapk.com/data/upload/2014/05_12/10/201405121041121648.png"> </a>
                      </article>
                      <article url="#ranking/detail:2640817" class="list_con list_url">
                        <div class="t_font">
                          <a class="list_title_font">旅游攻略</a>
                        </div>
                        <div class="list_star_down">
                          <div class="star_bg">
                            <div style="width: 90%;" class="star_level"></div>
                          </div>
                          <div class="list_down_tip">349万热度</div>
                        </div>
                      </article>
                      <aside>
                        <a h_app_lang="1" h_vender="2640817" class="down_btn_a">
                          <div class="down_btn"></div>
                        </a>
                      </aside>
                      <div class="z"></div>
                    </li>
                    <li class="app_item_li list_item">
                      <article>
                        <a href="#ranking/detail:2667013"><img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload//2014/05_20/15/201405201532572425.png"
                          src="http://cdn.image.market.hiapk.com/data/upload//2014/05_20/15/201405201532572425.png"> </a>
                      </article>
                      <article url="#ranking/detail:2667013" class="list_con list_url">
                        <div class="t_font">
                          <a class="list_title_font">高德地图</a>
                        </div>
                        <div class="list_star_down">
                          <div class="star_bg">
                            <div style="width: 90%;" class="star_level"></div>
                          </div>
                          <div class="list_down_tip">1亿热度</div>
                        </div>
                      </article>
                      <aside>
                        <a h_app_lang="1" h_vender="2667013" class="down_btn_a">
                          <div class="down_btn"></div>
                        </a>
                      </aside>
                      <div class="z"></div>
                    </li>
                    <li class="app_item_li list_item">
                      <article>
                        <a href="#ranking/detail:2658361"><img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/05_16/21/20140516095635_1334.png"
                          src="http://cdn.image.market.hiapk.com/data/upload/2014/05_16/21/20140516095635_1334.png"> </a>
                      </article>
                      <article url="#ranking/detail:2658361" class="list_con list_url">
                        <div class="t_font">
                          <a class="list_title_font">字体管家</a>
                        </div>
                        <div class="list_star_down">
                          <div class="star_bg">
                            <div style="width: 90%;" class="star_level"></div>
                          </div>
                          <div class="list_down_tip">440万热度</div>
                        </div>
                      </article>
                      <aside>
                        <a h_app_lang="1" h_vender="2658361" class="down_btn_a">
                          <div class="down_btn"></div>
                        </a>
                      </aside>
                      <div class="z"></div>
                    </li>
                    <li class="app_item_li list_item">
                      <article>
                        <a href="#ranking/detail:2656225"><img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/05_16/13/201405161358393346.png"
                          src="http://cdn.image.market.hiapk.com/data/upload/2014/05_16/13/201405161358393346.png"> </a>
                      </article>
                      <article url="#ranking/detail:2656225" class="list_con list_url">
                        <div class="t_font">
                          <a class="list_title_font">火花电视剧</a>
                        </div>
                        <div class="list_star_down">
                          <div class="star_bg">
                            <div style="width: 80%;" class="star_level"></div>
                          </div>
                          <div class="list_down_tip">327万热度</div>
                        </div>
                      </article>
                      <aside>
                        <a h_app_lang="1" h_vender="2656225" class="down_btn_a">
                          <div class="down_btn"></div>
                        </a>
                      </aside>
                      <div class="z"></div>
                    </li>
                    <li class="app_item_li list_item">
                      <article>
                        <a href="#ranking/detail:2668387"><img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/05_20/20/201405202057410597.png"
                          src="http://cdn.image.market.hiapk.com/data/upload/2014/05_20/20/201405202057410597.png"> </a>
                      </article>
                      <article url="#ranking/detail:2668387" class="list_con list_url">
                        <div class="t_font">
                          <a class="list_title_font">无秘（原秘密）</a>
                        </div>
                        <div class="list_star_down">
                          <div class="star_bg">
                            <div style="width: 100%;" class="star_level"></div>
                          </div>
                          <div class="list_down_tip">38万热度</div>
                        </div>
                      </article>
                      <aside>
                        <a h_app_lang="1" h_vender="2668387" class="down_btn_a">
                          <div class="down_btn"></div>
                        </a>
                      </aside>
                      <div class="z"></div>
                    </li>
                    <li class="app_item_li list_item">
                      <article>
                        <a href="#ranking/detail:2511266"><img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload//2014/03_31/11/201403311140432412.png"
                          src="http://cdn.image.market.hiapk.com/data/upload//2014/03_31/11/201403311140432412.png"> </a>
                      </article>
                      <article url="#ranking/detail:2511266" class="list_con list_url">
                        <div class="t_font">
                          <a class="list_title_font">同程旅游</a>
                        </div>
                        <div class="list_star_down">
                          <div class="star_bg">
                            <div style="width: 80%;" class="star_level"></div>
                          </div>
                          <div class="list_down_tip">185万热度</div>
                        </div>
                      </article>
                      <aside>
                        <a h_app_lang="1" h_vender="2511266" class="down_btn_a">
                          <div class="down_btn"></div>
                        </a>
                      </aside>
                      <div class="z"></div>
                    </li>
                    <li class="app_item_li list_item">
                      <article>
                        <a href="#ranking/detail:2601118"><img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/05_04/18/201405041810503073.png"
                          src="http://cdn.image.market.hiapk.com/data/upload/2014/05_04/18/201405041810503073.png"> </a>
                      </article>
                      <article url="#ranking/detail:2601118" class="list_con list_url">
                        <div class="t_font">
                          <a class="list_title_font">游戏秒懂17173</a>
                        </div>
                        <div class="list_star_down">
                          <div class="star_bg">
                            <div style="width: 100%;" class="star_level"></div>
                          </div>
                          <div class="list_down_tip">38万热度</div>
                        </div>
                      </article>
                      <aside>
                        <a h_app_lang="1" h_vender="2601118" class="down_btn_a">
                          <div class="down_btn"></div>
                        </a>
                      </aside>
                      <div class="z"></div>
                    </li>
                    <li class="app_item_li list_item">
                      <article>
                        <a href="#ranking/detail:2641981"><img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload//2014/05_12/15/201405121551065595.png"
                          src="http://cdn.image.market.hiapk.com/data/upload//2014/05_12/15/201405121551065595.png"> </a>
                      </article>
                      <article url="#ranking/detail:2641981" class="list_con list_url">
                        <div class="t_font">
                          <a class="list_title_font">途牛旅游-特价.旅行</a>
                        </div>
                        <div class="list_star_down">
                          <div class="star_bg">
                            <div style="width: 100%;" class="star_level"></div>
                          </div>
                          <div class="list_down_tip">213万热度</div>
                        </div>
                      </article>
                      <aside>
                        <a h_app_lang="1" h_vender="2641981" class="down_btn_a">
                          <div class="down_btn"></div>
                        </a>
                      </aside>
                      <div class="z"></div>
                    </li>
                    <li class="app_item_li list_item">
                      <article>
                        <a href="#ranking/detail:2667370"><img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/05_20/16/20140520042712_7732.png"
                          src="http://cdn.image.market.hiapk.com/data/upload/2014/05_20/16/20140520042712_7732.png"> </a>
                      </article>
                      <article url="#ranking/detail:2667370" class="list_con list_url">
                        <div class="t_font">
                          <a class="list_title_font">天天德州</a>
                        </div>
                        <div class="list_star_down">
                          <div class="star_bg">
                            <div style="width: 70%;" class="star_level"></div>
                          </div>
                          <div class="list_down_tip">24万热度</div>
                        </div>
                      </article>
                      <aside>
                        <a h_app_lang="1" h_vender="2667370" class="down_btn_a">
                          <div class="down_btn"></div>
                        </a>
                      </aside>
                      <div class="z"></div>
                    </li>
                    <li class="app_item_li list_item">
                      <article>
                        <a href="#ranking/detail:2645064"><img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/05_13/12/201405131236001424.png"
                          src="http://cdn.image.market.hiapk.com/data/upload/2014/05_13/12/201405131236001424.png"> </a>
                      </article>
                      <article url="#ranking/detail:2645064" class="list_con list_url">
                        <div class="t_font">
                          <a class="list_title_font">秒拍</a>
                        </div>
                        <div class="list_star_down">
                          <div class="star_bg">
                            <div style="width: 100%;" class="star_level"></div>
                          </div>
                          <div class="list_down_tip">118万热度</div>
                        </div>
                      </article>
                      <aside>
                        <a h_app_lang="1" h_vender="2645064" class="down_btn_a">
                          <div class="down_btn"></div>
                        </a>
                      </aside>
                      <div class="z"></div>
                    </li>
                  </ul>
                  <div class="add_inline">
                    <div class="addmore_box" id="riseAddMoreBox" style="visibility: visible;">
                      <div class="addmore" id="riseAddMore">加载更多</div>
                    </div>
                  </div>
                </div>
              </section>

              <section class="itemlistbox">
                <div id="appRankTab" class="item_title">
                  <span class="title_font24">应用周排行</span>
                  <div class="slidedown"></div>
                  <div class="z"></div>
                </div>
                <div id="appRankContainer" class="listcontainer"></div>
              </section>

              <section class="itemlistbox">
                <div id="gameRankTab" class="item_title">
                  <span class="title_font24">游戏周排行</span>
                  <div class="slidedown"></div>
                  <div class="z"></div>
                </div>
                <div id="gameRankContainer" class="listcontainer"></div>
              </section>
              <footer class="footer">
                <div class="footer_content">
                  <div class="versionLink">
                    < <a href="http://apk.hiapk.com/?forweb=1">网页版</a>| <a class="on">流畅版</a>>
                  </div>
                  <div style="margin-top: 5px;" class="wapDetail">

                    <div class="icp" style="padding-left: 0px; text-align: center;">闽ICP备09004645</div>
                  </div>
                  <div style="width: 215px; margin: 5px auto;" class="text_center">
                    <span style="display: block; float: left">福建博瑞网络科技有限公司</span><img width="40" style="margin-top: -5px; float: left;" alt=""
                      src="/resources/img/himarketwap/icon/nava.png">
                    <div class="z"></div>
                  </div>
                </div>
              </footer>
            </div>
            <div class="myScrollBarV" style="pointer-events: none; transition-property: opacity; overflow: hidden; opacity: 0;">
              <div
                style="pointer-events: none; transition-property: transform; transition-timing-function: cubic-bezier(0.33, 0.66, 0.66, 1); transform: translate(0px, 0px) translateZ(0px); height: 186px;"></div>
            </div>
          </div></li>
        <li id="categoryLiFrame">
          <div class="loadingbox loadview" style="margin-top: 270.5px; margin-bottom: 0px;">
            <div id="circleG">
              <div class="circleG" id="circleG_1"></div>
              <div class="circleG" id="circleG_2"></div>
              <div class="circleG" id="circleG_3"></div>
              <div class="z"></div>
            </div>
            <div class="list_load_font">正在载入请稍后......</div>
          </div>
        </li>
        <li id="topicLiFrame">
          <div class="loadingbox loadview" style="margin-top: 270.5px; margin-bottom: 0px;">
            <div id="circleG">
              <div class="circleG" id="circleG_1"></div>
              <div class="circleG" id="circleG_2"></div>
              <div class="circleG" id="circleG_3"></div>
              <div class="z"></div>
            </div>
            <div class="list_load_font">正在载入请稍后......</div>
          </div>
        </li>
        <li id="weeklyLiFrame">
          <div class="loadingbox loadview" style="margin-top: 270.5px; margin-bottom: 0px;">
            <div id="circleG">
              <div class="circleG" id="circleG_1"></div>
              <div class="circleG" id="circleG_2"></div>
              <div class="circleG" id="circleG_3"></div>
              <div class="z"></div>
            </div>
            <div class="list_load_font">正在载入请稍后......</div>
          </div>
        </li>
      </ul>
    </div>
  </div>
  <script type="text/template" id="funHeaderTpl"> 

  <div id="top_banner">
    <div class="top_banner_content">
      <div id="top_center">
        <div class="center_box">
          <div class="top_logo" url="#index"></div>
          <div class="top_searchdiv">
            <input type="text" class="top_shtext">
          </div>
        </div>
      </div>

      <div class="top_right">
        <div id="top_home" url="#index"></div>
        <div id="HideSearch" class="return_ui"></div>
        <div class="space_line"></div>
        <div class="fun_search">
          <div class="search_txt_box">
            <input type="text" id="search_txt" class="search_txt" value="" maxlength="25">
            <div class="clear_txt_box"></div>       
          </div>
        </div>
        <div id="btn_search" class="top_search"></div>
      </div>
    </div>
    <div class="top_banner_bottom"></div>
  </div>
  <section class="menu_box">
    <nav class="menu_nav">
      <div class="menu_list">
      <div id="menu_ranking" class="menu_item title_font24" url="#ranking">
        排行
        <div class="menu_on"></div>
      </div>
      <div class="space_line"></div>
      <div id="menu_category" class="menu_item title_font24" url="#category">
        分类
        <div class="menu_on"></div>
      </div>
      <div class="space_line"></div>
      <div id="menu_topic" class="menu_item title_font24" url="#topic">
        专题
        <div class="menu_on"></div>
      </div>
      <div class="space_line"></div>
      <div id="menu_weekly" class="menu_item title_font24" url="#weekly">
        周刊
        <div class="menu_on"></div>
      </div>
    </nav>
    <div class="nav_bottom"></div>
  </section>
</script>
</body>
</html>