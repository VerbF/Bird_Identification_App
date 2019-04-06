#!/usr/bin/env python
# coding: utf-8
# -*- coding: utf-8 -*-

import os
import sys
import os
sys.path.append("..")
from birds.birds_config import BirdsConfig

class InferenceConfig(BirdsConfig):
    GPU_COUNT = 1
    IMAGES_PER_GPU = 1