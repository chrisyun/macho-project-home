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
</head>

<body>





  <meta charset="utf-8">

  <link href="resources/template/himarketwap/appDetailPage.css?1400596037000" type="text/css" rel="stylesheet">
  <section class="top_header_fixed" id="appHeaderFrame2663442">


    <div class="top_banner_bg">
      <article id="goback">
        <a></a>
      </article>
      <article class="top_banner_title">
        <a> 秦时明月（送雪女） </a>
      </article>
      <aside>
        <div h_app_lang="1" h_vender="2663442" class="down_load">普通下载</div>
        <div h_app_lang="1" h_vender="2663442" class="speed_down"></div>
        <div class="z"></div>
      </aside>
    </div>
    <div class="top_banner_bottom"></div>
  </section>
  <div class="appdetail" id="appDetail2663442" style="overflow: hidden; height: 642px;">
    <div id="appDetailScroll2663442" style="transition-property: transform; transform-origin: 0px 0px 0px; transform: translate(0px, 0px) translateZ(0px);">
      <section id="apkDetailBox2663442">
        <section id="appIntroduceFrame2663442">
          <div>
            <section id="apkBaseMsg2663442">
              <article class="appicon_box">
                <img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/05_19/18/201405191858518526.png"
                  src="http://cdn.image.market.hiapk.com/data/upload/2014/05_19/18/201405191858518526.png">
              </article>
              <aside class="right_box">
                <div class="star_pkn_item">
                  <div class="star_bg">
                    <div style="width: 90%;" class="star_level"></div>
                  </div>
                </div>
                <div class="base_msg_item">
                  <article class="msgbox">
                    <div>版本&nbsp;:&nbsp;1.0.8</div>
                    <div>语言&nbsp;:&nbsp;中文</div>
                    <div>
                      时间&nbsp;:&nbsp;<span id="addTimespan">2014-05-19</span>
                    </div>
                  </article>
                  <aside class="msgbox">
                    <div>
                      热度&nbsp;:&nbsp; <span id="downCountspan">13万</span>
                    </div>
                    <div>大小&nbsp;:&nbsp; 65.47M</div>
                  </aside>
                </div>
              </aside>
            </section>
          </div>
          <section id="apkTip2663442">
            <article>
              <div class="app_save_ads">
                <img width="16px" src="resources/img/himarketwap/icon/save_icon.png">
                <div>安全</div>
              </div>
              <div class="app_save_ads">
                <img width="16px" src="resources/img/himarketwap/icon/noads_icon.png">
                <div>无广告</div>
              </div>
              <div class="app_save_ads">
                <img width="16px" src="resources/img/himarketwap/icon/offical.png">
                <div>官方</div>
              </div>
              <div style="float: left; margin-top: -3px; display: none;" id="anvaIcon">
                <img width="25" alt="" src="resources/img/himarketwap/icon/anva_1.png"> <img width="25 alt=" "="" src="resources/img/himarketwap/icon/anva_2.png">
              </div>
              <div class="slidedown"></div>
              <div class="z"></div>
            </article>
            <div class="z"></div>
            <div class="safety_line">

              <div class="safety_box">
                <span class="saft_soft">百度手机卫士</span><span class="safety_center">扫描结果</span><span class="safety_pass">通过</span>
              </div>
            </div>
          </section>
          <section id="apkImgBox2663442">
            <ul id="appScreen2663442" style="width: 700px;">
              <li><a class="screen_imgbox"><img class="detail_img" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/05_19/18/20140519065058_1671.jpg"
                  src="http://cdn.image.market.hiapk.com/data/upload/2014/05_19/18/20140519065058_1671.jpg" style="display: inline;"></a></li>

              <li><a class="screen_imgbox"><img class="detail_img" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/05_19/18/20140519065107_6468.jpg"
                  src="http://cdn.image.market.hiapk.com/data/upload/2014/05_19/18/20140519065107_6468.jpg" style="display: inline;"></a></li>

              <li><a class="screen_imgbox"><img class="detail_img" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/05_19/18/20140519065110_6272.jpg"
                  src="http://cdn.image.market.hiapk.com/data/upload/2014/05_19/18/20140519065110_6272.jpg" style="display: inline;"></a></li>

              <li><a class="screen_imgbox"><img class="detail_img" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/05_19/18/20140519065116_4413.jpg"
                  src="http://cdn.image.market.hiapk.com/data/upload/2014/05_19/18/20140519065116_4413.jpg" style="display: inline;"></a></li>

              <li><a class="screen_imgbox"><img class="detail_img" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/05_19/18/20140519065134_7641.jpg"
                  src="http://cdn.image.market.hiapk.com/data/upload/2014/05_19/18/20140519065134_7641.jpg" style="display: inline;"></a></li>
            </ul>
            <div class="z"></div>
          </section>

          <div class="app_clientbox">
            <a class="android_client" id="hiMarketApp" url="">安装安卓市场</a>
            <div class="client_tip">下载更便捷，更流畅</div>
          </div>
          <section id="apkMsg2663442">
            <header id="apkMsgHead2663442">
              <article class="on">软件介绍</article>
              <div class="app_spaceline"></div>
              <aside id="appCommentAside">应用评论</aside>
            </header>
            <section id="apkinformation2663442">
              <div class="hide_describe">
                <pre>《秦时明月》同名官方手游！秦时燃烧的激烈战火！明月映照的爱恨情仇！立足于百家与大秦帝国间，血洗泪与恨，挥手间便是毁灭与重生，天赐汝命，打造属于你的时代吧！
苍龙初现，帮会宣战！集结共破，夺星尘！冒险平定乱世，探索机关迷城，经历未闻奇遇，与英雄同论剑，孤身守擂台，十八般兵器造就电光火石般绚丽技能！广募悍将，共闯千机楼！广纳盟友建立帮会，一统江湖！
客服电话：4006661551
客服邮箱：gm@coco.cn</pre>
              </div>
              <div class="slidedown"></div>
              <div class="z"></div>
            </section>
            <section id="apkcomment2663442"></section>
          </section>
          <section class="imprint" id="apkImprint">
            <header class="headers_line" id="apkUpHead2663442">
              <div class="headers_title">更新说明</div>
            </header>
            <section id="upContent2663442">
              <div class="hide_imprint">
                <pre>版本更新</pre>
              </div>
              <div class="slidedown mright"></div>
              <div class="z"></div>
            </section>
          </section>
        </section>
      </section>
      <div id="app_other_box">
        <div id="guessLike">
          <div class="item_title">
            <div class="item_title_font">猜你喜欢</div>
          </div>
          <div class="app_other_box" id="appGuessView">


            <ul id="guessAppList">
              <li class="top_apkbox list_item">
                <div url="#index/detail:2663442/detail:2576650" class="guess_other_item top_appitem">
                  <div>
                    <img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/04_18/18/201404181841346093.png"
                      src="http://cdn.image.market.hiapk.com/data/upload/2014/04_18/18/201404181841346093.png">
                  </div>
                  <div class="guess_name">石器联萌</div>
                  <div class="app_size">91.54M</div>
                </div>
              </li>
              <li class="top_apkbox list_item">
                <div url="#index/detail:2663442/detail:2641553" class="guess_other_item top_appitem">
                  <div>
                    <img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/05_12/14/20140512022902_0031.png"
                      src="http://cdn.image.market.hiapk.com/data/upload/2014/05_12/14/20140512022902_0031.png">
                  </div>
                  <div class="guess_name">三国合伙人</div>
                  <div class="app_size">49.93M</div>
                </div>
              </li>
              <li class="top_apkbox list_item">
                <div url="#index/detail:2663442/detail:2453344" class="guess_other_item top_appitem">
                  <div>
                    <img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/03_11/18/201403111812528783.png"
                      src="http://cdn.image.market.hiapk.com/data/upload/2014/03_11/18/201403111812528783.png">
                  </div>
                  <div class="guess_name">一路修仙</div>
                  <div class="app_size">29.76M</div>
                </div>
              </li>
              <li class="top_apkbox list_item">
                <div url="#index/detail:2663442/detail:2591825" class="guess_other_item top_appitem">
                  <div>
                    <img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload//2014/04_24/17/201404241733569654.png"
                      src="http://cdn.image.market.hiapk.com/data/upload//2014/04_24/17/201404241733569654.png">
                  </div>
                  <div class="guess_name">进击部落</div>
                  <div class="app_size">47.60M</div>
                </div>
              </li>
            </ul>
            <div class="z"></div>
          </div>
        </div>
        <div id="authorOtherApp">
          <div class="item_title">
            <div class="item_title_font">其它作品</div>
          </div>
          <div class="app_other_box" id="appOtherView">


            <ul id="appAuthorOtherUl">
              <li class="top_apkbox list_item">
                <div url="#index/detail:2663442/detail:2610546" class="guess_other_item top_appitem">
                  <div>
                    <img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/04_30/18/201404301800308149.png"
                      src="http://cdn.image.market.hiapk.com/data/upload/2014/04_30/18/201404301800308149.png">
                  </div>
                  <div class="guess_name">神庙逃亡2</div>
                  <div class="app_size">28.40M</div>
                </div>
              </li>
              <li class="top_apkbox list_item">
                <div url="#index/detail:2663442/detail:2529370" class="guess_other_item top_appitem">
                  <div>
                    <img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/04_04/14/201404041444201722.png"
                      src="http://cdn.image.market.hiapk.com/data/upload/2014/04_04/14/201404041444201722.png">
                  </div>
                  <div class="guess_name">新风云</div>
                  <div class="app_size">48.55M</div>
                </div>
              </li>
              <li class="top_apkbox list_item">
                <div url="#index/detail:2663442/detail:2610174" class="guess_other_item top_appitem">
                  <div>
                    <img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/04_30/16/201404301622557935.png"
                      src="http://cdn.image.market.hiapk.com/data/upload/2014/04_30/16/201404301622557935.png">
                  </div>
                  <div class="guess_name">地铁跑酷墨西哥版</div>
                  <div class="app_size">31.86M</div>
                </div>
              </li>
              <li class="top_apkbox list_item">
                <div url="#index/detail:2663442/detail:2541109" class="guess_other_item top_appitem">
                  <div>
                    <img class="app_icon" data_src="http://cdn.image.market.hiapk.com/data/upload/2014/04_08/19/201404081927500882.png"
                      src="http://cdn.image.market.hiapk.com/data/upload/2014/04_08/19/201404081927500882.png">
                  </div>
                  <div class="guess_name">神啊救救我吧</div>
                  <div class="app_size">104.05M</div>
                </div>
              </li>
            </ul>
            <div class="z"></div>
          </div>
        </div>
      </div>
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
              src="./resources/img/himarketwap/icon/nava.png">
            <div class="z"></div>
          </div>
        </div>
      </footer>
    </div>
    <div class="myScrollBarV" style="pointer-events: none; transition-property: opacity; overflow: hidden; opacity: 0;">
      <div
        style="pointer-events: none; transition-property: transform; transition-timing-function: cubic-bezier(0.33, 0.66, 0.66, 1); transform: translate(0px, 0px) translateZ(0px); height: 341px;"></div>
    </div>
  </div>

  <section class="app_popimg_show" id="appPopImgShow2663442">
    <ul class="popimgul" id="appPopImgUl2663442"></ul>
    <div class="z"></div>
    <div class="pop_page" id="pop_page2663442"></div>
  </section>
</body>
</html>