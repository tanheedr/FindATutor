<?php

require "connection.php";
include ("login.php");
header('content-type: application/json');

session_start();

//echo $_SESSION['ID'][0];
echo "bbbbbbbbbb";
//$TutorID = $_SESSION['ID'][0];
//echo $TutorID;
echo "aaaaaaaaaaaaaaaaaaaaaaaaaa";

/*$query = "SELECT * FROM tutordescription WHERE TutorID = '$TutorID'";
$res = mysqli_query($connection, $query);
$json_data = array();

while($row = mysqli_fetch_assoc($res)){
    $json_data[] = $row;
}
echo json_encode($json_data);*/

?>