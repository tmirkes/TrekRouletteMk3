<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<%@ include file="head.jsp"%>
<body>
    <%@ include file="header.jsp"%>
    <p>Welcome to Trek Roulette</p>
    <a href="${pageContext.request.contextPath}/loadData"><button>Spin the wheel</button></a>
    <%@ include file="footer.jsp"%>
</body>
</html>