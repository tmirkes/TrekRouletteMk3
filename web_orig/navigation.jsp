<!-- https://stackoverflow.com/questions/8381151/can-you-use-jstl-cif-to-test-against-a-url-pattern -->
<c:choose>
    <c:when test="${currentUser == null}">
        <a href="public.jsp"><button>Home</button></a>
    </c:when>
    <c:when test="${fn:contains(pageContext.request.requestURI, '/manageOwn') || fn:contains(pageContext.request.requestURI, '/collection')}">
        <a href="snapshot"><button>Home</button></a>
    </c:when>
    <c:otherwise>
        <a href="member.jsp"><button>Home</button></a>
    </c:otherwise>
</c:choose>
<a href="about.jsp"><button>About</button></a>
<c:if test="${fn:contains(pageContext.request.requestURI, '/public') || fn:contains(pageContext.request.requestURI, '/member')}">
    <a href="getEpisode"><button>New Recommendation</button></a>
</c:if>
<c:if test="${currentUser != null}">
    <a href="manageOwn"><button>Collection</button></a>
</c:if>
<c:if test="${currentUser != null && currentUser.userName == 'admin'}">
    <a href="update.jsp"><button>Check for Updates</button></a>
</c:if>
<c:choose>
    <c:when test="${currentUser == null}">
        <a href = "logIn"><button>Log In</button></a>
    </c:when>
    <c:otherwise>
        <a href = "logOut"><button>Log Out</button></a>
    </c:otherwise>
</c:choose>