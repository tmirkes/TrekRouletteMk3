<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="en">
<%@ include file="head.jsp"%>
<body>
    <%@ include file="header.jsp"%>
    <%@ include file="navigation.jsp"%>
    <%@ include file="episode.jsp"%>
    index = ${error}

    ${episodeList}

    ${randomList}
    <%@ include file="footer.jsp"%>
</body>
</html>