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
	<form name="labelEditForm">
	   <input type="hidden" name="labelId" value="${cLabel.labelId }">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">编辑标签</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td><label class=" control-label key"><span>*</span>标签名称：</label>
						</td>
						<td><input class="form-control val" name="labelName" onclick="formVerify('labelName_edit')"
							maxlength="20" placeholder="请输入标签名称" value="${cLabel.labelName }" /> <span id="labelName_edit_mlcx_verify"></span>

						</td>
						<td><label class=" control-label key"><span>*</span>是否启用：</label>
                        </td>
                        <td><select name="enableStatus" class="form-control val">
                                <option value="1" <#if cLabel.enableStatus==1>selected="selected"</#if>>启用</option>
                                <option value="0" <#if cLabel.enableStatus==0>selected="selected"</#if>>停用</option>
                        </select></td>
					</tr>
					<tr>
                        <td><label class=" control-label key"><span>*</span>类型：</label>
                        </td>
                        <td><select name="labelType" class="form-control val">
                                <option value="1" <#if cLabel.labelType==1>selected="selected"</#if>>充电桩标签</option>
                                <option value="2" <#if cLabel.labelType==2>selected="selected"</#if>>停车场标签</option>
                        </select></td>
                    </tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="Editlabel"
								class="btn-new-type-edit pull-right"
								style="margin-right: 10px !important">保存</button></td>
						<td colspan="2"><button type="button"
								id="Editcloselabel" class="btn-new-type-edit pull-left"
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
				"labelEdit" : "res/js/ml/label_edit"
			}
		});
		require([ "labelEdit" ], function(labelEdit) {
			labelEdit.init();
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
</script>
