<?php

$con = new mysqli("localhost", "id9737010_case410807", "case407","id9737010_food");

$id = $_POST['id'];


$qurey = " UPDATE food1 SET bln_collect = '1' WHERE food1.id = $id";


mysqli_query($con,$qurey)or die(mysqli_error($con));

mysqli_close($con);
?>