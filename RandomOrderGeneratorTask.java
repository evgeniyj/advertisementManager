package advertisementManager;

import java.util.List;

/**
 * Created by Evgeniy on 19.11.2016.
 */
public class RandomOrderGeneratorTask implements Runnable
{
    private List<Tablet> tablets;
    private int interval;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval)
    {
        this.tablets = tablets;
        this.interval = interval;
    }

    @Override
    public void run()
    {
        try
        {
            while(!Thread.currentThread().isInterrupted())
            {
                int tabletNumber = (int) (Math.random() * tablets.size());
                Tablet tablet = tablets.get(tabletNumber);
                tablet.createTestOrder();
                Thread.sleep(interval);
            }
        }
        catch (InterruptedException e) {}
    }
}