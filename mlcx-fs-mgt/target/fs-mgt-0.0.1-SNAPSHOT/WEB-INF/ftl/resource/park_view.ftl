<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="parkAddForm">
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">场站详情</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span></span>编号：</label>
						</td>
						<td>
							<label class="control-label val">
							    ${park.parkNo}
							    </label>
						</td>
						<td>
							<label class=" control-label key"><span></span>名称：</label>
						</td>
						<td>
							<label class="control-label val">
							    ${park.parkName}
							    </label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>城市：</label>
						</td>
						<td>
							<label class="control-label val">
								${park.cityName}
								</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>详细地址：</label>
						</td>
						<td>
							<label class="control-label val">
								${park.addrStreet}
								</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>加盟商：</label>
						</td>
						<td>
							<label class="control-label val">
								${franchisee.franchiseeName}
								</label>

						</td>
						<td>
							<label class=" control-label key"><span></span>坐标：</label>
						</td>
						<td>
							<label class="control-label val">
							${park.longitude}N
							${park.latitude}E
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>电子围栏半径：</label>
						</td>
						<td>
							<label class="control-label val">
								${park.electronicFenceRadius}米
								</label>
						</td>
								<td>
									<label class=" control-label key"><span></span> 类别：</label>
								</td>
								<td>
									<label class="control-label val">
									<#if park.parkType=1>
									管理类
									<#else>
									使用类
									</#if>
								</label>
								</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>是否开放：</label>
						</td>
						<td>
							<label class="control-label val">
								<#if park.isPublic=1>
									开放
									<#else>
									不开放
									</#if>
									</label>
						</td>

						<td>
							<label class=" control-label key">样式：</label>
						</td>
						<td>
							<label class="control-label val">
								${park.style}
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">车位数：</label>
						</td>
						<td>
							<label class="control-label val">
							 ${park.parkingSpaceNumber}
							 </label>
						</td>
						<td>
							<label class=" control-label key">电桩数：</label>
						</td>
						<td>
							<label class="control-label val">
							 ${park.chargerNumber}
							 </label>
						</td>
					</tr>

					<tr>
						<td>
							<label class=" control-label key"><span></span>车辆数：</label>
						</td>
						<td>
							<label class="control-label val">
						     ${park.carNumber}
						     </label>
						</td>
						<td>
							<label class=" control-label key">服务：</label>
						</td>
						<td>
							<label class="control-label val">
						     ${park.supportedServices}
						     </label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">所属：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if park.ownerType=1>
								自有
							<#else>
								租用
							</#if>
							</label>
						</td>
						<#if (parkCompanyRelList?size>0)>
						<td>
							<label class=" control-label key"><span></span>所属集团：</label>
						</td>
						<td>
							<label class="control-label val">
							<#list parkCompanyRelList as company>
							${company.companyName}
							</#list>
							</label>
						</td>
						</#if>
					</tr>
					<tr>
						<#if park.isAvailable==1>
						<td>
							<label class=" control-label key"><span></span>状态：</label>
						</td>
						<td>
							<label class="control-label val">
							 启用
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>启用时间：</label>
						</td>
						<td>
							<label class="control-label val">
							${park.availableUpdateTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
						</td>
						<#else>
							<td>
								<label class=" control-label key"><span></span>状态：</label>
							</td>
							<td>
								<label class="control-label val">
							 停用
							</label>
							</td>
							<td>
								<label class=" control-label key"><span></span>停用时间：</label>
							</td>
							<td>
								<label class="control-label val">
							${park.availableUpdateTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
							</td>
							</#if>
					</tr>
					<tr>
							<td>
									<label class=" control-label key">场站电费：</label>
							</td>
							<td>
								<label class="control-label val">
							 ${park.electricPrice}元/度
							 </label>
						</td>
						<td>
							<label class=" control-label key">场站租金：</label>
							</td>
							<td>
								<label class="control-label val">
							 ${park.parkRent}元/月
							 </label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label"><span></span>交租日期：</label>
							</td>
							<td>
								<label class="control-label">
							${park.payRentDayOfMonth}
							</label>
							</td>
							<td>
								<label class=" control-label key"><span></span>创建日期：</label>
							</td>
							<td>
								<label class="control-label val">
							${park.createTime?string('yyyy-MM-dd')}
							</label>
							</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>更新日期：</label>
						</td>
						<td>
							<label class="control-label val">
							${park.updateTime?string('yyyy-MM-dd')}
							</label>
						</td>
						<td>
							<label class=" control-label key"><span></span>取车附加费用：</label>
						</td>
						<td>
							<label class="control-label val">
								${park.serviceFeeGet}元
								 </label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>还车附加费用</label>
						</td>
						<td>
							<label class="control-label val">
							    ${park.serviceFeeBack}元
							    </label>
						</td>
						<td>
							<label class=" control-label key"><span></span>营业时间：</label>
						</td>
						<td>
							<label class="control-label val">
							${park.businessHours}
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span></span>节假日营业：</label>
						</td>
						<td>
							<label class="control-label val">
							<#if park.isBusinessFestival==1>
							是
							</#if>
							<#if park.isBusinessFestival==0>
							否
							</#if>
							</label>
						</td>
							<#list picUrls  as urls>
						<td>
							<label class=" control-label key"><span></span>图片：</label>
						</td>
						<td>
						<div class="img-td-upload">
						<label class="control-label val">
                				<img src="${imagePath!''}/${urls}" width="280" height="180"/>
                				</label>
						</div>
							
						</td>
						</#list>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr style="text-align: center;">
						<td colspan="4"><button type="button" id="closepark" class="btn-new-type-edit">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
<script type="text/javascript">
    $(function () {
      require.config({paths: {"park":"res/js/resource/park_list"}});
		require(["park"], function (park){
			park.init();
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