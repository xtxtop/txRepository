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

	<div align="right"><a  title="打印" id="printInfoBtns"  target="_self" onclick="test();">打印出库单</a>&nbsp;&nbsp;&nbsp;</div>
	<br/>

<span id='divPrint'>
<div>
	<div>
		  <div>
			  
                  <div align="center"><h1>深圳前海旗丰供应链管理有限公司</h1></div><br/>
                  <div align="center"><h1>出&nbsp;&nbsp;库&nbsp;&nbsp;单</h1></br>
                  <div align="left"><h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h4></div><br/>
                  <div align="left"><h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h4></div><br/>
                  <div align="left"><h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h4></div><br/>
                  <div align="left"><h4>&nbsp;收货单位：${CompanyName} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${time}</h4></div><br/>
		       
		        <div class="row">     
		           <table  align="center" class="table-bordered table-striped table-hover" width="95%" style="border-collapse: collapse;border: none; ">
					  <tbody>
						<div style="display: none"></div>
						<tr height="40"><td style="border: solid #000 1px;">料号</td><td style="border: solid #000 1px;">品名</td><td style="border: solid #000 1px;">规格型号</td><td style="border: solid #000 1px;">品牌</td><td style="border: solid #000 1px;">出库数量</td></tr>
						<#list itemList as list>
					    <tr height="40"><td style="border: solid #000 1px;">${list.sku.mfrCode}</td><td style="border: solid #000 1px;">${list.itemName}</td><td style="border: solid #000 1px;">${list.skuSalesProps}</td><td style="border: solid #000 1px;">${list.brand.brandName}</td><td style="border: solid #000 1px;">${list.quantity}</td></tr>
					    </#list>
			 			<tr height="40"><td style="border: solid #000 1px;"></td><td style="border: solid #000 1px;"></td><td style="border: solid #000 1px;"></td><td style="border: solid #000 1px;"></td><td style="border: solid #000 1px;"></td></tr>
			 			<tr height="40"><td style="border: solid #000 1px;"></td><td style="border: solid #000 1px;"></td><td style="border: solid #000 1px;"></td><td style="border: solid #000 1px;"></td><td style="border: solid #000 1px;"></td></tr>
			 			<tr height="40"><td style="border: solid #000 1px;"></td><td style="border: solid #000 1px;"></td><td style="border: solid #000 1px;"></td><td style="border: solid #000 1px;"></td><td style="border: solid #000 1px;"></td></tr>
			 			<tr height="50"><td style="border: solid #000 1px;">备注</td><td style="border: solid #000 1px;" colspan="2">${buyerMemo}</td><td style="border: solid #000 1px;" >合计</td><td style="border: solid #000 1px;">${num}</td></tr>
		 			    <tr height="50"><td style="border: solid #000 1px;" colspan="2">负责人：</td><td style="border: solid #000 1px;" colspan="3">制单：</td></tr>
			 			
			 		   </tbody>
					</table>
			   </div>
			   
		  </div>
	</div>
</div>

	<script type="text/javascript" src="${basePath!'' }/res/js/order/main.js"></script>
</span>

<script type="text/javascript">
   function test(){
		document.body.innerHTML=document.getElementById('divPrint').innerHTML;
	    window.print();
	    window.opener=null;
		window.open('','_self');
		window.close();
   }

</script>

</html>
