<#if print == "1">
	<link rel="stylesheet" type="text/css" href="${basePath!'' }/res/dep/bootstrap-3.3.5/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${basePath!'' }/res/css/common.css"> 
<#else>
	<div align="right"><a  title="打印" id="printwareStockInfoBtns"  target="_self">打印入库单</a>&nbsp;&nbsp;&nbsp;</div>
	<br/>
</#if>

<span id='divPrint'>
<div>
	<div>
		  <div>
			  
                  <div align="center"><h1>深圳前海旗丰供应链管理有限公司</h1></div><br/>
                  <div align="center"><h1>入&nbsp;&nbsp;库&nbsp;&nbsp;单</h1></br>
                  <div align="left"><h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h4></div><br/>
                  <div align="left"><h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h4></div><br/>
                  <div align="left"><h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h4></div><br/>
                  <div align="left"><h4>&nbsp;入库部门：芯动商城 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${time}</h4></div><br/>
		       
		        <div class="row">     
		           <table id="orderPrintTid" align="center" class="table-bordered table-striped table-hover" width="95%" style="border-collapse: collapse;border: none; ">
					  <tbody>
						<div style="display: none"></div>
					 <tr height="40">
						<td style="border: solid #000 1px;" align="center" rowspan="2">序号 </td>
                        <td style="border: solid #000 1px;" align="center" rowspan="2">物料编码 </td>
		                <td style="border: solid #000 1px;" align="center" rowspan="2">品名</td>
		                <td style="border: solid #000 1px;" align="center" rowspan="2">规格型号</td>
						<td style="border: solid #000 1px;" align="center" rowspan="2">品牌</td>
		                <td style="border: solid #000 1px;" align="center" colspan="2">入库数量</td>
						<td style="border: solid #000 1px;" align="center" rowspan="2">单位</td>
					 <tr height="40">
		                <td style="border: solid #000 1px;" align="center" >送货数</td>
		                <td style="border: solid #000 1px;" align="center" >实收数</td>
		             </tr>
		         <#list purchaseOrder.purchaseOrderItemList as good>
				     <tr height="40">
					  <td style="border: solid #000 1px;" align="center">${good.number}</td>
					  <td style="border: solid #000 1px;" align="center">${good.sku.mfrCode}</td>
					  <td style="border: solid #000 1px;" align="center">${good.sku.skuName}</td>
					  <td style="border: solid #000 1px;" align="center">${good.skuSalesProps}</td>
					  <td style="border: solid #000 1px;" align="center">${good.brandName}</td>
					  <td style="border: solid #000 1px;" align="center">${good.quantity}</td>
					  <#if wareStockInItems??>
					  <#list wareStockInItems as wareStockIns>
					  <td style="border: solid #000 1px;" align="center">${wareStockIns.receivedQuantity}</td>
					  </#list>
					  <#else>
					  <td style="border: solid #000 1px;" align="center"></td>
					  </#if>
					  <td style="border: solid #000 1px;" align="center">PC</td>
					</tr>
		        </#list>
		             <tr height="40">
					  <td style="border: solid #000 1px;" align="center"></td>
					  <td style="border: solid #000 1px;" align="center"></td>
					  <td style="border: solid #000 1px;" align="center"></td>
					  <td style="border: solid #000 1px;" align="center"></td>
					  <td style="border: solid #000 1px;" align="center"></td>
					  <td style="border: solid #000 1px;" align="center"></td>
					  <td style="border: solid #000 1px;" align="center"> </td>
					  <td style="border: solid #000 1px;" align="center"> </td>
					</tr>
					 <tr height="40">
					  <td style="border: solid #000 1px;" align="center"></td>
					  <td style="border: solid #000 1px;" align="center"></td>
					  <td style="border: solid #000 1px;" align="center"></td>
					  <td style="border: solid #000 1px;" align="center"></td>
					  <td style="border: solid #000 1px;" align="center"></td>
					  <td style="border: solid #000 1px;" align="center"></td>
					  <td style="border: solid #000 1px;" align="center"> </td>
					  <td style="border: solid #000 1px;" align="center"> </td>
					</tr>
					 <tr height="40">
					  <td style="border: solid #000 1px;" align="center"></td>
					  <td style="border: solid #000 1px;" align="center"></td>
					  <td style="border: solid #000 1px;" align="center"></td>
					  <td style="border: solid #000 1px;" align="center"></td>
					  <td style="border: solid #000 1px;" align="center"></td>
					  <td style="border: solid #000 1px;" align="center"></td>
					  <td style="border: solid #000 1px;" align="center"> </td>
					  <td style="border: solid #000 1px;" align="center"> </td>
					</tr>
					<tr height="40">
					  <td style="border: solid #000 1px;" align="right">备注：</td>
					  <td style="border: solid #000 1px;"  colspan="3"  align="left"></td>
					  <td style="border: solid #000 1px;" align="right">合计：</td>
					  <td style="border: solid #000 1px;" align="center">${count}</td>
					  <td style="border: solid #000 1px;" align="center">${wareStockCount}</td>
					  <td style="border: solid #000 1px;" align="center"> </td>
					</tr>
					<tr height="40">
					  <td style="border: solid #000 1px;" colspan="8"  align="left">验收&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;填单</td>
					</tr>
			 		   </tbody>
					</table>
			   </div>
			   
		  </div>
	</div>
</div>

    <script type="text/javascript" src="${basePath!'' }/res/js/ware/main.js"></script>
</span>
