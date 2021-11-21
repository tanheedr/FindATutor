<?php

/*if($_SERVER['REQUEST_METHOD']=='GET'){
$Tutorid = $_GET['id'];*/

require "connection.php";
header('content-type: application/json');

/*$TutorID = $_POST["TutorID"];*/
$TutorID = 1;

$query = "SELECT * FROM tutordescription WHERE TutorID = '$TutorID'";
$res = mysqli_query($connection, $query);
$json_data = array();

while($row = mysqli_fetch_assoc($res)){
    $json_data[] = $row;
}
echo json_encode($json_data);
/*}*/

?>