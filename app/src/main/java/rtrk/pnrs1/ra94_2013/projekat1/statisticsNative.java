package rtrk.pnrs1.ra94_2013.projekat1;

public class statisticsNative {
    public native float getStatisticsResult(int mDone, int mTotal);

    static{
        System.loadLibrary("statisticsNative");
    }
}