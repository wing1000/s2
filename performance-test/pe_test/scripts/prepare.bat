@echo off
echo starting test initialization...

echo generating class path...
java -cp ..\test_sampler\lib\*;..\test_sampler\sampler\* fengfei.performance.MainJmeterClasspathGenerator

echo test initialization finished