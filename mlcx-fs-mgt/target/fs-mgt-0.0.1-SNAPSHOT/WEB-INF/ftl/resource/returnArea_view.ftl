<meta charset="utf-8">
<div class="container-fluid backgroundColor">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">还车区域详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;名称：</label>
						</td>
						<td>
							<label class="control-label val">
							    ${returnArea.areaName}
							    </label>
						</td>
						<td>
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;城市：</label>
						</td>
						<td>
							<label class="control-label val">
								${returnArea.cityName}
								</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;选点类型：</label>
						</td>
						<td>
							<label class="control-label val">
								<#if isPloygon==1>
									多边形
								<#else>
									圆形
								</#if>
								</label>
						</td>
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;坐标：</label>
						</td>
						<td>
							<label class="control-label val">
							${returnArea.longitude}&nbsp;N&nbsp;&nbsp;&nbsp;&nbsp;
							${returnArea.latitude}&nbsp;E
							</label>
						</td>
					</tr>
					<tr>
						<#if returnArea.isAvailable==1>	
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;状态：</label>
						</td>
						<td>
							<label class="control-label val">
							 启用
							</label>
						</td>
						<#else>
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;状态：</label>
						</td>
						<td>
							<label class="control-label val">
							 停用
							</label>
						</td>
						</#if>
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;创建日期：</label>
						</td>
						<td>
							<label class="control-label val">
							${returnArea.createTime?string('yyyy-MM-dd')}
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;更新日期：</label>
						</td>
						<td>
							<label class="control-label val">
							${returnArea.updateTime?string('yyyy-MM-dd')}
							</label>
						</td>
						<td colspan="2"></td>
					</tr>
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align:center;">
						<td colspan="4"><button type="button" id="closereturnArea" class="btn-new-type-edit">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
</div>

<script type="text/javascript">
    $(function () {
      require.config({paths: {"returnArea":"res/js/resource/returnArea_list"}});
		require(["returnArea"], function (returnArea){
			returnArea.init();
		});
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>