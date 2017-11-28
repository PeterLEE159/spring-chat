<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>로그인 폼</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<%@ include file="navi.jsp" %>
<div class="container">
	<div class="row">
		<div class="col-sm-4 col-sm-offset-4">
			<div class="panel panel-default">
				<div class="panel-heading">로그인 정보</div>
				<div class="panel-body">
					<form method="post" action="/login.do">
						<div class="form-group">
							<label>아이디</label>
							<input type="text" class="form-control" name="userid"/>
						</div>
						<div class="form-group">
							<label>비밀번호</label>
							<input type="password" class="form-control" name="userpwd"/>
						</div>
						<div class="form-group text-right">
							<button type="submit" class="btn btn-primary">로그인</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>