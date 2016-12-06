package com.pxene.pap.domain.beans;

public class AudioBean extends MediaBean
{
    private int timelength;

    
    public int getTimelength()
    {
        return timelength;
    }
    
    public void setTimelength(int timelength)
    {
        this.timelength = timelength;
    }
    
    
    @Override
    public String toString()
    {
        return "AudioBean [timelength=" + timelength + "]";
    }
}
