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
				location.href="/application/export?applicationDate="+  $("#requireData").val().trim()+"&applicationRepairer=" +  $("#requireCompany").val().trim()+"&equimentName="+ $("#equipmentName").val()+"&orderType="+ $("#orderType").val();
				$("#loading").hide();

			});	
        	
        	that.initCalendar();
        	
		},
		
		initCalendar : function() {
			identification.initCalendarByClass('form_datetime','form_date','form_time');			
		},

		
		// 设置参数隐藏域
		setSearchParam: function() {
			$("#requireData").val($("#applicationDateHid").val().trim());
			$("#requireCompany").val($("#applicationRepairerHid").val().trim());
			$("#equipmentName").val($("#equimentNameHid").val().trim());
		},
		searchList : function() {
			var that = this;
			
			$("#applicationResultList").empty();
			var params = that.getParams();
			console.log(params);
			identification.ajax("/application/search/" + $("#page").val(), JSON.stringify(params), "html", function(res) {
				$("#applicationResultList").html(res);
			});
		},
		
		getParams : function() {
			var data = {	
					"applicationDate": $("#requireData").val(),
					"applicationRepairer":$("#requireCompany").val(),
					"equimentName":$("#equipmentName").val(),
					"orderType":$("#orderType").val()
				};
		   return data;
		}
		
	});
 
})(jQuery);	