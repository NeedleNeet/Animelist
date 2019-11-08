<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Your Favorite Animes</title>
<link rel="stylesheet" href="/homepage.css" />
<link rel="stylesheet" href="/banner.css" />
</head>
<body>
	<header>
		<h1>Your Favorite Animes</h1>
		<br><br>
		<h2>Top Animes</h2>
	</header>
	<div id = "nav">
		<div id = "container">
			<form action ="/home">
				<button type = "submit">Home</button>
			</form>
			<form action = "/showProfile">
				<button type = "submit">Your Profile</button>
			</form>
			<form action="/search" method="post">
				<input type="text" name="keyword" placeholder="Search" aria-label="Search" id = "bar">
				<input type="hidden" name ="page" value = "1"/>
				<button type="submit">Search</button>
			</form>
		</div>
		<div id = "top">
			<form action ="/logout">
				<button type = "submit">Log Out</button>
			</form>
		</div>
	</div>
	<main>
		<div id = "content">
			<c:forEach items="${topAnimes}" var = "ta">
				<div id = "items">
					<div class = "title">
						<h3><a href ="/showAnime/${ta.mal_id}"><c:out value = "${ta.title}"></a></h3></c:out>
					</div>
					<div class="picture">
						<a href ="/showAnime/${ta.mal_id}"><img class = "thumbnail" src = "${ta.image_url}" alt = ""/></a>
					</div>
				</div>
			</c:forEach>
		</div>
	</main>
</body>
</html>