package org.zerock.b01.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.b01.domain.Chartest;
import org.zerock.b01.repository.chartRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class chartController {

    // chartRepository
    private final chartRepository chartrepository;

    @GetMapping("/chart")
    public String showChartPage(Model model) {
        List<ChartData> chartData = new ArrayList<>();

        // DB에서 데이터 가져오기
        List<Chartest> Charttests = chartrepository.findAll();
        for (Chartest chartest : Charttests) {
            chartData.add(new ChartData(chartest.getItem(),chartest.getCell()));
        }

        model.addAttribute("chartData", chartData);
        return "chart";
    }





}
