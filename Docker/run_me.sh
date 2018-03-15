#!/bin/bash

if test "$#" -ne 1 ; then
	echo "Usage: ./run_me.sh <eclipese_workspace>"
else

	sudo docker run -v $1:/home/working_dir/mount -v ~/.m2:/root/.m2/ -v ~/.eclipse:/root/.eclipse -d -p 5901:5901 herondev:latest

	echo "Container is now running, you may connect via any VNC viewer (try TigerVNC Viewer)"
	echo "Connect using port 5901. Password is vncpassword"

fi

