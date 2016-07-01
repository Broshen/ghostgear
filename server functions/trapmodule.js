<!DOCTYPE html>
<html>
<script>
function callPHP(params) {
    var httpc = new XMLHttpRequest(); // simplified for clarity
    var url = "boshencui.com/getAllTraps.php";
    httpc.open("POST", url, true); // sending as POST

    httpc.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    httpc.setRequestHeader("Content-Length", params.length); // POST request MUST have a Content-Length header (as per HTTP/1.1)

    httpc.onreadystatechange = function() { //Call a function when the state changes.
    if(httpc.readyState == 4 && httpc.status == 200) { // complete and no errors
        alert(httpc.responseText); // some processing here, or whatever you want to do with the response
        }
    }
    httpc.send(params);
}
</script>
<a href="#" onclick="callPHP('lorem=ipsum&foo=bar')">call PHP script</a>
</html>