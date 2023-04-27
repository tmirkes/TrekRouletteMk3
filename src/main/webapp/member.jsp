<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<%@ include file="head.jsp"%>
<body>
<%@ include file="header.jsp"%>
<%@ include file="navigation.jsp"%>
<%@ include file="episode.jsp"%>
<p>member = ${error}</p>
<p>welcome back, ${currentUser.userName}(${currentUser.id})!</p>
<%@ include file="footer.jsp"%>
</body>
</html>