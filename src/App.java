import java.util.ArrayList;
import java.util.List;

//Travel Package
class TravelPackage {
    private String name;
    private int passengerCapacity;
    private List<Destination> itinerary;
    private List<Passenger> passengers;

    public TravelPackage(String name, int passengerCapacity) {
        this.name = name;
        this.passengerCapacity = passengerCapacity;
        this.itinerary = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }

    public void addDestination(Destination destination) {
        itinerary.add(destination);
    }

    public void addPassenger(Passenger passenger) {
        if (passengers.size() < passengerCapacity) {
            passengers.add(passenger);
        } else {
            System.out.println("Travel package is full. Cannot add more passengers.");
        }
    }

    public void printItinerary() {
        System.out.println("Travel Package: " + name);
        for (Destination destination : itinerary) {
            System.out.println("Destination: " + destination.getName());
            destination.printActivities();
        }
    }

    public void printPassengerList() {
        System.out.println("Passenger List for Travel Package: " + name);
        System.out.println("Passenger Capacity: " + passengerCapacity);
        System.out.println("Number of Passengers Enrolled: " + passengers.size());
        for (Passenger passenger : passengers) {
            System.out.println(passenger);
        }
    }

    public List<Activity> getItinerary() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getItinerary'");
    }

    public List<Activity> getPassengers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPassengers'");
    }
}


//Destination
class Destination {
    private String name;
    private List<Activity> activities;

    public Destination(String name) {
        this.name = name;
        this.activities = new ArrayList<>();
    }

    public void addActivity(Activity activity) {
      
        if (!activities.contains(activity)) {
            activities.add(activity);
            activity.setDestination(this); 
        } else {
            System.out.println("Activity " + activity.getName() + " already exists at this destination.");
        }
    }
    
    public void printAvailableActivities() {
        System.out.println("Available Activities at " + name + ":");
        for (Activity activity : activities) {
            int remainingCapacity = activity.getCapacity() - activity.getPassengerCount();
            System.out.println(activity.getCapacity());
            System.out.println(activity.getPassengerCount());
            if (remainingCapacity > 0) {
                System.out.println(activity.getName() + " - Remaining Capacity: " + remainingCapacity);
            }
        }
    }

    public void printActivities() {
        System.out.println("Activities at " + name + ":");
        for (Activity activity : activities) {
            System.out.println(activity);
        }
    }

    public String getName() {
        return name;
    }

    public List<Activity> getActivities() {
        return activities;
    }
}


//ACTIVITY
class Activity {
    private String name;
    private String description;
    private double cost;
    private int capacity;
    private Destination destination;
    private List<String> passengers;

    public Activity(String name, String description, double cost, int capacity, Destination destination) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.capacity = capacity;
        this.destination = destination;
        this.passengers = new ArrayList<>();
    }

    public void addPassenger(String name) {
        if (passengers.size() < capacity) {
            passengers.add(name);
            System.out.println("Passenger " + name + " added to activity: " + this.name);
        } else {
            System.out.println("Activity " + this.name + " is already full.");
        }
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public Destination getDestination() {
        return destination;
    }

    public double getCost() {
        return cost;
    }

    public int getPassengerCount() {
        return this.passengers.size();
    }

    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public String toString() {
        return "Activity: " + name + ", Description: " + description + ", Cost: " + cost + ", Capacity: " + capacity;
    }
}


//Passenger
class Passenger {
    private String name;
    private int passengerNumber;
    private PassengerType type;
    private double balance;
    private List<Activity> activities;

    public Passenger(String name, int passengerNumber, PassengerType type, double balance) {
        this.name = name;
        this.passengerNumber = passengerNumber;
        this.type = type;
        this.balance = balance;
        this.activities = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addActivity(Activity activity) {
        activity.addPassenger(name);
        if (type == PassengerType.PREMIUM) {
            activities.add(activity);
            System.out.println("Activity added for passenger: " + name);
        } else {
            double price = activity.getCost();
            if (type == PassengerType.GOLD) {
                price *= 0.9;
            }
            if (balance >= price) {
                activities.add(activity);
                balance -= price;
                System.out.println("Activity added for passenger: " + name);
            } else {
                System.out.println("Insufficient balance to add activity for passenger: " + name);
            }
        }
    }

    public void printActivities() {
        System.out.println("Activities for Passenger " + name + ":");
        for (Activity activity : activities) {
            System.out.println(activity);
        }
    }

    @Override
    public String toString() {
        return "Passenger: " + name + ", Passenger Number: " + passengerNumber + ", Type: " + type + ", Balance: " + balance;
    }
}


enum PassengerType {
    STANDARD,
    GOLD,
    PREMIUM
}

public class App {
    public static void main(String[] args) throws Exception {
        TravelPackage europePackage = new TravelPackage("Europe Tour", 50);
        Destination paris = new Destination("Paris");
        Destination  rome= new Destination("Rome");

        Activity eiffelTowerVisit = new Activity("Eiffel Tower Visit", "Visit the iconic Eiffel Tower", 50.0, 100, paris);
        Activity louvreMuseumTour = new Activity("Louvre Museum Tour", "Guided tour of the Louvre Museum", 40.0, 80, paris);
        Activity colosseumVisit = new Activity("Colosseum Visit", "Explore the ancient Roman Colosseum", 60.0, 120, rome);

        paris.addActivity(eiffelTowerVisit);
        paris.addActivity(louvreMuseumTour);
        rome.addActivity(colosseumVisit);

        Passenger passenger1 = new Passenger("Pratham", 7, PassengerType.STANDARD, 500.0);
        Passenger passenger2 = new Passenger("Deep", 45, PassengerType.GOLD, 900.0);

        passenger1.addActivity(paris.getActivities().get(0));
        passenger2.addActivity(rome.getActivities().get(0));

        europePackage.addPassenger(passenger1);
        europePackage.addPassenger(passenger2);

        europePackage.printItinerary();
        europePackage.printPassengerList();

        passenger1.printActivities();
        passenger2.printActivities();

        paris.printAvailableActivities();
        rome.printAvailableActivities();
    }
}
