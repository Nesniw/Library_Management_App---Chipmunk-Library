<?php
    include 'koneksi.php';

    $result = mysqli_query($conn, "SELECT * FROM buku");
    if(mysqli_num_rows($result) > 0) {
        $items = array();
        while($row = mysqli_fetch_object($result)){
            array_push($items, $row);
        }

        $response['message'] = "Buku tersedia";
        $response['data'] = $items;
    } else {
        $response['message'] = "No products found";
    }

    echo json_encode($response);
?>