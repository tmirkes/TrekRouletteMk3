<div>
    <table>
        <thead>
            <tr>
                <th>You should watch...</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td></td>
                <td><h2>${selection.episode.title}</h2></td>
            </tr>
            <tr>
                <td></td>
                <td><em>${selection.episode.series.title} (S${selection.episode.seasonNumber}E${selection.episode.episodeNumber})</em></td>
            </tr>
            <tr>
                <td></td>
                <td>Starring:</td>
                <td>
                    <c:forEach items="${selection.episode.performers}" var="performer" varStatus="status">
                        ${performer.name}<c:if test="${!status.last}">, </c:if>
                    </c:forEach>
                </td>
            </tr>
            <c:if test="${!empty selection.episode.directors}">
                <tr>
                    <td></td>
                    <td>Directed by:</td>
                    <td>
                        <c:forEach items="${selection.episode.directors}" var="director" varStatus="status">
                            ${director.name}<c:if test="${!status.last}">, </c:if>
                        </c:forEach>
                    </td>
                </tr>
            </c:if>
            <c:if test="${!empty selection.episode.writers}">
                <tr>
                    <td></td>
                    <td>Written by:</td>
                    <td>
                        <c:forEach items="${selection.episode.writers}" var="writer" varStatus="status">
                            ${writer.name}<c:if test="${!status.last}">, </c:if>
                        </c:forEach>
                    </td>
                </tr>
            </c:if>
            <c:if test="${currentUser != null}">
                <c:choose>
                    <c:when test="${watchState == 0}">
                        <tr>
                            <td></td>
                            <td>
                                <form action="${pageContext.request.contextPath}/manageViews" method="post">
                                    <input hidden name="viewType" value="start">
                                    <input hidden name="userId" value="${currentUser.id}">
                                    <input hidden name="episodeId" value="${episodeObject.id}">
                                    <button id="top-left" style="margin: auto 0;>Start Watching</button>
                                </form>
                            </td>
                        </tr>
                    </c:when>
                    <c:when test="${watchState == 1}">
                        <tr>
                            <td></td>
                            <td>
                                <form action="${pageContext.request.contextPath}/manageViews" method="post">
                                    <input hidden name="viewType" value="done">
                                    <input hidden name="userId" value="${currentUser.id}">
                                    <input hidden name="episodeId" value="${episodeObject.id}">
                                    <button id="mid-right" style="margin: auto 0;>Finished Viewing</button>
                                </form>
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr><td></td><td><button disabled>Viewed</button></td></tr>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </tbody>
    </table>
</div>