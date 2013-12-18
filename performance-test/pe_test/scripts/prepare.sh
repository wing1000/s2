#! /bin/sh
echo "starting test initialization..."

JMETER_HOME=../jmeter
chmod +x $JMETER_HOME/bin/*.sh
chmod +x $JMETER_HOME/bin/jmeter
chmod +x $JMETER_HOME/bin/jmeter-server
chmod +x $JMETER_HOME/bin/jmeter-report

echo "generating class path..."
java -cp ../test_sampler/lib/*:../test_sampler/sampler/* fengfei.performance.MainJmeterClasspathGenerator

echo "test initialization finished"
