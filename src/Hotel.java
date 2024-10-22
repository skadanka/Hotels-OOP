import java.time.Year;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.*;

public class Hotel implements  ITestable{
    private String name;
    private HashMap<Client, ReservationSet> allReservation;
    private HashMap<Service, HotelService> services;
    private HashMap<Integer,Room> rooms;
    private String city;
    private Group group;
    private int rate;



    public Hotel(String city, String name,int rate){
        this.city = city;
        this.name = name;
        this.rate = rate;
        rooms = new HashMap<Integer,Room>();
        allReservation = new HashMap<Client, ReservationSet>();
        services = new HashMap<Service, HotelService>();

    }

    public void addReservationSet(Client client,ReservationSet reservationSet){
        allReservation.put(client,reservationSet);
    }

    public void addService(Service service, HotelService hotelService){
        services.put(service,hotelService);
    }

    public void addRoom(int roomNumber, Room room){
        rooms.put(roomNumber,room);
    }


    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public HashMap<Client, ReservationSet> getAllReservation(){return allReservation;}

    public HashMap<Service, HotelService> getServices(){return services;}

    public int getRate(){return rate;}

    @Override
    public boolean checkConstraints() {
        /**
         * Constraint 6
         */
         int numberVIPLimit = (int) (this.rooms.size() * 0.1);
         int numberVIP = 0;
         for (Room r: this.rooms.values())
         {
             if (r.getRoomCategory().getType().equals(RoomCategory.RoomType.VIP))
                 numberVIP++;
         }
         if (numberVIP > numberVIPLimit)
             return false;

        /**
         * Constraint 7
         */
        if (this.city.equals("LAS VEGAS"))
        {
            for (Client c: this.allReservation.keySet())
                if (c.getAge() < 21)
                    return false;
        }


        /**
         * Constraint 11
         */
        for (Service s1 : this.services.keySet())
        {
            for (Service s2 : this.services.keySet())
            {
                if (s1 != s2 && s1.getServiceName().equals(s2.getServiceName()))
                    return false;
            }
        }

        /**
         * Constraint 12
         */
        HashMap<Integer, Integer> years = new HashMap<>();
        for (HotelService hotelService : this.getServices().values())
        {
            for (Booking booking : hotelService.getGivenServices())
            {
                String y = booking.getDate().toString();
                int year = Integer.parseInt(y.substring(24));
                if (years.containsKey(year))
                {
                    int newIncome = years.get(year) + hotelService.getPrice();
                    years.put(year, newIncome);
                }
                else
                    years.put(year, 0);
            }
        }

        for (int year1 : years.keySet())
        {
            for (int year2 : years.keySet())
            {
                if (year1 > year2)
                {
                    if (!(years.get(year1) > years.get(year2)))
                        return false;
                }
            }
        }







//        TreeMap<Integer, Integer> yearlyIncome = new TreeMap<>();
//        for (ReservationSet rs : this.getAllReservation().values())
//        {
//            for(Reservation r : rs.getReservations())
//            {
//                if (r.getBookings() != null)
//                {
//                    String y = r.getBookings().getDate().toString();
//                    int year = Integer.parseInt(y.substring(24));
//                    yearlyIncome.put(year, 0);
//                }
//            }
//        }
//        for (HotelService hs : this.services.values())
//        {
//            if (hs.getGivenServices().size() > 0)
//            {
//                for (Booking b:
//                        hs.getGivenServices()) {
//
//                    String y = b.getDate().toString();
//                    int year = Integer.parseInt(y.substring(24));
//                    int income = yearlyIncome.get(year);
//                    income += hs.getPrice();
//                    yearlyIncome.put(year,income);
//
//                }
//            }
//        }
//        yearlyIncome.descendingKeySet();
//
//        for (int year1:
//             yearlyIncome.keySet()) {
//            for (int year2:
//                    yearlyIncome.keySet()) {
//                if (year2 < year1 && yearlyIncome.get(year2) > yearlyIncome.get(year1))
//                    return false;
//            }
//        }


        return true;
    }

    public static boolean checkAllIntancesConstraints(Model model){
        return true;
    }
}
