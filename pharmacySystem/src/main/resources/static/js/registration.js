$(window).on('load', function(){
	passwordsDoNotMatch();
	checkEmailEmpty();
});

function checkEmailEmpty(){
	$("#email").on("input", function(e){
		if($("#email").val() != ""){
			$("#error2").hide();
			$("#register").attr("disabled", false);
		}else{
			$("#error2").show();
			$("#register").attr("disabled", true);
		}
	});
}

function passwordsDoNotMatch(){

	$("#password").on("input", function(e){
		if($("#password").val() == ""){
			$("#password").css("background-color","red");
			$("#register").attr("disabled", true);
		}
		else{
			$("#password").css("background-color","white");
			$("#register").attr("disabled", false);
		}
	});
		
    $("#confirmPassword").on("input", function(e){
        if($("#password").val() != $("#confirmPassword").val()){
            $("#confirmPassword").css("background-color","red");
            $("#register").attr("disabled", true);
        }
        else {
            $("#confirmPassword").css("background-color","white");
            $("#register").attr("disabled", false);
        }
    });
    
    $("#register").click(function(event){
    	event.preventDefault();
    	disableRegistrationButton();
    	registration();
    });
}

function disableRegistrationButton(){

	if($("#email").val() != "" && $("#password").val() != "" && $("#confirmPassword").val() != "" && $("#password").val() == $("#confirmPassword").val()){
		$("#register").attr("disabled", false);
		$("#password").css("background-color","white");
		$("#confirmPassword").css("background-color","white");
	}
	
	if($("#email").val() == ""){
		 $("#error2").show();
		 $("#register").attr("disabled", true);
	}
	
	if($("#password").val() == ""){
		$("#password").css("background-color","red");
		$("#register").attr("disabled", true);
	}
	
	if($("#confirmPassword").val() == ""){	
		$("#confirmPassword").css("background-color","red");
		$("#register").attr("disabled", true);
	}
		
}

function registration(){

	var data = {
		"city": $("#city").val(),
		"address": $("#address").val(),
		"phoneNumber": $("#phoneNumber").val(),
		"country": $("#country").val(),
		"user": {
			"email": $("#email").val(),
			"password": $("#password").val(),
			"firstName": $("#firstName").val(),
			"lastName": $("#lastName").val()
		}
	}
	
	var transformedData = JSON.stringify(data);
	
	$.ajax({
        url: 'http://localhost:8080/register',
        type: 'POST',
        data: transformedData,
        contentType: 'application/json',
        dataType: 'json',
        complete: function(data) {
           if (data.status == 200){
            	alert("Confirmation mail has been sent to:" + $("#email").val());
            	sendConfirmationEmail($("#email").val());
            }
            if(data.status == 404)
                alert("Something went wrong.");
        }
    });
}

function sendConfirmationEmail(email){
	var data = [email, 'Approved'];
	var transformedData = JSON.stringify(data);
	
	$.ajax({
		url: 'http://localhost:8080/sendAccountConfirmation',
        type: 'POST',
        data: transformedData,
        contentType: 'application/json',
        dataType: 'json',
        complete: function(data) {
       	}
	});
}