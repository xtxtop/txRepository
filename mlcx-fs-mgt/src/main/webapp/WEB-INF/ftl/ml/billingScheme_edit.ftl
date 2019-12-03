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
	<form name="billingSchemeEditForm">
	   <input type="hidden" name="schemeNo" value="${billingScheme.schemeNo }">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">编辑计费方案</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label key"><span>*</span>计费方案名称：</>
						</td>
						<td><input class="form-control val" name="schemeName" value="${billingScheme.schemeName }"
							maxlength="20" placeholder="请输入名称" onclick="formVerify('schemeName_edit_billingScheme')"/> <span id="schemeName_edit_billingScheme_mlcx_verify"></span>

						</td>
						<td><label class=" control-label key"><span>*</span>计费方案版本：</>
                        </td>
                        <td><input class="form-control val" name="schemeVersions" value="${billingScheme.schemeVersions }"
                        onkeyup="this.value=this.value.replace(/[^\w\.\/]/ig,'')"
                            placeholder="请输入计费方案版本(长度为4的非中文)" maxlength="4" onclick="formVerify('schemeVersions_edit_billingScheme')"/> <span id="schemeVersions_edit_billingScheme_mlcx_verify"></span>

                        </td>
					</tr>
					<tr>
                        <td><label class=" control-label key"><span>*</span>生效时间：</>
                        </td>
                        <td><input class="form-control val" id="inpstart_edit" name="effectiveDate"  value="${billingScheme.effectiveDate?string('yyyy-MM-dd')}"
                            placeholder="请选择时间" readonly="readonly" /> 

                        </td>
                        <td><label class=" control-label key "><span>*</span>失效时间：</>
                        </td>
                        <td><input class="form-control val " id="inpend_edit" name="invalidDate" value="${billingScheme.invalidDate?string('yyyy-MM-dd')}"
                             placeholder="请选择时间" readonly="readonly" /> 

                        </td>
                      </tr>
					<tr>
                        <td><label class=" control-label key"><span>*</span>预冻结金额(元)：</>
                        </td>
                        <td><input class="form-control val" name="advanceFrozenMoney" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')" value="${billingScheme.advanceFrozenMoney }"
                            maxlength="20" placeholder="请输入预冻结金额" onclick="formVerify('advanceFrozenMoney_edit_billingScheme')"/> <span id="advanceFrozenMoney_edit_billingScheme_mlcx_verify"></span>

                        </td>
                        <td><label class=" control-label key"><span>*</span>最小冻结金额(元)：</>
                        </td>
                        <td><input class="form-control val" name="minFrozenMoney" value="${billingScheme.minFrozenMoney }" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')"
                            maxlength="20" placeholder="请输入最小冻结金额" onclick="formVerify('minFrozenMoney_edit_billingScheme')"/> <span id="minFrozenMoney_edit_billingScheme_mlcx_verify"></span>

                        </td>
                      </tr>
                   <!--    <tr>
                        <td><label class=" control-label key"><span>*</span>时段数：</>
                        </td>
                        <td colspan="3">
                         <div class="jeitem">
			                <div class="jeinpbox"><input type="text" class="jeinput" id="timeNumEdit" placeholder="请选择时间段" name="timeNum" value="${billingScheme.timeNum }"
			                onclick="formVerify('timeNum_edit_billingScheme')"/> <span id="timeNum_edit_billingScheme_mlcx_verify"></span>
			                </div>
			            </div>
                        </td>
                    </tr> -->
                    <tr>
                        <td><label class=" control-label key"><span>*</span>尖时段电价(元)：</>
                        </td>
                        <td><input class="form-control val" name="opintPrice" value="${billingScheme.opintPrice }" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')"
                            maxlength="20" placeholder="请输入尖时段电价" onclick="formVerify('opintPrice_edit_billingScheme')"/> <span id="opintPrice_edit_billingScheme_mlcx_verify"></span>

                        </td>
                        <td><label class=" control-label key"><span>*</span>峰时段电价(元)：</>
                        </td>
                        <td><input class="form-control val" name="peakPrice" value="${billingScheme.peakPrice }" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')"
                            maxlength="20" placeholder="请输入峰时段电价" onclick="formVerify('peakPrice_edit_billingScheme')"/> <span id="peakPrice_edit_billingScheme_mlcx_verify"></span>

                        </td>
                    </tr>
                    <tr>
                        <td><label class=" control-label key"><span>*</span>平时段电价(元)：</>
                        </td>
                        <td><input class="form-control val" name="flatPrice" value="${billingScheme.flatPrice }" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')"
                            maxlength="20" placeholder="请输入平时段电价" onclick="formVerify('flatPrice_edit_billingScheme')"/> <span id="flatPrice_edit_billingScheme_mlcx_verify"></span>

                        </td>
                        <td><label class=" control-label key"><span>*</span>谷时段电价(元)：</>
                        </td>
                        <td><input class="form-control val" name="valleyPrice" value="${billingScheme.valleyPrice }" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')"
                            maxlength="20" placeholder="请输入谷时段电价" onclick="formVerify('valleyPrice_edit_billingScheme')"/> <span id="valleyPrice_edit_billingScheme_mlcx_verify"></span>

                        </td>
                    </tr>
                    <tr>
                        <td><label class=" control-label key"><span>*</span>预约费率(%)：</>
                        </td>
                        <td><input class="form-control val" name="orderedRate" value="${billingScheme.orderedRate }" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')"
                            maxlength="20" placeholder="请输入预约费率" onclick="formVerify('orderedRate_edit_billingScheme')"/> <span id="orderedRate_edit_billingScheme_mlcx_verify"></span>

                        </td>
                        <td><label class=" control-label key"><span>*</span>服务费(元)：</>
                        </td>
                        <td><input class="form-control val" name="serviceCharge" value="${billingScheme.serviceCharge }" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')"
                            maxlength="20" placeholder="请输入服务费" onclick="formVerify('serviceCharge_edit_billingScheme')"/> <span id="serviceCharge_edit_billingScheme_mlcx_verify"></span>

                        </td>
                    </tr>
                    <tr>
                        <td><label class=" control-label key"><span>*</span>告警金额(元)：</> 
                        </td>
                        <td><input class="form-control val" name="warmingPrice" value="${billingScheme.warmingPrice }" onkeyup="this.value=this.value.replace(/[^\d.]/g,'')"
                            maxlength="20" placeholder="请输入告警金额" onclick="formVerify('warmingPrice_edit_billingScheme')"/> <span id="warmingPrice_edit_billingScheme_mlcx_verify"></span>

                        </td>
                         <td><label class=" control-label key"><span>*</span>状态：</>
                        </td>
                        <td><select name="status" class="form-control val">
                                <option value="1" <#if billingScheme.status==1 >selected</#if>>有效</option>
                                <option value="0" <#if billingScheme.status==0 >selected</#if>>无效</option>
                        </select> </td>
                    </tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editbillingScheme"
								class="btn-new-type-edit pull-right"
								style="margin-right: 10px !important">保存</button></td>
						<td colspan="2"><button type="button"
								id="editclosebillingScheme" class="btn-new-type-edit pull-left"
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
				"billingSchemeEdit" : "res/js/ml/billingScheme_edit"
			}
		});
		require([ "billingSchemeEdit" ], function(billingSchemeEdit) {
			billingSchemeEdit.init();
		});
	});
	 </script>
	<script type="text/javascript">
    var start = {}, end = {};
	var s=$("#inpstart_edit").val();
    end.minDate=s;
    jeDate('#inpstart_edit',{
        onClose:false,
        format: 'YYYY-MM-DD',
        minDate: '2014-06-16', //设定最小日期为当前日期
        donefun: function(obj){
        	end.minDate = obj.val; //开始日选好后，重置结束日的最小日期
            jeDate("#inpend_edit",LinkageEndDate_edit(false));
        }
    });
    jeDate('#inpend_edit',LinkageEndDate_edit);

    function LinkageEndDate_edit(istg) {
        return {
            trigger : istg || "click",
            onClose:false,
            format: 'YYYY-MM-DD',
            minDate: function (that) {
                //that 指向实例对象
                var nowMinDate = jeDate.valText('#inpstart_edit') == "" && jeDate.valText(that.valCell) == "";
                return nowMinDate ? jeDate.nowDate({DD:0}) : end.minDate ;
            }, //设定最小日期为当前日期
            maxDate: '2099-06-16', //设定最大日期为当前日期
            donefun: function(obj){
                start.maxDate = obj.val; //将结束日的初始值设定为开始日的最大日期
            }
        };    
    }
    </script>
