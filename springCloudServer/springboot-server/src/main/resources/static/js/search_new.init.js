define('search_new', ['jdf/1.0.0/unit/search/1.0.0/search', 'jdf/1.0.0/unit/getjsonp/1.0.0/getjsonp', 'product/module/clslog', 'jdf/1.0.0/ui/lazyload/1.0.0/lazyload', './search_plug.js?201710'], function(require, exports, module){
	if ( !window.console) { window.console = {}; window.console.log = window.console.error = function(){}; }
	if ( !window.SEARCH) { window.SEARCH = {}; }
	window.json_city = {"0":{"1":"北京","2":"上海","3":"天津","4":"重庆","5":"河北","6":"山西","7":"河南","8":"辽宁","9":"吉林","10":"黑龙江","11":"内蒙古","12":"江苏","13":"山东","14":"安徽","15":"浙江","16":"福建","17":"湖北","18":"湖南","19":"广东","20":"广西","21":"江西","22":"四川","23":"海南","24":"贵州","25":"云南","26":"西藏","27":"陕西","28":"甘肃","29":"青海","30":"宁夏","31":"新疆","32":"台湾","52993":"港澳","84":"钓鱼岛","53283":"海外"},"1":{"72":"朝阳区","2800":"海淀区","2801":"西城区","2802":"东城区","2803":"崇文区","2804":"宣武区","2805":"丰台区","2806":"石景山区","2807":"门头沟","2808":"房山区","2809":"通州区","2810":"大兴区","2812":"顺义区","2814":"怀柔区","2816":"密云区","2901":"昌平区","2953":"平谷区","3065":"延庆县"},"2":{"2813":"徐汇区","2815":"长宁区","2817":"静安区","2820":"闸北区","2822":"虹口区","2823":"杨浦区","2824":"宝山区","2825":"闵行区","2826":"嘉定区","2830":"浦东新区","2833":"青浦区","2834":"松江区","2835":"金山区","2836":"南汇区","2837":"奉贤区","2841":"普陀区","2919":"崇明县","78":"黄浦区"},"3":{"51035":"东丽区","51036":"和平区","51037":"河北区","51038":"河东区","51039":"河西区","51040":"红桥区","51041":"蓟县","51042":"静海县","51043":"南开区","51044":"塘沽区","51045":"西青区","51046":"武清区","51047":"津南区","51048":"汉沽区","51049":"大港区","51050":"北辰区","51051":"宝坻区","51052":"宁河县"},"4":{"113":"万州区","114":"涪陵区","115":"梁平县","119":"南川区","123":"潼南县","126":"大足区","128":"黔江区","129":"武隆县","130":"丰都县","131":"奉节县","132":"开县","133":"云阳县","134":"忠县","135":"巫溪县","136":"巫山县","137":"石柱县","138":"彭水县","139":"垫江县","140":"酉阳县","141":"秀山县","48131":"璧山县","48132":"荣昌县","48133":"铜梁县","48201":"合川区","48202":"巴南区","48203":"北碚区","48204":"江津区","48205":"渝北区","48206":"长寿区","48207":"永川区","50950":"江北区","50951":"南岸区","50952":"九龙坡区","50953":"沙坪坝区","50954":"大渡口区","50995":"綦江区","51026":"渝中区","51027":"高新区","51028":"北部新区","4164":"城口县","3076":"高新区"},"5":{"142":"石家庄市","148":"邯郸市","164":"邢台市","199":"保定市","224":"张家口市","239":"承德市","248":"秦皇岛市","258":"唐山市","264":"沧州市","274":"廊坊市","275":"衡水市"},"6":{"303":"太原市","309":"大同市","318":"阳泉市","325":"晋城市","330":"朔州市","336":"晋中市","350":"忻州市","368":"吕梁市","379":"临汾市","398":"运城市","3074":"长治市"},"7":{"412":"郑州市","420":"开封市","427":"洛阳市","438":"平顶山市","446":"焦作市","454":"鹤壁市","458":"新乡市","468":"安阳市","475":"濮阳市","482":"许昌市","489":"漯河市","495":"三门峡市","502":"南阳市","517":"商丘市","527":"周口市","538":"驻马店市","549":"信阳市","2780":"济源市"},"8":{"560":"沈阳市","573":"大连市","579":"鞍山市","584":"抚顺市","589":"本溪市","593":"丹东市","598":"锦州市","604":"葫芦岛市","609":"营口市","613":"盘锦市","617":"阜新市","621":"辽阳市","632":"朝阳市","6858":"铁岭市"},"9":{"639":"长春市","644":"吉林市","651":"四平市","2992":"辽源市","657":"通化市","664":"白山市","674":"松原市","681":"白城市","687":"延边州"},"10":{"727":"鹤岗市","731":"双鸭山市","737":"鸡西市","742":"大庆市","753":"伊春市","757":"牡丹江市","765":"佳木斯市","773":"七台河市","776":"黑河市","782":"绥化市","793":"大兴安岭地区","698":"哈尔滨市","712":"齐齐哈尔市"},"11":{"799":"呼和浩特市","805":"包头市","810":"乌海市","812":"赤峰市","823":"乌兰察布市","835":"锡林郭勒盟","848":"呼伦贝尔市","870":"鄂尔多斯市","880":"巴彦淖尔市","891":"阿拉善盟","895":"兴安盟","902":"通辽市"},"12":{"904":"南京市","911":"徐州市","919":"连云港市","925":"淮安市","933":"宿迁市","939":"盐城市","951":"扬州市","959":"泰州市","965":"南通市","972":"镇江市","978":"常州市","984":"无锡市","988":"苏州市"},"13":{"2900":"济宁市","1000":"济南市","1007":"青岛市","1016":"淄博市","1022":"枣庄市","1025":"东营市","1032":"潍坊市","1042":"烟台市","1053":"威海市","1058":"莱芜市","1060":"德州市","1072":"临沂市","1081":"聊城市","1090":"滨州市","1099":"菏泽市","1108":"日照市","1112":"泰安市"},"14":{"1151":"黄山市","1159":"滁州市","1167":"阜阳市","1174":"亳州市","1180":"宿州市","1201":"池州市","1206":"六安市","2971":"宣城市","1114":"铜陵市","1116":"合肥市","1121":"淮南市","1124":"淮北市","1127":"芜湖市","1132":"蚌埠市","1137":"马鞍山市","1140":"安庆市"},"15":{"1158":"宁波市","1273":"衢州市","1280":"丽水市","1290":"台州市","1298":"舟山市","1213":"杭州市","1233":"温州市","1243":"嘉兴市","1250":"湖州市","1255":"绍兴市","1262":"金华市"},"16":{"1303":"福州市","1315":"厦门市","1317":"三明市","1329":"莆田市","1332":"泉州市","1341":"漳州市","1352":"南平市","1362":"龙岩市","1370":"宁德市"},"17":{"1432":"孝感市","1441":"黄冈市","1458":"咸宁市","1466":"恩施州","1475":"鄂州市","1477":"荆门市","1479":"随州市","3154":"神农架林区","1381":"武汉市","1387":"黄石市","1396":"襄阳市","1405":"十堰市","1413":"荆州市","1421":"宜昌市","2922":"潜江市","2980":"天门市","2983":"仙桃市"},"18":{"4250":"耒阳市","1482":"长沙市","1488":"株洲市","1495":"湘潭市","1501":"衡阳市","1511":"邵阳市","1522":"岳阳市","1530":"常德市","1540":"张家界市","1544":"郴州市","1555":"益阳市","1560":"永州市","1574":"怀化市","1586":"娄底市","1592":"湘西州"},"19":{"1601":"广州市","1607":"深圳市","1609":"珠海市","1611":"汕头市","1617":"韶关市","1627":"河源市","1634":"梅州市","1709":"揭阳市","1643":"惠州市","1650":"汕尾市","1655":"东莞市","1657":"中山市","1659":"江门市","1666":"佛山市","1672":"阳江市","1677":"湛江市","1684":"茂名市","1690":"肇庆市","1698":"云浮市","1704":"清远市","1705":"潮州市"},"20":{"3168":"崇左市","1715":"南宁市","1720":"柳州市","1726":"桂林市","1740":"梧州市","1746":"北海市","1749":"防城港市","1753":"钦州市","1757":"贵港市","1761":"玉林市","1792":"贺州市","1806":"百色市","1818":"河池市","3044":"来宾市"},"21":{"1827":"南昌市","1832":"景德镇市","1836":"萍乡市","1842":"新余市","1845":"九江市","1857":"鹰潭市","1861":"上饶市","1874":"宜春市","1885":"抚州市","1898":"吉安市","1911":"赣州市"},"22":{"2103":"凉山州","1930":"成都市","1946":"自贡市","1950":"攀枝花市","1954":"泸州市","1960":"绵阳市","1962":"德阳市","1977":"广元市","1983":"遂宁市","1988":"内江市","1993":"乐山市","2005":"宜宾市","2016":"广安市","2022":"南充市","2033":"达州市","2042":"巴中市","2047":"雅安市","2058":"眉山市","2065":"资阳市","2070":"阿坝州","2084":"甘孜州"},"23":{"3690":"三亚市","3698":"文昌市","3699":"五指山市","3701":"临高县","3702":"澄迈县","3703":"定安县","3704":"屯昌县","3705":"昌江县","3706":"白沙县","3707":"琼中县","3708":"陵水县","3709":"保亭县","3710":"乐东县","3711":"三沙市","2121":"海口市","3115":"琼海市","3137":"万宁市","3173":"东方市","3034":"儋州市"},"24":{"2144":"贵阳市","2150":"六盘水市","2155":"遵义市","2169":"铜仁市","2180":"毕节市","2189":"安顺市","2196":"黔西南州","2205":"黔东南州","2222":"黔南州"},"25":{"4108":"迪庆州","2235":"昆明市","2247":"曲靖市","2258":"玉溪市","2270":"昭通市","2281":"普洱市","2291":"临沧市","2298":"保山市","2304":"丽江市","2309":"文山州","2318":"红河州","2332":"西双版纳州","2336":"楚雄州","2347":"大理州","2360":"德宏州","2366":"怒江州"},"26":{"3970":"阿里地区","3971":"林芝地区","2951":"拉萨市","3107":"那曲地区","3129":"山南地区","3138":"昌都地区","3144":"日喀则地区"},"27":{"2428":"延安市","2442":"汉中市","2454":"榆林市","2468":"商洛市","2476":"安康市","2376":"西安市","2386":"铜川市","2390":"宝鸡市","2402":"咸阳市","2416":"渭南市"},"28":{"2525":"庆阳市","2534":"陇南市","2544":"武威市","2549":"张掖市","2556":"酒泉市","2564":"甘南州","2573":"临夏州","3080":"定西市","2487":"兰州市","2492":"金昌市","2495":"白银市","2501":"天水市","2509":"嘉峪关市","2518":"平凉市"},"29":{"2580":"西宁市","2585":"海东地区","2592":"海北州","2597":"黄南州","2603":"海南州","2605":"果洛州","2612":"玉树州","2620":"海西州"},"30":{"2628":"银川市","2632":"石嘴山市","2637":"吴忠市","2644":"固原市","3071":"中卫市"},"31":{"4110":"五家渠市","4163":"博尔塔拉蒙古自治州阿拉山口口岸","15945":"阿拉尔市","15946":"图木舒克市","2652":"乌鲁木齐市","2654":"克拉玛依市","2656":"石河子市","2658":"吐鲁番地区","2662":"哈密地区","2666":"和田地区","2675":"阿克苏地区","2686":"喀什地区","2699":"克孜勒苏州","2704":"巴音郭楞州","2714":"昌吉州","2723":"博尔塔拉州","2727":"伊犁州","2736":"塔城地区","2744":"阿勒泰地区"},"32":{"2768":"台湾市"},"52993":{"52994":"香港特别行政区","52995":"澳门特别行政区"},"84":{"1310":"钓鱼岛"}};
	Array.prototype.unique = function(){var res=[],json={};for(var i=0,j=this.length;i<j;i++){if(!json[this[i]]){res.push(this[i]);json[this[i]]=1;}}return res}

	//是否支持history.pushState
	var enable_history_state = window.history && window.history.pushState && window.history.replaceState && !navigator.userAgent.match(/((iPod|iPhone|iPad).+\bOS\s+[1-4]\D|WebApps\/.+CFNetwork)/);

	//help tools
	var strip_tags = function(input, allowed) {
		allowed = (((allowed || "") + "").toLowerCase().match(/<[a-z][a-z0-9]*>/g) || []).join(''); // making sure the allowed arg is a string containing only tags in lowercase (<a><b><c>)
		var tags = /<\/?([a-z][a-z0-9]*)\b[^>]*>/gi,
			commentsAndPhpTags = /<!--[\s\S]*?-->|<\?(?:php)?[\s\S]*?\?>/gi;
		return input.replace(commentsAndPhpTags, '').replace(tags, function ($0, $1) {
			return allowed.indexOf('<' + $1.toLowerCase() + '>') > -1 ? $0 : '';
		});
	};
	var getQueryString = function(name, url) {
		var reg = new RegExp("(^|\\?|&)"+ name +"=([^&]*)(\\s|&|$)", "i");
		var param = url ? url : window.location.search;
		if ( reg.test(param) ) { return RegExp.$2; } return "";
	};
	var filtUrl = function(a, b, c) {
		var url, param, replace;
		switch ( arguments.length ) {
			case 0: return '';
			case 1: url = window.location.pathname+window.location.search; param = a; replace = ''; break;
			case 2: url = a; param = b; replace = ''; break;
			case 3: url = a; param = b; replace = c; break;
			default: break;
		}
		if ( typeof param=='string' && typeof replace=='string' ) {
			var reg = new RegExp('(^|\\?|&)'+param+'=([^&#]*)','gi');
			if ( !replace ) {
				url = url.replace(reg, '');
			} else if ( reg.test(url) ) {
				url = url.replace(reg, "$1"+param+"="+replace);
			} else {
				var index = url.indexOf("#"), hash = "";
				if ( index!=-1 ) { hash = url.substr(index); url = url.substr(0, index); }
				url = url+'&'+param+'='+replace+hash;
			}
		} else if ( param instanceof Array && typeof replace=='string' ) {
			for (var i=0, j=param.length; i<j; i++) {
				url = filtUrl(url, param[i], replace);
			}
		} else if ( param instanceof Array && replace instanceof Array && param.length==replace.length) {
			for (var i=0, j=param.length; i<j; i++) {
				url = filtUrl(url, param[i], replace[i]);
			}
		} else {
			url = false;
		}
		return url;
	};
	var bind_attr_href = function(container, name, value) { //局刷时在属性上追加参数
		$(container).unbind('click').bind('click', function(e){
			var realObj = $(e.target), href = realObj.attr('href');
			if ( !href ) { realObj = realObj.closest('a'); href = realObj.attr('href'); }
			if ( href && href!='javascript:;') {
				window.location.href = filtUrl(href, name, value);
				return false;
			}
		});
	};
	var htmlspecialchars = function(str, enc_quot) {
		str = str.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
		return enc_quot==true ? str.replace(/'/g, '&#0*39;') : str;
	};
	var html_chars_decode = function(str) {
		return !str ? '' : str.replace(/&lt;/g, '<').replace(/&gt;/g, '>').replace(/&amp;/g, '&').replace(/&quot;/g, '"').replace(/&#0*39;/g, "'");
	};
	var setCookie = function(a, b, c) {
		var d = c, e = new Date; e.setTime(e.getTime()+24*d*60*60*1e3);
		document.cookie=a+"="+escape(b)+";expires="+e.toGMTString()+";path=/;domain=."+pageConfig.FN_getDomain();
	};
	var template = function(sTemplate, oJson) {
		if( typeof oJson != 'object' ) { return ''; }
		return sTemplate.replace(/{#(.*?)#}/g, function() {
			var sWord = arguments[1];
			if ("undefined" != typeof(oJson[sWord]) && oJson[sWord]!=null) {
				return oJson[sWord] ;
			} else {
				return '' ;
			}
		}) ;
	};
	var imgUrl = function (sku, img, size) {
		size = size || 'n7/';
		return '//img1'+(sku%5)+'.360buyimg.com/'+size+img;
	};
	var anchor_loc = function() {
		self==top ? window.scrollTo(0, $('#J_main').offset().top) : window.parent.scrollTo(0, $('#J_main').offset().top+233);
	};
	var ngloader = function(t, vs) {
		var i = 0, loader = function() {
			if ( ++i > 100 ) return false;
			if ( typeof(JA)=='object' && typeof(JA.tracker)=='object' && typeof(JA.tracker.ngloader)=='function' ) {
				JA.tracker.ngloader(t, vs);
			} else {
				setTimeout(loader, 50);
			}
		}
		loader();
	};
	var checkLogin = function(callback) {
		seajs.use(['jdf/1.0.0/unit/login/1.0.0/login.js'], function(jdLogin){// 登陆组件
			jdLogin({
				clstag1: "login|keycount|5|3", clstag2: "login|keycount|5|4", modal: true,
				complete: function() {
					if ( typeof(callback)=='function' ) { callback(); }
				}
			})
		});
	};
	var WareType = {
		pop: function(wid) { return wid>=1000000001 && wid<=1999999999 || wid>=10000000001 && wid<=99999999999 || wid>=200000000001 && wid<=209999999999 },
		book: function(wid) { return wid>=10000001 && wid<=19999999 || wid>=110000000001 && wid<=139999999999 },
		mvd: function(wid) { return wid>=20000001 && wid<=29999999 || wid>=140000000001 && wid<=149999999999 }
	};
	var track_iframe_onebox = function() { //跟踪iframe的onebox点击，获取当前页面上显示的onebox名称
		var re = /^(https:)?\/\/(.*)\.(jd|360buy)\.com/i, onebox = [];
		$('.J_oneBoxFrame').each(function(){
			var type, src = $(this).attr('src') || $(this).attr('data-src');
			if ( re.test(src) ) {
				type = RegExp.$2; if ( type=='life' && src.indexOf('initRestaurant')>-1 ) {
					type = 'dingzuo';
				} else if ( type=='life' && src.indexOf('initTakeOut')>-1 ) {
					type = 'waimai';
				}
				onebox.push(type);
			} else if ( src.indexOf('//api.jd.yiche.com')>-1 ) {
				onebox.push('yiche');
			}
		})
		return onebox.join('$');
	};
	var eBookSentMsg = function(id, key) {//电子书免费下载
		var urlservice = "//gw.e.jd.com/downrecord/downrecord_insert.action?ebookId=" + id + "&key=" + key + "&callback=?";
		$.getJSON(urlservice, function(json){
			if ( json.code==1 ) {
				window.seajs.use(['jdf/1.0.0/ui/dialog/1.0.0/dialog'],function(dialog){
					$('body').dialog({
						title:'下载', hasButton:true,
						source:'如您已安装京东LeBook客户端，请点击“确定”自动启动客户端<br /><br />如您尚未安装京东LeBook客户端，请点击“取消”将引导您免费安装客户端',
						submitButton:'确定', cancelButton:'取消',
						onSubmit:function(){
							$('.ui-dialog, .ui-mask').remove();// 关闭原对话框
							window.location = "LEBK:///Bought";
						},
						onCancel:function(){
							$('.ui-mask').remove(); $('body').dialog({
								title:'下载', hasButton:true, source:'是否安装？', submitButton:'确定', cancelButton:'取消',
								onSubmit:function(){$('.ui-dialog, .ui-mask').remove();window.open("//e.jd.com/ebook/lebook_pc.aspx");}
							});
						}
					});
				});
			} else {
				alert(json.message);
			}
		});
	};
	var ditigalMusicSendMsg = function(pin, productid) {//数字音乐免费下载
		window.seajs.use(['jdf/1.0.0/ui/dialog/1.0.0/dialog', './script/digital_music_download_new'],function(dialog, download){
			$('body').dialog({
				title:'下载', hasButton:true,
				source:'如您已安装京东LeMusic客户端，请点击“确定”自动启动客户端<br /><br />如您尚未安装京东LeMusic客户端，请点击“取消”将引导您免费安装客户端',
				submitButton:'确定', cancelButton:'取消',
				onSubmit:function(){
					$('.ui-dialog, .ui-mask').remove();// 关闭原对话框
					var type = download.getProductType(productid);
					var str = "[a]user=" + pin + "&productid=" + productid + "&obtain=purchase&charset=gb2312&type=" + type + "[z]";
					window.location = "LeMusic://" + download.encode64(str);
				},
				onCancel:function(){
					$('.ui-mask').remove(); $('body').dialog({
						title:'下载', hasButton:true, source:'是否安装？', submitButton:'确定', cancelButton:'取消',
						onSubmit:function(){$('.ui-dialog, .ui-mask').remove();window.open("//app.music.jd.com/client_download.action");}
					});
				}
			});
		});
	};
	var switchBoxTab = function() {
		$('#J_oneboxTabs').find('a').click(function(){
			if ( $(this).hasClass('selected') ) { return ; }
			var index = $(this).index();
			var obj = $('.onebox-tab-cnt').addClass('hide').eq(index).removeClass('hide').find('.J_oneBoxFrame');
			var src = obj.attr('data-src');
			if ( src ) {
				obj.removeAttr('data-src').attr('src', src);
			}
			$(this).addClass('selected').siblings().removeClass('selected');
			if(!window.adExpoReported) {
				// 切换到'商品’tab，如果有商品广告就曝光上报，上报过不用再上报
				setTimeout(function () {
					if (!window.adExpoReported && $('#hasAdsStore').attr('data-exposal-url') && $('#hasAdsStore').is(':visible')) {
						// 广告曝光上报
						window.adExpoReported = true;
						searchPointReport($('#hasAdsStore').attr('data-exposal-url'));
						var target = $('[data-ad-shop-venderid]'); 
						if (target) {
							// shopid 和 venderid不一定相同
							shopid_list = [target.attr('data-ad-shop-venderid'), target.attr('data-shopid')];
							target.click(function(){
								searchUnit.shopFocus($(this).attr('data-shopid'), $(this).attr('data-ad-shop-venderid'), $(this).hasClass('z-focused') ? 'undo' : 'do');
							});
						}
					}
				}, 50);
			}
		})
	};
	//rewrite function
	var rewrite_fun = function(query_string) {
		//品牌id
		var brand_id = location.pathname.match(/pinpai\/([12]-)?(\d+-)?(\d+)\.html$/);
		//作者出版社
		var keyword = location.pathname.match(/(writer|publish)\/(.+?)_\d+\.html$/);
		//
		if ( brand_id ) {
			if ( query_string ) {
				query_string = filtUrl(query_string, 'brand_id', brand_id[3]);
			} else {
				query_string = 'brand_id=' + brand_id[3] + (brand_id[1] && brand_id[2] ? '&cid'+brand_id[1].replace('-','')+'='+brand_id[2].replace('-','') : brand_id[2] ? '&cid3='+brand_id[2].replace('-','') : '');
			}
		} else if ( keyword ) {
			query_string = filtUrl(query_string, ['keyword','enc'], [$.browser.msie ? encodeURIComponent(decodeURIComponent(keyword[2])) : keyword[2], 'utf-8']);
		}
		return query_string;
	};
	var handle_request = function(search) {
		//支持history对象
		if ( enable_history_state ) {
			//记录历史
			window.history.pushState({}, '', window.location.pathname + '?' + search);
			//
			SEARCH.load('s_new.php?' + rewrite_fun(search));
		} else {
			//使用锚点记录历史
			window.location.hash = search;
		}
	};
	/*
	 * @obj 放置标签的容器
	 * @icon 标签html
	 */
	var insert_pf_icon = function(obj, icon, element) {
		//图片形式前置的标签
		var element = element || '.J-picon-fix', target = obj.find(element);
		//保证红色在前，且自营为第一个
		if ( target.length ) {
			target.last().after(icon);
		} else {
			obj.prepend(icon);
		}
	};
	//日志参数初始化
	(function(){
		//原始搜索词
		QUERY_KEYWORD = html_chars_decode(window.QUERY_KEYWORD);
		REAL_KEYWORD = html_chars_decode(window.REAL_KEYWORD);
		$('#key').val(QUERY_KEYWORD);
		//日志参数
		if( typeof LogParm =='undefined') { LogParm={ab:'0000', result_count:0}; }
		LogParm.rec_type = LogParm.rec_type || '0';
		LogParm.ev = LogParm.ev || 0;
		LogParm.cid = LogParm.cid || '';
		LogParm.psort = LogParm.psort || '';
		LogParm.page = LogParm.page || '1';
		LogParm.sig = LogParm.sig || '';
		LogParm.rel_cat2 = LogParm.rel_cat2 || '';
		LogParm.rel_cat3 = LogParm.rel_cat3 || '';
		LogParm.log_id = LogParm.log_id || '';
		LogParm.expand = LogParm.expand || '';
		LogParm.mtest = LogParm.mtest || '';
	})();
	//埋点
	window.searchlog = getQueryString('forcebot') ? function(){} : function(){
		var args = Array.prototype.slice.call(arguments, 0), url, key, rel_key = args.length>4 && args[0]==1 && (args[3]==52 || args[3]==62) ? args.splice(4,1,'')[0] : '';
		if ( args[0]=='e' ) {
			key = LogParm.ekey; args.shift(); args.splice(4, 1, QUERY_KEYWORD);
		} else if ( args[0]==1 && window.REAL_KEYWORD ) {
			key = window.REAL_KEYWORD; window.REAL_KEYWORD != QUERY_KEYWORD && args.splice(4, 1, QUERY_KEYWORD);
		} else {
			key = window.QUERY_KEYWORD; args[0]==0 && window.REAL_KEYWORD && REAL_KEYWORD != QUERY_KEYWORD && args.splice(1, 1, REAL_KEYWORD);
		}
		var search_log_url = "https://sstat.jd.com/scslog?args=", j = args.length, expand = '';
		var search_log_args = encodeURIComponent(key)+"^#psort#^#page#^#cid#^"+encodeURIComponent(window.location.href);
		var mercury = {
			keyword: key,
			ev: LogParm.ev,
			ab: LogParm.ab,
			mtest: LogParm.mtest,
			rel_ver: readCookie('rkv') || '',
			sig: LogParm.sig,
			rel_cat2: LogParm.rel_cat2,
			rel_cat3: LogParm.rel_cat3,
			logid: LogParm.log_id,
			loc: readCookie('ipLoc-djd') || '',
			referer: document.referrer,
			anchor: window.location.hash.substr(1)
		};
		if ( j > 0 ) {
			if (args[0]==0) {
				mercury.front_cost = LogParm.front_cost = LogParm.front_cost || '0';
				mercury.back_cost = LogParm.back_cost  = LogParm.back_cost  || '0';
				mercury.ip = LogParm.ip = LogParm.ip || '';
				mercury.rec_type = LogParm.rec_type;
				mercury.result_count = LogParm.result_count;
				mercury.word = args[1] || LogParm.word || '';
				url = search_log_url + LogParm.rec_type + "^"+search_log_args+"^^^"+LogParm.result_count+"^"+args[1]+"^"+LogParm.ev+"^"+LogParm.ab+'^'+LogParm.back_cost+'^'+LogParm.front_cost+'^'+LogParm.ip;
				//referrer
				url += '^'+encodeURIComponent(document.referrer);
				//expand
				expand += LogParm.expand;
			} else if (args[0]==1) {
				if (LogParm.rec_type != 10){
					url = search_log_url + "1^"+search_log_args + "^";
					mercury.rec_type = 1;
				} else {
					url = search_log_url + "11^"+search_log_args + "^";
					mercury.rec_type = 11;
				}
				for (var i=1, len=Math.min(5,j); i<len; i++){
					url += encodeURI(args[i]) + "^";
				}
				if ( j > 3 ) {
					switch ( parseInt(args[3]) ) {
						case 51:
							LogParm.cid = args[1];
							break;
						case 55:
							LogParm.psort = args[1];
							break;
						case 56:
							LogParm.page = args[1];
							break;
						case 1:
						case 2:
						case 3:
						case 4:
						case 5:
						case 6:
						case 81:
						case 82:
						case 83:
							mercury.bhv = window.__behaivor__.getBehavior(String(args[1]), window.event || arguments.callee.caller.arguments[0]);
						default:
							mercury.wid = args[1];
							break;
					}
				}
				if ( j>=5 ) {
					mercury.word = args[4];
				}
				if ( rel_key ) {
					mercury.rel_key = rel_key;
				}
				mercury.pos = args[2];
				mercury.type = args[3];
				for (var i = 0, len = (5 - j); i < len; i++){
					url += "^";
				}
				url += LogParm.ev + "^" + LogParm.ab;
				//referrer
				url += '^^^^'+encodeURIComponent(document.referrer);
				//expand
				expand += j>=6 && args[5]!='' ? LogParm.expand+'$'+args[5] : LogParm.expand;
			}
			//sstat
			url = url.replace('#cid#', LogParm.cid).replace('#psort#', LogParm.psort).replace('#page#', LogParm.page);
			url += '^'+LogParm.rel_cat2+'^'+LogParm.rel_cat3+'^'+LogParm.log_id+'^'+encodeURIComponent(expand)+'^'+encodeURIComponent(LogParm.mtest);
			//
			$.getScript(url+'&sig='+encodeURIComponent(LogParm.sig));
			//mercury
			mercury.cid = LogParm.cid;
			mercury.sort = LogParm.psort;
			mercury.page = LogParm.page;
			//conver format
			expand = expand.split('$');
			for (var i=0, j=expand.length, k; i<j; i++) {
				k = expand[i].indexOf('=');
				if ( k>0 ) {
					mercury[expand[i].substr(0,k)] = expand[i].substr(k+1);
				}
			}
			//
			ngloader('search.000001', mercury);
		}
	};
	// 品专广告上报
	window.searchPointReport = function(url) {
		if (url) {
			var html = '<img src="'+ url +'" style="display:none;">';
			$('#hasAdsStore').append(html);
		}
	};
	// 穿插广告上报
	window.searchAdvPointReport = function(url) {
		if (url) {
			var imgObj = new Image();
			url = url.indexOf("?") < 0 ? url + "?random=" + Math.random() : url + "&random=" + Math.random();
			imgObj.src = url;
		}
	};
	//数字商品免费下载
	window.call_free_download = function(productId, productKey) {
		if (productId && productKey) {
			checkLogin(function(){eBookSentMsg(productId, productKey)});
		} else if (productId && !productKey) {
			var pin = readCookie('pin');
			if (pin) checkLogin(function(){ditigalMusicSendMsg(pin, productId)});
		}
	};
	//onebox点击埋点
	searchUnit.userActionOnebox = function(actionType) {
		//box类型，点击类型
		var type, click;
		//
		switch(parseInt(actionType)) {
			case 1:
				type = 'chongzhi'; click = 'order=1'; break;
			case 2:
				type = 'liuliang'; click = 'order=1'; break;
			case 3:
				type = 'jipiao'; click = 'order=0$foreign=0'; break;
			case 4:
				type = 'jipiao'; click = 'order=1'; break;
			case 5:
				type = 'jipiao'; click = 'order=0$foreign=1'; break;
			case 6:
				type = 'caipiao'; click = 'order=1'; break;
			case 7:
				type = 'caipiao'; click = 'order=0'; break;
			case 8:
				type = 'card'; click = 'order=1'; break;
			case 9:
				type = 'card'; click = 'order=0'; break;
			default:
				type = ''; click = ''; break;
		}
		if ( type && click ) {
			searchlog(1, 0, 0, 59, type, click);
		}
	};
	//车管家
	searchUnit.pageLoad = function(accy_id, car_id) {
		window.location.href = '//search.jd.com/Search?keyword=' + encodeURIComponent(QUERY_KEYWORD) + '&enc=utf-8&qrst=1&accy_id=' + encodeURIComponent(accy_id) + '&car_id=' + car_id;
		return false;
	};
	var searchGetShopId = function(shop_id, vender_id) {
		var $shopDom = $('#J_shop_focus_' + vender_id);
		// 普通店铺维持原有逻辑，广告店铺取店铺id（venderid与shopid可能不同）
		$shopDom = ($shopDom && $shopDom.length > 0) ? $shopDom : $('[data-ad-shop-venderid][data-shopid=' + shop_id + ']');
		return $shopDom;
	};
	//店铺加关注、登录弹窗
	searchUnit.shopFocus = function(shop_id, vender_id, action) {
		if ( action=='do' ) {
			//加关注
			checkLogin(function(){
				$.getJSON('//fts.jd.com/follow/vender/follow?sysName=search.jd.com&venderId='+shop_id+'&callback=?',function(res){
					if (typeof res=='object' && (res.data || res.code=='F0402')){
						var $shopDom = searchGetShopId(shop_id, vender_id);
						$shopDom && $shopDom.addClass('z-focused')
						.mouseenter(function(){
							$(this).addClass('z-focus-cancle').find('em').html('取消');
						}).mouseleave(function(){
							$(this).removeClass('z-focus-cancle').find('em').html('已关注');
						}).find('em').html('已关注');
					}
				});
			});
		} else if ( action=='undo' ) {
			//取消关注
			checkLogin(function(){
				window.seajs.use(['jdf/1.0.0/ui/dialog/1.0.0/dialog'],function(dialog){
					$('body').dialog({
						title: '关注', hasButton: true, fixed: true, width: 384, extendMainClass: 'dialog-confirm',
						source: '<div class="m-tipbox2"><div class="tip-inner tip-warn"><div class="tip-title"><i class="tip-icon"></i><div class="title-main">是否取消关注该品牌店铺？</div></div></div></div>',
						submitButton: '确定', cancelButton: '取消',
						onSubmit: function(){
							//关闭原对话框
							$('.ui-dialog,.ui-mask').remove();
							//取消关注
							$.getJSON('//fts.jd.com/follow/vender/unfollow?sysName=search.jd.com&venderId='+shop_id+'&callback=?',function(res){
								if (typeof res=='object' && res.data==true){
									var $shopDom = searchGetShopId(shop_id, vender_id);
									$shopDom && $shopDom.removeClass('z-focused z-focus-cancle').unbind('mouseenter mouseleave').find('em').html('加关注');
								}
							});
						},
						onCancel: function(){
							$('.ui-dialog,.ui-mask').remove();
						}
					})
				})
			})
		} else if ( action=='check' ) {
			//检查关注状态
			$.getJSON('//fts.jd.com/follow/vender/batchIsFollow?sysName=search.jd.com&venderIds='+shop_id+'&callback=?', function(res){
				if ( res && typeof res=='object' && typeof res.data=='object' && res.success ) {
					for (var i in res.data) {
						if ( res.data[i] ) {
							var $shopDom = searchGetShopId(shop_id, vender_id);
							$shopDom && $shopDom.addClass('z-focused')
							.mouseenter(function(){
								$(this).addClass('z-focus-cancle').find('em').html('取消')
							}).mouseleave(function(){
								$(this).removeClass('z-focus-cancle').find('em').html('已关注')
							}).find('em').html('已关注');
						}
					}
				}
			})
		}
	};


	//同步自身做为iframe时的高度
	SEARCH.sync_iframe_height = function() {
		self != top && typeof parent.searchUnit=='object' && typeof parent.searchUnit.resizeOnebox=='function' && parent.searchUnit.resizeOnebox($(document.body).height()-10, 'promotion', function(){pageConfig.FN_ImgError(document);$('#J_main').lazyload({type:"img",placeholderClass:"err-product",delay:20,space:200})});
	};
	//相关搜索
	SEARCH.relate_search = {
		init : function() {
			var sKeyword = QUERY_KEYWORD, _this = this, ab = LogParm.ab, is_market = pageConfig.searchType==9;
			if ( sKeyword && location.hostname=='search.jd.com' ) {
				$.ajax({
					url: '//qpsearch.jd.com/relationalSearch?keyword='+encodeURIComponent(sKeyword).toLocaleLowerCase() + '&ver=' + (getQueryString('rel_ver') || 'auto') + '&client=' + (is_market ? 'cs' : 'pc'),
					async: true, scriptCharset: 'utf-8', dataType: 'jsonp',
					success: function(data){
						_this.callback(data, true, ab, is_market);
					}
				});
			} else {
				_this.callback('', false, ab, is_market);
			}
		},
		callback : function(data, enable_log, ab, is_market) {
			var data = typeof data=='string' ? data : '', index = data.indexOf('^^'), version = index>-1 ? data.substr(0, index) : '', aRespTmp = data.substr(index>-1 ? index+2 : 0).replace(/\*$/, "").split("*"), aResp = [], html = '';
			for ( var i=0, j=aRespTmp.length; i<j; i++) {
				if ( ""==aRespTmp[i] ) { continue; }
				aResp.push(aRespTmp[i]);
			}
			for (var i=0, j=aResp.length; i<j; i++) {
				var sVal = aResp[i], sClass = i==0 ? ' class="fore"' : '', sTail = i==j-1 ? '' : '<b>|</b>';
				html += '<a onclick="searchlog(1,0,'+i+',#{type},\''+sVal+'\')" href="Search?keyword='+encodeURIComponent(sVal)+'&enc=utf-8'+ (is_market ? '&market=1' : '') +'&spm=2.1.'+i+'"'+sClass+'>'+sVal+'</a>'+sTail ;
			}
			//
			if ( html ) {
				//搜索框下方区域
				$("#hotwords").html(html.replace(/#{type}/g, 52));
				//页面底部的相关搜索
				$('.search-ext .fg-line-value').html(html.replace(/#{type}/g, 62));
			} else {
				//热门搜索词
				$.ajax({url:"//dc.3.cn/cathot/get2",dataType:"jsonp",scriptCharset:"gbk",success:function(b){if(typeof b!="object"||!b.data){return}for(var d=0,i=Math.min(b.data.length,8),j="",l=[],a;d<i;d++){a=b.data[d];if(a.n&&a.u){j+='<a href="'+a.u+'" target="_blank"'+(d==0?' style="color:#f30213;"':"")+">"+a.n+"</a><b>|</b>"}}$("#hotwords").html(j.substr(0,j.length-8))}});
				//隐藏页面底部的相关搜索
				$('.search-ext').hide();
			}
			//搜索框UI
			$("#hotwords").addClass('haveline'); $("#key").addClass('blurcolor').bind("focus",function(){$(this).addClass('defcolor').removeClass('blurcolor')}).bind("blur",function(){$(this).addClass('blurcolor').removeClass('defcolor')});
			//
			if ( enable_log ) {
				//上报展示埋点
				ngloader('search.000008', {keyword: QUERY_KEYWORD, ab: ab, from: version, num: j, word: aResp.join('*')});
				//记录cookie，便于后续统计点击、转化
				document.cookie = 'rkv=' + version + ';path=/;domain=.search.jd.com';
			}
		}
	};

	SEARCH.get_shop_info = function() {
		//init
		var shopid_list = [];
		var venderId = null;
		var shopId = null;
		//店铺id集合
		$('span.m-focus').not('[data-done="1"]').each(function(){
			this.setAttribute('data-done', '1');
			if(this.getAttribute('data-venderId')){
				venderId = this.getAttribute('data-venderId');
			}
			if(this.getAttribute('data-shopid')){
				shopid_list.push(this.getAttribute('data-shopid')); 
				shopId = this.getAttribute('data-shopid');
			}
			
		});
		//店铺信息填充
		venderId && $.getJSON('shop_head.php?ids=' + venderId, function(res){
			if ( typeof res=='object' && res.length ) {
				for (var i=0, j=res.length, target, tag, summary; i<j; i++) {
					//店铺obj
					target = $('#J_shop_focus_'+res[i].venderId).parent().parent().parent();
					//店铺标签初始化
					tag = '';
					//店铺简介
					summary = res[i].shop_brief || res[i].summary;
					//logo
					var logo = res[i].shop_logo ? res[i].shop_logo : '//misc.360buyimg.com/product/search/0.0.9/css/i/shop-def.png';
					logo = logo.replace('http://','//')
					//target.find('img').attr('src', logo).removeClass();
					$('#shopLogo').attr('src', logo).removeClass();
					//名称
					target.find('.shop-name a').html(res[i].shop_name);
					//主营品牌，简介
					res[i].main_brand && target.find('.shop-infor').eq(0).html('主营品牌：'+res[i].main_brand).next().html(summary ? '店铺简介：'+summary : '');
					//钻石服务
					if ( res[i].icon==1 ) { tag += '<a class="shop-tag-img" title="京东品质认证商家"><img src="//img11.360buyimg.com/uba/jfs/t3319/144/278979502/1525/f89d43fe/580484b5Ncf61e7b9.png" alt="品质认证" width="72" height="18"></a>'; }
					//京东自营
					if ( res[i].vender_type==1 ) { tag += '<em class="shop-act-tag tag-jd">京东自营</em>'; }
					//店铺标签
					tag && target.find('.shop-name').append(tag);
					//综合评分
					res[i].vender_total_score==0 ? target.find('.J_total_score').html('-') : target.find('.J_total_score').html(res[i].vender_total_score/100);
					//商品评分
					res[i].vender_ware_score==0 ? target.find('.J_ware_score').html('-') : target.find('.J_ware_score').html(res[i].vender_ware_score/100).after(res[i].vender_ware_score>=res[i].industry_total_score ? '<i class="i-up"></i>' : '<i class="i-down"></i>');
					//服务评分
					res[i].vender_service_score==0 ? target.find('.J_service_score').html('-') : target.find('.J_service_score').html(res[i].vender_service_score/100).after(res[i].vender_service_score>=res[i].industry_service_score ? '<i class="i-up"></i>' : '<i class="i-down"></i>');
					//时效评分
					res[i].vender_effective_score==0 ? target.find('.J_effective_score').html('-') : target.find('.J_effective_score').html(res[i].vender_effective_score/100).after(res[i].vender_effective_score>=res[i].industry_effective_score ? '<i class="i-up"></i>' : '<i class="i-down"></i>');
					//加减关注
					target.find('.m-focus').click(function(){searchUnit.shopFocus($(this).attr('data-shopid'), $(this).attr('data-venderid'), $(this).hasClass('z-focused') ? 'undo' : 'do')});
					if(res[i].goodShop){
						var shophtml = '';
						shophtml +='<div class="shop-infor">';
						shophtml +='<div class="shop-star-mode">';
						shophtml +='	<span class="shop-site">';
						shophtml +='		<img src="//img11.360buyimg.com/schoolbt/jfs/t1/104715/29/50/445/5da680d2Ede2043fc/c58d7090452e9811.png">';
						shophtml +='	</span>';
						shophtml +='</div>';
						shophtml +='</div>';
						target.find('.shop-name').after(shophtml);
					}else if(res[i].yhome){
						var shophtml = '';
						shophtml +='<div class="shop-infor">';
						shophtml +='<div class="shop-star-mode">';
						shophtml +='	<span class="shop-site">';
						shophtml +='		<img src="//img12.360buyimg.com/schoolbt/jfs/t1/59689/24/13072/1130/5da680d0E06c894d1/f05b1ff2036d5787.png">';
						shophtml +='	</span>';
						shophtml +='</div>';
						shophtml +='</div>';
						target.find('.shop-name').after(shophtml);
					}else if(res[i].scoreRankRateGrade && res[i].scoreRankRateGrade > 0 && shopId != 598847 && shopId != 659016){
						var star = res[i].scoreRankRateGrade*10*2;
						star = star > 92 ? star : star + 2;
						star = star > 100 ? 100 : star;
						var shophtml = '';
						shophtml +='<div class="shop-infor">';
						shophtml +='<div class="shop-star-mode">';
						shophtml +='    <span class="star-cont">';
						shophtml +='        店铺星级';
						shophtml +='        <span class="shop-star-box">';
						shophtml +='            <span class="shop-star">';
						shophtml +='                <span class="star" style="width:'+star+'%"></span>';
						shophtml +='            </span>';
						shophtml +='        </span>';
						shophtml +='    </span>';
						shophtml +='</div>';
						shophtml +='</div>';
						target.find('.shop-name').after(shophtml);
					}
				}
			}
		})
		// 对于广告店铺，需做曝光上报，单独绑定‘关注店铺’
		if($('#hasAdsStore').attr('data-exposal-url')) {
			var target = $('[data-ad-shop-venderid]'); 
			venderId = target.attr('data-ad-shop-venderid');
			if (target) {
				// shopid 和 venderid不一定相同
				shopid_list = [target.attr('data-ad-shop-venderid'), target.attr('data-shopid')];
				target.click(function(){
					searchUnit.shopFocus($(this).attr('data-shopid'), $(this).attr('data-ad-shop-venderid'), $(this).hasClass('z-focused') ? 'undo' : 'do');
				});
			}
			if (!window.adExpoReported && $('#hasAdsStore').is(':visible')) {
				// 广告曝光上报
				window.adExpoReported = true;
				searchPointReport($('#hasAdsStore').attr('data-exposal-url'));
			}
		} 
		//查询店铺关注状态
		shopid_list.length && searchUnit.shopFocus(shopid_list.join(','), venderId, 'check');
	};

	//前端价格
	SEARCH.get_digital_price = function(ids, attach) {
		this.enable_price && seajs.use('product/module/tools', function(tools){
			tools.getPrice(ids.replace(/J_/g, '').split(','), pageConfig.price_pdos_off, function(data){
				if ( typeof data=='object' ) {
					//
					for (var i=0, j=data.length, p=''; i<j; i++) {
						if ( data[i].p<0 ) {
							p = '<i>暂无报价</i>';
						} else if ( data[i].p==0 ) {
							p = '<i>免费</i>';
						} else {
							p = '<em>￥</em><i>'+data[i].p+'</i>';
						}
						//附属商品
						if ( attach ) {
							$("em."+data[i].id).html(strip_tags(p));
						} else {
							//获取后台返回的价格
							$("strong."+data[i].id).html(p);
						}
					}
				}
			}, {ext:'11',pin:decodeURIComponent(readCookie('pin')||'')})
		})
	};

	/*
	 * tpl==-1表示获取换购商品的库存状态，提供给UED调用
	 */
	SEARCH.get_ware_stock = function(ids, tpl, callback) {
		if (!this.enable_stock) { return; } // 库存容灾开关
		var pduid = readCookie('__jda'), pduid = pduid && pduid.indexOf('.')>-1 ? pduid.split('.')[1] : '', pdpin = readCookie('pin') || '', loc_list = (readCookie('ipLoc-djd')||'').replace(/\..*$/, '').split('-'), loc = loc_list.slice(0,4).join('-'), enable_spu_stock = this.enable_stock==2, spu_ids = [];
		//format
		ids = tpl != -1 && ids ? ids.substr(2).split(',J_') : ids;
		//sku库存
		if ( ids ) { get_stock(ids, 'sku', 30); } else { stock_loaded([],'sku'); }
		//库存加载成功时候的回调
		function stock_loaded(json, type) {
			var el_btns = $('#J_main').find('a[data-stock]'), el_btn, wid, info, is_tsyx, data_sv, nostore = [], spu;
			for (var i=0, l=el_btns.length; i<l; i++ ) {
				el_btn = el_btns.eq(i), wid = el_btn.attr('data-stock'), info = json[wid], is_tsyx = WareType.book(wid) || WareType.mvd(wid);
				//移除属性，防止el_btns重复处理
				( type=='sku' || info ) && el_btn.removeAttr('data-stock');
				//商品售止
				if ( type=='sku' && el_btn.attr('data-sv')==34 ) {
					(tpl=='1' || tpl=='2') && el_btn.hasClass('J_notification') && is_tsyx && nostore.push(wid);
					//tpl为3时，sku无货需请求spu库存
					tpl == 3 && enable_spu_stock && (spu = el_btn.closest('li').attr('data-spu')) && spu_ids.push(spu) && el_btn.attr('data-stock', spu);
				} else if ( type=='spu' && info && info['a']==34 ) {
					//当spu也无货时，才展示无货标签
					el_btn.parent().siblings('.p-stock').css('display', 'block');
				}
			}
			if ( type=='sku' ) {
				//重新发起spu库存请求
				spu_ids.length && get_stock(spu_ids, 'spu', 5);
				//商品售止
				nostore.length && $.getJSON('coupon.php?type=sale&sku='+nostore.join(','), function(data){
					if ( typeof data!='object' ) { return ; }
					for (var i in data) {
						if ( data[i]!=0 ) { continue; }
						if ( tpl=='2') { $("#J_store_"+i).replaceWith('<a class="p-o-btn addcart disabled"><i></i>加入购物车</a>'); } else { $("#J_store_"+i).before('<a href="javascript:;" class="'+(tpl=='1' ? 'p-o-btn addcart disabled' : 'disabled')+'"><i></i>加入购物车</a>').remove(); }
					}
				});
			}
		}
		//换购商品库存回调，只返回无货的sku列表
		function exchange_stock_loaded(json){
			if ( !json || typeof json!='object' || typeof callback!='function' ) { return false; }
			var city = loc_list[0] ? window.json_city[0][loc_list[0]] : '', no_stock_list = [];
			for (var wid in json) {
				var stock_val =  json[wid]['u']==1 ? 33 : parseInt(json[wid]['a']);
				if ( stock_val==0 || stock_val==18 || stock_val==34 ) {
					no_stock_list.push(wid);
				}
			}
			//执行callback
			callback(no_stock_list, city);
		}
		//库存接口sku数目不能超过30个
		function get_stock(ids, type, max_length) {
			do {
				$.ajax({
					url: '//fts.jd.com/' + (type=='sku' ? 'areaStockState' : 'areaStockSpuState') + '/mget?app=search_pc&ch=1&' + (type=='sku' ? 'skuNum' : 'spuNum') + '='+ids.splice(0,max_length).join(';')+'&area='+loc.replace(/-/g, ',')+'&pduid='+pduid+'&pdpin='+pdpin,
					async: true,
					dataType: 'jsonp',
					success: function(json) {
						tpl==-1 ? exchange_stock_loaded(json) : stock_loaded(json, type);
					}
				});
			} while (ids.length);
		}
	};
	
	SEARCH.get_icon_info = function() {
		//
		var fare_ids = [],slaveWarePresale_ids = [], slaveWarePresaleContainer = {}, presale_ids = [], lease_ids = [],lease_container={}, container = {}, obj = $('#J_goodsList').find('.p-img div[data-catid]').not('[data-done="1"]');
		var slaveWarePresaleObj = $('#J_goodsList').find('.ps-main img[data-sku]').not('[data-done="1"]');
		obj.each(function(){
			//获取数据
			var sku = $(this).closest('li').attr('data-sku'), info = $(this).data();
			//运费险参数
			info.venid && fare_ids.push(info.venid);
			//预约预售
			info.presale=='1' && presale_ids.push(sku) && (container[sku] = $(this).parent().parent());
			info.lease =='1' && lease_ids.push(sku) && (lease_container[sku] = $(this).parent().parent());
			
			//防止二次加载重复调用
			this.setAttribute('data-done', '1');
		});
		slaveWarePresaleObj.each(function(){
			//获取数据
			var sku = $(this).attr('data-sku'), info = $(this).data();
			slaveWarePresale_ids.push(sku);
			slaveWarePresaleContainer[sku] = $(this)
			info.presale == '1' && presale_ids.push(sku);
			this.setAttribute('data-done', '1');
		});
		//运费险
		fare_ids.length && $.getJSON('//baozhang.jd.com/service/getAllInsure?venderIds='+fare_ids.unique().join('-')+'&callback=?', function(res){
			if ( typeof res=='object' ) {
				for (var i=0, j=res.length; i<j; i++) {
					res[i].yFX=='1' && obj.filter('[data-venid="'+res[i].venderId+'"]').each(function(){
						$(this).parent().parent().find('.p-icons').append('<i class="goods-icons2 J-picon-tips" data-tips="退换货免运费">险</i>');
					})
				}
			}
		});
		//预约预售
		if ( presale_ids.length) {			
			var open_interval = false;
			//批量请求
			$.getJSON('//yushou.jd.com/youshouinfoList.action?sku='+presale_ids.join(',')+'&callback=?', function(json){
				if ( typeof json=='object' ) {
					//each
					for(var sku in json) {
						//
						var data = $.parseJSON(json[sku]); 
						if ( typeof(data)!="object" || data.type!=1 && data.type!=2 ) {
							continue;
						}
						//
						var _this = container[sku];
						if(_this){
							if(data.showPromoPrice && data.showPromoPrice == 1 && data.sellWhilePresell && data.sellWhilePresell == 1 && data.type && data.type==1){
								_this.find('#presaleBelt').show();
								_this.find('#presaleBelt').attr('id','');
							}else{
								SEARCH.presaleShow(_this, data, sku) && delete container[sku];
								//计时开关
								open_interval = true;
							}
							
						}
						if(slaveWarePresaleContainer[sku]){
							SEARCH.slaveWarePresaleData[sku] = data;
							var dataPriceContent = null;
							if ( data.type==1 ) {
								if ( data.hidePrice && data.hidePrice == 1 ) {
									dataPriceContent =  '待发布';
								}
							}else{
								if(data.ret){
									var ret = data.ret
									if ( ret.hideRealPrice==1 || ret.hidePrice==1 && ret.cs==3 ) {
										dataPriceContent =  '待发布';
									} else if ( ret.cp ) {
										dataPriceContent = ret.expAmount>0 && ret.depositWorth>0 && ret.oriPrice>0 ? ret.oriPrice : ret.cp;
									}
								}
							}
							if(data && data.showPromoPrice && data.showPromoPrice == 1 && data.sellWhilePresell && data.sellWhilePresell == 1 && data.type && data.type==1){
								$(slaveWarePresaleContainer[sku]).data('isPresaleSell', 1)
							}
							if(dataPriceContent){
								$(slaveWarePresaleContainer[sku]).data('dataPriceContent', dataPriceContent)
							}
						}
					}
				}
				for(var slavesku in slaveWarePresaleContainer){
					$(slaveWarePresaleContainer[slavesku]).bind('mouseenter', function(){
						var sku = $(this).attr('data-sku');
						var data = SEARCH.slaveWarePresaleData[sku];
						var _this = $(this).parent().parent().parent().parent().parent().parent();
						if(data && (!data.showPromoPrice || data.showPromoPrice != 1 || !data.sellWhilePresell || data.sellWhilePresell != 1)){
							SEARCH.presaleShow(_this, data, sku, true);
						}
						
					});
				}
				//未能正确返回预售价的商品，恢复显示实时价格
				var skus = [];
				for (var i in container) {
					//预售商品价格obj
					skus.push(i);
					var target = $('strong.J_'+i).filter('[data-price]');
					//
					target.html('<em>￥</em><i>' + target.attr('data-price') + '</i>').removeAttr('data-price');
				}
			})
		}
		//pop租赁，忽略预约预售产品
		lease_ids.length && $.getJSON('//zuzuapi.jd.com/pcClient/pop/goods/fetchAttr?skuIds='+lease_ids.join(',')+'&callback=?', function(res){
			if ( typeof res=='object' ) {
				var rs = res.code == 0 && res.data && res.data.length > 0 ? res.data : [];
				for (var i=0, j=rs.length; i<j; i++) {
					if( rs[i] && rs[i].unitRent){
						var time = '/天';
						switch(rs[i].leaseUnit){
						 case 1:
						 time = '/天';
						 break;
						 case 2:
						 time = '/周';
						 break;
						 case 3:
						 time = '/月';
						 break;
						}
						var unitRents = rs[i].unitRent.split('.');
						var pr = '';
						if(unitRents.length > 1){
							pr = unitRents[0] + '.' + unitRents[1];
							if(unitRents[1].length == 1){
								pr = pr + '0';
							}
						}else{
							pr = rs[i].unitRent + '.00';
						}
						$('strong.J_'+rs[i].skuId).html('<em>￥</em><i>' + pr + '</i><em>'+ time +'</em');
					}
				}
			}
		});
	};
	SEARCH.slaveWarePresaleData = {};
	SEARCH.presaleShow = function(_this, data, sku, iscallback){
		var rs = false;
		if(data && sku){
			var ret, time, status, time_html, price_obj, price=false ;
			//预约商品
			if ( data.type==1) {
				//倒计时
				time = data.d;
				//细化预约状态
				switch ( parseInt(data.state) ) {
					case 1: status = '预约未开始'; break;
					case 2: status = '预约中'; break;
					case 3: status = '抢购未开始'; break;
					case 4: status = '抢购中'; break;
					case 5: status = '抢购结束'; break;
					default: return false;
				}
				if (data.hidePrice && data.hidePrice == 1) {
					price = '待发布';
					rs = true;
				}
			} else {
				ret = data.ret; time = ret.d; status = ret.s=='0' ? '预售未开始' : '预售中';
				//预售类型阶梯
				if (ret.t=="2" && ret.sa) {
					var stage_html = '<div id="presale_show_data" class="p-presell-stage clearfix">';
					for (var i=0, j=ret.sa.length, class_name; i<j; i++) {
						class_name = (i+1)<ret.cs ? ' timeout' : (i+1)==ret.cs ? ' curr' : '';
						stage_html += '<span class="item'+class_name+'"><a href="javascript:void(0)"><em>满'+ret.sa[i].c+'人</em>';
						stage_html += '<strong>￥'+ret.sa[i].m+'</strong></a><i class="bottom"><em></em></i></span>';
					}
					stage_html += '</div>';
					_this.find('#presale_show_data').remove();
					_this.find(".p-name").after(stage_html);
				}
				if(!iscallback){
					if ( ret.hideRealPrice==1 || ret.hidePrice==1 && ret.cs==3 ) {
						price = '待发布';
						rs = true;
					} else if ( ret.cp ) {
						price = ret.expAmount>0 && ret.depositWorth>0 && ret.oriPrice>0 ? ret.oriPrice : ret.cp;
						rs = true;
					}
				}
			}
			if(price){
				price_obj = _this.find(".p-price").find('strong i');
				price_obj.html(price);
			}
			//倒计时
			if( !data.showPromoPrice || data.showPromoPrice != 1 || !data.sellWhilePresell || data.sellWhilePresell != 1 ){
				time_html = '<div id="presale_show_item" class="p-presell-time" data-time="'+time+'"><i></i><span>'+status+'</span><em>'+SEARCH.presaleShowtimeHtml(time)+'</em></div>';
				_this.find('#presale_show_item').remove();
				_this.append(time_html).parent().addClass('gl-item-presell');
			}
		}else{
			_this.find('#presale_show_data').remove();
			_this.find('#presale_show_item').remove();
		}
		return rs;
	};
	SEARCH.presaleShowtimeHtml = function(time) {
		numberFormat = function(str, length) {
			str = str.toString();
			return str.length>=length ? str : numberFormat("0"+str, length);
		};
		var day = Math.floor(time/86400); 
		time -= day*86400;
		var hour = Math.floor(time/3600); 
		time -= hour*3600; hour = numberFormat(hour, 2);
		var minute = Math.floor(time/60); 
		time -= minute*60; minute = numberFormat(minute, 2);
		var second = numberFormat(time, 2);
		return day>0 ? '剩余'+day+'天'+hour+'时'+minute+'分' : '剩余'+hour+'时'+minute+'分'+second+'秒';
	};
	SEARCH.get_prompt_adwords = function(ids) {
		this.enable_prom_adwords && $.getJSON('//ad.3.cn/ads/mgets?source=search_pc&skuids='+ids.replace(/J_/g, 'AD_')+'&callback=?', function(json){
			if (!json){ return; }
			for (var i=0, l=json.length; i<l; i++) {
				var id=json[i].id||'',obj=$('#J_'+id),adTitle=strip_tags(json[i].ad);
				if (obj.length && adTitle!=='') {
					obj.html(adTitle).parent().attr("title",adTitle).closest('li').find('.p-img>a').attr("title",adTitle);
				}
			}
		});
	};

	SEARCH.get_prompt_flag = function(ids) {
		this.enable_prom_flag && $.getJSON('//pf.3.cn/flags/mgets?source=search_pc&skuids='+ids+'&callback=?', function(data){
			if(!data || typeof data!=='object') { return; }
			for(var i=0, l=data.length, error_55=[], error_59=[]; i<l; i++) {
				var c = data[i], obj = $('#J_pro_'+c.pid), icon_html = get_html(c.pf, c.pfi, obj, c.pid);
				//插入标签
				insert_pf_icon(obj, icon_html);
			}
			//data not match
			if ( error_55.length || error_59.length ) {
				ngloader('search.000002', {
					logid: LogParm.log_id,
					key: QUERY_KEYWORD,
					err55: error_55.join(','),
					err59: error_59.join(',')
				})
			}
			function get_html(pf, pfi, obj, pid) {
				if ( !pf ) { return; }
				//校验后台返回的促销信息
				var promotion = obj.data('promotion'), wrong, promotion_type; if ( promotion && pfi ) {
					//默认不匹配
					wrong = true;
					//促销信息^促销id
					promotion = promotion.split('^'); promotion_type = promotion[1].substr(0,3);
					//校验后台返回的促销id是否正确
					for(var m = 0, n = pfi.length; m<n; m++) {
						//
						if ( pfi[m].indexOf(promotion[1]+'_')==0 ) {
							wrong = false;
							break;
						}
					}
					//记录不匹配sku
					wrong && (promotion_type=='55-' ? error_55.push(pid) : promotion_type=='59-' ? error_59.push(pid) : 0);
				}
				for(var i = 0, l = pf.length, bqarray = [], tips, promotion_name; i<l; i++) {
					switch (pf[i]){
						case 5:
							bqarray[1] = '<i class="goods-icons4 J-picon-tips" data-tips="购买本商品送赠品">赠</i>';
							break;
						case 55:
							if ( !bqarray[0] ) {
								//
								tips = '本商品参与满减促销'; promotion_name = '满减';
								//成功匹配
								if ( wrong==false && promotion_type=='55-' ) {
									//长度判断
									if ( promotion[0].length>10 ) {
										tips = promotion[0];
									} else {
										promotion_name = promotion[0];
									}
								} else if ( wrong==undefined && obj.siblings('.p-promo-flag').length==0 ) {
									//给wrong赋值，保证一个sku只上报一种类型的异常
									error_55.push(pid); wrong = true;
								}
								//
								bqarray[0] = '<i class="goods-icons4 J-picon-tips" data-tips="'+tips+'">'+promotion_name+'</i>';
							}
							break;
						case 58:
							//赠和满赠展示上互斥，优先展示赠
							if ( !bqarray[1] ) {
								bqarray[1] = '<i class="goods-icons4 J-picon-tips" data-tips="满指定金额即赠热销商品">满赠</i>';
							}
							break;
						case 59:
							if ( wrong==false && promotion_type=='59-' ) {
								bqarray[0] = '<i class="goods-icons4 J-picon-tips" data-tips="本商品参与满件促销">'+promotion[0]+'</i>';
							} else if ( wrong==undefined && obj.siblings('.p-promo-flag').length==0 ) {
								error_59.push(pid); wrong = true;
							}
							break;
						default:
							break;
					}
				}
				for(var i = 0, tmp = []; i<2; i++) {
					bqarray[i] && tmp.push(bqarray[i]);
				}
				return tmp.join('');
			}
		});
	};

	SEARCH.get_comment_nums = function(ids) {
		$.getJSON('//club.jd.com/comment/productCommentSummaries.action?referenceIds='+ids.replace(/J_/g, '')+'&callback=?', function(data){
			if( typeof(data)!="object" || typeof(data.CommentsCount)!="object" ) { return false; }
			for(var i=0, j=data.CommentsCount, k=j.length; i<k; i++) {
				if ( typeof(j[i].CommentCountStr)!="undefined" && !j[i].SensitiveBook) {
					$("#J_comment_"+j[i].SkuId).html(j[i].CommentCountStr);
					$("#J_comment_"+j[i].SkuId).parent().append('条评价');
				}
			}
		});
	};

	SEARCH.get_im_info = function(el_shops, ids) {
		if ( !el_shops || !ids ) { return false; }
		$.ajax({
			url: '//chat1.jd.com/api/checkChat?pidList='+ids,
			dataType: 'jsonp', jsonp: 'callback', scriptCharset: 'utf-8',
			success: function(res) {
				if ( typeof(res)!='object' ) { return false; }
				var area = $('#store-selector').find('.text').text(),
					is_match = function(str, reg, index) {
						str = str==undefined ? '' : $.trim(str);
						var res = str.match(reg); index = index==undefined ? 1 : index;
						return res===null ? '' : res[index];
					};
				for (var i=0, j=res.length; i<j; i++) {
					var curr = res[i], im_string = '', el_shop = el_shops[curr.pid], is_jd = el_shop.attr('data-selfware')=='1';
					if ( curr.code==1 ) {
						im_string = '<b class="'+ (is_jd ? 'im-02' : 'im-01') +'" style="background:url(//img14.360buyimg.com/uba/jfs/t26764/156/1205787445/713/9f715eaa/5bc4255bN0776eea6.png) no-repeat;" title="联系客服" onclick="searchlog(1,'+curr.shopId+',0,61)"></b>';
					} else if ( curr.code==3 ) {
						im_string = '<b class="'+ (is_jd ? 'im-02' : 'im-01') +'" style="background:url(//img14.360buyimg.com/uba/jfs/t26764/156/1205787445/713/9f715eaa/5bc4255bN0776eea6.png) no-repeat;" title="联系客服" onclick="searchlog(1,'+curr.shopId+',0,61)"></b>';
					} else { continue; }
					//获取、拼接参数
					var params = {};
					var stock = $.trim(el_shop.siblings('.p-stock').html());
					stock = stock=='暂不支持配送' ? stock : !stock?'有货':stock.substr(stock.length-2);
					params.stock = area + '（' + stock + '）';
					params.pid = curr.pid;
					params.score = el_shop.attr('data-score');
					params.evaluationRate = el_shop.attr('data-reputation');
					params.commentNum = el_shop.siblings('.p-commit').find('a').html();
					var img_obj = el_shop.siblings('.p-img').find('img').eq(0);
					var img_src = img_obj.attr('src');
					if ( img_src==undefined || img_src=='//misc.360buyimg.com/lib/img/e/blank.gif' ) {
						img_src = img_obj.attr('data-lazy-img');
					}
					params.imgUrl = is_match(img_src, /http\S+?\.com\/\w+?\/(\S+)/);
					params.wname = el_shop.siblings('.p-name').find('em').html().replace(/<span[\s\S]+?<\/span>|<font class="skcolor_ljg">|<\/font>/g, '');
					params.advertiseWord = $.trim(el_shop.siblings('.p-name').find('i.promo-words').html());
					params.seller = $.trim(curr.seller);
					params.entry = 'jd_search';
					//拼接url
					var url = '//'+curr.chatDomain+'/index.action?';
					for (var key in params) {
						url += key+'='+encodeURI(encodeURI(params[key]))+'&';
					}
					url = url.substr(0,url.length - 1);
					if(el_shop.find('span.J_im_icon').size() > 0){
						el_shop.find('span.J_im_icon').append(im_string).find('b').click((function(url){
							return function(){ window.open(url); return false; }
						})(url));
					}else{
						el_shop.find('span.J_im_icon,a.curr-shop').append(im_string).find('b').click((function(url){
							return function(){ window.open(url); return false; }
						})(url));
					}
				}
			}
		})
	};

	SEARCH.get_shop_name = function(tpl){//获取店铺名
		var _this = this, el_shops = $('#J_main').find('div.p-shop,div.p-shopnum').not('[data-done="1"]'), shop_length = el_shops.length, pids = [], shop_id, el_dongdong = {};
		if ( !shop_length ) { return false; }
		for(var i=0, arr=[], pid; i<shop_length; i++) {
			shop_id = el_shops[i].getAttribute('data-shopid');
			verderId = el_shops[i].getAttribute('data-verderId');
			var dongdong = el_shops[i].getAttribute('data-dongdong')
			shop_id && arr.push(verderId);
			pid = el_shops.eq(i).closest('li[data-sku]').attr('data-sku');
			pids.push(pid);	
			if(dongdong == ''){
				el_dongdong[pid] = el_shops.eq(i);
			}
			el_shops[i].setAttribute('data-done', '1');
		}
		if ( arr.length ) {
			$.getJSON('//rms.shop.jd.com/json/pop/shopInfo.action?callback=?',{'ids':arr.unique().join(",")}, function(res){
				if( !res && res.length < 1 ) { return; }
				for(var i=0,j=res.length,map={}; i<j; i++ ) { 
					if (res[i] && res[i].success && !res[i].errorMsg){
						map[res[i].venderId] = res[i];
					}
				}
				for(var i=0; i<shop_length; i++) {
					var el_shop = el_shops.eq(i), curr = map[el_shop.attr('data-verderId')];
					if ( !curr ) { continue; }
					curr.shop_id = el_shop.attr('data-shopid')
					//图书模板
					tpl=='2' && el_shop.removeAttr('data-shopid').find('a.curr-shop').replaceWith('<a class="curr-shop" target="_blank" onclick="searchlog(1,'+curr.shop_id+',0,58)" href="//mall.jd.com/index-'+curr.shop_id+'.html" title="'+curr.name+'">'+curr.name+'</a>');
					//3C、服装模板
					(tpl=='1' || tpl=='3') && el_shop.removeAttr('data-shopid').html('<span class="J_im_icon"><a target="_blank" onclick="searchlog(1,'+curr.shop_id+',0,58)" href="//mall.jd.com/index-'+curr.shop_id+'.html" title="'+curr.name+'">'+curr.name+'</a></span>');
				}
				//im图标
				_this.get_im_info(el_dongdong, pids.unique().join(','));
			});
		} else {
			_this.get_im_info(el_dongdong, pids.unique().join(','));
		}
	};

	SEARCH.get_ware_info = function() {
		var commit_ids = [],main_ids = [], icon_ids=[], plist = $('#J_main'), tpl = plist.find('ul.gl-warp').attr('data-tpl'), attach_ids = [];
		plist.find("strong[class^='J_']").not('[data-done="1"]').each(function(){
			this.setAttribute('data-done', '1'); main_ids.push(this.className);
		});
		//附属sku(纸质书绑定电子书)
		plist.find("em[class^='J_']").not('[data-done="1"]').each(function(){
			this.setAttribute('data-done', '1'); attach_ids.push(this.className);
		});
		//
		plist.find("div.p-icons").not('[data-done="1"]').each(function(){
			this.setAttribute('data-done', '1'); icon_ids.push(this.id.replace('pro_', ''));
		});
		plist.find("div.p-commit").not('[data-done="1"]').each(function(){
			this.setAttribute('data-done', '1'); 
			var id = $(this).find('strong').find('a').eq(0).attr('id');
			if(id){
				commit_ids.push(id.replace('J_comment_', ''));
			}
		});
		
		//数字价格
		main_ids.length && this.get_digital_price(main_ids.join(','), 0);
		//库存
		this.get_ware_stock("", tpl);
		//店铺信息
		this.get_shop_info();
		//只包含主商品
		if(commit_ids){
			//评论数
			commit_ids = commit_ids.join(',');
			!this.is_exchange_list && this.get_comment_nums(commit_ids);
		}
		if ( icon_ids.length ) {
			//
			icon_ids = icon_ids.join(',');
			//促销语
			this.get_prompt_adwords(icon_ids);
			//促销标签
			!this.is_exchange_list && this.get_prompt_flag(icon_ids);
		}
		//运费险、预约预售、租赁
		this.get_icon_info();
		//店铺名称 and 咚咚客服
		this.get_shop_name(tpl);
		//附属商品价格
		attach_ids.length && this.get_digital_price(attach_ids.join(','), 1);
	};

	SEARCH.get_diviner_ware = function() { //少结果调推荐
		var skus = [], _this = this, cid = _this.cid, plist = $('#J_goodsList'), loc_list = (readCookie('ipLoc-djd')||'').split('-'), city = loc_list[0] ? window.json_city[0][loc_list[0]] : '', num = $(window).width()>=1390 ? 10 : 8;
		//获取页面主商品
		plist.find('li[data-sku]').slice(0,4).each(function(){skus.push(this.getAttribute('data-sku')); });
		//
		$.ajax({
			url: '//diviner.jd.com/diviner?p=907006&skus='+skus.join(',')+'&uuid='+ (readCookie('__jda') || '') +'&pin='+(readCookie('pin')||'')
				+'&c3='+cid+'&lid='+loc_list[0]+'&lim='+num+'&ec=utf-8',
			dataType: 'jsonp',
			success: function(json) {
				if (typeof(json)!='object' || !json.success) return ;
				var diviner_html = '<div class="m-tipbox"><div class="tip-inner"><div class="tip-text">根据上面的商品结果，为您推荐的相似商品。</div></div></div><ul class="gl-warp clearfix J_diviner">',
				tpl = plist.find('ul[data-tpl]').attr('data-tpl'), tpl_item,
				new_image = function(src) {
					if(src){
						var img = new Image();
						src = src + "&m=UA-J2011-1&ref=" + encodeURIComponent(document.referrer) + "&random=" + Math.random();
						img.setAttribute('src', src);
					}
				};

				var tpl_scroll = '<div class="p-scroll"><span class="ps-prev">&lt;</span><span class="ps-next">&gt;</span><div class="ps-wrap"><ul class="ps-main">'+
						'<li class="ps-item"><a href="javascript:;" class="curr"><img data-sku="{#sku#}" width="25" height="25" data-lazy-img="{#img_url#}"></a></li>'+
						'</ul></div></div>',
					tpl_shop = '<div class="p-shop" data-selfware="0" data-score="5" data-reputation="100" data-done="1">{#shop#}</div>',
					book_shop = '<div class="p-shopnum" data-selfware="1" data-score="5" data-reputation="100" data-done="1"><span class="curr-shop">{#shop#}</span></div>';
				tpl_item = '<li data-sku="{#sku#}" class="gl-item" data-clk="{#clk#}" data-pos="{#pos#}"><div class="gl-i-wrap"><div class="p-img"><a target="_blank" title="{#t#}" href="//item.jd.com/{#sku#}.html">'+
				'<img data-img="1" data-lazy-img="{#img_url#}"></a></div>'+
				(tpl=='3' ? tpl_scroll : '')+
				'<div class="p-price"><strong class="J_{#sku#}"><em>￥</em><i>{#jp#}</i></strong></div>'+
				'<div class="p-name"><a target="_blank" title="{#t#}" href="//item.jd.com/{#sku#}.html"><em>{#t#}</em><i class="promo-words" id="J_AD_{#sku#}"></i></a></div>'+
				'<div class="p-commit"><strong>已有<a id="J_comment_{#sku#}" target="_blank" href="//item.jd.com/{#sku#}.html#comment">0</a></strong></div>'+
				(tpl=='3' ? '<div class="p-focus"><a class="J_focus" data-sku="{#sku#}" href="javascript:;" title="点击关注"><i></i>关注</a></div>'+tpl_shop:'') +
				'<div class="p-icons" id="J_pro_{#sku#}"></div>'+
				(tpl=='2' ? book_shop : '');
				if(tpl=='1' || tpl=='2'){
					tpl_item += '<div class="p-operate">'+
						(tpl=='1' ? '<a class="p-o-btn contrast J_contrast" data-sku="{#sku#}" href="javascript:;"><i></i>对比</a>' : '')+
						'<a class="p-o-btn focus J_focus" data-sku="{#sku#}" href="javascript:;"><i></i>关注</a>'+
						'<a class="p-o-btn addcart{#disabledaddcart#}" data-stock="{#sku#}" data-sv="1" data-disable-notice="0" data-presale="0" {#disabledaddcarturl#}="//gate.jd.com/InitCart.aspx?pid={#sku#}&pcount=1&ptype=1" target="_blank"><i></i>加入购物车</a></div>';
				}
				tpl_item += '</div></li>';
				var ware_count = json.data.length;
				for(var i=0; i<ware_count; i++) {
					var data = json.data[i]; data.index = data.sku%5; data.pos = i;
					data.shop = WareType.pop(data.sku) ? ' 第三方商家' : ' 京东自营';
					data.disabledaddcart = data.c3 && data.c3==15048 ? ' disabled' : ''
						data.disabledaddcarturl = data.c3 && data.c3==15048 ? 'hrefd' : 'href'
					if (tpl=='2') {
						data.img_url = imgUrl(data.sku, data.img, 'cms/s200x200_');
					} else {
						data.img_url = imgUrl(data.sku, data.img, plist.hasClass('gl-type-2') ? 'n8/' : 'n7/');
					}
					diviner_html += template(tpl_item, data);
				}
				plist.append(diviner_html+'</ul>');

				window.searchUnit && window.searchUnit.setImgLazyload && window.searchUnit.setImgLazyload('ul.J_diviner');
				window.seajs.use('product/search/'+_this.ui_ver+'/js/goodsList',function(goodsList){goodsList.init()});
				_this.get_ware_info();


				//推荐点击埋点
				plist.find('ul.J_diviner>li').click(function(e){
					var _this = $(this), node = e.target.nodeName;
					if ( (node=='A' || node=='IMG') && !$(e.target).parents('.p-scroll').length || node=='FONT' || node=='EM' || node=='I') {
						//推荐点击埋点
						new_image(_this.attr('data-clk'));
						//搜索点击埋点
						JA.tracker.ngloader('search.000003', {
							logid: LogParm.log_id,
							logtype: 1,
							key: QUERY_KEYWORD,
							reco_type: 'tj',
							result_count: ware_count,
							pos: _this.attr('data-pos')
						});
					}
				});
				//推荐展示埋点
				new_image(json.impr);
				//搜索展示埋点
				ngloader('search.000003', {
					logid: LogParm.log_id,
					logtype: 0,
					key: QUERY_KEYWORD,
					reco_type: 'tj',
					result_count: ware_count,
					pos: 0
				});
			}
		});
	};

	SEARCH.load = function(url, scroll) {
		if ( this.loading ) {
			return false;
		} else {
			this.loading = true;
		}
		var _this = this, click = getQueryString('click', url), ref_type = scroll ? 1 : click==_this.click ? 2 : 3;
		if ( $('#J_viewType').attr('data-ref')!='1' ) {//保留网格，列表状态
			url = filtUrl(url, 'vt', _this.view_type)
		}
		$.ajax({
			url : filtUrl(url, 'cs', ref_type==3 ? 'y' : '').replace(/[\s&]*$/g,''),
			timeout : 15000,
			error : function() {//一般情况下，属于网络超时
				_this.load_error = true; if ( ref_type==1 ) {
					$('#J_scroll_loading').addClass('notice-loading-error').find('span').html('加载失败，请<a href="javascript:void(0)" onclick="SEARCH.load(\''+url+'\',true)"><font color="blue">重试</font></a>');
				} else {
					$('#J_loading').find('span').css('background', 'none').html('加载失败，请<a href="javascript:void(0)" onclick="$(\'#J_loading\').remove();SEARCH.load(\''+url+'\')"><font color="blue">重试</font></a>');
				}
			},
			beforeSend : function() {
				if ( ref_type==1 ) {
					$('#J_scroll_loading').removeClass('notice-loading-error').find('span').html('正在加载中，请稍后~~');
				} else {
					$('#J_filter').after('<div id="J_loading" class="notice-filter-loading"><div class="nf-l-wrap"><span>正在加载中，请稍后~</span></div></div>');
					//锚点定位
					ref_type==3 && anchor_loc();
				}
			},
			success : function(res) {
				if ( ref_type==1 ) {
					$('#J_scroll_loading').remove();
					var container = _this.is_shop ? $('#J_shopList') : $("#J_goodsList").find('ul.gl-warp');
					container.append(res);
				} else if ( ref_type==2 ) {
					$('#J_filter').nextAll().remove(), $('#J_filter').after(res);
				} else {
					//广告模块
					var tmp_adv_html = $('#J_main').find('.m-aside').find('.J_adv_tuiguang_exposal').remove().end().html();
					//覆盖原内容
					$("#J_searchWrap").html(res).find('.m-aside').html(tmp_adv_html);
					//锚点定位
					anchor_loc();
					//属性区
					window.seajs.use(['product/search/'+_this.ui_ver+'/js/selector'], function(selector){selector.init()});
					//重新绑定事件
					_this.bind_events.init();
				}
				
				_this.success_js(ref_type);
			},
			complete : function() {
				_this.loading = false;
			}
		});
	};

	SEARCH.success_js = function(ref_type) {
		delete this.load_error;
		//获取商品促销，评论数信息
		this.get_ware_info();
		//图片懒加载
		SEARCH.replaceImgSuffix();
		//商品区事件绑定
		window.setImgTabInitFlag = false;
		window.seajs.use('product/search/'+this.ui_ver+'/js/goodsList',function(goodsList){goodsList.init()});
		//换购商品选中,调用UED函数
		if ( this.is_exchange_list && window.searchUnit && window.searchUnit.setGoodsChecked ) { window.searchUnit.setGoodsChecked(); }
		//左侧广告位加载
		if ( ref_type!=1 ) { window.searchUnit.initAside(); }
		//展示埋点
		searchlog(0,0);
	};

	SEARCH.page_html = function(i, total_page, result_count, scroll, next_start, prev_start, advware_count, promotion_count) {
		//current page
		this.current_page = i;
		//next search start
		this.next_start = next_start;
		//prev search start
		this.prev_start = prev_start;
		//shop count
		this.advware_count = advware_count;
		//promotion count
		this.promotion_count = promotion_count;
		//init
		if ( scroll ) {
			this.enable_twice_loading = 1; i = Math.ceil(i/2); total_page = Math.ceil(total_page/2);
			var prev_page = 2*i-3, next_page = 2*i+1;
		} else {
			this.enable_twice_loading = 0;
			var prev_page = i-1, next_page = i+1;
		}
		//top page html
		var html = '<span class="fp-text"><b>'+i+'</b><em>/</em><i>'+total_page+'</i></span>';
		if ( i<=1 ) {
			html += '<a class="fp-prev disabled" href="javascript:;">&lt;</a>';
		} else {
			html += '<a class="fp-prev" onclick="SEARCH.page('+prev_page+')" href="javascript:;" title="使用方向键左键也可翻到上一页哦！">&lt;</a>';
		}
		if ( i>= total_page ) {
			html += '<a class="fp-next disabled" href="javascript:;">&gt;</a>';
		} else {
			html += '<a class="fp-next" onclick="SEARCH.page('+next_page+')" href="javascript:;" title="使用方向键右键也可翻到下一页哦！">&gt;</a>';
		}
		$('#J_topPage').html(html); $('#J_resCount').html(result_count);
		//bottom page html
		if ( total_page<=1 ) { return ''; }
		var start = i-2, end = Math.min(total_page, i+2), html='<span class="p-num">';
		if ( end < 7 ) { end = Math.min(7, total_page); } else { start = end-4; }
		if ( i <= 1 ) {
			html += '<a class="pn-prev disabled"><i>&lt;</i><em>上一页</em></a>';
		} else {
			html += '<a class="pn-prev" onclick="SEARCH.page('+prev_page+', true)" href="javascript:;" title="使用方向键左键也可翻到上一页哦！"><i>&lt;</i><em>上一页</em></a>';
		}
		for ( var c=1; c<=total_page; c++ ) {
			if ( c<=2 || c>=start && c<=end ) {
				html += c==i ? '<a href="javascript:;" class="curr">'+c+'</a>' : '<a onclick="SEARCH.page('+(scroll ? 2*c-1 : c)+', true)" href="javascript:;">'+c+'</a>';
			} else if ( c<start ) {
				html += '<b class="pn-break">...</b>'; c = start-1;
			} else if ( c>end ) {
				html += '<b class="pn-break">...</b>'; break;
			}
		}
		if ( i>=total_page ) {
			html += '<a class="pn-next disabled"><em>下一页</em><i>&gt;</i></a>';
		} else {
			html += '<a class="pn-next" onclick="SEARCH.page('+next_page+', true)" href="javascript:;" title="使用方向键右键也可翻到下一页哦！"><em>下一页</em><i>&gt;</i></a>';
		}
		html += '</span><span class="p-skip"><em>共<b>'+total_page+'</b>页&nbsp;&nbsp;到第</em><input class="input-txt" type="text" value="'+i+'" onkeydown="javascript:if(event.keyCode==13){SEARCH.page_jump('+total_page+','+scroll+');return false;}"><em>页</em><a class="btn btn-default" onclick="SEARCH.page_jump('+total_page+','+scroll+')" href="javascript:;">确定</a></span>';
		$('#J_bottomPage').html(html);
	};

	SEARCH.page = function(i, bottom) {
		i = parseInt(i, 10);
		if ( i<1 ) { i=1; }
		//锚点定位
		bottom && anchor_loc();
		//pagesize
		var start, min = Math.min, multiple = this.enable_twice_loading ? 1 : 2, pagesize = 30*multiple, advware_pagesize = 4*multiple, promotion_pagesize = 2*multiple;
		//start
		if ( i == 1 ) {
			start = 1;
		} else if ( i < this.current_page ) {
			start = this.prev_start - (this.current_page - i) * pagesize + min(this.advware_count, (this.current_page-1)*advware_pagesize) - min(this.advware_count, (i-1)*advware_pagesize) + min(this.promotion_count, (this.current_page-1)*promotion_pagesize) - min(this.promotion_count, (i-1)*promotion_pagesize);
		} else if ( i == this.current_page ) {
			start = this.prev_start;
		} else {
			start = this.next_start + (i - this.current_page - 1) * pagesize - min(this.advware_count, (i-1)*advware_pagesize) + min(this.advware_count, this.current_page*advware_pagesize) - min(this.promotion_count, (i-1)*promotion_pagesize) + min(this.promotion_count, this.current_page*promotion_pagesize);
		}
		//
		handle_request(this.base_url+'&page='+i+'&s='+start+'&click='+this.click);
		//
		searchlog(1, i, 0, 56);
	};

	SEARCH.sort_html = function(s) {
		s = s || ''; if ( s=='0' ) { s=''; }
		var html = '', tpl = '<a href="javascript:;" class="#class#" onclick="#click#">#name#</a>',
			click=class_name='';
		if ( s=='' ) { class_name='curr'; } else { click="SEARCH.sort('')"; }
		html += tpl.replace('#class#',class_name).replace('#click#',click).replace('#name#','综合<i></i>');

		class_name=click=''; if ( s=='3' ) { class_name='curr'; } else { click="SEARCH.sort('3')"; }
		html += tpl.replace('#class#',class_name).replace('#click#',click).replace('#name#','销量<i></i>');

		class_name=click=''; if ( s=='4' || s=='11') { class_name='curr'; } else { click="SEARCH.sort('"+(this.comment_6m ? 11 : 4)+"')"; }
		html += tpl.replace('#class#',class_name).replace('#click#',click).replace('#name#','评论数<i></i>');

		if ( $('ul.gl-warp').attr('data-tpl')=='2' ) {
			class_name=click=''; if ( s=='6' ) { class_name='curr'; } else { click="SEARCH.sort('6')"; }
			html += tpl.replace('#class#',class_name).replace('#click#',click).replace('#name#','出版时间<i></i>');
		} else {
			class_name=click=''; if ( s=='5' ) { class_name='curr'; } else { click="SEARCH.sort('5')"; }
			html += tpl.replace('#class#',class_name).replace('#click#',click).replace('#name#','新品<i></i>');
		}

		if ( this.is_promotion ) {
			class_name=click=''; if ( s=='9' ) { class_name='curr'; } else { click="SEARCH.sort('9')"; }
			html += tpl.replace('#class#',class_name).replace('#click#',click).replace('#name#','降价幅度<i></i>');
			class_name=click=''; if ( s=='10' ) { class_name='curr'; } else { click="SEARCH.sort('10')"; }
			html += tpl.replace('#class#',class_name).replace('#click#',click).replace('#name#','降价金额<i></i>');
		}

		if ( s=='2' ) { class_name='curr'; click="SEARCH.sort('1')"; } else if ( s=='1') { class_name='curr'; click="SEARCH.sort('2')"; } else { class_name = ''; click="SEARCH.sort('2')"; }
		html += tpl.replace('#class#',class_name).replace('#click#',click).replace('#name#','<span class="fs-tit">价格</span><em class="fs-'+(s=='1'?'down':'up')+'"><i class="arrow-top"></i><i class="arrow-bottom"></i></em>');

		html = html.replace(/>(.+?)<i><\/i><\/a>/g, '><span class="fs-tit">'+"$1"+'</span><em class="fs-down"><i class="arrow"></i></em></a>');

		$('#J_filter').find('div.f-sort').html(html);
	};

	SEARCH.sort = function(s) {
		s = s || ''; if ( s=='0' ) { s=''; }
		//
		handle_request(filtUrl(this.base_url, 'psort', s)+'&click='+this.click);
		//
		searchlog(1,s,0,55);
		//属性筛选时保留排序状态
		bind_attr_href('#J_selector', 'psort', s);
	};
	SEARCH.exchange_filter = function(cid,pos) {
		var e = window.event || arguments.callee.caller.arguments[0], _this = $(e.target ? e.target : e.srcElement).parent();
		//已被选中的不再请求
		if ( _this.hasClass('selected') ) { return false; }
		//
		handle_request(filtUrl(this.base_url, cid?'cid3':['cid2','cid3'], cid?""+cid:''));
		//样式
		_this.addClass('selected').siblings().removeClass('selected');
		//日志
		searchlog(1,cid,pos,51);
	};
	SEARCH.page_jump = function(total_page, scroll) {
		var page = parseInt($('#J_bottomPage').find('input').val(), 10);
		if ( isNaN(page) ) { page = 1; }
		if ( page>total_page ) { page = total_page; }
		this.page(scroll ? 2*page-1 : page, true);
	};

	SEARCH.scroll = function() {
		var page = this.current_page + 1, container_obj = $('#J_goodsList'), show_items = [], tpl, attr_name,
			url = 's_new.php?'+rewrite_fun(this.base_url)+'&page='+page+'&s='+this.next_start+'&scrolling=y&log_id='+LogParm.log_id+'&tpl=';
		//记录当前页使用模板，防止二次加载模板变动
		if ( this.is_shop ) {
			tpl = '3_M';
		} else {
			tpl = container_obj.find('ul.gl-warp').attr('data-tpl') + (container_obj.hasClass('gl-type-2') ? '_L' : '_M');
		}
		url += tpl;
		//记录当前页已展示商品，用于商品广告去重
		if ( page%2==0 ) {
			//主从合并模板用warepid去重
			attr_name = tpl.indexOf('3_')==0 ? 'data-pid' : 'data-sku';
			//
			container_obj.find('ul.gl-warp > li['+attr_name+']').each(function(){
				show_items.push(this.getAttribute(attr_name));
			})
			url += '&show_items=' + show_items.join(',');
		}
		//request
		this.load(url, true);
		//log
		searchlog(1, page, 0, 56);
	};

	SEARCH.bind_events = {
		//配送区
		iplocation: function(s) {
			seajs.use(['jdf/1.0.0/ui/switchable/1.0.0/switchable', 'jdf/1.0.0/ui/area/1.0.0/area'], function(){
				$('#J_store_selector').area({
					scopeLevel: 4,
					hasCommonAreas: 0,
					hasOversea: 1,
					repLevel: 0,
					hasCssLink: 0,
					onChange: function(curArea, local){
						window.location.href = window.location.pathname + '?' + filtUrl(s.base_url, ['stock','dt']) + '&' + Math.random() + '#J_main';
						return false;
					}
				})
			})
		},
		//已选分类填充
		async_category: function(s) {
			//基础url
			var base_url = rewrite_fun(s.base_url);
			//
			if ( s.c_category ) {
				$.get('category.php?'+ filtUrl(base_url, ['ev','psort']) + (s.p_category ? '&c=all' : ''), function(res){
					$('#J_crumbsBar').find('ul[data-level="c"]').append(res).find('li').length>30 && $('#J_crumbsBar').find('ul[data-level="c"]').parent().addClass('menu-drop-xl');
				})
			}
			if ( s.p_category ) {
				$.get('category.php?'+ filtUrl(base_url, ['ev','psort','cid1','cid2','cid3']) + '&cid2=' + s.p_category, function(res){
					$('#J_crumbsBar').find('ul[data-level="p"]').append(res);
				})
			}
		},
		//价格区间
		price_select: function(s) {
			var select = $("#J_selectorPrice"), txt = select.find("input"), default_min = txt.eq(0).val(), default_max = txt.eq(1).val(), default_color = '#ccc', select_color = '#333', default_val = '¥';
			if ( !txt.length ) { return ; }
			txt.keypress(function(event) {
				var keycode = event.keyCode||event.charCode;
				if ( keycode && (keycode<48 || keycode>57) && keycode!=46 && keycode!=8 && keycode!=37 && keycode!=39 ) {
					event.preventDefault();
				}
			}).focus(function(){
				if ( $(this).val()==default_val ) { $(this).val('').css('color', select_color); }
			}).blur(function(e) {
				var input = $(this), v = $.trim(input.val()), reg = new RegExp("^[0-9]+(.[0-9]{2})?$", "g");
				if (!reg.test(v)) { input.val(default_val).css('color', default_color); }
				e.stopPropagation();
			});
			//确定按钮
			select.find(".J-price-confirm").click(function(event, mark) {
				var min = parseInt(txt.eq(0).val(), 10), max = parseInt(txt.eq(1).val(), 10), url = $(this).attr('data-url');
				if ( mark=='cancle' ) {
					url = url.replace('exprice_%7Bmin%7D-%7Bmax%7D%40', '').replace('exprice_%7Bmin%7D-%7Bmax%7D%5E', '');
				} else if ( !isNaN(min) && !isNaN(max) ) {
					if ( min > max ) { var tmp = min; min=max; max=tmp; }
					searchlog(1,0,0,22,'价格::'+min+'-'+max);
					url = url.replace('%7Bmin%7D', min).replace('%7Bmax%7D', max);
				} else if ( !isNaN(min) ) {
					searchlog(1,0,0,22,'价格::'+min+'gt');
					url = url.replace('%7Bmin%7D', min).replace('-%7Bmax%7D', 'gt');
				} else if ( !isNaN(max) ) {
					searchlog(1,0,0,22,'价格::0-'+max);
					url = url.replace('%7Bmin%7D', 0).replace('%7Bmax%7D', max);
				} else {
					return false;
				}
				window.location.href = url;
				return false;
			});
			//清空按钮
			select.find('.J-price-cancle').click(function(){
				txt.val(default_val).css('color', default_color);
			});
			//柱状图点击
			$('#J_filter').find('.fdg-item').mouseenter(function(){
				var res = $(this).attr('data-range').match(/(\d+)-(\d*)/), min = res[1], max = res[2];
				txt.eq(0).val(min); txt.eq(1).val(max);
				txt.css('color', select_color);
			}).mouseleave(function(){
				//恢复默认价格
				txt.eq(0).val(default_min); txt.eq(1).val(default_max);
				//恢复默认样式
				default_min==default_val && txt.eq(0).css('color', default_color);
				default_max==default_val && txt.eq(1).css('color', default_color);
			}).click(function(){
				//触发筛选
				select.find(".J-price-confirm").trigger('click', [$(this).hasClass('fdg-item-curr')?'cancle':'']);
			});
			//输入框hover
			select.filter('.f-price').mouseenter(function(){
				$(this).addClass('f-price-focus');
			}).mouseleave(function(){
				$(this).removeClass('f-price-focus');
			});
		},
		//条件过滤
		condition_filter: function(s) {
			$("#J_feature,#J_location").find('a').click(function(e){
				var field = $(this).attr('data-field');
				if ( !field ) { return false; }
				if ( $(this).hasClass('selected') ) {
					$(this).removeClass('selected');
					handle_request(filtUrl(s.base_url, field)+'&click='+(s.click+1));
				} else {
					$(this).addClass('selected');
					handle_request(filtUrl(s.base_url, field, $(this).attr('data-val'))+'&click='+(s.click+1));
				}
				return false;
			});
		},
		//网格、列表、店铺切换
		view_type: function(s) {
			//init
			s.view_type = getQueryString('vt', s.base_url);
			//
			$("#J_viewType").find('a').click(function(){
				if ( $(this).hasClass('selected') ) { return false; }
				$(this).addClass('selected').siblings().removeClass('selected');
				var vt = s.view_type = $(this).attr('data-value');
				if ( $(this).parent().attr('data-ref')=='1' ) {
					//店铺、商品切换需要局刷
					handle_request(filtUrl(s.base_url, 'vt', vt)+'&click='+(s.click+1));
				} else {
					//网格、列表切换更换class
					if ( vt=='1' ) {
						$('#J_goodsList').attr('class', $('#J_goodsList').attr('class').replace('gl-type-4', 'gl-type-5')).find('li[data-type="activity"]').hide();
					} else {
						$('#J_goodsList').attr('class', $('#J_goodsList').attr('class').replace('gl-type-5', 'gl-type-4')).find('li[data-type="activity"]').show();
					}
				}
				return false;
			});
		},
		//在结果中搜索
		research: function(s) {
			var TEXT = '在结果中搜索', exp_key = getQueryString('exp_key', s.base_url), reSearchSubmit = function(textObj){
				var text = $.trim(textObj.val());
				if ( (text!='' || exp_key) && text!=TEXT ) {
					searchlog(1,0,0,27);
					window.location.href = '?'+filtUrl(s.base_url, 'exp_key', encodeURIComponent(text))+'#J_crumbsBar';
				}
			};
			$("#J_filter").find('.f-search a').click(function(e){
				reSearchSubmit($(this).prev());
			}).prev().focus(function(){
				if ( $.trim($(this).val())==TEXT ) { $(this).val(''); }
			}).blur(function(){
				if ( $.trim($(this).val())=='' && exp_key=='' ) { $(this).val(TEXT); }
			}).keydown(function(e){
				if(e.keyCode==13) { reSearchSubmit($(this)); }
			});
		},
		//init
		init: function() {
			//
			var s = window.SEARCH;
			//遍历
			for (var i in this ) { i!='init' && this[i](s); }
		}
	};
	SEARCH.replaceImgSuffix  = function(){
		var obj = $('#J_goodsList').find('li').find('.p-img');
		obj.each(function(){
			//获取数据
			var img = $(this).find('img').eq(0).attr('source-data-lazy-img');
			if(img){
				var jpg = img.indexOf('.jpg');
				var jpeg = img.indexOf('.jpeg');
				if(!pageConfig.closeJpg && (jpeg > 0 || jpg > 0)){
					$(this).find('img').eq(0).attr('data-lazy-img', img + '.dpg');
				}else{
					$(this).find('img').eq(0).attr('data-lazy-img', img);
				}
			}
			$(this).find('img').eq(0).attr('source-data-lazy-img', '');
		});
		setTimeout(function(){
			if(window.searchUnit && window.searchUnit.setImgLazyload){
				window.searchUnit.setImgLazyload('#J_main');
			}
			$('#J_main').lazyload({source: "source-data-lazy-advertisement",isUseLeft :true,onLoadImg : function(){	
				$(this).hide()	
				
			}});
		}, 10);
		
	};
	SEARCH.init = function(page, total_page, result_count, sort, scroll, recommend, next_start, prev_start, advware_count, promotion_count) {
		recommend && this.get_diviner_ware(),
		this.get_ware_info(),
		this.page_html(page, total_page, result_count, scroll, next_start, prev_start, advware_count, promotion_count),
		this.sort_html(sort),
		this.bind_events.init(),
		this.sync_iframe_height(),
		this.replaceImgSuffix(),
		//box tab
		switchBoxTab();
	};
	//局部刷新，监听hash值的改变
	(function(w, s){
		//H5
		if ( enable_history_state ) {
			w.onpopstate = function(e) {
				//查询字符串
				var query_string = rewrite_fun(w.location.search.substr(1));
				//
				query_string && s.load('s_new.php?'+query_string);
			}
		//hash
		} else if ( typeof(s.is_correct_hash)=='function' ) {
			$(w).hashchange(function(){
				var _hash = s.get_real_hash();
				if ( !_hash || _hash=='J_searchWrap' ) {
					_hash = w.location.search.substr(1);
				}
				//
				_hash = rewrite_fun(_hash);
				//
				s.is_correct_hash(_hash) && s.load('s_new.php?'+_hash);
			});
		}
	})(window, SEARCH) ;
	//下拉刷新
	var timeout_id = null; $(window).scroll(function(){
		clearTimeout(timeout_id);
		timeout_id = setTimeout(function(){
			var loading_obj = $("#J_scroll_loading");
			if ( SEARCH.loading || SEARCH.load_error || !SEARCH.enable_twice_loading || !loading_obj.length || loading_obj.offset().top-600>$(window).height()+$(window).scrollTop() ) {
				return false;
			}
			SEARCH.scroll();
		}, 20);
	}).load(function(){
		//耗时统计
		var timing = JSTiming.getTimes(); timing['oReady'] = reday_time; timing['oLoad'] = new Date().getTime()-jdpts._st;
		//上报
		ngloader('search.000010', timing);
	});
	//快捷键翻页
	var reday_time; $(document).keyup(function(e){
		var tag_name=document.activeElement.tagName.toLowerCase();
		if(tag_name=="input"||tag_name=="textarea") { return; }
		var currKey=0, e=e||event, obj=$("#J_filter"), top=0; currKey=e.keyCode||e.which||e.charCode;
		if ( obj.length ) { top = obj.offset().top; }
		switch(currKey) {
			case 37 : $('#J_bottomPage a.pn-prev').trigger('click');break;
			case 39 : $('#J_bottomPage a.pn-next').trigger('click');break;
			default : break;
		}
	}).ready(function(){
		//加载时间
		reday_time = new Date().getTime()-jdpts._st;
		//相关搜索
		SEARCH.relate_search.init();
		//展示埋点
		searchlog(0, track_iframe_onebox()); log(2, 1, QUERY_KEYWORD);
	});
	//输出
	module.exports = SEARCH;
})
