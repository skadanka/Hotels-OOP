import java.util.HashSet;

public class Group implements  ITestable{
    private int groupId;
    HashSet<Hotel> hotels;

    public Group(int id){
        hotels = new HashSet<Hotel>();
        groupId = id;
    }



    public void addHotelToGroup(Hotel hotel){
        hotels.add(hotel);
    }

    //getters

    public int getGroupId() {
        return groupId;
    }

    public HashSet<Hotel> getHotels() {
        return hotels;
    }

    @Override
    public boolean checkConstraints() {

        /**
         * Constraint 1
         */

        for (Hotel h1:
             hotels) {

            for (Hotel h2:
                    hotels)

                if (!h1.equals(h2) && h1.getCity().equals(h2.getCity()))
                    return false;

        }

        /**
         * Constraint 4
         */
        for (Hotel h1: hotels)
        {
            for (Hotel h2: hotels)
            {
                for (Service s: h1.getServices().keySet())
                if (!h2.getServices().containsKey(s))
                    return false;
            }
        }

        return true;
    }
    public static boolean checkAllIntancesConstraints(Model model){
        return true;
    }
}
