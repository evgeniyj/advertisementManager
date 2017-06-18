package advertisementManager;

import advertisementManager.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Evgeniy on 18.11.2016.
 */
public class ConsoleHelper
{
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message)
    {
        System.out.println(message);
    }

    public static String readString() throws IOException
    {
        return reader.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException
    {
        List<Dish> order = new LinkedList<>();
        writeMessage("Please, choose dish");
        writeMessage(Dish.allDishesToString());
        while (true)
        {
            String dish = readString();

            if (dish.equalsIgnoreCase("EXIT")) break;
            else if (!Dish.allDishesToString().contains(dish)) writeMessage(dish + " is not detected");
            else order.add(Dish.valueOf(dish));
        }
        return order;
    }

}
