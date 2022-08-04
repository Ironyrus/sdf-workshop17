package workshop17.apiworkshop.app;

import java.io.File;
import java.io.FileReader;

/*
PS C:\Users\vans_\sdf-workshop1> 
git add . (add ALL content of cart to github)
git commit -m "While Loop"                  (add comment while committing)
git push origin main                        (push to main branch)
*/
//Deploying to Heroku using Github Actions - Kenneth's method
//https://github.com/marketplace/actions/deploy-to-heroku

//Deploying to Heroku using Heroku deployment features:
//https://devcenter.heroku.com/articles/github-integration#enabling-github-integration

import java.rmi.StubNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import workshop17.apiworkshop.models.currConverter;
import workshop17.apiworkshop.models.options;
import workshop17.apiworkshop.models.weatherObj;

@Controller
public class currencyWeatherController {

    @Autowired
    redisService service;

    @GetMapping
    public String getHome( Model model) {
        
        List<options> optionList = getCameraList();
        
        model.addAttribute("options", new options(optionList));

        return "index";
    }

    @GetMapping("/showWeather")
    public String showWeather(@RequestParam String country, Model model) {
        egg egg = new egg(country);
        weatherObj weatherObj = egg.getWeather(egg.coordinates);

        System.out.println("IN GETMAPPING: " + weatherObj.getWeather().get(0));
        System.out.println("weatherObj: " + weatherObj.getMain());
        HashMap weatherMap = new HashMap<>();
        weatherMap = (HashMap)weatherObj.getWeather().get(0);

        double temp = (double)weatherObj.getMain().get("temp");
        temp = temp - 273.15;

        String icon = (String)weatherMap.get("icon");
        String weatherNow =(String)weatherMap.get("main");
        // String temp = (String)mainMap.get("temp");

        model.addAttribute("weather", weatherObj);
        model.addAttribute("weatherNow", weatherNow);
        model.addAttribute("icon", icon);
        model.addAttribute("temp", temp);
        return "showWeather";
    }

    @GetMapping("showConverter")
    public String showConverter(Model model,
                                @RequestParam String from,
                                @RequestParam String to,
                                @RequestParam String amount) {
        System.out.println("From: " + from + " To: " + to + " Amount: " + amount);
        egg egg = new egg("Singapore");
        currConverter convObj = egg.convertCurrency(to, from, amount);
        model.addAttribute("date", convObj.getDate());
        model.addAttribute("result", convObj.getResult());
        model.addAttribute("from", convObj.getQuery().get("from"));
        model.addAttribute("to", convObj.getQuery().get("to"));
        model.addAttribute("amount", convObj.getQuery().get("amount"));
        model.addAttribute("rate", convObj.getInfo().get("rate"));
        return "showConverter";
    }

    @PostMapping("showTrafficCam")
    public String showTrafficCam(Model model,
                                @ModelAttribute options options) {
        System.out.println("TEST: " + options.getOptionNearbyLocations() + options.getOption() + options.getOptions());

        egg egg = new egg();
        HashMap camera = egg.getTrafficImage(options.getOption());
        
        List<options> optionList = getCameraList();
        //getting nearby locations
        String nearbyLocations = "No nearby location data found.";
        for (int i = 0; i < optionList.size(); i++) {
            if(optionList.get(i).getOption().equals(options.getOption())){
                nearbyLocations = (String)optionList.get(i).getOptionNearbyLocations();
            }
        }
        model.addAttribute("image", camera.get("image"));
        model.addAttribute("camLocation", options.getOption());
        model.addAttribute("timestamp", camera.get("timestamp"));
        model.addAttribute("nearbyLocations", nearbyLocations);
        service.save(camera.get("timestamp") + " | " + options.getOption().trim() + " | " + camera.get("image")); //works
        //service.deleteAll(); //works
        return "showTrafficCam";
    }

    //Helper class to get a list of options that users choose from website.
    public List<options> getCameraList() {
        FileReader fReader = null;
        List<String> options = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        //Read from file
        try {
            File myObj = new File("./src/main/resources/static/egg.txt");
            fReader = new FileReader(myObj);
            int i;
            while(((i=fReader.read()) != -1)) {
                sb.append((char)i);
            }
            
            fReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] terms = sb.toString().split("~");
        List<options> optionList = new ArrayList<>();

        for (int i = 0; i < terms.length; i++) {
            String[] temp = terms[i].split("Nearby Locations:");
            optionList.add(i, new options());
            optionList.get(i).setOption(temp[0]);
            optionList.get(i).setOptionNearbyLocations(temp[1]);
        }
        return optionList;
    }
}