(function($) {
	
	Class('Identification.resultFile.Add',{
		init:function() {
			this.bindEvent();
			this.initCalendar();
		},
		
		initCalendar : function() {
			identification.initCalendarByClass('form_datetime','form_date','form_time');			
		},
		
		bindEvent: function() {
			var that = this;			
			
			//申请保存
	        $("#saveApplicationBtn").click(function() {
	        	//check input required
	        	if($("#company").val().trim() == "") {
	        		var companyLab = $("#companyLable").text();
	        		
	        		alert("请输入" + companyLab);
	        		
	        		return;
	        	}
	        	
	        	if($("#department").val().trim() == "") {
	        		var companyLab = $("#departmentLable").text();
	        		
	        		alert("请输入" + companyLab);
	        		
	        		return;
	        	}
	        	
	        	
	        	if($("#applicationDate").val().trim() == "") {
	        		var companyLab = $("#applicationDateLable").text();
	        		
	        		alert("请输入" + companyLab);
	        		
	        		return;
	        	}
	        	
	          	
	        	if($("#resultFileIdHid").val().trim() == "") {
	        		var companyLab = $("#resultFileuploadLable").text();
	        		
	        		alert("请输入" + companyLab);
	        		
	        		return;
	        	}
	        	
 	        	var saveData = that.getSaveData();
	        	identification.ajax("/resultFile/add", JSON.stringify(saveData), "json", function(res) {
	        		if(res.msg == "success") {
		        		alert("插入成功!");
						location.href="/resultFile/init";   
	        		}else{
	        			alert("插入失败!")
	        		}
				});	 
	        });		
	        
        	
	        //申请文件上传
        	//identification.fileUpload("/application/requestFileUpload","requestFileupload", "requsetFileNameSpan", "requsetFileIdHid");	  
	        
	        //结论文件
        	identification.fileUpload("/resultFile/requestFileUpload","resultFileupload", "resultFileNameSpan", "resultFileIdHid");	  
	        
		},
		
       getSaveData : function() {
    	   var that = this;
    	   var data = {};
    	   data.mongoFileId = $("#resultFileIdHid").val();
    	   data.applicationDate = $("#applicationDate").val();
    	   data.repairerName = $("#company").val();
    	   data.equimentGroup = $("#department").val();
    	   data.mongoFileName = $("#resultFileNameSpan").text();
    	   
    	   return data;
       }
 		
	});
 
})(jQuery);	