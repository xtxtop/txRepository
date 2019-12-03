<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form >
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">警告详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span></span>城市：</label>
						</td>
						<td>
							<label class="control-label val">${warning.cityName}</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>警告级别：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if warning.warningLevel==1>
							一级
							</#if>
							<#if warning.warningLevel==2>
							二级
							</#if>
							<#if warning.warningLevel==3>
							三级
							</#if>
							</lable>
						</td>
					</tr>
					<tr>
						
						<td>
							<label class=" control-label key"><span></span>警告类别：</label>
						</td>
						<td>
							<label class="control-label val">
							${warning.warningType}
							</lable>
						</td>
						<td>
							<label class=" control-label key"><span></span>警告子类：</label>
						</td>
						<td>
							<label class="control-label val">${warning.warningSubType}</lable>
						</td>
						
					</tr>
					<tr>
						
						<td>
							<label class=" control-label key"><span></span>警告时间：</label>
						</td>
						<td>
							<label class="control-label val">${warning.warningTime?string('yyyy-MM-dd HH:mm:ss')}</lable>
						</td>
						<td>
							<label class=" control-label key"><span></span>警告内容：</label>
						</td>
						<td>
							<label class="control-label val">${warning.warningContent}</lable>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>相关场站：</label>
						</td>
						<td>
							<label class="control-label val">
							${warning.parkName}
							</lable>
						</td>
						<td>
							<label class=" control-label key"><span></span>相关车辆：</label>
						</td>
						<td>
							<label class="control-label val">${warning.carPlateNo}</lable>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>相关会员：</label>
						</td>
						<td>
							<label class="control-label val">${warning.memberName}</lable>
						</td>
						<td>
							<label class=" control-label key"><span></span>相关订单：</label>
						</td>
						<td>
							<label class="control-label val">${warning.orderNo}</lable>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>需要人工关闭：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if warning.isNeedManualClosed==1>
							是
							<#else>
							否
							</#if>
							</lable>
						</td>
						<td>
							<label class=" control-label key"><span></span>是否关闭：</label>
						</td>
						<td>
						<label class="control-label val">
							<#if warning.isClosed==0>
							未关闭
							<#else>
							已关闭
							</#if>
							</lable>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>关闭时间：</label>
						</td>
						<td>
							<label class="control-label val">
								${warning.closedTime?string('yyyy-MM-dd HH:mm:ss')} 
							</lable>
						</td>
						<td>
							<label class=" control-label key"><span></span>关闭操作人：</label>
						</td>
						<td>
							<label class="control-label val"> ${sysUser.userName}</lable>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label reason key"><span></span>关闭备注：</label>
						</td>
						<td>
							<label class="control-label val"> ${warning.closedMemo}</lable>
						</td>
						<td colspan="2"></td>
					</tr>
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeViewwarning" class="btn-new-type-edit">
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
	  require.config({paths: {"warning":"res/js/monitor/warning_list"}});
		require(["warning"], function (warning){
			warning.init();
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