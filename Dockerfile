FROM centos:centos7

# Dockerfile author / maintainer 
MAINTAINER mjschmidt mjschmidt 

# This is passed to the heron build command via the --config flag
ENV TARGET_PLATFORM centos

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

RUN yum -y install java-1.8.0-openjdk java-1.8.0-openjdk-devel

ENV JAVA_HOME /usr/lib/jvm/java-1.8.0

RUN yum -y install maven eclipse

RUN git clone https://github.com/twitter/heron.git
RUN curl -LO http://github.com/twitter/heron/releases/download/0.17.5/heron-install-0.17.5-centos.sh
RUN chmod +x heron-install-0.17.5-centos.sh
RUN ./heron-install-0.17.5-centos.sh
ENV PATH="$PATH:$HOME/bin"
