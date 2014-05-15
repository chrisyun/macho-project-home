<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my"%>
	<footer class="m_footer">
	<p class="mf_o4">
	<center style="font-size: 11px;">
		© 2007-2015 兔兔查 <br> 版权所有 <br>备案号：苏ICP备13057562号-1 <br> 客服电话：010-66505512 <img src="http://c.cnzz.com/wapstat.php?siteid=5747697&r=&rnd=1376431314" width="0"
			height="0" /><img src="http://c.cnzz.com/wapstat.php?siteid=1000217844&r=&rnd=816488206" width="0" height="0" />
	</center>
	</p>
	</footer> <br>
	<br>
	<div onclick="downtutucha()"
		style="background: url(/tu1.png) no-repeat; background-size: 39px 39px; position: fixed; bottom: 0; right: 0; font-size: 9px; width: 40px; height: 40px;"></div>
	<div onClick="dontshowmeagain()"
		style="background: url(/tu2.png) no-repeat; background-size: 39px 39px; position: fixed; bottom: 0; left: 0; font-size: 9px; width: 40px; height: 40px;"></div>
	<script>
		function dontshowmeagain() {
			if (confirm('此操作会导致您以后无法浏览此页面，如果误点请点击取消?')) {
				var cookie = new COOKIES();
				cookie.set('dontshowme', 1, 'd365', '/');
			}
		}
		function downtutucha() {
			if (confirm('正在准备下载兔兔查安卓版...')) {
				window.location.href = 'http://data.youwoma.com/appdata/Appsecrettutucha.apk';
			}
		}
	</script> </section>
	<script>
		var Cookie = {
			get : function(name) {
				var value = '', matchs;
				if (matchs = document.cookie.match("(?:^| )" + name
						+ "(?:(?:=([^;]*))|;|$)"))
					value = matchs[1] ? unescape(matchs[1]) : "";
				return value
			},
			set : function(name, value, expire, domain) {
				expire = expire || 30 * 24 * 3600 * 1000;
				var date = new Date(), cookie = "";
				date.setTime(date.getTime() + expire);
				cookie = name + "=" + escape(value) + ";expires="
						+ date.toGMTString() + ";path=/;";
				domain && (cookie += "domain=" + domain + ";");
				document.cookie = cookie
			},
			del : function(name, domain) {
				Cookie.set(name, '', -1, domain)
			}
		};
		var browser = {
			versions : function() {
				var u = navigator.userAgent, app = navigator.appVersion;
return {
					trident : u.indexOf('Trident') > -1, //IE内核
					presto : u.indexOf('Presto') > -1, //opera内核
					webKit : u.indexOf('AppleWebKit') > -1, //苹果、谷歌内?
					gecko : u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
					mobile : !!u.match(/AppleWebKit.*Mobile.*/)
							|| !!u.match(/AppleWebKit/), //是否为移动终?
					ios : !!u.match(/(i[^;]+\;(U;)? CPU.+Mac OS X)/), //ios终端
					android : u.indexOf('Android') > -1
							|| u.indexOf('Linux') > -1, //android终端或者uc浏览?
					iPhone : u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览?
					iPad : u.indexOf('iPad') > -1, //是否iPad
					webApp : u.indexOf('Safari') == -1
				//是否web应该程序，没有头部与底部
				};
			}(),
			language : (navigator.browserLanguage || navigator.language)
					.toLowerCase()
		}

		browser.versions.android && DownApk();

		function DownApk() {

			if (Cookie.get("isApk") != "Yes") {
				if (confirm('推荐下载手机版兔兔查?')) {
					window.location.href = 'http://e.ran10.com.cn/c?z=ran10&la=0&si=18&cg=9&c=154&ci=25&or=203&l=248&bg=248&b=289&u=http://data.youwoma.com/appdata/Appsecrettutucha.apk';
				}
				Cookie.set("isApk", "Yes");
			}
		}
	</script>
	<!--放在页面最-->
	<script id="gd" type="text/javascript" c="gds_test" src="http://gds.9game.cn/public/javascripts/gd.js"></script>
	<script type="text/javascript">
		UC_GD_fillSlotAsync('214', 'PAGE_AD_2');
	</script>
