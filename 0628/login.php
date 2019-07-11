<?php


$phone = filter_input(INPUT_POST,"phone");
$password = filter_input(INPUT_POST,"password");


$mysqli = new mysqli("localhost", "id9737010_case410807", "case407","id9737010_food");

$result = mysqli_query($mysqli,"select * from user where phone='".$phone."' and password = '".$password."'");


if ($data = mysqli_fetch_array($result)){
    echo '1';
}

mysqli_close($mysqli);


?>