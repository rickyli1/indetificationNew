(function($) {
	Class('Identification.equipment.Add',{
		init:function() {
			this.no = 0;
			this.bindEvent();
		},
		
		bindEvent: function() {
			var that = this;			
			$("#addEquipmentDetailBtn").click(function() {
	            that.no = that.no +1;
	            var data = {  no : that.no};
	            var datas = {};
	        	identification.ajaxNoasync("/equipment/getApplicationData", null, "json", function(res) {
	        		datas = $.extend(data, res);
				});	         	

				$("#detailTemplate").tmpl(datas).appendTo( "#equipmentDetailBody" );
				
//				that.initCalendar();
				
				$(".deleteClass").each(function(index,elemet){
					var removeNo = $(this).attr("data-no");
					$(this).click(function() {
						that.no = that.no -1;
						$("#tr" + removeNo).remove();
					});
				});
				
			});	
			
			$("#saveEquipmentBtn").click(function() {
	        	var saveData = that.getSaveData();
	        	identification.ajax("/equipment/add", JSON.stringify(saveData), "html", function(res) {
					$("#alertDiv").empty();
					$("#alertDiv").html(res);
				});	     
	        	
	        });	
			
			$("#updateEquipmentBtn").click(function() {
	        	var saveData = that.getUpdateData();
	        	identification.ajax("/equipment/updateEquipment", JSON.stringify(saveData), "html", function(res) {
					$("#alertDiv").empty();
					$("#alertDiv").html(res);
					window.location.reload()
				});	     
	        	
	        });		
	       
	       
		},
		
       getSaveData : function() {
    	   var equipment = {};
//    	   var rep = {};
//    	   rep.equipments = [];
    	   equipment.equipmentNo = $("#equipmentNo").val();
    	   equipment.equipmentName = $("#equipmentName").val();
		   equipment.equipmentLimit = $("#dlimit").val();
		   equipment.groupNo = $("#groupNo").val();
		   equipment.subGroupNo = $("#subGroupNo").val();
		   equipment.remark = $("#remark").val();
    	   return equipment;
       } ,
       getUpdateData : function() {
    	   var equipment = {};
//    	   var rep = {};
//    	   rep.equipments = [];
    	   equipment.equipmentId = $("#dequipmentId").val();
    	   equipment.equipmentName = $("#dequipmentName").val();
		   equipment.dlimit = $("#dlimit").val();
		   equipment.equipmentGroup = $("#dgroupNo").val();
		   equipment.equipmentSubGroup = $("#dsubGroupNo").val();
		   equipment.remark = $("#dremark").val();
		   equipment.dequipmentLevel = $("#equipmentLevel").val();
		   equipment.equipmentCompany = $("#dequipmentCompany").val();
    	   return equipment;
       } ,
       goDetail:function(equipmentId) {
			var that = this;
			var data = {	
				"equipmentId": equipmentId
			};
			identification.ajaxNoasync("/equipment/updateInit", JSON.stringify(data), "json", function(res) {
				that.createModalBody(res); 
			});			
		},
		createModalBody:function(data) {
			$("#dequipmentName").val(data.equipmentName);
			$("#dequipmentId").val(data.equipmentId);
			$("#dlimit").val(data.equipmentLimit);
			$("#dremark").val(data.remark);
			$("#dgroupNo").val(data.equipmentGroup);
			$("#dsubGroupNo").val(data.equipmentSubGroup);
			$("#dequipmentLevel").val(data.equipmentLevel);
			$("#dequipmentCompany").val(data.equipmentCompany);
		},
		
	});
 
})(jQuery);	