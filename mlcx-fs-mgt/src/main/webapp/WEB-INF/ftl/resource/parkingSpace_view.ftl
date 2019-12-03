<meta charset="utf-8">
<div class="container-fluid backgroundColor">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">车位详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>编号：</label>
						</td>
						<td>
							<label class="control-label val">
							    ${parkingSpace.parkingSpaceNo}
							    </label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							<label class="control-label val">
								   ${parkingSpace.cityName}
								   </label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>场站名称：</label>
						</td>
						<td>
								<label class="control-label val">
				            	${park.parkName}
				            	</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>所属：</label>
						</td>
						<td>
							<label class="control-label val">
				            	<#if park.ownerType=1>
							      自有
							 <#elseif park.ownerType=2>
							     租用
							</#if>
				            	</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>状态：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if parkingSpace.isAvailable=1>
							       启用
							 <#else>
							     停用 
							</#if>
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>带电桩：</label>
						</td>
						
						<td>
							<label class="control-label val">
							<#if parkingSpace.hasCharger=1>
							     是
							 <#else>
							     否
							</#if>
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>启用时间</label>
						</td>
						<td>
							<label class="control-label val">
								${parkingSpace.availableUpdateTime?string('yyyy-MM-dd HH:mm:ss')}
								</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>停用时间</label>
						</td>
						<td>
							<label class="control-label val">
								${parkingSpace.disabledUpdateTime?string('yyyy-MM-dd HH:mm:ss')}
								</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>电桩编号：</label>
						</td>
						<td>
							<label class="control-label val">
							${parkingSpace.chargerNo}
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>创建日期：</label>
						</td>
						<td>
							<label class="control-label val">
							${parkingSpace.createTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>更新日期：</label>
						</td>
						<td>
							<label class="control-label val">
							${parkingSpace.updateTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
						</td>
						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closeParking" class="btn-new-type-edit">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
</div>


<script type="text/javascript">
 $(function(){
       require.config({paths: {"parkingSpace":"res/js/resource/parkingSpace_list"}});
		require(["parkingSpace"], function (parkingSpace){
			parkingSpace.init();
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