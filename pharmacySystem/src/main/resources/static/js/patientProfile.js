$(window).on('load', function(){
    cancel();
    getCurrentUser();
    toggleNewPasswordOptions();
    passwordsDoNotMatch();
    
    $("#allergies").click(function(event){
    	event.preventDefault();
    	window.location.href="patientAllergies.html";
    });
});

function cancel(){
    $("#cancel").click(function(event){
        event.preventDefault();
        window.location.href = "../index.html";
    })
}

function getCurrentUser(){
    $.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getUser',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            if(data.responseJSON == undefined || data.responseJSON.type != "ROLE_PATIENT"){
                alert("You cannot access this page!");
                window.location.href = "../index.html";
            }else {
                $("#email").val(data.responseJSON.email);
                $("#password").val(data.responseJSON.password);
                $("#firstName").val(data.responseJSON.firstName);
                $("#lastName").val(data.responseJSON.lastName);
                $("#address").val(data.responseJSON.patient.address);
                $("#phoneNumber").val(data.responseJSON.patient.phoneNumber);
                $("#country").val(data.responseJSON.patient.country);
                $("#city").val(data.responseJSON.patient.city);
            }
            saveUserProfile(data.responseJSON.id);
        }
    });
}

function toggleNewPasswordOptions(){
    $("#changePassword").click(function(event){
        event.preventDefault();

        if($("#newPassword").is(":hidden")){
            $("#newPassword").slideDown(1000);
            $("#newPassword").val("");
            $("#retypeNewPassword").slideDown(1000);
            $("#retypeNewPassword").val("");
            $("#newPassword2").slideDown(1000);
            $("#retypeNewPassword2").slideDown(1000);
            $("#changePassword").html("Cancel");
        }else {
            $("#newPassword").slideUp(1000);
            $("#newPassword").val("");
            $("#retypeNewPassword").slideUp(1000);
            $("#retypeNewPassword").val("");
            $("#newPassword2").slideUp(1000);
            $("#retypeNewPassword2").slideUp(1000);
            $("#changePassword").html("Change Password");
        }
    });
}

function passwordsDoNotMatch(){
    $("#retypeNewPassword").on("input", function(e){
        var password = $("#newPassword").val();
        var confirmedPassoword = $("#retypeNewPassword").val();

        if(confirmedPassoword != password){
            $("#retypeNewPassword").css("background-color","red");
            $("#save").attr("disabled", true);
        }
        else {
            $("#retypeNewPassword").css("background-color","white");
            $("#password").val(confirmedPassoword);
            $("#save").attr("disabled", false);
        }
    });
}

function saveUserProfile(id){
    $("#save").click(function(event){
        event.preventDefault();

        var data = {
            "id": id,
            "address": $("#address").val(),
            "phoneNumber": $("#phoneNumber").val(),
            "city": $("#city").val(),
            "country": $("#country").val(),
            "user": {
                "password": $("#password").val(),
                "firstName": $("#firstName").val(),
                "lastName": $("#lastName").val(),
            }
        }

        var transformedData = JSON.stringify(data);

        $.ajax({
            method: 'POST',
            url: 'http://localhost:8080/updatePatient',
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