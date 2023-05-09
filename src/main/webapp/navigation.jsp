<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- https://stackoverflow.com/questions/8381151/can-you-use-jstl-cif-to-test-against-a-url-pattern -->
<div class="button-col">
    <c:choose>
        <c:when test="${currentUser == null}">
            <div class="button" id="top-left"><a href="public.jsp">Home</a></div>
        </c:when>
        <c:when test="${fn:contains(pageContext.request.requestURI, '/manageOwn') || fn:contains(pageContext.request.requestURI, '/collection')}">
            <div class="button" id="top-left"><a href="snapshot">Home</a></div>
        </c:when>
        <c:otherwise>
            <div class="button" id="top-left"><a href="member.jsp">Home</a></div>
        </c:otherwise>
    </c:choose>
    <div class="button" id="mid-left"><a href="about.jsp">About</a></div>
    <c:if test="${currentUser != null && currentUser.userName == 'admin'}">
        <div class="button" id="bottom-left"><a href="update.jsp">Updates</a></div>
    </c:if>
</div>
<div class="button-col">
    <c:if test="${fn:contains(pageContext.request.requestURI, '/public') || fn:contains(pageContext.request.requestURI, '/member')}">
        <div class="button" id="top-right"><a href="getEpisode">Suggest</a></div>
    </c:if>
    <c:choose>
        <c:when test="${currentUser != null}">
            <div class="button" id="mid-right"><a href="manageOwn">Collection</a></div>
        </c:when>
        <c:otherwise>
            <div class="button" id="mid-right"><a href=""></a></div>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${currentUser == null}">
            <div class="button" id="bottom-right"><a href="logIn">Log In</a></div>
        </c:when>
        <c:otherwise>
            <div class="button" id="bottom-right"><a href="logOut">Log Out</a></div>
        </c:otherwise>
    </c:choose>
</div>
