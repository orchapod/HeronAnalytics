#!/bin/bash

if test "$#" -ne 1 ; then
	echo "Usage: ./run_me.sh <eclipse_workspace>"
else

	sudo docker run -v $1:/home/working_dir/mount -v ~/.m2:/root/.m2/ -v ~/.eclipse:/root/.eclipse -d -p 5901:5901 -p 8888:8888 -p 8889:8889 herondev:latest

	echo "Container is now running, you may connect via any VNC viewer (try TigerVNC Viewer)"
	echo "Connect using port 5901. Password is vncpassword"
	echo "Heron tracker is up. You may connect it using port 8888."
	echo "Heron UI is up. You may connect it using port 8889."

fi

