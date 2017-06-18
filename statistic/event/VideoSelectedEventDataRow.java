package advertisementManager.statistic.event;

import advertisementManager.ad.Advertisement;

import java.util.Date;
import java.util.List;

/**
 * Created by Evgeniy on 18.11.2016.
 */
public class VideoSelectedEventDataRow implements EventDataRow
{
    private List<Advertisement> optimalVideoSet;
    private long amount;
    private int totalDuration;
    private Date currentDate;

    public long getAmount()
    {
        return amount;
    }

    public VideoSelectedEventDataRow(List<Advertisement> optimalVideoSet, long amount, int totalDuration)
    {
        this.optimalVideoSet = optimalVideoSet;
        this.amount = amount;
        this.totalDuration = totalDuration;
        this.currentDate = new Date();
    }

    public void setCurrentDate(Date currentDate)
    {
        this.currentDate = currentDate;
    }

    @Override
    public Date getDate()
    {
        return currentDate;
    }

    @Override
    public int getTime()
    {
        return totalDuration;
    }

    @Override
    public EventType getType()
    {
        return EventType.SELECTED_VIDEOS;
    }
}
