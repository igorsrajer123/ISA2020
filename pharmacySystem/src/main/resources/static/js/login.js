$(window).on('load', function(){
    $("#error").hide();
    
    login();
});

function login(){    
	$('#login').click(function (event) {
        event.preventDefault();
       
       	var data = {
       		"username":	$("#email").val(),
       		"password": $("#password").val()
       }
       
       var transformedData = JSON.stringify(data);
    	
       $.ajax({
        url: 'http://localhost:8080/login',
        type: 'POST',
        data: transformedData,
        contentType: 'application/json',
        dataType: 'json',
        complete: function (data) {
            if (data.status == 200) {
                alert("Log In Successful!");
              	window.location.href = "../index.html";
                $("#error").hide();
                $.cookie('token', data.responseJSON.accessToken);
                console.log(data.responseJSON.accessToken);
            }else {
                $("#error").show();
            }
        }
    });
    });
}
