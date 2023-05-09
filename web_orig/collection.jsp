<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!doctype html>
<html lang="en">
<%@ include file="head.jsp"%>
<body>
<%@ include file="header.jsp"%>
<%@ include file="navigation.jsp"%>
    <div style="display: inline-block; width: 50%;">
        <form action="${pageContext.request.contextPath}/manageOwn" method="POST">
            <fieldset>
            <legend>Seasons you own</legend>
                <c:choose>
                <c:when test="${owned.size() == 0}">
                    <p>You don't have any series available.  Use the other form on this page to add some to your collection!</p>
                </c:when>
                <c:otherwise>
                <input hidden name="userIdRemove" id="userIdRemove" value="${currentUser.id}">
            <c:forEach var="own" items="${owned}" varStatus="status">
                <input type="checkbox" name="season" id="<c:out value="${own.id}"></c:out>" value="<c:out value="${own.id}"></c:out>">
                <label for="<c:out value="${own.id}"></c:out>"><c:out value="${own.series.substring(11, own.series.length())}"></c:out> Season <c:out value="${own.season}"></c:out></label><br>
            </c:forEach>
                    <input hidden name="operation" id="opRemove" value="remove">
                <button style="margin: auto 0;>Remove seasons</button>
                </c:otherwise>
                </c:choose>
            </fieldset>
        </form>
    </div>
    <div style="display: inline-block; width: 50%;">
        <form action="${pageContext.request.contextPath}/manageOwn" method="POST">
            <fieldset>
            <legend>Seasons you do not own</legend>
                <c:choose>
                    <c:when test="${unowned.size() == 0}">
                        <p>You own all the Star Trek there is.</p>
                    </c:when>
                    <c:otherwise>
                        <input hidden name="userIdAdd" id="userIdAdd" value="${currentUser.id}">
                        <c:forEach var="unown" items="${unowned}" varStatus="status">
                            <input type="checkbox" name="season" id="<c:out value="${unown.id}"></c:out>" value="<c:out value="${unown.id}"></c:out>">
                            <label for="<c:out value="${unown.id}"></c:out>"><c:out value="${unown.series.substring(11, unown.series.length())}"></c:out> Season <c:out value="${unown.season}"></c:out></label><br>
                        </c:forEach>
                        <input hidden name="operation" id="opAdd" value="add">
                        <button style="margin: auto 0;>Add seasons</button>
                    </c:otherwise>
                </c:choose>
            </fieldset>
        </form>
    </div>
<%@ include file="footer.jsp"%>
</body>
</html>