package uz.pc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    private ModelAndView view;

    /*
     * Other pages
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView actionIndex() {
        view = new ModelAndView("dashboard");
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

    @RequestMapping(value = "/employees/create", method = RequestMethod.GET)
    public ModelAndView actionEmployeesCreate() {
        view.setViewName("pages/employees/create");
        return view;
    }

}
