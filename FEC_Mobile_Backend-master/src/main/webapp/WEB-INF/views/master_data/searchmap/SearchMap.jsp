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
			<spring:message var="menu_Location" code="SearchMap.Location"></spring:message>
			<spring:message var="menu_banner_list" code="menu.branch.list"></spring:message>
		<h4 style="padding: 8px 0 0 10px;">
				<a onclick="backBranch()"class="Color_back"><c:out value="${menu_navigation }"></c:out></a>
				<span> > </span>
				<span class="Colorgray"><c:out value="${menu_Location}"></c:out></span>
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
<ext:messages bean="${bean}"></ext:messages>
<form:form method="POST" id="search_form_CMS"
		cssClass="form-horizontal margin_bottom_form" modelAttribute="bean">
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
				<div class="span12">
				<div class="text-center">
				<input id="pac-input"  style="width:350px"/>
				</div>
			</div>
	<div class="row-fluid">
		<div class="span12">
			<div class="span12">
			<div class="text-center">
			<spring:message var="latitude" code="SearchMap.latitude"></spring:message>
			<input class="latitude" name="entity.latitude" value="${latitude}"  readonly="readonly"/>
			<spring:message var="longitude" code="SearchMap.longitude"></spring:message>
			<input class="longitude" name="entity.longitude" value="${longitude}"  readonly="readonly"/>
			<spring:message var="city" code="SearchMap.city"></spring:message>
			<input id="administrative_area_level_1"  name="entity.city" value="${city}"  readonly="readonly"/>
			<!-- <input id="street_number">
			<input id="route">
			
			<input id="locality">
			<input id="country">
			<input id="postal_code"> -->
		
			<spring:message var="district" code="SearchMap.district"></spring:message>
			<input id="administrative_area_level_2" class="administrative_area_level_2" name="entity.district" value="${district}" readonly="readonly"/>
			<div class="row-fluid">
			<div class="span12">
			</div> 
			</div>
											<div class="row-fluid">
												<div class="span12">
													<div class="span6">
														<spring:message var="branch" code="menu.branch"></spring:message>
														<input id="branchName"name="branchName" placeholder="Branch Name" >
													</div>
													<div class="span6">
														<form:select path="buyOrPay" class="span4" itemValue="languageId">
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
		var map = new google.maps.Map(document.getElementById('map'), {
			center : {lat: 10.783361, lng: 106.697809},
			zoom : 13,
			mapTypeId : google.maps.MapTypeId.ROADMAP
			
		});

		
		// Create the search box and link it to the UI element.
		var input = document.getElementById('pac-input');
		var autocomplete = new google.maps.places.Autocomplete(input);
  		autocomplete.bindTo('bounds', map);

  		//XOA 
  		

		var componentForm = {
			/* street_number: 'long_name',
			route: 'long_name',
			locality: 'long_name',
			country: 'long_name',
			postal_code: 'short_name', */
			administrative_area_level_1: 'long_name',
			administrative_area_level_2: 'long_name',
		};

		// [START region_getplaces]
		// Listen for the event fired when the user selects a prediction and retrieve
		// more details for that place.
		autocomplete.addListener('place_changed', function() {
			resetInput();
			var place = autocomplete.getPlace();


			if (place.length == 0) {
				return;
			}

			for (var i = 0; i < place.address_components.length; i++) {
			    var addressType = place.address_components[i].types[0];
			    if (componentForm[addressType]) {
			      	var val = place.address_components[i][componentForm[addressType]];
			      	document.getElementById(addressType).value = val;
			    }
			}

			// For each place, get the icon, name and location.
			var bounds = new google.maps.LatLngBounds();
				
			$('.latitude').val(place.geometry.location.lat());
			$('.longitude').val(place.geometry.location.lng());
			
			
		

			if($("#administrative_area_level_2").val() == ''){
				$("#branchName").attr("readonly","readonly");
				$("#buyOrPay").attr("disabled","disabled");
			}else{
				$("#branchName").removeAttr("readonly");
				 $("#buyOrPay").removeAttr("disabled");
			}
			
			var icon = {
				url : place.icon,
				size : new google.maps.Size(71, 71),
				origin : new google.maps.Point(0, 0),
				anchor : new google.maps.Point(17, 34),
				scaledSize : new google.maps.Size(25, 25)
			
			};

			// Create a marker for each place.
			var marker = new google.maps.Marker({
				map : map,
				icon : icon,
				title : place.name,
				position : place.geometry.location
			});
			marker.setAnimation(google.maps.Animation.BOUNCE);
			if (place.geometry.viewport) {
				// Only geocodes have viewport.
				bounds.union(place.geometry.viewport);
			} else {
				bounds.extend(place.geometry.location);
			}

			map.fitBounds(bounds);
		});
	}
	

	$("#pac-input").click(function(){
		
	    //focus 'to' field
	});
/* 	$("#pac-input").change(function() {
		if($("#administrative_area_level_2").val() == 'District'){
			$("#branchName").attr("readonly","readonly");
			$("#buyOrPay").attr("disabled","disabled");
		}else{
			$("#branchName").removeAttr("readonly");
			 $("#buyOrPay").removeAttr("disabled");
		}
		
	}); */
		
	// [END region_getplaces]
	
</script>
<script
	src="https://maps.googleapis.com/maps/api/js?libraries=places&callback=initAutocomplete"
	async defer></script>
