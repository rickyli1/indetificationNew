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
				location.href="/audit/export?applicationDate="+  $("#requireData").val().trim()+"&applicationRepairer=" +  $("#requireCompany").val().trim()+"&equimentName="+ $("#equipmentName").val();
				$("#loading").hide();

			});	
        	
        	//文件修改保存
           	$("#auditSaveBtn").click(function(){
        		var datas = that.getSaveParams();
        		
        		if(datas.length <= 0) {
        			alert("请选择要修改的项目！");
        			return false;
        		}
        		
        		$("#loading").show();
    			var params = {"appliationList" : datas};
    			
    			identification.ajax("/audit/save", JSON.stringify(params), "html", function(res) {
    				alert("保存成功！");
           	        window.location.href = window.location.href;				        
    			});			
				$("#loading").hide();

			});
           	
           	//check all 
           	$("#auditChkAll").click(function() {
           		if($(this).prop('checked')) {
           			$(".chooseAuditChk").each(function() {$(this).prop("checked", true);});
           		}else {
           			$(".chooseAuditChk").each(function() {$(this).prop("checked", false);});

           		}
           	}) ;
        	
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
		},
		
		getSaveParams:function() {
			var saveParams = [];
			
			$(".chooseAuditChk").each(function() {
				if($(this).prop('checked')) {
					var id = $(this).attr("data-id");
					var data = {	
							"applicationId":id,
							"haveSuccesWwork": $("input[name='haveSuccesWwork" + id + "']" + ":checked").val(),
							"areaHaveAbility":$("input[name='areaHaveAbility" + id + "']" + ":checked").val(),
							"orginizationResult":$("input[name='orginizationResult" + id + "']" + ":checked").val(),
							
							"remark":$("#remark"+id).val()
						};
					
					saveParams.push(data);
				}
			})
			console.log(saveParams);
			
		   return saveParams;
		}
		
	});
 
})(jQuery);	