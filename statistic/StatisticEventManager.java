package advertisementManager.statistic;

import advertisementManager.statistic.event.CookedOrderEventDataRow;
import advertisementManager.statistic.event.EventDataRow;
import advertisementManager.statistic.event.EventType;
import advertisementManager.statistic.event.VideoSelectedEventDataRow;

import java.util.*;

/**
 * Created by Evgeniy on 18.11.2016.
 */
public class StatisticEventManager
{
    private static StatisticEventManager ourInstance = new StatisticEventManager();
    private StatisticStorage storage = new StatisticStorage();

    public static StatisticEventManager getInstance()
    {
        return ourInstance;
    }

    private StatisticEventManager()
    {
    }

    public void register(EventDataRow data)
    {
        storage.put(data);
    }

    public Map<Date, Double> getDailyProfit()
    {
        Map<EventType, List<EventDataRow>> map = storage.getMap();
        List<EventDataRow> advList = map.get(EventType.SELECTED_VIDEOS);
        Map<Date, Double> result = new HashMap<>();
        Calendar cal = Calendar.getInstance();

        for (EventDataRow data : advList)
        {
            double amount = ((VideoSelectedEventDataRow)data).getAmount()/(double)100;

            Date date = data.getDate();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            date = cal.getTime();

            if (result.get(date) == null)
            {
                result.put(date,amount);
            }
            else
            {
                result.put(date,result.get(date)+amount);
            }
        }
        return result;
    }


    public Map<Date, Map<String, Integer>> getCookInfo()
    {
        Map<EventType, List<EventDataRow>> map = storage.getMap();
        List<EventDataRow> cookList = map.get(EventType.COOKED_ORDER);
        Map<Date, Map<String, Integer>> result = new HashMap<>();

        Calendar cal = Calendar.getInstance();

        for (EventDataRow data : cookList)
        {
            int totalTime = (data.getTime() + 59) / 60;
            Date date = data.getDate();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            date = cal.getTime();

            String name = ((CookedOrderEventDataRow) data).getCookName();

            if (result.get(date) == null)
            {
                result.put(date, new HashMap<String, Integer>());
                result.get(date).put(name, totalTime);
            }
            else
            {
                Map<String, Integer> cook = result.get(date);
                if (cook.get(name) == null)
                {
                    cook.put(name, totalTime);
                }
                else
                {
                    cook.put(name, cook.get(name) + totalTime);
                }
            }
        }
        return result;
    }


    private class StatisticStorage
    {
        private Map<EventType, List<EventDataRow>> map = new HashMap<>();
        public StatisticStorage()
        {
            for (EventType e: EventType.values()){
                map.put(e,new ArrayList<EventDataRow>());
            }
        }

        public Map<EventType, List<EventDataRow>> getMap()
        {
            return map;
        }

        private void put(EventDataRow data)
        {
            map.get(data.getType()).add(data);
        }
    }
}
