(function($) {
	Class('Identification.application.List', {
		init:function() {
			this.bindEvent();
		},
		
		bindEvent: function() {
			var that = this;
			
			$("#applicationSearchBtn").click(function(){
				$("#page").val(1);
				that.searchList();
			});	
			
	        //文件导入
        	identification.fileUpload("/application/import","applicationImoprtBtn", "", "");	
        	
           //文件导出
        	$("#applicationExportBtn").click(function(){
        		$("#loading").show();
				location.href="/application/export";   
				$("#loading").hide();

			});	
        	
		},
		
		searchList : function() {
			var that = this;
			
			$("#applicationResultList").empty();
			identification.ajax("/application/search/" + $("#page").val(), null, "html", function(res) {
				$("#applicationResultList").html(res);
			});
		}
		
	});
 
})(jQuery);	