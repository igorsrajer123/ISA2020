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
            if(data.responseJSON == undefined || data.responseJSON.type != "ROLE_PHARMACY_SYSTEM_ADMIN"){
                alert("You cannot access this page!");
                window.location.href = "../index.html";
            }else {
            	getMedicationTypes();
            	getAllMedications();
            	checkFields();
            	submitData();
            }
        }
    });
}

function checkFields(){
	$("#save").click(function(event){
		event.preventDefault();
		
		if($("#name").val() == ""){
			$("#name").css("background-color","red");
		}
		
		if($("#code").val() == ""){
			$("#code").css("background-color","red");
		}
		
		if($("#type").val() == ""){
			$("#type").css("background-color","red");
		}
		
		if($("#dailyIntake").val() == ""){
			$("#dailyIntake").css("background-color","red");
		}
		
		if($("#sideEffects").val() == ""){
			$("#sideEffects").css("background-color","red");
		}
		
		if($("#composition").val() == ""){
			$("#composition").css("background-color","red");
		}
		
		if($("#substitution").val() == ""){
			$("#substitution").css("background-color","red");
		}
	});
}

function submitData(){
	$("#save").click(function(){
		event.preventDefault();
		
		if($("#name").val() != "" && $("#code").val() != "" && 
				$("#type").val() != "" && $("#dailyIntake").val() != "" &&
				$("#sideEffects").val() != "" && $("#composition").val() != "" && $("#substitution").val() != ""){
					$("#name").css("background-color","white");
					$("#code").css("background-color","white");
					$("#type").css("background-color","white");
					$("#dailyIntake").css("background-color","white");
					$("#sideEffects").css("background-color","white");
					$("#composition").css("background-color","white");
					$("#substitution").css("background-color","white");
					addMedication();
		}else{
			alert("Please enter the required data!");
		}
	});
}

function getMedicationTypes(){
	$.ajax({
		method: 'GET',
        url: 'http://localhost:8080/getAllMedicationTypes',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	var types = data.responseJSON;
        	
        	for(var i = 0; i < types.length; i++){
            	$("#type").append($("<option>", {
            			id: types[i].id,
	            		value: types[i].name,
	            		text: types[i].name
	            	}));         
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
        	
        	for(var i = 0; i < meds.length; i++){
            	$("#substitution").append($("<option>", {
            			id: meds[i].id,
	            		value: meds[i].name,
	            		text: meds[i].name
	            	}));         
            }		
       	}
	});
}

function addMedication(){
	var data = {
		"name": $("#name").val(),
		"code": $("#code").val(),
		"sideEffects": $("#sideEffects").val(),
		"chemicalComposition": $("#composition").val(),
		"substitution": $("#substitution").val(),
		"dailyIntake": parseFloat($("#dailyIntake").val()),
		"medicationType": {
			"id":	$("#type").children(":selected").attr("id"),
			"name":	$("#type").val()
		}
	}

	var transformedData = JSON.stringify(data);
	
	$.ajax({
        url: 'http://localhost:8080/addMedication',
        type: 'POST',
        data: transformedData,
        contentType: 'application/json',
        dataType: 'json',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            if (data.status == 201){
                alert("Success!");
                window.location.href = "systemAdminProfile.html";
            }
            else 
                alert("Error!");
        }
    });
}