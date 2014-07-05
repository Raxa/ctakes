Ext.define('Doctor.view.Main', {
    extend: 'Ext.Container',
	alias: "widget.secondview",
    
	initialize: function() {
	
		this.callParent(arguments)
		
		var backButton = {
            xtype: "button",
            ui: "back",
            text: "Back"
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
					placeHolder: 'Medical Description in Layman language translated to desired language'
					readOnly: True
				}		
			]	
		};
		
		var newButton = {
            xtype: "button",
            text: 'Show Me More'
        };
 
		this.add([topToolbar,textArea,newButton,bottomToolbar]);
	
	}
});
