(function($) {
	Class('Identification.repairer.List', {
		init:function() {
			this.bindEvent();
		},
		
		bindEvent: function() {
			var that = this;
			
			$("#searchBtn").click(function(){
				$("#page").val(1);
				that.searchList();
			});	
			
	        //文件导入
        	identification.fileUpload("/repairer/import","repairerImoprtBtn", "", "");	
        	
           //文件导出
        	$("#repairerExportBtn").click(function(){
        		$("#loading").show();
				location.href="/repairer/export";   
				$("#loading").hide();

			});	
        	
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