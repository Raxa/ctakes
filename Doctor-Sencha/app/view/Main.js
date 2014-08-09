Ext.define('Doctor.view.Main', {
	extend: 'Ext.Panel',
	requires: ["Ext.form.FieldSet","Ext.Ajax"],

	initialize: function() {

		this.callParent(arguments)

		var topToolbar = {
				xtype: "toolbar",
				title: 'Autocomplete',
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
				        	id: 'inputText',
						name:'inputText',
				        	placeHolder: 'Enter your prescription with acronyms in capital',
						maxRows: 10,
						enableKeyEvents: true,
						listeners : {
							keyup:this.onSearchKeyUp
						}
				        }
				        ]	
		};

		var firstRow = {
			items: [{
				xtype: 'spacer',
				}, {
					layout: {
						type: 'hbox'
					},
					items: [{
						xtype: 'button',
						id: 'button0',
						text: 'Suggestion 1',
						ui: 'decline',
						hidden: 'true',
						handler: this.onButtonPress
						},{
						xtype: 'spacer',
						// The amount of spacing between buttons
						width: 20
						}, {
						xtype: 'button',
						id: 'button1',
						text: 'Suggestion 2',
						ui: 'decline',
						hidden: 'true',
						handler: this.onButtonPress
						},{
						xtype: 'spacer',
						// The amount of spacing between buttons
						width: 20
						},{
						xtype: 'button',
						id: 'button2',
						text: 'Suggestion 3',
						ui: 'decline',
						hidden: 'true',
						handler: this.onButtonPress						
						},{
						xtype: 'spacer',
						// The amount of spacing between buttons
						width: 20
						}, {
						xtype: 'button',
						id: 'button3',
						text: 'Suggestion 4',
						ui: 'decline',
						hidden: 'true',
						handler: this.onButtonPress
						},{
						xtype: 'spacer',
						// The amount of spacing between buttons
						width: 20
						}, {
						xtype: 'button',
						id: 'button4',
						text: 'Suggestion 5',
						ui: 'decline',
						hidden: 'true',
						handler: this.onButtonPress
						},{
						xtype: 'spacer',
						// The amount of spacing between buttons
						width: 20
						}, {
						xtype: 'button',
						id: 'button5',
						text: 'Suggestion 6',
						ui: 'decline',
						hidden: 'true',
						handler: this.onButtonPress			
						}
					]
					}, {
						xtype: 'spacer',
					}]
		};		

		var linespace = {
			xtype:'spacer',
		};

		var secondRow = {
			items: [{
				xtype: 'spacer',
				}, {
					layout: {
						type: 'hbox'
					},
					items: [{
						xtype: 'button',
						id: 'button6',
						text: 'Suggestion 1',
						ui: 'confirm',
						hidden: 'true',
						handler: this.onButtonPress
						},{
						xtype: 'spacer',
						// The amount of spacing between buttons
						width: 20
						}, {
						xtype: 'button',
						id: 'button7',
						text: 'Suggestion 2',
						ui: 'confirm',
						hidden: 'true',
						handler: this.onButtonPress
						},{
						xtype: 'spacer',
						// The amount of spacing between buttons
						width: 20
						},{
						xtype: 'button',
						id: 'button8',
						text: 'Suggestion 3',
						ui: 'confirm',
						hidden: 'true',
						handler: this.onButtonPress		
						},{
						xtype: 'spacer',
						// The amount of spacing between buttons
						width: 20
						}, {
						xtype: 'button',
						id: 'button9',
						text: 'Suggestion 4',
						ui: 'confirm',
						hidden: 'true',
						handler: this.onButtonPress
						},{
						xtype: 'spacer',
						// The amount of spacing between buttons
						width: 20
						}, {
						xtype: 'button',
						id: 'button10',
						text: 'Suggestion 5',
						ui: 'confirm',
						hidden: 'true',
						handler: this.onButtonPress
						},{
						xtype: 'spacer',
						// The amount of spacing between buttons
						width: 20
						}, {
						xtype: 'button',
						id: 'button11',
						text: 'Suggestion 6',
						ui: 'confirm',
						hidden: 'true',
						handler: this.onButtonPress			
						}
					]
					}, {
						xtype: 'spacer',
					}]
		};
		this.add([topToolbar,textArea,firstRow,linespace,linespace,secondRow,bottomToolbar]);
		
	},

	onButtonPress: function(btn){
		console.log("Change Input text value");
		var inputText = Ext.getCmp('inputText').getValue();
		if (inputText.lastIndexOf(' ')==-1) {
			inputText = btn.getText();
		}
		else {
			inputText = inputText.substr(0,inputText.lastIndexOf(' ')+1)+btn.getText() ;
		}
		
		Ext.getCmp("inputText").setValue(inputText);

	},

	onSearchKeyUp: function() {
		console.log("Call to the autocomplete server");
		var inputText = Ext.getCmp('inputText').getValue();
		var textToQuery;
		if (inputText.lastIndexOf(' ')==-1) {
			//console.log(inputText);
			textToQuery = inputText;
		}
		else {
			//console.log(inputText.substr(inputText.lastIndexOf(' '),inputText.length));
			textToQuery = inputText.substr(inputText.lastIndexOf(' '),inputText.length).trim();
		}
		console.log("Text to Query "+textToQuery);
		if (textToQuery.length>=2){
			Ext.Ajax.request({
				method: 'GET',
				type: 'jsonp',
				withCredentials: false,
				useDefaultXhrHeader: false,
				url: "http://ec2-54-186-181-202.us-west-2.compute.amazonaws.com:8080/Doctor-Backend/rest/doctor/hello",
				params: {
					text: textToQuery,
				},
				success: function(response){
					var obj = Ext.decode(response.responseText);
					console.log("Got response yay");
					console.log(obj);
					var drugRows = [];
					var testRows = [];
					for(i=0;i<obj.length;i++){
						if(obj[i]["Type"]=="Drug"){
							drugRows.push(obj[i]["Text"]);
						}
						else if (obj[i]["Type"]=="Test"){
							testRows.push(obj[i]["Text"]);
						}
					}
					console.log(drugRows.length);
					for(i=0;i<drugRows.length;i++){
						var id = "button"+i;
						Ext.getCmp(id).show().setText(drugRows[i]);
						console.log("Show"+id);
					}
					for(i=0;i<testRows.length;i++){
						var id = "button"+(6+i);
						Ext.getCmp(id).show().setText(testRows[i]);
						console.log("Show test"+id);
					}
					
					for(i=drugRows.length;i<6;i++){
						var id = "button"+i;
						console.log("Hide "+id);
						Ext.getCmp(id).hide();
					}
					
					for(i=testRows.length;i<6;i++){
						var id = "button"+(6+i);
						console.log("Hide test"+id);
						Ext.getCmp(id).hide();
					}
				}
			});
		}
		console.log("Finished");
	}
});
