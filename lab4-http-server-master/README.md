# Lab 4 - HTTP Server Lab
# Using IntelliJ

This lab builds on the networking and HTTP lectures and videos and gives you experience working with a transport layer protocol (TCP) and implementing basic elements of an application layer protocol (HTTP).

You should first view the [13 minute video](http://heinzcollege.mediasite.com/Mediasite/Play/21d1939d54f04444b042e45fc923742e1d)
 that explains TCP and UDP sockets and introduces the task for this lab.

:warning: **If you have reached this point, and you have not watched [the video](http://heinzcollege.mediasite.com/Mediasite/Play/21d1939d54f04444b042e45fc923742e1d), go back.**

This repository's Example-Socket-Code directory has example client and server socket implementations in several languages. You can clone or download this whole repository, or just download the raw version of EchoServerTCP.java.

For this lab you are to build your own web server. Just like Apache, IIS, or nginx, you are to build a web server that can take HTTP requests from a browser and return static HTML files. (Of course, your server will be much simpler than Apache and the rest.)

You will start with the EchoServerTCP.java class, and change it to be able to handle simple HTTP GET requests for static html files.

You will develop your HTTP Server in IntelliJ IDEA so that you can continue to get comfortable using the IDE. Note that you are not developing a Web Application; rather you are developing a simple Java desktop application. Explore how do create a simple Java desktop application in IntelliJ, and help others in your Lab to figure it out also. (You are encouraged to help each other for Labs, but doing so is taboo for Projects.)

Your Java application should be based on EchoServerTCP.java (i.e. begin by making a copy of that code), but should have a class name that makes sense for its role as a simple web server.

You will also have to figure out how to create a Run Configuration for a Java desktop app. You run it as you would any Java application. It does not use TomEE.

You will make requests to your simple HTTP server using a browser. You do not need to develop an HTTP client.

**So to review so far:**
* The web browser (e.g. Chrome) will be your client.
* You are to create a web server, using EchoServerTCP.java as a starting point

EchoServerTCP.java is not currently a web server. It is a silly application
that just echoes back to the sender whatever it received.

**Like a web server, EchoServerTCP.java can:**
* Open a TCP socket from a client
* Read data arriving on the socket
* Write data to the socket

But unlike a web server, when EchoServerTCP.java reads from the socket, it
writes the same data back to the socket. This is not the HTTP protocol, and the browser will be confused when it receives _back_ an HTTP request instead of receiving an expected HTTP response.

**What a web server does but EchoServerTCP.java does NOT currently do is:**
* Parse the data being read and interpret it as an HTTP request
* Write a valid HTTP response header to the socket
* Read the file the HTTP request asked for from local disk
* Write the file data the HTTP request asked for to the socket

Your task in this lab is to understand EchoServerTCP.java, and then use it to
help you develop a web server.

**_Code is poetry_**. Read and work to understand each line of EchoServerTCP.java as if you were learning and understanding a poem. Discuss each line with others in your lab so that together you fully understand the code. If you first understand the EchoServerTCP.java poem, it will be much easier to build the web server.

Your HTTP server should be able to handle multiple requests, one-at-a-time. You do **not** have to handle multiple simultaneous requests, so your solution can be single-threaded. (I.e. you do not have to use threads.)

**In general terms, your simple HTTP server should:**

* Create a socket
* Loop forever...
  * accept a socket connection
  * read the first line of the HTTP request
  * parse out the file path requested<br>
  * :checkered_flag: **(CHECKPOINT: print the file path to the console.)**
  * try to open the file requested on your local disk
  * if successful
    * reply with OK status response header
    * reply with blank line to indicate end of headers
    * while more lines of the file remain...
      * read a line of the file
      * write to the socket
  * else
     * report file not found using the correct HTTP status code
  * flush the socket
  * close the socket

**HINTS:**

**HTTP Request:**
  * You should review the Server Side Programming slides to review the format of simple HTTP requests.
  * E.g. In Chrome, here is an HTTP request header of http://localhost:3000/test.html
```
GET /test.html HTTP/1.1
Host: localhost:3000
Connection: keep-alive
Cache-Control: max-age=0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2)  ...
Accept-Encoding: gzip,deflate,sdch
Accept-Language: en-US,en;q=0.8
Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3
```

  * A blank line delimits the end of the HTTP request
  * So your HTTP Server program must read the first line from the TCP socket, parse out the *resource identifier* (e.g. /test.html) and then try to read that file (e.g. test.html) from your local file system (see TEST HTML FILES below).

**HTTP Response:**

* The minimal response header is simply ```HTTP/1.1 200 OK\n\n```.  The backslash-n indicates a new line. The extra new line means end of headers. A typical server will send additional headers, but you don't need to.
* After the response header and new line, you would follow with the content of the requested file.
* You only need to handle GET requests (not POST, etc.)
* Put a test html file in your IntelliJ project directory to make it easy to find.
* You are probably familiar with the Scanner class which can be helpful with this task. Otherwise you may prefer to use the FileReader class to open the file, and pass the FileReader to a BufferedReader to make the file easy to read a line at a time.
* Repeatedly read a line from the file and write it to the socket, until the whole file has been read and sent.
* Return the appropriate HTTP Status Code (404) if the file is not found.
* You will visit your simple HTTP server using a browser.<br>
(You do **not** develop an HTTP client.)
* A typical HTTP response header will have the content-length, or "transfer-encoding chunked". We are cheating and just closing the socket. This will indicate to the browser that the response has been completed.

**TEST HTML FILES:**
* You must create your own test HTML file. It can be as simple or complex as you like. Use an existing html file if you have one.

**Extra challenge (not required):**
* Return ```405 Method Not Allowed``` if you receive an HTTP method you don't handle

:checkered_flag: **LAB COMPLETION: Demonstrate your solution to a TA for credit.**
* Show the project, code, and execution in IntelliJ
* You should demonstrate 200 OK and 404 File Not Found
* If you don't complete the lab by the end of class, you must show a TA during their office hours before 1:25pm EST on Monday.

Be sure to complete **and understand** the lab, because there will be questions regarding it on the midterm and/or final.
