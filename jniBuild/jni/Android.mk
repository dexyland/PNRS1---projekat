LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := libstatisticsNative
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := native.c

include $(BUILD_SHARED_LIBRARY)