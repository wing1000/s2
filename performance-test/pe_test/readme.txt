JMeter changes:
1. remove directory docs,extra,printable_docs,bin/examples. Remove file NOTICE,README.

2. bin/log4j.conf
	2.1 change
		log4j.appender.Root_Appender.File=root.log
		to
		log4j.appender.Root_Appender.File=../../root.log

	2.2 change
		log4j.appender.Root_Appender.Append=true
		to
		log4j.appender.Root_Appender.Append=false

	2.3 append
		log4j.logger.com.livejournal.service.relation=ERROR
		log4j.logger.org.apache=WARN

3. bin/jmeter(*nix), and bin/jmeter.bat(Windows)
	3.1 change
		HEAP="-Xms512m -Xmx512m"
		to
		HEAP="-Xms512m -Xmx1024m"

4. bin/jmeter.properties
	4.1 chanage
		#language=en
		to
		language=en

	4.1 change
		#log_file=jmeter.log
		to
		log_file=../../jmeter.log

	4.2 change
		#mode=Statistical
		to
		mode=Statistical

	4.3 change
		#time_threshold=60000
		to
		time_threshold=3000
	
	4.5 change
		#summariser.name=summary
		to
		summariser.name=summary
		
		#summariser.interval=180
		to
		summariser.interval=180
		
		#summariser.log=true
		to
		summariser.log=true
		
		#summariser.out=true
		to
		summariser.out=true

5. bin/user.properties
	5.1 Append linux version of user.classpath and search_paths.

6. bin/jmeter-server(*nix), bin/jmeter-server.bat(Windows)
	6.1 *nix change
	${DIRNAME}/jmeter ${RMI_HOST_DEF} -Dserver_port=${SERVER_PORT:-1099} -s -j jmeter-server.log "$@"
	to
	${DIRNAME}/jmeter ${RMI_HOST_DEF} -Dserver_port=${SERVER_PORT:-1099} -s -j ../../jmeter-server.log "$@"
	
	6.2 Windows change
	call jmeter -s -j jmeter-server.log %JMETER_CMD_LINE_ARGS%
	to
	call jmeter -s -j ../../jmeter-server.log %JMETER_CMD_LINE_ARGS%


Usage:
1. make sure jdk or jre is installed.
2. run scripts/rs_test.sh(*nix) or scripts/rs_test.bat(Windows) to generate classpath for JMeter, it will modify jmeter/bin/user.properties.
3. launch jmeter/bin/jmeter(*nix) or jmeter/bin/jemeter.bat(Windows).