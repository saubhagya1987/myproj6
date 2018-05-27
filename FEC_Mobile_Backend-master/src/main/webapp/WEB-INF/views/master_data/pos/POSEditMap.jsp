<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<spring:message var="msg.save.success" code="msg.save.success"></spring:message>
<spring:message var="msg_deleteQuestion"
	code="msg.alert.delete.question"></spring:message>

<!-- start title -->
<div class="title_top" >

<div class="container-fluid">
		<div class="row-fluid">
		<div class="span6">
			<spring:message var="menu_navigation" code="menu.navigation"></spring:message>
			<spring:message var="menu_pos" code="pop.pop"></spring:message>
			<spring:message var="menu_pos_list" code="pop.editMap"></spring:message>
			<spring:message var="menu_pos_list1" code="pop.list"></spring:message>
			<h4 style="padding: 8px 0 0 10px;">
				<a onclick="back()"class="Color_back"><c:out value="${menu_navigation }"></c:out></a>
				<span> > </span>
				<a onclick="backListCMS()"class="Color_back"><c:out value="${menu_pos }"></c:out></a>
				<span> > </span>
				<span onclick="backListCMS()"class="Color_back"><c:out value="${menu_pos_list1}"></c:out></span>
				<span> > </span>
				<span class="Colorgray"><c:out value="${menu_pos_list}"></c:out></span>
			</h4>
		</div>	
	<div class="span6" >
				<div class="menu_images">
					<ul style="float: right;">
						<spring:message var="msg_buttonAdd" code="button.add"></spring:message>

						<spring:message var="msg_buttonDelete" code="button.delete"></spring:message>


					</ul>
				</div>
			</div>
	</div>
	</div>
</div>


<div class="container-fluid unit_bg_content">
<spring:message var="msg.save.success" code="msg.save.success"></spring:message>
<form:form method="POST" id="search_form_CMS"
		cssClass="form-horizontal margin_bottom_form" modelAttribute="bean">
		<ext:messages bean="${bean}"></ext:messages>
		<div class="row-fluid ">
		<div class="accordion-group">
			<div class="accordion-heading">
				<div class="row-fluid">
					<div class="span2 title1">
						<h3>
							<spring:message code="SearchMap.SearchMap"></spring:message>
						</h3>
					</div>
					<div class="span1 unit_accordion" style="text-align: right;"> 
						<a class="accordion-toggle" data-toggle="collapse"
										data-parent="#accordion2" href="#collapseOne"><i
										class="bms-icon-accordion-down bms-icon-accordion"></i></a>
					</div>
				</div>
			</div>
		</div>
		<div id="collapseOne" class="accordion-body collapse in border-group">
			<div class="accordion-inner">
				<div class="input-area">
						<div class="row-fluid">
							<div class="row-fluid">
								<div class="span12">
									<div class="text-right">
										<div class="span6">
											<input id="pac-input" style="width: 350px" value="${bean.entity.address_number}" name="entity.address_number" />
										</div>
										<div class="text-center">
											<form:select path="entity.buyOrPay" class="span2" itemValue="languageId">
												<form:option value="buy">
													<spring:message code="SearchMap.buy"></spring:message>
												</form:option>
												<form:option value="pay">
													<spring:message code="SearchMap.pay"></spring:message>
												</form:option>
											</form:select>
										</div>
									</div>
								</div>
								<br></br>
							</div>
							<div class="row-fluid">
								<div class="span12">
									<div class="span12">
										<div class="text-center">
											<spring:message var="latitude" code="SearchMap.latitude"></spring:message>
											<input class="latitude" name="entity.latitude" value="${latitude}" readonly="readonly" />
											<spring:message var="longitude" code="SearchMap.longitude"></spring:message>
											<input class="longitude" name="entity.longitude" value="${longitude}" readonly="readonly" />
											<spring:message var="city" code="SearchMap.city"></spring:message>
											<input id="administrative_area_level_1" name="entity.city" value="${city}" readonly="readonly" />
											<spring:message var="district" code="SearchMap.district"></spring:message>
											<input id="administrative_area_level_2" class="administrative_area_level_2" name="entity.district" value="${district}" readonly="readonly" />
											<div class="row-fluid">
												<div class="span12"></div>
											</div>
											<spring:message var="save_btn_msg" code="button.save"></spring:message>
										</div>
									</div>
								</div>
								<div class="row-fluid">
									<div class="span12">
										<div class="text-center">
											<sec:authorize access="!hasAnyRole('R010')">
												<input type="submit" value="${save_btn_msg}" class="btn_search_general" />
											</sec:authorize>

										</div>
									</div>
								</div>
							</div>
						</div>

					</div>
			</div>
		</div>
	
	</div>	

			
			<div class="row-fluid">
				<div id="map" style="height: 500px"></div>
			</div>
</form:form>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		initialize();
		$("#reset").click(function() {
			reset();
		});
		//$("#regionCombobox").trigger("change");
		$("#parentCombobox").change(function() {
			var html = '';
			html += $(this).find('option:selected').text();
			$('#autocomplete').val(html);
			$("#subCombobox option").remove();
			$.ajax({
					url : '${url}/master_data/branch/list_district_json?parent='+ $("#parentCombobox").val(),
					dataType : 'json',
					type : 'POST',
					cache : false,
					success : function(data) {
						$("#subCombobox").append("<option value=''><spring:message code='msg.choose'/></option>");
						$.each(data, function(i, object) {
							$("#subCombobox").append("<option value='"+object.districtId+"'>" + object.nameShow + "</option>");
						});
					}
				});
			

			});

		$('.cb_isprimary').change(function(event) {
			if($(this).is(':checked')){
				$(this).val('true')
			}else{
				$(this).val('false')
			}
		});

		$('.cb_type').change(function(event) {

		});

	});

	function backList() {
		document.location.href = "${url}/master_data/branch/list";
	}

	// This example displays an address form, using the autocomplete feature
	// of the Google Places API to help users fill in the information.
	// This example adds a search box to a map, using the Google Place Autocomplete
	// feature. People can enter geographical searches. The search box will return a
	// pick list containing a mix of places and predicted search terms.
	
	$("#pac-input").change(function() {
		var value=	$("#pac-input").val();
		if(value=='' || value==null){
			resetInput();
		}
	});

	function resetInput(){
		$('.latitude').val('');
		$('.longitude').val('');
		$("#administrative_area_level_2").val('');
		$("#administrative_area_level_1").val('');
	}
	
	function initAutocomplete() {	
		// Create the search box and link it to the UI element.
		var input = document.getElementById('pac-input');
		var autocomplete = new google.maps.places.Autocomplete(input); 	
		autocomplete.addListener('place_changed', function() {		
			initialize();
		});
	}
	
function initialize() {
    geocoder = new google.maps.Geocoder();

    var address = document.getElementById('pac-input').value;
    geocoder.geocode({ 'address': address }, function (results, status) {    	
        if (status == google.maps.GeocoderStatus.OK) {
            var myOptions = {
                zoom: 18,
                center: results[0].geometry.location,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };
            map = new google.maps.Map(document.getElementById("map"), myOptions);
           
            var iconBase = '${url}/static/images/mapgoogle.png';            
           
            $('.latitude').val(results[0].geometry.location.lat());
			$('.longitude').val(results[0].geometry.location.lng());
			if(results[0].address_components[4] != null){
				$("#administrative_area_level_2").val(results[0].address_components[4].long_name);
			}
			else{
				$("#administrative_area_level_2").val('');
			}
			if(results[0].address_components[3] != null){
				$("#administrative_area_level_1").val(results[0].address_components[3].long_name);
			}else{
				$("#administrative_area_level_1").val('');
			}
            
            var marker = new google.maps.Marker({ position: results[0].geometry.location,map:map });
            marker.setAnimation(google.maps.Animation.BOUNCE);
        } else {
            alert('Geocode was not successful for the following reason: ' + status);
        }
    });
}


function initialize() {
    geocoder = new google.maps.Geocoder();

    var address = document.getElementById('pac-input').value;
    geocoder.geocode({ 'address': address }, function (results, status) {    	
        if (status == google.maps.GeocoderStatus.OK) {
            var myOptions = {
                zoom: 18,
                center: results[0].geometry.location,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };
            map = new google.maps.Map(document.getElementById("map"), myOptions);
           
            var iconBase = '${url}/static/images/mapgoogle.png';            
           
            $('.latitude').val(results[0].geometry.location.lat());
			$('.longitude').val(results[0].geometry.location.lng());
			if(results[0].address_components[4] != null){
				$("#administrative_area_level_2").val(results[0].address_components[4].long_name);
			}
			else{
				$("#administrative_area_level_2").val('');
			}
			if(results[0].address_components[3] != null){
				$("#administrative_area_level_1").val(results[0].address_components[3].long_name);
			}else{
				$("#administrative_area_level_1").val('');
			}
            
            var marker = new google.maps.Marker({ position: results[0].geometry.location,map:map });
            marker.setAnimation(google.maps.Animation.BOUNCE);
        } else {
            alert('Geocode was not successful for the following reason: ' + status);
        }
    });
}




function backListCMS() {
	document.location.href = "${url}/master_data/pos/list";
	}
</script>
<script src="https://maps.googleapis.com/maps/api/js?libraries=places&callback=initAutocomplete"></script>
