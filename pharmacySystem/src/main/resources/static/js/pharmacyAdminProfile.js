$(window).on("load", function(){
	getCurrentUser();
});

function getCurrentUser(){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getUser',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            if(data.responseJSON == undefined || data.responseJSON.type != "ROLE_PHARMACY_ADMIN"){
                alert("You cannot access this page!");
                window.location.href = "../index.html";
            }else {
                $("#email").val(data.responseJSON.email);
                $("#firstName").val(data.responseJSON.firstName);
                $("#lastName").val(data.responseJSON.lastName);
            }
          //  saveUserProfile(data.responseJSON);
          getAdminFromUserId(data.responseJSON.id);
        }
    });
}

function getAdminFromUserId(userId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacyAdminFromUserId/' + userId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            //getPharmacy(data.responseJSON.id);
            saveUserProfile(data.responseJSON);
        }
    });
}

function saveUserProfile(user){
	$("#save").click(function(event){
        event.preventDefault();
	
		var data = {
        	"id": user.id,
            "user": {
                "email": $("#email").val(),
                "firstName": $("#firstName").val(),
                "lastName": $("#lastName").val(),
            }
        }

        var transformedData = JSON.stringify(data);
		
        $.ajax({
            method: 'POST',
            url: 'http://localhost:8080/updatePharmacyAdmin',
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
