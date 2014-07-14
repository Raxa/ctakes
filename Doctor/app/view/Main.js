Ext.define('Doctor.view.Main', {
	extend: 'Ext.form.Panel',
	requires: "Ext.form.FieldSet",
	alias: "widget.takeprescription",

	initialize: function() {

		this.callParent(arguments)

		var topToolbar = {
			xtype: "toolbar",
			title: 'That\'s What Doctor Said',
			docked: "top",
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
				        	name: 'doctorPrescription',
				        	id: 'doctorPrescription',
				        	maxRows: 15,
				        	placeHolder: 'Enter the Doctor\'s Prescription here with acronyms in capital',
				        	required: true
				        },
				        {
				        	xtype: 'selectfield',
				        	id: 'selLang',
				        	autoSelect: true,
				        	options: [
				        	          {text: 'English', value: 'english'},
				        	          {text: 'Hindi', value: 'hindi'},
				        	          {text: 'Urdu', value: 'urdu'}
				        	          ]				
				        }				
				        ]	
		};

		var newButton = {
				xtype: "button",
				text: 'Convert',
				ui: 'action',
				handler: this.onConvertPress,
		};

		this.add([topToolbar,textArea,newButton,bottomToolbar]);

	},

	onConvertPress: function() {
		console.log("Call to the ctakes backend server");
		var prescription = Ext.getCmp('doctorPrescription').getValue();
		var language = Ext.getCmp('selLang').getValue();
		console.log(prescription);
		console.log(language);
		
		Ext.Ajax.on('beforerequest', function (con, opt) {
			Ext.Viewport.setMasked({
			xtype: 'loadmask',
			message: 'Please wait…',
			indicator: true
			});
		}, this);
		
		Ext.Ajax.on('requestcomplete', function (con, res, opt) {
			Ext.Viewport.setMasked(false);
		}, this);
		
		Ext.Ajax.request({
			method: 'GET',
			type: 'jsonp',
			withCredentials: false,
			useDefaultXhrHeader: false,
			url: "http://localhost:8080/ctakes/rest/ctakes/hello",
			params: {
				text: prescription,	
				language: language
			},
			success: function(response){
				var obj = Ext.decode(response.responseText);
				console.log(obj[0]);
				Ext.Viewport.remove(Ext.Viewport.getActiveItem(), true);
				Ext.Viewport.setActiveItem(Ext.create('Doctor.view.SecondView'));
				Ext.getCmp('drugName').setValue(obj[0]["drug"]);
				Ext.getCmp('laymanText').setValue(obj[0]["naturalText"]);
				Ext.getCmp('drugIndication').setValue(obj[0]["indication"]);
				Ext.getCmp('drugContraindication').setValue(obj[0]["contraindication"]);
				Ext.getCmp('drugCaution').setValue(obj[0]["caution"]);
				Ext.getCmp('drugSideEffects').setValue(obj[0]["sideeffects"]);
				
				
			}
		});
	}

});
