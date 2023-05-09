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
            TrekRoulette &#149; About Trek Roulette
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

            <h1>Behind the Scenes</h1>

            <h3>Why was this built?</h3>
            <p>Trek Roulette is the product of a semester's work at Madison College in Madison, WI.</p>
            <p>Based on a system devised by a group of friends in Milwaukee, WI, Trek Roulette is designed to provide an answer when you want to watch some Star Trek, but just don't have the energy or inspiration to choose an episode for yourself.  Where the original version was hosted on a local machine via spreadsheets and similar docs, Trek Roulette is hosted on Amazon Web Services, and can be accessed from anywhere on the web.</p>

            <h3>How does it work?</h3>
            <p>Trek Roulette was built in Java to provide the core experience.  On entering the site, the backing database is queried and a complete list of available episodes is compiled.  One is selected at random, and the Star Trek API is called to get the detailed information for that episode.  It is then displayed to the user via JSP.</p>
            <p>Creating an account and logging in allows for the user's collection to be logged and managed.  When logged in, the recommendations are only drawn from those series and seasons that are indicated as "owned" within the user's database records.</p>
            <p>Similarly, when logged in a user can track viewing of a given episode, marking them as being views in progress or completed.  The selection process ignores episodes that have been marked complete, but allows for episodes marked "in progress" to be recommended again until they are marked complete.</p>

            <h3>What is the Star Trek API?</h3>
            <p>Hosted at <a href="https://stapi.co/">stapi.co</a>, the Star Trek API is a large repository of data gathered from <a href="https://memory-alpha.fandom.com/wiki/Portal:Main">Memory Alpha</a>, <a href="https://memory-beta.fandom.com/wiki/Main_Page">Memory Beta</a>, and Star Trek <a href="http://www.startrekcards.com/">collector cards</a>.  Within the data hosted by the API is in-world information about ships, characters, species, and all manner of lore, as well as real-world production data, including cast and crew, broadcast information, and production dates.</p>
            <p>Even the most basic API calls return a wealth of information, only a portion of which is actually used in the performance of Trek Roulette's recommendations.</p>

            <h3>What's with the funky site design?</h3>
            <p>I'm glad you asked.  This site design was developed as an emulation of the in-world LCARS (Library Computer Access/Retrieval System) OS designed by Michael Okuda for Star Trek: The Next Generation.  It has come to be one of the signature elements of Star Trek on the small screen, and can be seen in nearly every series since The Next Generation.</p>
            <p>This particular aesthetic of the design was a version seen in its original setting: the USS Enterprise-D.</p>
            <img src="630-tnglcars2.jpg" alt="Geordi LaForge at an Enterprise D science station">

            <h3>What tools were used to develop Trek Roulette?</h3>
            <br>
            <table>
                <thead>
                <tr colspan="2">
                    <td>Technologies Leveraged</td>
                </tr>
                <tr>
                    <td>Application</td>
                    <td>Purpose</td>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>Amazon Cognito</td>
                    <td>Security/Authentication</td>
                </tr>
                <tr>
                    <td>Amazon RDS<br>MySQL 8.0.22</td>
                    <td>Database</td>
                </tr>
                <tr>
                    <td>Hibernate 5.4.4.Final</td>
                    <td>ORM Framework</td>
                </tr>
                <tr>
                    <td>Maven</td>
                    <td>Dependency Management</td>
                </tr>
                <tr>
                    <td><a href="https://stapi.co/">Star Trek API</a></td>
                    <td>Web Services consumed in Java</td>
                </tr>
                <tr>
                    <td><a href="https://www.thelcars.com/">The LCARS</a> template<br>(modified)</td>
                    <td>CSS</td>
                </tr>
                <tr>
                    <td>Amazon Cognito<br>(authentication data)</td>
                    <td>Data Validation</td>
                </tr>
                <tr>
                    <td>Log4J2</td>
                    <td>Logging</td>
                </tr>
                <tr>
                    <td>Amazon Web Services</td>
                    <td>Hosting</td>
                </tr>
                <tr>
                    <td>JUnit/Jupiter<br><a href="https://squaretest.com/">Squaretest</a></td>
                    <td>Unit Testing</td>
                </tr>
                <tr>
                    <td>IntelliJ IDEA 2022.3.1<br>Ultimate Edition</td>
                    <td>IDE</td>
                </tr>
                </tbody>
            </table>

            <!-- End content area. -->

            <c:import url="footer.jsp"></c:import>
        </div>
    </div>
</div>
<script type="text/javascript" src="lcars.js"></script>
</body>
</html>