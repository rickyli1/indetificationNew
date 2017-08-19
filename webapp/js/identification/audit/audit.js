(function($) {
	Class('Identification.audit.List', {
		init:function() {
			this.bindEvent();
			this.formatHistory();
		},
		
		bindEvent: function() {
			var that = this;
			
			$("#auditSearchBtn").click(function(){
				$("#page").val(1);
				that.searchList();
			});	
			
	       
           //文件导出
        	$("#auditExportBtn").click(function(){
        		$("#loading").show();
				location.href="/application/export?applicationDate="+  $("#requireData").val().trim()+"&applicationRepairer=" +  $("#requireCompany").val().trim()+"&equimentName="+ $("#equipmentName").val()+"&orderType="+ $("#orderType").val();
				$("#loading").hide();

			});	
        	
        	that.initCalendar();
        	
        	//order change
        	$("#orderType").change(function() {
        		$("#page").val(1);
				that.searchList();
        	});
        	
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
			identification.ajax("/audit/search/" + $("#page").val(), JSON.stringify(params), "html", function(res) {
				$("#applicationResultList").html(res);
				that.formatHistory();
			});
		},
		
		formatHistory:function() {
			$(".history").each(function(index,item) {
				$(item).html($(item).text().replace(/\n/ig, "<br>"));
			})
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