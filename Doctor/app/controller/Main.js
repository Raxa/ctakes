Ext.define('Doctor.controller.Main', {
    extend: 'Ext.app.Controller',
    config: {
        refs: {
            backOnSecondView: '#backOnSecondView'
        },
        control: {
            backOnSecondView: {
                tap: 'onBackPress'
            }
        }
    },

	onBackPress: function(){
		// Ext.Viewport.remove(Ext.Viewport.getActiveItem(), true);
		// Ext.Viewport.setActiveItem(Ext.create('Doctor.view.Main')); 

		Ext.getCmp('secondview').hide();
		Ext.getCmp('main').show();

	}
});