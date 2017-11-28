<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-default">
	<div class="container">
    	<div class="navbar-header">
      		<a class="navbar-brand" href="/home.do">home</a>
    	</div>
    	<ul class="nav navbar-nav">
      		<li class="active"><a href="/home.do">Home</a></li>
      		<c:if test="${not empty LOGIN_USER }">
      			<li><a href="/users.do">목록</a></li>
      		</c:if>
    	</ul>
    	<ul class="nav navbar-nav navbar-right">
    		<c:if test="${not empty LOGIN_USER }">
    			<p class="navbar-text"><strong class=''>${LOGIN_USER.name }</strong>님 환영합니다.</p>
    		</c:if>
    	<c:choose>
    		<c:when test="${empty LOGIN_USER }">
	      		<li><a href="form.do">회원가입</a></li>
	      		<li><a href="login.do">로그인</a></li>
    		</c:when>
    		<c:otherwise>
    			<li><a href="logout.do">로그아웃</a></li>
    		</c:otherwise>
    	</c:choose>
    	</ul>
  	</div>
</nav>