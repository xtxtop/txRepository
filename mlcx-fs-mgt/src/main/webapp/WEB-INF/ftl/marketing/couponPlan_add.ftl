<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
<meta name="" http-equiv="X-UA-compatible" content="IE=8,chorm=1"/ie="edge">
<div class="container-fluid backgroundColor">
	<form name="couponPlanAddFrom">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增优惠券方案</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>标题：</label>
						</td>
						<td>
							<input class="form-control val" name="title" id="titleId" placeholder="请输入标题"/>
							<span name="titleAdd"></span>
						</td>
						<td>
							<label class=" control-label key">子标题：</label>
						</td>
						<td>
							<input class="form-control val" name="subtitle" placeholder="请输入子标题"/>
							<span name="subtitleAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">优惠类型：</label>
						</td>
						<td>
							<select name="couponType" class="form-control val">
						 <option  value="1" >优惠券</option>
						 <option  value="2" >订单分享类优惠券</option>
					</select>
					<span name="couponTypeAdd"></span>
					    </td>
					    <td>
							<label class=" control-label key">优惠类型：</label>
						</td>
						<td>
						<select name="couponMethod" class="form-control val">
						 <option  value="1" >折扣</option>
						 <option  value="2" >直减</option>
					</select>
					<span name="couponMethodAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">优惠使用类型：</label>
						</td>
						<td>
							<select name="couponUseType" class="form-control val">
						 		<option  value="1" >分时</option>
						 		<option  value="2" >日租</option>
							</select>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>发放数量：</label>
						</td>
						<td>
							<input class="form-control val" name="maxQuantity" placeholder="请输入发放数量"/>
							<span name="maxQuantityAdd"></span>
						</td>
					</tr>
					<tr class="form-group couponMethod-1">
							<td>
							<label class=" control-label key"><span>*</span>折扣率：</label>
						</td>
						<td>
							<input class="form-control placeholder="折扣率" val" name="discount" placeholder="请输入折扣率"/>
							<span name="discountAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>封顶金额：</label>
						</td>
						<td>
							<input class="form-control val" placeholder="封顶金额" name="discountMaxAmount" placeholder="请输入封顶金额"/>
							<span name="discountMaxAmountAdd"></span>
						</td>
					</tr>
					<tr  class="form-group  couponMethod-2"   hidden="hidden">
					<td>
					<label class=" control-label key">直减金额：</label>
					</td>
					<td>
					<input class="form-control val" placeholder="直减金额" name="discountAmount" placeholder="请输入直减金额"/>
					<span name="discountAmountAdd"></span>
					</td>
					<td>
					<label class=" control-label key"><span>*</span>满减金额：</label>
					</td>
					<td>
					<input class="form-control val" placeholder="满减金额" name="consumeAmount" placeholder="请输入满减金额"/>
					<span name="consumeAmountAdd"></span>
					</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>车型：</label>
						</td>
						<td>
							<input class="form-control val" name="carModelName" id="carModelName" readonly placeholder="请选择车型"/>
							<input type="hidden" name="carModelId"/>
				<input type="hidden" name="backUpCarModelId"/>
				<input type="hidden" name="backUpCarModelName"/> 
				<button type="button" class="btn btn-default" id="checkAddCarBtn">车型列表</button>
				<span name="carModelNameAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							<input class="form-control val" name="cityName" id="cityName" readonly placeholder="请选择城市"/>
							<button type="button" class="btn btn-default" id="checkAddCityBtn">城市列表</button>
				<input type="hidden" name="cityId" id="cityId">
				<input type="hidden" name="backUpCityId"/>
				<input type="hidden" name="backUpCityName"/> 
				<span name="cityNameAdd"></span>
						</td>
					</tr>
					
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>有效时间类型：</label>
						</td>
						<td>
							<input type="radio" name = "availableTime" value="1" checked="checked">天数
							<input type="radio" name = "availableTime" value="2" >日期				
						</td>
						<td class="availableTime-1">
							<label class=" control-label key"><span>*</span>有效天数：</label>
						</td>
						<td class="availableTime-1">
							<input class="form-control val" name="availableDays" placeholder="请输入有效天数"/>
							<span name="availableDaysAdd"></span>
						</td>
						
						<td class="availableTime-2" hidden="1">
							<label class=" control-label key"><span>*</span>有效日期：</label>
						</td>
						<td class="availableTime-2 new-time-180423 clearfix" hidden="1">
							<input id="datetimepicker" placeholder="开始时间" class="val form-control pull-left" name="vailableTime1" />
				 			<span class="pull-left">至</span>
				 			<input id="datetimepicker1" placeholder="结束时间" class="val form-control pull-left" name="vailableTime2" />
				 			<span name="vailableTime1Add"></span>
				 			<span name="vailableTime2Add"></span>
						</td>
					</tr>
					
					<tr class="add-car-last-tr">
						<td>
							<label class=" control-label key">图片：</label>
						</td>
						<td class="odd-td">
						<input name="photoUrl" type="hidden"/>
						<div class="img-td-upload" id="couponPlanPicUrlImg">
							<div id="addCouponPlanPicUrlButton" class="add-img-position">
								<h3 class="icon">+</h3>
								<h3 class="text">添加图片</h3>
							</div>
						</div>
						<span name="couponPlanPicUrlAdd"></span>
						</td>
						<td>
							<label class=" control-label reason key">备注：</label>
						</td>
						<td>
							<textarea class="form-control val" rows="1"  name="remark" placeholder="请填写备注"></textarea>
							<span name="remarkAdd"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addCouponPlan" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeAddCouponPlan" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<!-- 弹出框-->
    <div class="modal fade" id="couponPlanPicUrlAddModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">图片上传</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="couponPlanPicUrlAddForm">
					<div class="form-group ">
                            <label class="col-md-3 control-label val">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control val" placeholder="" name="couponPlanPicUrl1" readonly>
                            </div>
                     </div>
                     <div class="form-group ">
                           <label class="col-md-3 control-label val">图片：</label>
                           <div class="col-md-8">
                                <div id="couponPlanPicUploader"><span name="couponPlanPicErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="addCouponPlanPicBtn" value="确定">
					</div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
<div class="modal fade" id="couponPlanModal" tabindex="-1" role="dialog">
	<div class="modal-dialog" style="width:750px;">
	   	<div class="modal-content">
	   		<div class="with-border">
					<div class="title-new-details">选择车型列表</div>
				</div>
		       <!--定义操作列按钮模板-->
		       <script id="couponPlanBtnTpl" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
			        <div class="box-body box-body-change-padding">
			         <table id="couPonPlanLists" class="table table-bordered table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
			        <div class="carRedPacketAddParkTool-bullet" id="couponPlanToolss">
					</div>
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
    
    
    
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"couponPlanAdd":"res/js/marketing/couponPlan_add"}});
		require(["couponPlanAdd"], function (couponPlanAdd){
			couponPlanAdd.init();
		});  
		
		$("#datetimepicker").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            minView: "month",
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
        $("#datetimepicker1").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            minView: "month",
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
		
		var config=new Object();
		config.uploadId="couponPlanPicUploader";
		config.storePath = "couponPlan_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=500 * 1024;
		config.minSizeLimit=10* 1024;
		config.formName= "couponPlanPicUrlAddForm";
		//文件回显inputName
		config.inputName = "couponPlanPicUrl1";
		//错误提示span标签name
		config.spanName = "couponPlanPicErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});
    });
</script>