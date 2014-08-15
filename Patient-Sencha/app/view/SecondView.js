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
				        	xtype: 'textfield',
				        	label: 'Drug',
				        	labelWrap: true,
				        	id: 'drugName',
				        	placeHolder: 'Drug Name',
				        	readOnly: true
				        },
				        {
				        	xtype: 'textfield',
				        	label: 'Prescription Meaning',
				        	labelWrap: true,
				        	id: 'laymanText',
				        	placeHolder: 'What Doctor Meant',
				        	readOnly: true
				        },
				        {
				        	xtype: 'textareafield',
				        	label: 'Indication',
				        	labelWrap: true,
				        	id: 'drugIndication',
				        	placeHolder: 'Drug Indications',
				        	readOnly: true
				        },
				        {
				        	xtype: 'textareafield',
				        	label: 'Contra Indications',
				        	labelWrap: true,
				        	id: 'drugContraindication',
				        	placeHolder: 'Drug ContraIndications',
				        	readOnly: true
				        },
				        {
				        	xtype: 'textareafield',
				        	label: 'Cautions',
				        	labelWrap: true,
				        	id: 'drugCaution',
				        	placeHolder: 'Drug Cautions',
				        	readOnly: true
				        },
				        {
				        	xtype: 'textareafield',
				        	label: 'Side Effects',
				        	labelWrap: true,
				        	id: 'drugSideEffects',
				        	placeHolder: 'Drug SideEffects',
				        	readOnly: true
				        },
				        

				        ]	
		};


		this.add([topToolbar,textArea,bottomToolbar]);

	},

	onBackPress: function(){
		Ext.Viewport.remove(Ext.Viewport.getActiveItem(), true);
		Ext.Viewport.setActiveItem(Ext.create('Doctor.view.Main')); 
	}

});
