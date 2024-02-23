package ua.danyloki.vacanciestelegrambot.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.danyloki.vacanciestelegrambot.dto.VacancyDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VacancyService {
    @Autowired
    private VacanciesReaderService vacanciesReaderService;
    private final Map<String, VacancyDto> vacancies = new HashMap<>();

    @PostConstruct
    public void init() {
        List<VacancyDto> list = vacanciesReaderService.getVacanciesFromFile("vacancies.csv");
        for (VacancyDto vacancy : list) {
            vacancies.put(vacancy.getId(), vacancy);
        }

        /*
        VacancyDto googleDev = new VacancyDto();
        googleDev.setId("1");
        googleDev.setTitle("Junior Dev at Google");
        googleDev.setShortDescription("Welcome to Google!");
        vacancies.put("1", googleDev);

        VacancyDto middle = new VacancyDto();
        middle.setId("2");
        middle.setTitle("Middle Java Dev");
        middle.setShortDescription("Join our awesome company!");
        vacancies.put("2", middle);
        */
    }

    public List<VacancyDto> getJuniorVacancies() {
        return vacancies.values().stream().filter(v -> v.getTitle().toLowerCase().contains("junior")).toList();
    }

    public List<VacancyDto> getMiddleVacancies() {
        return vacancies.values().stream().filter(v -> v.getTitle().toLowerCase().contains("middle")).toList();
    }

    public List<VacancyDto> getSeniorVacancies() {
        return vacancies.values().stream().filter(v -> !v.getTitle().toLowerCase().contains("junior"))
                .filter(v -> !v.getTitle().toLowerCase().contains("middle"))
                .toList();
    }

    public VacancyDto get(String id) {
        return vacancies.get(id);
    }
}
