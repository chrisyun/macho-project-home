var TONGJI = {
			favoritesurl_u : "favoritesurl_u=0",//常用网址统计参数
		    favoritesurl_u : "favoritesurl_indexrp=0",//常用网址挖掘统计
			customindex_u :  "&customindex_u=0",//个性化统计参数
			_hmtPixel : "http://hm.baidu.com/hm.gif?si=fe00609a77efad0b872dd8e184258a80&amp;et=0&amp;nv=1&amp;st=1&amp;su=http%3A%2F%2Fm.hao123.com%2F&amp;v=wap-0-0.2&amp;rnd=8278036225",
			Hm_lpvt : "1389338044",
			Hm_lvt : "1389338044",
			isLogin : !!"",
			// nav_loc : "",
			osName : "android",
			tn : "",
			tnvr : "bjlt|baiduios|zhangbaiios|hao123app|hao123appbdllq|hao123appios2.0|^hcp[^2-3]|baiduboxapp|sjllqjp|baiduapp|boxandpush|ff_|baiduandroid|boxiospush|liantong06"
		};
		TONGJI.is_fuceng = (function(){ //是否显示浮层
				var reg = new RegExp(/bjlt|baiduios|zhangbaiios|hao123app|hao123appbdllq|hao123appios2.0|^hcp[^2-3]|baiduboxapp|sjllqjp|baiduapp|boxandpush|ff_|baiduandroid/i);
				return reg.exec(TONGJI.tn) == null
			})();
		TONGJI.start_time = new Date().getTime();

		hao123 = window.hao123 || {};
var PDC = {_version : "1.7",_render_start : new Date().getTime(),_analyzer : {loaded : false,url : "/static/js/wpo.mpda.js?v=1.7",callbacks : []},_opt : {is_login : false,sample : 0,product_id : 0,page_id : 0,special_pages : []},_cpupool : {},_timingkey : "start",_timing : {},init : function(a) {for (var b in a) {if(a.hasOwnProperty(b)){this._opt[b] = a[b];}}},mark : function(a) {this._timing[a] = new Date().getTime();if (window.CPU_MONITOR) {if(this._lastkey){CPU_MONITOR.end(this._lastkey + "-" + this._timingkey, this._cpupool);}if (a != "let") {CPU_MONITOR.start(this._timingkey + "-" + a, this._cpupool);this._lastkey = this._timingkey;this._timingkey = a;}}},view_start : function() {this.mark("vt");},tti : function() {this.mark("tti");},page_ready : function() {this.mark("fvt");},metadata : function() {var opt = this._opt;var meta = {env : {user : (opt.is_login == true ? 1 : 0),product_id : opt.product_id,page_id : PDC._is_sample(opt['sample']) ? opt.page_id : 0},common_resources : opt.common_resources,special_resources : opt.special_resources,render_start : this._render_start,timing : this._timing,display : opt.display};var special_id = [];var special_pages = opt['special_pages'];for (var i =0; i<special_pages.length;i++) {if (PDC._is_sample(special_pages[i]['sample'])) {special_id.push(special_pages[i]['id']);}}if (special_id.length > 0) {meta['env']['special_id'] = "[" + special_id.join(",") + "]";}return meta;}};(function() {window.addEventListener("load", function() {PDC.mark("lt");}, false);})();

F._fileMap({'/static/js/index_js_7770b0dd.js' : ['/static/common/lib/gmu/zepto/zepto.js','/static/html5_2013/ui/tab/tab.js','/static/html5_2013/ui/iScroll/iScroll.js','/static/html5_2013/ui/common/common.js','/static/html5_2013/index/index.js','/static/widget/html5_2013/banner/banner.js','/static/widget/html5_2013/cover/cover.js','/static/widget/html5_2013/fenlei/fenlei.js','/static/widget/html5_2013/fuceng/fuceng.js','/static/widget/html5_2013/gexing/gexing_add/gexing_add.js','/static/widget/html5_2013/gexing/gexing_caipiao/gexing_caipiao.js','/static/widget/html5_2013/gexing/gexing_dianshi/gexing_dianshi.js','/static/common/lib/gmu/uibase/uibase.js','/static/common/lib/gmu/base/base.js','/static/common/lib/gmu/uibase/widget/widget.js','/static/common/lib/gmu/uibase/control/control.js','/static/html5_2013/lib/hao123/base.js','/static/widget/html5_2013/gexing/gexing_gupiao/gexing_gupiao.js','/static/widget/html5_2013/gexing/gexing_rp_xiaoshuo_tv/gexing_rp_xiaoshuo_tv.js','/static/widget/html5_2013/gexing/gexing_shishi/gexing_shishi.js','/static/widget/html5_2013/gexing/gexing_tianqi/gexing_tianqi.js','/static/widget/html5_2013/gexing/gexing_tiyu/gexing_tiyu.js','/static/widget/html5_2013/gexing/gexing_xingzuo/gexing_xingzuo.js','/static/widget/html5_2013/goto/goto.js','/static/widget/html5_2013/header/header.js','/static/widget/html5_2013/search/search.js','/static/widget/html5_2013/sudi/sudi.js','/static/widget/html5_2013/zixun/zixun.js']});
