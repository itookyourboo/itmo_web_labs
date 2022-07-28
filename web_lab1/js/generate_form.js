$(document).ready(function () {
    const checkboxes = ['-5', '-4', '-3', '-2', '-1', '0', '1', '2', '3'];
    for (let i = 0; i < checkboxes.length; i++) {
        let container = document.createElement('div')
        container.className = 'checkbox-centered'
        container.innerHTML += `<input class="x-checkbox" id="x-checkbox${i}" type="checkbox" name="x_val" value="${checkboxes[i]}">`
        container.innerHTML += `<label for="x-checkbox${i}">${checkboxes[i]}</label>`
        $('#x-checkboxes').append(container)
    }

    const buttons = ['1', '1.5', '2', '2.5', '3'];
    for (let j = 0; j < buttons.length; j++) {
        $('#r-buttons')
            .append(`<input class="r-button" id="r-button${j}" type="button" onclick="chooseR(this)" value="${buttons[j]}">`);
    }
});