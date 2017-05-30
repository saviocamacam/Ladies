all:
	rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false

initserver:
	java -Djava.security.policy=policy.txt -Djava.rmi.server.codebase=file:policy.txt server.MainServer
	
initclient:
	java -Djava.security.policy=policy.txt client.MainClient	