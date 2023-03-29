var net = require('net');

var server = net.createServer(function(socket) {
	socket.on('data', function(data) {
		dataReceived(socket, data);
    }); 
});

server.listen(7777);

function dataReceived(socket, data) {
	data = data.toString('utf8');
	console.log("Message: "+data);
	socket.write(data);
	}
