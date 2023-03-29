<?
$host = "localhost";
$port = 7777;
// create socket
$socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP) 
     or die("Could not create socket\n");
// bind socket to port
$result = socket_bind($socket, $host, $port) 
     or die("Could not bind to socket\n");
// start listening for connections
$result = socket_listen($socket, 3) 
   or die("Could not set up socket listener\n");
// accept incoming connections
// spawn another socket to handle communication
$spawn = socket_accept($socket) 
    or die("Could not accept incoming connection\n");
while (true):
	// read client input
    $input = socket_read($spawn, 1024) 
        or die("Could not read input\n");
	$output = $input;
	echo "Echoing $output\n";
    socket_write($spawn, $output, strlen ($output)) 
        or die("Could not write output\n");
endwhile;
// close sockets
socket_close($spawn);
socket_close($socket);
?>
