Ext.define('Doctor.view.Main', {
	extend: 'Ext.form.Panel',
	requires: "Ext.form.FieldSet",
	alias: "widget.takeprescription",
	id: 'main',
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
	//TODO: move this function to controller
	//Views should be as minimal as possible and should contain only view info
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
			url: "http://ec2-54-186-181-202.us-west-2.compute.amazonaws.com:8080/ctakes2/rest/ctakes/hello",
			params: {
				text: prescription,	
				language: language
			},
			success: function(response){
				var obj = Ext.decode(response.responseText);
				console.log(obj);
				if(Ext.getCmp('secondview') === undefined){
					Ext.create('Doctor.view.SecondView');
				}
				Ext.getCmp('main').hide();
				Ext.getCmp('secondview').show();
				Ext.getCmp('carouselBody').setActiveItem(0);

				//sets up views needed for drugs
				Ext.getCmp('secondview').addNewDrug(obj.length);

				//sets the values appropriately
				for(var i=0; i<obj.length; i++){	
					Ext.getCmp('drugName'+i).setValue(obj[i]["drug"]);
					Ext.getCmp('laymanText'+i).setValue(obj[i]["naturalText"]);
					Ext.getCmp('drugIndication'+i).setValue(obj[i]["indication"]);
					Ext.getCmp('drugContraindication'+i).setValue(obj[i]["contraindication"]);
					Ext.getCmp('drugCaution'+i).setValue(obj[i]["caution"]);
					Ext.getCmp('drugSideEffects'+i).setValue(obj[i]["sideeffects"]);
				}
				
			},
			failure: function(response){
				console.log('rest failed')

				
			}
		});
	}

});
