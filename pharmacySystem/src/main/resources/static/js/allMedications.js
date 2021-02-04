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
            if(data.responseJSON == undefined || data.responseJSON.type == "ROLE_PATIENT" ||
            	data.responseJSON.type == "ROLE_PHARMACY_SYSTEM_ADMIN"){
            	getAllMedications();
            }else {
            	 alert("You cannot access this page!");
                 window.location.href = "../index.html";
           	}
        }
    });
}

function getAllMedications(){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getAllMedications',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var meds = data.responseJSON;
            
            $.ajax({
            	method: 'GET',
		        url: 'http://localhost:8080/getAllMedicationTypes',
		        headers: {
		   			Authorization: 'Bearer ' + $.cookie('token')
				},
		        complete: function (data) {
		       		var types = data.responseJSON;
		       		generateTable(meds, types);
		       		searchMedications(meds);
		       	}
            });
        }
    });
}

function generateTable(meds, types){
	var medsTable = $("#table tbody");
    medsTable.empty();
            
	for(var i = 0; i < meds.length; i++){

		for(var j = 0; j < types.length; j++){
			if(types[j].id == meds[i].medicationType.id){
	            medsTable.append("<tr id='" + meds[i].id + "'><td>" + meds[i].name +   
	            "</td><td>" + meds[i].code +
	            "</td><td>" + meds[i].dailyIntake +
	            "</td><td>" + meds[i].sideEffects +
	            "</td><td>" + meds[i].chemicalComposition +
	            "</td><td>" + types[j].name +
	            "</td></tr>");
	                
	            $("#table").append(medsTable);
           	}
        }
	}
}

function searchMedications(meds){
	$("#search").on("input", function(){
		if($("#search").val() != ""){
	        $.ajax({
            	method: 'GET',
		        url: 'http://localhost:8080/getAllMedicationTypes',
		        headers: {
		   			Authorization: 'Bearer ' + $.cookie('token')
				},
		        complete: function (data) {
		       		var types = data.responseJSON;
		       		
		       		var medsTable = $("#table tbody");
		    		medsTable.empty();
		       		
		       		for(var i = 0; i < meds.length; i++){
		       			for(var j = 0; j < types.length; j++){
		       				if(types[j].id == meds[i].medicationType.id){
								if(meds[i].name.toLowerCase().includes($("#search").val().toLowerCase()) ||
									meds[i].code.toLowerCase().includes($("#search").val().toLowerCase())){
								 	medsTable.append("<tr><td>" +  meds[i].name +
				                	"</td><td>" + meds[i].code +
				                	"</td><td>" + meds[i].dailyIntake +
				                	"</td><td>" + meds[i].sideEffects +
				                	"</td><td>" + meds[i].chemicalComposition +
				                	"</td><td>" + types[j].name +
				                	"</td></tr>");
				
				                	$("#table").append(medsTable);
		                		}
	                		}
						}
					}
		       	}
            });
		}else{
			getAllMedications();
		}
	});
}
