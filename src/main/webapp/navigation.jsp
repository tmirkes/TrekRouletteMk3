<c:choose>
    <c:when test="${currentUser == null}">
        <a href="public.jsp"><button>Home</button></a>
    </c:when>
    <c:otherwise>
        <a href="member.jsp"><button>Home</button></a>
    </c:otherwise>
</c:choose>
<a href="about.jsp"><button>About</button></a>
<a href="/selector"><button>New Recommendation</button></a>
<c:if test="${currentUser != null}">
    <a href="collection.jsp"><button>Collection</button></a>
</c:if>
<c:choose>
    <c:when test="${currentUser == null}">
        <a href = "logIn"><button>Log In</button></a>
    </c:when>
    <c:otherwise>
        <a href = "logOut"><button>Log Out</button></a>
    </c:otherwise>
</c:choose>