/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

function getNumberOfInstances(){
	var x = 0;//x is the number of instances
	for(var instances in CKEDITOR.instances)
		{ x ++;}
	return x;
};

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	// Toolbar groups configuration.
	// Toolbar configuration generated automatically by the editor based on config.toolbarGroups.
	//http://ckeditor.com/latest/samples/plugins/toolbar/toolbar.html
	config.entities_latin = false;	
	config.toolbar = [
		
		{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
		{ name: 'document', groups: [ 'mode', 'document', 'doctools' ], items: [ 'Source', '-', 'Save', 'NewPage', 'Preview', 'Print', '-', 'Templates' ] },
		{ name: 'document', groups: [ 'mode', 'document', 'doctools' ], items: [ 'Source', '-', 'null', 'null', 'Preview', 'null', '-', 'null' ] },
		{ name: 'clipboard', groups: [ 'clipboard', 'undo' ], items: [ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo' ] },
		{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ], items: [ 'null', 'null', '-', 'null', '-', 'Scayt' ] },
		{ name: 'forms', items: [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField' ] },
		{ name: 'links', items: [ 'Link', 'Unlink', 'Anchor' ] },
		{ name: 'insert', items: [ 'Image', 'null', 'Table', 'null', 'null', 'null', 'SpecialChar', 'null' ] },
		{ name: 'colors'},
		{ name: 'document', groups: [ 'mode', 'document', 'doctools' ]},
		'/',	
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'RemoveFormat' ] },
		{ name: 'styles', items: [ 'Styles', 'Format', 'Font', 'FontSize' ] },
		{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ], items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'null', 'null', '-', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'null', 'null', 'null' ] },
		
	
		{ name: 'colors', items: [ 'TextColor', 'BGColor' ] },
		{ name: 'tools', items: [ 'Maximize', 'ShowBlocks' ] },
		{ name: 'others', items: [ '-' ] },
		
	];

	// Toolbar groups configuration.
	config.toolbarGroups = [
		{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
		{ name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
		{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ] },
		{ name: 'forms' },
		'/',
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ] },
		{ name: 'links' },
		{ name: 'insert' },
		'/',
		{ name: 'styles' },
		{ name: 'colors' },
		{ name: 'tools' },
		{ name: 'others' },
		{ name: 'about' }
	];
	
	if (getNumberOfInstances() >=2) {
		config.height = 80;
	}
	
};
