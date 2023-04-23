<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<%@ include file="head.jsp"%>
<body>
    <%@ include file="header.jsp"%>
    <%@ include file="navigation.jsp"%>
    <c:choose>
        <c:when test="${empty userName}">
            <a href = "logIn">Log in</a>
        </c:when>
        <c:otherwise>
            <h3>Good to see you, ${userName}</h3>
        </c:otherwise>
    </c:choose>
    index = ${error}
    <%@ include file="footer.jsp"%>
</body>
</html>