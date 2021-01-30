$(window).on('load', function(){
	logout();
});

function logout(){
	$("#logout").click(function(event){
		
		$.removeCookie('token', {path: '/' });
	});
}