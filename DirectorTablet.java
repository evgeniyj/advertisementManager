package advertisementManager;
import advertisementManager.ad.Advertisement;
import advertisementManager.ad.StatisticAdvertisementManager;
import advertisementManager.statistic.StatisticEventManager;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Evgeniy on 18.11.2016.
 */
public class DirectorTablet
{
    public void printAdvertisementProfit()
    {
        Map<Date, Double> result = StatisticEventManager.getInstance().getDailyProfit();
        double total = 0;
        NumberFormat formatter = new DecimalFormat("#0.00");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-y", Locale.ENGLISH);

        TreeMap<Date, Double> sortedResult = new TreeMap<>(Collections.reverseOrder());
        sortedResult.putAll(result);

        Iterator it = sortedResult.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry) it.next();
            double sum = (double) pair.getValue();
            Date date = (Date) pair.getKey();
            ConsoleHelper.writeMessage(dateFormat.format(date) + " - " + formatter.format(sum));
            total += sum;
            it.remove(); // avoids a ConcurrentModificationException
        }
        ConsoleHelper.writeMessage("Total - " + formatter.format(total));
    }

    public void printCookWorkloading()
    {
        Map<Date, Map<String, Integer>> result = StatisticEventManager.getInstance().getCookInfo();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-y", Locale.ENGLISH);

        TreeMap<Date, Map<String, Integer>> sortedResult = new TreeMap<>(Collections.reverseOrder());
        sortedResult.putAll(result);

        Iterator it = sortedResult.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry) it.next();

            Date date = (Date) pair.getKey();
            Map<String, Integer> cooks = (Map<String, Integer>) pair.getValue();

            TreeMap<String, Integer> sortedCooks = new TreeMap<>();
            sortedCooks.putAll(cooks);

            ConsoleHelper.writeMessage(dateFormat.format(date));

            Iterator it1 = sortedCooks.entrySet().iterator();
            while(it1.hasNext())
            {
                Map.Entry pair1 = (Map.Entry) it1.next();
                String name = (String) pair1.getKey();
                int time = (int) pair1.getValue();
                ConsoleHelper.writeMessage(name + " - " + time + " min");
            }
            ConsoleHelper.writeMessage("");

            it.remove(); 
        }
    }

   
    public void printActiveVideoSet()
    {
        boolean isActive = true;
        ArrayList<Advertisement> list = StatisticAdvertisementManager.getInstance().getAdvertisementStatus(isActive);

        Collections.sort(list, new Comparator<Advertisement>() {
            public int compare(Advertisement o1, Advertisement o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        for (Advertisement advertisement:list)
        {
            ConsoleHelper.writeMessage(advertisement.getName() + " - " + advertisement.getHits());
        }

    }

    public void printArchivedVideoSet()
    {
        boolean isActive = false;
        ArrayList<Advertisement> list = StatisticAdvertisementManager.getInstance().getAdvertisementStatus(isActive);

        Collections.sort(list, new Comparator<Advertisement>() {
            public int compare(Advertisement o1, Advertisement o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        for (Advertisement advertisement:list)
        {
            ConsoleHelper.writeMessage(advertisement.getName());
        }
    }


}
