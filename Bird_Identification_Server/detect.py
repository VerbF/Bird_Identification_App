#!/usr/bin/env python
# coding: utf-8
# -*- coding: utf-8 -*-

import os
import sys
import random
import math
import re
import time
import numpy as np
import cv2
import skimage.io
import matplotlib
import matplotlib.pyplot as plt
import tensorflow as tf
from mrcnn.config import Config
from base64 import b64encode
from json import dumps
#import utils
from mrcnn import model as modellib,utils
from mrcnn import visualize
import yaml
from mrcnn.model import log
from PIL import Image
from birds.birds_dataset import BirdsDataset
from birds.inference_config import InferenceConfig

#基础设置
#路径设置
ROOT_DIR = os.getcwd()
MODEL_DIR = os.path.join(ROOT_DIR, "logs")
DETECT_IMAGE_DIR = os.path.join(ROOT_DIR,"detect_images")
DETECT_RESULTS_DIR = os.path.join(ROOT_DIR,"detect_results")
#编码格式设置
ENCODING = 'utf-8'

#获得轴心，控制图片大小
def get_ax(rows=1, cols=1, size=8):
    """Return a Matplotlib Axes array to be used in
    all visualizations in the notebook. Provide a
    central point to control graph sizes.
    
    Change the default size attribute to control the size
    of rendered images
    """
    _, ax = plt.subplots(rows, cols, figsize=(size*cols, size*rows))
    return ax
#加载单张图片
def load_image(img_path):
        """Load the specified image and return a [H,W,3] Numpy array.
        """
        # Load image
        image = skimage.io.imread(img_path)
        # If grayscale. Convert to RGB for consistency.
        if image.ndim != 3:
            image = skimage.color.gray2rgb(image)
        # If has an alpha channel, remove it for consistency
        if image.shape[-1] == 4:
            image = image[..., :3]
        return image
#保存识别出的结果保存到json文件(图像 + 鸟类名字 + 鸟类信息)
def save_detect_json_result(file_name, bird_name):     
    #读取二进制图片，获得原始字节码
    with open(file_name,'rb') as jpg_file:
        byte_content = jpg_file.read()
    #把原始字节码编码成 base64 字节码
    base64_bytes = b64encode(byte_content)
    #将 base64 字节码解码成 utf-8 格式的字符串
    base64_string = base64_bytes.decode(ENCODING)
    #用字典的形式保存数据
    raw_data = {}
    raw_data["image_base64_string"] =  base64_string
    raw_data['bird_name'] = bird_name
    raw_data['bird_info'] = 'there is test bird info'
    #将字典变成 json 格式，缩进为 2 个空格
    json_data = dumps(raw_data, indent=2)
    #将 json 格式的数据保存到文件中
    JSON_NAME = os.path.join(DETECT_RESULTS_DIR,image_name + '.json')
    with open(JSON_NAME,'w') as json_file:
        json_file.write(json_data)
        json_file.close()
    print('Save json successfully!')

#推断设置
inference_config = InferenceConfig()
#在推断模式下重新创建模型
model = modellib.MaskRCNN(mode="inference", 
                          config=inference_config,
                          model_dir=MODEL_DIR)
#训练好的模型权重文件
model_path = os.path.join(MODEL_DIR, "birds_model.h5")
#加载权重文件
model.load_weights(model_path, by_name=True)

#数据集基础设置
bird_dataset = BirdsDataset()
bird_dataset.add_classes()
bird_dataset.prepare()
#指定待检测的图片
image_name = sys.argv[1]
detect_img = load_image(os.path.join(DETECT_IMAGE_DIR,image_name))
#得到结果
results = model.detect([detect_img], verbose=1)
r = results[0]
#设置检测结果图片保存路径
save_path = os.path.join(DETECT_RESULTS_DIR,image_name)

#保存图片结果
visualize.save_detect_image_result(save_path,detect_img,  r['rois'], r['masks'], r['class_ids'], 
                            bird_dataset.class_names, r['scores'] ,ax=get_ax(),show_mask = True, show_bbox = False)

#保存文本结果
save_detect_json_result(save_path , bird_dataset.class_names[r['class_ids'][0]])  


   




