<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
<html lang="en-US">
<head>
<base href="http://www.tutucha.com/" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" /> - ${game.label}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1, target-densitydpi=medium-dpi, user-scalable=no">
<link rel="stylesheet" href="./gamelist_files/common.css">
<link rel="stylesheet" href="./gameshow_files/total.css">
</head>
<body class="bg-white">

  <header class="main-header ucFixBug">
  <div class="header-content">详情</div>
  <div class="header-widget">
    <div class="header-sub">
      <span class="guide-icon guide-icon-return" onclick="javascript:history.go(-1)"></span>
    </div>

  </div>
  </header>
  <div class="topfix-holder"></div>
  <section id="fix-main">
  <div id="header_fix">
    <div class="detial">
      <div class="pic-par">
        <img src="http://image.game.uc.cn/2013/7/31/9270789.png" alt="" class="game-icon">
        <p class="star">
          <i class="star-item "></i> <i class="star-item "></i> <i class="star-item "></i> <i class="star-item "></i> <i class="star-item half"></i>
        </p>
      </div>
      <div class="content">
        <h2 class="loum-title">${game.label}</h2>
        <p class="loum-word">
          <span>状态:${game.status}</span>
        </p>
        <p class="loum-word" style="padding-left: 30px;">
          <span class="loum">类型:<i>角色</i></span> 
        </p>
        <p class="loum-word">
          <span>版本:${game.version}</span>
        </p>
        <p class="loum-word" style="padding-left: 30px;">
          <span class="loum">大小:<i> ${game.size} </i></span>
        </p>
      </div>
    </div>
  </div>
  <div id="fixBtn" class="btn-double-line fix-btn" style="position: relative; top: 0px; -webkit-box-shadow: none; z-index: 11;">
    <span href="/game/downs_34950_2.html" type="btn" data-statis="text:bt_x_detail_15_tbxz_34950" class="btn-img-key"> <a href="?op=down&id=30"> 下载 </a>
    </span>
  </div>
  <div id="btnPlaceholder" class="btn-double-line-placeholder" style="display: none;"></div>
  <div id="header_fix_main">

    <div class="fold-container">
      <div class="module-wrap ">
        <div class="module-header labelOpenCloseBar">
          <p class="title">游戏简介</p>
          <span class="labelOpenCloseBarArr dot-arr "></span>
        </div>
        <div class="labelOpenCloseTarget module-wrap-content">
          <div class="textmain-block">
            ${game.description}
            <c:forEach var="screenshot" items="${game.screenshots}">
            <img src="${screenshot}">
            </c:forEach>
          </div>
        </div>
      </div>


    </div>
  </div>
  </section>

  <section class="bottom-container">
  <ul class="bottom-default">

    <li type="btn" href="#">TOP<i class="icon-gotop"></i>
    </li>
  </ul>
  </section>

  <section class="footer-container"> <footer class="footer-default">
  <style>
body {
	margin: auto;
	width: 320px;
}
</style>

  <footer class="m_footer">
  <p class="mf_o4">
  <center style="font-size: 11px;">
    © 2007-2015 兔兔查 <br> 版权所有 <br>备案号：苏ICP备13057562号-1 <br> 客服电话：13011080792 <img
      src="http://c.cnzz.com/wapstat.php?siteid=5747697&r=http%3A%2F%2Fwww.tutucha.com%2F%3Fop%3Dgame&rnd=1246347146" width="0" height="0" /><img
      src="http://c.cnzz.com/wapstat.php?siteid=1000217844&r=http%3A%2F%2Fwww.tutucha.com%2F%3Fop%3Dgame&rnd=2135715021" width="0" height="0" />
  </center>
  </p>
  </footer> </footer> </section>
  <div class="popup-up-remind" id="openTestRemindPop">
    <p class="bar">
      <i class="key" id="openTestRemindPopTitle">操作成功</i> <span id="openTestRemindPopText">开测提醒将发送至您的系统消息</span>
    </p>
  </div>

</body>
</html>