#include "rtrk_pnrs1_ra94_2013_projekat1.h"
#include <jni.h>

JNIEXPORT jfloat JNICALL Java_rtrk_pnrs1_ra94_12013_projekat1_statisticsNative_getStatisticsResult
  (JNIEnv *env, jobject obj, jfloat mDone, jfloat mTotal)
  {
    return (jfloat)(100*(mDone/mTotal));
  }