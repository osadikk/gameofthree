			
document.write('<script type="text/javascript" src="/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>');
document.write('<script type="text/javascript" src="/webjars/stomp-websocket/2.3.3-1/stomp.min.js"></script>');



var commandBaseUri="http://localhost:8087/";
var queryBaseUri="http://localhost:8088/";

window.onload = function () { 
	
	$("#adj").keypress(function (evt) {
		evt.preventDefault();
		});
	$(document).keydown(function(e) {
	    var elid = $(document.activeElement).hasClass('textInput');
	    if (e.keyCode === 8 && !elid) {
	        return false;
	    };
	});
	
	var socket = new SockJS(commandBaseUri+'gameofthree/');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
	    stompClient.subscribe('/topic/messages/' + $("#gameId").val(), function(message) {	 
	    	var tbody=$("#messageTable").find("tbody");
	    	tbody.append("<tr><td>" + message.body + "</td></tr>");
	    	
	    	
	    	if(message.body.indexOf("played")>0 || message.body.indexOf("started")>0){

	    		getGame();
	    	}
	    	
	    	
	    });
});
		
	getGame();
}

function getGame(){
  	$.ajax({
  	    type: "GET",
  	    url: queryBaseUri+'getGame/'+$("#gameId").val(),
  	    accept: "application/json",
  	    dataType: "json",
  	    headers : {
  	        "Content-Type" : "application/json"
  	     },
  	    success: function (data) {
  	    	$("#number").val(data.number);
  	    },
  	    error: function (err) {
  	    	var tbody=$("#messageTable").find("tbody");
  	    	tbody.append("<tr><td>" + err.responseJSON.message + "</td></tr>");
  	    }
  	});
}


function start(){	
	  var gameRequest = {
			  gameId : $("#gameId").val(),
			  userId : $("#userId").val()
	         };
	  
	  var requestJSON = JSON.stringify(gameRequest);
$.ajax({
  type: "PUT",
  url: commandBaseUri+'startGame/',
  accept: "application/json",
  dataType: "json",
  headers : {
      "Content-Type" : "application/json"
   },
  data :requestJSON,
  success: function (data) {
  },
  error: function (err) {
  	var tbody=$("#messageTable").find("tbody");
  	tbody.append("<tr><td>" + err.responseJSON.message + "</td></tr>");
  }
});
}


function play(){	
	  var playGameRequest = {
			  gameId : $("#gameId").val(),
			  userId : $("#userId").val(),
			  adjustment : $("#adj").val(),
			  number : $("#number").val()
	         };
	  
	  var requestJSON = JSON.stringify(playGameRequest);
$.ajax({
    type: "PUT",
    url: commandBaseUri+'playGame/',
    accept: "application/json",
    dataType: "json",
    headers : {
        "Content-Type" : "application/json"
     },
    data :requestJSON,
    success: function (data) {
    },
    error: function (err) {
    	var tbody=$("#messageTable").find("tbody");
    	tbody.append("<tr><td>" + err.responseJSON.message + "</td></tr>");
    }
});
}