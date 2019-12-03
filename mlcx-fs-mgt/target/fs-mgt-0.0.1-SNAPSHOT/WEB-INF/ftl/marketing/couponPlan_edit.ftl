<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="couponPlanEditFrom">
		<input type="hidden" name="planNo" value="${couponPlan.planNo}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">优惠券编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>标题：</label>
						</td>
						<td>
							<input class="form-control val" name="title" id="titleId" value="${couponPlan.title}"/>
							<span name="titleEdit"></span>
						</td>
						<td>
							<label class=" control-label key">子标题：</label>
						</td>
						<td>
							<input class="form-control val" name="subtitle" value="${couponPlan.subtitle}" />
							<span name="subtitleEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">优惠类型：</label>
						</td>
						<td>
							<select name="couponType" class="form-control val">
								 <option value="1" <#if couponPlan.couponType==1>selected="selected"</#if> >优惠券</option>
								 <option value="2" <#if couponPlan.couponType==2>selected="selected"</#if> >订单分享类优惠券</option>
							</select>
					<span name="couponTypeEdit"></span>
					    </td>
					    <td>
							<label class=" control-label key">优惠类型：</label>
						</td>
						<td>
						<select name="couponMethod" class="form-control val">
								 <option  value="1" <#if couponPlan.couponMethod==1>selected="selected"</#if> >折扣</option>
								 <option  value="2" <#if couponPlan.couponMethod==2>selected="selected"</#if> >直减</option>
							</select>
					<span name="couponMethodEdit"></span>
						</td>
					</tr>
					<tr>
						<td  class="form-group couponMethod-1" <#if couponPlan.couponMethod==2>hidden="hidden" </#if>>
							<label class=" control-label key"><span>*</span>折扣率：</label>
						</td>
						<td  class="form-group couponMethod-1" <#if couponPlan.couponMethod==2>hidden="hidden" </#if>>
							<input class="form-control val" name="discount"  value="${couponPlan.discount}"/>
						<span name="discountEdit"></span>
						</td>
						<td  class="form-group couponMethod-1" <#if couponPlan.couponMethod==2>hidden="hidden" </#if>>
							<label class=" control-label key"><span>*</span>封顶金额：</label>
						</td>
						<td  class="form-group couponMethod-1" <#if couponPlan.couponMethod==2>hidden="hidden" </#if>>
							<input class="form-control val" name="discountMaxAmount" value="${couponPlan.discountMaxAmount}"/>
						   <span name="discountMaxAmountEdit"></span>
						</td>
					</tr>
					<tr>
						<td  class="form-group couponMethod-2" <#if couponPlan.couponMethod==1>hidden="hidden" </#if>>
							<label class=" control-label key"><span>*</span>直减金额：</label>
						</td>
						<td  class="form-group couponMethod-2" <#if couponPlan.couponMethod==1>hidden="hidden" </#if>>
							<input class="form-control val" name="discountAmount"  value="${couponPlan.discountAmount}"/>
						    <span name="discountAmountEdit"></span>
						</td>
						<td  class="form-group couponMethod-2" <#if couponPlan.couponMethod==1>hidden="hidden" </#if>>
							<label class=" control-label key"><span>*</span>满减金额：</label>
						</td>
						<td  class="form-group couponMethod-2" <#if couponPlan.couponMethod==1>hidden="hidden" </#if>>
							<input class="form-control val" name="consumeAmount" value="${couponPlan.consumeAmount}"/>
							<span name="consumeAmountEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">优惠使用类型：</label>
						</td>
						<td>
							<select name="couponUseType" class="form-control val">
								<option  value="1" <#if couponPlan.couponUseType==1>selected="selected"</#if> >分时</option>
								<option  value="2" <#if couponPlan.couponUseType==2>selected="selected"</#if> >日租</option>
							</select>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>车型：</label>
						</td>
						<td>
							<input class="form-control val" name="carModelName" id="carModelName" readonly value="${couponPlan.carModelName}"/>
							<button class="page-button-style" type="button" id="checkEditCarBtn">车型列表</button>
				<input type="hidden" name="carModelId" id="carModelId"  value="${couponPlan.carModelId}"/> 
				<input type="hidden" name="backUpCarModelId"  value="${couponPlan.carModelId}"/>
				<input type="hidden" name="backUpCarModelName" value="${couponPlan.carModelName}"/> 
				<span name="carModelNameEdit"></span>
						</td>
					</tr>
					<tr>
							<td>
							<label class=" control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							<input class="form-control val" name="cityName" id="cityName" readonly value="${couponPlan.cityName}"/>
							<button class="page-button-style" type="button" id="checkEditCityBtn">城市列表</button> 	
				<input type="hidden" name="cityId" id="cityId" value="${couponPlan.cityId}">
				<input type="hidden" name="backUpCityId"  value="${couponPlan.cityId}"/>
				<input type="hidden" name="backUpCityName" value="${couponPlan.cityName}"/> 
				<span name="cityNameEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>发放数量：</label>
						</td>
						<td>
							<input class="form-control val" name="maxQuantity" value="${couponPlan.maxQuantity}"/>
							<span name="maxQuantityEdit"></span>
						</td>
					</tr>
					<tr>
					<td>
					<label class=" control-label key"><span>*</span>有效时间类型：</label>
					</td>
					<td>
					<input type="radio" name = "availableTime" value="1" <#if couponPlan.availableDays??>checked="checked"</#if>>天数
					<input type="radio" name = "availableTime" value="2" <#if !couponPlan.availableDays??>checked="checked"</#if>>日期				
					</td>
					<td class="form-group availableTime-1" <#if !couponPlan.availableDays??>hidden="hidden"</#if>>
					<label class=" control-label key"><span>*</span>有效天数：</label>
					</td>
					<td class="form-group availableTime-1" <#if !couponPlan.availableDays??>hidden="hidden"</#if>>
					<input class="form-control val" name="availableDays" value="${couponPlan.availableDays}"/>
					<span name="availableDaysEdit"></span>
					</td>
					
					<td class="availableTime-2" <#if couponPlan.availableDays??>hidden="hidden"</#if>>
							<label class=" control-label key"><span>*</span>有效日期：</label>
						</td>
						<td class="new-time-180423 clearfix availableTime-2" <#if couponPlan.availableDays??>hidden="hidden"</#if>>
							<input class="datetimepicker form-control val" name="vailableTime1" value="${couponPlan.vailableTime1?string('yyyy-MM-dd')}"/>
				 			<span class="pull-left">至</span>
				 			<input class="datetimepicker form-control val" name="vailableTime2" value="${couponPlan.vailableTime2?string('yyyy-MM-dd')}"/>
				 			<span name="vailableTime1Edit"></span>
				 			<span name="vailableTime2Edit"></span>
						</td>
					</tr>
					
					<tr class="add-car-last-tr">
						<td>
							<label class=" control-label key">图片：</label>
						</td>
						<td class="odd-td">
						<input name="photoUrl" type="hidden" value="${couponPlan.couponPlanPicUrl}"/>
						<div class="img-td-upload" style="background-image: url(${imagePath!''}/${couponPlan.couponPlanPicUrl});" id="couponPlanPicUrlImgEdit">
							<div id="editCouponPlanPicUrlButton" class="add-img-position">
								<h3 class="icon">+</h3>
								<h3 class="text">添加图片</h3>
							</div>
						</div>
						<span name="couponPlanPicUrlEdit"></span>
						</td>
						<td>
							<label class=" control-label reason key">备注：</label>
						</td>
						<td>
							<textarea class="form-control val" rows="6"  name="remark">${couponPlan.remark}</textarea>
							<span name="remarkEdit"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editCouponPlan" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeEditCouponPlan" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<!-- 弹出框-->
    <div class="modal fade" id="couponPlanPicUrlEditModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">图片上传</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="couponPlanPicUrlEditForm">
					<div class="form-group col-md-6">
                            <label class="col-md-3 control-label val">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control val" placeholder="" name="couponPlanPicUrl1" readonly>
                            </div>
                     </div>
                     <div class="form-group col-md-6">
                           <label class="col-md-3 control-label val">图片：</label>
                           <div class="col-md-8">
                                <div id="couponPlanPicUploaderEdit"><span name="couponPlanPicEditErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="editCouponPlanPicBtn" value="确定" style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    
  
<div class="modal fade" id="couponPlanModal" tabindex="-1" role="dialog">
	<div class="modal-dialog" style="width:750px;">
	   	<div class="modal-content">
	   		<div class="with-border">
					<div class="title-new-details">选择</div>
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
			    <div class="carRedPacketAddParkTool-bullet" id="parkToolss">
					</div>
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"couponPlanEdit":"res/js/marketing/couponPlan_edit"}});
		require(["couponPlanEdit"], function (couponPlanEdit){
			couponPlanEdit.init();
		});  
		
		$(".datetimepicker").datetimepicker({
            language: "zh-CN",
            minView: "month",
            autoclose: true,
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
		
		
		var config=new Object();
		config.uploadId="couponPlanPicUploaderEdit";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）,couponPlan_photo(活动图片)
		config.storePath = "couponPlan_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=500 * 1024;
		config.minSizeLimit=10* 1024;
		config.formName= "couponPlanPicUrlEditForm";
		//文件回显inputName
		config.inputName = "couponPlanPicUrl1";
		//错误提示span标签name
		config.spanName = "couponPlanPicEditErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});
    });
</script>