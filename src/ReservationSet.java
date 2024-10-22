import java.util.ArrayList;

public class ReservationSet implements  ITestable{
    private Client client;
    private Hotel hotel;
    private ArrayList<Reservation> reservations;

    public ReservationSet(){
        reservations = new ArrayList<Reservation>();
    }

    public void setClient(Client client){
        this.client = client;
    }

    public void setHotel(Hotel hotel){
        this.hotel = hotel;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }


    public Client getClient() {
        return client;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    @Override
    public boolean checkConstraints() {
        /**
         * Constraint 2
         */
        if(reservations.size() >= 5)
        {
            int vip = 0;
            int booking_amount = 0;
            for (Reservation r:reservations)
            {
                if(r.getBookings() != null){
                    if (r.getBookings().getRoom().getRoomCategory().getType().equals(RoomCategory.RoomType.VIP))
                        vip++;
                        booking_amount++;
                }
                    
            }
            if (vip == 0 && booking_amount >= 5)
                return false;
        }

        /**
         * Constraint 10
         */
        double ranking = 0;
        int reviewCount = 0;
        if (this.hotel.getRate() == 5)
        {
            for (Reservation r : this.getReservations())
            {
                if (r.getBookings().getReview() != null) {
                    ranking += r.getBookings().getReview().getRank();
                    reviewCount++;
                }
            }
            if (!(ranking / reviewCount > 7.5))
                return false;
        }

        return true;
    }

    public static boolean checkAllIntancesConstraints(Model model){
        return true;
    }
}
