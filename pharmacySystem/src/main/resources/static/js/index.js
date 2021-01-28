window.onload = function(){
    getCurrentUser();
}

function welcomeMessage(user){
    if (user == undefined) {
        $("#currentUser").hide();
        $("#user").hide();
    } else {
        $("#navBar").append("<label id='currentUser'>Current user: </label><label id='user'></label>");
        $("#currentUser").show();
        $("#user").text(user.email);
    }
}

function getCurrentUser(){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getUser',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            //welcomeMessage(data.responseJSON);
            alert(data.responseJSON.type);
        }
    });

}
