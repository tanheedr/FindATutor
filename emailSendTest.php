<?php

$to = 'findatutor7@gmail.com';
$subject = 'Test Subject';
$message = 'Hi this is a message';
$headers = 'From: findatutor7@gmail.com'."\r\n".
            'MIME-Version: 1.0'."\r\n".
            'Content-type: text/html; charset=utf-8';

if(mail($to, $subject, $message, $headers))
    echo "Email sent";
else
    echo "Error";

?>