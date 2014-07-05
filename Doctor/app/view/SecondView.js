Ext.define('Doctor.view.SecondView', {
	extend: 'Ext.Panel',
	requires: "Ext.form.FieldSet",
	alias: "widget.secondview",

	initialize: function() {

		this.callParent(arguments)

		var backButton = {
			xtype: "button",
			ui: "back",
			text: "Back",
			handler: this.onBackPress,
		};

		var topToolbar = {
				xtype: "toolbar",
				title: 'That\'s What Doctor Said',
				docked: "top",
				items: [
				        backButton
				        ]
		};

		var bottomToolbar = {
				xtype: "toolbar",
				title: 'Powered by Raxa',
				docked: "bottom",
		};

		var textArea = {
				xtype: 'fieldset',
				items: [
				        {
				        	xtype: 'textareafield',
				        	id: 'laymanText',
				        	placeHolder: 'Medical Description in Layman language translated to desired language',
				        	readOnly: true
				        }		
				        ]	
		};

		var newButton = {
				xtype: "button",
				text: 'Show Me More'
		};

		this.add([topToolbar,textArea,newButton,bottomToolbar]);

	},
	
	onBackPress: function(){
		Ext.Viewport.remove(Ext.Viewport.getActiveItem(), true);
		Ext.Viewport.setActiveItem(Ext.create('Doctor.view.Main')); 
	}
});
