<?php

$con = new mysqli("localhost", "id9737010_case410807", "case407","id9737010_food");

$problem_phone = $_POST['problem_phone'];
$problem_type = $_POST['problem_type'];
$problem_content = $_POST['problem_content'];

$qurey = " INSERT INTO `problem`(`problem_phone`, `problem_type`, `problem_content`) VALUES ('$problem_phone','$problem_type','$problem_content')";

mysqli_query($con,$qurey)or die(mysqli_error($con));

mysqli_close($con);
?>