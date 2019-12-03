<meta charset="utf-8">
<div class="container-fluid backgroundColor">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">套餐产品详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>套餐编号：</label>
						</td>
						<td>
							<label class="control-label val">${pricingPackage.packageNo}</lable>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>套餐名称：</label>
						</td>
						<td>
							<label class="control-label val">${pricingPackage.packageName}</lable>
							</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>套餐类型：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if pricingPackage.packageType==1>赠送类套餐</#if>
							<#if pricingPackage.packageType==2>销售套餐</#if>
							<#if pricingPackage.packageType==4>订单分享类套餐</#if>
							<#if pricingPackage.packageType==5>红包赠送套餐</#if>
							</lable>
						</td>

						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							  <label class="control-label val">${pricingPackage.cityName}</lable>
						</td>

					</tr>

					
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>销售价：</label>
						</td>
						<td>
							 <label class="control-label val">${pricingPackage.price}元</label>
						</td>
							<td>
								<label class=" control-label key"><span></span>时长：</label>
							</td>
							<td>
								<label class="control-label val">${pricingPackage.minutes}
							  分钟
							  </label>
							</td>
						
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>套餐每日抵扣上限：</label>
						</td>
						<td>
							<label class="control-label val">${pricingPackage.deductionCeiling}
							  分钟
							  </label>
						</td>
						<td>
							<label class=" control-label key"><span></span>有效天数：</label>
						</td>
						<td>
							<label class="control-label val">${pricingPackage.availableDays}
							天
							</label>
						</td>
					</tr>
					<tr>
							<td>
								<label class=" control-label key"><span></span>上下架状态：</label>
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
							<label class=" control-label key"><span></span>下架时间：</label>
						</td>
						<td>
							<label class="control-label val">
							 ${pricingPackage.availabelUpdateTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
						</td>
						<#else>
							<td>
								<label class=" control-label key"><span></span>上架时间：</label>
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
							<label class=" control-label key"><span></span>上/下架操作人：</label>
						</td>
						<td>
							<label class="control-label val">
								 ${sysUser.userName}
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>审核状态：</label>
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
							<label class=" control-label key"><span></span>审核时间：</label>
						</td>
						<td>
							<label class="control-label val">
								 ${pricingPackage.cencorTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>审核人：</label>
						</td>
						<td>
							<label class="control-label val">
								 ${sysUser.userName}
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>创建时间：</label>
						</td>
						<td>
							<label class="control-label val">
								 ${pricingPackage.createTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>更新时间：</label>
						</td>
						<td>
							<label class="control-label val">
								 ${pricingPackage.updateTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align:center">
						<td colspan="4"><button type="button" id="closeViewpricingPackage" class="btn-new-type-edit">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
          
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