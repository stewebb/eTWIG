package net.etwig.webapp.controller.api;

import net.etwig.webapp.model.Portfolio;
import net.etwig.webapp.services.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
public class PortfolioAPIController {

    @Autowired
    PortfolioService portfolioService;

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Map<String, Object> eventInfo) {

        // TODO Add an Portfolio
        return null;
    }

    @PostMapping("/edit")
    public Map<String, Object> edit(@RequestBody Map<String, Object> eventInfo) {

        // TODO Edit an portfolio
        return null;
    }

    @PostMapping("/view")
    public Portfolio view(@RequestParam Long portfolioId) {
        return portfolioService.findById(portfolioId);
    }

    @PostMapping("/remove")
    public Map<String, Object> remove(@RequestBody Map<String, Object> eventInfo) {
        return null;
    }

}
