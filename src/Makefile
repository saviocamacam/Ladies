all:
	rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false

server:
	java -Djava.security.policy=policy.txt -Djava.rmi.server.codebase=file:policy.txt server.MainServer
	
client:
	java -Djava.security.policy=policy.txt client.MainClient	