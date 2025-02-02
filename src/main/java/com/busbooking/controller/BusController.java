package com.busbooking.controller;

import com.busbooking.model.Bus;
import com.busbooking.services.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/manage-buses")
public class BusController {
    @Autowired
    private BusService busService;

    @GetMapping
    public String showManageBusesPage(Model model) {
        List<Bus> buses = busService.getAllBuses();
        model.addAttribute("buses", buses);
        return "manage-buses";
    }

    @PostMapping("/add")
    public String addBus(@ModelAttribute Bus bus) {
        busService.saveBus(bus);
        return "redirect:/manage-buses";
    }

    @GetMapping("/delete/{id}")
    public String deleteBus(@PathVariable Long id) {
        busService.deleteBus(id);
        return "redirect:/manage-buses";
    }
}
