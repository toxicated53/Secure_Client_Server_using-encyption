# Client-Server Messaging Demo

## Overview
This Java Project demonstrates ** Secure Client-Server Connection ** with a ** MITM Spy **. The goal is to show how encrypted messages can be exchanged between Client and Server even if a Spy (Third Person) is keeping eye on the messages.
**This is a Demo Project and for Educational purpose only ** 
---
## Workflow 
### 1. Server
- Listens for Client connection.
- Receives AES-Encrypted messages from Client.
- Decryptes the incoming messages using a ** secret symmetric key **.
- Responds to the Client with Acknowledgement.

### 2. Client
- Connects to the Server either directly or through the Spy.
- Sends AES-Encrypted messages to Server.
- Decryptes them using the same ** secret symmetric key **.
- Continues to Process further.

### 3. Spy
- Listens to both the ports (i.e Client and Server).
- Every Communication occurs through Spy.
- Can monitor the activity going through Client and Server.

**NOTE:- EVEN IF THE CONNECTION HAPPENS THROUGH SPY, THE MESSAGES ARE AES-ENCRYPTED AND SPY WOULD NOT BE ABLE TO DECRYPT AND UNDERSTAND THE COMMUNICATION BETWEEN CLIENT AND SERVER.**
---
## Learning Objectives 
- Understand **AES-Encryption** for client-server messages.
- Learned about **MITM,Spy* and how could they intercept traffic.
- Hands-on Experience with **JAVA-SOCKET Programming**.
- Explore basic ** network security concepts ** under controlled environment.
---
## Compile and Run
- Make sure JDK is installed and updated to Latest Version.
- On Terminal
- javac DuplexEncryptedServer.java DuplexEncryptedClient.java Spy.java
- java DuplexEncryptedServer
- java Spy
- java DuplexEncryptedClient

