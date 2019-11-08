<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search Result</title>
<link rel="stylesheet" href="/banner.css" />
<link rel="stylesheet" href="/result.css" />
</head>
<body>
	<header>
		<h1>Search Result</h1>
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
		<c:forEach items="${animes}" var="a">
			<div id = "items">
					<div class = "title">
						<h3><a href ="/showAnime/${a.mal_id}"><c:out value = "${a.title}"></a></h3></c:out>
					</div>
					<div class="picture">
						<a href ="/showAnime/${a.mal_id}"><img class = "thumbnail" src = "${a.image_url}" alt = ""/></a>
					</div>
				</div>
		</c:forEach>
	</main>
	<footer>
		<div id = "pagination">
			<form action = "/previousPage" method = "post">
				<input type="hidden" name = "oldKeyword" value = "${oldKeyword}"/>
				<input type="hidden" name = "page" value = "${page}"/>
				<button type="submit">Last Page</button>
			</form>
			<div id = "pageCount">
				<c:set var = "currPage" value = "${page}"/>
				<c:forEach items="${pages}" var="pg">
					<c:choose>
						<c:when test="${currPage == pg}">
							<div id = "currP"><c:out value="${pg}"/></div>
						</c:when>
						<c:otherwise>
							<form action = "/search" method = "post">
								<input type="hidden" name = "page" value = "${pg}"/>
								<input type="hidden" name = "keyword" value = "${oldKeyword}"/>
								<button type = "submit">${pg}</button>
							</form>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<form action = "/nextPage" method = "post">
				<input type="hidden" name = "oldKeyword" value = "${oldKeyword}"/>
				<input type="hidden" name = "page" value = "${page}"/>
				<button type="submit">Next Page</button>
			</form>
		</div>
	</footer>
</body>
</html>