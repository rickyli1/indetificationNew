(function($) {
	Class('Identification.repairer.List',{
		init:function() {
			this.bindEvent();
		},
		
		bindEvent: function() {
			var that = this;
			
			$("#searchBtn").click(function(){
				$("#page").val(1);
				that.searchList();
			});	
			
	        //申请文件上传
        	identification.fileUpload("/repairer/import","repairerImoprtBtn", "", "");	  

		},
		
		searchList : function() {
			var that = this;
			
			$("#repairerResultList").empty();
			var data = this.getSearchConditions();
			identification.ajax("/repairer/search/" + $("#page").val(), null, "html", function(res) {
				$("#repairerResultList").html(res);
			});
		}
		
	});
 
})(jQuery);	