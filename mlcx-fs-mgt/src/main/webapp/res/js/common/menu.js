/**
 * 
 */
(function(){
	/**
	 * 全局config
	 */
	window.xzConfig={
			path:{
				app:$("#appPath").val()?$.trim($("#appPath").val()):"",
				img:$("#imgPath").val()?$.trim($("#imgPath").val()):"",
				res:$("#resPath").val()?$.trim($("#resPath").val()):"",
				resImg:$("#resImgPath").val()?$.trim($("#resImgPath").val()):"",
				before:$("#beforePath").val()?$.trim($("#beforePath").val()):""		
			}		
	};
	/**
	 * ajax请求是否会话超时(ajax跳到登陆页)
	 */
	var sid = $("#skey").val();
	$.ajaxSetup({
		 /*complete:function(XMLHttpRequest,status){
			 var session_is_timeout = XMLHttpRequest.getResponseHeader('session_is_timeout');
			 //var res = XMLHttpRequest.responseText; 
			 if(session_is_timeout){ 
				 parent.location.href = "toLogin.do";
			 } 
		 }*/
		 data:{"ssid":sid},
		 complete:function(XMLHttpRequest,status){
			 updateForms(sid);
			 //updateTags(sid);
			 var session_is_timeout = XMLHttpRequest.getResponseHeader('session_is_timeout');
			 //var res = XMLHttpRequest.responseText; 
			 if(session_is_timeout){ 
				 parent.location.href = "toLogin.do";
			 } 
		 }
		
	});
	/**
	 * 设置tab href 加载时为异步
	 */
	$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
	    options.async = true;
	});
	/**
	 * bootbox 汉化
	 */
	bootbox.setLocale("zh_CN");  
	
	var appPath=getPath("app");
	var menu={"menu":[
//	                       {"id":"id3","parentId":"0","name":"系统管理","link":"#","sort":"3",
//		                	   	"child":[{"id":"id1","parentId":"0","name":"系统参数管理","link":"http://localhost:8080/b2b-mgt/sysParam/mainPage.do","sort":"1"},
//		            	                 {"id":"id1","parentId":"0","name":"系统用户管理","link":"http://localhost:8080/b2b-mgt/sysUser/mainPage.do","sort":"2"},
//		            	                 {"id":"id1","parentId":"0","name":"系统角色管理","link":"http://localhost:8080/b2b-mgt/sysRole/mainPage.do","sort":"3"},
//		            	                 {"id":"id1","parentId":"0","name":"系统权限管理","link":"http://localhost:8080/b2b-mgt/sysPermission/mainPage.do","sort":"4"},
//		            	                 {"id":"id1","parentId":"0","name":"系统日志管理","link":"http://localhost:8080/b2b-mgt/sysOpLog/mainPage.do","sort":"5"},
//		            	                 {"id":"id1","parentId":"0","name":"系统地区管理","link":"http://localhost:8080/b2b-mgt/sysRegion/mainPage.do","sort":"6"},
//		            	                 {"id":"id1","parentId":"0","name":"黑词管理","link":"http://localhost:8080/b2b-mgt/dirtyWord/dirtyWord.do","sort":"7"},
//		            	                 {"id":"id1","parentId":"0","name":"操作日志管理","link":"http://localhost:8080/b2b-mgt/sysOpLogUser/mainOpLogUser.do","sort":"8"},
//		            	                 {"id":"id1","parentId":"0","name":"短信模板管理","link":"http://localhost:8080/b2b-mgt/sendMessage/sendMessage.do","sort":"9"},
//		            	                 {"id":"id1","parentId":"0","name":"邮件模板管理","link":"http://localhost:8080/b2b-mgt/messageTemplate/messageTemplate.do","sort":"10"}]
//		                   },
//						   {"id":"id3","parentId":"0","name":"客服管理","link":"#","sort":"4",
//		            	        "child":[{"id":"id1","parentId":"0","name":"商城投诉列表","link":"http://localhost:8080/b2b-mgt/complaint/complaint.do","sort":"1"},
//		            	                 {"id":"id1","parentId":"0","name":"商城咨询列表","link":"http://localhost:8080/b2b-mgt/consulting/consulting.do","sort":"2"},
//		            	                 {"id":"id1","parentId":"0","name":"商城建议列表","link":"http://localhost:8080/b2b-mgt/suggest/suggest.do","sort":"3"},
//		            	                 {"id":"id1","parentId":"0","name":"商城邮件模板列表","link":"http://localhost:8080/b2b-mgt/mailTemplate/mailTemplate.do","sort":"4"},
//		            	                 {"id":"id1","parentId":"0","name":"商城发送邮件列表","link":"http://localhost:8080/b2b-mgt/sendMail/sendMail.do","sort":"5"},
//		            	                 {"id":"id1","parentId":"0","name":"商城短信模板列表","link":"http://localhost:8080/b2b-mgt/smsTemplate/smsTemplate.do","sort":"6"},
//		            	                 {"id":"id1","parentId":"0","name":"商城发送短信列表","link":"http://localhost:8080/b2b-mgt/sendsms/sendsms.do","sort":"7"},
//		            	                 {"id":"id1","parentId":"0","name":"商城消息模板列表","link":"http://localhost:8080/b2b-mgt/messageTemplate/messageTemplate.do","sort":"8"},
//		            	                 {"id":"id1","parentId":"0","name":"商城发送消息列表","link":"http://localhost:8080/b2b-mgt/sendMessage/sendMessage.do","sort":"9"}]
//
//		                   },
//		                   {"id":"id3","parentId":"0","name":"定价求购管理","link":"#","sort":"4",
//		            	        "child":[{"id":"id1","parentId":"0","name":"定价求购列表","link":"http://localhost:8080/b2b-mgt/fixedPriceInquiry/fixedPriceInquiry.do","sort":"1"}]
//		                   },
//		                   {"id":"id3","parentId":"0","name":"竞价求购管理","link":"#","sort":"4",
//		            	        "child":[{"id":"id1","parentId":"0","name":"竞价求购列表","link":"http://localhost:8080/b2b-mgt/inquiry/inquiry.do","sort":"1"}]
//		                   },
//		                   {"id":"id3","parentId":"0","name":"风控管理","link":"#","sort":"4",
//		            	        "child":[{"id":"id1","parentId":"0","name":"客户额度、账期设定","link":"http://localhost:8080/b2b-mgt/riskcontrol/getUserQuotaPager.do","sort":"1"},
//		            	                 {"id":"id1","parentId":"0","name":"超额管控","link":"http://localhost:8080/b2b-mgt/memberBuyerInfo/memberBuyerInfo.do","sort":"2"},
//		            	                 {"id":"id1","parentId":"0","name":"逾期监管","link":"http://localhost:8080/b2b-mgt/blacklistLog/blacklistLog.do","sort":"3"}]
//		                   },
//		                   
//		                   {"id":"id3","parentId":"0","name":"客户管理","link":"#","sort":"4",
//
//		            	        "child":[{"id":"id1","parentId":"0","name":"客户列表","link":"http://localhost:8080/b2b-mgt/memberCompanyInfo/memberCompanyInfo.do","sort":"1"},
//		            	                 {"id":"id1","parentId":"0","name":"客户额度授信列表","link":"http://localhost:8080/b2b-mgt/memberBuyerInfo/memberBuyerInfo.do","sort":"2"},
//		            	                 {"id":"id1","parentId":"0","name":"黑名单列表","link":"http://localhost:8080/b2b-mgt/blacklistLog/blacklistLog.do","sort":"3"},
//		            	                 {"id":"id1","parentId":"0","name":"客户操作员列表","link":"http://localhost:8080/b2b-mgt/memberUser/memberUser.do","sort":"4"},
//		            	                 {"id":"id1","parentId":"0","name":"求购列表,求购信息","link":"http://localhost:8080/b2b-mgt/inquiry/inquiry.do","sort":"5"}]
//		                   } ,
//		                   {"id":"id3","parentId":"0","name":"仓库管理","link":"#","sort":"4",
//		            	        "child":[{"id":"id1","parentId":"0","name":"收货管理","link":"http://localhost:8080/b2b-mgt/wareStockIn/wareStockInPage.do","sort":"1"}]
//		                   },
//		                   {"id":"id3","parentId":"0","name":"拍卖管理","link":"#","sort":"4",
//
//		            	        "child":[{"id":"id1","parentId":"0","name":"拍卖列表","link":"http://localhost:8080/b2b-mgt/auction/auction.do","sort":"1"}
//		            	                 ]
//		                   },
//		                   {"id":"id3","parentId":"0","name":"采购单管理","link":"#","sort":"4",
//
//		            	        "child":[{"id":"id1","parentId":"0","name":"采购单列表","link":"http://localhost:8080/b2b-mgt/purchaseOrder/purchaseOrder.do","sort":"1"}]
//		                   }
	                      ]};
	//左菜单
	$.ajax({
		url : getPath("app")+'/leftPage.do',
		type : 'post',
		data: {"appPath":getPath("app")+"/"},
		dataType : 'json',
		//async : false,
		success : function(result) {
			menu.menu = result;
			var template = Handlebars.compile($("#menu-template").html());
			$(".sidebar-menu").html(template(menu));
			
			
			
			$(".sidebar").scrollTop(10);//控制滚动条下移10px
			  if($(".sidebar").scrollTop()>0){
				$(".sidebar").css('width','217px')
			  }else{
				$(".sidebar").css('width','auto')
				}
			  $(".sidebar").scrollTop(0);//滚动条返回顶部
			
			handleClickSecond();
		},
		error : function(msg) {
		}
	});
	
	function handleClickSecond(){
		$(".treeview-menu>li").each(function(){
			$(this).click(function(){
				$(this).addClass("active").siblings().removeClass("active");
				$(this).parents(".treeview").siblings().find(".treeview-menu>li").removeClass("active");
				var url=$(this).data("url");
				var urls=url.split("/");
				var model=urls[urls.length-2];
				$("#appModel").val("model");
				window.xzConfig.appModel=model;
				addTab($(this).attr("title"),$(this).data("url"));
			});		
		});
	}
	
	
//	var template = Handlebars.compile($("#menu-template").html());
//	$(".treeview-menu").html(template(menu));
//	$(".treeview-menu>li>a").each(function(){
//		$(this).click(function(){
//			var url=$(this).data("url");
//			var urls=url.split("/");
//			var model=urls[urls.length-2];
//			$("#appModel").val("model");
//			window.xzConfig.appModel=model;
//			addTab($(this).attr("title"),$(this).data("url"));
//		});		
//	});
	$(".indexmenu-search-btn").on("click",function(){
		$(".treeview").removeClass("active");
		$(".treeview-menu").removeClass("menu-open");
		$(".treeview-menu").hide();
		//$(".search-memu").removeClass("search-memu");
		var v=$.trim($(".indexmenu-search-input").val());
		var reg =new RegExp(v,"gim"); 
		if(!v || v=="管理"){
			return;
		}
		$(".sidebar-menu .treeview-menu a").each(function(){
			if(reg.test($(this).text())){
				$(this).closest(".treeview").addClass("active");
				$(this).closest(".treeview-menu").addClass("menu-open");
				$(this).closest(".treeview-menu").show();
				//$(this).addClass("search-memu");
			}
		});
	});
	
	setInterval(function(){
		var nowTime = new Date();
		var hour = nowTime.getHours();
		var minutes = nowTime.getMinutes();
		var seconds = nowTime.getSeconds();
		var renderTime = (hour>9?hour:('0'+hour))+":"+(minutes>9?minutes:('0'+minutes))+":"+(seconds>9?seconds:('0'+seconds));
		$(".user-year-month-day").text(moment(nowTime).format("YYYY-MM-DD"));
		$(".time").text(renderTime);
	},1000);

	$(window).load(function() {  
		initTab();
    });
})(jQuery);
/*
 * 初始配置tab
 */
function initTab(){
	var height = document.body.clientHeight-100;
	$('.tabs-panels').css('height',height+'px')
	$('#mainContentHeight').val(height);

	var tab = $("#mainConsoleTabs");
	tab.tabs({ 
		tools:[{
			iconCls:"icon-remove",
			handler:function(){
				closeAllTab();
			}}],
		fit:true/*,
		onSelect:function(title){
            var allTabs = $('#mainConsoleTabs').tabs('tabs');
            var selectTabs = $('#mainConsoleTabs').tabs('getSelected');
            for(var i=0;i<allTabs.length;i++) {
                if(allTabs[i].panel('options').title!=selectTabs.panel('options').title){
                    
                    var tab = $('#mainConsoleTabs').tabs('getSelected');  // get selected panel
                    $('#mainConsoleTabs').tabs('update', {
                        tab: allTabs[i],
                        options: {
                            //title: allTabs[i].panel('options').title,
                            content:""
                            //href: '#'  // the new content URL
                        }
                    });
                    
                    allTabs[i].tabs();
                    //alert(allTabs[i].panel('options').title);
                }
            }
            //alert(allTabs.length+"-"+selectTabs.length);getTabIndex
            //alert(title+' is selected');
            //#这里写你要怎么处理这个选中的tab
        }*/
	});
}
/*
 * 增加tab
 */
function addTab(title, href, icon,fn) {
	var tab = $("#mainConsoleTabs");
	if (tab.tabs("exists", title)) {//如果tab已经存在,则选中并刷新该tab
		tab.tabs("select", title);
		refreshTab({
			tabTitle : title,
			url : href
		});
		$.parser.onComplete=function(){
			var selTab=$('#mainConsoleTabs').tabs('getSelected');
			var collapsedBox=$(selTab).find(".box").find(".fa-angle-up");
			if(collapsedBox){
				$(collapsedBox).parents(".box").addClass("collapsed-box");
				//document.getElementsByClassName("btn-box-tool")[0].innerHTML="收起<img src='res/img/up.jpg' style='margin-top: -4px'/>"
				$(collapsedBox).removeClass("fa-angle-up");
				$(collapsedBox).addClass("fa-angle-down");
				//$(".fa-angle-down").hide();
				//$(".i1").hide();
				//$(".i2").show()
				//$(collapsedBox).addClass("i1");
				//$(collapsedBox).removeClass("i2");
				//$(".box-body").animate({
				//	height:"100px"
				//})
			}
			if(fn){
				fn();
			}
		};
	} else {
		/*if (href) {
			var content = '<iframe scrolling="no" frameborder="0"  src="'+ href + '" style="width:100%;height:100%;"></iframe>';
		} else {
			var content = '请耐心等待...';
		}*/	
		/*tab.tabs({"onAdd":function(title){
			if(fn){
				fn();	
			}
		}});*/
		$.parser.onComplete=function(){
			var selTab=$('#mainConsoleTabs').tabs('getSelected');
			var collapsedBox=$(selTab).find(".box").find(".fa-angle-up");
			if(collapsedBox){
				console.log(collapsedBox+11111)
				$(collapsedBox).parents(".box").addClass("collapsed-box");
				//document.getElementsByClassName("btn-box-tool")[0].innerHTML="展开<img src='res/img/down.jpg' style='margin-top: -4px'/>"
				$(collapsedBox).removeClass("fa-angle-up");
				$(collapsedBox).addClass("fa-angle-down");
				//$(".fa-angle-down").hide();
				//$(".i1").show();
				//$(".i2").hide()
			}
			if(fn){
				fn();
			}
		};
		tab.tabs("add", {
			title : title,
			closable : true,
			//content : content,
			href:href,
			iconCls : icon || "icon-default",
			pill : true,
			width: "100%", 
			height: "auto",
			fit:true
		});
	}
}
/*
 * 更新tab
 */
function updateTab(cfg){
	var currentTab = cfg.tabTitle ? $("#mainConsoleTabs").tabs("getTab", cfg.tabTitle): $("#mainConsoleTabs").tabs("getSelected");
	$("#mainConsoleTabs").tabs("update",{  
	     tab:currentTab,  
	     options : {  
	    	 title : cfg.tabTitle,
	    	 href : cfg.url
	     }  
	}); 
}
/*
 * 删除tab
 */
function closeTab(title){
	var tab = $("#mainConsoleTabs").tabs("getSelected");
	if (tab){
		var index = $("#mainConsoleTabs").tabs("getTabIndex", tab);
		$("#mainConsoleTabs").tabs("close", index);
	}	
}

/*
 * 关闭所有tab
 */
function closeAllTab(){
	 $("#mainConsoleTabs .tabs> li").each(function(index, obj) {  
         //获取所有可关闭的选项卡  
         var tab = $(".tabs-closable", this).text();  
         $("#mainConsoleTabs").tabs("close", tab);  
    });	
}

/*
 * 删除tab
 */
function closeTabBT(title){
		$("#mainConsoleTabs").tabs("close", title);
}

/*
 * 刷新tab @cfg example: {tabTitle:'tabTitle',url:'refreshUrl'}
 * 如果tabTitle为空，则默认刷新当前选中的tab 如果url为空，则默认以原来的url进行reload
 */  
function refreshTab(cfg) {
	var refreshTab = cfg.tabTitle ? $("#mainConsoleTabs").tabs("getTab", cfg.tabTitle): $("#mainConsoleTabs").tabs("getSelected");
	refreshTab.panel("refresh", cfg.url);
}

// 3个参数的名字可以随便命名,但必须是3个参数,少一个都不行
function retrieveData( sSource111,aoData111, fnCallback111) {
	$.ajax({
		url : sSource111,//这个就是请求地址对应sAjaxSource
		data : {"aoData":JSON.stringify(aoData111)},//这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
		type : 'post',
		dataType : 'json',
		//async : false,
		success : function(result) {
			fnCallback111(JSON.parse(result));//把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
		},
		error : function(msg) {
		}
	});
}

function getPath(type){
	return window.xzConfig.path[type];
}

function getAppModel(){
	return window.xzConfig.appModel;
}

//给表单增加一个隐藏域的token
function updateForms(token){
	var forms = document.getElementsByTagName('form');
	for(var i = 0; i< forms.length; i++){
		var url = forms[i].action;
//		if(url == null || url ==""){
//			continue;
//		}
		var flag = true;
		var inputs = forms[i].getElementsByTagName("input");
		//如果存在就重新赋值
		for(var j = 0; j< inputs.length; j++){
			if(inputs[j].name == "ssid"){
				inputs[j].value = token;
				flag = false;
				break;
			}
		}
		//否则重新定义一个input
		if(forms[i] && flag){
			var e = document.createElement("input");
			e.name = "ssid";
			e.value = token;
			e.type = "hidden";
			forms[i].appendChild(e);
		}
		
	}
}
//给a标签加token参数
function updateTags(token){
	var all = document.getElementsByTagName('a');
	var len = all.length;
	for(var i = 0; i < len; i++){
		var e = all[i];
		updateTag(e,'href',token);
	}
}
//给a标签加token参数
function updateTag(element,attr,token){
	var location = element.getAttribute(attr);
	if(location !=null && location != "" ){
		var fragmentIndex = location.indexOf('#');
		var fragment = null;
		if(fragmentIndex != -1){
			//url 中含有相当页面的锚标记
			fragment = location.substring(fragmentIndex);
			location = location.substring(0,fragmentIndex);
		}
		
		var index = location.indexOf('?');
		if(index != -1){
			//url已有参数
			location = location + '&ssid='+token;
		}else{
			//url没有参数
			location = location + '?ssid='+token;
		}
		
		if(fragment != null){
			location += fragment;
		}
		index = location.indexOf('javascript:void(0)');
		if(index != -1){
			location = element.getAttribute(attr);
		}
		element.setAttribute(attr,location);
	}
}
//验证表单
function spanWarning(a,b){
	$("#"+a+"_mlcx_verify").html("<font color='red'>"+b+"</font>");
}
//清空span提示
function formVerify(x){
	$("#"+x+"_mlcx_verify").html("");
}
//广告数组

var type_advert=["总首页","充电桩首页","无人停车场","充电站列表","充电桩个人中心","充电桩地锁订单列表",
                 "充电桩地锁订单详情","充电桩地锁订单评论","充电桩充电订单列表","充电桩充电订单详情","充电桩充电订单评论",
                 "充电桩我的钱包","充电桩充值记录","充电桩添加爱车","充电站详情","停车场详情","停车场列表","地锁预约"];//广告类型

var advertPosition_o=["首页第一位置轮播图[750px*300px]","首页第二位置轮播图[750px*300px]","首页第三位置轮播图[750px*300px]",
                      "首页第四位置轮播图[750px*300px]","首页第五位置轮播图[750px*300px]","首页第六位置轮播图[750px*300px]",
                      "首页第七位置轮播图[750px*300px]","首页第八位置轮播图[750px*300px]","首页第九位置轮播图[750px*300px]"];//总首页
var advertPosition_s=["顶部轮播图[750px*380px]","滚动文字","图片","中间轮播图"];//无人停车场
var advertPosition_t=["顶部轮播图","中间轮播图","广告","图片","滚动文字","最新动态"];//充电桩
var advertPosition_f=["顶部轮播图[750px*280px]","滚动文字","图片[750px*460px]","中间轮播图[702px*240px]"];//个人中心-地锁订单列表-....-充值记录 共用
var advertPosition_six=["顶部轮播图[750px*240px]","滚动文字","图片[702px*240px]","中间轮播图[702px*240px]"];//停车场详情
var advertPosition_se=["顶部轮播图[750px*380px]","滚动文字"];//停车场列表
var advertPosition_t_o=["顶部轮播图[750px*300px]"];//充电桩首页 顶部轮播图
var advertPosition_t_t=["中间第一位置轮播图[750px*200px]","中间第二位置轮播图[750px*200px]","中间第三位置轮播图[750px*200px]",
                        "中间第四位置轮播图[750px*200px]","中间第五位置轮播图[750px*200px]","中间第六位置轮播图[750px*200px]",
                        "中间第七位置轮播图[750px*200px]"];
var advertPosition_t_s=["第一位置广告[702px*200px]","第二位置广告[702px*200px]","第三位置广告[702px*200px]",
                        "第四位置广告[702px*200px]","第五位置广告[702px*200px]","第六位置广告[702px*200px]",
                        "第七位置广告[702px*200px]"];
var advertPosition_t_f=["第一位置图片[702px*410px]","第二位置图片[702px*410px]","第三位置图片[702px*410px]",
                        "第四位置图片[702px*620px]","第五位置图片[702px*620px]","第六位置图片[702px*620px]",
                        "第七位置图片[702px*620px]"];
var advertPosition_t_fv=["第一位置滚动文字","第二位置滚动文字","第三位置滚动文字",
                        "第四位置滚动文字","第五位置滚动文字","第六位置滚动文字",
                        "第七位置滚动文字","系统顶部滚动文字"];
var advertPosition_t_sx=["猛龙动态[250px*180px]","外部动态[250px*180px]"];
