<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Home</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<%@ include file="navi.jsp" %>
<div class="container">
	<h1>User List</h1>
	
	<table class="table table-condensed" id="table-users">
		<thead>
			<tr>
				<th>순번</th>
				<th>아이디</th>
				<th>이름</th>
				<th>상태</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${users }" varStatus="loop">
				<c:if test="${user.id ne LOGIN_USER.id }">
					<tr>
						<td>${loop.count }</td>
						<td>${user.id }</td>
						<td>${user.name }</td>
						<td><span class="label label-default" id="label-${user.id }">오프라인</span></td>
						<td><button class="btn btn-primary btn-xs disabled" id='btn-req-${user.id }'>채팅요청</button></td>
					</tr>
				</c:if>
			</c:forEach>
		</tbody>
	</table>
	
	<div class="row" id="div-checkbox-container"></div>
</div>
</body>
<script type="text/javascript">
$(function() {
	var ws = new WebSocket("ws://192.168.10.254:8081/chat.do");
	
	ws.onmessage = function(event) {
		var data = event.data;
		var dataItems = data.split(":");
		var protocol = dataItems[0];
		
		if (protocol == 'ON_USERS') {
			var messageItems = dataItems[1].split(",");
			
			$.each(messageItems, function(index, establishedUserId) {
				$("span#label-" + establishedUserId).removeClass("label-default").addClass("label-success").text("온라인");
				$("button#btn-req-" + establishedUserId).removeClass("disabled");					
			})
		} else if (protocol == 'OFF_USER') {
			var offlineUserId = dataItems[1];
			
			$("span#label-" + offlineUserId).addClass("label-default").removeClass("label-success").text("오프라인");
			$("button#btn-req-" + offlineUserId).addClass("disabled");
			$("#div-checkbox-container #chatbox-" + offlineUserId).remove();
		} else if (protocol == 'ON_CHAT') {
			var chatId = dataItems[1];
			$("button#btn-req-" + chatId).addClass("disabled");
			
			var html = "<div class='col-sm-6' id='chatbox-"+chatId+"'>";
			html += "<div class='panel panel-default' >";
			html += "	<div class='panel-heading'>"+chatId+"님과 대화중 <button class='btn btn-danger btn-xs pull-right' id='btn-close-"+chatId+"'>닫기</div>";
			html += "	<div class='panel-body' id='chatbox-message-"+chatId+"'></div>";
			html += "	<div class='panel-footer'>";
			html += "		<form class='form-horizontal' id='message-form-"+chatId+"'>";
			html += "			<div class='form-group'>";
			html += "				<div class='col-sm-10'>";
			html += "					<input type='text' class='form-control' name='message' id='input-message-"+chatId+"'>";
			html += "				</div>";
			html += "				<div class='col-sm-2'>";
			html += "					<button type='button' class='btn btn-default btn-sm' id='btn-send-"+chatId+"'>전송</button>";
			html += "				</div>";
			html += "			</div>";
			html += "		</form>";
			html += "	</div>";
			html += "</div>";
			html += "</div>";
			$("#div-checkbox-container").append(html);
		} else if (protocol == 'OFF_CHAT') {
			var closedChatId = dataItems[1];
			$("button#btn-req-" + closedChatId).removeClass("disabled");
			$("#div-checkbox-container #chatbox-" + closedChatId).remove();
		} else if (protocol == 'CHAT_MSG') {
			var chatId = dataItems[1];
			var sendId = dataItems[2];
			var message = dataItems[3];
			var $chatbox = $("#chatbox-message-"+chatId).append("<p>["+sendId+"] "+message+"</p>");
		}
	};
	
	$("#table-users").on("click", "button[id^='btn-req-']", function() {
		if (!$(this).hasClass('disabled')) {
			var responseId = $(this).attr("id").replace('btn-req-', '');
			
			ws.send('CONNECT:' + responseId);
			$(this).addClass('disabled')
		}
	});
	
	$("#div-checkbox-container").on('click', "button[id^='btn-close-']", function() {
		var closedId = $(this).attr("id").replace('btn-close-', '');
		ws.send('CLOSE:' + closedId);
	});
	
	$("#div-checkbox-container").on('click', "button[id^=btn-send-]", function(event) {
		event.preventDefault();
		
		var chatId = $(this).attr('id').replace('btn-send-', '');
		var message = $("#input-message-"+chatId).val();
		if (message != "") {
			ws.send("MSG:" + chatId + ":" + message);
			$("#input-message-"+chatId).val("");
		}
	})
	
})
</script>
</html>