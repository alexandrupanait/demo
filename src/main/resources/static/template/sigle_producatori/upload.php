<?
$target_path =  basename( $_FILES["upload"]["name"]);
if(move_uploaded_file($_FILES["upload"]["tmp_name"], $target_path)) {
echo "OK";
} else{
echo "ERROR";
}


?>