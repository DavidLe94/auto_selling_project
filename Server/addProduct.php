<?php
require_once 'include/config.php';

//if name product is not null or is exits
if(isset($_POST['nameProduct'])&& $_POST['nameProduct']!=''){
//get value post client
  $nameProduct = $_POST['nameProduct'];
  $model = $_POST['model'];
  $brand = $_POST['brand'];
  $price = $_POST['price'];
  $image = $_POST['image'];
  $description = $_POST['description'];
  $staDelete = $_POST['staDelete'];

  $query = "INSERT INTO products(id, nameProduct, model, brand, price, image, description, staDelete)
  VALUES(null, '$nameProduct', '$model', '$brand', '$price', '$image', '$description', '$staDelete')";
	$result = $conn->query($query);

  if($result){
    $json["success"]=1;
    $json["message"]="add new 1 product";
  }else{
    $json["success"]=0;
    $json["message"]="can't add new product";
  }
//name product is null or exits
}else{
  $json["success"]=0;
  $json["message"]="enter name product";
}

echo json_encode($json);
?>
