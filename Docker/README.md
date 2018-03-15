Heron Development Docker Container

This docker container is installed with:

Eclipse
Maven
Java JDK 8
Heron 1.17.5
TigerVNC
XFCE4

With the tools in the container, you'll be able to run the docker and either SSH into the container or connect to the container using a VNC viewer (password is 'vncpassword'). The container will mount three directories on your host computer. It will mount your Maven repo, Eclipse config directory, and your chosen project directory. When you've connected to the container, your project directory will be mounted at '/home/working_dir/mount'. You can run Eclipse to work on your project. You can also run Maven to build your project and deploy it at Heron. 

To start a container, you can either use 'run_me.sh' and provide your desired project directory to be mounted on the container, or run the following command:
 
	$ docker run -v <path_on_your_host>:/home/working_dir/mount -v <path_to_m2_on_your_host>:~/.m2 -v <path_to_eclipse_on_your_host>:~/.eclipse -d -p 5901:5901 herondev:latest

 <path_on_your_host> is your eclipse workspace. Mount this, then when you start up eclipse on the container, tell eclipse to use /home/working_dir/mount as eclipse workspace.

 <path_to_m2_on_your_host> is your Maven repo. You'd probably want to use '~/.m2'. (Make sure that directory exists before continuning with the command)

 <path_to_eclipse_on_your_host> is your Eclipse config directory. You'd probably want to use '~/.eclipse'. (Make sure that directory exists before continuning with the command)

