<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="pricingPackageAddFrom">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">套餐产品新增</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>套餐名称：</label>
						</td>
						<td>
							<input class="form-control val" name="packageName" placeholder="请输入套餐名称"/>
							<span name="packageName"></span>
						</td>
							<td>
								<label class="control-label key"><span>*</span>套餐种类：</label>
							</td>
							<td>
								<input type="radio" disabled="disabled" name="packageKind" value="2" checked="checked">金额套餐
							</td>
					</tr>
					<tr>
						<td>
							<label class="control-label key"><span>*</span>套餐类型：</label>
						</td>
						<td>
							<select name="packageType" class="form-control val">
								<option value="1">赠送类套餐</option>
								<option value="2">销售套餐</option>
								<option value="4">订单分享类套餐</option>
								<option value="5">红包赠送套餐</option>
							</select>
						</td>

						<td>
							<label class="control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							<select name="cityId" class="form-control val">
								<#list cities as citie>
									<option value="${citie.dataDictItemId}">
										${citie.itemValue}
									</option>
								</#list>
							</select>
						</td>

					</tr>
					<tr>
						<td>
							<label class="control-label key"><span>*</span>销售价(元)：</label>
						</td>
						<td>
							<input class="form-control val" name="price" placeholder="请输入销售价"/>
							<span name="price"></span>
						</td>
						<td>
							<label class="control-label key"><span>*</span>金额(元)：</label>
						</td>
						<td id="moneyDiv">
								<input class="form-control val" name="packAmount" maxlength="6" placeholder="请输入金额"/>
								<span name="packAmount"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>是否限制购买次数：</label>
						</td>
						<td>
							<input type="radio" checked name="isLimitTimes" value="1">是
							<input type="radio" name="isLimitTimes" value="0">否
							<span name="isLimitTimes"></span>
						</td>
						<td colspan="2"></td>

					</tr>
					<tr id="buyTimesDiv">
					<td>
							<label class="control-label key"><span>*</span>限制购买次数(次)：</label>
						</td>
						<td>
							 <input class="form-control val" name="buyTimes" maxlength="6"placeholder="请输入限制购买次数"/>
							 <span name="buyTimes"></span>
						</td>
						<td colspan="2"></td>
					</tr>
					<tr id="availableDaysDiv" style="display:none;">
						<td>
							<label class="control-label key"><span>*</span>&nbsp;&nbsp;有效天数(天)：</label>
						</td>
                        <td>
                        	<input class="form-control val" name="availableDays" maxlength="3"/>
                        	<span name="availableDays"></span>
                        </td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addpricingPackage" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeaddpricingPackage" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"pricingPackageAdd":"res/js/marketing/pricingPackage_add"}});
		require(["pricingPackageAdd"], function (pricingPackageAdd){
			pricingPackageAdd.init();
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