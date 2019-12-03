<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="carIllegalAddForm" class="form-horizontal">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">违章新增</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
				     <tr>
						<td  style="text-align:left !important;" colspan="4"><span style="color:#ff0000;">*</span>输入车牌号和时间系统将自动匹配订单或调度单，若没匹配就手动输入相应信息</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>车牌号：</label>
						</td>
						<td>
							<input class="form-control val" name="carPlateNo"  placeholder="请输入车牌号"/>
							<span name="carPlateNoAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
							<input type="hidden" id="cityName" name="cityName">
						</td>
						<td>
							<select name="cityId" class="form-control val" id="cityId" onchange="selectSetValue('cityId','cityName')">
                                      <option value="-1">请选择</option>
                                      <#if cities?exists>
                                          <#list cities as city>
                                              <option value="${city.dataDictItemId}">${city.itemValue}</option>
                                          </#list>
                                      </#if>
                                  </select>
							<span name="cityIdAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>违章时间：</label>
						</td>
						<td>
							 <input class="datetimepicker form-control val" name="illegalTime" id="illegalTime" placeholder="请选择违章时间"/>
							<span name="illegalTimeAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>违章地点：</label>
						</td>
						<td>
							<input class="form-control val" name="illegalLocation"  placeholder="请输入违章地点"/>
							<span name="illegalLocationAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>违章类型：</label>
						</td>
						<td>
							 <select name="illegalType" class="form-control val">
                                      <option value="">请选择</option>
                                      <option value="1">未系安全带</option>
                                      <option value="2">压禁止标线</option>
                                      <option value="3">违停</option>
                                      <option value="4">闯红灯</option>
                                      <option value="5">不服从指挥</option>
                                      <option value="6">超速行驶</option>
                                      <option value="7">未设警告标志</option>
                                      <option value="8">未停车让行</option>
                                      <option value="9">未保持车距</option>
                                      <option value="10">未按道行驶</option>
                                  </select>
							<span name="illegalTypeAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>罚款金额：</label>
						</td>
						<td>
							<input class="form-control val" name="illegalFines"  placeholder="请输入罚款金额"/>
							<span name="illegalFinesAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>处理状态：</label>
						</td>
						<td>
							<select name="processingStatus" class="form-control val">
                                    <option value="">请选择</option>
                                    <option value="0">未处理</option>
                                    <option value="1">处理中</option>
                                    <option value="2">已处理</option>
                                </select>
							<span name="processingStatusAdd"></span>
						</td>

						<td>
							<label class=" control-label key"><span></span>缴费状态：</label>
						</td>
						<td>
							 <select name="paymentStatus" class="form-control val">
                                    <option value="">请选择</option>
                                    <option value="0">未缴款</option>
                                    <option value="1">已缴款</option>
                                </select>
							<span name="paymentStatusAdd"></span>
						</td>

					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>用车类型：</label>
						</td>
						<td>
							 <select name="useCarType" class="form-control val">
                                      <option value="">请选择</option>
                                      <option value="1">订单用车</option>
                                      <option value="2">调度用车</option>
                                  </select>
							<span name="useCarTypeAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span></span>单据号：</label>
						</td>
						<td>
							<input class="form-control val" name="documentNo" placeholder="请输入单据号"/>
							<span name="documentNoAdd"></span>
						</td>

					</tr>
					<tr>
						<td>
							<input type="hidden" name="driverId" />
							<label class=" control-label key"><span></span>用车人：</label>
						</td>
						<td>
							<input class="form-control val" name="driverName" placeholder="请输入用车人"/>
							<span name="driverNameAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>处理机构：</label>
						</td>
						<td>
							<input class="form-control val" name="processingAgency"  placeholder="请输入处理机构"/>
							<span name="processingAgency"></span>
						</td>

					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>扣分：</label>
						</td>
						<td>
							<input class="form-control val" name="pointsDeduction"  placeholder="请输入扣分"/>
							<span name="pointsDeductionAdd"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>违章内容：</label>
						</td>
						<td>
							<textarea class="form-control val"   name="illegalDetail"  placeholder="请填写违章内容"></textarea>
							<span name="illegalDetailAdd"></span>
						</td>

					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveAddCarIllegalAdd" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeAddCarIllegalAdd" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"carIllegalAdd":"res/js/car/car_illegal_add"}});
		require(["carIllegalAdd"], function (carIllegalAdd){
			carIllegalAdd.init();
		});
	});
    $(function () {
        $(".datetimepicker").datetimepicker({
	        language: "zh-CN",
            autoclose: true,
            todayBtn: true,
            minuteStep: 5,
            clearBtn: true,//清除按钮
            format: "yyyy-mm-dd hh:ii:ss"//日期格式
        });
    });
</script>