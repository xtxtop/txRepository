<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="pricingPackageCensorEditFrom">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">套餐产品审核</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>套餐编号：</label>
						</td>
						<td>
							<input class="form-control val" name="packageNo" value="${pricingPackage.packageNo}" readonly/>
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
					<#if pricingPackage>
						<#if pricingPackage.minutes??>
						<td>
							<label class=" control-label key"><span>*</span>时长：</label>
						</td>
						<td>
							<label class="control-label val">${pricingPackage.minutes}
								  分钟
								  </label>
						</td>
						</#if>
						<#if pricingPackage.packAmount??>
							<td>
								<label class=" control-label key"><span>*</span>金额：</label>
							</td>
							<td>
								<label class="control-label val">${pricingPackage.packAmount}
								  元
								  </label>
							</td>
						</#if>
						</#if>
						<td>
							<label class=" control-label key"><span>*</span>销售价：</label>
						</td>
						<td>
							<label class="control-label val">${pricingPackage.price}元</label>
						</td>
					</tr>
					
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>有效天数：</label>
						</td>
						<td>
							<label class="control-label val">${pricingPackage.availableDays}
							天
							</label>
						</td>
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
					</tr>
					<tr>
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
						<td>
							<label class=" control-label key"><span>*</span>上/下架操作人：</label>
						</td>
						<td>
							<label class="control-label val">
								 ${sysUser.userName}
							</label>
						</td>
					</tr>
					<tr>
						
						<td>
							<label class=" control-label key"><span>*</span>审核时间：</label>
						</td>
						<td>
							<label class="control-label val">
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
								 ${pricingPackage.createTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>更新时间：</label>
						</td>
						<td>
							<label class="control-label val">
								 ${pricingPackage.updateTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>审核状态：</label>
						</td>
						<td>
							<label class="radio-inline">
	                               <input type="radio" name="cencorStatus" checked="true" value="1"> 已审核
	                            </label>
							<label class="radio-inline">
	                               <input type="radio" name="cencorStatus"  value="2">不通过
                                </label>
						</td>
						<td>
							<label class=" control-label key reason"><span>*</span>审核备注：</label>
						</td>
						<td>
							<textarea class="form-control val" name="cencorMemo"></textarea>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addCencorpricingPackage" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeCencorpricingPackage" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"pricingPackageEdit": "res/js/marketing/pricingPackage_edit"
			}
		});
		require(["pricingPackageEdit"], function(pricingPackageEdit) {
			pricingPackageEdit.init();
		});
	});
	$(function() {
		$(".datepicker").datepicker({
			language: "zh-CN",
			autoclose: true, //选中之后自动隐藏日期选择框
			clearBtn: true, //清除按钮
			todayBtn: true, //今日按钮
			format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
		$(".datetimepicker").datetimepicker({
			language: "zh-CN",
			autoclose: true,
			todayBtn: true,
			minuteStep: 5,
			format: "yyyy-mm-dd hh:ii:ss" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
	});
</script>