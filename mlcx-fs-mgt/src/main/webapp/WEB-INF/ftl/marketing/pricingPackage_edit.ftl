<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="pricingPackageEditFrom">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">套餐产品编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>套餐编号：</label>
						</td>
						<td>
							<input class="form-control val" name="packageNo" value="${pricingPackage.packageNo}" readonly style="background-color: white"/>
						</td>	
							<td>
								<label class=" control-label key"><span>*</span>套餐名称：</label>
							</td>
							<td>
								 <input class="form-control val" name="packageName" value="${pricingPackage.packageName}"/>
								 <span name="packageName"></span>
							</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>套餐类型：</label>
						</td>
						<td>
							<select name="packageType" class="form-control val" disabled style="background-color: white">
									<option <#if pricingPackage.packageType==1>selected</#if> value="1">赠送类套餐</option>
									<option <#if pricingPackage.packageType==2>selected</#if> value="2">销售套餐</option>
									<option <#if pricingPackage.packageType==4>selected</#if> value="3">订单分享类套餐</option>
									<option <#if pricingPackage.packageType==5>selected</#if> value="5">红包赠送套餐</option>
								</select>
						</td>

						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							<select name="cityId" class="form-control val" disabled style="background-color: white">
								 <#list cities as citie>
									 <option  <#if pricingPackage.cityId==citie.dataDictItemId>selected</#if> value="${citie.dataDictItemId}" >
				                            ${citie.itemValue}
				                     </option>
				                 </#list>  
								</select>
						</td>

					</tr>
					<tr>
						<td class="even-td">
							<label class=" control-label key"><span>*</span>销售价：</label>
						</td>
						<td class="btn-btnA-con odd-td">
							<input class="form-control val" name="price" value="${pricingPackage.price}" readonly style="cursor:not-allowed"/>
							<div class="btn-btnA">元</div>
							<span name="price"></span>
						</td>
						<#if pricingPackage>
						<#if pricingPackage.packAmount??>
						<input type="hidden" name="packageKind" value="2">
						<td class="even-td">
							<label class=" control-label key"><span>*</span>金额：</label>
						</td>
						<td class="btn-btnA-con odd-td">
							<#if pricingPackage.packageType==5>
								 ${pricingPackage.packAmount}  
								<#else>
								<input class="form-control val" name="packAmount" maxlength="6" value="${pricingPackage.packAmount}"/>
								</#if>
							<div class="btn-btnA">元</div>
							<span name="packAmount"></span>
						</td>
						</#if>
						</#if>
					</tr>
					<#if pricingPackage>
					<#if pricingPackage.minutes??>
					<input type="hidden" name="packageKind" value="1">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>时长：</label>
						</td>
						<td>
							<input class="form-control val" name="minutes" maxlength="6"  value="${pricingPackage.minutes}" readonly style="background-color: white"/>
							<div>分钟</div>
							<span name="minutes" ></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>套餐每日抵扣上限：</label>
						</td>
						<td class="btn-btnA-con">
							 <input class="form-control val" name="deductionCeiling" maxlength="6" value="${pricingPackage.deductionCeiling}"/>
							 <div class="btn-btnA">分钟</div>
							 <span name="deductionCeiling"></span>
						</td>
					</tr>
					</#if>	
					</#if>
					<#if pricingPackage.minutes??>
						<tr>
							<td>
								<label class=" control-label key"><span>*</span>有效天数：</label>
							</td>
							<td>
								<input class="form-control val" name="availableDays" maxlength="3"  value="${pricingPackage.availableDays}"/>
								<div>天</div>
								<span name="availableDays"></span>
							</td>
							<td></td>
							<td></td>
						</tr>
						</#if>
						<#if pricingPackage.isAvailable==0>	
							<tr>
								<td>
									<label class=" control-label key"><span>*</span>限制购买次数：</label>
								</td>
								<td class="btn-btnA-con">
								<div id="buyTimesDivEidt"<#if pricingPackage.isLimitTimes==0>style="display:none"</#if>>
							 <input class="form-control val" name="buyTimes" value="${pricingPackage.buyTimes}" maxlength="6"/>
							<div class="btn-btnA">次</div>
							<span name="buyTimesEdit"></span>
						</div>
								</td>
								<td>
									<label class=" control-label key"><span>*</span>是否限制购买次数：</label>
								</td>
								<td>
									<input type="radio" class="isLimitTimesEdit" name="isLimitTimes"  value="1" <#if pricingPackage.isLimitTimes=1>checked="checked"</#if>/>是
								<input type="radio" class="isLimitTimesEdit" name="isLimitTimes" value="0" <#if pricingPackage.isLimitTimes=0>checked="checked"</#if>/>否
								<span name="isLimitTimesEdit"></span>
								</td>
							</tr>
							</#if>
							<tr>
								<td>
									<label class=" control-label key"><span>*</span>上下架状态：</label>
								</td>
								<td>
									<label class="control-label val">
							<#if pricingPackage.isAvailable==0>
							下架
							<#else>
							上架
							</#if>
							</label>
								</td>
								 <#if pricingPackage.isAvailable==0>
								<td>
									<label class=" control-label key"><span>*</span>下架时间：</label>
								</td>
								<td>
									<label class="control-label val">
							 ${pricingPackage.availabelUpdateTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
								</td>
								<#else>
								<td>
									<label class=" control-label key"><span>*</span>上架时间：</label>
								</td>
								<td>
									<label class="control-label val">
								 ${pricingPackage.availabelUpdateTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
								</td>
								</#if>
							</tr>
							<tr>
								<td>
									<label class=" control-label key"><span>*</span>上/下架操作人：</label>
								</td>
								<td>
									<label class="control-label val">
								 ${sysUser.userName}
							</label>
								</td>
								<td>
									<label class=" control-label key"><span>*</span>审核状态：</label>
								</td>
								<td>
									<label class="control-label val">
							<#if pricingPackage.cencorStatus==0>
							未审核
							</#if>
							<#if pricingPackage.cencorStatus==1>
							已审核
							</#if>
							<#if pricingPackage.cencorStatus==2>
							未通过
							</#if>
							</label>
								</td>
							</tr>
							<tr>
								<td>
									<label class=" control-label key"><span>*</span>审核时间：</label>
								</td>
								<td>
									<label class="control-label val">
									<#if pricingPackage.cencorTime??>
									${pricingPackage.cencorTime?string('yyyy-MM-dd HH:mm:ss')}	
									</#if>
								 
							</label>
								</td>
								<td>
									<label class=" control-label key"><span>*</span>审核人：</label>
								</td>
								<td>
									<label class="control-label val">
								 ${sysUser.userName}
							</label>
								</td>
							</tr>
							<tr>
								<td>
									<label class=" control-label key"><span>*</span>创建时间：</label>
								</td>
								<td>
									<label class="control-label val">
									<#if pricingPackage.createTime??>
										${pricingPackage.createTime?string('yyyy-MM-dd HH:mm:ss')}
									</#if>
								 	
							</label>
								</td>
								<td>
									<label class=" control-label key"><span>*</span>更新时间：</label>
								</td>
								<td>
									<label class="control-label val">
									<#if pricingPackage.updateTime??>
										${pricingPackage.updateTime?string('yyyy-MM-dd HH:mm:ss')}
									</#if>
								 		
							</label>
								</td>
							</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editpricingPackage" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeeditpricingPackage" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"pricingPackageEdit":"res/js/marketing/pricingPackage_edit"}});
		require(["pricingPackageEdit"], function (pricingPackageEdit){
			pricingPackageEdit.init();
		});  
	});    
      $(function () {
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
         $(".datetimepicker").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd hh:ii:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>