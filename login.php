<?php

require "connection.php";

session_start();

if(isset($_POST["Email"])){$email = $_POST["Email"];}
if(isset($_POST["Password"])){$password = $_POST["Password"];}
$hashedPassword = hash('sha1', $password);
$isValidEmail = filter_var($email, FILTER_VALIDATE_EMAIL);

if($connection){
    if ($isValidEmail === false){
        echo "Invalid Email";
    }
    else{
        $sqlCheckEmail = "SELECT * FROM accounts WHERE Email LIKE '$email'";
        $emailQuery = mysqli_query($connection, $sqlCheckEmail);
        if (mysqli_num_rows($emailQuery) > 0){
            $sqlLogin = "SELECT * FROM accounts WHERE Email LIKE '$email' AND Password LIKE '$hashedPassword'";
            $loginQuery = mysqli_query($connection, $sqlLogin);
            if (mysqli_num_rows($loginQuery) > 0){
                $sqlGetID = "SELECT ID FROM accounts WHERE Email LIKE '$email'";
                $res = mysqli_query($connection, $sqlGetID);
                $_SESSION['ID'] = mysqli_fetch_row($res);
                /*$json_data = array();

                while($row = mysqli_fetch_assoc($res)){
                    $json_data[] = $row;
                }*/
                echo "Login Successful";
            }
            else{
                echo "Wrong password";
            }
        }
        else{
            echo "This email is not registered";
        }
    }
}
else{
    echo "Connection Error";
}

?>