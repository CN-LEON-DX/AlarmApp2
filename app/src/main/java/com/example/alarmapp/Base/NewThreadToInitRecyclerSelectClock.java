package com.example.alarmapp.Base;

import java.util.ArrayList;
import java.util.List;

public class NewThreadToInitRecyclerSelectClock extends Thread{
    private List<Clock> clockList = new ArrayList<>();
    @Override
    public void run() {
        super.run();
        addCity("Amsterdam",-6,"Europe/Amsterdam");
        addCity("Ankara",-4,"Europe/Istanbul");
        addCity("Athens",-5,"Europe/Athens");
        addCity("Berlin",-6,"Europe/Berlin");
        addCity("Bogota",-12,"America/Bogota");
        addCity("Brasilia",-10,"America/Sao_Paulo");
        addCity("Buenos Aires",-10,"America/Argentina/Buenos_Aires");
        addCity("Cairo",-5,"Africa/Cairo");
        addCity("Canberra",+4,"Australia/Sydney");
        addCity("Cape Town",-5,"Africa/Johannesburg");
        addCity("chiang Mai",+0,"Asia/Bangkok");
        addCity("Copenhagen",-6,"Europe/Copenhagen");
        addCity("Dubai",-3,"Asia/Dubai");
        addCity("Hà nội",+0,"Asia/Ho_Chi_Minh");
        addCity("Havana",-12,"America/Havana");
        addCity("Helsinki",-5,"Europe/Helsinki");
        addCity("Hồng Kông",+1,"Asia/Hong_Kong");
        addCity("Istanbul",-4,"Europe/Istanbul");
        addCity("Jakarta",+0,"Asia/Jakarta");
        addCity("Jerusalem",-5,"Asia/Jerusalem");
        addCity("Kuala Lumpur",+1,"Asia/Kuala_Lumpur");
        addCity("Tokyo",+2,"Asia/Tokyo");
        addCity("Lima",-12,"America/Lima");
        addCity("Lisbon",-7,"Europe/London");
        addCity("Los angles",-15,"America/Los_Angeles");
        addCity("Madrid",-6,"Europe/Madrid");
        addCity("Manila",+1,"Asia/Manila");
        addCity("Mexico City",-13,"America/Mexico_City");
        addCity("Moscow",-4,"Europe/Moscow");
        addCity("Nairobi",-4,"Africa/Nairobi");
        addCity("New York",-12,"America/New_York");
        addCity("Oslo",-6,"Europe/Oslo");
        addCity("Paris",-6,"Europe/Paris");
        addCity("Prague",-6,"Europe/Prague");
        addCity("Rio de Janeiro",-10,"America/Sao_Paulo");
        addCity("Rome",-6,"Europe/Rome");
        addCity("San Francisco",-15,"America/Los_Angeles");
        addCity("Santiago",-10,"America/Santiago");
        addCity("seoul",+2,"Asia/Seoul");
        addCity("Singapore",+1,"Asia/Singapore");
        addCity("Stockholm",-6,"Europe/Stockholm");
        addCity("Sydney",+4,"Australia/Sydney");
        addCity("Toronto",-12,"America/Toronto");
        addCity("Thượng Hải",+1,"Asia/Shanghai");
    }
    public void addCity(String nameCity,int timeDifferences,String timeZone){
        Clock clock = new Clock();
        clock.setCity(nameCity);
        clock.setTimeDifferences(String.valueOf(timeDifferences));
        clock.setTimeZone(timeZone);
        clockList.add(clock);
    }
    public ArrayList<Clock> list(){
        return new ArrayList<>(clockList);
    }
}
