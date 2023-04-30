<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="en">
<%@ include file="head.jsp"%>
<body>
<%@ include file="header.jsp"%>
<%@ include file="navigation.jsp"%>
update = ${error}
<c:choose>
    <c:when test="${updateStatus == null}">
        <p>Do you want to check for database updates from stapi.co?</p>
        <a href="dbUpdate"><button>Proceed</button></a>
    </c:when>
    <c:otherwise>
        Update check complete.<br>
        ${updateStatus}
        <c:remove var="updateStatus" scope="session"></c:remove>
    </c:otherwise>
</c:choose>
<%@ include file="footer.jsp"%>
</body>
</html>