#!/bin/sh
echo "第一个参数$1";
cd $1;
if [ ! -x changeFile.sh ];then
chmod +x changeFile.sh;
fi
for x in `ls *.hprof`;do a=`echo $x|awk -F '_' '{print "2-"$1"_"$2}'`; hprof-conv -z $x $a; done
rm -rf 2019.*
