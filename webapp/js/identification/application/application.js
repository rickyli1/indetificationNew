(function($) {
	Class('Identification.application.List', {
		init:function() {
			this.bindEvent();
			this.formatHistory();
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
			identification.ajax("/application/search/" + $("#page").val(), JSON.stringify(params), "html", function(res) {
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
		},
		
		goApplicationDelete:function(id) {
			if(confirm("确定删除此项么？")) {
			}
		},
		
		goApplicationUpdate:function(id){
			$("#updateApplicationDate").val($("#applicationDate"+id).text());
			$("#updateApplicationRepairer").val($("#applicationRepairer"+id).text());
			$("#updateEquipmentManager").val($("#equipmentManager"+id).text());
			$("#updateEquimentGroup").val($("#equimentGroup"+id).text());
			$("#updateEquimentName").val($("#equimentName"+id).text());
			$("#updateRepairerLevel").val($("#repairerLevel"+id).text());
			$("#updateRepairerHistory").val($("#repairerHistory"+id).html().replace(/<br>/ig, "\n"));
			$("#updateRemark").val($("#remark"+id).text());
			$("#updateApplicationId").val(id);
			$("#updateApplicationDateTxt").val($("#applicationDate"+id).text());
			identification.initCalendarByClass('form_datetime','form_date','form_time');			
		},
		
		updatelicationUpdate:function() {
			
			var params = this.getUpdateParams();
			
		},
		
		getUpdateParams:function() {
			var data = {	
					"applicationDate": $("#updateApplicationDate").val(),
					"applicationRepairer":$("#updateApplicationRepairer").val(),
					"equipmentManager":$("#updateEquipmentManager").val(),
					"equimentGroup":$("#updateEquimentGroup").val(),
					"equimentName":$("#updateEquimentName").val(),
					"repairerLevel":$("#updateRepairerLevel").val(),
					"repairerHistory":$("#updateRepairerHistory").val(),
					"remark":$("#updateRemark").val(),
					"applicationId":$("#updateApplicationId").val()
				};
		   return data;
			
		}
		
	});
 
})(jQuery);	