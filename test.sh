#!/bin/sh
a=1
b=1
echo $a
echo $b
for I in 1 2 3 4 5 6 7 8
do
c=a
echo c $c
echo a $a
b=$a
b=$(($a+$c))
echo $b
done

if (git rev-parse --verify --quiet test)
then
	echo "Already Release branch exits"
fi
