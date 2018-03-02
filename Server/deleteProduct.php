<?php
require_once 'include/config.php';

$id=$_POST['id'];

$query="UPDATE products
SET staDelete = '1'
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
