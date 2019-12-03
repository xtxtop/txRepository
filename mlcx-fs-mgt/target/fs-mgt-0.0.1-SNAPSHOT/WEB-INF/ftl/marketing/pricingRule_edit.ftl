<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="pricingRuleEditForm">
		<input type="hidden" name="orderCaculateType" value="${orderCaculateType}"/>
					 <input type="hidden" name="ruleNo" value="${pricingRule.ruleNo}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">计费规则调整</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>名称：</label>
						</td>
						<td>
							<input class="form-control val" name="ruleName" value="${pricingRule.ruleName}"/>
							<span name="ruleNameEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>工作日按时间计费：</label>
						</td>
						<td class="btn-btnA-con">
							 <input class="form-control val" name="priceOfMinute" value="${pricingRule.priceOfMinute}"/>
							<div class="btn-btnA">元/分钟</div>
							<span name="priceOfMinuteEdit"></span>
						</td>
					</tr>
					<#if pricingRule.cencorStatus==1>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							 <select name="cityId" class="form-control val">
									 <option  value="${pricingRule.cityId}" >
				                            ${pricingRule.cityName}
				                     </option>
								</select>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>车辆品牌：</label>
						</td>
						<td>
						<select name="carBrand" class="form-control val" disabled>
								 <#list carBrands as carBrand>
									 <option  value="${carBrand.carBrandId}" <#if pricingRule.carModelName?contains(carBrand.carBrandName)>selected</#if>>
				                            ${carBrand.carBrandName}
				                     </option>
				                 </#list>  
								</select>
								<span name="carBrandEdit"></span>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>车辆型号：</label>
						</td>
						<td>
							 <select name="carModel" class="form-control val" disabled>
								 <#list carModels as carModel>
									 <option  value="${carModel.carSeriesId}" <#if pricingRule.carModelName?contains(carModel.carSeriesName)>selected</#if>>
				                            ${carModel.carSeriesName}
				                     </option>
				                 </#list>  
								</select>
								<span name="carModelEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>工作日按里程计费：</label>
						</td>
						<td class="btn-btnA-con">
							<input class="form-control val" name="priceOfKm" value="${pricingRule.priceOfKm}"/>
							<div class="btn-btnA">元/公里</div>
							<span name="priceOfKmEdit"></span>
						</td>
					</tr>
					<#else>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							  <select name="cityId" class="form-control val">
								 <#list cities as citie>
									 <option  <#if pricingRule.cityId==citie.dataDictItemId>selected</#if> value="${citie.dataDictItemId}" >
				                            ${citie.itemValue}
				                     </option>
				                 </#list>  
								</select>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>车辆品牌：</label>
						</td>
						<td>
							<select name="carBrand" class="form-control val" >
								  <#list carBrands as carBrand>
									 <option  value="${carBrand.carBrandId}" <#if pricingRule.carModelName?contains(carBrand.carBrandName)>selected</#if>>
				                            ${carBrand.carBrandName}
				                     </option>
				                 </#list>
								</select>
							<span name="carBrandEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>车辆型号：</label>
						</td>
						<td>
							 <select name="carModel" class="form-control val">
								<#list carModels as carModel>
									 <option  value="${carModel.carSeriesId}" <#if pricingRule.carModelName?contains(carModel.carSeriesName)>selected</#if>>
				                            ${carModel.carSeriesName}
				                     </option>
				                 </#list>  
								</select>
							<span name="carModelEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>工作日按里程计费：</label>
						</td>
						<td class="btn-btnA-con">
							<input class="form-control val" name="priceOfKm" value="${pricingRule.priceOfKm}"/>
							<div class="btn-btnA">元/公里</div>
							<span name="priceOfKmEdit"></span>
						</td>
					</tr>
					</#if>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>工作日计费封顶：</label>
						</td>
						<td class="btn-btnA-con">
							<input class="form-control val" name="billingCapPerDay" value="${pricingRule.billingCapPerDay}"/>
							<div class="btn-btnA">元/天</div>
							<span name="billingCapPerDayEdit"></span>
						</td>
						<#if orderCaculateType==1>
							<td>
							<label class=" control-label key"><span>*</span>周末按时间计费：</label>
						</td>
						<td>
							<input class="form-control val" name="priceOfMinuteOrdinary" value="${pricingRule.priceOfMinuteOrdinary}"/>
							<div>元/分钟</div>
							<span name="priceOfMinuteOrdinaryEdit"></span>
						</td>
					</tr>
					
					<tr>
						
						<td>
							<label class=" control-label key"><span>*</span>周末按里程计费：</label>
						</td>
						<td class="btn-btnA-con">
							<input class="form-control val" name="priceOfKmOrdinary" value="${pricingRule.priceOfKmOrdinary}"/>
							<div class="btn-btnA">元/公里</div>
							<span name="priceOfKmOrdinaryEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>周末计费封顶：</label>
						</td>
						<td class="btn-btnA-con">
							<input class="form-control val" name="billingCapPerDayOrdinary" value="${pricingRule.billingCapPerDayOrdinary}"/>
							<div class="btn-btnA">元/天</div>
							<span name="billingCapPerDayOrdinaryEdit"></span>
						</td>
						</#if>
						<td>
							<label class=" control-label key"><span>*</span>起步价：</label>
						</td>
						<td class="btn-btnA-con">
							 <input class="form-control val" name="baseFee" value="${pricingRule.baseFee}"/>
							<div class="btn-btnA">元</div>
							<span name="baseFeeEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>不计免赔费（元/次）：</label>
						</td>
						<td class="btn-btnA-con">
							<input class="form-control" name="regardlessFranchise" value="${pricingRule.regardlessFranchise}"/>
							<div class="btn-btnA">元/次</div>
							<span name="regardlessFranchiseEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>保险费（元/天）：</label>
						</td>
						<td class="btn-btnA-con">
							<input class="form-control" name="insuranceAmount" value="${pricingRule.insuranceAmount}"/>
						 <div class="btn-btnA">元/天</div>
						 <span name="insuranceAmountEdit"></span>
						</td>
					</tr>
					
					<tr>
						<#if pricingRule.cencorStatus==1>
						<td>
							<label class=" control-label key"><span>*</span>集团名称：</label>
						</td>
						<td>
						<select name="companyId" class="form-control val" disabled >
							  <option value="" <#if pricingRule.companyId==null||pricingRule.companyId==''>selected</#if>>平台</option>
								 <#list companyList as company>
									 <option  value="${company.companyId}" <#if pricingRule.companyId==company.companyId>selected</#if>>
				                            ${company.companyName}
				                     </option>
				                 </#list>  
								</select>
						<span name="companyIdEdit"></span>
						</td>
						<#else>
						<td>
							<label class=" control-label key"><span>*</span>集团名称：</label>
						</td>
						<td>
							<select name="companyId" class="form-control val">
							  <option value="" <#if pricingRule.companyId==null||pricingRule.companyId==''>selected</#if>>平台</option>
								 <#list companyList as company>
									 <option  value="${company.companyId}" <#if pricingRule.companyId==company.companyId>selected</#if>>
				                            ${company.companyName}
				                     </option>
				                 </#list>  
								</select>
							<span name="companyIdEdit"></span>
						</td>
						<!--<input type="hidden" name="discount" value="1"/>-->
						</#if>
						<td>
							<button type="button" class="btn btn-info"
				id="addPeakhousrDay">添加高峰时段</button>
						</td>
						<td>
							<label class=""> <input type="radio"
					name="isOverdue" id="overdueId" value="0" checked>进行中
				</label> <label class=""> <input type="radio"
					name="isOverdue" id="isOverdueId" value="1">已过期
				</label>
						</td>
					</tr>
						<#if orderCaculateType==1>
					<tr>
						<td>
							<button type="button" class="btn btn-info"  id="customizedDateModalDay">添加自定义计费规则</button>
						</td>
						<td>
							<label class="">
						<input type="radio" name="isOverdue" id="overdueId" value="0" checked>进行中
					</label>
					 <label class="">
							<input type="radio" name="isOverdue" id="isOverdueId" value="1" >已过期
					</label>
						</td>
					</tr>
					</#if>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="2"><button type="button" id="EditPricingRule" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
      <td colspan="2"><button type="button" id="closeEditPricingRule" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
			<div class="modal-dialog " style="width:377px;">
	<#if orderCaculateType==1>
	   	<div class="modal-content">
	       <#--<div class="col-xs-14">-->
	       <div class="">
		       <!--定义操作列按钮模板-->
		       <script id="pricingRuleCustomizedDateTplAdd" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <#--<div class="box">-->
			   	   <div class="box-body pcds" style="width: 1200px; margin-top: 75px; float: left; margin-left: -400px;">
					   <table id="pricingRuleCustomizedDateListAdd" class="table table-hover" width="100%" style=""></table>
				   </div><!-- /.box-body -->
			   <#--</div><!-- /.box &ndash;&gt;-->
		   </div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
	</#if>
                <!-- 高峰时段列表 -->
				<div class="">
					<!--定义操作列按钮模板-->
					<script id="peakHousrModalList" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
				<div class="box-body pcds"
					style="width: 1200px; margin-top: 75px; float: left; margin-left: -390px;">
					<table id="peakHousrModalListTable" class="table table-hover"
						width="100%" style=""></table>
				</div>
			 </div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
</div>
	</form>
</div>
<div class="modal fade" id="customizedDateModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">自定义日期计费</h4>
                </div>
                <div class="modal-body hzlist">
                   <form class="form-horizontal" name="customizedDateForm"> 
                   		 <input type="hidden"  name="customizedId">
				      	 <input type="hidden"  name="ruleNo" value="${pricingRule.ruleNo}">
				      	 <input type="hidden"  name="cityId" value="${pricingRule.cityId}">
				      	 <input type="hidden"  name="cityName" value="${pricingRule.cityName}">
				      	 <input type="hidden"  name="carModelName" value="${pricingRule.carModelName}">
				      	 <input type="hidden"  name="companyId" value="${pricingRule.companyId}">
				         <div class="form-group col-md-12">
				         	 <label class="col-sm-4 control-label key"><span>*</span>自定义日期按时间计费：</label>
                             <div class="col-sm-4">
                                 <input class="form-control val pdd" name="priceOfMinuteCustomized">
                             </div>
                             <div>元/分钟</div>
                             <div><span name="priceOfMinuteCustomizedError"></span></div>
	                      </div>
	                      <div class="form-group col-md-12">
				         	 <label class="col-sm-4 control-label key"><span>*</span>自定义日期按公里计费：</label>
                             <div class="col-sm-4">
                                 <input class="form-control val pdd" name="priceOfKmCustomized">
                             </div>
                             <div>元/公里</div>
                             <div><span name="priceOfKmCustomizedError"></span></div>
	                      </div>
	                      <div class="form-group col-md-12">
				         	 <label class="col-sm-4 control-label key"><span>*</span>自定义日期计费封顶：</label>
                             <div class="col-sm-4">
                                 <input class="form-control val pdd" name="billingCapPerDayCustomized">
                             </div>
                             <div>元/天</div>
                             <div><span name="billingCapPerDayCustomizedError"></span></div>
	                      </div>
	                      <div class="form-group col-md-12">
				         	 <label class="col-sm-4 control-label key"><span>*</span>自定义日期：</label>
                             <div class="col-sm-4">
                                 <input class="form-control val datepicker pdd" name="customizedDateStr" readOnly="readOnly">
                             </div>
                             <div><span name="customizedDateError"></span></div>
	                      </div>
				   </form> 
				   <div class="modal-footer">
	                   <input type="button" class="btn btn-default pull-right sure" id="customizedDateBtn" value="确定" >
	                   <button type="button" class="btn btn-default pull-right cancels"  id="customizedDateCancel">取消</button>
               	   </div>    
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
   <div class="modal fade" id="peakHousrModalDiv">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">高峰时段</h4>
					</div>
					<div class="modal-body gflist">
						<form class="form-horizontal" name="peakHousrModalForm">
							<input type="hidden" name="peakHourId" value="" id="peakHourId">
							<input type="hidden" name="ruleNo" value="${pricingRule.ruleNo}">
							<div class="form-group col-md-12">
								<label class="col-sm-4 control-gf-label key"><span
									style="color: red">*</span>高峰时段开始时间：</label>
								<div class="col-sm-4">
									<input class="form-control-input val pdd" name="peakStartHours"
										placeholder="输入格式（按小时比如8或20）">
								</div>
								<div>
									<span name="peakStartHoursAdd" style="margin-left: 15px;"></span>
								</div>
							</div>
							<div class="form-group col-md-12">
								<label class="col-sm-4 control-gf-label key"><span
									style="color: red">*</span>高峰时段结束时间：</label>
								<div class="col-sm-4">
									<input class="form-control-input val pdd" name="peakEndHours"
										placeholder="输入格式（按小时比如8或20）">
								</div>
								<div>
									<span name="peakEndHoursAdd" style="margin-left: 15px;"></span>
								</div>
							</div>
							<div class="form-group col-md-12">
								<label class="col-sm-4 control-gf-label key"><span
									style="color: red" style="color: red">*</span>高峰时段按分钟计费：</label>
								<div class="col-sm-4">
									<input class="form-control-input val pdd" name="priceOfMinute" placeholder="请输入高峰时段计费规则">
								</div>
								<div>
									<span name="priceOfMinuteAdd" style="margin-left: 15px;"></span>
								</div>
							</div>
							<div class="form-group col-md-12">
								<label class="col-sm-4 control-gf-label key"><span
									style="color: red">*</span>高峰时段按里程计费：</label>
								<div class="col-sm-4">
									<input class="form-control-input val pdd" name="priceOfKm" placeholder="请输入高峰时段计费规则">
								</div>
								<div>
									<span name="priceOfKmAdd" style="margin-left: 15px;"></span>
								</div>
							</div>
						</form>
						<div class="modal-footer">
							<input type="button" class="btn btn-default pull-right sure"
								id="peakHousrEditBtn" value="确定">
							<button type="button" class="btn btn-default pull-right cancels"
								id="peakHousrEditCancel">取消</button>
						</div>
					</div>

				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"pricingRuleEdit":"res/js/marketing/pricingRule_edit"}});
		require(["pricingRuleEdit"], function (pricingRuleEdit){
			pricingRuleEdit.init();
		});  
	});    
      $(function () {
        $("input[name='customizedDateStr']").datepicker({
            language: "zh-CN",
            startDate: new Date(),
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            multidate:true,
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        }); 
    });
</script>