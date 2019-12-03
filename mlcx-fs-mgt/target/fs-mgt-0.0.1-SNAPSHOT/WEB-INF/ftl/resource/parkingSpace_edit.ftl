<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="parkingEditForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">状态车位编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>编号：</label>
						</td>
						<td>
							<input class="form-control val" name="parkingSpaceNo" value="${parkingSpace.parkingSpaceNo}" readonly="true"/>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							<select name="cityId" class="form-control val">
								 <#list cities as citie>
									 <option <#if parkingSpace.cityId==citie.dataDictItemId>selected</#if> value="${citie.dataDictItemId}" >
				                            ${citie.itemValue}
				                     </option>
				                 </#list>  
								</select>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>场站名称：</label>
						</td>
						<td>
								<select name="parkNo" class="form-control val">
								 <#list parks as park>
									 <option <#if parkingSpace.parkNo==park.parkNo>selected</#if> value="${park.parkNo}" >
				                            ${park.parkName}
				                     </option>
				                 </#list>  
								</select>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>状态：</label>
						</td>
						<td>
							<select name="isAvailable" class="form-control val">
                                    <option value="1" <#if parkingSpace.isAvailable=1>selected="selected"</#if> >启用</option>
                                    <option value="0" <#if parkingSpace.isAvailable=0>selected="selected"</#if> >停用</option>
                                </select>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>带电桩：</label>
						</td>
						<td>
							<select name="hasCharger" class="form-control val">
                                    <option value="1" <#if parkingSpace.hasCharger=1>selected="selected"</#if> >是</option>
                                    <option value="0" <#if parkingSpace.hasCharger=0>selected="selected"</#if> >否</option>
                                </select>
						</td>
						<td>
							 <label class=" control-label key"><span>*</span>电桩编号：</label>
						</td>
						
						<td>
							<div id="chargerNoXSEdit" <#if parkingSpace.hasCharger=1>style="display:block"</#if>
							 <#if parkingSpace.hasCharger=0>style="display:none"</#if>
                                >
                                <input class="form-control val" name="chargerNo" value="${parkingSpace.chargerNo}"/>
                             <span name="chargerNo"></span> 
                             </div>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>所属：</label>
						</td>
						<td>
							<label class="control-label val">
								<#if parkingSpace.ownerType=1>
								自用
								<#else>
								租用
								</#if>
								</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>启用时间</label>
						</td>
						<td>
							<label class="control-label val">
								${parkingSpace.availableUpdateTime?string('yyyy-MM-dd HH:mm:ss')}
								</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>停用时间</label>
						</td>
						<td>
							<label class="control-label val">
								${parkingSpace.disabledUpdateTime?string('yyyy-MM-dd HH:mm:ss')}
								</label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>创建日期：</label>
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
					<tr>
						<td colspan="2"><button type="button" id="saveParking" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeParkingSpace" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
       require.config({paths: {"parkingSpaceEdit":"res/js/resource/parkingSpace_edit"}});
		require(["parkingSpaceEdit"], function (parkingSpaceEdit){
			parkingSpaceEdit.init();
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