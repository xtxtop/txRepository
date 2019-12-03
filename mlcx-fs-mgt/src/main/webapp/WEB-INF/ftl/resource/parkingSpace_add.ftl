<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="parkingAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">车位新增</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							<select name="cityId" id="cityId" class="form-control val">
								 <#list cities as citie>
									 <option  value="${citie.dataDictItemId}"  <#if cityId == citie.dataDictItemId> selected="selected"</#if>>
				                            ${citie.itemValue}
				                     </option>
				                 </#list>  
								</select>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>场站名称：</label>
						</td>
						<td>
							<select name="parkNo" id="parkNode" class="form-control val">
								 <#list parks as park>
									 <option  value="${park.parkNo}" <#if parkNo == park.parkNo> selected="selected"</#if>>
				                            ${park.parkName}
				                     </option>
				                 </#list>  
								</select>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>带电桩：</label>
						</td>
						<td>
							<select name="hasCharger" class="form-control val">
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>电桩编号：</label>
						</td>
						<td>
							<div id="chargerNoXS">
								<input class="form-control val" name="chargerNo" value="${parkingSpace.chargerNo}" placeholder="请输入电桩编号"/>
								<span name="chargerNo"></span>
							</div>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addParking" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="addcloseParkingSpace" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
       require.config({paths: {"parkingSpaceAdd":"res/js/resource/parkingSpace_add"}});
		require(["parkingSpaceAdd"], function (parkingSpaceAdd){
			parkingSpaceAdd.init();
		});  
		});
</script>
<script type="text/javascript">
    $(function () {
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>