package advertisementManager;

import advertisementManager.kitchen.Cook;
import advertisementManager.kitchen.Order;
import advertisementManager.kitchen.Waitor;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Evgeniy on 18.11.2016.
 */
public class Restaurant
{
    private static final int ORDER_CREATING_INTERVAL = 100;
    private final static LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>();
    public static void main(String[] args)
    {
        ArrayList<Tablet> tablets = new ArrayList<>();
        Waitor waitor = new Waitor();

        Cook cook1 = new Cook("Amigo1");
        cook1.setQueue(queue);
        Cook cook2 = new Cook("Amigo2");
        cook2.setQueue(queue);

        cook1.addObserver(waitor);
        cook2.addObserver(waitor);

        Thread threadCook1 = new Thread(cook1);
        Thread threadCook2 = new Thread(cook2);

        threadCook1.start();
        threadCook2.start();

        for (int i = 0; i < 5; i++)
        {
            Tablet tablet = new Tablet(i);
            tablet.setQueue(queue);
            tablets.add(tablet);
        }


        RandomOrderGeneratorTask randomOrderGeneratorTask = new RandomOrderGeneratorTask(tablets,ORDER_CREATING_INTERVAL);

        Thread thread = new Thread(randomOrderGeneratorTask);
        thread.start();

        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        thread.interrupt();

        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();

    }
}
