  	/**
	 *	+-----------------------------------------------------------------------------------------------
	 *	|	file		entrance of the JS framework
	 *	+-----------------------------------------------------------------------------------------------
	 *	|	@author 	Goloo <goloo@139.com>
	 *	|	@mktime		2010-05-05
	 *	|	@summary	GolooMvc base class
  	 *	|	@version 	$Id: goloo.js 0001 2013-03-22 04:54:10Z Goloo $
	 *	|	@since		1.0
	 *	|	@edit date	2013.06.02
	 *	+-----------------------------------------------------------------------------------------------
	 * 	|	DO NOT modify this file manually.
	 *	+-----------------------------------------------------------------------------------------------
	 */
	
	String.prototype.trim = function() { return this.replace(/(^\s*)|(\s*$)/g, "");}// 过滤空格
	String.prototype.isDate = function(){ var regex = new RegExp("^[\d]([-|/])[\d]{1,2}([-|/])[\d]{1,2}$"); console.log(this); return regex.exec(this);}// 判断是否时间
	String.prototype.alphaNum = function(s) { var ch; var len=s.length; for(i=0;i<len;i++) { ch = s.charAt(i); if(!(isLowerChar(ch)||isUpperChar(ch)||isNumber(ch))) return false; } return true; }// 数字字母字符串组合
	String.prototype.isLowerChar = function(Ch){ return Ch.charCodeAt(0)>=97&&Ch.charCodeAt(0)<=122; }// 是否小写字母
	String.prototype.isUpperChar = function(Ch) { return Ch.charCodeAt(0)>=65&&Ch.charCodeAt(0)<=90; }// 是否大写字母
	String.prototype.is_numeric = function(type) { if(typeof(type) == 'undefined') type=0; var dotCharCode = 46; if(type=='int') dotCharCode = 999999; for(var i=0;i<this.strlen();i++){if((this.charCodeAt(i)<48 && this.charCodeAt(i) != dotCharCode) || this.charCodeAt(i)>57) return false;}return true;}
	String.prototype.isEmail = function(s){res=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;var re = new RegExp(res); return !(s.match(re)== null);}// 是否电子邮件
	String.prototype.copy = function(msg){if(ua()=='ie'){clipboardData.setData('Text',this.toString());if(msg) alert(msg)}else{prompt('拷贝:',this);}}; // 拷贝字符串
	String.prototype.strlen = function(){return (ua()=='ie' && this.indexOf('\n') != -1) ? this.replace(/\r?\n/g, '_').length : this.length;}// 字符串长度
	String.prototype.abs=function(){return Math.abs(Number(this));}//字符串绝对值
	String.prototype.toNum = function(){var r  = Number(this); return isNaN(r)?0:r;}//转换成数字

	Number.prototype.NaN0=function(){return isNaN(this)?0:this;}
	Number.prototype.abs=function(){return Math.abs(this);}
	Number.prototype.flow=function(pos){return Math.round(this*Math.pow(10,pos))/Math.pow(10,pos);}

	Array.prototype.each=function(func){for(var i=0,l=this.length;i<l;i++) {func(this[i],i);};};
	Array.prototype['toJSON'] = function(){var json = {};return json;}
	Array.prototype['add'] = function(sE){oE = eval(sE);for (var idx in this){var o = eval((this[idx]));if (o.item == oE.item) this[idx] = sE;else this.push(sE);}}
	Array.prototype['delete'] = function(element){for (var i = 0; i < this.length; ++ i){if (this[i] == element){for(var idx = i; i < this.length - 1; ++ idx) this[idx] = this[idx + 1];break;}}}
	Array.prototype['in_array'] = function(s){var xx = this;if(typeof s=='string'||typeof s == 'number'){for(var i in xx){if(xx[i] == s) return true;}}return false;}
	
	Object.prototype.each=function(func){for(var i=0,l=this.length;i<l;i++) {if(typeof(this[i])!='undefined'){if(this[i].nodeType==1) func(this[i],i)}};};
	//c.addEvent(['onclick'],function()
	Object.prototype.addEvent = function (b, c, d){	if (this.attachEvent) this.attachEvent(b[0], c);else this.addEventListener(b[1] || b[0].replace(/^on/, ""), c, d || false);return c;}
	Object.prototype.delEvent = function (a, b, c, d){if (a.detachEvent) a.detachEvent(b[0], c);else a.removeEventListener(b[1] || b[0].replace(/^on/, ""), c, d || false);return c;}
	Object.prototype.reEvent = function (){return window.event ? window.event : (function(o){do{o = o.caller;} while (o && !/^\[object[ A-Za-z]*Event\]$/.test(o.arguments[0]));return o.arguments[0];})(this.reEvent);}
	Object.prototype.fixEvent = function (e) {if (typeof e == "undefined"){e = window.event;}if (typeof e.layerX == "undefined"){e.layerX = e.offsetX; e.layerY = e.offsetY;}if (typeof e.which == "undefined"){e.which = e.button;}if (typeof e.x == 'undefined'){e.x = e.clientX;e.y = e.clientY;}return e;}	


 	/**
	 *	+-----------------------------------------------------------------------------------------------
	 *	| 全局变量
	 *	+-----------------------------------------------------------------------------------------------
	 */
		GLOBALS = {}; 
		OBJ_ARR = Array();
	
	/**
	 *	+-----------------------------------------------------------------------------------------------
	 *	| 页面启动完毕自动加载函数
	 *	+-----------------------------------------------------------------------------------------------
	 */
	var auto_func_arr = [];
	var readyChangeFunc = function()
	{
		if (document.readyState=="complete")
		{
			if(typeof(auto_func_arr) != 'undefined')
			{
				foreach(auto_func_arr,function(c)
				{
					c();
				});
			}
			clearInterval(readyChangFlag);
		}
	}
	
	var readyChangFlag = setInterval(readyChangeFunc,100);
	var onDOMReady = function(foo)
	{
		auto_func_arr.push(foo);
	} 
	
	//IE
	if(typeof(HTMLElement)=="undefined")
	{
	}
	//Firefox
	else if(typeof(HTMLElement)=="object")
	{
	}
	
	//opera
	else if(typeof(HTMLElement)=="function")
	{
	}
	
	/**
	 * 非IE 增加 outerHTML 属性
	 *	&& ua()!='ie' : 用来屏蔽IE9
	 */ 
	if(typeof(HTMLElement)!="undefined" && !window.opera && ua()!='ie') 
	{ 
 		HTMLElement.prototype.__defineGetter__("outerHTML",function() 
		{ 
			var a=this.attributes, str="<"+this.tagName, i=0;
			for(;i<a.length;i++) if(a[i].specified) str+=" "+a[i].name+'="'+a[i].value+'"'; 
			if(!this.canHaveChildren) return str+" />"; 
			return str+">"+this.innerHTML+"</"+this.tagName+">"; 
		}); 
		HTMLElement.prototype.__defineSetter__("outerHTML",function(s) 
		{ 
			var r = this.ownerDocument.createRange(); 
			r.setStartBefore(this); 
			var df = r.createContextualFragment(s); 
			this.parentNode.replaceChild(df, this); 
			return s; 
		}); 
		HTMLElement.prototype.__defineGetter__("canHaveChildren",function() 
		{ 
			return !/^(area|base|basefont|col|frame|hr|img|br|input|isindex|link|meta|param)$/.test(this.tagName.toLowerCase()); 
		}); 
	}   

    /**
     * 	+---------------------------------------------
     *	|	创建类的基类
     *	|---------------------------------------------
     *	|	新建的类需要实现 initialize 方法,否则报错
     * 	+---------------------------------------------
     */

	var Class = 
	{
		create : function () 
		{
			return function () 
			{
				this.initialize.apply(this, arguments);
			};
		}
	};

    /**
     * 	+---------------------------------------------
     *	|	类 PHP 中的 _GET 全局变量实现
     *	|---------------------------------------------
     *	|		author:	goloo
     *	|		mktime:	2013-03-22
     * 	+---------------------------------------------
     */
	var _GET = {}							
    var u = window.location.toString();
    u = u.split('?');
    if(typeof(u[1]) == 'string')
	{
		u=u[1].split('&');
		for(i=0;i<u.length;i++)
		{
			s=u[i].split("=");
			eval('_GET.'+s[0]+'="'+s[1]+'"');
        }
    }    
	
	/**
     * 	+---------------------------------------------
     *	|	流程控制扩展
     * 	+---------------------------------------------
     *	|	@param1	s	array		被遍历的对象
     *	|	@param2	f	function	处理函数
     * 	+---------------------------------------------
	 */
		function foreach(s,f)
		{
			var l = s.length;
			var b = '';
			for(var i=0;i<l;i++)
			{
				b = f(s[i]);	
				if(b=='break')
				{
					break;	
				}
			}
		}
		
    /**
     * 	+---------------------------------------------
     *	|	类 PHP 中的 $_GET 全局变量实现
     *	|---------------------------------------------
     *	|		author:	goloo
     *	|		mktime:	2013-03-22
     * 	+---------------------------------------------
     */
	function ua()
	{
		var ua;
		var userAgent = navigator.userAgent.toLowerCase();
		/*OPERA*/
		(userAgent.indexOf('opera') != -1 && opera.version())?ua='opera':'';
		((navigator.product == 'Gecko') && userAgent.substr(userAgent.indexOf('firefox') + 8, 3))?ua='ff':'';
		((userAgent.indexOf('msie') != -1 && ua!='opera') && userAgent.substr(userAgent.indexOf('msie') + 5, 3))?ua='ie':'';
		((userAgent.indexOf('webkit') != -1 || userAgent.indexOf('safari') != -1))?ua='sf':'';
		return ua;
	} 
	
    /**
     * 	+---------------------------------------------
     *	|	COOKIE 处理类
     *	|---------------------------------------------
     *	|		author:	goloo
     *	|		mktime:	2013-03-22
     * 	+---------------------------------------------
     */
	
	var COOKIES = Class.create();
	COOKIES.prototype =
	{
		/*构造函数*/
		initialize: function ()
		{	
		},
		
		/**
		 * 添加一个COOKIE
		 *	@param	sName	cookie名称
		 *	@param	sValue	cookie值
		 *	@param	oExpires	cookie过期时间	s1:1秒/m1:1分/h1:1小时
		 */
		set:function (sName,sValue,oExpires,sPath,sDomain,bSecure)
		{
			var sCookie = sName + "=" + encodeURIComponent(sValue);
			if(oExpires)
			{
				var strsec = this.getCookieTime(oExpires);
				var exp = new Date();
					exp.setTime(exp.getTime() + strsec*1);
					sCookie += "; expires=" + exp.toGMTString();
			}
			if(sPath)
			{
				sCookie += "; path=" + sPath;	
			}
			if(sDomain)
			{
				sCookie +="; domain=" + sDomain;	
			}
			if(bSecure)
			{
				sCookie +="; secure";
			}
			document.cookie= sCookie;
		},
			
		getCookieTime:function(str)
		{
			var str1=str.substring(1,str.length)*1; 
			var str2=str.substring(0,1); 
			if (str2=="s"){
			return str1*1000;
			}else if (str2=="h"){
			return str1*60*60*1000;
			}else if (str2=="d"){
			return str1*24*60*60*1000;
			}
		},
		
		/**
		 * 获取一个COOKIE
		 */
		get:function (sName)
		{
			var sRE = "(?:; |)" + sName + "=([^;]*);?";
			var oRE = new RegExp(sRE);
			if (oRE.test(document.cookie))
			{
				return decodeURIComponent(RegExp["$1"]);	
			}
			else
			{
				return null;	
			}
		},
		
		
		/**
		 * 获取一个COOKIE
		 */
		getCookies: function (name)
		{
			var cookie_start = document.cookie.indexOf(name);
			var cookie_end = document.cookie.indexOf(";", cookie_start);
			return cookie_start == -1 ? '' : decodeURIComponent(document.cookie.substring(cookie_start + name.length + 1, (cookie_end > cookie_start ? cookie_end : document.cookie.length)));
		},
		
		/**
		 * 检测是否开启了COOKIE
		 */
		ifCookie: function ()
		{
			var result=false;
			if(navigator.cookiesEnabled) return true;
			document.cookie = "testcookie=yes;";
			var cookieSet = document.cookie;
			if (cookieSet.indexOf("testcookie=yes") > -1)
			result=true;
			document.cookie = "";
			return result;
		}
		
	}

	/**
	 * 转换为数组
	 */
	var $A = function (a)
	{
		return a ? Array.apply(null, a) : new Array;
	};
		 
	/**
	 * 绑定事件
	 */
	Function.prototype.bind = function ()
	{
		var wc = this, a = $A(arguments), o = a.shift();
 		return function ()
		{
			//绑定到对象 带其余参数 并执行这个函数
			wc.apply(o, a.concat($A(arguments)));
		};
	};

	/**
     * 	+---------------------------------------------
     *	|	AJAX
     * 	+---------------------------------------------
     *	|	@param1 string url 
     *	|	       |?|作为url 和QueryString的分割符
     *	|	@param2 string method
     *	|	@param3 string callback
     * 	+---------------------------------------------
	 */
	 
 	var ajax = Class.create();
		ajax.prototype = 
		{
			initialize : function (u,m,c)
			{
				var obj = this;
					obj.url = u;
					obj.callback = c;
					obj.r = false;
				
				if(window.XMLHttpRequest)
				{
					obj.r = new XMLHttpRequest();
					if(obj.r.overrideMimeType) obj.r.overrideMimeType('text/xml');
				}
				else if(window.ActiveXObject)
				{
					var v = ['Microsoft.XMLHTTP', 'MSXML.XMLHTTP', 'Microsoft.XMLHTTP', 'Msxml2.XMLHTTP.7.0', 'Msxml2.XMLHTTP.6.0', 'Msxml2.XMLHTTP.5.0', 'Msxml2.XMLHTTP.4.0', 'MSXML2.XMLHTTP.3.0', 'MSXML2.XMLHTTP'];
					for(var i=0; i<v.length; i++)
					{
						try
						{
							obj.r = new ActiveXObject(v[i]);
						}
						catch(e){alert('XMLHttpRequest Create Failed!');}
					}
				}
				
				obj.r.onreadystatechange = function()	
				{
					if (obj.r.readyState == 4)
					{
						if (obj.r.status == 200)
						{
							myData = obj.r.responseText;
							obj.callback(myData)
						}
						else if(obj.r.status == 404) myData = "Err:404"
						else if(obj.r.status == 403) myData = "Err:403"
						else myData = obj.r.status
					}
				}
				if(m == 'get') obj.get();
				if(m == 'post') obj.post(u,c);
			},
			
			get : function()
			{	
				var obj = this;
				var delay = 1000;
				if(window.XMLHttpRequest)
				{
					this.r.open('GET',obj.url,true);
					this.r.send(null);
				}
				else 
				{
					this.r.open("GET",obj.url,true);
					this.r.send(); 
				}
			},
		
			/**
			 * POST 提交
			 *　|?|作为url 和QueryString的分割符
			 */
			post : function(u,c)
			{ 
				var obj = this;
				u = u.split('|?|'); 
				obj.u = u[0];
				obj.c = c;
				obj.s = u[1];
				obj.r.open('POST',obj.u);
				obj.r.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
				obj.r.send(obj.s);
			}
		}
	
	/**
	 *	+-----------------------------------------------------------------------------------------------
	 *	| 根据对象标识获取对象
	 *	+-----------------------------------------------------------------------------------------------
	 * 	| @param1	o 	string	对象标识
	 * 	| @return	 	object	返回对象
	 *	+-----------------------------------------------------------------------------------------------
	 * 	| 规则:id:#  | name:@	|	class:.	|	tagName:<
	 *	+-----------------------------------------------------------------------------------------------
	 */
	function _(o)
	{
 		var obj = Object();
		
 		/*documnet*/
		if(typeof(o)=='object') obj=o;
		
		if(_t(/^#(.+)/,o)) obj = document.getElementById(o.replace('#',''));
		if(_t(/^\.(.+)/,o)) obj = getElementsByClassName(o.replace('.',''));
		if(_t(/^@(.+)/,o)) obj = document.getElementsByName(o.replace('@',''));
		if(_t(/^<(.+)/,o)) obj = document.getElementsByTagName(o.replace('<',''));
		if(obj==null) return ;	
		
		/*child object`s getElementsByTagName method*/
		obj.getTags = function(tag){return obj.getElementsByTagName(tag)}
		
		/**
		 * create sub obj
		 * @param1 string HTML tag be created
		 * @param2 string HTML content be inserted
		 * @param3 string position of HTML tag be created
		 */
		obj.create = function(t,s,p)
		{
			if(typeof(t)=='undefined'||t =='') t ='div';
			if(typeof(s)=='undefined') s='';
			if(obj == window || obj == document) obj = document.body;
			
			t = t.split(' #');
 			var d = document.createElement(t[0]);
 			if(s) d.innerHTML = s;
			 
			if(typeof(p)=='undefined' || p =='behind') obj.appendChild(d);
			else if(p == 'before')
			{
  				if(obj.childNodes.length>0)
				{
					var childObj = obj.childNodes[0];
					obj.insertBefore(d,childObj);
				}
				else obj.appendChild(d);
 			}
			if(typeof(t[1]) != 'undefined') d.id = t[1];
			return d;
		}
		
		/**
		 * Remove a childNode
		 */
		obj.remove = function(c)
		{
			if(obj==document) obj=document.body;
			if(typeof(c) == 'undefined')
			{
				if(ua()=='ff' || ua()=='sf') obj.parentNode.removeChild(obj);
 				else obj.removeNode();
			}
			else obj.removeChild(c);
		}
		
		/*obj clientHeight*/
	 	if(obj==document)
		{
			obj.clientHeight = document.body.clientHeight;
			obj.clientWidth = document.body.clientWidth;
 			obj.scrollHeight = document.body.scrollHeight;
			obj.scrollWidth = document.body.scrollWidth;
			obj.scrollLeft = document.body.scrollLeft;
			obj.scrollTop = document.body.scrollTop;
		}
		
		/*obj childNodes*/
		/**
		 * if ff chlidsNodes.length = 1;
		 * if ie childsNodes.length = 0;
		 */
		obj.childs = (function()
		{
			if(typeof(obj.childNodes) == 'undefined')
			{
				var co = [0];
 				return co;
			}
			else return obj.childNodes;	
 		})();
		
		return obj;
	}  
	
	var $c=function(array){var nArray = [];for (var i=0;i<array.length;i++) nArray.push(array[i]);return nArray;};
	var getElementsByClassName=function(cn)
	{
		var hasClass=function(w,Name)
		{
			var hasClass = false;
			w.className.split(' ').each(function(s)
			{
				if (s == Name) hasClass = true;
			});
			return hasClass;
		}; 
		var elems =document.getElementsByTagName("*")||document.all;
		var elemList = [];
		$c(elems).each(function(e)
		{
			if(hasClass(e,cn)){elemList.push(e);}
		})
		return $c(elemList);
	}; 
	var _t=function(p,s){return p.test(s)}

