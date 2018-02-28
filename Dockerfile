# This Dockerfile is used to build an headles vnc image based on Centos 
FROM consol/centos-xfce-vnc 

# Dockerfile author / maintainer 
MAINTAINER mjschmidt mjschmidt 
# Additional author: Thomas Ansill

# Switch user if it somehow switched to something else
USER root
RUN mkdir -p /home/working_dir
WORKDIR /home/working_dir

# RDP stuff
RUN yum -y install xrdp
# RUN systemctl enable xrdp.service probably does not work
# RUN systemctl start xrdp.service

# This is passed to the heron build command via the --config flag
ENV TARGET_PLATFORM centos

# Install basic stuff
RUN yum -y upgrade
RUN yum -y update
RUN yum -y install \
      curl \
      cmake \
      openssl-devel \
      file \
      gcc \
      gcc-c++ \
      git \
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
      wget \
      which

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
RUN ./heron-install-0.17.5-centos.sh
ENV PATH="$PATH:$HOME/bin"
