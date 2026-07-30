  _translations = {
    "OK": "OK",
    "Now": "Acum",
    "Today": "Azi",
	"Clear": "Sterge"
  }

  Date.weekdays = $w("D L Ma Mi J V S");

  Date.months = $w("Ianuarie Februarie Martie Aprilie Mai Iunie Iulie August Septembrie Octombrie Noiembrie Decembrie");


Date.prototype.toFormattedString = function(include_time){
  switch(this.getMonth()){
  	case 0: m = "Ian"; break;
	case 1: m = "Feb"; break;
	case 2: m = "Mar"; break;
	case 3: m = "Apr"; break;
	case 4: m = "Mai"; break;
	case 5: m = "Iun"; break;
	case 6: m = "Iul"; break;
	case 7: m = "Aug"; break;
	case 8: m = "Sep"; break;
	case 9: m = "Oct"; break;
	case 10: m = "Noi"; break;
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
  	case "Ian": m = 0; break;
	case "Feb": m = 1; break;
	case "Mar": m = 2; break;
	case "Apr": m = 3; break;
	case "Mai": m = 4; break;
	case "Iun": m = 5; break;
	case "Iul": m = 6; break;
	case "Aug": m = 7; break;
	case "Sep": m = 8; break;
	case "Oct": m = 9; break;
	case "Noi": m = 10; break;
	case "Dec": m = 11; break;
  }  
  date.setDate(d[1]);
  date.setMonth(m);
  date.setFullYear(d[3])
  return date;
}