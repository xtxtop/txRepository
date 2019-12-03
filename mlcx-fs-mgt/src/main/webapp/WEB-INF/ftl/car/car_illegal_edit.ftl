<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="carIllegalEditForm" class="form-horizontal">
		<input type="hidden" name="illegalId" value="${carIllegal.illegalId}" />
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">违章编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>车牌号：</label>
						</td>
						<td>
							<input class="form-control val" name="carPlateNo" value="${carIllegal.carPlateNo}" />
							<span name="carPlateNoEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
							<input type="hidden" id="cityId" name="cityId" value="${carIllegal.cityId}">
						</td>
						<td>
							<input class="form-control val" name="cityName" value="${carIllegal.cityName}" readonly>
							<span name="cityNameEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>违章时间：</label>
						</td>
						<td>
							<input class="datetimepicker form-control val" name="illegalTime" value="${carIllegal.illegalTime?string('yyyy-MM-dd hh:mm:ss')}" id="recordAccidentTime" />
							<span name="illegalTimeEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>违章地点：</label>
						</td>
						<td>
							<input class="datepicker form-control val" name="illegalLocation" value="${carIllegal.illegalLocation}" />
							<span name="illegalLocationEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>违章类型：</label>
						</td>
						<td>
							<select name="illegalType" class="form-control val">
								<option value="">请选择</option>
								<option value="1" <#if carIllegal.illegalType==1>selected=selected</#if> >未系安全带</option>
								<option value="2" <#if carIllegal.illegalType==2>selected=selected</#if> >压禁止标线</option>
								<option value="3" <#if carIllegal.illegalType==3>selected=selected</#if> >违停</option>
								<option value="4" <#if carIllegal.illegalType==4>selected=selected</#if> >闯红灯</option>
								<option value="5" <#if carIllegal.illegalType==5>selected=selected</#if> >不服从指挥</option>
								<option value="6" <#if carIllegal.illegalType==6>selected=selected</#if>>超速行驶</option>
								<option value="7" <#if carIllegal.illegalType==7>selected=selected</#if> >未设警告标志</option>
								<option value="8" <#if carIllegal.illegalType==8>selected=selected</#if> >未停车让行</option>
								<option value="9" <#if carIllegal.illegalType==9>selected=selected</#if> >未保持车距</option>
								<option value="10" <#if carIllegal.illegalType==10>selected=selected</#if> >未按道行驶</option>
							</select>
							<span name="illegalTypeEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>罚款金额：</label>
						</td>
						<td>
							<input class="form-control val" name="illegalFines" value="${carIllegal.illegalFines}" />
							<span name="illegalFinesEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>处理状态：</label>
						</td>
						<td>
							<select name="processingStatus" class="form-control val">
								<option value="">请选择</option>
								<option value="0" <#if carIllegal.processingStatus==0>selected=selected</#if>>未处理</option>
								<option value="1" <#if carIllegal.processingStatus==1>selected=selected</#if>>处理中</option>
								<option value="2" <#if carIllegal.processingStatus==2>selected=selected</#if>>已处理</option>
							</select>
							<span name="processingStatusEdit"></span>
						</td>

						<td>
							<label class=" control-label key"><span></span>缴费状态：</label>
						</td>
						<td>
							<select name="paymentStatus" class="form-control val">
								<option value="">请选择</option>
								<option value="0" <#if carIllegal.paymentStatus==0>selected=selected</#if>>未缴款</option>
								<option value="1" <#if carIllegal.paymentStatus==1>selected=selected</#if>>已缴款</option>
							</select>
							<span name="paymentStatusEdit"></span>
						</td>

					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>用车类型：</label>
						</td>
						<td>
							<select name="useCarType" class="form-control val">
								<option value="">请选择</option>
								<option value="1" <#if carIllegal.useCarType==1>selected=selected</#if>>订单用车</option>
								<option value="2" <#if carIllegal.useCarType==2>selected=selected</#if>>调度用车</option>
							</select>
							<span name="useCarTypeEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span></span>单据号：</label>
						</td>
						<td>
							<input class="form-control val" name="documentNo" value="${carIllegal.documentNo}" />
							<span name="documentNoEdit"></span>
						</td>

					</tr>
					<tr>
						<td>
							<input type="hidden" name="driverId" />
							<label class=" control-label key"><span></span>用车人：</label>
						</td>
						<td>
							<input class="form-control val" name="driverName" value="${carIllegal.driverName}" />
							<span name="driverNameEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>处理机构：</label>
						</td>
						<td>
							<input class="form-control val" name="processingAgency" value="${carIllegal.processingAgency}" />
							<span name="processingAgency"></span>
						</td>

					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>扣分：</label>
						</td>
						<td>
							<input class="form-control val" name="pointsDeduction" value="${carIllegal.pointsDeduction}" />
							<span name="pointsDeductionAdd"></span>
						</td>
						<td>
							<label class=" control-label reason key"><span></span>备注：</label>
						</td>
						<td>
							<input class="form-control val" name="memo" value="${carIllegal.memo}" />
						</td>

					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>创建时间：</label>
						</td>
						<td>
							<label class="control-label val">${carIllegal.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>更新时间：</label>
						</td>
						<td>
							<label class="control-label val">${carIllegal.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
						</td>

					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>违章内容：</label>
						</td>
						<td>
							<textarea class="form-control val" name="illegalDetail">${carIllegal.illegalDetail}</textarea>
							<span name="illegalDetailEdit"></span>
						</td>
						<td></td>
						<td></td>
					</tr>

				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="saveCarIllegalEdit" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeCarIllegalEdit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
	  require.config({paths: {"carIllegalEdit":"res/js/car/car_illegal_edit"}});
		require(["carIllegalEdit"], function (carIllegalEdit){
			carIllegalEdit.init();
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