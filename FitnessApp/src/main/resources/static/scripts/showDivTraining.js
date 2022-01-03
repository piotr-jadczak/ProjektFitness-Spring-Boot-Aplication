function showDiv(i) {
    if(i == 'ONETIME') {
        document.getElementById(i).style.display = 'block';
        document.getElementById('REGULAR').style.display = 'none';
    }
    else {
        document.getElementById(i).style.display = 'block';
        document.getElementById('ONETIME').style.display = 'none';
    }
}