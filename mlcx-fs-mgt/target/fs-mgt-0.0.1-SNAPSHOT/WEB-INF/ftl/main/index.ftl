<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<!-- Tell the browser to be responsive to screen width -->
		<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
		<title>猛龙出行分时租赁管理系统 </title>
		<!-- Bootstrap 3.3.5 -->
		<link rel="stylesheet" type="text/css" href="res/dep/bootstrap-3.3.5/css/bootstrap.min.css">
		<!-- Font Awesome -->
		<link rel="stylesheet" type="text/css" href="res/dep/font-awesome-4.5.0/css/font-awesome.min.css">
		<!-- Ionicons -->
		<link rel="stylesheet" type="text/css" href="res/dep/ionicons/css/ionicons.min.css">
		<!-- Theme style -->
		<link rel="stylesheet" type="text/css" href="res/dep/AdminLTE-2.3.0/css/AdminLTE.css">
		<!-- AdminLTE Skins. We have chosen the skin-blue for this starter
       page. However, you can choose any other skin. Make sure you
       apply the skin class to the body tag so the changes take effect.
         -->

		<!-- 加载百度地图样式信息窗口 -->
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=XWlUgpVK4cGkZjyBSoyNTM8wEV1T0qgN"></script>

		<!-- datepicker -->
		<link rel="stylesheet" type="text/css" href="res/dep/datepicker/datepicker3.css">
		<!-- daterangepicker -->
		<link rel="stylesheet" type="text/css" href="res/dep/daterangepicker/daterangepicker-bs3.css">
		<!--datetimepicker-->
		<link rel="stylesheet" type="text/css" href="res/dep/datetimepicker/bootstrap-datetimepicker.css">
		<!-- DataTables -->
		<link rel="stylesheet" type="text/css" href="res/dep/DataTables/css/dataTables.bootstrap.css">
		<!-- skin -->
		<link rel="stylesheet" type="text/css" href="res/dep/AdminLTE-2.3.0/css/skins/skin-blue-light.css">
		<!-- easyui css -->
		<link rel="stylesheet" type="text/css" href="res/dep/jquery-easyui-1.4.4/themes/icon.css">
		<!-- 自定义css -->
		<link rel="stylesheet" type="text/css" href="res/css/common.css">

		<!-- jstree -->
		<link rel="stylesheet" type="text/css" href="res/dep/jstree/themes/default/style.min.css" />
		<!-- 文件上传 -->
		<!-- <link rel="stylesheet" type="text/css" href="res/dep/jQuery-File-Upload/css/jquery.fileupload.css"> -->
		<link rel="stylesheet" type="text/css" href="res/dep/fine-uploader/css/fineuploader.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="res/dep/fine-uploader/css/fineuploader-gallery.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="res/dep/fine-uploader/css/fineuploader-new.css" rel="stylesheet">
		<!--星级评分-->
		<link href="res/dep/rating/star-rating.min.css" media="all" rel="stylesheet" type="text/css" />
		<!-- tree grid -->
		<link rel="stylesheet" type="text/css" href="res/dep/jquery-treegrid/css/jquery.treegrid.css" rel="stylesheet">
		<!-- summernote 富文本编辑器 -->
		<!-- <link rel="stylesheet" type="text/css" href="res/dep/summernote/summernote.css" rel="stylesheet">
 -->
		<!-- 首页面板数据样式 -->
		<link href="res/css/index_value_styles.css" rel="stylesheet" />
		<link href="res/css/font/iconfont.css" rel="stylesheet" />

		<!-- 2018-0307 版本修改样式文件 -->
		<link rel="stylesheet" type="text/css" href="res/css/css-aaron-180307.css">
		
		<!-- 2018-0404 版本修改样式文件 -->
		<link rel="stylesheet" type="text/css" href="res/css/css-arron-main-page-180404.css">
		
		<script src="res/js/echarts/echarts-all.js"></script>
		<!--  jedate-->
		<link type="text/css" rel="stylesheet" href="res/jedate/test/jeDate-test.css">
		<link type="text/css" rel="stylesheet" href="res/jedate/skin/jedate.css">
        <script type="text/javascript" src="res/jedate/src/jedate.js"></script>
		<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
 <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
 <!-- <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>-->
 <![endif]-->
		<!--[if lt IE 9]>
 <script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
 <![endif]-->
	</head>
	<!--
BODY TAG OPTIONS:
=================
Apply one or more of the following classes to get the
desired effect
|---------------------------------------------------------|
| SKINS         | skin-blue                               |
|               | skin-black                              |
|               | skin-purple                             |
|               | skin-yellow                             |
|               | skin-red                                |
|               | skin-green                              |
|---------------------------------------------------------|
|LAYOUT OPTIONS | fixed                                   |
|               | layout-boxed                            |
|               | layout-top-nav                          |
|               | sidebar-collapse                        |
|               | sidebar-mini                            |
|---------------------------------------------------------|
-->

	<body class="hold-transition skin-blue-light sidebar-mini">

		<div class="wrapper">
			<!-- Main Header -->
			<header class="main-header">

				<input type="hidden" id="appPath" value="${basePath!'' }">
				<input type="hidden" id="imgPath" value="${imagePath!'' }">
				<input type="hidden" id="resPath" value="${resPath!'' }">
				<input type="hidden" id="resImgPath" value="${resImgPath!'' }">
				<input type="hidden" id="beforePath" value="${beforePath!'' }">
				<input type="hidden" id="appModel">
				<!--存储主要可是窗口有效高度-->
				<input type="hidden" id="mainContentHeight" value="">
				<!-- Header Navbar -->
			</header>
			<!-- Left side column. contains the logo and sidebar -->
			<aside class="main-sidebar">

				<!-- sidebar: style can be found in sidebar.less -->
				<section class="sidebar">

					<!-- Sidebar user panel (optional) -->
					<#--<div class="user-panel">
						<div class="pull-left image">
							<img src="res/dep/AdminLTE-2.3.0/img/user2-160x160.jpg" class="img-circle" alt="User Image" data-url="${basePath!''}/sysUser/updatePassword.do" onclick="changePassword($(this).data('url'));">
						</div>
						<div class="pull-left info">
							<p></p>
							<!-- /.search form -->

							<!-- Sidebar Menu -->
							<div class="left-nav-bg-img">
							</div>
							<div class="fs-manger-tab">
							<div class="border_length">
							<div class="userName clearfix">
								<img src="res/dep/AdminLTE-2.3.0/img/user2-160x160.jpg" class="user-image pull-left" alt="User Image">
								<span class="pull-left">${sysUser.userName!''}</span>
								<a style="margin-top:2px" class="pull-right" href="${basePath!'' }/loginOut.do"><img src="res/img/loginOut.png"/></a>
								</div>
								
								<!--<div class="user-time">
									<span class="user-year-month-day"></span>
									<span class="time"></span>
								</div>-->
        	                    
							</div>
								
							</div>
								
							<ul class="sidebar-menu" id="leftNavBar">
							</ul>
							</li>
							</ul>
							<!-- /.sidebar-menu -->

							<script id="menu-template" type="text/x-handlebars-template">
								{{#each menu}} 
								{{#if child}}
								<li class="treeview">
									<a class="left-nav-span click-name up-down-icon-a" href="javascript:void(0)">
										<i class="fa icon hziconfont icons-system {{iconName}}"></i>
										<span class="click-name">{{name}}</span>
										<i class="fa fa-angle-down up-down-icon click-name"></i>
									</a>
									<ul class="treeview-menu">
										{{#each child}}
										<li title="{{name}}" data-url="{{link}}">
											<a href="javascript:void(0)" title="{{name}}">{{name}}</a>
										</li>
										{{/each }}
									</ul>
								</li>
								{{else}}
								<li>
									<a href="javascript:void(0)"><i class="fa icon hziconfont icons-system {{iconName}}"></i><span>{{name}}</span></a>
								</li>
								{{/if}}
							{{/each}}
							</script>

				</section>
				<!-- /.section -->
				<!-- /.sidebar -->
			</aside>
			<!-- 关闭tab页的显示内容 -->
			
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper">
			
			<div  class="close-all-tab-btn">
				<div class="tab-button" onclick="closeAllTab()" type="button"><i class="fa fa-angle-right"></i>&nbsp;&nbsp;关闭所有</div>
			</div>
				
				
				<div id="mainConsoleTabs" class="easyui-tabs">
					<div title="主控制台">
						<div class="container-fluid containerBg">
							<div class="row blList">
								
								<div class="search">
									<h1 class="pull-left">快捷搜索：</h1>
									<div class="searchChoose t_c">
										<i class="fa fa-angle-down up-down-icon-select click-name"></i>
										<select class="searchChoose-select" id="searchType" autocomplete="off">
											<option value="order" selected="selected">订单</option>
											<option value="member">会员</option>
											<option value="car">车辆</option>
											<option value="park">场站</option>
											<option value="worker">任务</option>
											<img src="res/img/imgdown.png" class="imgdown" />
										</select>
									</div>
									<input type="text" autocomplete="off" class="searchInput" id="searchInput" placeholder="请输入订单号">
									<span class="btn_search t_c" id="quickSearchBut"><i class="fa fa-search"></i></span>
									<span class="seach-tip">请完善搜索信息</span>
								</div>
								<#if roleAdminTag!=null && roleAdminTag!=0>
									<div class="sum">
										<div class="date-group-content peoList bg1">
											<h1 class="orderName">总订单数</h1>
											<p class="num" id="orderSum"></p>
										</div>
										<div class="date-group-content peoList bg2">
										    <h1 class="orderName">总会员数</h1>
											<p class="num" id="memberSum"></p>
										</div>
										<div class="date-group-content peoList bg3">
											<h1 class="orderName">认证会员</h1>
											<p class="num" id="AcMemSum"></p>
										</div>
										<div class="date-group-content peoList bg4">
										<h1 class="orderName">总车辆</h1>
											<p class="num" id="carSum"></p>
										</div>
										<div class="date-group-content peoList bg5">
											<h1 class="orderName">总场站数</h1>
											<p class="num" id="airForSum"></p>
										</div>
									</div>
								</#if>
							</div>
							<div class="row waitList">
								
								<div class="deal">
									<div class="col-lg-6 backlog">
										<p class="backTitle mgl20">
											<span class="ft24 mgl20 color_font_db">
                					<img src="res/img/icon-one.png" class="iconimg"/>
                					待办</span>
										</p>
										<div class="row mgl20">
											<div class="col-md-3 back1 b_b b_l b_r b_t">
												<span class="member1">待退款
												<img class="floatRightIconRed" src="res/img/icon-red.png">
												</span>
												<p class="ft24" id="waitRefund"></p>
											</div>
											<div class="col-md-3 back1 b_b b_r b_t">
												<span class="member1">待认证会员</span>
												<p class="ft24" id="waitCensorMember"></p>
											</div>
											<div class="col-md-3 back1 b_b b_r b_t">
												<span class="member1">非订单用车</span>
												<p class="ft24" id="onOrder"></p>
											</div>
											<div class="col-md-3 back1 b_b b_l b_t b_r">
												<span class="member1">待开发票</span>
												<p class="ft24" id="invoice"></p>
											</div>
										</div>
										<div class="row mgl20">
											<div class="col-md-3 back1 b_b b_l b_r">
												<span class="member1">待回复反馈</span>
												<p class="ft24" id="customerFeedback"></p>
											</div>
											<div class="col-md-3 back1 b_b b_l b_r">
												<span class="member1">新网点反馈数</span>
												<p class="ft24" id="newParkOpening"></p>
											</div>
											<div class="col-md-3 back1 b_b  b_r">
												<span class="member1">订单评价</span>
												<p class="ft24" id="orderComments"></p>
											</div>
										</div>
										
									</div>
									<div class="col-lg-6 backlog">
										<p class="backTitle mgl20">
											<span class="ft24 mgl20 color_font_jy">
                					<img src="res/img/icon-two.png" class="iconimg"/>
                					交易</span>
										</p>
										<div class="row mgl20">
											<div class="col-md-3 back1 b_b b_l b_r b_t">
												<span class="member1">未支付订单</span>
												<p class="ft24" id="notPayOrder"></p>
											</div>
											<div class="col-md-3 back1 b_b b_r b_t">
												<span class="member1">今日新增订单</span>
												<p class="ft24" id="todayOrder"></p>
											</div>
											<div class="col-md-3 back1 b_b b_r b_t">
												<span class="member1">进行中的订单</span>
												<p class="ft24" id="doingOrder"></p>
											</div>
											<div class="col-md-3 back1 b_b b_l b_r b_t">
												<span class="member1">今日充值笔数</span>
												<p class="ft24" id="recharge"></p>
											</div>
										</div>
									</div>
								</div>
								<div class="deal">
									<div class="col-lg-6 backlog">
										<p class="backTitle mgl20">
											<span class="ft24 mgl20 color_font_cw">
                					<img src="res/img/icon-three.png" class="iconimg"/>
                					车务</span>
										</p>
										<div class="row mgl20">
											<div class="col-md-3 back1 b_b b_l b_r b_t">
												<span class="member1">车位不足场站</span>
												<p class="ft24" id="lotParkingSpace"></p>
											</div>
											<div class="col-md-3 back1 b_b b_r b_t">
												<span class="member1">待办任务</span>
												<p class="ft24" id="waitWorkerOrder"></p>
											</div>
											<div class="col-md-3 back1 b_b b_r b_t">
												<span class="member1">电量不足车辆</span>
												<p class="ft24" id="carLowPower"></p>
											</div>
											<div class="col-md-3 back1 b_b b_l b_t b_r">
												<span class="member1">上线车辆</span>
												<p class="ft24" id="online"></p>
											</div>
										</div>
										<div class="row mgl20">
											<div class="col-md-3 back1 b_b b_l b_r">
												<span class="member1">下线车辆</span>
												<p class="ft24" id="offline"></p>
											</div>
											<div class="col-md-3 back1 b_b  b_r">
												<span class="member1">进行中的任务</span>
												<p class="ft24" id="doingWorkerOrder"></p>
											</div>
											<div class="col-md-3 back1"></div>
										</div>
									</div>
									<div class="col-lg-6 backlog">
										<p class="backTitle mgl20">
											<span class="ft24 mgl20 color_font_jg">
                					<img src="res/img/icon-four.png" class="iconimg"/>
                					监控警告</span>
										</p>
										<div class="row mgl20">
											<div class="col-md-3 back1 b_b b_l b_r b_t">
												<span class="member1">闲置车辆</span>
												<p class="ft24" id="carFreeTime"></p>
											</div>
											<div class="col-md-3 back1 b_b b_r b_t">
												<span class="member1">断线车辆</span>
												<p class="ft24" id="carNotLine"></p>
											</div>
											<div class="col-md-3 back1 b_b b_r b_t">
												<span class="member1">非订单用车</span>
												<p class="ft24" id="notOrder"></p>
											</div>
											<div class="col-md-3 back1 b_b b_l b_r b_t">
												<span class="member1">小电瓶低电</span>
												<p class="ft24" id="lowPower1"></p>
											</div>
										</div>
										<div class="row mgl20">
											<div class="col-md-3 back1 b_b b_l b_r">
												<span class="member1">超额订单</span>
												<p class="ft24" id="excessOrder"></p>
											</div>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row tubiaobox">
							<div class="dealTB">
							<#if roleAdminTag!=null && roleAdminTag!=0>
									<div class="col-md-6 date-group-content">
										<div class="tubiao">
											<#include "main/values/orderChart.ftl" parse="true" encoding="utf-8">
										</div>
									</div>
									<div class="col-md-6 date-group-content rightJj">
										<div class="tubiao">
											<#include "main/values/member.ftl" parse="true" encoding="utf-8">
										</div>
									</div>
							</div>
							<div class="dealTB">
								<div class="col-md-6 date-group-content">
									<div class="tubiao">
										<#include "main/values/carCordon.ftl" parse="true" encoding="utf-8">
									</div>
								</div>
								<div class="col-md-6 date-group-content rightJj">
									<div class="tubiao">
										<#include "main/values/alarmNum.ftl" parse="true" encoding="utf-8">
									</div>
								</div>
							</div>
							<div class="dealTB">
							</#if>
								<div class="col-md-6 date-group-content">
									<div class="tubiao">
										<#include "main/values/carOrder.ftl" parse="true" encoding="utf-8">
									</div>
								</div>
							</div>
							</div>
							<!-- /.row -->

							<style>
								.a:hover,
								.b:hover,
								.c:hover,
								.d:hover,
								.e:hover {
									color: #ff7314;
									cursor: pointer;
								}
							</style>
							<!-- /.row -->
						</div>
						<!-- /.container-fluid -->
					</div>
					<!-- /.tab -->
				</div>
				<!-- /.easyui-tabs -->

			</div>
			<!-- /.content-wrapper -->
			<!-- 公用组件加载区域 -->
			<div id="appComponent"></div>
			<!-- 文件上传加载区域 -->
			<div id="appUpload"></div>

			<script type="text/x-handlebars-template" id="upload-item-template">
				<li class="qq-upload-success">
					<!--
	    <div class="qq-progress-bar-container-selector">
	        <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-progress-bar-selector qq-progress-bar"></div>
	    </div>

	    <span class="qq-upload-spinner-selector qq-upload-spinner"></span>
		-->
					<img class="qq-thumbnail-selector" qq-max-size="100" qq-server-scale style="width:100px;heigth:75px">
					<span class="qq-upload-file-selector qq-upload-file"></span>
					<span class="qq-edit-filename-icon-selector qq-edit-filename-icon" aria-label="Edit filename"></span>
					<input class="qq-edit-filename-selector qq-edit-filename" tabindex="0" type="text">
					<span class="qq-upload-size-selector qq-upload-size"></span>
					<button type="button" class="qq-btn qq-upload-delete-selector qq-upload-up">上移</button>
					<button type="button" class="qq-btn qq-upload-delete-selector qq-upload-down">下移</button>
					<button type="button" class="qq-btn qq-upload-delete-selector qq-upload-remove">删除</button>
					<span role="status" class="qq-upload-status-text-selector qq-upload-status-text"></span>
				</li>
			</script>

			<!-- Main Footer -->
			<footer class="main-footer">
				<!-- To the right -->
				<div class="pull-right hidden-xs">
					<#--Anything you want-->
				</div>
				<!-- Default to the left -->
				Copyright &copy; 2017<a style="color: #8899aa">浙江猛龙科技有限公司</a>. All rights reserved.
			</footer>
			<!-- ./wrapper -->
			<!-- [if lt IE 9] -->
			<!-- jQuery 2.1.4 -->
			<!-- <script type="text/javascript" src="res/js/jQuery-2.1.4.min.js"></script> -->
			<!-- [endif] -->
			<script type="text/javascript" src="res/js/lib/jquery-1.11.3.js"></script>
			<!-- jquery form -->
			<script type="text/javascript" src="res/dep/jquery-form/jquery.form.js"></script>
			<!-- Bootstrap 3.3.5 -->
			<script type="text/javascript" src="res/dep/bootstrap-3.3.5/js/bootstrap.min.js"></script>
			<!-- easyui tabs lazyload -->
			<script type="text/javascript" src="res/dep/jquery-easyui-1.4.4/plugins/jquery.parser.js"></script>
			<script type="text/javascript" src="res/dep/jquery-easyui-1.4.4/easyloader-n.js"></script>

			<!-- AdminLTE App -->
			<script type="text/javascript" src="res/dep/AdminLTE-2.3.0/js/app.js"></script>
			<!-- 日期处理 -->
			<script type="text/javascript" src="res/dep/daterangepicker/moment.js"></script>
			<!-- 日期 -->
			<script type="text/javascript" src="res/dep/datepicker/bootstrap-datepicker.js"></script>
			<script type="text/javascript" src="res/dep/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
			<!-- 日期范围 -->
			<script type="text/javascript" src="res/dep/daterangepicker/daterangepicker.js"></script>
			<!-- <script type="text/javascript" src="res/dep/daterangepicker/locales/daterangepicker.zh-CN.js"></script> -->
			<!--日期时间-->
			<script type="text/javascript" src="res/dep/datetimepicker/bootstrap-datetimepicker.js"></script>
			<script type="text/javascript" src="res/dep/datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>
			<!-- DataTables -->
			<script type="text/javascript" src="res/dep/DataTables/js/jquery.dataTables.min.js"></script>
			<script type="text/javascript" src="res/dep/DataTables/js/dataTables.bootstrap.min.js"></script>
			<script type="text/javascript" src="res/dep/DataTables/js/dataTables.rowsGroup.js"></script>
			<!-- 通用配置 datatables -->
			<script type="text/javascript" src="res/js/common/dataTables.js"></script>
			<!-- <script type="text/javascript" src="http://cdn.datatables.net/plug-ins/1.10.11/sorting/custom-data-source/dom-checkbox.js"></script> -->
			<!-- <script type="text/javascript" src="http://cdn.datatables.net/plug-ins/1.10.11/sorting/date-dd-MMM-yyyy.js"></script> -->

			<!-- jquery validate -->
			<script type="text/javascript" src="res/dep/jquery-validation/jquery.validate.js"></script>
			<script type="text/javascript" src="res/js/common/validate.js"></script>
			<script type="text/javascript" src="res/dep/jquery-validation/localization/messages_zh.min.js"></script>

			<!-- jstree -->
			<script type="text/javascript" src="res/dep/jstree/jstree.min.js"></script>
			<!-- 消息框 -->
			<script type="text/javascript" src="res/dep/bootbox/bootbox.min.js"></script>
			<!-- 日期处理 -->
			<!-- <script type="text/javascript" src="http://cdn.bootcss.com/moment.js/2.11.2/moment.js"></script> -->
			<!-- 文件上传 -->
			<!-- <script type="text/javascript" src="res/dep/fine-uploader/js/uploader.basic.js"></script> -->
			<script type="text/javascript" src="res/dep/fine-uploader/js/all.fine-uploader.min.js"></script>
			<!-- summernote 富文本编辑器 -->
			<!-- <script type="text/javascript" src="res/dep/summernote/summernote.js"></script>
			<script type="text/javascript" src="res/dep/summernote/lang/summernote-zh-CN.js"></script>
			<script type="text/javascript" src="res/js/common/summernote.js"></script> -->
			<script src="res/dist/summernote.js"></script>
			<script src="res/dist/summernote-zh-CN.js"></script>    <!-- 中文-->
			<link href="res/dist/summernote.css" rel="stylesheet"/>
			<!-- SlimScroll -->
			<!-- <script src="res/dep/slimScroll/jquery.slimscroll.min.js"></script> -->
			<!-- FastClick -->
			<!-- <script src="res/dep/fastclick/fastclick.min.js"></script> -->

			<!-- template 引擎 -->
			<script type="text/javascript" src="res/js/lib/handlebars-v4.0.5.js"></script>
			<!-- 功能js -->
			<script type="text/javascript" src="res/js/common/menu.js"></script>
			<script type="text/javascript" src="res/js/common/common.js"></script>

			<script type="text/javascript" src="res/js/lib/require.js"></script>
			<!--星级评分-->
			<!--<script type="text/javascript" src="res/dep/rating/star-rating.js" ></script>-->
			<!-- tree grid -->
			<script type="text/javascript" src="res/dep/jquery-treegrid/js/jquery.treegrid.js"></script>
			<script type="text/javascript" src="res/dep/jquery-treegrid/js/jquery.treegrid.bootstrap3.js"></script>
			<script>
				function changePassword(url) {
					var urls = url.split("/");
					var model = urls[urls.length - 2];
					$("#appModel").val("model");
					window.xzConfig.appModel = model;
					addTab("修改密码", url);
				}

				function memberResult(url) {
					addTab("会员管理", url);
				}

				function depositRefundResult(url) {
					addTab("押金退款列表", url);
				}

				function workerOrderResult(url) {
					addTab("调度工单列表", url);
				}

				function orderStrikeBalanceResult(url) {
					addTab("订单冲账列表", url);
				}

				function pricingPackageResult(url) {
					addTab("套餐产品列表", url);
				}

				function warningResult(url) {
					addTab("警告管理列表", url);
				}

				function js_method_car(url) {

					addTab("车辆监控", url);
				}

				function js_method_park(url) {

					addTab("场站监控", url);
				}

				function js_method_member(url) {

					addTab("会员管理", url);
				}

				function js_method_order(url) {

					addTab("订单列表", url);
				}

				function js_method_prderp(url) {

					addTab("订单冲账列表", url);
				}
			</script>
			<script type="text/javascript">
				$(function() {
					require.config({
						paths: {
							"indexChart": "res/js/main/indexChart"
						}
					});
					require(["indexChart"], function(indexChart) {
						indexChart.init();
						
					});
				});
			</script>
			<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. Slimscroll is required when using the
     fixed layout. -->
	</body>

</html>