<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %><div>
    <h3>You should watch...</h3>
    <div>
        <div style="width: 50%; float: left;">
            <h2>${selection.episode.title}</h2>
            <p><em>${selection.episode.series.title} (S${selection.episode.seasonNumber}E${selection.episode.episodeNumber})</em></p>
            <c:if test="${currentUser != null}">
                <c:choose>
                    <c:when test="${watchState == 0}">
                        <form action="${pageContext.request.contextPath}/manageViews" method="post">
                            <input hidden name="viewType" value="start">
                            <input hidden name="userId" value="${currentUser.id}">
                            <input hidden name="episodeId" value="${episodeObject.id}">
                            <button class="button content-action" style="margin: auto;">Start Watching</button>
                        </form>
                    </c:when>
                    <c:when test="${watchState == 1}">
                        <form action="${pageContext.request.contextPath}/manageViews" method="post">
                            <input hidden name="viewType" value="done">
                            <input hidden name="userId" value="${currentUser.id}">
                            <input hidden name="episodeId" value="${episodeObject.id}">
                            <button class="button content-action" style="margin: auto;">Finished Viewing</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <button class="button content-action" style="margin: auto;">Viewed</button>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div>
        <div style="width: 50%; float: left;">
            <p>Starring</p>
            <p style="margin-left: 20px;">
                <c:forEach items="${selection.episode.performers}" var="performer" varStatus="status">
                    ${performer.name}<c:if test="${!status.last}">, </c:if>
                </c:forEach>
            </p>
            <c:if test="${!empty selection.episode.directors}">
                <p>Directed by:</p>
                <p style="margin-left: 5%;">
                    <c:forEach items="${selection.episode.directors}" var="director" varStatus="status">
                        ${director.name}<c:if test="${!status.last}">, </c:if>
                    </c:forEach>
                </p>
            </c:if>
            <c:if test="${!empty selection.episode.writers}">
                <p>Written by:</p>
                <p style="margin-left: 5%;">
                    <c:forEach items="${selection.episode.writers}" var="writer" varStatus="status">
                        ${writer.name}<c:if test="${!status.last}">, </c:if>
                    </c:forEach>
                </p>
            </c:if>
        </div>
    </div>
</div>