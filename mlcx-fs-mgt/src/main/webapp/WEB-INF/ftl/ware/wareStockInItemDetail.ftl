
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
 <meta charset="utf-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
 <!-- Tell the browser to be responsive to screen width -->
 <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<title>行知出行管理平台 </title>
 <!-- Bootstrap 3.3.5 -->
 <link rel="stylesheet" type="text/css" href="${basePath!'' }/res/dep/bootstrap-3.3.5/css/bootstrap.min.css">
 <link rel="stylesheet" type="text/css" href="${basePath!'' }/res/css/common.css">
 
</head>

<div align="right"><a  title="打印" id="wareStockInfoBtns"  target="_self" onclick="test();">打印入库单</a>&nbsp;&nbsp;&nbsp;</div>
<br/>

<span id='divPrints'>
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
		       
		      
		           <table  align="center"  width="95%" style="border-collapse: collapse;border: none; ">
					  
					 <tr height="40">
						<td style="border: solid #000 1px;" align="center" rowspan="2">序号 </td>
                        <td style="border: solid #000 1px;" align="center" rowspan="2">物料编码 </td>
		                <td style="border: solid #000 1px;" align="center" rowspan="2">名称</td>
		                <td style="border: solid #000 1px;" align="center" rowspan="2">规格型号</td>
						<td style="border: solid #000 1px;" align="center" rowspan="2">品牌</td>
		                <td style="border: solid #000 1px;" align="center" colspan="2">入库数量</td>
						<td style="border: solid #000 1px;" align="center" rowspan="2">单位</td>
					 <tr height="40">
		                <td style="border: solid #000 1px;" align="center" >应收数量</td>
		                <td style="border: solid #000 1px;" align="center" >实收数量</td>
		             </tr>
		         <#list wareStockIn.wareStockInItemList as item>
				     <tr height="40">
					  <td style="border: solid #000 1px;" align="center">${item.number}</td>
					  <td style="border: solid #000 1px;" align="center">${item.mfrCode}</td>
					  <td style="border: solid #000 1px;" align="center">${item.itemName}</td>
					  <td style="border: solid #000 1px;" align="center">${item.skuSalesProps}</td>
					  <td style="border: solid #000 1px;" align="center">${item.brandName}</td>
					  <td style="border: solid #000 1px;" align="center">${item.receivableQuantity}</td>
					  <td style="border: solid #000 1px;" align="center">${item.receivedQuantity}</td>
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
					  <td style="border: solid #000 1px;" align="center">${wareStockCountOut}</td>
					  <td style="border: solid #000 1px;" align="center">${wareStockCount}</td>
					  <td style="border: solid #000 1px;" align="center"> </td>
					</tr>
					<tr height="40">
					  <td style="border: solid #000 1px;" colspan="8"  align="left">验收&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;填单</td>
					</tr>
			 		  
					</table>
			 
			   
		  </div>
	</div>
</div>

    <script type="text/javascript" src="${basePath!'' }/res/js/ware/main.js"></script>
</span>

<script type="text/javascript">
   function test(){
		document.body.innerHTML=document.getElementById('divPrints').innerHTML;
	    window.print();
	    window.opener=null;
		window.open('','_self');
		window.close();
   }
</script>

</html>
