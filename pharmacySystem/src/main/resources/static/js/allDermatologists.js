$(window).on("load", function(){
	getCurrentUser();
	$("#filters").hide();
	
	$("#btnFilter").click(function(event){
		event.preventDefault();
		
		if($("#filters").is(":hidden"))
			$("#filters").slideDown(1000);
		else if(!$("#filters").is(":hidden"))
			$("#filters").slideUp(1000);
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
            if(data.responseJSON == undefined || data.responseJSON.type == "ROLE_PHARMACY_SYSTEM_ADMIN" ||
            	data.responseJSON.type == "ROLE_DERMATOLOGIST" || data.responseJSON.type == "ROLE_SUPPLIER" ||
            	data.responseJSON.type == "ROLE_PHARMACIST" || data.responseJSON.type == "ROLE_PHARMACY_ADMIN"){
                
                alert("You cannot access this page!");
                window.location.href = "../index.html";
            }else{
            	getAllDermatologists();
           	}
        }
    });
}

function getAllDermatologists(){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getAllDermatologists',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var dermatologists = data.responseJSON;

            var dermatologistsTable = $("#table tbody");
            dermatologistsTable.empty();
            for(var i = 0; i < dermatologists.length; i++){
            	dermatologistsTable.append("<tr id='" + dermatologists[i].id + "'><td>" + dermatologists[i].user.firstName +    
                "</td><td>" + dermatologists[i].user.lastName +
                "</td><td>" + 
                "</td><td>" + dermatologists[i].rating +
                 "</td><td><button id='" + dermatologists[i].id + dermatologists[i].id + "'>Available Examinations</button>" +
                "</td></tr>");
                
                $("#table").append(dermatologistsTable);
                getDermatologistPharmacies(dermatologists[i].id);
              	buttonClicked(dermatologists[i].id);
            }
            searchDermatologists(dermatologists);
        }
    });
}

function searchDermatologists(dermatologists){
	$("#search").on("input", function(){
		if($("#search").val() != ""){
			
			var dermatologistsTable = $("#table tbody");
		    dermatologistsTable.empty();
	            
			for(var i = 0; i < dermatologists.length; i++){
				if(dermatologists[i].user.firstName.toLowerCase().includes($("#search").val().toLowerCase()) ||
					dermatologists[i].user.lastName.toLowerCase().includes($("#search").val().toLowerCase())){
					 dermatologistsTable.append("<tr id='" + dermatologists[i].id + "'><td>" +  dermatologists[i].user.firstName +
	                "</td><td>" + dermatologists[i].user.lastName +
	                "</td><td>" + 
	                "</td><td>" + dermatologists[i].rating +
	                "</td><td><button id='" + dermatologists[i].id + dermatologists[i].id + "'>Available Examinations</button>" +
	                "</td></tr>");
	                
	                $("#table").append(dermatologistsTable);
	                getDermatologistPharmacies(dermatologists[i].id);
	                buttonClicked(dermatologists[i].id);
				}
			}
		}else{
			getAllDermatologists();
		}
	});
}

function getDermatologistPharmacies(dermatologistId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getDermatologistPharmacies/' + dermatologistId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            var pharmacies = data.responseJSON;
            var all = "";
            for(var i = 0; i < pharmacies.length; i++){
            	all += pharmacies[i].name + ", ";
            }
            
            var text = all.replace(/,\s*$/, "");
            $("#" + dermatologistId + " td:nth-child(3)").text(text);
        }
    });
}

function buttonClicked(id){
	$("#" + id + id).click(function(event){
		event.preventDefault();
		window.location.href = "dermatologistAllFreeExaminations.html?dermatologistId=" + id;
	});
}