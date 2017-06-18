package advertisementManager.statistic.event;

import java.util.Date;

/**
 * Created by Evgeniy on 18.11.2016.
 */
public interface EventDataRow
{
    EventType getType();

    Date getDate();

    int getTime();
}
