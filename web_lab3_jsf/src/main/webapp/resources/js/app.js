$(document).ready(function () {
    const rHidden = document.getElementById('form:r_hidden');
    let buttons = document.querySelectorAll(".r-group");

    if (rHidden.value && buttons) {
        buttons[rHidden.value - 1].style.color = 'red';
    }

    buttons.forEach(click);
    function click(element) {
        element.onclick = function () {
            buttons.forEach(function (element) {
                element.style.color = null;
            });
            this.style.color = "red";
            rHidden.value = this.textContent;
            redrawGraph();
            redrawPoints();
        }
    }
});