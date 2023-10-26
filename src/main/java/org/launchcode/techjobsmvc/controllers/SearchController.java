package org.launchcode.techjobsmvc.controllers;

import org.launchcode.techjobsmvc.models.Job;
import org.launchcode.techjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import static org.launchcode.techjobsmvc.controllers.ListController.columnChoices;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")//Base route is set to search
public class SearchController {

    @GetMapping(value = "")//Maps to search
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);//add column choices to the model
        return "search";//renders search template
    }

 //The handler processes search requests and updates the view
    @PostMapping(value = "results")//Handles POST requests to search/results and below we have logic to fetch jobs based on criteria
    public String displaySearchResults(Model model, @RequestParam String searchType,
                                       @RequestParam String searchTerm) {

        ArrayList<Job> jobs;
        if (searchType.equalsIgnoreCase("all") || searchTerm.isEmpty()) {
            jobs = JobData.findAll();
        } else {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
        }

        model.addAttribute("columns", columnChoices);
        model.addAttribute("jobs", jobs);
        model.addAttribute("searchPerformed",true);//indicates a search was performed

        return "search";//renders same search template but with results
    }
};
