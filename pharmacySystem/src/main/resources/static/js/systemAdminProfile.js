$(window).on("load", function(){
	getCurrentUser();
	redirectUser();
});

function getCurrentUser(){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getUser',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            if(data.responseJSON == undefined || data.responseJSON.type != "ROLE_PHARMACY_SYSTEM_ADMIN"){
                alert("You cannot access this page!");
                window.location.href = "../index.html";
            }else {
                $("#email").val(data.responseJSON.email);
                $("#firstName").val(data.responseJSON.firstName);
                $("#lastName").val(data.responseJSON.lastName);
            }
            saveUserProfile(data.responseJSON);
        }
    });
}

function saveUserProfile(user){
	$("#save").click(function(event){
        event.preventDefault();
		alert(user.pharmacySystemAdministrator);
        var data = {
        	"id": user.pharmacySystemAdministrator.id,
            "user": {
                "email": $("#email").val(),
                "firstName": $("#firstName").val(),
                "lastName": $("#lastName").val(),
            }
        }

        var transformedData = JSON.stringify(data);

        $.ajax({
            method: 'POST',
            url: 'http://localhost:8080/updateSystemAdmin',
            data: transformedData,
            contentType: 'application/json',
            dataType: 'json',
            headers: {
                Authorization: 'Bearer ' + $.cookie('token')
              },
            complete: function (data) {
                if(data.status == 200)
                    alert("Changes successful!");
                else
                    alert("Something went wrong...");    
            }
        });
    });
}	

function redirectUser(){
	$("#newPharmacy").click(function(){
		window.location.href = "newPharmacy.html";
	});
	
	$("#newSystemAdmin").click(function(){
		window.location.href = "newSystemAdmin.html";
	});
	
	$("#newPharmacyAdmin").click(function(){
		window.location.href = "newPharmacyAdmin.html";
	});
	
	$("#newDermatologist").click(function(){
		window.location.href = "newDermatologist.html";
	});
	
	$("#newSupplier").click(function(){
		window.location.href = "newSupplier.html";
	});
	
	$("#newMedication").click(function(){
		window.location.href = "newMedication.html";
	});
	
}