<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="entries" class="web_lab2.beans.EntriesBean" scope="session"/>

<%! final int VERSION = 59; %>

<head>
    <meta charset="UTF-8">
    <link rel="shortcut icon" href="#"/>
    <link rel="stylesheet" href="css/style.css?<%=VERSION%>">
    <title>Web lab №2</title>
</head>
<body>
<header>
    <h1>Лабораторная работа №2. Вариант 521682</h1>
    <h2>Харламов Александр Сергеевич, P3213</h2>
</header>

<div class="param x-param">
    <label for="x-radios">X: </label>
    <div id="x-radios">
        <% for (int i = -3; i <= 5; i++) { %>
        <div class="checkbox-centered">
            <input class="x-radio" id="x-radio<%=i%>" type="radio" name="x" value="<%=i%>">
            <label for="x-radio<%=i%>"><%=i%>
            </label>
        </div>
        <% } %>
    </div>
</div>

<div class="param y-param">
    <label for="y-input">Y: </label>
    <input id="y-input" type="text" name="y" maxlength="10" autocomplete="off" placeholder="(-3; 3)">
</div>

<div class="param r-param">
    <label for="r-checkboxes">R: </label>
    <div id="r-checkboxes">
        <% for (int i = 2; i <= 6; i++) { %>
        <div class="checkbox-centered">
            <input class="r-checkbox" id="r-checkbox<%=i%>" type="checkbox" name="r" value="<%=(float) i / 2%>">
            <label for="r-checkbox<%=i%>"><%=(float) i / 2%>
            </label>
        </div>
        <% } %>
    </div>
</div>

<svg id="areas" width="300px" height="300px"></svg>

<input id="checkBtn" class="submit-btn" type="submit" onclick="submit()" value="Проверить">
<input id="clearBtn" class="submit-btn" type="submit" onclick="clearTable()" value="Очистить">

<ul id="errors" style="list-style-type: none"></ul>

<div id="outputContainer">
    <table id="result-table">
        <tr class="table-header">
            <th class="coords-col">X</th>
            <th class="coords-col">Y</th>
            <th class="coords-col">R</th>
            <th class="time-col">Время запроса</th>
            <th class="time-col">Время исполнения</th>
            <th class="hitres-col">Попадание</th>
        </tr>
        <c:forEach var="entry" items="${entries.entries}">
            <tr class="${(entry.hit? "hit-yes": "hit-no")}">
                <td>${entry.x}</td>
                <td>${entry.y}</td>
                <td>${entry.r}</td>
                <td>${entry.currentTime}</td>
                <td>${entry.executionTime}</td>
                <td>${(entry.hit? "Да" : "Нет")}</td>
            </tr>
        </c:forEach>
    </table>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://d3js.org/d3.v7.min.js"></script>
<script src="js/generate_areas.js?<%=VERSION%>"></script>
<script src="js/main.js?<%=VERSION%>"></script>
</body>
</html>
