package advertisementManager.kitchen;

/**
 * Created by Evgeniy on 18.11.2016.
 */
public enum Dish
{
    Fish(25), Steak(30), Soup(15), Juice(5), Water(3);

    private int duration;

    Dish(int duration)
    {
        this.duration = duration;
    }

    public int getDuration()
    {
        return duration;
    }

    public static String allDishesToString()
    {
        String result = "";
        Dish[] list = Dish.values();
        for (int i = 0; i < list.length; i++)
        {
            if (i == 0) result = list[i].toString();
            else result = result + ", " + list[i];

        }
        return result;
    }
}
