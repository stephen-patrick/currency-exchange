## Overview
A sample application, demonstrating how to integrate Rabbit MQ and AKKA in a play framework application. This sample lacks many functional and non functional aspects expected in a production application.  This sample includes two endpoints a rabbitmq endpoint and an akka endpoint. </p>  <p>A simple web interface can be found at: http://blooming-escarpment-1547.herokuapp.com/fx-akka
				 		 
## Setup / Configuration

Basic requirement is play framework binaries. The akka endpoint can be executed once installed from /fx-akka.   Messages can be sent via a http post request to /fx-akka. The interaction is not full duplex.
				 		
To test the Rabbitmq endpoint, Rabbitmq server must be installed See -> https://www.rabbitmq.com/download.html 
