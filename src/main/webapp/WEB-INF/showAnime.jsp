<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Anime Info</title>
<link rel="stylesheet" href="/banner.css" />
<link rel="stylesheet" href="/show.css" />
</head>
<body>
	<header>
		<h1>${anime.title}</h1>
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
		<div id = "left">
			<img src = "${anime.image_url}" alt ="" id ="image"/>
			<p class = "infor">
				Title: ${anime.title}
			</p>
			<p class = "infor">
				Type: ${anime.type}
			</p>
			<p class = "infor">
				Source: ${anime.source}
			</p>
			<p class = "infor">
				Episodes: ${anime.episodes}
			</p>
			<p class = "infor">
				Status: ${anime.status}
			</p>
			<p class = "infor">
				Aired: ${anime.aired.string}
			</p>
			<p class = "infor">
				Duration: ${anime.duration}
			</p>
		</div>
		<div id = "right">
			<div id = "join">
				<p id = "score">
					Score: ${anime.score}
				</p>
				<p id = "add">
					<c:choose>
						<c:when test="${added == false}">
							<form action ="/add/${anime.mal_id}" method= "post">				
								<button type = "submit" class="btn btn-primary btn-lg btn-block">Add it to Your Favorite</button>
							</form>
						</c:when>
						<c:otherwise>
							<form action ="/delete/${anime.mal_id}" method= "post">				
								<button type = "submit" class="btn btn-primary btn-lg btn-block">Remove from Your Favorite</button>
							</form>
						</c:otherwise>
					</c:choose>
				</p>
			</div>
			<p id = "synopsis">
				${anime.synopsis}
			</p>
			<p>
				<h2>Trailer:<br></h2>
				<iframe width="560" height="315" src="${anime.trailer_url}" frameborder="0" allow="encrypted-media" allowfullscreen></iframe>
			</p>
		</div>
	</main>
</body>
</html>