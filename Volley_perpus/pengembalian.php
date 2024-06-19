<?php
    include 'koneksi.php';
    $kdpeng = $_POST['kd_peminjaman'];
    $statpeng = $_POST['statusp'];
    $statbuku = $_POST['statusb'];
    $kdbuku = $_POST['kd_buku'];

    date_default_timezone_set('Asia/Jakarta');
    $tglkem = date('y-m-d h:i:s');
    
    $query = "UPDATE peminjaman SET status='".$statpeng."', tgl_kembali='".$tglkem."' WHERE kd_peminjaman='".$kdpeng."'";
    $query2 = "UPDATE buku SET status='".$statbuku."' WHERE kd_buku='".$kdbuku."'";
    $result = mysqli_query ($conn, $query) or die('Error query: '.$query);
    $result2 = mysqli_query ($conn, $query2) or die('Error query: '.$query2);
    if($result == 1 && $result2 == 1){
        $response['message']="Pengembalian sukses";
    }
    else{
        $response['message']="Pengembalian gagal";
    }
    echo json_encode($response);
    mysqli_close($conn);
?>