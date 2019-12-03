<meta charset="utf-8">
<style>
.daterangepicker .calendar-date {
	border: 1px solid #ddd;
	padding: 4px;
	border-radius: 4px;
	background: #fff;
	display: none;
}
.daterangepicker{
    width: 185px;
    }
   .ranges{
    width:167px;
   }
   .applyBtn {
    width: 54px;
   }
   .cancelBtn  {
    width: 54px;
   }
</style>
<div class="container-fluid backgroundColor">
	<form name="parkBillingAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增计费规则</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label key"><span>*</span>计费方案名称名称：</label>
						</td>
						<td><input class="form-control val" name="parkBillingName"
							maxlength="20" placeholder="请输入计费方案名称"/>
                            <span id="parkBillingName_add_matching_mlcx_verify"></span>
						</td>
                        <td><label class=" control-label key"><span>*</span>计费方案版本：</label>
                        </td>
                        <td><input class="form-control val" name="billingVersion"
                                   maxlength="20" placeholder="请输入计费方案版本"/>
                            <span id="billingVersion_add_matching_mlcx_verify"></span>
                        </td>
					</tr>

                    <tr>
                        <td><label class=" control-label key"><span>*</span>停车类型：</label>
                        </td>
                        <td><select name="parkType" class="form-control val">
                            <option value="">--全部--</option>
                            <option value="1">闸</option>
                            <option value="0">地锁</option>
                        </select>
                            <span id="parkType_add_matching_mlcx_verify"></span>
                        </td>

                        <td><label class=" control-label key"><span>*</span>预约免费时长(分钟)：</label>
                        </td>
                        <td><input class="form-control val" name="freeTime"
                                   maxlength="20" placeholder="请输入预约免费时长"/>
                            <span id="freeTime_add_matching_mlcx_verify"></span>
                        </td>
                    </tr>

                    <td><label class=" control-label key"><span>*</span>计费时间单位(分钟)：</label></td>
                    <td><input class="form-control val" name="unitTime" value="60"
                               maxlength="20" placeholder="请输入计费时间单位"/>
                        <span id="unitTime_add_matching_mlcx_verify"></span>
                    </td>

                    <td><label class=" control-label key"><span>*</span>金额(元/单位时间)：</label>
                    </td>
                    <td><input class="form-control val" name="overtimePrice"
                               maxlength="20" placeholder="请输入金额"/>
                        <span id="overtimePrice_add_matching_mlcx_verify"></span>
                    </td>
                    </tr>


                    <tr>
                        <td><label class=" control-label key"><span>*</span>是否启用：</label>
                        </td>
                        <td><select name="status" class="form-control val">
                            <option value="1">启用</option>
                            <option value="0">停用</option>
                        </select> </td>
                        <td><label class=" control-label key">封顶金额：</label>
                          <td><input class="form-control val" name="cappingPrice"
                               maxlength="20" placeholder="请输入封顶金额"/>
                     </td>
                    </tr>

				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addparkBilling"
								class="btn-new-type-edit pull-right"
								style="margin-right: 10px !important">保存</button></td>
						<td colspan="2"><button type="button"
								id="addclosematching" class="btn-new-type-edit pull-left"
								style="margin-left: 10px !important">关闭</button></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(function() {
		require.config({
			paths : {
				"parkBillingAdd" : "res/js/mlpark/parkBilling_add"
			}
		});
		require([ "parkBillingAdd" ], function(parkBillingAdd) {
            parkBillingAdd.init();
		});
		$(".datetimepicker").datetimepicker({
			language : "zh-CN",
			autoclose : true,
			clearBtn : true, //清除按钮
			todayBtn : true,
			minuteStep : 5,
			format : "yyyy-mm-dd hh:ii:ss" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
	});
