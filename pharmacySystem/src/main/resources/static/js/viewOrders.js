$(window).on("load", function(){
	getCurrentUser();
	$("#back").click(function(event){
		event.preventDefault();
		
		window.location.href = "index.html";
	});
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
            getPharmacy(data.responseJSON);
        }
    });
}

function getPharmacy(admin){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getAdminsPharmacy/' + admin.id,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	if(data.status == 200){
        		getPharmacyOrders(data.responseJSON.id, admin)
        	}else{
        		alert("No pharmacy found!");
        	}
       	}
	});
} 

function getPharmacyOrders(pharmacyId, admin){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacyOrders/' + pharmacyId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var orders = data.responseJSON;
            
            var ordersTable = $("#table tbody");
            ordersTable.empty();

            for(var i = 0; i < orders.length; i++){
            	if(!orders[i].deleted){
	            	ordersTable.append("<tr id='" + orders[i].id + "'><td>" + admin.user.firstName  + " " + admin.user.lastName +
	                "</td><td>" + orders[i].untilDate +
	                "</td></tr>");
	
	                $("#table").append(ordersTable);
	                chooseOrder(orders[i], orders[i].untilDate);
	        	}
            }
        }
    });
}

function chooseOrder(order, untilDate){
	$("#" + order.id).click(function(event){
		event.preventDefault();
		window.location.href = "offers.html?orderId=" + order.id + "&untilDate=" + untilDate + "&pharmacyAdmin=" + order.pharmacyAdministrator.id;
	});
}
