import socket

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind(('localhost', 7777))
s.listen(1)

conn, addr = s.accept()
while 1:
    data = conn.recv(20) # 20 is buffer size, typically larger
    if not data: break
    print "Echoing:", data
    conn.send(data)  # echo
conn.close()
