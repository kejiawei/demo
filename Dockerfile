# 声明基础镜像=>实际部署时，根据具体环境调整；默认是公网镜像
FROM openjdk:8-jre-alpine
# 声明应用发布者、来源
MAINTAINER avicit <avicit@avic-digital.com>
# 声明工作目录
WORKDIR /tmp

# 声明变量：应用运行模式
ENV RUN_MODE "Docker"
# 声明变量：应用名称
ENV APP_NAME demo
# 声明变量：应用版本
ENV APP_VERSION 0.0.1-SNAPSHOT
# 声明变量：应用开放端口
ENV SERVER_PORT 10008

# 设置容器时间和宿主机时间一致
RUN echo "Asia/Shanghai" > /etc/timezone
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

# 添加执行应用jar
ADD demo-boot/target/${APP_NAME}-boot.jar app.jar

# 声明端口，可设置多个，仅声明
EXPOSE $SERVER_PORT

# 默认加载[dev]配置，需根据具体环境调整
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar","--spring.profiles.active=dev"]
