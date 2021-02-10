$(window).on("load", function(){
	$("#datepicker").datepicker({minDate:0});
	getCurrentUser();
	
		
	untilDate = getUrlVars()["untilDate"];
	var parts = untilDate.split('-');
	var ourDate = parts[1] + "/" + parts[2] + "/" + parts[0];
	$("#datepicker").val(ourDate);
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
	var orderId = getUrlVars()["orderId"];
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getAdminsPharmacy/' + adminId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	if(data.status == 200){
        		getOrderItems(orderId, adminId);
        	}else{
        		alert("No pharmacy found!");
        	}
       	}
	});
} 

function getOrderItems(orderId, adminId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getOrderItems/' + orderId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var items = data.responseJSON;
        	addItemsToList(items, adminId);
        	getOrderOffers(orderId, adminId);
        }
    });
}

function addItemsToList(items, adminId){
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
            
            for(var i = 0; i < meds.length; i++){
            	for(var j = 0; j < items.length; j++){
            		if(items[j].medication.id == meds[i].id){
            				$("#" + meds[i].id).prop('checked', true);
            				$("#" + meds[i].id + meds[i].id).val(items[j].amount);
            		}
            	}
            }          
        }
    });
}

function getOrderOffers(orderId, adminId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getOrderOffers/' + orderId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var offers = data.responseJSON;
            
            var offersTable = $("#offers tbody");
            offersTable.empty();

			var todayDate = new Date();
			
            for(var i = 0; i < offers.length; i++){
            	
            	var expiringDate = getUrlVars()["untilDate"];
            	var expiringParts = expiringDate.split("-");
            	var myDate = new Date(expiringParts[0], expiringParts[1] - 1, expiringParts[2]);
				
            	if(getUrlVars()["pharmacyAdmin"] == adminId && todayDate > myDate){
            		if(offers[i].status == "ACTIVE"){
		            	offersTable.append("<tr><td>" + offers[i].supplier.id +
		                "</td><td>" + offers[i].dateOfDelivery +
		                "</td><td>" + offers[i].price + 
		                "</td><td>" + offers[i].status + 
		                "</td><td><button id='" + offers[i].id + "' style='height:25px;'>Accept</button>" +
		                "</td></tr>");
		
		                $("#offers").append(offersTable);
		                acceptOffer(offers[i].id);
		            }else {
		            	offersTable.append("<tr><td>" + offers[i].supplier.id +
		                "</td><td>" + offers[i].dateOfDelivery +
		                "</td><td>" + offers[i].price + 
		                "</td><td>" + offers[i].status + 
		                "</td></tr>");
		
		                $("#offers").append(offersTable);
		            }
               	}else{
	               	offersTable.append("<tr><td>" + offers[i].supplier.id +
	                "</td><td>" + offers[i].dateOfDelivery +
	                "</td><td>" + offers[i].price + 
	                 "</td><td>" + offers[i].status + 
	                "</td></tr>");
	
	                $("#offers").append(offersTable);
               	}
            }   
            
            if(offers.length > 0){
            	$("#datepicker").attr("disabled", true);
            	$("#submit").attr("disabled", true);
            	$("#submit").css("background-color", "gray");
            	$("#notification").hide();
            	$("#delete").attr("disabled", true);
            	$("#delete").css("background-color", "gray");
            	
            	$("#table [type='checkbox']").each(function(i, check){
            		$(this).attr("disabled", true);
            		$(this).closest("td").siblings(2).find("input").attr("disabled", true);
            	});
            }else {
            	deleteOrder(orderId);
            	checkFields(adminId, orderId);
            }
		}
	});
}

function checkFields(adminId, orderId){
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
					"id": orderId,
					"untilDate": sendingDate,
					"pharmacyAdministrator": {
						"id": adminId
					}
				}

				var transformedData = JSON.stringify(data);
				
				$.ajax({
					url: 'http://localhost:8080/updateOrder',
					type: 'PUT',
					data: transformedData,
					contentType: 'application/json',
					dataType: 'json',
					headers: {
						Authorization: 'Bearer ' + $.cookie('token')
					},
					complete: function (data) {
						if (data.status == 200){    							
							$("#table [type='checkbox']").each(function(i, check){
								if(check.checked){
									var medId = $(this).attr('id');
									var amount = $(this).closest('td').siblings(3).find('input').val();
									
									updateOrder(data.responseJSON.id, medId, amount);
								}
							});
						}else 
							alert("Error!");
					}
				});
			}else{
				alert("Error!");
			}
		}else{
			$("#datepicker").css("background-color", "red");
		}
	});
}

function updateOrder(orderId, medId, amount){

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
			alert(data.status);
		}
	});

}

function acceptOffer(offerId){
	$("#" + offerId).click(function(event){
		event.preventDefault();
		
		$.ajax({
	        url: 'http://localhost:8080/acceptOffer/' + offerId,
	        type: 'PUT',
	        contentType: 'application/json',
	        dataType: 'json',
	        headers: {
	   			Authorization: 'Bearer ' + $.cookie('token')
			},
	        complete: function (data) {
	       		if(data.status == 200){
	       			$.ajax({
				        url: 'http://localhost:8080/offerAccepted/' + offerId,
				        type: 'POST',
				        contentType: 'application/json',
				        dataType: 'json',
				        headers: {
				   			Authorization: 'Bearer ' + $.cookie('token')
						},
				        complete: function (data) {
				        	if(data.status == 200){
				        		alert("Offer has been accepted successfully!");
				        		location.reload();
				        	}
				        }
					});
	       		}else{
	       			alert("Error!");
	       		}
	       	}
		});
	});
}

function deleteOrder(orderId){
	$("#delete").click(function(event){
		event.preventDefault();
		
		$.ajax({
	        method: 'DELETE',
	        url: 'http://localhost:8080/deleteOrder/' + orderId,
	        contentType: 'application/json',
		    dataType: 'json',
	        headers: {
	   			Authorization: 'Bearer ' + $.cookie('token')
			},
	        complete: function (data) {
	        	if(data.status == 200){
	        		alert("Success!");
	        		window.location.href = "index.html";
	        	}else
	        		alert("Error!");
	       	}
		});
	});
}

function getUrlVars() {
    var vars = {};
    window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}

