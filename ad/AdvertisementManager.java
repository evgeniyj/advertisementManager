package advertisementManager.ad;

import advertisementManager.ConsoleHelper;
import advertisementManager.statistic.StatisticEventManager;
import advertisementManager.statistic.event.VideoSelectedEventDataRow;

import java.util.*;

/**
 * Created by Evgeniy on 18.11.2016.
 */
public class AdvertisementManager
{
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds;

    public AdvertisementManager(int timeSeconds)
    {
        this.timeSeconds = timeSeconds;
    }

    public <Advertisement> Set<ArrayList<Advertisement>> powerSet(List<Advertisement> originalSet) {
        Set<ArrayList<Advertisement>> sets = new HashSet<>();
        if (originalSet.isEmpty()) {
            sets.add(new ArrayList<Advertisement>());
            return sets;
        }
        List<Advertisement> list = new ArrayList(originalSet);
        Advertisement head = list.get(0);
        ArrayList<Advertisement> rest = new ArrayList<>(list.subList(1, list.size()));
        for (ArrayList<Advertisement> set : powerSet(rest)) {
            ArrayList<Advertisement> newSet = new ArrayList<>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
    }

    private int getTotalDuration(ArrayList<Advertisement> list)
    {
        int totalDuration = 0;
        for (Advertisement ad : list)
        {
            totalDuration += ad.getDuration();
        }
        return totalDuration;
    }

    private int getTotalCost(ArrayList<Advertisement> list)
    {
        int totalCost = 0;
        for (Advertisement ad : list)
        {
            totalCost += ad.getAmountPerOneDisplaying();
        }
        return totalCost;
    }

    public void processVideos() throws NoVideoAvailableException
    {
        if (storage.list().isEmpty())
        {
            throw new NoVideoAvailableException();
        }

        ArrayList<Advertisement> foo = new ArrayList<>(storage.list());

        Set<ArrayList<Advertisement>> sets = powerSet(foo);

        for (ArrayList<Advertisement> adv : sets)
        {
            Iterator iterator = adv.iterator();
            while (iterator.hasNext())
            {
                Advertisement advertisement = (Advertisement) iterator.next();
                if (advertisement.getHits() == 0) iterator.remove();
            }
        }

        ArrayList<ArrayList<Advertisement>> finalSet = new ArrayList<>();

        Iterator iterator1 = sets.iterator();
        while (iterator1.hasNext())
        {
            ArrayList<Advertisement> tmp = (ArrayList<Advertisement>) iterator1.next();
            if (tmp.isEmpty()) iterator1.remove();
        }
        for (ArrayList<Advertisement> adv : sets)
        {
            boolean hasZeroHits = false;
            int totalTime = 0;
            long totalMoney = 0;
            for (Advertisement ad: adv)
            {
                totalTime += ad.getDuration();
                totalMoney += ad.getAmountPerOneDisplaying();
                if (ad.getHits() <= 0) hasZeroHits = true;
            }
            if (totalTime <= timeSeconds && !hasZeroHits)
            {
                finalSet.add(adv);
            }
        }

        if (finalSet.isEmpty())
        {
            throw new NoVideoAvailableException();
        }

        Collections.sort(finalSet, new Comparator<ArrayList<Advertisement>>() {
            public int compare(ArrayList<Advertisement> set1, ArrayList<Advertisement> set2) {
                Integer cost1 = getTotalCost(set1);
                Integer cost2 = getTotalCost(set2);
                int result = cost2.compareTo(cost1);
                if (result == 0)
                {
                    Integer dur1 = getTotalDuration(set1);
                    Integer dur2 = getTotalDuration(set2);
                    int result2 = dur2.compareTo(dur1);
                    if (result2 == 0)
                    {
                        Integer count1 = set1.size();
                        Integer count2 = set2.size();
                        return count1.compareTo(count2);
                    }
                    return result2;
                }else
                    return result;
            }
        });

        ArrayList<Advertisement> finalAdvertisementSet = finalSet.get(0);
        if (finalAdvertisementSet.isEmpty())
        {
            throw new NoVideoAvailableException();
        }

        Collections.sort(finalAdvertisementSet, new Comparator<Advertisement>() {
            public int compare(Advertisement o1, Advertisement o2) {
                Long a = o1.getAmountPerOneDisplaying();
                Long b = o2.getAmountPerOneDisplaying();
                int result = b.compareTo(a);
                if (result == 0)
                {
                    Long c = o1.getAmountPerOneDisplaying()*1000/o1.getDuration();
                    Long d = o2.getAmountPerOneDisplaying()*1000/o2.getDuration();
                    return c.compareTo(d);
                }
                else return result;
            }
        });

        long amount = 0;
        int totalDuration = 0;

        for (Advertisement ad : finalAdvertisementSet) {
            amount += ad.getAmountPerOneDisplaying();
            totalDuration += ad.getDuration();
        }

        StatisticEventManager.getInstance().register(new VideoSelectedEventDataRow(finalAdvertisementSet, amount, totalDuration));
        for (Advertisement advertisement : finalAdvertisementSet)
        {
            ConsoleHelper.writeMessage(advertisement.getName() + " is displaying... " + advertisement.getAmountPerOneDisplaying() + ", " + advertisement.getAmountPerOneDisplaying()*1000/advertisement.getDuration());
            advertisement.revalidate();
        }
    }
}
