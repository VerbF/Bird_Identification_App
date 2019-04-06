#!/usr/bin/env python
# coding: utf-8
# -*- coding: utf-8 -*-

import os
import sys
sys.path.append("..")
from mrcnn.config import Config

class BirdsConfig(Config):
    """Configuration for training on the  birds dataset.
    Derives from the base Config class and overrides values specific
    to the  birds dataset.
    """
    # Give the configuration a recognizable name
    NAME = "birds"
 
    # Train on 1 GPU and 8 images per GPU. We can put multiple images on each
    # GPU because the images are small. Batch size is 8 (GPUs * images/GPU).
    GPU_COUNT = 1
    IMAGES_PER_GPU = 1
 
    # Number of classes (including background)
    NUM_CLASSES = 1 + 2  # background + 2 birds
 
    # Use small images for faster training. Set the limits of the small side
    # the large side, and that determines the image bird.
    IMAGE_MIN_DIM = 320
    IMAGE_MAX_DIM = 384
 
    # Use smaller anchors because our image and objects are small
    RPN_ANCHOR_SCALES = (8 * 6, 16 * 6, 32 * 6, 64 * 6, 128 * 6)  # anchor side in pixels
 
    # Reduce training ROIs per image because the images are small and have
    # few objects. Aim to allow ROI sampling to pick 33% positive ROIs.
    TRAIN_ROIS_PER_IMAGE = 100
 
    # Use a small epoch since the data is simple
    STEPS_PER_EPOCH = 100
 
    # use small validation steps since the epoch is small
    VALIDATION_STEPS = 50