package uz.pc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uz.pc.db.dao.interfaces.DashboardDAO;

import java.time.LocalDateTime;

@Controller
public class MainController {

    private ModelAndView view;
    private DashboardDAO dashboardDAO;

    public MainController(DashboardDAO dashboardDAO) {
        this.dashboardDAO = dashboardDAO;
    }

    /*
     * Other pages
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView actionIndex() {
        view = new ModelAndView("dashboard");
        view.addObject("statsCollection", dashboardDAO.collectStatsForProduction(LocalDateTime.now().getMonth().name()));
        return view;
    }

    @RequestMapping(value = "/filter/{month}", method = RequestMethod.GET)
    public ModelAndView actionIndex(@PathVariable String month) {
        view = new ModelAndView("dashboard");
        view.addObject("statsCollection", dashboardDAO.collectStatsForProduction(month));
        return view;
    }

    /*
     * Pages for Products
     */
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ModelAndView actionProductsIndex() {
        view = new ModelAndView("pages/products/index");
        return view;
    }

    /*
     * Pages for Employees
     */
    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public ModelAndView actionEmployeesIndex() {
        view = new ModelAndView("pages/employees/index");
        return view;
    }

    /*
     * Pages for Production
     */
    @RequestMapping(value = "/production", method = RequestMethod.GET)
    public ModelAndView actionProductionIndex() {
        view = new ModelAndView("pages/production/index");
        return view;
    }

    @RequestMapping(value = "/production/add-new", method = RequestMethod.GET)
    public ModelAndView actionProductionRegister() {
        view = new ModelAndView("pages/production/register");
        return view;
    }

    /*
     * Pages for salaries
     */
    @RequestMapping(value = "/salaries", method = RequestMethod.GET)
    public ModelAndView actionSalariesIndex() {
        view = new ModelAndView("pages/salaries/index");
        return view;
    }

    /*
     * Pages for groups
     */
    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public ModelAndView actionGroupsIndex() {
        view = new ModelAndView("pages/groups/index");
        return view;
    }


    /*
     * Pages for settings
     */
    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public ModelAndView actionSettingsIndex() {
        view = new ModelAndView("pages/settings/index");
        return view;
    }


    /*
     * Pages for attendance
     */
    @RequestMapping(value = "/attendance", method = RequestMethod.GET)
    public ModelAndView actionAttendanceIndex() {
        view = new ModelAndView("pages/attendance/index");
        return view;
    }

}
