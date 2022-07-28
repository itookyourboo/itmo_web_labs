let graph, graphElem;

$(document).ready(function () {
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
    let rect = {x: W2, y: H2 - 2 * R, w: 2 * R, h: 2 * R};
    // Треугольник
    let triangle = `${W2},${H2} ${W2 - 2 * R},${H2} ${W2},${H2 + R}`;
    // Четверть круга
    let circle = `M${W2},${H2} ${W2 + 2 * R},${H2} A${2 * R} ${2 * R} 0 0 1 ${W2} ${H2 + 2 * R}Z`;

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

    graphElem.addEventListener("click", (event) => {
        clearErrors();
        let r = validateR();
        if (r != null) {
            let pos = getMousePosition(event);
            console.log(pos.x + " " + pos.y);
            setPointer(pos.x, pos.y);

            toAreaCoords(pos, r);
            console.log(pos.x + " " + pos.y);
            sendCheckRequest({
                x: normalize(pos.x),
                y: normalize(pos.y),
                r: r
            }, "svg");
        }
    });
});

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

function setPointer(x, y) {
    let circle = graphElem.querySelector('circle');
    if (!circle) {
        graphElem.insertAdjacentHTML("beforeend", `<circle r="5" cx="${x}" cy="${y}" fill-opacity="0.7" fill="red" stroke="firebrick"></circle>`);
    } else {
        circle.setAttribute('cx', x);
        circle.setAttribute('cy', y);
    }
}

function removePointer() {
    let circle = graphElem.querySelector('circle');
    if (circle) graphElem.removeChild(circle);
}
