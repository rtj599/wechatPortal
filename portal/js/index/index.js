new Vue({
	el: '#index_content',
	data:{
		 message: 'Hello Vue!'
	},
	methods:{
		getData:function(){
			alert("ab");
			if(!CheckSubmitted.submitRequest()){
				return; 
			}
			
			$.ajax({
				url : _callServiceUrl + 'portal.do',   
				dataType : 'json',
				type : 'POST',
				async : true,
				data : "",
				headers:{transType:"user",subTransType:"userList"},
				beforeSend : function(XMLHttpRequest) {
					//
				},
				success : function(data) {
					CheckSubmitted.requestComplete();
					alert("gg");
				},
				error : function(err) {
					CheckSubmitted.requestComplete();
					// 接口调用失败
				}
			});
			
			
		}
	}
});
