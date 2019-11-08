<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Your Profile</title>
<link rel="stylesheet" href="profile.css" />
<link rel="stylesheet" href="/banner.css" />
</head>
<body>
	<header>
		<h1>Your Favorite Animes</h1>
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
		<div id = left>
			<p>
				<h1>Welcome to your Profile ${user.name}</h1>
			</p>
			<p>
				<img class="profilePic" src="user-placeholder.png" alt="" />
			</p>
		</div>
		<div id = right>
			<c:forEach items="${userFavorites}" var = "uf">
				<div id = "items">
					<div class = "title">
						<h3><a href ="/showAnime/${uf.anime.id}"><c:out value = "${uf.anime.title}"></a></h3></c:out>
					</div>
					<div class="picture">
						<a href ="/showAnime/${uf.anime.id}"><img class = "thumbnail" src = "${uf.anime.imageUrl}" alt = ""/></a>
					</div>
				</div>
			</c:forEach>
		</div>
	</main>
</body>
</html>