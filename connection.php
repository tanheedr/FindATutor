<?php

/*$db_name = "tanheeddb";
$username = "tanheed";
$password = "u7ntaQs8";
$host = "localhost";*/

$db_name = "tanheeddb";
$username = "root";
$password = "";
$host = "127.0.0.1";

$connection = mysqli_connect($host,$username,$password,$db_name);
/*$sql = "insert into user_info values('".$username."','".$password."')   ;";*/

/*$connection = mysqli_connect('localhost','tanheed','u7ntaQs8','tanheeddb');*/

/*$connection = new PDO("mysql:host=$host;port=3306;db_name=$db_name", $username, $password);
$connection::setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);*/

/*try{
    $connection = new PDO("mysql:host=$host;db_name=$db_name", $username, $password);
    $connection -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    echo "Connected Succesfully";
}
catch (PDOException $e){
    echo "Connection failed: ". $e -> getMessage();
}*/

/*if (!$connection){
    var_dump("CONN Failed");
    echo "<script>alert('Connection Failed')</script>";
}
else{
    echo "Connection Success";
}*/

/*$db_name = "db_name";
$username = "username";
$password = "password";
$host = "either ip address or localhost";

$connection = mysqli_connect($host,$username,$password,$db_name);*/

?>