<?php

class Connect{
    private $connection;

    function __construct(){

    }

    function connect(){
        include_once dirname(__FILE__).'/constants.php';
        $this -> connection = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);

        if(mysqli_connect_errno()){
            echo "Connection error".mysqli_connect_err();
        }
        return $this -> connection;
    }
}

/*$db_name = "tanheeddb";
$username = "root";
$password = "";
$host = "127.0.0.1";

$connection = mysqli_connect($host,$username,$password,$db_name);*/

?>