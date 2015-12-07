#Alberto
###Date: 

#Santi
###Date:

#Íñigo
###Date: 6/12/2015
-Question: Need to ask how the GreetingControler knows to whom must send 
the message.

#David
###Date: 7/12/2015
Added service which verifies an URI against the Google Safe Browsing API.
With Big Web Service technology, it is used asynchronously and provides a
single-method inteface. Tests needed to be implemented.
-Question: Need to ask why @Value doesn't work. Need to ask if the element
	ShortURL can be described the way it is in "checker.xsd". Above all, need 
	to ask if the services is well implemented.
-Fixed: A problem that occurs when a wrong-formatted URI is going to be checked
		to be spam in "checkInternal" method URLControllerWithLogs.java.
