
<meta charset="utf-8">
<style>
@media only screen and (max-width:992px) {
.pull-down-menu input, .pull-down-menu select {
    width: 30%  !important;
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
table{
border:1px solid rgba(127,127,127,0.05);
}
</style>
  <div class="container-fluid">
   <div class="search-input-wrapper">
  <div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
			<!--<span class="pull-right moreButton" id="moreCarList">更多</span>-->
		</div>
     <div class="search-input-content">
       <form name="deviceSearchForm">
       <div class="seach-input-border-box">
        <div class="seach-input-details close-content clearfix">
                <div class="seach-input-item pull-left">
                    <span>终端序列号</span>
                    <input class="form-control" name="deviceSn" placeholder="请输入终端序列号">
                </div>
                <div class="seach-input-item pull-left">
                    <span>绑定车辆</span>
                    <input class="form-control" name="carPlateNo" placeholder="请输入要绑定车辆">
                </div>
			     <div class="seach-input-item pull-left">
                    <span>启用状态</span>
                    <select class="form-control" name="isAvailable">
                                           <option value="">全部</option>
                                           <option value="1">启用</option>
                                           <option value="0">停用</option>
                                         </select>
                </div>
                <div class="seach-input-item pull-left">
                    <span>终端在线</span><select class="form-control" name="isOnline">
                    <option value="">全部</option>
                    <option value="1">在线</option>
                    <option value="2">不在线</option>
                </select>
                </div>
                
                
	                 <div class="seach-input-item pull-left">
	                    <span>城市</span>
	                   <select name="cityId" class="form-control">
					              <option value="">请选择</option>
									 <#list cities as citie>
										 <option  value="${citie.dataDictItemId}" >
					                            ${citie.itemValue}
					                     </option>
					                 </#list>  
					  </select>
				</div>
			
			
               
                
                
            </div>
            <div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="deviceSearch">确定</button>
			</div>
		</div>
         </form>
       </div><!-- /.box -->
      </div><!-- /.col -->
      <div class="row show-table-content">
       <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="deviceBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="deviceList" class="table table-hover" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->   
      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->
    <div class="modal fade" id="enableDeviceModel">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">终端设备启用</h4>
                </div>
                <div class="modal-body">
                   <form class="form-horizontal" name="enableDeviceForm"> 
				      <input type="hidden"  name="terminalDeviceNo" id="enableDeviceNo">
				      <input type="hidden"  name="isAvailable" value="1">
				        <label for="inputEmail3" class=" control-label wen" id="enableDeviceMemo"></label>
				         <div>
				            <label for="inputEmail3" class=" control-label reason">理由:</label>
				         </div> 
				          <div class="form-group">
	                            <div class="col-sm-8">
	                                 <textarea class="form-control textareas" name="availableMemo"></textarea>
	                            </div>
	                      </div>
	                      <div class="modal-footer">

                    <input type="button" class="btn btn-default pull-right sure" id="enableDeviceBtn" value="确定" >
                    <button type="button" class="btn btn-default pull-right cancels"  id="enableDeviceCancel">取消</button>
                </div>              
				   </form> 
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 
    <div class="modal fade" id="disableDeviceModel">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">终端设备停用</h4>
                </div>
                <div class="modal-body">
                   <form class="form-horizontal" name="disableDeviceForm"> 
				      <input type="hidden"  name="terminalDeviceNo" id="disableDeviceNo">
				      <input type="hidden"  name="isAvailable" value="0">
				        <label for="inputEmail3" class=" control-label wen" id="disableDeviceMemo"></label>
				         <div>
				            <label for="inputEmail3" class=" control-label reason">理由:</label>
				         </div> 
				          <div class="form-group">
	                            <div class="col-sm-8">
	                                 <textarea class="form-control textareas" name="availableMemo"></textarea>
	                            </div>
	                      </div>
	                      <div class="modal-footer">
                    <input type="button" class="btn btn-default pull-right sure" id="disableDeviceBtn" value="确定" >
                    <button type="button" class="btn btn-default pull-right cancels"  id="disableDeviceCancel">取消</button>
                </div>              
				   </form> 
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 
    
    <div class="modal fade" id="resetBlueToothNameModel">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">终端设备重置蓝牙名称</h4>
                </div>
                <div class="modal-body">
                   <form class="form-horizontal" name="resetBlueToothNameForm"> 
				      <input type="hidden"  name="deviceNo" >
				      蓝牙默认名称：<input type="text"  name="bluetoothName">
	                <div class="modal-footer">
                    <input type="button" class="btn btn-default pull-right sure" id="resetBlueToothNameBtn" value="确定" >
                    <button type="button" class="btn btn-default pull-right cancels"  id="resetBlueToothNameBtnCancel">取消</button>
                </div>              
				   </form> 
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 
    
    <script type="text/javascript">
    $(function(){
	  require.config({paths: {"device":"res/js/device/device_list"}});
		require(["device"], function (device){
			device.init();
		});  
	});
    $(function () {
        $(".datetimepicker").datetimepicker({
	        language: "zh-CN",
	         minView: "month",
            autoclose: true,
            todayBtn: true,
            minuteStep: 5,
            clearBtn: true,//清除按钮
            format: "yyyy-mm-dd"//日期格式
        });
    });
</script>
