package advertisementManager.ad;

import java.util.ArrayList;

/**
 * Created by Evgeniy on 19.11.2016.
 */
public class StatisticAdvertisementManager
{
    private static StatisticAdvertisementManager ourInstance = new StatisticAdvertisementManager();

    public static StatisticAdvertisementManager getInstance()
    {
        return ourInstance;
    }

    private StatisticAdvertisementManager()
    {
    }

    private AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();

    public ArrayList<Advertisement> getAdvertisementStatus(boolean active)
    {
        ArrayList<Advertisement> list = (ArrayList<Advertisement>) advertisementStorage.list();
        ArrayList<Advertisement> resultActive = new ArrayList<>();
        ArrayList<Advertisement> resultArchived = new ArrayList<>();

        for (Advertisement advertisement:list)
        {
            if (advertisement.getHits() > 0)
            {
                resultActive.add(advertisement);
            }
            else if (advertisement.getHits() <= 0)
            {
                resultArchived.add(advertisement);
            }
        }
        return active ? resultActive : resultArchived;
    }

}
