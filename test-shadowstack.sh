#!/bin/bash
od -An -tx1 -w1 -v ./shadowstack-tests/success.hex > out
sbt "testOnly main.ShadowStackTest" &> /dev/null; output=$?
if [ $output -eq 0 ]; then
  echo "Non overflow test success!!"
else
  echo "Non overflow test failed..."
fi

od -An -tx1 -w1 -v ./shadowstack-tests/failure.hex > out
sbt "testOnly main.ShadowStackTest" &> /dev/null; output=$?
if [ $output -eq 1 ]; then
  echo "Overflow test success!!"
else
  echo "Overflow test failed..."
fi
