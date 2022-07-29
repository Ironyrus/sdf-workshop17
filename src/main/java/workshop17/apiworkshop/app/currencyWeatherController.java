package workshop17.apiworkshop.app;

/*
PS C:\Users\vans_\sdf-workshop1> 
git add . (add ALL content of cart to github)
git commit -m "While Loop"                  (add comment while committing)
git push origin main                        (push to main branch)
*/

import java.rmi.StubNotFoundException;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class currencyWeatherController {
    
    @GetMapping
    public String getHome() {
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
}
