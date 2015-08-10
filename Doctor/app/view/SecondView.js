Ext.define('Doctor.view.SecondView', {
	extend: 'Ext.Panel',
	requires: "Ext.form.FieldSet",
	alias: "widget.secondview",
	id: 'secondview',
	initialize: function() {
		this.callParent(arguments);
	},
	showAnimation: {
        type: 'slide',
        direction: 'left'
    },
	addNewDrug: function(j){
		if(Ext.getCmp('carouselBody').getItems().length-1 < j){
			console.log('i need more carousels');
			for(var i=Ext.getCmp('carouselBody').getItems().length-1; i<j; i++){
				console.log('i am called')
				Ext.getCmp('carouselBody').add({
					xtype: 'fieldset',
					id: 'innerItem'+i,
					items: [{
			        	xtype: 'textfield',
			        	label: 'Drug',
			        	labelWrap: true,
			        	id: 'drugName'+i,
			        	placeHolder: 'Drug Name',
			        	readOnly: true
			        },
			        {
			        	xtype: 'textfield',
			        	label: 'Prescription Meaning',
			        	labelWrap: true,
			        	id: 'laymanText'+i,
			        	placeHolder: 'What Doctor Meant',
			        	readOnly: true
			        },
			        {
			        	xtype: 'textareafield',
			        	label: 'Indication',
			        	labelWrap: true,
			        	id: 'drugIndication'+i,
			        	placeHolder: 'Drug Indications',
			        	readOnly: true
			        },
			        {
			        	xtype: 'textareafield',
			        	label: 'Contra Indications',
			        	labelWrap: true,
			        	id: 'drugContraindication'+i,
			        	placeHolder: 'Drug ContraIndications',
			        	readOnly: true
			        },
			        {
			        	xtype: 'textareafield',
			        	label: 'Cautions',
			        	labelWrap: true,
			        	id: 'drugCaution'+i,
			        	placeHolder: 'Drug Cautions',
			        	readOnly: true
			        },
			        {
			        	xtype: 'textareafield',
			        	label: 'Side Effects',
			        	labelWrap: true,
			        	id: 'drugSideEffects'+i,
			        	placeHolder: 'Drug SideEffects',
			        	readOnly: true
			        }]	
				});		
			}
		}else if(Ext.getCmp('carouselBody').getItems().length-1 > j){
			for(var k=j; k<Ext.getCmp('carouselBody').getItems().length; k++){
				console.log('delete: '+k)
				Ext.getCmp('innerItem'+k).destroy()
			}
		}
		
	},
	config: {
		items: [{
			xtype: "toolbar",
			title: 'That\'s What Doctor Said',
			docked: "top",
			items: [{
				xtype: "button",
				ui: "back",
				text: "Back",
				id: 'backOnSecondView',
			}]
		},
		{
			xtype: "toolbar",
			title: 'Powered by Raxa',
			docked: "bottom",
		},
		{
            xtype: 'carousel',
            id: 'carouselBody',
            fullscreen: true,
            width: '100%',
            height: '100%',
            centered: true,
            items:[]
		},


		]
	}
});
