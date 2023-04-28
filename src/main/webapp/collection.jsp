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
        <form>
            <legend>Seasons you own</legend>
            <input hidden name="function" id="function" value="remove">
            <input hidden name="userId" id="userId" value="${currentUser.id}">
            <c:forEach var="own" items="${owned}" varStatus="status">
                <c:set var="series" value="${0 + (3 * status.index)}"></c:set>
                <c:set var="season" value="${1 + (3 * status.index)}"></c:set>
                <c:set var="id" value="${2 + (3 * status.index)}"></c:set>
                <input type="checkbox" name="<c:out value="${own.get(series)}"></c:out>" id="<c:out value="${own.get(series)}"></c:out><c:out value="${own.get(season)}"></c:out>" value="<c:out value="${own.get(id)}"></c:out>}">
                <label for="<c:out value="${own.get(series)}"></c:out><c:out value="${own.get(season)}"></c:out>"><c:out value="${own.get(series)}"></c:out> Season <c:out value="${own.get(season)}"></c:out></label><br>
            </c:forEach>
        </form>
        doop doop
    </div>
    <div style="display: inline-block; width: 50%;">
        <form action="/manageOwn" method="POST">
            <legend>Seasons you do not own</legend>
            <input hidden name="function" id="function" value="add">
            <input hidden name="userId" id="userId" value="${currentUser.id}">
            <c:forEach var="unown" items="${unowned}" varStatus="status">
                <c:set var="series" value="${0 + (3 * status.index)}"></c:set>
                <c:set var="season" value="${1 + (3 * status.index)}"></c:set>
                <c:set var="id" value="${2 + (3 * status.index)}"></c:set>
                <input type="checkbox" name="<c:out value="${unown.get(series)}"></c:out>" id="<c:out value="${unown.get(series)}"></c:out><c:out value="${unown.get(season)}"></c:out>" value="<c:out value="${unown.get(id)}"></c:out>}">
                <label for="<c:out value="${unown.get(series)}"></c:out><c:out value="${unown.get(season)}"></c:out>"><c:out value="${unown.get(series)}"></c:out> Season <c:out value="${unown.get(season)}"></c:out></label><br>
            </c:forEach>
        </form>
        doop doop
    </div>
<form>
    <input hidden name="userId" id="userId" value="${currentUser.id}">
    <fieldset>
        <legend>The Original Series</legend>
        <input type="checkbox" id="tos1" name="tos1" value="SAMA...">
        <label for="tos1">1</label>
        <input type="checkbox" id="tos2" name="tos2" value="SAMA...">
        <label for="tos2">2</label>
        <input type="checkbox" id="tos3" name="tos3" value="SAMA...">
        <label for="tos3">3</label>
    </fieldset>
    <fieldset>
        <legend>The Animated Series</legend>
        <input type="checkbox" id="tas1" name="tas1" value="SAMA...">
        <label for="tas1">1</label>
        <input type="checkbox" id="tas2" name="tas2" value="SAMA...">
        <label for="tas2">2</label>
    </fieldset>
    <fieldset>
        <legend>The Next Generation</legend>
        <input type="checkbox" id="tng1" name="tng1" value="SAMA...">
        <label for="tng1">1</label>
        <input type="checkbox" id="tng2" name="tng2" value="SAMA...">
        <label for="tng2">2</label>
        <input type="checkbox" id="tng3" name="tng3" value="SAMA...">
        <label for="tng3">3</label>
        <input type="checkbox" id="tng4" name="tng4" value="SAMA...">
        <label for="tng4">4</label>
        <input type="checkbox" id="tng5" name="tng5" value="SAMA...">
        <label for="tng5">5</label>
        <input type="checkbox" id="tng6" name="tng6" value="SAMA...">
        <label for="tng6">6</label>
        <input type="checkbox" id="tng7" name="tng7" value="SAMA...">
        <label for="tng7">7</label>
    </fieldset>
    <fieldset>
        <legend>Deep Space Nine</legend>
        <input type="checkbox" id="ds91" name="ds91" value="SAMA...">
        <label for="ds91">1</label>
        <input type="checkbox" id="ds92" name="ds92" value="SAMA...">
        <label for="ds92">2</label>
        <input type="checkbox" id="ds93" name="ds93" value="SAMA...">
        <label for="ds93">3</label>
        <input type="checkbox" id="ds94" name="ds94" value="SAMA...">
        <label for="ds94">4</label>
        <input type="checkbox" id="ds95" name="ds95" value="SAMA...">
        <label for="ds95">5</label>
        <input type="checkbox" id="ds96" name="ds96" value="SAMA...">
        <label for="ds96">6</label>
        <input type="checkbox" id="ds97" name="ds97" value="SAMA...">
        <label for="ds97">7</label>
    </fieldset>
    <fieldset>
        <legend>Voyager</legend>
        <input type="checkbox" id="voy1" name="voy1" value="SAMA...">
        <label for="voy1">1</label>
        <input type="checkbox" id="voy2" name="voy2" value="SAMA...">
        <label for="voy2">2</label>
        <input type="checkbox" id="voy3" name="voy3" value="SAMA...">
        <label for="voy3">3</label>
        <input type="checkbox" id="voy4" name="voy4" value="SAMA...">
        <label for="voy4">4</label>
        <input type="checkbox" id="voy5" name="voy5" value="SAMA...">
        <label for="voy5">5</label>
        <input type="checkbox" id="voy6" name="voy6" value="SAMA...">
        <label for="voy6">6</label>
        <input type="checkbox" id="voy7" name="voy7" value="SAMA...">
        <label for="voy7">7</label>
    </fieldset>
    <fieldset>
        <legend>Enterprise</legend>
        <input type="checkbox" id="ent1" name="ent1" value="SAMA...">
        <label for="ent1">1</label>
        <input type="checkbox" id="ent2" name="ent2" value="SAMA...">
        <label for="ent2">2</label>
        <input type="checkbox" id="ent3" name="ent3" value="SAMA...">
        <label for="ent3">3</label>
        <input type="checkbox" id="ent4" name="ent4" value="SAMA...">
        <label for="ent4">4</label>
    </fieldset>
    <fieldset>
        <legend>Discovery</legend>
        <input type="checkbox" id="dis1" name="dis1" value="SAMA...">
        <label for="dis1">1</label>
        <input type="checkbox" id="dis2" name="dis2" value="SAMA...">
        <label for="dis2">2</label>
        <input type="checkbox" id="dis3" name="dis3" value="SAMA...">
        <label for="dis3">3</label>
        <input type="checkbox" id="dis4" name="dis4" value="SAMA...">
        <label for="dis4">4</label>
        <input type="checkbox" id="dis5" name="dis5" value="SAMA...">
        <label for="dis5">5</label>
    </fieldset>
    <fieldset>
        <legend>Picard</legend>
        <input type="checkbox" id="pic1" name="pic1" value="SAMA...">
        <label for="pic1">1</label>
        <input type="checkbox" id="pic2" name="pic2" value="SAMA...">
        <label for="pic2">2</label>
        <input type="checkbox" id="pic3" name="pic3" value="SAMA...">
        <label for="pic3">3</label>
    </fieldset>
    <fieldset>
        <legend>Lower Decks</legend>
        <input type="checkbox" id="ld1" name="ld1" value="SAMA...">
        <label for="ld1">1</label>
        <input type="checkbox" id="ld2" name="ld2" value="SAMA...">
        <label for="ld2">2</label>
        <input type="checkbox" id="ld3" name="ld3" value="SAMA...">
        <label for="ld3">3</label>
        <input type="checkbox" id="ld4" name="ld4" value="SAMA...">
        <label for="ld4">4</label>
        <input type="checkbox" id="ld5" name="ld5" value="SAMA...">
        <label for="ld5">5</label>
    </fieldset>
    <fieldset>
        <legend>Prodigy</legend>
        <input type="checkbox" id="pro1" name="pro1" value="SAMA...">
        <label for="pro1">1</label>
        <input type="checkbox" id="pro2" name="pro2" value="SAMA...">
        <label for="pro2">2</label>
    </fieldset>
    <fieldset>
        <legend>Strange New Worlds</legend>
        <input type="checkbox" id="snw1" name="snw1" value="SAMA...">
        <label for="snw1">1</label>
        <input type="checkbox" id="snw2" name="snw2" value="SAMA...">
        <label for="snw2">2</label>
        <input type="checkbox" id="snw3" name="snw3" value="SAMA...">
        <label for="snw3">3</label>
    </fieldset>
</form>


<%@ include file="footer.jsp"%>
</body>
</html>