<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>登录</title>
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
     <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
           page. However, you can choose any other skin. Make sure you
           apply the skin class to the body tag so the changes take effect.
     -->
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
    
     <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
     <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
     <!--[if lt IE 9]>
     <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
     <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
     <![endif]-->
     <!--[if lt IE 9]>
     <script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
     <![endif]-->
  </head>
  <body class="hold-transition login-page">
    <div class="login-box">
      <div class="login-logo">
        <a href="#"><b>分时租赁</b></a>
      </div><!-- /.login-logo -->
      <div class="login-box-body">
        <p class="login-box-msg">Sign in to start your session</p>
        <form action="login.do" method="post">
          <div class="form-group has-feedback">
            <input type="text" class="form-control" placeholder="text" name="loginName" value="">
            <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input type="password" class="form-control" placeholder="Password" name="loginPassword" value="">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
          </div>
          <div class="row">
            <!--<div class="col-xs-8">
              <div class="checkbox icheck">
                <label>
                  <input type="checkbox"> Remember Me
                </label>
              </div>
            </div><!-- /.col -->
            <div class="col-xs-4">
              <button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>
            </div><!-- /.col -->
          </div>
        </form>

       

      </div><!-- /.login-box-body -->
    </div><!-- /.login-box -->

    <!-- jQuery 2.1.4 -->
    <script type="text/javascript" src="res/js/lib/jquery-1.11.3.js"></script>
    <!-- Bootstrap 3.3.5 -->
    <script type="text/javascript" src="res/dep/bootstrap-3.3.5/js/bootstrap.min.js"></script>
    <!-- iCheck -->
    <!--<script src="../../plugins/iCheck/icheck.min.js"></script> -->
   <!-- <script>
      $(function () {
        $('input').iCheck({
          checkboxClass: 'icheckbox_square-blue',
          radioClass: 'iradio_square-blue',
          increaseArea: '20%' // optional
        });
      });
    </script>-->
  </body>
</html>
