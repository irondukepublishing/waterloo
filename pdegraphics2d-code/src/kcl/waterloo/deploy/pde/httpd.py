# Run this Python script from the command line to:
# [1] Get an available port number from your OS
# [2] Create a server using that socket (on 127.0.0.1)
# [3] Deliver "index.html" (in the same folder as the script) to the system browser via the server
#
# Requires Python 2.7.
#
# Use this for browsers that impose security restriction for
# XMLHTTPRequests for local file access and will not load the pde file
# or images.
# M.L. 06.05.2013


import socket
s = socket.socket()
s.bind(('', 0))
PORT = s.getsockname()[1]
SERVER = '127.0.0.1'
import SimpleHTTPServer
import BaseHTTPServer
import SocketServer
Handler = SimpleHTTPServer.SimpleHTTPRequestHandler
class Server(SocketServer.ThreadingMixIn, BaseHTTPServer.HTTPServer):
    pass
httpd = Server((SERVER, PORT), Handler)
print "Web Server listening on http://%s:%s/." % (SERVER, PORT)
print "Close this window or type ctrl+c to exit"

# Open index.html in the default browser
import webbrowser
url = 'http:127.0.0.1:{0}'.format(PORT)
webbrowser.open_new_tab(url)

# Keep the server running
try:
    httpd.serve_forever()
except KeyboardInterrupt:
    print "Disconnecting ..."