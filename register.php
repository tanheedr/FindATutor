<?php

require "connection.php";

$firstName = $_POST["FirstName"];
$firstName = ucfirst($firstName);
$surname = $_POST["Surname"];
$surname = ucfirst($surname);
$email = $_POST["Email"];
$password = $_POST["Password"];
$confirmPassword = $_POST["ConfirmPassword"];
$accountType = $_POST["AccountType"];
$isValidEmail = filter_var($email, FILTER_VALIDATE_EMAIL);

if ($accountType == "Parent"){
    $accountType = 1;
}
else{
    $accountType = 2;
}

if($connection){
    if(strlen($password) > 64 || strlen($password) < 6){
        echo "Password must be between 6-64 characters";
    }
    else if($password !== $confirmPassword){
        echo "Passwords do not match";
    }
    else if (strlen($firstName) > 64 || strlen($firstName) < 2 || !ctype_alpha($firstName)){
        echo "Enter valid first name";
    }
    else if (strlen($surname) > 64 || strlen($surname) < 2 || !ctype_alpha($surname)){
        echo "Enter valid surname";
    }
    else if($isValidEmail === false){
        echo "Invalid Email";
    }
    else{
        $sqlCheckEmail = "SELECT * FROM accounts WHERE Email LIKE '$email'";
        $emailQuery = mysqli_query($connection, $sqlCheckEmail);
        
        if(mysqli_num_rows($emailQuery) > 0){
            echo "This Email is already registered";
        }
        else{
            $hashedPassword = hash('sha1', $password);
            $sql_register = "INSERT INTO accounts (`AccountType`,`FirstName`,`Surname`,`Email`,`Password`) VALUES ('$accountType','$firstName','$surname','$email','$hashedPassword')";
            if(mysqli_query($connection,$sql_register)){
                echo "Succesfully Registered";
                if($accountType === 2){
                    $res = mysqli_query($connection,"SELECT * FROM accounts WHERE Email LIKE '$email'");
                    $res2 = $res->fetch_array();
                    $sqlGetTutorID = intval($res2['ID']);
                    $sql_register_tutor_data = "INSERT INTO tutordescription (`TutorID`, `Photo`, `Subjects`, `HourlyCost`, `Description`, `Qualifications`, `Verified`) VALUES ('$sqlGetTutorID', NULL, NULL, NULL, NULL, NULL, '0')";
                }
            }
            else{
                echo "Registration Error";
            }

        }
    }
}
else{
    echo "Connection Error";
}

?>