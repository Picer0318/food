<?php

$con = new mysqli("localhost", "id9737010_case410807", "case407","id9737010_food");


$query = "SELECT phone,name,sex FROM userdata";

$result = mysqli_query($con,$query);
$number_of_rows = mysqli_num_rows($result);

$temp_array = array();

if($number_of_rows > 0){
    while($row = mysqli_fetch_assoc($result)){
        $temp_array[] = $row;
    }
}

echo json_encode(array("userData"=>$temp_array),JSON_UNESCAPED_UNICODE);

mysqli_close($con);
?>