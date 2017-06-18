package advertisementManager.kitchen;

import advertisementManager.ConsoleHelper;
import advertisementManager.statistic.StatisticEventManager;
import advertisementManager.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Evgeniy on 18.11.2016.
 */
public class Cook extends Observable implements Runnable
{
    private String name;
    private boolean busy;
    private LinkedBlockingQueue<Order> queue;
    private boolean exception = false;

    public void setQueue(LinkedBlockingQueue<Order> queue)
    {
        this.queue = queue;
    }

    public boolean isBusy()
    {
        return busy;
    }

    public Cook(String name)
    {
        this.name = name;
    }

    public void startCookingOrder(Order order)
    {
        busy = true;
        if (!order.isEmpty())
        {
            ConsoleHelper.writeMessage("Start cooking - " + order.toString() + ", cooking time " + order.getTotalCookingTime() + "min");
            StatisticEventManager.getInstance().register(new CookedOrderEventDataRow(order.getTablet().toString(),name,order.getTotalCookingTime()*60,order.getDishes()));
            try
            {
                Thread.sleep(order.getTotalCookingTime()*10);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            setChanged();
            notifyObservers(order);
        }
        busy = false;
    }
    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public void run()
    {
        while (true)
        {
            if (!queue.isEmpty())
            {
                try
                {
                    startCookingOrder(queue.take());
                }
                catch (InterruptedException e)
                {
                    exception = true;
                }
            }
            else if (queue.isEmpty() && exception) break;
        }
    }
}
