<?php


// connect to the server
$conn = new mysqli("localhost","boshencu_tester","mysqlpassword","boshencu_test1");
//$mysqlpassword= "fuckyousql1!"

// Check connection
if ($conn->connect_error) {

    die("Connection failed: " . $conn->connect_error);

} 

//after connection, retrieve data
// array for JSON response
$response = array();

$result = mysqli_query($conn,"SELECT  `Latitute` ,  `Longitute` ,  `Time` 
FROM  `LobsterTraps` 
WHERE  `IsPublic` =1");

if(mysqli_num_rows($result) > 0){

     $trap = array();
     $counter = 0;

    while($row = mysqli_fetch_array($result)){
        $trap[$counter][0] = $row["Latitute"];
        $trap[$counter][1] = $row["Longitute"];
        $trap[$counter][2] = $row["Time"];

        $counter++;

    }
    // success
    $response["success"] = 1;

    echo json_encode($trap);
}



// $response["array"]=$result;

// // echoing JSON response
// echo json_encode($response);

$conn->close();

?>