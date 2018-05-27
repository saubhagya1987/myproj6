<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ext" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="ext-form" tagdir="/WEB-INF/tags/form"%>
<c:set var="url" value="${pageContext.request.contextPath}"></c:set>
<spring:message var="msg.save.success" code="msg.save.success"></spring:message>
<spring:message var="msg_deleteQuestion"
	code="msg.alert.delete.question"></spring:message>

<!-- start title -->
<div class="title_top" >
<div class="container-fluid">
		<div class="row-fluid">
		<div class="span6">
		<spring:message var="menu_hobby" code="customer.hobby.title"></spring:message>
		<spring:message var="hobby_list" code="hobby.List"></spring:message>
		<h4 style="padding: 8px 0 0 10px;">
		  <a onclick="back()"class="Color_back">  <c:out value="${menu_hobby }"></c:out></a>
		  <span> > </span>
		  <span class="Colorgray"class="Color_back">  <c:out value="${hobby_list }"></c:out></span>
		</h4>
		</div>
	<div class="span6" >
				<div class="menu_images">
					<ul style="float: right;">
						<spring:message var="msg_buttonAdd" code="button.add"></spring:message>

						<spring:message var="msg_buttonDelete" code="button.delete"></spring:message>


						<!-- <li class="new"><a onclick="showTextVal(true)"
							title="Add new"><span class="new_text"></span></a></li> -->
					</ul>
				</div>
			</div>
	</div>
	</div>
</div>

<!-- and title -->
<div class="container-fluid unit_bg_content">
<form:form method="POST" id="search_form" cssClass="form-horizontal margin_bottom_form"
		modelAttribute="bean">


				<input id="pac-input" />
				<div id="map" style="height: 300px"></div>
				<input  class="latitude" />
				<input  class="longitude" />
				<input id="administrative_area_level_1" class="administrative_area_level_1">
				<input id="administrative_area_level_2" class="administrative_area_level_2">
	
	
</form:form>	
<script type="text/javascript">
	$(document).ready(function() {
		$("#reset").click(function() {
			reset();
		});
		//$("#regionCombobox").trigger("change");
			

	});
	function backList() {
		document.location.href = "${url}/master_data/branch/list";
	}

	// This example displays an address form, using the autocomplete feature
	// of the Google Places API to help users fill in the information.
	// This example adds a search box to a map, using the Google Place Autocomplete
	// feature. People can enter geographical searches. The search box will return a
	// pick list containing a mix of places and predicted search terms.
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

		var componentForm = {
			/*street_number: 'short_name',
			route: 'long_name',
			locality: 'long_name',
			country: 'long_name',
			postal_code: 'short_name',*/
			administrative_area_level_1: 'long_name',
			administrative_area_level_2: 'long_name',
		};

		// [START region_getplaces]
		// Listen for the event fired when the user selects a prediction and retrieve
		// more details for that place.
		autocomplete.addListener('place_changed', function() {
			var place = autocomplete.getPlace();
			var city ;
			var district;
			
			for(var i = 0; i < place.address_components.length; i += 1) {
			  var addressObj = place.address_components[i];
			  for(var j = 0; j < addressObj.types.length; j += 1) {
			    if (addressObj.types[j] === 'administrative_area_level_1') {
			      console.log(addressObj.types[j]); // confirm that this is 'country'
			      console.log(addressObj.long_name); // confirm that this is the country name
			      district=addressObj.long_name;
			    }
			    if (addressObj.types[j] === 'administrative_area_level_2') {
			      console.log(addressObj.types[j]); // confirm that this is 'country'
			      console.log(addressObj.long_name); // confirm that this is the country name
			      city=addressObj.long_name;
				    }
			  }
			}

			if (place.length == 0) {
				return;
			}
			
			var latitude= place.geometry.location.lat();
			var longitude=place.geometry.location.lng();

			console.log(latitude);

			$('.latitude').val(latitude);
			$('.longitude').val(longitude);
			$('#administrative_area_level_1').val(city);
			$('#administrative_area_level_2').val(district);
			// For each place, get the icon, name and location.
			var bounds = new google.maps.LatLngBounds();
				
			
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

			if (place.geometry.viewport) {
				// Only geocodes have viewport.
				bounds.union(place.geometry.viewport);
			} else {
				bounds.extend(place.geometry.location);
			}

			map.fitBounds(bounds);
		});
	}
	// [END region_getplaces]
</script>
<script
	src="https://maps.googleapis.com/maps/api/js?libraries=places&callback=initAutocomplete"
	async defer></script>
		</div>
