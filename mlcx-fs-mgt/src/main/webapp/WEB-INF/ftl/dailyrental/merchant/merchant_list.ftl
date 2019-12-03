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
           <form class="form-horizontal" name="merchantSerachForm">
        	<div class="seach-input-border-box">
					
					<!-- /.box-tools -->
					<div class="seach-input-details seach-input-details-change close-content clearfix">
	                    <div class="seach-input-item seach-input-item-change pull-left">
	                        <span>租赁商名称</span>
	                        <input  class="form-control" name="merchantName" placeholder="请输入租赁商名称">
	                    </div>
	                     <div class="seach-input-item seach-input-item-change pull-left">                        
	                     	<span>联系人</span>
	                     	<input  class="form-control" name="cantactPerson" placeholder="请输入联系人">
	                     </div>
	                    <div class="seach-input-item seach-input-item-change pull-left">
	                        <span>联系电话</span>
	                        <input class="form-control" name="mobilePhone" placeholder="请输入联系电话">
	                    </div>
            	</div>
            	<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="merchantSearch">确定</button>
					</div>
       		 </div>
         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     <div class="row show-table-content">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="merchantListTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="merchantList" class="table table-hover table-bettween-far" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
    </div><!-- /.container-fluid -->
    <div class="modal fade"  id="onMerchantModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">确定启用</h4>
                </div>
                <div class="modal-body">
                  <form class="form-horizontal" name="onMerchantForm"> 
			      <input type="hidden"  name="merchantId">
			      <input type="hidden"  name="isAvailable" value="1">
			      <label for="inputEmail3" class=" control-label wen" id="onMerchantMemo"></label>
			     </div>
			    
			     </form> 
			      <div class="modal-footer">
                    <input type="button" class="btn btn-default pull-right sure" id="merchantOnFormBtn" value="确定" >
                     <button type="button" class="btn btn-default pull-right cancels"  id="merchantOnCancelBtn">取消</button>
                </div> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
     
    <div class="modal fade" id="offMerchantModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">确定停用</h4>
                </div>
                <div class="modal-body">
                   <form class="form-horizontal" name="offMerchantForm"> 
				      <input type="hidden"  name="merchantId">
				      <input type="hidden"  name="isAvailable" value="0">
				      <label for="inputEmail3" class=" control-label wen" id="offMerchantMemo"></label>
				   </form> 
				       <div class="modal-footer">
                    <input type="button" class="btn btn-default pull-right sure" id="merchantOffBtn" value="确定" >
                     <button type="button" class="btn btn-default pull-right cancels"  id="merchantOffCancel">取消</button>
                </div>      
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 
     <div class="modal fade"  id="cencorMerchantModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">审核</h4>
                </div>
                <div class="modal-body">
                  <form class="form-horizontal" name="cencorMerchantForm"> 
			      	  <input type="hidden"  name="merchantId">
			      	  <label for="inputEmail3" class=" control-label wen" id="cencorMerchantMemo"></label>
				      <div style="margin-left:35px;"> 
					      <input type="radio" checked="checked" name="cencorStatus" value="1">通过
					      <input type="radio"  name="cencorStatus" value="2">不通过
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
			     <div class="modal-footer">
                   	 <input type="button" class="btn btn-default pull-right sure" id="merchantCencorFormBtn" value="确定" >
                     <button type="button" class="btn btn-default pull-right cancels"  id="merchantCencorCancelBtn">取消</button>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
     
    <script type="text/javascript">
	$(function(){
	  require.config({paths: {"merchant":"res/js/dailyrental/merchant/merchant_list"}});
		require(["merchant"], function (merchant){
			merchant.init();
		});  
	});    
</script>
