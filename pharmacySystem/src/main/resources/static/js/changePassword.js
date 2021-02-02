$(window).on("load", function(){
	getCurrentUser();
	passwordsDoNotMatch();
});

function getCurrentUser(){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getUser',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            if(data.responseJSON == undefined || data.responseJSON.type == "ROLE_PATIENT"){
                alert("You cannot access this page!");
                window.location.href = "../index.html";
            }else {
                savePassword(data.responseJSON);
            }
        }
    });
}

function savePassword(){
	$("#confirm").click(function(){
		event.preventDefault();		
		
		var data = [ $("#confirmPassword").val(),""];

        var transformedData = JSON.stringify(data);
	
        $.ajax({
            method: 'POST',
            url: 'http://localhost:8080/changePassword',
            data: transformedData,
            contentType: 'application/json',
            dataType: 'json',
            headers: {
                Authorization: 'Bearer ' + $.cookie('token')
              },
            complete: function (data) {
                if(data.status == 200){
                    alert("Changes successful!");
                    window.location.href = "../index.html";
                }
                else
                    alert("Something went wrong...");    
            }
        });
    });
}

function passwordsDoNotMatch(){
    $("#confirmPassword").on("input", function(e){
        var password = $("#newPassword").val();
        var confirmedPassoword = $("#confirmPassword").val();

        if(confirmedPassoword != password){
            $("#confirmPassword").css("background-color","red");
            $("#confirm").attr("disabled", true);
        }
        else {
            $("#confirmPassword").css("background-color","white");
            $("#newPassword").val(confirmedPassoword);
            $("#confirm").attr("disabled", false);
        }
    });
    
    $("#newPassword").on("input", function(e){
        var password = $("#newPassword").val();
        var confirmedPassoword = $("#confirmPassword").val();
        
        if(confirmedPassoword == "")
        	$("#confirm").attr("disabled", true);

        if(confirmedPassoword != password){
            $("#confirm").attr("disabled", true);
        }
        else {
            $("#newPassword").val(confirmedPassoword);
            $("#confirm").attr("disabled", false);
        }
    });
}
