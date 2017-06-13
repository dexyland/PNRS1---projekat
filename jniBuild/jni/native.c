#include <jni.h>
#include "native.h"

JNIEXPORT jfloat JNICALL Java_rtrk_pnrs1_ra94_12013_projekat1_statisticsNative_getStatisticsResult(JNIEnv *env, jobject obj, jint mDone, jint mTotal){
    return (jfloat)(100*mDone/mTotal);
}