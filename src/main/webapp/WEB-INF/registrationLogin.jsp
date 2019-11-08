<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<%@ page isErrorPage="true" %>   
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Page</title>
    <link rel="stylesheet" href="/landing.css" />
</head>
<body>
	<div id = "title">
			<h1>Welcome to Your Favorite Animes</h1>
	</div>
	<main>
	    <div id = "register">
	    	<div>
	    		<h1>Register</h1>
	    	</div>
	    	<div id = "formL">
		    	<p><form:errors path="user.*"/></p>   
			    <form:form method="POST" action="/registration" modelAttribute="user">
			    	<p>
			    		<form:label path="name">Name:</form:label><br>
			            <form:input type = "text" path="name"/>
			    	</p>
			        <p>
			            <form:label path="email">Email:</form:label><br>
			            <form:input type = "email" path="email"/>
			        </p>
			        <p>
			            <form:label path="password">Password:</form:label><br>
			            <form:password path="password"/>
			        </p>
			        <p>
			            <form:label path="passwordConfirm">Password Confirmation:</form:label><br>
			            <form:password path="passwordConfirm"/>
			        </p>
			        <button type="submit">Register</button>
			    </form:form>
		    </div>
	    </div>
	    
	    <div id = "login">
	    	<div>
	    		<h1>Login</h1>
	    	</div>
	    	<div id = "formR">
			    <p><c:out value="${error}" /></p>
			    <form method="post" action="/login">
			        <p>
			            <label for="email">Email:</label><br>
			            <input type="text" id="email" name="email"/>
			        </p>
			        <p>
			            <label for="password">Password:</label><br>
			            <input type="password" id="password" name="password"/>
			        </p>
			        <button type="submit">Login</button>
			    </form>
		    </div>
	    </div>
    </main>
</body>
</html>