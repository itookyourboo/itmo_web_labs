let x, y, r;
const dataType = 'html';


if (dataType === 'json') {
    window.onload = (event) => {
        if (document.cookie !== '') {
            document.cookie.split("|").forEach(function (strElem) {
                let newRow = generateRowFromElem(JSON.parse(strElem));
                $('#result-table tr:first').after(newRow);
            });
        }
    };
}

function chooseR(element) {
    r = element.value;
    [...document.getElementsByClassName("r-button")].forEach(function (btn) {
        btn.style.transform = "";
    });
    element.style.transform = "scale(1.1)";
}

function submit() {
    $('#errors').empty();
    if (validateX() & validateY() & validateR()) {
        $.get("php/answer.php", {
            'x': x.join(','),
            'y': y,
            'r': r,
            'timezone': new Date().getTimezoneOffset(),
            'wholeTable': false,
            'dataType': dataType
        }).done(function (data) {
            if (dataType === 'json') {
                let arr = JSON.parse(data);
                arr.forEach(function (elem) {
                    if (!elem.validate) return;
                    let newRow = generateRowFromElem(elem);
                    $('#result-table tr:first').after(newRow);
                    if (document.cookie !== '') {
                        document.cookie += '|' + JSON.stringify(elem)
                    } else document.cookie = JSON.stringify(elem);
                });
            } else if (dataType === 'html') {
                $('#result-table tr:first').after(data);
            }
        }).fail(function(xhr, status, error) {
            alert(xhr.responseText)
        });
    }
}

function generateRowFromElem(elem) {
    let newRow = elem.isHit ? '<tr class="hit-yes">' : '<tr class="hit-no">';
    newRow += '<td>' + elem.x + '</td>';
    newRow += '<td>' + elem.y + '</td>';
    newRow += '<td>' + elem.r + '</td>';
    newRow += '<td>' + elem.currentTime + '</td>';
    newRow += '<td>' + elem.execTime + '</td>';
    newRow += '<td>' + (elem.isHit ? 'Да' : 'Нет') + '</td>';

    return newRow;
}

function showError(message) {
    $('#errors').append(`<li>${message}</li>`)
}

function validateX() {
    const boxes = [...document.querySelectorAll("input[class=x-checkbox]:checked")];
    if (boxes.length === 0) {
        showError("Выберите хотя бы один X");
        return false;
    }
    x = boxes.map(x => x.getAttribute('value'));
    return true;
}

function validateY() {
    y = document.querySelector("input[id=y-input]").value.replace(",", ".");
    if (y === undefined) {
        showError("Введите Y");
        return false;
    }
    if (!isNumeric(y)) {
        showError("Y не является числом");
        return false;
    }
    if (!((y > -3) && (y < 3))) {
        showError("Y не входит в область допустимых значений");
        return false;
    }
    return true;
}

function validateR() {
    if (r)
        return true;

    showError("Выберите R");
    return false;
}

function isNumeric(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}