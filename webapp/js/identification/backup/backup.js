(function($) {
	Class('Identification.backup.List', {
		init:function() {
			this.bindEvent();
		},
		
		bindEvent: function() {
			var that = this;
			
			$("#backupBtn").click(function(){
				var filename = $("#fileName").val();
				if(filename == ''){
					alert("备份文件名称不能为空！");
					return ;
				}
				that.searchList();
			});	
			
			$("#recoverBakcupBtn").click(function(){
				var filepath = $("#filepath").val();
				if(filepath == ''){
					alert("文件名称不能为空！");
					return ;
				}
				that.recover(filepath);
			});	
			
			$("#deleteBakcupBtn").click(function(){
				var filepath = $("#filepath").val();
				if(filepath == ''){
					alert("文件名称不能为空！");
					return ;
				}
				that.goDel(filepath);
			});	
        	
		},
		recover:function(filepath) {
			var that = this;
			if(confirm("确定恢复此项么？")) {
				identification.ajax("/backup/recoverStart/"+ filepath, "", "html", function(res) {
					alert("恢复成功！");
					window.location.reload();
				});
			}
		},
		goDel:function(filepath) {
			var that = this;
			if(confirm("确定删除此项么？")) {
				identification.ajax("/backup/deleteBackup/"+ filepath, "", "html", function(res) {
					alert("删除成功！");
					window.location.reload();
				});
			}
		},
		searchList : function() {
			var that = this;
			
			var data = {};
			identification.ajax("/backup/backupStart/" + $("#fileName").val(), JSON.stringify(data), "html", function(res) {
				alert("备份成功！");
				window.location.reload();
			});
		}
		
	});
 
})(jQuery);	