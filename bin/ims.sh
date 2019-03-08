#!/bin/bash

# Java ENV
export JAVA_HOME=/home/wuyu/java/jdk1.8
export JRE_HOME=${JAVA_HOME}/jre

# Apps Info
# 应用存放地址
APP_HOME=/home/wuyu/PixelStack
# 应用名称
APP_NAME=ims_pixelStack

# Shell Info 

# 使用说明，用来提示输入参数
usage() {
    echo "Usage: sh ims.sh ims_pixelStack [start|stop|restart|status]"
    exit 1
}

# 检查程序是否在运行
is_exist(){
        # 获取 PID
        PID=$(ps -ef |grep ${APP_NAME} | grep -v $0 |grep -v grep |awk '{print $2}')
        # -z "${pid}"判断 pid 是否存在，如果不存在返回 1，存在返回 0
        if [ -z "${PID}" ]; then
                # 如果进程不存在返回1
                return 1
        else
                # 进程存在返回0
                return 0
        fi
}

# 定义启动程序函数
start(){
        is_exist
        if [ $? -eq "0" ]; then
                echo "${APP_NAME} is already running, PID=${PID}"
        else    
                nohup ${JRE_HOME}/bin/java -jar ${APP_HOME}/target/${APP_NAME}.jar >../logs/nohup.log 2>&1 &
                PID=$(echo $!)
                echo "${APP_NAME} start success, PID=$!"
        fi
}

# 停止进程函数
stop(){
        is_exist
        if [ $? -eq "0" ]; then
                kill -9 ${PID}
                echo "${APP_NAME} process stop, PID=${PID}"
        else    
                echo "There is not the process of ${APP_NAME}"
        fi
}

# 重启进程函数 
restart(){
        stop
        start
}

# 查看进程状态
status(){
        is_exist
        if [ $? -eq "0" ]; then
                echo "${APP_NAME} is running, PID=${PID}"
        else    
                echo "There is not the process of ${APP_NAME}"
        fi
}

case $2 in
"start")
        start
        ;;
"stop")
        stop
        ;;
"restart")
        restart
        ;;
"status")
       status
        ;;
	*)
	usage
	;;
esac
exit 0
