function sendCheckRequest(form, key) {
    $.get("app", {
        'x': form.x,
        'y': form.y,
        'r': form.r,
        'key': key
    }).done(function (data) {
        document.getElementById("outputContainer").innerHTML = data;
    }).fail(function (xhr, status, error) {
        alert(xhr.responseText)
    });
}

function submit() {
    let form = validateForm();
    if (form == null) return;

    sendCheckRequest(form, "button");
}

function clearTable() {
    $.get("app", {
        "clear": true
    }).done(function (data) {
        document.getElementById("outputContainer").innerHTML = data;
    }).fail(function (xhr, status, error) {
        alert(xhr.responseText);
    });
}

function showError(message) {
    $('#errors').append(`<li>${message}</li>`)
}

function validateX() {
    let xRadio = document.querySelector('input[name="x"]:checked');
    if (xRadio === null || xRadio === undefined) {
        showError("Выберите X");
        return null;
    }
    return xRadio.value;
}

function validateY() {
    let y = document.querySelector("input[id=y-input]").value.replace(",", ".");
    if (y === undefined) {
        showError("Введите Y");
        return null;
    }
    if (!isNumeric(y)) {
        showError("Y не является числом");
        return null;
    }
    if (!((y > -3) && (y < 3))) {
        showError("Y не входит в область допустимых значений");
        return null;
    }
    return y;
}

$(".r-checkbox").change(function () {
    $(".r-checkbox").not(this).prop('checked', false);
});

function validateR() {
    let rCheck = document.querySelector("input[name='r']:checked");
    if (rCheck === null || rCheck === undefined) {
        showError("Выберите R");
        return null;
    }
    return rCheck.value;
}

function clearErrors() {
    $('#errors').empty();
}

function validateForm() {
    clearErrors();
    let x = validateX(), y = validateY(), r = validateR();
    if (x != null && y != null && r != null)
        return {x: x, y: y, r: r};
    else return null;
}

function isNumeric(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}