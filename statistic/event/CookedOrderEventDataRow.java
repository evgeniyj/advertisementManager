package advertisementManager.statistic.event;

import advertisementManager.kitchen.Dish;

import java.util.Date;
import java.util.List;

/**
 * Created by Evgeniy on 18.11.2016.
 */
public class CookedOrderEventDataRow implements EventDataRow
{
    private String tabletName;
    private String cookName;
    private int cookingTimeSeconds;
    private List<Dish> cookingDishs;
    private Date currentDate;

    public CookedOrderEventDataRow(String tabletName, String cookName, int cookingTimeSeconds, List<Dish> cookingDishs)
    {
        this.tabletName = tabletName;
        this.cookName = cookName;
        this.cookingTimeSeconds = cookingTimeSeconds;
        this.cookingDishs = cookingDishs;
        this.currentDate = new Date();
    }

    public void setCurrentDate(Date currentDate)
    {
        this.currentDate = currentDate;
    }

    public String getCookName()
    {
        return cookName;
    }

    @Override
    public Date getDate()
    {
        return currentDate;
    }

    @Override
    public int getTime()
    {
        return cookingTimeSeconds;
    }

    @Override
    public EventType getType()
    {
        return EventType.COOKED_ORDER;
    }
}
