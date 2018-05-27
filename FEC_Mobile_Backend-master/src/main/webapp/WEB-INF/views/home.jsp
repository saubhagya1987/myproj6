<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<script type="text/javascript">
$(function () {
    	
    	// Radialize the colors
		Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function(color) {
		    return {
		        radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
		        stops: [
		            [0, color],
		            [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
		        ]
		    };
		});
		
		// Build the chart
        $('#chart').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: 'Browser market shares at a specific website, 2010'
            },
            tooltip: {
        	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        color: '#000000',
                        connectorColor: '#000000',
                        formatter: function() {
                            return '<b>'+ this.point.name +'</b>: '+ this.percentage +' %';
                        }
                    }
                }
            },
            series: [{
                type: 'pie',
                name: 'Browser share',
                data: [
                    ['Firefox',   45.0],
                    ['IE',       26.8],
                    {
                        name: 'Chrome',
                        y: 12.8,
                        sliced: true,
                        selected: true
                    },
                    ['Safari',    8.5],
                    ['Opera',     6.2],
                    ['Others',   0.7]
                ]
            }]
        });
    });
    

		</script>
		<script type="text/javascript" src="${url}/static/js/highcharts.src.js">
		</script>
<!-- start title -->
	<div id="ajax-upload"></div>
	<div class="title_top">        
        <div class="container-fluid">
        	<div class="row-fluid">
                <div class="span6 title_h2">
                    <h2> Dashboard </h2>
                </div>
                <div class="span6">
                	<div class="menu_images">
                     <ul style="float: right;">
                     	 <li class="import"><a href="#"><span class="import_text">Import</span></a></li>
                         <li class="delete"><a href="#"><span class="delete_text">Delete</span></a></li>
                         <li class="edit"><a href="#"><span class="edit_text">Edit</span></a></li>
                         <li class="new"><a href="#"><span class="new_text">New</span></a></li>
                     </ul>
                     </div>
                </div>
            </div>
        </div>
    </div>
     <!-- and title -->
    <div class="container-fluid">
    	<!-- start search -->
    	<div class="row-fluid">
    		<div id="chart"></div>
        </div>
        
        <!-- and search -->
        
         <!-- start table -->
        <div class="row-fluid">
        	<div class="span12">
        		${sessionScope.localeSelect}
        		<table class="table table-bordered table-hover">
            	
              <thead>
              	<tr>
                <td colspan="4" style="padding: 0px; margin: 0px;">
              	<div class="title_table row-fluid">
                	<div class="span6 title"><h3><span class="icon-list icon-medium"></span> Department list </h3></div>
                    <div class="span6">
                    	<div class="pagination1 pagination1-small pagination1-right" style="padding-right: 10px;">
                        <ul>
                        <li><a href="#">Prev</a></li>
                        <li><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>
                        <li><a href="#">...</a></li>
                        <li><a href="#">155</a></li>
                        <li><a href="#">Next</a></li>
                        </ul>
                        </div>
                    </div>
                </div>
                </td>
                </tr>
                <tr>
                  <th >#</th>
                  <th>First Name</th>
                  <th>Last Name</th>
                  <th>Username</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td style="width: 20px;">1</td>
                  <td>Mark</td>
                  <td>Otto</td>
                  <td>@mdo</td>
                </tr>
                <tr>
                  <td class="colortd">2</td>
                  <td>Jacob</td>
                  <td>Thornton</td>
                  <td>@fat</td>
                </tr>
                <tr>
                  <td>3</td>
                  <td>Larry</td>
                  <td>the Bird</td>
                  <td>@twitter</td>
                </tr>
                <tr>
                <td colspan="4" style="padding: 0px; margin: 0px;">
              	<div class="title_table row-fluid">
                	<div class="span6 title">&nbsp;</div>
                    <div class="span6">
                    	<div class="pagination pagination-small pagination-right" style="padding-right: 10px;">
                        <ul>
                        <li><a href="#">Prev</a></li>
                        <li><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>
                        <li><a href="#">...</a></li>
                        <li><a href="#">155</a></li>
                        <li><a href="#">Next</a></li>
                        </ul>
                        </div>
                    </div>
                </div>
                </td>
                </tr>
              </tbody>
            </table>
        	</div>
        </div>
         <!-- and table -->
     </div>