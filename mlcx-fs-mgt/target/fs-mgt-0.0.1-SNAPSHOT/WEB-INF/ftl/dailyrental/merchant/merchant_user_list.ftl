<meta charset="utf-8">
<style>
@media only screen and (max-width:992px) {
.pull-down-menu input, .pull-down-menu select {
    width: 30%;
}
.seach-input-item pull-left .form-control{
	width:45% !important;
	margin-right:20%;
}
.pull-down-menu span {
    width: 20%;
}
}
@media only screen and (max-width:768px) {
.row .sorting_disabled{
font-size:1.1rem !important;
}
}
@media only screen and (min-width:768px) and (max-width:1024px) {
.row .sorting_disabled{
font-size:1.2rem !important;
}
}
@media only screen and (min-width:1024px) and (max-width:1366px) {
.row .sorting_disabled{
font-size:1.3rem !important;
}
}
.other{
margin:0 !important;
padding:0 !important;
}
.box-footer button{
margin:0 1%;
}
.timeBegin span{
  margin-right:-10% !important;
}
.timeBegin input{
  width:50%;
}
table{
border:1px solid rgba(127,127,127,0.05);
}

</style>
   <div class="container-fluid">
    <div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
		<div class="search-input-content">
           <form class="form-horizontal" name="merchantUserSerachForm">
        	<div class="seach-input-border-box">
					
					<!-- /.box-tools -->
					<div class="seach-input-details close-content clearfix">
	                    <div class="seach-input-item pull-left">
	                    	<span>商家名称</span>
	                     	 <select name="merchantId" class="form-control val">
	                             <option value="" >请选择</option>
	                             <#list merchants as merchant>
	                                 <option value="${merchant.merchantId}" >
	                                        ${merchant.merchantName}
	                                 </option>
	                             </#list>
                             </select>
	                    </div>
	                     <div class="seach-input-item pull-left">                        
	                     	<span>用户名</span>
	                     	<input  class="form-control" name="userName" placeholder="请输入用户名">
	                     </div>
	                    <div class="seach-input-item pull-left">
	                        <span>电话</span>
	                        <input class="form-control" name="mobilePhone" placeholder="请输入电话">
	                    </div>
	                    <div class="seach-input-item pull-left">
	                    	 <span>审核状态</span>
	                     	 <select name="censorStatus" class="form-control val">
	                             <option value="" >请选择</option>
	                             <option value="0" >未审核</option>
	                             <option value="1" >已审核</option>
	                             <option value="3" >审核未通过</option>
                             </select>
	                    </div>
	                         <div class="seach-input-item pull-left">
	                    	 <span>黑名单</span>
	                     	 <select name="isBlacklist" class="form-control val">
	                             <option value="" >请选择</option>
	                             <option value="0" >正常</option>
	                             <option value="1" >黑名单</option>
                             </select>
	                    </div>
	                
            	</div>
            	<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="merchantUserSearch">确定</button>
					</div>
       		 </div>
         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     <div class="row show-table-content">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="merchantUserListTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="merchantUserList" class="table table-hover table-bettween-far" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
    </div><!-- /.container-fluid -->
    
     <div class="modal fade"  id="cencorMerchantUserModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">审核</h4>
                </div>
                <div class="modal-body">
                  <form class="form-horizontal" name="cencorMerchantUserForm"> 
			      	  <input type="hidden"  name="userNo">
			      	  <label for="inputEmail3" class=" control-label wen" id="cencorMerchantUserMemo"></label>
				      <div style="margin-left:35px;"> 
					      <input type="radio" checked="checked" name="censorStatus" value="1">通过
					      <input type="radio"  name="censorStatus" value="2">不通过
					  </div>
				      <div>
				          <label for="inputEmail3" class=" control-label reason">理由:</label>
				      </div> 
			          <div class="form-group">
                            <div class="col-sm-8">
                                 <textarea class="form-control textareas" name="cencorMemo"></textarea>
                            </div>
                      </div>
			     </form> 
			     </div>
			     <div class="modal-footer">
                   	 <input type="button" class="btn btn-default pull-right sure" id="merchantUserCencorFormBtn" value="确定" >
                     <button type="button" class="btn btn-default pull-right cancels"  id="merchantUserCencorCancelBtn">取消</button>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <div class="modal fade" id="merchantUserInBlacklistModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">移入黑名单</h4>
                </div>
                <div class="modal-body">
                   <form class="form-horizontal" name="merchantUserInBlacklistForm"> 
				      <input type="hidden"  name="userNo">
				      <input type="hidden"  name="isBlacklist" value="1">
				      <label for="inputEmail3" class=" control-label wen" id="merchantUserInBlacklistMemo"></label>
				   	  <div>
				          <label for="inputEmail3" class=" control-label reason">理由:</label>
				      </div> 
			          <div class="form-group">
	                        <div class="col-sm-8">
	                             <textarea class="form-control textareas" name="blacklistMemo"></textarea>
	                        </div>
	                  </div>
				   </form> 
				</div>
				<div class="modal-footer">
                     <input type="button" class="btn btn-default pull-right sure" id="merchantUserInBlackConfirmBtn" value="确定" >
                     <button type="button" class="btn btn-default pull-right cancels"  id="merchantUserInBlackCancel">取消</button>
                </div>      
                
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 
    
    <div class="modal fade"  id="merhcantUserOutBlacklistModel">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">移出黑名单</h4>
                </div>
                <div class="modal-body">
                  <form class="form-horizontal" name="merhcantUserOutBlacklistForm"> 
			      <input type="hidden"  name="userNo">
			      <input type="hidden"  name="isBlacklist" value="0">
			      <label for="inputEmail3" class=" control-label wen" id="merhcantUserOutBlackListMemo"></label>
			      <div>
			          <label for="inputEmail3" class=" control-label reason">理由:</label>
			      </div> 
		          <div class="form-group">
                        <div class="col-sm-8">
                             <textarea class="form-control textareas" name="blacklistMemo"></textarea>
                        </div>
                  </div>
			      </form> 
			    </div>
			    <div class="modal-footer">
                     <input type="button" class="btn btn-default pull-right sure" id="merhcantUserOutBlackConfirmBtn" value="确定" >
                     <button type="button" class="btn btn-default pull-right cancels"  id="merhcantUserOutBlackCancelBtn">取消</button>
                </div> 
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
     
    <script type="text/javascript">
	$(function(){
	  require.config({paths: {"merchantUser":"res/js/dailyrental/merchant/merchant_user_list"}});
		require(["merchantUser"], function (merchantUser){
			merchantUser.init();
		});  
	});    
</script>
