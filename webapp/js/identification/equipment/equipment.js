(function($) {
	Class('Identification.equipment.List', {
		init:function() {
			this.bindEvent();
		},
		
		bindEvent: function() {
			var that = this;
			
			$("#equipmentSearchBtn").click(function(){
				$("#page").val(1);
				that.searchList();
			});	
			
	        //文件导入
        	identification.fileUpload("/equipment/import","equipmentImoprtBtn", "", "");	
        	
           //文件导出
        	$("#equipmentExportBtn").click(function(){
        		var data = {	
    				"page":$("#page").val(),
    				"equipmentName": $("#equipmentName").val(),
    				"equipmentGroup":$("#equipmentGroup").val(),
    				"equipmentSubGroup":$("#equipmentSubGroup").val(),
    				"equipmentLevel":$("#equipmentLevel").val(),
    			};
        		$("#loading").show();
        		var equipmentName = $("#equipmentName").val();
        		var equipmentGroup = $("#equipmentGroup").val();
        		var equipmentSubGroup = $("#equipmentSubGroup").val();
        		var equipmentLevel = $("#equipmentLevel").val();
        		var url = encodeURI("/equipment/export?equipmentGroup="+equipmentGroup+"&equipmentSubGroup="+equipmentSubGroup+"&equipmentLevel="+equipmentLevel+"&equipmentName="+equipmentName);
//				location.href=encodeURI("/equipment/export/"+json);   
        		window.open(url, '_self',
        		'width=1,height=1,toolbar=no,menubar=no,location=no');
				$("#loading").hide();

			});	
        	
		},
		goDel:function(equipmentNo) {
			var that = this;
			identification.ajax("/equipment/deleteEquipment", equipmentNo, "json", function(res) {
				$("#alertDiv").empty();
				$("#alertDiv").html(res);
			});
			that.searchList();
		},
		searchList : function() {
			var that = this;
			
			$("#equipmentResultList").empty();
			var data = this.getSearchConditions();
			identification.ajax("/equipment/search/" + $("#page").val(), JSON.stringify(data), "html", function(res) {
				$("#equipmentResultList").html(res);
			});
		}	// 取得隐藏域参数
		,getSearchConditions: function() {
			var data = {	
				"page":$("#page").val(),
				"equipmentName": $("#equipmentName").val(),
				"equipmentGroup":$("#equipmentGroup").val(),
				"equipmentSubGroup":$("#equipmentSubGroup").val(),
				"equipmentLevel":$("#equipmentLevel").val(),
			};
			return data;
	    },
	    // 设置参数隐藏域
		setSearchParam: function() {
			$("#equipmentNameHide").val($("#equipmentName").val());
			$("#equipmentGroupHide").val($("#equipmentGroup").val());
			$("#equipmentSubGroupHide").val($("#equipmentSubGroup").val());
			$("#equipmentLevelHide").val($("#equipmentLevel").val());
			
		}
		
	});
 
})(jQuery);	