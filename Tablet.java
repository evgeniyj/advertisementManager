package advertisementManager;

import advertisementManager.ad.AdvertisementManager;
import advertisementManager.ad.NoVideoAvailableException;
import advertisementManager.kitchen.Order;
import advertisementManager.kitchen.TestOrder;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Evgeniy on 18.11.2016.
 */
public class Tablet
{
    private static Logger logger = Logger.getLogger(Tablet.class.getName());
    public final int number;
    Order order;
    private LinkedBlockingQueue<Order> queue;

    public void setQueue(LinkedBlockingQueue<Order> queue)
    {
        this.queue = queue;
    }

    public Tablet(int number)
    {
        this.number = number;
    }

    public void createOrder()
    {
        try
        {
            order = new Order(this);
            newOrder(order);
        }
        catch (IOException e)
        {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
    }

    public void createTestOrder()
    {
        try
        {
            order = new TestOrder(this);
            newOrder(order);
        }
        catch (IOException e)
        {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
    }

    public void newOrder(Order order)
    {
        ConsoleHelper.writeMessage(order.toString());
        try
        {
            queue.put(order);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        try
        {
            new AdvertisementManager(order.getTotalCookingTime()*60).processVideos();
        }
        catch (NoVideoAvailableException e)
        {
            logger.log(Level.INFO, "No video is available for the order " + order);

        }
    }


    @Override
    public String toString()
    {
        return "Tablet{" +
                "number=" + number +
                '}';
    }
}
