<?php

require_once("json_encode.php");
error_reporting(E_ERROR | E_PARSE);

function validateX($xVal)
{
    return isset($xVal);
}

function validateY($yVal)
{
    if (!isset($yVal))
        return false;

    $numY = str_replace(',', '.', $yVal);
    return is_numeric($numY) && $numY > -3 && $numY < 3;
}

function validateR($rVal)
{
    return isset($rVal);
}

function validateForm($xVal, $yVal, $rVal)
{
    return validateX($xVal) && validateY($yVal) && validateR($rVal);
}

function checkTriangle($xVal, $yVal, $rVal)
{
    return $xVal <= 0 && $yVal >= 0 &&
        $yVal <= $xVal / 2 + $rVal / 2;
}

function checkRectangle($xVal, $yVal, $rVal)
{
    return $xVal >= 0 && $yVal >= 0 &&
        $xVal <= $rVal && $yVal <= $rVal;
}

function checkCircle($xVal, $yVal, $rVal)
{
    return $xVal >= 0 && $yVal <= 0 &&
        pow($xVal, 2) + pow($yVal, 2) <= pow($rVal / 2, 2);
}

function checkHit($xVal, $yVal, $rVal)
{
    return checkTriangle($xVal, $yVal, $rVal) || checkRectangle($xVal, $yVal, $rVal) ||
        checkCircle($xVal, $yVal, $rVal);
}

function getResultArray($xVal, $yVal, $rVal, $timezone) {
    $results = array();

    foreach ($xVal as $value) {
        $isValid = validateForm($value, $yVal, $rVal);
        $isHit = checkHit($value, $yVal, $rVal);
        $currentTime = date('H:i:s', time() - $timezone * 60);
        $executionTime = round(microtime(true) - $_SERVER['REQUEST_TIME_FLOAT'], 7);

        array_push($results, array(
            "validate" => $isValid,
            "x" => $value,
            "y" => $yVal,
            "r" => $rVal,
            "currentTime" => $currentTime,
            "execTime" => $executionTime,
            "isHit" => $isHit
        ));
    }

    return $results;
}

function generateTableWithRows($wholeTable, $results) {
    $html = $wholeTable == 'true'? '<table id="result-table"><tr class="table-header">
        <th class="coords-col">X</th>
        <th class="coords-col">Y</th>
        <th class="coords-col">R</th>
        <th class="time-col">Время запроса</th>
        <th class="time-col">Время исполнения</th>
        <th class="hitres-col">Попадание</th>
    </tr>': '';

    foreach ($results as $elem)
        $html .= generateRow($elem);

    if ($wholeTable == 'true') $html .= '</table>';
    return $html;
}

function generateRow($elem) {
    $isHit = $elem['isHit'] ? 'Да': 'Нет';
    $elemHtml = $elem["isHit"]? '<tr class="hit-yes">' : '<tr class="hit-no">';
    $elemHtml .= '<td>' . $elem['x'] . '</td>';
    $elemHtml .= '<td>' . $elem['y'] . '</td>';
    $elemHtml .= '<td>' . $elem['r'] . '</td>';
    $elemHtml .= '<td>' . $elem['currentTime'] . '</td>';
    $elemHtml .= '<td>' . $elem['execTime'] . '</td>';
    $elemHtml .= '<td>' . $isHit . '</td>';
    $elemHtml .= '</tr>';

    return $elemHtml;
}


$xVal = explode(",", $_GET['x']);
$yVal = $_GET['y'];
$rVal = $_GET['r'];
$dataType = $_GET['dataType'];
$wholeTable = $_GET['wholeTable'];

if (!isset($wholeTable)) $wholeTable = true;

$timezone = $_GET['timezone'];

$results = getResultArray($xVal, $yVal, $rVal, $timezone);

if ($dataType == 'json')
    echo toJSON($results);
else if($dataType == 'html')
    echo generateTableWithRows($wholeTable, $results);
