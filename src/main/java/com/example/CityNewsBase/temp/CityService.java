package com.example.CityNewsBase.temp;


import com.example.CityNewsBase.model.cityNewsModel.City;
import com.example.CityNewsBase.model.cityNewsModel.State;
import com.example.CityNewsBase.repository.CityRepository;
import com.example.CityNewsBase.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;

    public void addCities(Map<String, List<String>> states) {
        for (Map.Entry<String, List<String>> entry : states.entrySet()) {
            String stateName = entry.getKey();
            List<String> cities = entry.getValue();

            // Sprawdź, czy stan już istnieje, jeśli nie, dodaj nowy
            State state = stateRepository.findByStateName(stateName);
            if (state == null) {
                state = new State();
                state.setStateName(stateName);
                state = stateRepository.save(state);
            }

            // Dodaj miasta do bazy danych
            for (String cityName : cities) {
                City city = new City();
                city.setCityName(cityName);
                city.setState(state);
                cityRepository.save(city);
            }
        }
    }
}
