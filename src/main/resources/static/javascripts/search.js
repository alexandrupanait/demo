
function FocusBlankSearchKey() {
	if(document.getElementById('search_key').value=='produse, solutii etc.' || document.getElementById('search_key').value=='products, solutions etc.')
		document.getElementById('search_key').value=''
}
function BlurAddSearchKey() {
	if(document.SearchForm.searchkey.value=='')document.SearchForm.searchkey.value='cauta';
}
function CheckSearch(w) {

	if (document.SearchForm.searchkey.value.length <= 2) { 
		alert('Insert Key!');
		document.SearchForm.searchkey.focus(); 
		return false;
	}
	return true;
}
