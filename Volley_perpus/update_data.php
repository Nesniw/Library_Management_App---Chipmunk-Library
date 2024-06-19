<?php
    include 'koneksi.php';
    $kd = $_POST['kd_buku'];
    $jud = $_POST['judul'];
    $gen = $_POST['genre'];
    $pbit = $_POST['penerbit'];
    $parn = $_POST['pengarang'];
    $thn = $_POST['tahun_terbit'];
    $rak = $_POST['no_rak'];
    $isbn = $_POST['isbn'];

    $query = "UPDATE buku SET judul='".$jud."', genre='".$gen."', penerbit='".$pbit."', pengarang='".$parn."', tahun_terbit='".$thn."', no_rak='".$rak."', isbn='".$isbn."' WHERE kd_buku='".$kd."'";
    $result = mysqli_query ($conn, $query) or die('Error query: '.$query);
    if($result == 1){
        $response['message']="Success Update";
    }
    else{
        $response['message']="Failed Update";
    }
    echo json_encode($response);
    mysqli_close($conn);
?>