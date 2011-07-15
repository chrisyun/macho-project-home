/**
 * conver 0 to "00" 
 */
function convert2Digital(d) {
	if (isNaN(d)) {
		return d;
	}
	return (d < 10)?"0" + d:"" + d;
}

function convert2DateStr() {
	var result = "";
    var vars = convert2DateStr.arguments;  
    //Year
    if (vars.length > 1) {
    	result += vars[0];
    }
    
    // Month
    if (vars.length > 2) {
    	result += "-" + convert2Digital(vars[1]);
    }
    
    // Day
    if (vars.length > 3) {
    	result += "-" + convert2Digital(vars[2]);
    }
    
    // Hour
    if (vars.length > 4) {
    	result += " " + convert2Digital(vars[3]);
    }
    
    // Minute
    if (vars.length > 5) {
    	result += ":" + convert2Digital(vars[4]);
    }
    return result;
}