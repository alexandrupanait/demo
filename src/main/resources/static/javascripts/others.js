function clear_filter() {
  document.getElementById('product_availability').options[0].selected='true';
  document.getElementById('supplier_id').options[0].selected='true';
  document.getElementById('product_name_filter').clear();
  document.filter.submit()
}