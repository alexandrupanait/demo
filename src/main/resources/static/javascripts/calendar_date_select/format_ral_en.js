Date.prototype.toFormattedString = function(include_time){
  switch(this.getMonth()){
  	case 0: m = "Jan"; break;
	case 1: m = "Feb"; break;
	case 2: m = "Mar"; break;
	case 3: m = "Apr"; break;
	case 4: m = "May"; break;
	case 5: m = "Jun"; break;
	case 6: m = "Jul"; break;
	case 7: m = "Aug"; break;
	case 8: m = "Sep"; break;
	case 9: m = "Oct"; break;
	case 10: m = "Nov"; break;
	case 11: m = "Dec"; break;
  }
  str = Date.padded2(this.getDate()) + " " + m + " " + this.getFullYear(); 
  return str;
}



Date.parseFormattedString = function (string) {
  var regexp = "([0-9]{2}) ([a-z]{3}) ([0-9]{4})";
  var d = string.match(new RegExp(regexp, "i"));
  if (d==null) return Date.parse(string); // at least give javascript a crack at it.
  var offset = 0;
  var date = new Date;
  switch(d[2]){
  	case "Jan": m = 0; break;
	case "Feb": m = 1; break;
	case "Mar": m = 2; break;
	case "Apr": m = 3; break;
	case "MaY": m = 4; break;
	case "Jun": m = 5; break;
	case "Jul": m = 6; break;
	case "Aug": m = 7; break;
	case "Sep": m = 8; break;
	case "Oct": m = 9; break;
	case "Nov": m = 10; break;
	case "Dec": m = 11; break;
  }  
  date.setDate(d[1]);
  date.setMonth(m);
  date.setFullYear(d[3])
  return date;
}