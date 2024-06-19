<?php
    include 'koneksi.php';
    $kd = $_POST['kd_buku'];
    $img = $_POST['image'];
    $jud = $_POST['judul'];
    $gen = $_POST['genre'];
    $pbit = $_POST['penerbit'];
    $parn = $_POST['pengarang'];
    $thn = $_POST['tahun_terbit'];
    $rak = $_POST['no_rak'];
    $isbn = $_POST['isbn'];

    date_default_timezone_set('Asia/Jakarta');
    $path = 'images/'.date(format:"d-m-Y-his").'-'.rand(100,10000).'.jpg';

    $data = mysqli_query($conn,"SELECT * FROM buku WHERE kd_buku='$kd'");
    $d = mysqli_fetch_array($data);
    unlink($d['image']);

    $query = "UPDATE buku SET image='".$path."', judul='".$jud."', genre='".$gen."', penerbit='".$pbit."', pengarang='".$parn."', tahun_terbit='".$thn."', no_rak='".$rak."', isbn='".$isbn."' WHERE kd_buku='".$kd."'";
    $result = mysqli_query ($conn, $query) or die('Error query: '.$query);
    if($result == 1){
        file_put_contents($path, base64_decode($img));
        $response['message']="Success Update";
    }
    else{
        $response['message']="Failed Update";
    }
    echo json_encode($response);
    mysqli_close($conn);
?>