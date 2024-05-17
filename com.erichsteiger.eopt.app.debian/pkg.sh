#!/bin/bash -x
mkdir -p target
cp -R eopt target/
cp ../com.erichsteiger.eopt.app/target/com.erichsteiger.eopt.app-$1-full.jar target/eopt/usr/share/eopt/eopt-$1.jar

cd target
dpkg-deb --root-owner-group --build eopt
mv eopt.deb eopt-$1.deb
