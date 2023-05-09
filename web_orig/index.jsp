<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<%@ include file="head.jsp"%>
<body>
    <%@ include file="header.jsp"%>
    <a href="${pageContext.request.contextPath}/snapshot"><button>Spin the wheel</button></a>
    <c:if test="${currentUser != null}">
        <c:remove var="currentUser"></c:remove>
    </c:if>
    <c:set var="activeUser" value="inactive" scope="session"></c:set>
    <%@ include file="footer.jsp"%>
</body>
</html>