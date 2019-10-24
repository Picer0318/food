<?php

$con = new mysqli("localhost", "id9737010_case410807", "case407","id9737010_food");

$report_phone = $_POST['report_phone'];
$reported_phone_food = $_POST['reported_phone_food'];
$report_content = $_POST['report_content'];

$qurey = " INSERT INTO `report`(`report_phone`, `reported_phone_food`, `report_content`) VALUES ('$report_phone','$reported_phone_food','$report_content')";

mysqli_query($con,$qurey)or die(mysqli_error($con));

mysqli_close($con);
?>