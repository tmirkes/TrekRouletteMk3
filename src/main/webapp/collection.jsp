<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!doctype html>
<html lang="en">
<%@ include file="head.jsp"%>
<body>
<%@ include file="header.jsp"%>
<%@ include file="navigation.jsp"%>
owned = ${owned.size()}<br>
unowned = ${unowned.size()}<br>
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
                <c:set var="series" value="${0 + (3 * status.index)}"></c:set>
                <c:set var="season" value="${1 + (3 * status.index)}"></c:set>
                <c:set var="id" value="${2 + (3 * status.index)}"></c:set>
                <input type="checkbox" name="season" id="<c:out value="${own.get(id)}"></c:out>" value="<c:out value="${own.get(id)}"></c:out>">
                <label for="<c:out value="${own.get(id)}"></c:out>"><c:out value="${own.get(series)}"></c:out> Season <c:out value="${own.get(season)}"></c:out></label><br>
            </c:forEach>
                    <input hidden name="operation" id="opRemove" value="remove">
            <button>Remove seasons</button>
                </c:otherwise>
                </c:choose>
            </fieldset>
        </form>
    </div>
    <div style="display: inline-block; width: 50%;">
        <form action="${pageContext.request.contextPath}/manageOwn" method="POST">
            <fieldset>
            <legend>Seasons you do not own</legend>
            <input hidden name="userIdAdd" id="userIdAdd" value="${currentUser.id}">
            <c:forEach var="unown" items="${unowned}" varStatus="status">
                <c:set var="series" value="${0 + (3 * status.index)}"></c:set>
                <c:set var="season" value="${1 + (3 * status.index)}"></c:set>
                <c:set var="id" value="${2 + (3 * status.index)}"></c:set>
                <input type="checkbox" name="season" id="<c:out value="${unown.get(id)}"></c:out>" value="<c:out value="${unown.get(id)}"></c:out>">
                <label for="<c:out value="${unown.get(id)}"></c:out>"><c:out value="${unown.get(series)}"></c:out> Season <c:out value="${unown.get(season)}"></c:out></label><br>
            </c:forEach>
                <input hidden name="operation" id="opAdd" value="add">
            <button>Add seasons</button>
            </fieldset>
        </form>
    </div>

<%@ include file="footer.jsp"%>
</body>
</html>