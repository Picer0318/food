<?php

$con = new mysqli("localhost", "id9737010_case410807", "case407","id9737010_food");

$id = $_POST['id'];


$qurey = " UPDATE food1 SET boolean = '0' WHERE food1.id = $id";
$qurey1 = " UPDATE food1 SET bln_complete = '1' WHERE food1.id = $id";

mysqli_query($con,$qurey)or die(mysqli_error($con));
mysqli_query($con,$qurey1)or die(mysqli_error($con));


mysqli_close($con);
?>