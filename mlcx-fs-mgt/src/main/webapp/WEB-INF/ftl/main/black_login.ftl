<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="generator" content="EverEdit" />
		<title>猛龙出行管理平台</title>
		<!-- Tell the browser to be responsive to screen width -->
		<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
		<!-- Bootstrap 3.3.5 -->
		<link rel="stylesheet" type="text/css" href="res/dep/bootstrap-3.3.5/css/bootstrap.min.css">
		<!-- Font Awesome -->
		<link rel="stylesheet" type="text/css" href="res/dep/font-awesome-4.5.0/css/font-awesome.min.css">
		<!-- Ionicons -->
		<link rel="stylesheet" type="text/css" href="res/dep/ionicons/css/ionicons.min.css">
		<!-- Theme style -->
		<link rel="stylesheet" type="text/css" href="res/dep/AdminLTE-2.3.0/css/AdminLTE.css">
		<!-- daterangepicker -->
		<link rel="stylesheet" type="text/css" href="res/dep/daterangepicker/daterangepicker-bs3.css">
		<!-- DataTables -->
		<link rel="stylesheet" type="text/css" href="res/dep/DataTables/css/dataTables.bootstrap.css">
		<!-- skin -->
		<link rel="stylesheet" type="text/css" href="res/dep/AdminLTE-2.3.0/css/skins/skin-green-light.css">
		<!-- easyui css -->
		<link rel="stylesheet" type="text/css" href="res/dep/jquery-easyui-1.4.4/themes/icon.css">
		<!-- 自定义css -->
		<link rel="stylesheet" type="text/css" href="res/css/common.css">

		<link href="res/css/login/black_login.css" rel="stylesheet" type="text/css" />
		<link href="res/css/font/iconfont.css" rel="stylesheet" />
		
		
		
		
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
		<link rel="stylesheet" type="text/css" href="res/dep/summernote/summernote.css" rel="stylesheet">

		<!-- 首页面板数据样式 -->
		<link href="res/css/index_value_styles.css" rel="stylesheet" />
		<link href="res/css/font/iconfont.css" rel="stylesheet" />

		<!-- 2018-0307 版本修改样式文件 -->
		<link rel="stylesheet" type="text/css" href="res/css/css-aaron-180307.css">
		
		<!-- 2018-0404 版本修改样式文件 -->
		<link rel="stylesheet" type="text/css" href="res/css/css-arron-main-page-180404.css">
		
	</head>

	<body style="background: url(res/img/login.png) 0 50%">
		<style>
		 * {
				padding: 0;
				margin: 0;
			}
			
			body {
				position: fixed;
				width: 100%;
				height: 100%;
				top: 0;
				left: 0;
				right: 0;
				bottom: 0;
				background-size: cover;
			}
			
			.page-bg img{
				display:block;
				width:100%;
			}
			.page-bg{
				margin-top:-80px;
			}
			
			
			.container-con{
				position: fixed;
	    		width: 100%;
	    		z-index:100;
			}
			
			.img-title {
				position:fixed;
				width:100%;
				top:0;
				left:0;
				height: 80px;
				background-repeat: no-repeat;
				text-align: center;
				position: relative;
			}
			
			.img-title .title-con {
				text-align: center;
				color: #fff;
				font-size: 20px;
				margin: -62px;
				letter-spacing: 2px;
			}
			
			.img-title .eng-title-con {
				font-size: 10px;
				margin-top: 4px;
			}
			
			.center {
				background: rgba(255, 255, 255, 0.54);
				border: 4px solid RGBA(255, 255, 255, 0.13);
				border-radius: 8px;
				position: fixed;
    			top: 30%;
    			right: 20%;
			}
			
			.login-account {
				padding-top: 36px;
			}
			
			.login-account>span {
				font-size: 2rem;
				background: linear-gradient(to right, #F82490, #9027E0);
				-webkit-background-clip: text;
				color: transparent;
			}
			
			.login-account>div {
				text-align: center;
				margin: auto;
				color: black;
				padding-top: 36px;
				padding-left: 36px;
				padding-right: 36px;
				padding-bottom: 36px;
			}
			
			.login-account .iconfont {
				font-size: 1rem;
				position: absolute;
				margin-top: 1%;
				margin-left: 0.3%;
				color: #999999;
			}
			
			.input-box:first-child {
				margin-top: 0;
			}
			
			.input-box {
				border-radius: 5px;
				background: #FFFFFF;
				outline: none;
				margin: 24px 0;
				padding: 10px 10px 10px 10px;
				opacity: 0.74;
				border-radius: 6px;
				font-size: 16px;
				z-index: 1;
			}
			
			.alin {
				vertical-align: middle;
			}
			
			.col-1 input {
				border-radius: 5px;
				width: 80%;
				background: #FFFFFF;
				border: none;
				outline: none;
				opacity: 0.74;
				border-radius: 6px;
				padding: 3px 30px 3px 10px;
			}
			
			.col-1 input::-webkit-input-placeholder {
				/* WebKit browsers */
				font-size: 1.2rem;
			}
			
			.col-1 input:-moz-placeholder {
				/* Mozilla Firefox 4 to 18 */
				font-size: 1.2rem;
			}
			
			.col-1 input::-moz-placeholder {
				/* Mozilla Firefox 19+ */
				font-size: 1.2rem;
			}
			
			.col-1 input:-ms-input-placeholder {
				/* Internet Explorer 10+ */
				font-size: 1.2rem;
			}
			
			.col-1 span {
				padding-left: 34px;
				padding-top: 45px;
				font-size: 2.5rem;
			}
			
			.col-1 .button-btn {
				background: linear-gradient(to right, #F82490, #9027E0);
				border-radius: 6px;
				color: #FFFFFF;
				margin-top: 40px;
				font-size: 2rem;
			}
			
			.col-1 .button-btn .button {
				background: linear-gradient(to right, #F82490, #9027E0);
				padding: 4% 0;
				border: none;
				width: 100%;
				border-radius: 6px;
			}
			
			.fontSize {
				font-size: 1rem;
			}
			
			.col-1 {
				border-right: 0;
				border-bottom: 2px solid white 80%;
			}
			
			.col-1 h2 {
				display: none;
			}
			
			.col-1 .btn {
				margin-left: 55px;
			}
		</style>
			
		<div class="img-title">
			<img src="res/img/login-title.png" alt="" class="alin">
			<div class="title-con">
				<span>猛龙出行管理平台</span>
				<p class="eng-title-con">RAPTORS TRAVEL  MANAGEMENT SYSTEM</p>
			</div>
		</div>
		
		<!-- 登陆背景图片加载处理，模拟bg图 -->
		<!--
		<div class="page-bg">
			<img src="res/img/login_bg/1.png"/>
			<img src="res/img/login_bg/2.png"/>
			<img src="res/img/login_bg/3.png"/>
			<img src="res/img/login_bg/4.png"/>
			<img src="res/img/login_bg/5.png"/>
			<img src="res/img/login_bg/6.png"/>
			<img src="res/img/login_bg/7.png"/>
			<img src="res/img/login_bg/8.png"/>
			<img src="res/img/login_bg/9.png"/>
			<img src="res/img/login_bg/10.png"/>
		</div>
		 -->
		<!-- container -->
		<div class="con">
			<form name="loginForm" action="login.do" method="post" class="" style="z-index: 30000">
				<div class="container-con">
					
					<div class="col-1 center">
						<div class="login-account">
							<span>用户登录</span>
							<div>
								<div class="input-box">
									<img src="res/img/user.svg" alt="" class="alin">
									<input class="first alin" placeholder="请输入用户名称" name="loginName" required="" aria-required="true" type="text">
								</div>
								<div class="input-box">
									<img src="res/img/lock.svg" alt="" class="alin">
									<input class="pass alin" placeholder="请输入密码" name="loginPassword" required="" aria-required="true" type="password">
								</div>

								<div class="button-btn">
									<button type="submit" id="loginBtn" class="button">登录</button>
								</div>
							</div>
						</div>
					</div>

				</div>
			</form>
		</div>

	</body>

</html>
<script type="text/javascript" src="res/js/lib/jquery-1.11.3.js"></script>
<!-- jquery form -->
<script type="text/javascript" src="res/dep/jquery-form/jquery.form.min.js"></script>
<script type="text/javascript" src="res/js/common/md5.js"></script>

<script src="res/js/echarts/echarts-all.js"></script>
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
<script type="text/javascript" src="res/dep/summernote/summernote.js"></script>
<script type="text/javascript" src="res/dep/summernote/lang/summernote-zh-CN.js"></script>
<script type="text/javascript" src="res/js/common/summernote.js"></script>
<!-- SlimScroll -->
<!-- <script src="res/dep/slimScroll/jquery.slimscroll.min.js"></script> -->
<!-- FastClick -->
<!-- <script src="res/dep/fastclick/fastclick.min.js"></script> -->

<!-- template 引擎 -->
<script type="text/javascript" src="res/js/lib/handlebars-v4.0.5.js"></script>
<!-- 功能js -->
<script type="text/javascript" src="res/js/common/common.js"></script>

<script type="text/javascript" src="res/js/lib/require.js"></script>
<!--星级评分-->
<!--<script type="text/javascript" src="res/dep/rating/star-rating.js" ></script>-->
<!-- tree grid -->
<script type="text/javascript" src="res/dep/jquery-treegrid/js/jquery.treegrid.js"></script>
<script type="text/javascript" src="res/dep/jquery-treegrid/js/jquery.treegrid.bootstrap3.js"></script>