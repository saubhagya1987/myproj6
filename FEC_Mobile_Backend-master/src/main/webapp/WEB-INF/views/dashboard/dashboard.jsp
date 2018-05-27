<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap-modal-bs3patch.css" />
<link rel="stylesheet" type="text/css"
	href="${url}/static/css/bootstrap-modal.css" />

<script type="text/javascript"
	src="${url}/static/js/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="${url}/static/js/bootstrap-modal.js"></script>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<script type="text/javascript" src="${url}/static/js/highcharts.js">
	
</script>
<script type="text/javascript" src="${url}/static/js/drilldown.js">
	
</script>

<spring:message code="wait.loadding" var="loadding" /> 
<script type="text/javascript">

	var idIndex = 0;
	var ajaxUrl = '${url}/dashboard/getListReport';
	$(document).ready(function() {
	imgNoDataStr = '<img src="${url}/static/images/chart_nodata_${sessionScope.localeSelect}.jpg"/>';

	loadChart(idIndex, ajaxUrl)
	$(".myCalendar").datepicker({
			format: '${sessionScope.formatDate}'.toLowerCase(),
			autoclose:true			
	});	

	});

	function loadChart(idIndex, ajaxUrl) {
		$("div.container-fluid").mask("${loadding}");
		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		$.ajax({
			url : ajaxUrl,
			cache : false,
			dataType: 'json',
			 type: 'GET',
			data : {
				fromDate : fromDate,
				toDate : toDate
			},
			success : function(result) {
				console.log(result);
				var dataPie = [];
				var categoriesCol = [];
				var dataCol = [];

				var dataChart = result.dataChart;
				var title = result.title;
				var idIndex = 0;
				////
				jQuery.each(dataChart, function(i, val) {
					var name = val.name;
					var y = val.y;
					var tempPie = {};
					tempPie = {
						'name' : name,
						'y' : y
					};
					dataPie.push(tempPie);

					categoriesCol.push(name);
					var tempCol = {
						'y' : y,
						'id' : name,
					};
					dataCol.push(tempCol);

				});
// 				console.log(dataCol);
				drawPieChart(idIndex, title, dataPie);
				//	drawColChart(idIndex, title, categoriesCol, dataCol);

			}
		});
		$("div.container-fluid").unmask();
	}
	function onSearch() {
		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		if (fromDate == "") {
			$("#fromDate").val('${fromday}');
		}
		if (toDate == "") {
			$("#toDate").val('${today}');
		}
		loadChart(idIndex, ajaxUrl)
	}
	function drawPieChart(idIndex, title, dataResult) {
		// Build the chart

		$('#pieChart' + idIndex).highcharts({
			chart : {
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : true,
				type : 'pie',
				height : 300
			},
			title : {
				text : title
			},
			tooltip : {
				pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
			},
			plotOptions : {
				pie : {
					allowPointSelect : true,
					cursor : 'pointer',
					dataLabels : {
						enabled : false
					},
					showInLegend : true
				}
			},
			series : [ {
				name : "Brands",
				colorByPoint : true,
				data : dataResult
			} ]
		});
	}

	function drawColChart(idIndex, title, categoriesResult, dataResult) {

		$('#colChart' + idIndex).highcharts({
							chart : {
								type : 'column',
								height : 300
							},
							title : {
								text : title
							},
							subtitle : {
								text : 'Source: WorldClimate.com'
							},
							xAxis : {
								categories : [ 'Jan', 'Feb', 'Mar', 'Apr',
										'May', 'Jun', 'Jul', 'Aug', 'Sep',
										'Oct', 'Nov', 'Dec' ],
								crosshair : true
							},
							yAxis : {
								min : 0,
								title : {
									text : 'Rainfall (mm)'
								}
							},
							tooltip : {
								headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
								pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
										+ '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
								footerFormat : '</table>',
								shared : true,
								useHTML : true
							},
							plotOptions : {
								column : {
									pointPadding : 0.2,
									borderWidth : 0
								}
							},
							series : [ {
								name : 'dasdad',
								point : {
									events : {								
									}
								},
								data : dataResult
							} ]
						});
	}
	
	function backList() {
		document.location.href = "${url}/dashboard/view";
	}
</script>

<div id="ajax-upload"></div>
<!-- start title -->
<div class="title_top">
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span6">
			<spring:message var="menu_report" code="menu.report"></spring:message>
			<spring:message var="menu_report_online_user" code="report.online.user"></spring:message>
			<h4 style="padding: 8px 0 0 10px;">
				<a onclick="backList()" class="Color_back"><c:out value="${menu_report }"></c:out></a>
				<span> > </span>
				<span class="Colorgray"><c:out value="${menu_report_online_user}"></c:out></span>
			</h4>
		</div>	
		
		<div class="span6">


				<div class="menu_images">
					<ul style="float: right; display: none" id="btn_back">
						<li class="back"><a href="javascript:btn_back()"><span
								class="new_text"></span></a></li>

					</ul>
				</div>

			</div>
</div>
</div>	
</div>
<!-- and title -->


<!-- start dashboard -->
<div class="container-fluid unit_bg_content">

		<div class="row-fluid">
			<div class="span6 title_h2">
				<h2>
					<spring:message code="report.online.user.report" />
				</h2>
			</div>			
		</div>

	<div class="row-fluid"
		style="padding-top: 10px; background: #b2e8e8; margin-top: 10px">
		<div class="span1 offset3" style="padding: 3px 0 0 10px;">
			<label style="font-weight: bold;">From</label>
		</div>
		<div class="span1">
			<input type="text" class="myCalendar text_center" value="${fromday}"
				id="fromDate" style="width: 80px;" />
		</div>
		<div class="span1" style="padding: 3px 0 0 15px;">
			<label style="font-weight: bold;">To</label>
		</div>
		<div class="span1">
			<input type="text" class="myCalendar text_center" value="${today}"
				id="toDate" style="width: 80px;" />

		</div>
		<div class="span1">
			<input type="hidden" id="action" name="action" value="search" />

			<spring:message var="apply_now_search" code="apply.now.search"></spring:message>
			<input type="button" class="btn_search_general" onclick="onSearch()"
				name="renew" value="${apply_now_search } "
				style="width: 80px; margin-left: 88px" />
		</div>
	</div>

	<div class="row-fluid">
		<label id="msgError"></label>
	</div>


	<div class="row-fluid">
		<div id="collapseOne" class="accordion-body collapse in">
			<div class="accordion-inner">
				
				<div class="row-fluid" id="collapse0">

					<div class="row-fluid">
						<div class="span12">
							<div class="db_round_2 span12">
								<div id="pieChart0">
									<div class="noData"></div>
								</div>

							</div>
						</div>
					
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

