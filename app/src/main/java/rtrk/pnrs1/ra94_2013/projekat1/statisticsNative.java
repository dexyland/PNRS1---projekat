package rtrk.pnrs1.ra94_2013.projekat1;

public class statisticsNative {
    public native float getStatisticsResult(float mFinished, float mTotal);

    static{
        System.loadLibrary("statisticsNative");
    }
}