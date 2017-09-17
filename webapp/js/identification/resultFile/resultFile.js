(function($) {
	Class('Identification.resultFile.List', {
		init:function() {
			this.bindEvent();
		},
		
		bindEvent: function() {
			var that = this;
			
			$("#resultFileSearchBtn").click(function(){
				$("#page").val(1);
				that.searchList();
			});	
        	that.initCalendar();
		},
		
		initCalendar : function() {
			identification.initCalendarByClass('form_datetime','form_date','form_time');			
		},

		
		// 设置参数隐藏域
		setSearchParam: function() {
			$("#requireDataHide").val($("#requireData").val().trim());
			$("#repairerNameHide").val($("#repairerName").val().trim());
			$("#equimentGroupHide").val($("#equimentGroup").val().trim());
		},
		
		
		searchList : function() {
			var that = this;
			
			$("#resultFileList").empty();
			that.setSearchParam();
			var params = that.getParams();
			identification.ajax("/resultFile/search/" + $("#page").val(), JSON.stringify(params), "html", function(res) {
				$("#resultFileList").html(res);
			});
		},
		
		resultFilePageover : function() {
			var that = this;
			$("#resultFileList").empty();
			var params = that.getPageoverParams();
			identification.ajax("/resultFile/search/" + $("#page").val(), JSON.stringify(params), "html", function(res) {
				$("#resultFileList").html(res);
			});

		},
		
		getParams : function() {
			var data = {	
					"applicationDate": $("#requireData").val(),
					"repairerName":$("#repairerName").val(),
					"equimentGroup":$("#equimentGroup").val()
				};
		   return data;
		},
		
		getPageoverParams:function() {
			var data = {	
					"applicationDate": $("#requireDataHide").val(),
					"repairerName":$("#repairerNameHide").val(),
					"equimentGroup":$("#equimentGroupHide").val()
				};
		   return data;

		}
			
	});
 
})(jQuery);	