<meta charset="utf-8">
<style media="screen">
.carTrack-info ul>li:first-child{
font-weight:600;
text-align:left;
}
.searching span{
font-size:1.5rem;
font-weight:600;
color:black;
margin-right: 5%;
}
.con-border{
border:1px solid #dedede;
padding-right: 0px;
}
.new-seacher-content{
	overflow: hidden;
	background: #fff;
	padding: 10px 16px;
	position: absolute;
	top:10px;
	left:40px;
	z-index:10;
	box-shadow:2px 0px 5px rgba(0,0,0,0.2) 
}
.new-seacher-content input,
.new-seacher-content .carMonitorSearchButton,
.new-seacher-content select
{
	border: none;
	outline: none;
	background: none;
	float: left;
	line-height: 30px;
	height: 32px;
	box-sizing: border-box;
	border: solid 1px #dedede;
	padding-left: 14px;
	border-radius:4px;
	font-size: 14px;
}

.new-seacher-content select.type1{
	width: 100px;
}
.new-seacher-content select.type2{
	width: 100px;
	margin-left: 12px;
}
.new-seacher-content input{
	width: 155px;
	margin-left: 12px;
}
.new-seacher-content .carMonitorSearchButton{
	width: 72px;
	text-align:center;
	margin-left: 16px;
	padding: 0;
	border-radius:6px;
	background: #2A64FB;
	color: #fff;
	cursor: pointer;
}
</style>
   <div class="container-fluid con-border">
      <div class="row" style="position:relative">
       <div class="new-seacher-content">
           <form class="form-horizontal" name="carTrackSerachForm">
        	 	<input class="form-control" type="hidden" name="flag" value="${flag}">
                <input name="carPlateNo" value="${carPlateNo}" placeholder="请输入车牌号">
                <input class="datetimepicker form-control" name="createTimeStart" style="background:#FFFFFF;color:#3f9fff;" value="${startTime?string('yyyy-MM-dd HH:mm:ss')}" readonly/>
                <input class="datetimepicker form-control" name="createTimeEnd" style="background:#FFFFFF;color:#3f9fff;" value="${endTime?string('yyyy-MM-dd HH:mm:ss')}"  readonly/>
                <input type="button" class="carMonitorSearchButton" id="carTrackSearch" value="搜索" />
         </form>
   		 </div>
       </div>
		<div id="trackMap"></div>
	 	<div class="carTrack-info">
	 </div>
   </div><!-- /.container-fluid -->
 <style type="text/css">
	#controller{width:100%; border-bottom:3px outset; height:30px; filter:alpha(Opacity=100); -moz-opacity:1; opacity:1; z-index:10000;}
	#trackMap{
			width:100%;
			overflow: hidden;
           /*  margin:0 0 0 1%; */
           display: inline-block;
		}
	.carInfro1{
		margin-left: -25px; 
	}

	.carInfro1 li{
		height:20px;
		line-height:20px;
		/* font-size:12px; */
	}
	.carTrack-info h1,.carTrack-info h2{
		margin:0;padding:0;
	}
	.carTrack-info{
		position: absolute;
		height: 900px!important;
		width: 0%;
		right: 0;
		top: 56px;
		background: rgb(27,33,45);
	}
	.head-infro{
		overflow:hidden;
		box-sizing: border-box;
		padding: 0 18px;
		background: linear-gradient(90deg,#607BF1,#6097F1);
	}
	.head-infro .left,.head-infro .right{
		color: #fff;
		line-height: 40px;
	}
	.head-infro .left{
		float: left;
		font-size: 16px;
	}	
	.head-infro .right{
		float: right;
		font-size: 12px;
	}
	.echart-content{
		border-bottom: solid 1px #252E3B;
	}
	.text-infro-content .title{
		font-size: 14px;
		font-weight: normal;
		line-height: 34px;
		color: #999;
		box-sizing: border-box;
		padding-left: 16px;
		
	}
	.text-infro-content .details{
		font-size: 14px;
		color: #fff;
		text-align: center;
		line-height: 34px;
		background: #222A36;
	}
</style>
<script type="text/javascript" src="res/dep/LuShu/LuShu_min.js"></script>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"carTrack":"res/js/monitor/car_track"}});
		require(["carTrack"], function (carTrack){
			carTrack.init();
		});
	});
	 $(function () {
        $(".datetimepicker").datetimepicker({
	        language: "zh-CN",
            autoclose: true,
            todayBtn: true,
            minuteStep: 5,
            clearBtn: true,//清除按钮

            format: "yyyy-mm-dd hh:ii:ss"//日期格式
        });
    });
</script>


<script type="text/javascript">
$(document).ready(function(){
  $(".carC").click(function(){
  $('.box').toggle();
  });
  $(".closeB").click(function(){
    $('.box').css('display','none');
  });
});

</script>