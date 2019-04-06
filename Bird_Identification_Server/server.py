#!/usr/bin/env python
# coding: utf-8
import os
import sys
import random
import string
import socket
from flask import Flask, request,Response

app = Flask(__name__)
#基础设置
ip_address = '192.168.1.105'
port = '8888'
ROOT_DIR = os.path.dirname(os.path.abspath(__file__))#根目录
DETECT_IMAGES_DIR = os.path.join(ROOT_DIR,"detect_images")#放置待检测图片的目录
DETECT_RESULTS_DIR = os.path.join(ROOT_DIR,"detect_results")#放置检测结果图片的目录
DETECT_FILE_PATH = os.path.join(ROOT_DIR,"detect.py")#检测文件的路径

#接收图片 处理图片 返回图片
@app.route('/uploadImg',methods=['GET','POST'])
def uploadImg():
    #生成随机字符串，防止图片名字重复
    ran_str = ''.join(random.sample(string.ascii_letters + string.digits, 16))
    #获取图片文件 name = upload
    img = request.files.get('img')
    #图片名称 给图片重命名 为了图片名称的唯一性
    imgName = ran_str + '_' +img.filename
    #图片path和名称组成图片的保存路径
    file_path = os.path.join(DETECT_IMAGES_DIR , imgName)
    #保存图片
    img.save(file_path)

    #执行 识别鸟类的脚本 图图片的名字作为参数
    cmd_str = ("python " + DETECT_FILE_PATH + " " + imgName)
    os.system(cmd_str)

    #获取 识别的结果图片
    imgPath = os.path.join(DETECT_RESULTS_DIR , imgName)
    with open(imgPath, 'rb') as f:
        image = f.read()

    #返回结果
    return Response(image, mimetype="image/jpeg")

if __name__ == '__main__':
    app.run(host=ip_address,port=port,debug=True)
 