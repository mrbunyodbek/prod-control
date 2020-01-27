package uz.pc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uz.pc.collections.Statistics;
import uz.pc.db.dao.interfaces.DashboardDAO;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private DashboardDAO dashboardDAO;

    public DashboardController(DashboardDAO dashboardDAO) {
        this.dashboardDAO = dashboardDAO;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<Statistics> getAll() {
        return new ResponseEntity<>(dashboardDAO.collectStatsForProduction(), HttpStatus.OK);
    }

}
