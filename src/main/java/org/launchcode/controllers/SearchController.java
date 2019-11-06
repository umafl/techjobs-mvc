package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by LaunchCode & Josh Markus
 */


@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }


    @RequestMapping(value = "results")
    public String search(Model model, @RequestParam String searchColumn, @RequestParam String searchTerm) {

        // calling findByValue() in JobData
        // searching ALL columns for the specified searchTerm
        // displaying jobs that match
        if (searchColumn.equals("all")) {
            ArrayList<HashMap<String, String>> jobsByColumnAndValue = JobData.findByValue(searchTerm);
            model.addAttribute("jobList", jobsByColumnAndValue);
            model.addAttribute("columns", ListController.columnChoices);
            return "search";

            // calling findByColumnAndValue() in JobData
            // searching specified column for specified searchTerm
        } else {
            ArrayList<HashMap<String, String>> jobsByColumnAndValue = JobData.findByColumnAndValue(searchColumn, searchTerm);
            model.addAttribute("jobList", jobsByColumnAndValue);
            model.addAttribute("columns", ListController.columnChoices);
            return "search";
        }
    }
}


/**
 First successful attempt at searching by 'value' and 'column & value'.
 Realized I could refactor and use "JobData.findByColumnAndValue(searchColumn, searchTerm)"
 and "JobData.findByValue(searchTerm)"
 The code below represents verbose methods of the findBy...() methods in JobData:
 if (searchColumn.equals("all")) {
 for (int i = 0; i < jobs.size(); i++) {
 HashMap<String, String> job = jobs.get(i);
 for (Map.Entry<String, String> record : jobs.get(i).entrySet()) {
 String value = record.getValue();
 if (value.contains(searchTerm)) {
 searchList.add(job);
 }
 }
 }
 model.addAttribute("jobList", searchList);
 model.addAttribute("columns", ListController.columnChoices);
 return "search";
 // searching specified column for specified searchTerm
 } else {
 for (int i = 0; i < jobs.size(); i++) {
 HashMap<String, String> job = jobs.get(i);
 for (Map.Entry<String, String> record : jobs.get(i).entrySet()) {
 String key = record.getKey();
 String value = record.getValue();
 if (key.contains(searchColumn)) {
 if (value.contains(searchTerm)) {
 searchList.add(job);
 }
 }
 }
 }
 }
 **/
