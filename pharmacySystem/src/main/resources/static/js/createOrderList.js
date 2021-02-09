$(window).on("load", function(){
	$("#datepicker").datepicker({minDate:0});
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
            if(data.responseJSON != undefined && data.responseJSON.type == "ROLE_PHARMACY_ADMIN"){
                getAdminFromUserId(data.responseJSON.id);
            }else {
            	alert("You cannot access this page!");
                window.location.href = "../index.html";
           	}
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
            getPharmacy(data.responseJSON.id);
        }
    });
}

function getPharmacy(adminId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getAdminsPharmacy/' + adminId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	if(data.status == 200){
        		addItemToList(adminId);
        	}else{
        		
        	}
       	}
	});
} 

function addItemToList(adminId){
		$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getAllMedications',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var meds = data.responseJSON;
            
            var medsTable = $("#table tbody");
            medsTable.empty();

            for(var i = 0; i < meds.length; i++){
            	medsTable.append("<tr><td><input type='checkbox' id='" + meds[i].id + "'/>" +   
                "</td><td>" + meds[i].name +
                "</td><td><input type='text' id='" + meds[i].id + meds[i].id + "'/>" +
                "</td></tr>");

                $("#table").append(medsTable);
            }
            checkFields(adminId);
        }
    });
}

function checkFields(adminId){
	$("#submit").click(function(event){
		event.preventDefault();
		
		var invalid = false;
		var numberOfCheckedMeds = 0;
		if($("#datepicker").val() != ""){
			$("#table [type='checkbox']").each(function(i, check){
				if(check.checked){
					numberOfCheckedMeds++;
					
					var amount = $(this).closest('td').siblings(3).find('input').val();
				
					if(amount != "")
						$(this).closest('td').siblings(3).find('input').css("background-color", "white");
					else{
						$(this).closest('td').siblings(3).find('input').css("background-color", "red");	
						invalid = true;
					}
				}
			});
			
			if(numberOfCheckedMeds != 0 && !invalid){
				var ourDate = $("#datepicker").val();
				var parts = ourDate.split('/');
				var sendingDate = parts[2] + "-" + parts[0] + "-" + parts[1];
		
				var data = {
					"untilDate": sendingDate,
					"pharmacyAdministrator": {
						"id": adminId
					}
				}

				var transformedData = JSON.stringify(data);
	
				$.ajax({
					url: 'http://localhost:8080/createOrder',
					type: 'POST',
					data: transformedData,
					contentType: 'application/json',
					dataType: 'json',
					headers: {
						Authorization: 'Bearer ' + $.cookie('token')
					},
					complete: function (data) {
						if (data.status == 200){    
							var order = data.responseJSON;
							
							$("#table [type='checkbox']").each(function(i, check){
								if(check.checked){
									var medId = $(this).attr('id');
									var amount = $(this).closest('td').siblings(3).find('input').val();
									
									addMedicationsToOrder(order.id, medId, amount);
								}
							});
							
						}else 
							alert("Error!");
					}
				});
			}else
				alert("Error!");
		}else{
			$("#datepicker").css("background-color", "red");
		}	
	});
}

function addMedicationsToOrder(orderId, medId, amount){
		
	var data = {
		"orderForm": {
			"id": orderId
		},
		"medication": {
			"id": medId
		},
		"amount": amount
	}

	var transformedData = JSON.stringify(data);
	
	$.ajax({
		url: 'http://localhost:8080/createOrderItem',
	 	type: 'POST',
		data: transformedData,
		contentType: 'application/json',
		dataType: 'json',
	 	headers: {
			Authorization: 'Bearer ' + $.cookie('token')
		},
		complete: function (data) {
			if (data.status == 200){    
				alert("Order has been made successfully!");
				window.location.href = "index.html";
	  		}
  			else 
		  		alert("Error!");
			}
	});
}