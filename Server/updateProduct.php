<?php
require_once 'include/config.php';

//get value post client
$id=$_POST['id'];
$name=$_POST['nameProduct'];
$model=$_POST['model'];
$brand=$_POST['brand'];
$price=$_POST['price'];
$image=$_POST['image'];
$description=$_POST['description'];

$query="UPDATE products
SET nameProduct = '$name', model = '$model', brand = '$brand', price = '$price', image = '$image', description = '$description', staDelete = '0'
WHERE id='$id'";
$result = $conn->query($query);
if($result){
  $json["success"]=1;
  $json["message"]="update 1 product";
}else{
  $json["success"]=0;
  $json["message"]="update failed";
}

echo json_encode($json);
?>
