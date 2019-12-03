<meta charset="utf-8">
   <div class="container-fluid">
      <div class="row">
       <div class="col-md-12">
        <div class="box box-default">
         <div class="box-header with-border">
          <h3 class="box-title">修改密码</h3>
          <div class="box-tools pull-right">
          </div><!-- /.box-tools -->
         </div><!-- /.box-header -->
          <!-- /.box-footer -->
       </div><!-- /.box -->
      </div><!-- /.col -->
      </div><!-- /.row -->
      <form name="updatePassword">
				<div class="row" style="margin-top: 5%;margin-left: 14%;width: 36%;">
				 <input name="userId" type="hidden" value="${user.userId}"/>
			<div class="form-group">
            	<label for="inputEmail3" class="col-sm-3 control-label">原密码：</label>
                <div class="col-sm-8" >
                	<input type="password"id="oldPassword" class="form-control" name="oldPassword" placeholder="请输入原密码" >
                </div>
            </div> 
        	<div class="form-group">
            	<label for="inputEmail3" class="col-sm-3 control-label">新密码：</label>
                <div class="col-sm-8" >
                	<input type="password"id="newPassword" class="form-control" name="password" placeholder="请输入新密码" >
                </div>
            </div> 
            <div class="form-group">
            	<label for="inputEmail3" class="col-sm-3 control-label">确认新密码：</label>
                <div class="col-sm-8" >
                	<input type="password" id="newPassword1" class="form-control" name="password1" placeholder="请再次输入新密码" >
                </div>
            </div> 
                              <div class="modal-footer" style="border:none;padding:0;">
                              <div class="col-sm-9" style="width: 184px;">
				<button type="button" class="btn btn-default pull-right navbar-btn btn-flat" id="sysuser-update-password">修改</button> 					
			</div>
                   </div>              
        		</div><!-- /.row -->
        		</form>
    </div><!-- /. container-fluid-->
    <script type="text/javascript" src="${basePath!'' }/res/js/system/main.js"></script>
    <style type="text/css">
    .form-group{overflow: auto;}
    .form-group .col-sm-3{
    width:100px;
    height: 34px;
    line-height: 34px;
    padding: 0px;
    text-align: right;
    }
    </style>
    
