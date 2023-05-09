<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<c:import url="head.jsp"></c:import>
<body>
<div class="wrap">
    <div class="scroll-top">
        <a id="scroll-top" href=""><span class="hop">screen</span> top</a>
    </div>
    <div class="left-frame-top">
        <div class="panel-1"><a href="">LCARS</a></div>
        <div class="panel-2">02<span class="hop">-262000</span></div>
    </div>
    <div class="right-frame-top">
        <div class="banner">
            TrekRoulette &#149; Collection
        </div>
        <div class="data-cascade-button-group">
            <div class="cascade-wrapper">
                <div class="data-cascade" id="default">
                    <div class="row-1"><div class="dc1">101</div><div class="dc2">7109</div><div class="dc3">1966</div><div class="dc4">1222</div><div class="dc5">2020</div><div class="dc6">1444</div><div class="dc7">102</div><div class="dc8">1103</div><div class="dc9">1935</div><div class="dc10">1940</div><div class="dc11">708</div><div class="dc12">M113</div><div class="dc13">1956</div><div class="dc14">1209</div></div><div class="row-2"><div class="dc1">102</div><div class="dc2">8102</div><div class="dc3">1987</div><div class="dc4">044</div><div class="dc5">0051</div><div class="dc6">607</div><div class="dc7">1976</div><div class="dc8">1031</div><div class="dc9">1984</div><div class="dc10">1954</div><div class="dc11">1103</div><div class="dc12">415</div><div class="dc13">1045</div><div class="dc14">1864</div></div><div class="row-3"><div class="dc1">103</div><div class="dc2">714</div><div class="dc3">1993</div><div class="dc4">0222</div><div class="dc5">052</div><div class="dc6">1968</div><div class="dc7">2450</div><div class="dc8">746</div><div class="dc9">56</div><div class="dc10">47</div><div class="dc11">716</div><div class="dc12">8719</div><div class="dc13">417</div><div class="dc14">602</div></div><div class="row-4"><div class="dc1">104</div><div class="dc2">6104</div><div class="dc3">1995</div><div class="dc4">322</div><div class="dc5">90</div><div class="dc6">1931</div><div class="dc7">1701</div><div class="dc8">51</div><div class="dc9">29</div><div class="dc10">218</div><div class="dc11">908</div><div class="dc12">2114</div><div class="dc13">85</div><div class="dc14">3504</div></div><div class="row-5"><div class="dc1">105</div><div class="dc2">08</div><div class="dc3">2001</div><div class="dc4">713</div><div class="dc5">079</div><div class="dc6">1940</div><div class="dc7">LV</div><div class="dc8">426</div><div class="dc9">105</div><div class="dc10">10</div><div class="dc11">1206</div><div class="dc12">1979</div><div class="dc13">402</div><div class="dc14">795</div></div><div class="row-6"><div class="dc1">106</div><div class="dc2">31</div><div class="dc3">2017</div><div class="dc4">429</div><div class="dc5">65</div><div class="dc6">871</div><div class="dc7">1031</div><div class="dc8">541</div><div class="dc9">656</div><div class="dc10">764</div><div class="dc11">88</div><div class="dc12">001</div><div class="dc13">27</div><div class="dc14">05</div></div>
                </div>
            </div>
            <c:import url="navigation.jsp"></c:import>
        </div>
        <div class="runner">
            <div class="top-corner-bg">
                <div class="top-corner"></div>
            </div>
            <div class="block-2"></div>
            <div class="block-4">
                <div class="block-4-floatbar-top"></div>
            </div>
        </div>
        <div class="bar-panel">
            <div class="bar-1"></div>
            <div class="bar-2"></div>
            <div class="bar-3"></div>
            <div class="bar-4"><div class="bar-4-inside"></div></div>
            <div class="bar-5"></div>
        </div>
    </div>
</div>
<div class="wrap" id="gap">
    <div class="left-frame">
        <div>
            <div class="panel-3">03<span class="hop">-111968</span></div>
            <div class="panel-4">04<span class="hop">-041969</span></div>
            <div class="panel-5">05<span class="hop">-1701D</span></div>
            <div class="panel-6">06<span class="hop">-071984</span></div>
            <div class="panel-7">07<span class="hop">-081940</span></div>
            <div class="panel-8">08<span class="hop">-47148</span></div>
            <div class="panel-9">09<span class="hop">-081966</span></div>
        </div>
        <div>
            <div class="panel-10">10<span class="hop">-31</span></div>
        </div>
    </div>
    <div class="right-frame">
        <div class="bar-panel">
            <div class="bar-6"></div>
            <div class="bar-7"></div>
            <div class="bar-8"></div>
            <div class="bar-9"><div class="bar-9-inside"></div></div>
            <div class="bar-10"></div>
        </div>
        <div class="runner">
            <div class="corner-bg">
                <div class="corner"></div>
            </div>
            <div class="block-2"></div>
            <div class="block-4b">
                <div class="block-4-floatbar-bottom"></div>
                <div class="block-4-thinbar"></div>
            </div>
        </div>
        <div class="content">

            <!-- Start your content here. -->

            <h1>Your Collection</h1>

            <p>Use this interface to log which series you have available in your collection.  This can include any form of access, including digital files, streaming access, or physical media.</p>
            <div style="display: inline-block; width: 50%; height: 100%; float: left;">
                <form action="${pageContext.request.contextPath}/manageOwn" method="POST">
                    <fieldset>
                        <legend>Access</legend>
                        <c:choose>
                            <c:when test="${owned.size() == 0}">
                                <p>You don't have any series available.  Add some to your collection!</p>
                            </c:when>
                            <c:otherwise>
                                <input hidden name="userIdRemove" id="userIdRemove" value="${currentUser.id}">
                                <c:forEach var="own" items="${owned}" varStatus="status">
                                    <input type="checkbox" name="season" id="<c:out value="${own.id}"></c:out>" value="<c:out value="${own.id}"></c:out>">
                                    <label for="<c:out value="${own.id}"></c:out>"><c:out value="${own.series.substring(11, own.series.length())}"></c:out> Season <c:out value="${own.season}"></c:out></label><br>
                                </c:forEach>
                                <input hidden name="operation" id="opRemove" value="remove">
                            </c:otherwise>
                        </c:choose>
                    </fieldset>
                    <div style="width: 100%; justify-content: center;"><button class="button content-action" style="margin: auto;">Remove access</button></div>
                </form>
            </div>
            <div style="display: inline-block; width: 50%; height: 100%;">
                <form action="${pageContext.request.contextPath}/manageOwn" method="POST">
                    <fieldset>
                        <legend>No access</legend>
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
                            </c:otherwise>
                        </c:choose>
                    </fieldset>
                    <div style="width: 100%; justify-content: center;"><button class="button content-action" style="margin: auto;">Add access</button></div>
                </form>
            </div>

            <!-- End content area. -->

            <c:import url="footer.jsp"></c:import>
        </div>
    </div>
</div>
<script type="text/javascript" src="lcars.js"></script>
</body>
</html>