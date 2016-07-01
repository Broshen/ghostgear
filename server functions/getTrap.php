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

if (isset($_POST["user"])){

    $user=$_POST["user"];
    echo $user;

    $result = mysqli_query($conn,"SELECT  `TrapId` ,`Latitute` , `Longitute` , `IsPublic` , `Time` 
                                    FROM  `LobsterTraps` 
                                    WHERE  `UserId` = '$user'");

    if(mysqli_num_rows($result) > 0){

         $trap = array();
         $counter = 0;

        while($row = mysqli_fetch_array($result)){
            $trap[$counter][0] = $row["TrapId"];
            $trap[$counter][1] = $row["Latitute"];
            $trap[$counter][2] = $row["Longitute"];
            $trap[$counter][3] = $row["IsPublic"];
            $trap[$counter][4] = $row["Time"];

            $counter++;

        }
        // success
        $response["success"] = 1;

        echo json_encode($trap);
    }
}
else
    echo "failed";



$conn->close();

?>