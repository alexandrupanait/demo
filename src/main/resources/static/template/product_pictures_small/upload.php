<?
$target_path =  basename( $_FILES["file"]["name"]);
if(move_uploaded_file($_FILES["file"]["tmp_name"], $target_path)) {
echo "OK";
} else{
echo "ERROR";
}


?>