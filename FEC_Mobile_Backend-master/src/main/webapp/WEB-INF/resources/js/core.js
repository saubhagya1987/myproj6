/**
 * 
 */
var core = {
	message : function(status, message){
		console.log(status);
		var success = '<div class="alert alert-success fade in">'
			+'<span>'+message+'</span>'
			+'<button class="close" data-dismiss="alert" type="button">×</button>'
		+'</div>';
		var error = '<div class="alert alert-error fade in">'
			+'<span>'+message+'</span>'
			+'<button class="close" data-dismiss="alert" type="button">×</button>'
		+'</div>';
		if(status == "success"){
			return success;
		}else{
			return error;
		}
	},
	//init Plupload
	InitPlupload : function(browse_button, container, uploadUrl, multi_selection, max_file_size, mime_types, filelist, consoleLog, FileUploaded, UploadComplete, url, resize) {
		var uploader = new plupload.Uploader({
			runtimes : 'html5,flash,silverlight,html4',

			browse_button : browse_button, // you can pass in id...
			container : document.getElementById(container), // ... or DOM Element
			// itself

			url : uploadUrl,

			// Resize images on client-side if we can
			resize : resize,

			multi_selection : multi_selection,

			filters : {
				max_file_size : max_file_size,
				mime_types : mime_types
			},

			// Flash settings
			flash_swf_url : url + '/static/js/plupload-2.1.2/Moxie.swf',

			// Silverlight settings
			silverlight_xap_url : url + '/static/js/plupload-2.1.2/Moxie.xap',

			init : {
				PostInit : function() {
					document.getElementById(filelist).innerHTML = '';
				},

				FilesAdded : function(up, files) {
					plupload.each(files, function(file) {
						document.getElementById(filelist).innerHTML += '<div id="' + file.id + '"><a title="download file" href="'+url+'/ajax/download?fileName='+ file.name +'">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></div>';
					});

					$("#" + filelist).show();
					uploader.start(); // auto start when FilesAdded
				},

				UploadProgress : function(up, file) {
					document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
				},

				Error : function(up, err) {
					console.log("\nError #" + err.code + ": " + err.message);
					document.getElementById(consoleLog).innerHTML = "\nError #" + err.code + ": " + err.message;
					$("#" + consoleLog).show();
				},

				FileUploaded : FileUploaded,
				UploadComplete : UploadComplete
			}
		});

		uploader.init();
	}, 

	loadSttTableJs : function(selecter){
		var tr = $(selecter).find('tr');
		$.each(tr, function(index, val) {
			$(this).find('.stt').text(index + 1);
		});
	},

	typeahead : function(lstData, selecter){
		var labels, mapped;
		$(selecter).typeahead({
			minLength : 3,
			source: function (query, process) {
				labels = [];
	            mapped = {};
				$.each(data, function (i, object) {
		            var query_label = object.stateName + ' - ' + object.stateCode;
		            // example query_label: "AC/DC - Shoot To Thrill"

		            // mapping song object
		            mapped[query_label] = object;
		            labels.push(query_label);
	          	});

	          	process(labels);
			},
			// Method fired when a result is selected.
	        updater: function (query_label) {
	            var song = mapped[query_label];

	            // Here gonna add some js to save song id to hidden input 
	            // ...

	            // If user selects an item, the inputed value will be for example "AC/DC - Shoot To Thrill {3}"
	            var input_label = query_label + '{'+ song.stateCode + '}';
	            return input_label;
	        },
	        matcher: function (item) {
			    if (item.toLowerCase().indexOf(this.query.trim().toLowerCase()) != -1) {
			        return false;
			    }
			},
	          // Method responsible for element view
	        highlighter: function (query_label) {
	            var song = mapped[query_label];
	            var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&');

	            var highlighted_label = query_label.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
	              return '<strong>' + match + '</strong>'
	            });

	            // Item will be viewed as "AC/DC - Shoot To Thrill (Back to Black)
	            var view_label = highlighted_label + ' (<i>' + song.stateCode + '</i>)';            
	            return view_label;
	        }
		}); // end typeahead
	},

	multipleSelect : function(selecter, single, multiple){
		$(selecter).multipleSelect({
	        filter: true,
	        single: single,
	        multiple: multiple,
	    });
	},
	
	checkEmail : function(email){
		var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;	
	    if (filter.test(email)) {
	        return true;
	    }
	    else {
	        return false;
	    }
	},
};

var service = {

	config : {
		hostName : window.location.hostname,
	},

	loadLstMenuCard : function(url){
		var rs;
		$.ajax({
    		url: url+'/homepage/list_menu_card',
    		type: 'POST',
    		dataType: 'json',
    		async : false,
    		success : function(data){
    			rs = data;
    		}
    	})
    	return rs;
	}
}

