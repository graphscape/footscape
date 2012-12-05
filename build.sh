#!/bin/bash
echo $FS_BUILDING
filename=$FS_BUILDING/build.properties
if [ -f $filename ] 
then
echo "file exist"
else
echo "file not exist"
fi

while read line
do 
if echo $line | grep -q "list.modules"
then
modules=`echo $line | awk -F "=" '{ print $2 }' ` 
fi
done < $filename
echo $modules

#echo $modules | awk -F "," '{ i=1; while(i<=NF){ print $i; i++}}'
echo $modules | awk  -F "," '{ i=1; while(i<=NF){ cmd="ant -buildfile fs-"$i"/build.xml" ;print cmd ; system(cmd); i++}}'
