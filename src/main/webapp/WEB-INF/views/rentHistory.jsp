<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page contentType="text/html; charset=utf8"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>ホーム｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="widt=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap CSS -->
<link rel= "stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css">
</head>
<body class="wrapper">
    <header>
        <div class="left">
            <img class="mark" src="resources/img/logo.png" />
            <div class="logo">Seattle Library</div>
        </div>
        <div class="right">
            <ul>
                <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
                <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li>
            </ul>
        </div>
    </header>
 <main>
  <div class ="content_body">
   <div class ="col-5 ml-3">
    <table class="table table-bordered">
     <thead>
      <tr>
      <th>書籍名</th>
      <th>貸出日</th>
      <th>返却日</th>
    </tr>
    </thead>
  <tbody>
     <c:forEach var="rentHistory" items="${bookList}">
    <tr>
     <td><form method="post" action="<%=request.getContextPath()%>/details">
     <a href="javascript:void(0)" onclick="this.parentNode.submit();">${rentHistory.title}
     </a><input type="hidden" name="bookId" value="${rentHistory.id}">
   </form>
      </td>
     <td>${rentHistory.rentDate}</td> 
     <td>${rentHistory.returnDate}</td> 
    </tr>
    </c:forEach>
  </tbody>
</table>
</div>
</div>
</main>
</body>
</html>