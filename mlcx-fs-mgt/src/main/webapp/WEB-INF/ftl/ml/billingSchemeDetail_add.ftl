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
	<form name="billingSchemeDetailAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增明细</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label key"><span>*</span>序号：</label>
						</td>
						<td><input class="form-control val" name="serialNumber" onkeyup="this.value=this.value.replace(/\D/g,'')"
							maxlength="20" placeholder="请输入序号" onclick="formVerify('serialNumber_add_billingSchemeDetail')"/> <span id="serialNumber_add_billingSchemeDetail_mlcx_verify"></span>

						</td>
						<td><label class=" control-label key"><span>*</span>计费方案：</label>
                        </td>
                        <td><select name="schemeNo" class="form-control val" onclick="formVerify('schemeNo_add_billingSchemeDetail')"> 
                                <option value="">请选择</option>
                                <#list scheme as s>
                                 <#if s.status==1>
                                    <option value="${s.schemeNo }">${s.schemeName }</option>
                                 </#if>
                                 </#list>
                               </select> 
                        <span id="schemeNo_add_billingSchemeDetail_mlcx_verify"></span>
                        </td>
					</tr>
					<tr>
                        <td><label class=" control-label key"><span>*</span>开始时间-结束时间：</>
                        </td>
                        <td colspan="3">
                         <div class="jeitem">
                            <div class="jeinpbox"><input type="text" class="jeinput" id="timeAdds" placeholder="请选择开始时间和结束时间" name="timeQuantum" value=""
                            onclick="formVerify('timeQuantum_add_billingSchemeDetail')"/> <span id="timeQuantum_add_billingSchemeDetail_mlcx_verify"></span>
                            </div>
                        </div>
                        </td>
                    </tr>
					<tr>
                       <td><label class=" control-label key">备注：</label>
                        </td>
                        <td>
                            <select class="form-control val" name="memo">
                                <option value="">请选择</option>
                                <option value="1">尖时段</option>
                                <option value="2">峰时段</option>
                                <option value="3">平时段</option>
                                <option value="4">谷时段</option>
                            </select>
                        </td>
                        <td><label class=" control-label key">状态：</label>
                        </td>
                        <td>
                            <select class="form-control val" name="status">
                                <option value="1">有效</option>
                            </select>
                        </td>
                    </tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addbillingSchemeDetail"
								class="btn-new-type-edit pull-right"
								style="margin-right: 10px !important">保存</button></td>
						<td colspan="2"><button type="button"
								id="addclosebillingSchemeDetail" class="btn-new-type-edit pull-left"
								style="margin-left: 10px !important">关闭</button></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
</div>
<script type="text/javascript">
	$(function() {
		require.config({
			paths : {
				"billingSchemeDetailAdd" : "res/js/ml/billingSchemeDetail_add"
			}
		});
		require([ "billingSchemeDetailAdd" ], function(billingSchemeDetailAdd) {
			billingSchemeDetailAdd.init();
		});
		$(".datetimepicker").datetimepicker({
			language : "zh-CN",
			autoclose : true,
			clearBtn : true, //清除按钮
			todayBtn : true,
			minuteStep : 5,
			format : "yyyy-mm-dd hh:ii:ss" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
		jeDate("#timeAdds",{
            format: "hh:mm",
            multiPane:false,
            range:" 至 " 
        });
	});
