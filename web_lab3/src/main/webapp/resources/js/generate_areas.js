let graph, graphElem;

const W = 300, H = 300;
const LINE_LENGTH = 2;
const W2 = W / 2, H2 = H / 2;
const R = 60;
const TEXT_OFFSET = 6;
const TEXT_X = W2 + TEXT_OFFSET, TEXT_Y = H2 - TEXT_OFFSET;
const OPACITY = 0.5, FILL = 'blue', COLOR = '#001238', STROKE = 'navy';

// Оси и отметки
let lines = [[0, H2, W, H2], [W2, 0, W2, H]];
for (let n = 1; n <= 2; n++) {
    for (let i = -1; i <= 1; i += 2) {
        let dx = W2 + i * n * R, dy = W2 + i * n * R
        lines.push([dx, H2 - LINE_LENGTH, dx, H2 + LINE_LENGTH])
        lines.push([W2 - LINE_LENGTH, dy, W2 + LINE_LENGTH, dy])
    }
}

// X, Y, R
let texts = [
    [265, TEXT_Y, 'R'], [200, TEXT_Y, 'R/2'], [75, TEXT_Y, '-R/2'], [20, TEXT_Y, '-R'], [290, TEXT_Y - 2, 'x'],
    [TEXT_X, 35, 'R'], [TEXT_X, 95, 'R/2'], [TEXT_X, 215, '-R/2'], [TEXT_X, 275, '-R'], [TEXT_X + 2, 10, 'y']
];

// Стрелки на координатных осях
let arrows = [
    [`${W},${H2} ${W - TEXT_OFFSET},${H2 + TEXT_OFFSET} ${W - TEXT_OFFSET},${H2 - TEXT_OFFSET}`],
    [`${W2},${0} ${W2 - TEXT_OFFSET},${TEXT_OFFSET} ${W2 + TEXT_OFFSET},${TEXT_OFFSET}`]
]

// Прямоугольник
let rect = {x: W2 - R, y: H2, w: R, h: 2 * R};
// Треугольник
let triangle = `${W2},${H2} ${W2},${H2 - R} ${W2 + R},${H2}`;
// Четверть круга
let circle = `M${W2},${H2} ${W2 + R},${H2} A${R} ${R} 0 0 1 ${W2} ${H2 + R}Z`;

$(document).ready(function () {
    graph = d3.select('svg');
    graphElem = document.querySelector('svg');

    graph.append('rect')
        .attr('x', rect.x)
        .attr('y', rect.y)
        .attr('width', rect.w)
        .attr('height', rect.h)
        .attr('fill-opacity', OPACITY)
        .attr('stroke', STROKE)
        .attr('fill', FILL);

    graph.append('polygon')
        .attr('points', triangle)
        .attr('fill-opacity', OPACITY)
        .attr('stroke', STROKE)
        .attr('fill', FILL);

    graph.append('path')
        .attr('d', circle)
        .attr('fill-opacity', OPACITY)
        .attr('fill', FILL)
        .attr('stroke', STROKE);

    lines.forEach(function (line) {
        graph.append('line')
            .attr('x1', line[0])
            .attr('y1', line[1])
            .attr('x2', line[2])
            .attr('y2', line[3])
            .attr('stroke', COLOR);
    });

    texts.forEach(function (text) {
        graph.append('text')
            .attr('x', text[0])
            .attr('y', text[1])
            .text(text[2]);
    });

    arrows.forEach(function (arrow) {
        graph.append('polygon')
            .attr('points', arrow)
            .attr('fill', COLOR);
    });

    redrawPoints();
    redrawGraph()

    graphElem.addEventListener("click", (event) => {
        const r = document.getElementById('form:r_hidden').value;
        if (r != null) {
            let pos = getMousePosition(event);
            let areaPos = {...pos};
            toAreaCoords(areaPos, r);

            document.getElementById('form:x_input').value = normalize(areaPos.x);
            document.getElementById('form:y').value = normalize(areaPos.y);

            let btn = document.getElementById('form:submitButton');
            btn.click();
        }
    });
});


function redrawGraph() {
    let rVal = document.getElementById('form:r_hidden').value;
    if (rVal) {
        let _R = (rVal).toString();
        let _R2 = (rVal / 2).toString();
        let _texts = [
            [265, TEXT_Y, _R], [200, TEXT_Y, _R2], [75, TEXT_Y, '-' + _R2], [20, TEXT_Y, '-' + _R], [290, TEXT_Y - 2, 'x'],
            [TEXT_X, 35, _R], [TEXT_X, 95, _R2], [TEXT_X, 215, '-' + _R2], [TEXT_X, 275, '-' + _R], [TEXT_X + 2, 10, 'y']
        ];

        document.querySelectorAll('text').forEach((node, i) => {
            node.innerHTML = _texts[i][2];
        })
    }
}

function getMousePosition(event) {
    const rect = graphElem.getBoundingClientRect();
    return {
        x: event.clientX - rect.left,
        y: event.clientY - rect.top
    };
}

function toAreaCoords(pos, r) {
    pos.x = pos.x - 150;
    pos.y = 150 - pos.y;

    const k = r / 120;
    pos.x *= k;
    pos.y *= k;
}

function fromAreaCoords(pos, r) {
    const k = r / 120;
    pos.x /= k
    pos.y /= k;

    pos.y = 150 - pos.y;
    pos.x = pos.x + 150;
}

function normalize(num) {
    return Math.round(num * 10) / 10;
}

function addPointer(x, y, r, isHit) {
    let pos = {x: x, y: y};
    fromAreaCoords(pos, r);
    graphElem.insertAdjacentHTML('beforeend', getCircleSvg(pos.x, pos.y, isHit));
}

function getCircleSvg(x, y, isHit) {
    return `<circle r="5" cx="${x}" cy="${y}" fill-opacity="0.7" fill="${isHit ? 'green' : 'red'}"></circle>`
}

function redrawPoints() {
    clearPoints();
    // console.log("REDRAW");
    const xTableValues = document.getElementsByClassName("table-x-value");
    const yTableValues = document.getElementsByClassName("table-y-value");
    const rTableValues = document.getElementsByClassName("table-r-value");
    const hitTableValues = document.getElementsByClassName("table-hit-value");
    const rHidden = document.getElementById('form:r_hidden');

    for (let i = 0; i < xTableValues.length; i++) {
        let x = parseFloat(xTableValues[i].textContent),
            y = parseFloat(yTableValues[i].textContent),
            r = parseFloat(rTableValues[i].textContent),
            hit = hitTableValues[i].textContent === 'Да';
        if (r === parseFloat(rHidden.value)) {
            addPointer(x, y, r, hit);
        }
    }
}

function clearPoints() {
    [...document.querySelectorAll('circle')].map(n => n && n.remove());
}
