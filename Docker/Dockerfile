FROM centos:7

# Dockerfile author / maintainer 
MAINTAINER tbansil tbansil

# Install basic stuff
RUN yum install -y epel-release
RUN yum -y upgrade
RUN yum -y update
RUN yum -y install \
	bzip2 \
      curl \
      cmake \
      openssl-devel \
      file \
      gcc \
      gcc-c++ \
      git \
      gedit \
      kernel-devel \
      libtool \
      make \
      patch \
      python \
      python-devel \
      python-setuptools \
      zip \
      unzip \
      vim \
	net-tools2 \
	sudo \
      wget \
      which

# Install TigerVNC
RUN yum -y install tigervnc-server
RUN yum clean all

# Install XFCE4
RUN yum -y -x gnome-keyring --skip-broken groupinstall "Xfce" "X Window System" "Desktop"
RUN yum -y groupinstall "Fonts"
RUN yum erase -y *power* *screensaver*
RUN rm /etc/xdg/autostart/xfce-polkit*
RUN /bin/dbus-uuidgen > /etc/machine-id
ADD ./xfce/ /root/

# VNC port
EXPOSE 5901 

# Init folder
RUN mkdir -p /init
ADD ./start_up_scripts/ /init/
RUN chmod a+x /init/*
RUN /init/set_user_permission.sh /init $HOME

# LibNSS wrapper
RUN yum -y install nss_wrapper gettext
RUN echo 'source /init/generate_container_user' >> $HOME/.bashrc

# Set VNC password and other settings
ENV	VNC_PASSWORD=vncpassword \
	VNC_RESOLUTION=1280x1024 \
	VNC_COL_DEPTH=24 \
	DISPLAY=:1 \
	TERM=xterm

# Set the entrypoint in docker startup
ENTRYPOINT ["/init/vnc_startup.sh"]
CMD ["--tail-log"]

# Create working dir
RUN mkdir -p /home/working_dir
WORKDIR /home/working_dir

# RDP stuff
# RUN yum -y install xrdp
# RUN systemctl enable xrdp.service probably does not work
# RUN systemctl start xrdp.service

# This is passed to the heron build command via the --config flag
ENV TARGET_PLATFORM centos


# Install java 8 JDK
RUN yum -y install java-1.8.0-openjdk java-1.8.0-openjdk-devel

# Set Java 8 home
ENV JAVA_HOME /usr/lib/jvm/java-1.8.0

# Install maven
RUN yum -y install maven

# Centos 7 doesn't have RPM file for eclipse?
# Installing eclipse manually
# Get binary
RUN curl -LO http://mirror.math.princeton.edu/pub/eclipse//technology/epp/downloads/release/oxygen/2/eclipse-jee-oxygen-2-linux-gtk-x86_64.tar.gz

# Unarchive the binary into software directory
RUN tar -zxvf eclipse-jee-oxygen-2-linux-gtk-x86_64.tar.gz -C /usr/local/

# Throw away the tar as we don't need it anymore
RUN rm -rf eclipse-jee-oxygen-2-linux-gtk-x86_64.tar.gz

# Link the executable to /bin so other programs can find the executable
RUN ln -s /usr/local/eclipse/eclipse /usr/bin/eclipse

# Creates a desktop entry for eclipse
# Puts a shortcut in the DE application launcher
RUN printf "[Desktop Entry]\nName=Eclipse\nComment=Eclipse IDE\nType=Application\nEncoding=UTF-8\nExec=/usr/bin/eclipse\nIcon=/usr/local/eclipse/icon.xpm\nCategories=GNOME;Application;Development;\nTerminal=false\nStartupNotify=true" > /usr/share/applications/eclipse.desktop

# Set up heron
RUN git clone https://github.com/twitter/heron.git
RUN curl -LO http://github.com/twitter/heron/releases/download/0.17.5/heron-install-0.17.5-centos.sh

# Run the heron install script
RUN chmod +x heron-install-0.17.5-centos.sh
RUN ./heron-install-0.17.5-centos.sh --user
ENV PATH="$PATH:/root/bin"

# Remove heron stuff that we don't need (heron is already installed somewhere else)
RUN rm -rf heron
RUN rm -rf heron-install-0.17.5-centos.sh

# Create a directory that is to be mounted by the host
RUN mkdir -p /home/working_dir/mount

# Hook up .m2 in container with vm
RUN mkdir -p /root/.m2

# Hook up .eclipse in container with vm
RUN mkdir -p /root/.eclipse

# Add README.md
ADD README.md /root/
RUN mkdir -p /root/Desktop/
ADD README.md /root/Desktop/
