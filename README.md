# Hotels.com System - Educational Project
Credit: BGU - Dr Arnon Strum
## Introduction
This project is an educational exercise aimed at understanding and applying constraint language (OCL - Object Constraint Language) to model and enforce constraints within a hotel booking system. The goal is to demonstrate how constraints defined in OCL can be applied and enforced using Java programming language. The system is inspired by the Hotels.com platform and focuses on managing hotels, rooms, clients, bookings, reviews, and services.

## Project Goals
1. **Learn and Understand OCL**: 
   - This project aims to provide a better understanding of Object Constraint Language (OCL) by defining real-world constraints for a hotel booking system.
   
2. **Apply Constraints Using Java**: 
   - The project demonstrates how to enforce the constraints defined in OCL by implementing them using Java code.

3. **Create a Hotel Booking System**:
   - Simulate a hotel reservation system where users (clients) can make bookings for rooms. The system tracks important information such as:
     - **Rooms**: Available rooms in the hotel with prices and reviews.
     - **Reservations**: Room bookings made by clients.
     - **Clients**: The users of the system making reservations.
     - **Services**: Additional services offered to hotel guests.
   
4. **Enforce Business Rules Using OCL**:
   - The system enforces various business rules and constraints, such as:
     - A reservation can only be made if there are available rooms.
     - Clients can only book a room if their account is valid.
     - Ensure that the price charged for the reservation matches the room category booked.
     - Clients cannot overbook rooms for the same day.
     
## System Overview
The system includes the following key components:

- **Client**: Represents hotel guests who can make reservations through the system.
- **Room**: Represents hotel rooms that are available for booking. Each room has a rate (price), review score, and type.
- **Reservation**: Represents a booking made by a client for a specific room.
- **Service**: Additional services that can be offered to clients during their stay.
- **Hotel**: Manages multiple rooms and tracks reservations.

![image](https://github.com/user-attachments/assets/fe3ade76-d14c-4ad2-b2bd-83186d6b35f6)

### Business Logic
- Clients can make a reservation for one or more rooms, but for simplicity, this project assumes that all reservations are for one day only.
- Each room has a price (rate) and a review score.
- If a room category is unavailable, clients may be upgraded to a higher room category.
- The system must ensure that the room booked by the client is the same room category for which they were charged.
- The hotel may offer additional services to clients, which can be added to the total booking amount.

## OCL Constraints
1. Can't have 2 hotels from the same company in one city
   **context** Group
   invariant uniqueCompanyhotel : self.Hotel -> isUnique(h:Hotel | h.city)
   We get collection for each groups of the hotels they own, and by asking if the Hotel.city is unique, so [1,0] in the same city.

2. Hotel Rating between 1-5
   **context** Hotel
   invariant hotelRating : self.rate > 0 and self.rate <= 5

3. Booking Date equal to requested Date at Order
   **context** Booking
   invariant Reservation_Booking_Date_match:
   self.date = Reservation.requestedDate 

4. Hotel can't have more then one room with the same ID
   **context** Hotel
   invariant uniqueRoomID: self.Room -> isUnique(r: Room | r.number)

5. 

6. Customer sum of expenses for all rooms stayed on all hotels can't exceed 10,000 per year, excluding special services
   **context** Client
   invariant ExpnsesLimitPerYear : ReservationSet.Reservation>collect(Booking.Room.RoomCategory.price)-> sum() < 10000$

7. Client can't review Hotel before finished the visit.
   **context** Booking
   invarinant legalReview: self.Date.isAfter(Review.Date)

8. Client City and Hotel city can't be the same
   **context** Client
   invariant: ReservationHotel.city <> self.city

9. Reservation and Hotel room are at the same Hotel
   **context** Reservation
   invariant: self.ReservationSet.Hotel  = Booking.Room.Hotel

10. Hotels from same group Hold the same Service
    **context** Group
    services = self.Hotel.HotelService.Service -> asSet
    self.Hotel->includesAll(services)

11. No Reservation of RoomCategory in Hotel that doesn't own them
    **context** Hotel
    invariant res_roomCategory = self.ReservationSet.Reservation->collect(RoomCategory):
    roomCategory = self.Room -> collect(RoomCategory)->asSet
    roomCategory -> includesAll(res_roomCategory)

12. Hotels with rating under 4 doesn't hold VIP Rooms
    **context** Hotel
    invarinat: result = select(rate < 4)
    result.Room.RoomCategory ->excludes(rc: RoomCategory | rc.RoomType ::VIP)

13. All VIP services given to VIP Rooms
    **context** HotelService
    vipRooms_services = Hotel.Room -> select(rc: RoomCategory  | rc.RoomType = VIP) -> collect(Booking).HotelService.Service
    vipRooms_services->forAll(s : Service | oclIsTypeOf(VipService))

14. Amount of VIP Rooms at Hotel can't exceed 10% of total rooms at the Hotel
    **context** Hotel
    self.Room->size() / Room->select(RoomCategory.RoomType::VIP) < 0.1

15. All Hotels at city = "LAS VEGES" contain CommunityService called "CASINO"
    **context** Hotel
    invariant: lasVegas_hotel = self -> select(city="LAS VEGES") 
    lasVegas_hotel.HotelService.Service->includes(CommintyService.name = "CASINO")

16. Client Age most be above 21 for Reservation in "LAS VEGES" Hotels
    **context** Hotel
    invariant above21LA: self -> select(city="LAS VEGES").ReservasionSet.Client -> forall(c:Client| age > 21)

17. Room Category can be same In Reservation and above

18. Client that Reserved VIP Room most write Review on the Hotel 
    **context** Booking
    invairant: Room.RoomCategory.RoomType  = VIP and self.Review -> exist()

19. Reviews Average is above 7.5 if Hotel Rate is 5
    **context** Hotel
    invariant: self -> select(rate = 5)
    let hotelReviews = self .ReservationsSet.Reservation.Booking.Review
    hotelReviews -> sum(rank) / HotelReviews -> size() >= 7.5

20. All Services given during visit Related to the Hotel
    **context** Booking
    invariant: self.HotelService.Hotel -> forall(h: Hotel | h = self.Room.Hotel)

21. Hotel can't have services with the same name
    **context** Hotel
    invariant: self.HotelService.Service -> isUnique(s :Service |s.Servicename)

22. Hotel income will be higher From last year
    **context** Hotel 
    Booking_year = HotelService.Booking.Date.year
    current_year_income = HotelService.Booking -> select(Date.at(Booking_year)) -> sum(price)
    previous_year_income = HotelService.Booking -> select(Date.at(Booking_year -1)) ->sum(price)

    current_year_income > previous_year_income 

    

## Technologies Used
- **Java**: For implementing the business logic and enforcing constraints.
- **OCL (Object Constraint Language)**: For defining and modeling system constraints.


